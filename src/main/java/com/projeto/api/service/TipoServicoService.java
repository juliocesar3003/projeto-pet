package com.projeto.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.Agendamentos;
import com.projeto.api.entidades.TipoServicos;
import com.projeto.api.repository.TipoServicoRepository;
import com.projeto.api.service.exceptions.DataBaseException;
import com.projeto.api.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoServicoService {

	
	@Autowired
	private TipoServicoRepository repository;
	
	public List<TipoServicos> findAll(){
		return repository.findAll();
	}
	
	public TipoServicos findById(Long id) {
		Optional<TipoServicos> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public TipoServicos insert(TipoServicos obj) {
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
	
	public TipoServicos update(Long id, TipoServicos obj) {
		try {
			TipoServicos obj1 = repository.getReferenceById(id);
			UpdateData(obj,obj1);
			return repository.save(obj1);
		}
		catch(EntityNotFoundException e) {
		throw new ResourceNotFoundException(id);
	}
	}
	public void inserirAgendamento(TipoServicos obj) {
		repository.save(obj);
		}
	
	

	private void UpdateData(TipoServicos obj, TipoServicos obj1) {
		obj1.setServico(obj.getServico());
		obj1.setValor(obj.getValor());
		
	}
	
}
