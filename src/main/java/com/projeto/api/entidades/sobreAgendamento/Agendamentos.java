package com.projeto.api.entidades.sobreAgendamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.Servicos.Servico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    
	private LocalDateTime date;

    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Clientes cliente;
	
	@ManyToOne
	@JoinColumn(name = "animais_id")
	private Animais animal;
	
				
	@ManyToMany
	@JoinTable(name = "tb_agendamento_servico",
	joinColumns = @JoinColumn(name = "agendamento_id"),
	inverseJoinColumns = @JoinColumn(name = "servico_id"))
	private List<Servico> servicos = new ArrayList<>();
	
	private Double valorTotal;
	
    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }
	
//	public void getTotal(){
//		Double soma = 0.0;
//		for(Servico x : servicos ) {
//			soma += x.getValor();
//		}
//		 setValorTotal(soma);
//	}
	
	
}
