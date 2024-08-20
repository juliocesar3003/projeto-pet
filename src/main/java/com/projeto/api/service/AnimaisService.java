package com.projeto.api.service;

import java.util.List;


import com.projeto.api.DTO.Requests.Reponses.ResponseAnimais;
import com.projeto.api.DTO.Requests.RequestAnimalDto;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;
import com.projeto.api.entidades.entidadesAnimais.factory.AnimalFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.repository.AnimaisRepository;
import com.projeto.api.infra.exception.exceptions.DataBaseException;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;



@Service
public class AnimaisService {

	@Autowired
	private AnimalFactory factory;

	@Autowired
	private AnimaisRepository repository;

	@Autowired
	private ClientesService clienteService;
	
	public List<Animais> findAll(){
		return repository.findAll();
	}
	public List<Animais> findbyNome(String nome){
		return repository.findByNome(nome);
	}
	
	public ResponseAnimais findById(Long id) {
		Animais animal = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));

		if (animal instanceof Cachorro) {
			return transformeInReponse((Cachorro) animal);
		} else {
			return transformeInReponseGenerico(animal);
		}

	}

	public Animais insert( String tipo,RequestAnimalDto	 obj, long id) {

		Animais pet = transformarObjAnimal(obj,tipo,id);
		return repository.save(pet);
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
			Animais animal = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

			if (animal instanceof Cachorro) {
			 	 updateAnimal((Cachorro) animal,(Cachorro) obj);
				  return  repository.save(animal);
			}
			else {
				updateAnimalGenerico(animal,obj);
				return repository.save(animal);
			}


	}
	private void updateAnimalGenerico(Animais animal, Animais obj) {
		animal.setNome(obj.getNome());
		animal.setRaca(obj.getRaca());
		animal.setObservacao(obj.getObservacao());

	}

	private void updateAnimal(Cachorro animal, Cachorro obj) {
		animal.setNome(obj.getNome());
		animal.setRaca(obj.getRaca());
		animal.setObservacao(obj.getObservacao());
		animal.setPorte(obj.getPorte());
		animal.setPelagem(obj.getPelagem());
	}


	private Animais transformarObjAnimal(RequestAnimalDto obj, String tipo,Long id){
		Clientes cliente = clienteService.findByid(id);
		Animais pet =
			factory.criarPet(tipo,
					obj.nome(),
					obj.raca(),
					obj.obs(),
					cliente,
					Porte.valueOf(obj.porte()),
					Pelagem.valueOf(obj.pelagem())
					);
			return pet;
	}

	private ResponseAnimais transformeInReponse(Cachorro cachorro) {
		ResponseAnimais response = new ResponseAnimais(
				cachorro.getId(),
				"cachorro",
				cachorro.getNome(),
				cachorro.getRaca(),
				cachorro.getObservacao(),
				cachorro.getCliente().getNome(),
				cachorro.getPorte().toString(),
				cachorro.getPelagem().name().toString());

		return response;
	}

	private ResponseAnimais transformeInReponseGenerico(Animais animal) {
		return new ResponseAnimais(
				animal.getId(),
				animal.getClass().getSimpleName().toLowerCase(),
				animal.getNome(),
				animal.getRaca(),
				animal.getObservacao(),
				animal.getCliente().getNome(),
				null,
				null
		);
	}


}
