package com.projeto.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.api.entidades.Clientes;

public interface ClienteRepository extends JpaRepository<Clientes, Long>{
	Clientes findByCelular(Long celular);
}
