package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidades.Clientes;

public interface ClienteRepository extends JpaRepository<Clientes, Long>{

}
