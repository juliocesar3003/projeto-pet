package com.example.demo.entidades;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_servicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoServicos implements Serializable{
	private static final long serialVersionUID = 1L;

	private UUID id;
	
	private String servico;
	private Integer valor;
}
