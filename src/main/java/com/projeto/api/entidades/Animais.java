package com.projeto.api.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.api.entidades.porteEnum.TipoPorte;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private Long id;
	
	private String tipoAnimal;
	
	private String nome;
	
	private String raca;
	
	private TipoPorte porte;
	
	private String observacao;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Clientes cliente;
	
	@JsonIgnore
	@OneToMany(mappedBy = "animal")
	private List<Agendamentos> agendamentosPet = new ArrayList<>();

	

	
}
