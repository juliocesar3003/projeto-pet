package com.projeto.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.Animais;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.repository.AnimaisRepository;
import com.projeto.api.service.exceptions.DataBaseException;
import com.projeto.api.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AnimaisService {

	@Autowired
	private AnimaisRepository repository;

	@Autowired
	private ClientesService clienteService;
	
	public List<Animais> findAll(){
		return repository.findAll();
	}
	
	public Animais findById(Long id) {
		Optional<Animais> animal = repository.findById(id);
		return animal.orElseThrow(()-> new ResourceNotFoundException(id));
	}
	
	public Animais insert(Animais obj, long id) {
		Clientes objCliente = clienteService.findByid(id);
		obj.setCliente(objCliente);
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
	
	public Animais update(Long id, Animais obj) {
			try {
				Animais animal = repository.getReferenceById(id);
				updateData(animal,obj);
				return repository.save(animal);
			}
			catch(EntityNotFoundException e ) {
				throw new ResourceNotFoundException(id);
			}
	}

	private void updateData(Animais animal, Animais obj) {
		animal.setNome(obj.getNome());
		animal.setPorte(obj.getPorte());
		animal.setRaca(obj.getRaca());
		animal.setTipoAnimal(obj.getTipoAnimal());
		animal.setCliente(obj.getCliente());
		animal.setObservacao(obj.getObservacao());
		
	}
}
