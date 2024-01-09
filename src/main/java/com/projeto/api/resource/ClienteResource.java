package com.projeto.api.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.PutExchange;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.service.ClientesService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping(value = "/users")
public class ClienteResource {

	@Autowired
	private ClientesService service;
	
	@GetMapping
	public ResponseEntity<List<Clientes>> findAll(){
		List<Clientes> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Clientes> findbyId(@PathVariable Long id ){
		Clientes obj = service.findByid(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Clientes> insert(@RequestBody Clientes obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "{/id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = " {/id}")
	public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Clientes obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}



