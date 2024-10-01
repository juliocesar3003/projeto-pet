package com.projeto.api.service;

import java.util.ArrayList;
import java.util.List;

import com.projeto.api.DTO.Reponses.ServicoResponse;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.Servicos.Servico;
import com.projeto.api.repository.ServicoRepository;
import com.projeto.api.infra.exception.exceptions.DataBaseException;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;

@Service
public class ServicoService {

	
	@Autowired
	private ServicoRepository repository;

	@Autowired
	private EmpresaService empresaService;
	
	public List<ServicoResponse> findAll(JwtAuthenticationToken token){

		Empresa empresa = empresaService.autenticarToken(token);

			List<Servico> lista = repository.findByEmpresaAssociadaId(empresa.getId());

			return transformeListResponse(lista);

	}

	public Servico findByIdInterno(Long id,JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);

		Servico obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		obj = verificarServicoAssociado(obj,empresa);

		return obj;

	}


	public ServicoResponse findById(Long id,JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);

		Servico obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		obj = verificarServicoAssociado(obj,empresa);

		return trasformeResponse(obj);

	}
	
	public void insert(Servico obj, JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);

		Servico servico = criarObj(obj,empresa);

		repository.save(servico);
	}



	public void delete(Long id,JwtAuthenticationToken token) {
		try {
		 Empresa empresa = empresaService.autenticarToken(token);

		 Servico servico = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

		 servico = verificarServicoAssociado(servico,empresa);

		 repository.delete(servico);

		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	public void update(Long id, Servico obj,JwtAuthenticationToken token) {

			Empresa empresa = empresaService.autenticarToken(token);

			Servico servico = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

			servico = verificarServicoAssociado(servico,empresa);

			servico = updateObj(servico,obj);

			repository.save(servico);


	}

	private Servico updateObj(Servico servico,Servico obj) {
		servico.setNome(obj.getNome());
		return servico;
	}

	@NonNull
	private Servico criarObj(@NonNull Servico obj, Empresa empresa) {
		Servico servicoConcreto = new Servico();
		servicoConcreto.setNome(obj.getNome());
		servicoConcreto.setEmpresaAssociada(empresa);
		return servicoConcreto;
	}

	public Servico verificarServicoAssociado(Servico servico, Empresa empresa) {
		return empresaService.verificarAssociacaoComEmpresa(servico, empresa, Servico::getEmpresaAssociada);
	}

	private List<ServicoResponse> transformeListResponse(List<Servico> lista) {

		List<ServicoResponse> listResponse = new ArrayList<>();
		for(Servico i : lista){
			ServicoResponse response = 	trasformeResponse(i);
			listResponse.add(response);
		}
		return listResponse;
	}

	private ServicoResponse trasformeResponse(Servico i) {
		ServicoResponse response = new ServicoResponse(i.getNome());
		return response;
	}

}
