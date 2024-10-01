package com.projeto.api.entidades.sobreAgendamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime HorarioEntrada;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime horarioEntradaRealizada;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime horarioSaida;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime horarioSaidaRealizada;

	@Enumerated(EnumType.STRING)
	private FormaDePagamento formaDePagamento;

	@Enumerated(EnumType.STRING)
	private StatusAgendamento status;

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
	private List<ValoresPorServico> servicos = new ArrayList<>();
	
	private Double valorTotal;

	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresaAssociada;
	
    public void addServico(ValoresPorServico servico) {
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
