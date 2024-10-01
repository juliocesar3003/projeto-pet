package com.projeto.api.resource;

import com.projeto.api.DTO.Request.EmpresaRequest;
import com.projeto.api.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("empresa")
public class EmpresaResource {

    @Autowired
    EmpresaService empresaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarEmpresa(@RequestBody EmpresaRequest obj,
                             JwtAuthenticationToken token){
        empresaService.criarEmpresa(obj, token);
    }
}
