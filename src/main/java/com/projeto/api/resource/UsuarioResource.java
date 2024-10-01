package com.projeto.api.resource;

import com.projeto.api.entidades.sobreUsuario.Roles;
import com.projeto.api.entidades.sobreUsuario.Usuario;
import com.projeto.api.repository.RolesRepository;
import com.projeto.api.repository.UsuarioRepository;
import com.projeto.api.DTO.Request.UsuarioRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
public class UsuarioResource {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/usuarios")
    public ResponseEntity<Void> novoUsuario(@RequestBody UsuarioRequest dto){

        var basicRole = rolesRepository.findByNome(Roles.Values.BASIC.name());

        if (basicRole == null) {
            throw new RuntimeException("Role básico não encontrado");
        }

       var usuarioFromDb = usuarioRepository.findByusuario(dto.nomeUsuario());
       if (usuarioFromDb.isPresent()){
           throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
       }
       var usuario = new Usuario();
            usuario.setUsuario(dto.nomeUsuario());
            usuario.setSenha(passwordEncoder.encode(dto.senha()));
            usuario.setRoles(Set.of(basicRole));

            usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }
}
