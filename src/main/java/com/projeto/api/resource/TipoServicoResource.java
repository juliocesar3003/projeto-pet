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

import com.projeto.api.entidades.TipoServicos;
import com.projeto.api.service.TipoServicoService;

@CrossOrigin(origins = "http://127.0.0.1:5500" )
@RestController
@RequestMapping(value = "/Tiposervicos")
public class TipoServicoResource {

	
	@Autowired
	private TipoServicoService service;
	
	@GetMapping
	private List<TipoServicos> findAll(){
		List<TipoServicos> lista = service.findAll();
		return lista;
	}
	
	@GetMapping(value = "/{id}")
	public TipoServicos findById(@PathVariable Long id){
		TipoServicos obj = service.findById(id);
		return obj;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TipoServicos insert( @RequestBody TipoServicos obj ){
		return obj = service.insert(obj);
		
	}	
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		service.delete(id);
	}
	
	@PutMapping(value =  "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
		public TipoServicos update(@PathVariable Long id, @RequestBody TipoServicos obj){
		return obj = service.update(id, obj);
		
	}
}
