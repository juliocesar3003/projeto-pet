package com.projeto.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.projeto.api.DTO.Requests.Reponses.ClienteResponse;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.infra.exception.exceptions.CustomACessDeniedException;
import com.projeto.api.infra.exception.exceptions.CustomIllegalArgumentException;
import com.projeto.api.repository.EmpresaRepository;
import jakarta.persistence.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.repository.ClienteRepository;
import com.projeto.api.infra.exception.exceptions.DataBaseException;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientesService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EmpresaRepository empresaRepository;


	public List<ClienteResponse> findAll(JwtAuthenticationToken token){

		Empresa empresa = autenticarToken(token);

			List<Clientes> listaClients = repository.findByEmpresaAssociadaId(empresa.getId());

			return converterListaEmDto(listaClients);


	}


	private List<ClienteResponse> converterListaEmDto(List<Clientes> lista) {
		List<ClienteResponse> listaDto = new ArrayList<>();
		for(Clientes i : lista) {
			ClienteResponse dto =  converterClienteEmDto(i);
			listaDto.add(dto);
		}
		return listaDto;

	}

	private ClienteResponse converterClienteEmDto(Clientes i) {
		List<Cachorro> pets = new ArrayList<>();
		if(i.getPet() instanceof Cachorro){
			 pets =  i.getPet();
		}
	   ClienteResponse dto = new ClienteResponse(i.getNome(), i.getCelular(),i.getEndereco(),pets,i.getAgendamento());
		return dto;

	}

	public ClienteResponse findByid(Long id,JwtAuthenticationToken token) {

		Empresa empresa = autenticarToken(token);

		 Clientes cliente = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

		if(empresa.getId().equals(cliente.getEmpresaAssociada().getId())){
			return converterClienteEmDto(cliente);
		}
		else{
			throw  new AccessDeniedException("Cliente não pertence à empresa do usuario logado");
		}
	}
	
	public Clientes insert(Clientes obj, JwtAuthenticationToken token) {

		 Empresa empresa = autenticarToken(token);

		 Clientes cliente = CriarData(obj,empresa);

		return repository.save(cliente);
	}



	public void delete(Long id,JwtAuthenticationToken token) {

			Empresa empresa = autenticarToken(token);

			Clientes cliente = repository.findById(id).orElseThrow(
					()-> new ResourceNotFoundException(id));

			if(empresa.getId().equals(cliente.getEmpresaAssociada().getId())){
				repository.deleteById(id);
			}
			else{
				throw new RuntimeException("opsss deu algum erro! Não foi possivel deletar esse id");
			}

	}

	public ClienteResponse update (Long id ,Clientes obj, JwtAuthenticationToken token) {
		try {
			Empresa empresa = autenticarToken(token);
			Clientes cliente = repository.getReferenceById(id);
			if(empresa.getId().equals(cliente.getEmpresaAssociada().getId())){

				cliente = updateData(obj,cliente);

				repository.save(cliente);

				ClienteResponse response = converterClienteEmDto(cliente);

				return response;
			}
			else{
				throw  new CustomACessDeniedException("Cliente não pertence à empresa do usuario logado");
			}
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private Clientes CriarData(Clientes obj,Empresa empresa ) {
		Clientes cliente = new Clientes();
		cliente.setNome(obj.getNome());
		cliente.setCelular(obj.getCelular());
		cliente.setEndereco(obj.getEndereco());
		cliente.setEmpresaAssociada(empresa);
		return cliente;
	}

	private Clientes updateData(Clientes obj, Clientes cliente) {
		cliente.setNome(obj.getNome());
		cliente.setCelular(obj.getCelular());
		cliente.setEndereco(obj.getEndereco());

		return cliente;
	}

	private Empresa autenticarToken(JwtAuthenticationToken token) {

		Object empresaIdObject = token.getTokenAttributes().get("empresaId");

		if (empresaIdObject == null) {
			throw new CustomIllegalArgumentException("O atributo 'empresaId' não está presente no token.");
		}

		Long empresaId;
		try {
			empresaId = Long.valueOf(empresaIdObject.toString());
		} catch (NumberFormatException e) {
			throw new CustomIllegalArgumentException("O atributo 'empresaId' não é um número válido.");
		}


		Empresa empresa = empresaRepository.findById(empresaId)
				.orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

		return empresa;

	}
}
