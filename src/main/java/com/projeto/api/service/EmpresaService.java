package com.projeto.api.service;

import com.projeto.api.DTO.Requests.EmpresaRequest;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.entidades.sobreUsuario.Usuario;
import com.projeto.api.infra.exception.exceptions.CustomACessDeniedException;
import com.projeto.api.infra.exception.exceptions.CustomIllegalArgumentException;
import com.projeto.api.infra.exception.exceptions.ResourceNotFoundException;
import com.projeto.api.repository.EmpresaRepository;
import com.projeto.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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



    public Empresa autenticarToken(JwtAuthenticationToken token) {


        Object empresaIdObject = token.getTokenAttributes().get("empresaId");

        if (empresaIdObject == null) {
            throw new CustomIllegalArgumentException("O atributo 'empresaId' não está presente no token.");
        }

        Long empresaId;
        try {
            empresaId = Long.valueOf(empresaIdObject.toString());
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("O atributo 'empresaId' não é um número válido.");
        }


        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

        return empresa;

    }

    public <T> T verificarAssociacaoComEmpresa(T entidade, Empresa empresa, Function<T, Empresa> getEmpresaAssociada) {
        Empresa empresaAssociada = getEmpresaAssociada.apply(entidade);

        if (empresa.getId().equals(empresaAssociada.getId())) {
            return entidade;
        } else {
            throw new CustomACessDeniedException("Entidade não pertence à empresa do usuário logado");
        }
    }

}
