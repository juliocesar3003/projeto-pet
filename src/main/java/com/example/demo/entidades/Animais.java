package com.example.demo.entidades;

import java.io.Serializable;
import java.util.UUID;

import com.example.demo.entidades.porteEnum.TipoPorte;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name ="tb_animais")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Animais implements Serializable{
private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	private String tipoAnimal;
	
	private String nome;
	
	private String raca;
	
	private TipoPorte porte;
	
	private String observacao;
	
	
}
