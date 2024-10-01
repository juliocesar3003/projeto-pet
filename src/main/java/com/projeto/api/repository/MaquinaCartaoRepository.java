package com.projeto.api.repository;

import com.projeto.api.entidades.Servicos.Servico;
import com.projeto.api.entidades.sobreAgendamento.MaquinaCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MaquinaCartaoRepository extends JpaRepository<MaquinaCartao, Long> {
    List<MaquinaCartao> findByEmpresaAssociadaId(Long empresaId);
}
