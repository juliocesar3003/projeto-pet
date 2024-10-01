package com.projeto.api.service;

import com.projeto.api.DTO.Reponses.AgendamentoResponse;
import com.projeto.api.DTO.Reponses.ResponseAnimais;
import com.projeto.api.DTO.Reponses.ValoresResponse;
import com.projeto.api.DTO.Request.AgendamentoRequest;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.sobreAgendamento.Builder.AgendamentoBuilder;
import com.projeto.api.entidades.sobreAgendamento.Stage.*;
import com.projeto.api.entidades.sobreAgendamento.StatusAgendamento;
import com.projeto.api.entidades.sobreAgendamento.TaxaDeAtraso;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.infra.exception.exceptions.CustomDeleteporStageError;
import com.projeto.api.infra.exception.exceptions.CustomIllegalArgumentException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;

import com.projeto.api.repository.AgendamentosRepository;
import com.projeto.api.infra.exception.exceptions.DataBaseException;



import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class AgendamentosService {

	@Autowired
	private AgendamentosRepository repository;

	@Autowired
	private ClientesService clienteService;

	@Autowired
	private AnimaisService animaisService;
	
	@Autowired
	private ValoresService valoresService;

	@Autowired
	private AgendamentoDirector director;

	@Autowired
	private EmpresaService empresaService;
	

	public List<AgendamentoResponse> findAllResponse(JwtAuthenticationToken token){

		Empresa empresa = empresaService.autenticarToken(token);

		List<Agendamentos> lista = repository.findByEmpresaAssociadaId(empresa.getId());

		List<AgendamentoResponse> listaResponse = new ArrayList<>();

		for (Agendamentos i : lista){
			listaResponse.add(criarResponse(i));
		}


		return listaResponse;

	}



	public AgendamentoResponse findByIdResponse(Long id, JwtAuthenticationToken token) {
		Empresa empresa = empresaService.autenticarToken(token);

		Agendamentos agendamento = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

		agendamento = verificarAgendamentoAssociado(agendamento,empresa);

		AgendamentoResponse response = criarResponse(agendamento);

	    return response;

	}

	private AgendamentoResponse criarResponse(Agendamentos obj) {

		ResponseAnimais responseAnimal = transformarResponseAnimais(obj);
		List<ValoresResponse> valoresResponses = valoresService.transformeListResponse(obj.getServicos());

				AgendamentoResponse response =  new AgendamentoResponse(
						obj.getId(),
						obj.getHorarioEntrada(),
						obj.getHorarioEntradaRealizada(),
						obj.getHorarioSaida(),
						obj.getHorarioSaidaRealizada(),
						obj.getFormaDePagamento().toString(),
						obj.getStatus().toString(),
						obj.getCliente().getNome(),
						responseAnimal,
						valoresResponses,
						obj.getValorTotal()
				);
		return response;
	}

	private ResponseAnimais transformarResponseAnimais(Agendamentos obj){
		if(obj.getAnimal() instanceof Cachorro){
			ResponseAnimais responseAnimal = animaisService.transformeInReponse((Cachorro) obj.getAnimal());
			return responseAnimal;
		}
		else{
			ResponseAnimais responseAnimalGenerico = animaisService.transformeInReponseGenerico((Animais) obj.getAnimal());
			return responseAnimalGenerico;
		}
	}




	



	@Transactional
	public void iniciarAgendamento(AgendamentoRequest obj, JwtAuthenticationToken token){

		Agendamentos agendamento = criarAgendamento(obj,token);

		repository.save(agendamento);

		}

	private Agendamentos criarAgendamento(AgendamentoRequest obj, JwtAuthenticationToken token){

		Empresa empresa = empresaService.autenticarToken(token);
		Clientes cliente = clienteService.findByid(obj.cliente(),token);
		Animais animal =  animaisService.findById(obj.animal(),token);
		List<ValoresPorServico> servicos = instaciarListaServico(obj.servicos(),token);
		Double valor = instaciarValor(servicos);
		director.setStage(new StageAgendado());

		 return director.saveCurrentStateAgendado(obj,cliente,animal,servicos,empresa,valor);


	}


	@Transactional
	public void transitarParaEstagio(Long id, String nextStage, AgendamentoRequest request,JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);
		Agendamentos agendamento = repository.findByEmpresaAssociadaIdAndId(empresa.getId(),id);
		director.setStage(getStageFromName(nextStage));
		director.setBuilder(new AgendamentoBuilder().fromExisting(agendamento));
		Double valor = 0.00;

		switch (nextStage) {
			case "SegundoEstagio":
				 List<ValoresPorServico> servicos = instaciarListaServicoExistente(agendamento.getServicos(),request.servicos(),token);
				 valor = instaciarValorExistente(agendamento.getValorTotal(),servicos,agendamento.getHorarioEntrada(),request.horarioEntradaRealizada());
				 director.saveCurrentStateEmAndamento(request,servicos,valor);
				break;

			case "TerceiroEstagio":
				 valor = agendamento.getValorTotal() + TaxaDeAtraso.calcularTaxaDeAtrasoSaida(agendamento.getHorarioSaida().toLocalTime(),request.horarioSaidaRealizada().toLocalTime());
				 director.saveCurrentStateFinalizado(request,valor);
				break;
			case "QuartoEstagio":
				 valor += agendamento.getValorTotal();
				 director.saveCurrentStateCancelado(request,valor);
				break;
			default:
				throw new CustomIllegalArgumentException("Estágio desconhecido: " + nextStage);
		}


		 repository.save(agendamento);
	}

	private Double instaciarValor(List<ValoresPorServico> servicos) {
		Double valorTotal = 0.00;
		for(ValoresPorServico i : servicos){
			valorTotal += i.getValor();
		}
		return  valorTotal;
	}

	private Double instaciarValorExistente(Double valor,List<ValoresPorServico> servicos, LocalDateTime entrada,LocalDateTime entradaRealizada) {
		Double valorTotal = 0.00;
		for(ValoresPorServico i : servicos){
			valorTotal += i.getValor();
		}
		Double taxa = TaxaDeAtraso.calcularTaxaDeAtrasoEntrada(entrada.toLocalTime(),entradaRealizada.toLocalTime());

		return valor + valorTotal + taxa;
	}


	private AgendamentoStage getStageFromName(String stageName) {
		switch (stageName) {
			case "SegundoEstagio":
				return new StageEmAndamento();
			case "TerceiroEstagio":
				return new StageFinalizado();
			case "QuartoEstagio":
				return new StageCancelado();
			default:
				throw new CustomIllegalArgumentException("Estágio desconhecido: " + stageName);
		}
	}


	private List<ValoresPorServico> instaciarListaServico(List<Long> servicos,JwtAuthenticationToken token) {
		List<ValoresPorServico> listaServicos = new ArrayList<>();
		if(servicos.equals(null)){
			throw new CustomIllegalArgumentException("lista esta vazia");
		}
		for(Long id : servicos){
			listaServicos.add(valoresService.findById(id,token));
		}
		return listaServicos;
	}

	private List<ValoresPorServico> instaciarListaServicoExistente(List<ValoresPorServico> servicosExistente,List<Long> servicos,JwtAuthenticationToken token) {

		List<ValoresPorServico> listaServicos = new ArrayList<>();
		if(servicos == null){
			return listaServicos;
		}
		else{
			for(Long id : servicos){
				ValoresPorServico servico = valoresService.findById(id,token);
				if(servicosExistente.equals(servico)){
					throw new CustomIllegalArgumentException("Não é possivel colocar um serviço já adicionado");
				}
				listaServicos.add(servico);
			}
			return listaServicos;
		}
	}


	public void delete(Long id,JwtAuthenticationToken token) {
		try {
			Empresa empresa = empresaService.autenticarToken(token);
			Agendamentos agendamento = repository.findByEmpresaAssociadaIdAndId(empresa.getId(),id);
			if(agendamento.getStatus().equals(StatusAgendamento.CANCELADO)){
				repository.deleteById(agendamento.getId());
			}
			else{
				throw new CustomDeleteporStageError("Precisa coloca-lo em estagio cancelado para deleta-lo");
			}

		}		
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}


	public Agendamentos verificarAgendamentoAssociado(Agendamentos agendamento, Empresa empresa) {
		return empresaService.verificarAssociacaoComEmpresa(agendamento, empresa, Agendamentos::getEmpresaAssociada);
	}

	}

