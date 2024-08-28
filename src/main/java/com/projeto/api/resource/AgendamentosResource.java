package com.projeto.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.service.AgendamentosService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping(value = "/agendamentos")
public class AgendamentosResource {

	@Autowired
	private AgendamentosService service;

	
//	@GetMapping
//	public List<ExibirAgendamentoDTO> findAll(){
//	List <ExibirAgendamentoDTO> lista = service.findAll();
//	return lista;
//	}
//
//	@GetMapping(value = "/{id}")
//	public ExibirAgendamentoDTO findById(@PathVariable Long id){
//		ExibirAgendamentoDTO obj = service.findById(id);
//
//		return obj;
//	}
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public Agendamentos insert(@RequestBody AgendamentosDTO objDto){
//		Agendamentos agendamento = service.insert(objDto);
//		 return agendamento;
//
//	}
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		service.delete(id);
	}

	@PutMapping(value =  "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
		public Agendamentos update(@PathVariable Long id, @RequestBody Agendamentos obj){
		return obj = service.update(id, obj);
		
	}
	
	}
