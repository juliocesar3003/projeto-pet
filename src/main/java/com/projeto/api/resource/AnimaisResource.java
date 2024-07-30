package com.projeto.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.projeto.api.entidades.Animais;
import com.projeto.api.service.AnimaisService;

@CrossOrigin(origins = "http://127.0.0.1:5500" )
@RestController
@RequestMapping(value = "/animais")
public class AnimaisResource {

	
	@Autowired
	private AnimaisService service;
	
	@GetMapping
	public List<Animais> findAll(){
	List <Animais> lista = service.findAll();
	return lista;
	}
	
	@GetMapping(value = "/{id}")
	public Animais findById(@PathVariable Long id){
		Animais obj = service.findById(id);
		return obj;
	}
	@GetMapping(value = "/buscarNomes/{nome}")
	public List<Animais> findByNome(@PathVariable String nome){
	List <Animais> lista = service.findbyNome(nome);
	return lista;
	}
	
	
	@PostMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Animais insert(@PathVariable Long id, @RequestBody Animais obj ){
		return obj = service.insert(obj, id);
		
	}	
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		service.delete(id);
	}

	@PutMapping(value =  "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
		public Animais update(@PathVariable Long id, @RequestBody Animais obj){
		return obj = service.update(id, obj);
		
	}
	
	}

