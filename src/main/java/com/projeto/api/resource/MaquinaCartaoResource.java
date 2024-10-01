package com.projeto.api.resource;

import com.projeto.api.DTO.Reponses.MaquinaResponse;
import com.projeto.api.entidades.sobreAgendamento.MaquinaCartao;
import com.projeto.api.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500" )
@RestController
@RequestMapping(value = "/maquinaCartao")
public class MaquinaCartaoResource {

	
	@Autowired
	private MaquinaService service;
	
	@GetMapping
	private List<MaquinaResponse> findAll(JwtAuthenticationToken token){
		List<MaquinaResponse> lista = service.findAll(token);
		return lista;
	}
	
	@GetMapping(value ="/{id}")
	public MaquinaResponse findById(@PathVariable Long id, JwtAuthenticationToken token){
		 MaquinaResponse obj = service.findByidResponse(id,token);
		return obj;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void insert(@RequestBody MaquinaCartao obj, JwtAuthenticationToken token ){
		 service.insert(obj,token);
		
	}	
	
	@DeleteMapping(value ="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id,JwtAuthenticationToken token){
		service.delete(id,token);
	}
	
	@PutMapping(value ="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long id, @RequestBody MaquinaCartao obj, JwtAuthenticationToken token){
		 service.update(id, obj,token);

	}
}
