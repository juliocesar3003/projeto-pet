package com.projeto.api.resource;

import java.util.List;

import com.projeto.api.DTO.Reponses.ServicoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.projeto.api.entidades.Servicos.Servico;
import com.projeto.api.service.ServicoService;

@CrossOrigin(origins = "http://127.0.0.1:5500" )
@RestController
@RequestMapping(value = "/servicos")
public class ServicoResource {

	
	@Autowired
	private ServicoService service;
	
	@GetMapping
	private List<ServicoResponse> findAll(JwtAuthenticationToken token){
		List<ServicoResponse> lista = service.findAll(token);
		return lista;
	}
	
	@GetMapping(value ="/{id}")
	public ServicoResponse findById(@PathVariable Long id, JwtAuthenticationToken token){
		 ServicoResponse obj = service.findById(id,token);
		return obj;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void insert(@RequestBody Servico obj,JwtAuthenticationToken token ){
		 service.insert(obj,token);
		
	}	
	
	@DeleteMapping(value ="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id,JwtAuthenticationToken token){
		service.delete(id,token);
	}
	
	@PutMapping(value ="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
		public void update(@PathVariable Long id, @RequestBody Servico obj, JwtAuthenticationToken token){
		 service.update(id, obj,token);

	}
}
