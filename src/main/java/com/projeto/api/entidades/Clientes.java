package com.projeto.api.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="tb_clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clientes implements Serializable {
private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	 private String nome;
	
	 private Long celular;
	
	 private String endereco;
	 
	@OneToMany(mappedBy = "cliente")
	private List<Animais> pet = new ArrayList<>();
	

	@OneToMany(mappedBy = "cliente")
	private List<Agendamentos> agendamento = new ArrayList<>();


}
