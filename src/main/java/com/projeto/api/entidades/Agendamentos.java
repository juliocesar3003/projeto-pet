package com.projeto.api.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
				
	@OneToMany(mappedBy = "agendamento")
	private List<TipoServicos> servicos = new ArrayList<>(); 
	
	private Double valorTotal;
	
    public void addServico(TipoServicos servico) {
        this.servicos.add(servico);
    }
	
	public void getTotal(){
		Double soma = 0.0;
		for(TipoServicos x : servicos ) {
			soma += x.getValor();
		}
		 setValorTotal(soma);
	}
	
	
}
