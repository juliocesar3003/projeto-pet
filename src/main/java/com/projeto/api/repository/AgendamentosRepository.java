package com.projeto.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;

import java.util.List;


public interface AgendamentosRepository extends JpaRepository<Agendamentos, Long>{

    List<Agendamentos> findByEmpresaAssociadaId(Long empresaId);
    Agendamentos findByEmpresaAssociadaIdAndId(Long empresaId,Long id);
}
