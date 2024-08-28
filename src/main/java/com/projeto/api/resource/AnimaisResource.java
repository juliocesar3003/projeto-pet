package com.projeto.api.resource;

import java.util.List;

import com.projeto.api.DTO.Requests.Reponses.ResponseAnimais;
import com.projeto.api.DTO.Requests.AnimalRequest;
import com.projeto.api.service.AnimaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.api.entidades.entidadesAnimais.Animais;

@CrossOrigin(origins = "http://127.0.0.1:5500" )
@RestController
@RequestMapping(value = "/animais")
public class AnimaisResource {

	
	@Autowired
	private AnimaisService service;
	
	@GetMapping
	public List<ResponseAnimais> findAll(JwtAuthenticationToken token){
	List <ResponseAnimais> lista = service.findAll(token);
	return lista;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseAnimais findById(@PathVariable Long id, JwtAuthenticationToken token){
		ResponseAnimais obj = service.findById(id,token);
		return obj;
	}
	@GetMapping(value = "/buscarNomes/{nome}")
	public List<Animais> findByNome(@PathVariable String nome){
	List <Animais> lista = service.findbyNome(nome);
	return lista;
	}
	
	
	@PostMapping(value = "/{tipoAnimal}/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void insert(@PathVariable String tipoAnimal, @PathVariable Long id, @RequestBody AnimalRequest obj, JwtAuthenticationToken token){
		service.insert(tipoAnimal, obj, id,token);
	}	
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id,JwtAuthenticationToken token){
		service.delete(id,token);
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
		public void update(@PathVariable Long id, @RequestBody Animais obj,JwtAuthenticationToken token){
		 service.update(id,obj,token);
		
	}
	
	}

