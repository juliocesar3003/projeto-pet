package com.projeto.api.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_agendamentos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agendamentos implements Serializable{
private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private LocalDateTime data;

	private Clientes cliente;
	
	private Animais animal;
	
	private Integer valorTotal;
	
	private List<TipoServicos> servicos = new ArrayList<>(); 
	
}
