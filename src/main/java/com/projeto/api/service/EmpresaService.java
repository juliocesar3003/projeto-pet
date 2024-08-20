package com.projeto.api.service;

import com.projeto.api.DTO.Requests.EmpresaRequest;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.entidades.sobreUsuario.Usuario;
import com.projeto.api.repository.EmpresaRepository;
import com.projeto.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EmpresaRepository empresaRepository;


    public void criarEmpresa(EmpresaRequest obj, JwtAuthenticationToken token){

        Usuario usuario = usuarioRepository.findById(Long.valueOf(token.getName())).orElseThrow();

        var empresa = new Empresa();

        empresa.setUsuario(usuario);
        empresa.setNome(obj.nome());

        usuario.setEmpresaAssociada(empresa);

        empresaRepository.save(empresa);

    }
}
