package com.projeto.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;


public interface AgendamentosRepository extends JpaRepository<Agendamentos, Long>{

	
}
