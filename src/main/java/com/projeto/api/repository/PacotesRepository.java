package com.projeto.api.repository;

import com.projeto.api.entidades.pacotes.Pacote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacotesRepository extends JpaRepository<Pacote,Long> {
}
