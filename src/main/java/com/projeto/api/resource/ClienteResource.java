package com.projeto.api.resource;

import java.util.List;

import com.projeto.api.DTO.Reponses.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.service.ClientesService;

@CrossOrigin(origins = "http://127.0.0.1:5500" )
@RestController
@RequestMapping(value = "/users")
public class ClienteResource {

	@Autowired
	private ClientesService service;

	@GetMapping
	public ResponseEntity<List<ClienteResponse>> findAll(JwtAuthenticationToken token){
		List<ClienteResponse> list = service.findAll(token);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteResponse> findbyId(@PathVariable Long id, JwtAuthenticationToken token){
		ClienteResponse obj = service.findByidResponse(id,token);
		return ResponseEntity.ok().body(obj);
	}
 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void insert(@RequestBody Clientes obj,
										   JwtAuthenticationToken token){
		 service.insert(obj,token);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id,JwtAuthenticationToken token){
		service.delete(id,token);
		return ResponseEntity.noContent().build();
	}

	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteResponse> update(@PathVariable Long id, @RequestBody Clientes obj,JwtAuthenticationToken token){
		 ClienteResponse response = service.update(id,obj, token);
		return ResponseEntity.ok().body(response);
	}
}



