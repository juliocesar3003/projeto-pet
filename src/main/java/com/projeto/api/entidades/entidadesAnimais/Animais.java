package com.projeto.api.entidades.entidadesAnimais;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.projeto.api.entidades.Agendamentos;
import com.projeto.api.entidades.Clientes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_animal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Cachorro.class, name = "cachorro"),
})
public  abstract class Animais implements Serializable{
private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String raca;

	private String observacao;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Clientes cliente;
	
	@JsonIgnore
	@OneToMany(mappedBy = "animal")
	private List<Agendamentos> agendamentosPet = new ArrayList<>();

	public Animais(String nome, String raca, String observacao, Clientes cliente) {
		this.nome = nome;
		this.raca = raca;
		this.observacao = observacao;
		this.cliente = cliente;
	}
}
