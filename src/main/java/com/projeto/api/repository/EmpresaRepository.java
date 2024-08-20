package com.projeto.api.repository;


import com.projeto.api.entidades.sobreUsuario.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

}
