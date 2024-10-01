package com.projeto.api.resource;

import com.projeto.api.DTO.Reponses.AgendamentoResponse;
import com.projeto.api.DTO.Request.AgendamentoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.service.AgendamentosService;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping(value = "/agendamentos")
public class AgendamentosResource {

	@Autowired
	private AgendamentosService service;

	
	@GetMapping
	public List<AgendamentoResponse> findAll(JwtAuthenticationToken token){
	 return service.findAllResponse(token);

	}

	@GetMapping(value = "/{id}")
	public AgendamentoResponse findById(@PathVariable Long id,JwtAuthenticationToken token){
		 return service.findByIdResponse(id, token);


	}
	@PostMapping("/iniciar")
	@ResponseStatus(HttpStatus.CREATED)
	public void insertAgendamento(@RequestBody AgendamentoRequest request, JwtAuthenticationToken token){
		service.iniciarAgendamento(request,token);

	}
	@PostMapping("/transitar/{id}/{nextStage}")
	@ResponseStatus(HttpStatus.CREATED)
	public void TransitarAgendamento(@PathVariable Long id,
					   @PathVariable String nextStage,
					   @RequestBody AgendamentoRequest request,
					   JwtAuthenticationToken token){
		 service.transitarParaEstagio(id,nextStage,request,token);

	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id,JwtAuthenticationToken token){
		service.delete(id,token);
	}


	
	}
