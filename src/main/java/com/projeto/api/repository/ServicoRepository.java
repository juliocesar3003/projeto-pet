package com.projeto.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.api.entidades.Servicos.Servico;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long>{

    List<Servico> findByEmpresaAssociadaId(Long empresaId);
}
