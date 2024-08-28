package com.projeto.api.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

	 @ManyToOne
	 @JoinColumn(name = "empresa_id")
	 private Empresa empresaAssociada;
	 
	@OneToMany(mappedBy = "cliente")
	private List<Cachorro> pet = new ArrayList<>();
	

	@OneToMany(mappedBy = "cliente")
	private List<Agendamentos> agendamento = new ArrayList<>();


}
