package com.projeto.api.repository;


import com.projeto.api.entidades.sobreUsuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByusuario(String usuario);
}
