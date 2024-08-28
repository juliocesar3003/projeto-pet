package com.projeto.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;

import com.projeto.api.repository.AgendamentosRepository;
import com.projeto.api.infra.exception.exceptions.DataBaseException;



import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;


import jakarta.persistence.EntityNotFoundException;

@Service
public class AgendamentosService {

	@Autowired
	private AgendamentosRepository repository;
	

	
	@Autowired
	private ClientesService clienteService;
	
	@Autowired
	private ServicoService ServicoService;
	


  
//
//	@Transactional(readOnly = true)
//	public List<ExibirAgendamentoDTO> findAll(){
//		List<Agendamentos> lista =  repository.findAll();
//		return converterListaDeAgendamentos(lista);
//	}
//
//
//	private List<ExibirAgendamentoDTO> converterListaDeAgendamentos(List<Agendamentos> lista) {
//		List<ExibirAgendamentoDTO> listaDto = new ArrayList<>();
//		for(Agendamentos i : lista) {
//		ExibirAgendamentoDTO obj =converterobj(i);
//		listaDto.add(obj);
//		}
//		return listaDto ;
//		}
//
//
//
//	@Transactional(readOnly = true)
//	public ExibirAgendamentoDTO findById(Long id) {
//	    Optional<Agendamentos> objOptional = repository.findById(id);
//	    Agendamentos obj = objOptional.orElseThrow(() -> new ResourceNotFoundException(id));
//	    return converterobj(obj);
//
//	}
//
//	private ExibirAgendamentoDTO converterobj(Agendamentos obj) {
//
//	ExibirAgendamentoDTO Dto = new ExibirAgendamentoDTO();
//
//	return	Dto.builder()
//		.NumeroCelular(obj.getCliente().getCelular())
//		  .nomeAnimal(obj.getAnimal()
//			.getNome())
//		     .nomeCliente(obj.getCliente().getNome())
//		      .servicos(converter(obj.getServicos()))
//		       .date(obj.getDate())
//		        .ValorTotal(obj.getValorTotal()).build();
//
//	}

	

//	private List<ExibirServicosDto> converter(List<Servico> servicos) {
//		return servicos.stream().map(
//				servico -> ExibirServicosDto.builder()
//				.NomeServico(servico.getServico())
//				.Valor(servico.getValor())
//				.build()
//				).collect(Collectors.toList());
//
//	}

//	@Transactional
//	public Agendamentos insert(AgendamentosDTO obj){
//		Agendamentos agendamento = new Agendamentos();
//		Animais objAnimal =  animaisService.findById(obj.getIdAnimal());
//		agendamento.setDate(obj.getData());
//		agendamento.setAnimal(objAnimal);
//		agendamento.setCliente(objAnimal.getCliente());
//		List<TipoServicos> lista = addservico(obj.getServicos(), agendamento);
//	    agendamento.getServicos().addAll(lista);
//		repository.save(agendamento);
//		return agendamento;
//
//		}
	 
		
	

//	private List<Servico> addservico(List<InforServicosDto> servicos, Agendamentos agendamento) {
//		List<Servico> lista = new ArrayList<>();
//		double total = 0;
//
//		if(servicos == null) {
//			System.out.println("lista vazia");
//		}
//		else {
//		for(InforServicosDto i : servicos) {
//			Servico servico = ServicoService.findById(i.getNumeroServico());
//			lista.add(servico);
//			total += servico.getValor();
//		}
//		}
//
//		agendamento.setValorTotal(total);
//		return lista;
//	}


	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}		
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
		
	}
	
	public Agendamentos update(Long id, Agendamentos obj) {
		try {
			Agendamentos agendamento = repository.getReferenceById(id);
			updateData(agendamento,obj);
			return repository.save(agendamento);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Agendamentos agendamento, Agendamentos obj) {
		agendamento.setDate(obj.getDate());
		agendamento.setServicos(obj.getServicos());
		
	}

	}

