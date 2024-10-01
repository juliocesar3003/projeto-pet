package com.projeto.api.service;

import com.projeto.api.DTO.Request.AnimalRequest;
import com.projeto.api.DTO.Reponses.ResponseAnimais;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;
import com.projeto.api.entidades.entidadesAnimais.factory.AnimalFactory;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.infra.exception.exceptions.DataBaseException;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;
import com.projeto.api.repository.AnimaisRepository;
import com.projeto.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class AnimaisService {

	@Autowired
	private AnimalFactory factory;

	@Autowired
	private AnimaisRepository repository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EmpresaService empresaService;

	public List<ResponseAnimais> findAll(JwtAuthenticationToken token){

		Empresa empresa = empresaService.autenticarToken(token);

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

	public ResponseAnimais findByIdResponse(Long id,JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);

		ResponseAnimais response = null;

		Animais animal = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));

			animal = verificarServicoAssociado(animal,empresa);

			if (animal instanceof Cachorro) {
				return response = transformeInReponse((Cachorro) animal);
			}
			else {
				return response = transformeInReponseGenerico(animal);
			}
		}

	public Animais findById(Long id,JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);

		Animais animal = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));

		animal = verificarServicoAssociado(animal,empresa);

		if (animal instanceof Cachorro) {
			return (Cachorro) animal;
		}
		else {
			return animal;
		}
	}



	public void insert(String tipo, AnimalRequest obj, long id, JwtAuthenticationToken token) {

		Empresa empresa = empresaService.autenticarToken(token);

		Animais pet = transformarObjAnimal(obj,tipo,id,empresa);

		 repository.save(pet);
	}
	
	public void delete(Long id, JwtAuthenticationToken token) {
		try {
			Empresa empresa = empresaService.autenticarToken(token);

			Animais animal = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

			animal = verificarServicoAssociado(animal, empresa);

			repository.delete(animal);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
		catch(RuntimeException x) {
			throw new RuntimeException("opss deu algum erro, tente novamente mais tarde");
		}
	}
	
	public Animais update(Long id, Animais obj,JwtAuthenticationToken token) {

		   Empresa empresa = empresaService.autenticarToken(token);

		   Animais animal = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));


			animal = verificarServicoAssociado(animal,empresa);

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

	public ResponseAnimais transformeInReponse(Cachorro cachorro) {
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

	public ResponseAnimais transformeInReponseGenerico(Animais animal) {
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

	public Animais verificarServicoAssociado(Animais animais, Empresa empresa) {
		return empresaService.verificarAssociacaoComEmpresa(animais, empresa, Animais::getEmpresaAssociada);
	}
}
