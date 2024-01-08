package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidades.Animais;
public interface AnimaisRepository extends JpaRepository<Animais, Long>{

}
