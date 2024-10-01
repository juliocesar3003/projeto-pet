package com.projeto.api.repository;

import com.projeto.api.entidades.Servicos.ValoresPorServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValoresServicoRepository extends JpaRepository<ValoresPorServico,Long> {

    List<ValoresPorServico> findByEmpresaAssociadaId(Long empresaId);
}
