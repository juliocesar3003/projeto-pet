package com.projeto.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.api.entidades.Animais;
public interface AnimaisRepository extends JpaRepository<Animais, Long>{
	
	List<Animais> findByNome(String nome);
}
