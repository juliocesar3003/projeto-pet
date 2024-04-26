package com.projeto.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.repository.ClienteRepository;
import com.projeto.api.service.exceptions.DataBaseException;
import com.projeto.api.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientesService {

	@Autowired
	private ClienteRepository repository;
	
	
	
	public List<Clientes> findAll(){
		return repository.findAll();
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
