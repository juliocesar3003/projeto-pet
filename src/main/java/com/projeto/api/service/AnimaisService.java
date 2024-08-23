package com.projeto.api.service;

import java.util.ArrayList;
import java.util.List;

import com.projeto.api.DTO.Requests.Reponses.ResponseAnimais;
import com.projeto.api.DTO.Requests.AnimalRequest;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;
import com.projeto.api.entidades.entidadesAnimais.factory.AnimalFactory;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.infra.exception.exceptions.CustomACessDeniedException;
import com.projeto.api.infra.exception.exceptions.CustomIllegalArgumentException;
import com.projeto.api.repository.ClienteRepository;
import com.projeto.api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
	private ClienteRepository clienteRepository;

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public List<ResponseAnimais> findAll(JwtAuthenticationToken token){

		Empresa empresa = autenticarToken(token);

		List<Animais> lista = repository.findByEmpresaAssociadaId(empresa.getId());

		return converterInResponse(lista);
	}

	private List<ResponseAnimais> converterInResponse(List<Animais> lista) {
		List<ResponseAnimais> listaCachorro = new ArrayList<>();
		for (Animais i : lista) {
			listaCachorro.add(transformeInReponse((Cachorro) i));
		}

		return listaCachorro;
	}

	public List<Animais> findbyNome(String nome){
		return repository.findByNome(nome);
	}
	
	public ResponseAnimais findById(Long id,JwtAuthenticationToken token) {

		Empresa empresa = autenticarToken(token);

		ResponseAnimais response = null;

		Animais animal = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));

		if(empresa.getId().equals(animal.getEmpresaAssociada().getId())){

			if (animal instanceof Cachorro) {
				return response = transformeInReponse((Cachorro) animal);
			}
			else {
				return response = transformeInReponseGenerico(animal);
			}
		}
		else{
			throw  new CustomACessDeniedException("animal não pertence à empresa do usuario logado");
		}
	}

	public Animais insert(String tipo, AnimalRequest obj, long id, JwtAuthenticationToken token) {

		Empresa empresa = autenticarToken(token);

		Animais pet = transformarObjAnimal(obj,tipo,id,empresa);

		return repository.save(pet);
	}
	
	public void delete(Long id, JwtAuthenticationToken token) {
		try {
			Empresa empresa = autenticarToken(token);

			Animais animal = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

			if(empresa.getId().equals(animal.getEmpresaAssociada().getId())){

				repository.deleteById(id);
			}
			else{
				throw new RuntimeException("opsss deu algum erro! Não foi possivel deletar esse id");
			}
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	public Animais update(Long id, Animais obj,JwtAuthenticationToken token) {

		   Empresa empresa = autenticarToken(token);

		   Animais animal = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

			if(empresa.getId().equals(animal.getEmpresaAssociada().getId())){

				if (animal instanceof Cachorro) {
					updateAnimal((Cachorro) animal,(Cachorro) obj);
					return  repository.save(animal);
				}
				else {
					updateAnimalGenerico(animal,obj);
					return repository.save(animal);
				}

			}
			else{
				throw new CustomACessDeniedException("animal não pertence à empresa do usuario logado");
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


	private Animais transformarObjAnimal(AnimalRequest obj, String tipo, Long id, Empresa empresa){


		Clientes cliente = clienteRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException(id));
		Animais pet =
			factory.criarPet(tipo,
					obj.nome(),
					obj.raca(),
					obj.obs(),
					cliente,
					empresa,
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
