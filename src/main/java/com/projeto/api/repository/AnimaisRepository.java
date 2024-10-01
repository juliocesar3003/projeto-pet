package com.projeto.api.repository;

import java.util.List;

import com.projeto.api.entidades.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.api.entidades.entidadesAnimais.Animais;
public interface AnimaisRepository extends JpaRepository<Animais, Long>{
	
	List<Animais> findByNome(String nome);
	List<Animais> findByEmpresaAssociadaId(Long empresaId);
}
