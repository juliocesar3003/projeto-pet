package com.projeto.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.Animais;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.repository.ClienteRepository;
import com.projeto.api.resource.dto.Animais.ExibirAnimaisDto;
import com.projeto.api.resource.dto.clientes.ExibirClientesdto;
import com.projeto.api.service.exceptions.DataBaseException;
import com.projeto.api.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientesService {

	@Autowired
	private ClienteRepository repository;
	
	
	
	public List<ExibirClientesdto> findAll(){
		List<Clientes> listaClients = repository.findAll();
		return converterListaEmDto(listaClients);
	}
	
	
	public ExibirClientesdto findByCelular(Long celular){
		Clientes cliente =  repository.findByCelular(celular);
		return converterClienteEmDto(cliente);
	}
	
	
	
	private List<ExibirClientesdto> converterListaEmDto(List<Clientes> lista) {
		List<ExibirClientesdto> listaDto = new ArrayList<>();
		for(Clientes i : lista) {
			ExibirClientesdto dto =  converterClienteEmDto(i);
			listaDto.add(dto);
		}
		return listaDto;
		
	}

	private ExibirClientesdto converterClienteEmDto(Clientes i) {
		ExibirClientesdto dto = new ExibirClientesdto();
		
		return dto.builder()
				 .nome(i.getNome())
				 .celular(i.getCelular())
				 .listaAnimais(conveterAnimais(i.getPet()))
				 .build();
		
	}

	private List<ExibirAnimaisDto> conveterAnimais(List<Animais> pet) {
		
		return pet.stream().map(
			animal -> ExibirAnimaisDto.builder()
			.nome(animal.getNome())
			.observacoes(animal.getObservacao())
			.porte(animal.getPorte().toString())
			.raca(animal.getRaca()).build()
			).collect(Collectors.toList());
	}

	public Clientes findByid(Long id) {
		Optional<Clientes> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
		
	}
	
	public Clientes insert( Clientes obj) {
		return repository.save(obj);
	}
	
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
	public void deletePorCelular(Long celular) {
		try {
			Clientes cliente = repository.findByCelular(celular);
			repository.deleteById(cliente.getId());
		}		
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(celular);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
		
	}
	
	public Clientes update (Long id ,Clientes obj) {
		try {
			Clientes cliente = repository.getReferenceById(id);
			updateData(cliente,obj);
			return repository.save(cliente);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Clientes cliente, Clientes obj) {
	  cliente.setNome(obj.getNome());
	  cliente.setCelular(obj.getCelular());
	  cliente.setEndereco(obj.getEndereco());
		
	}
}
