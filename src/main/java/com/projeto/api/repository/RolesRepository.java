package com.projeto.api.repository;


import com.projeto.api.entidades.Roles;
import com.projeto.api.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long>{

    Roles findByNome(String nome);
}
