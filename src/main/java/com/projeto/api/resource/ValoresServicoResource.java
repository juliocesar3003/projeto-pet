package com.projeto.api.resource;

import com.projeto.api.DTO.Requests.Reponses.ServicoResponse;
import com.projeto.api.DTO.Requests.Reponses.ValoresResponse;
import com.projeto.api.DTO.Requests.ValoresServicoRequest;
import com.projeto.api.entidades.Servicos.Servico;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.service.ValoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("valores")
public class ValoresServicoResource {

    @Autowired
    private ValoresService service;

    @GetMapping
    private List<ValoresResponse> findAll(JwtAuthenticationToken token){
        List<ValoresResponse> lista = service.findAll(token);
        return lista;
    }

    @GetMapping(value ="/{id}")
    public ValoresResponse findById(@PathVariable Long id, JwtAuthenticationToken token){
        ValoresResponse obj = service.findById(id,token);
        return obj;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody ValoresServicoRequest obj, JwtAuthenticationToken token){
        service.insert(obj,token);
    }

    @DeleteMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,JwtAuthenticationToken token){
        service.delete(id,token);
    }

    @PutMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody ValoresPorServico obj, JwtAuthenticationToken token){
        service.update(id, obj,token);

    }
}
