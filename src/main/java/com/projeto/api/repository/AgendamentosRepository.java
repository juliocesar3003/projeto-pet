package com.projeto.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.api.entidades.Agendamentos;
import java.util.List;


public interface AgendamentosRepository extends JpaRepository<Agendamentos, Long>{

	
}
