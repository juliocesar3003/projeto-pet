package com.projeto.api.entidades.Servicos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_servicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@OneToOne(mappedBy = "servico")
	private ValoresPorServico valor;

	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresaAssociada;

}
