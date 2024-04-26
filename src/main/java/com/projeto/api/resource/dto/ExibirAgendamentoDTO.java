package com.projeto.api.resource.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExibirAgendamentoDTO {
	
	private long NumeroCelular;
	private String nomeAnimal;
	private String nomeCliente;
	private Double ValorTotal;
	private LocalDateTime date;
	
	private List<ExibirServicosDto> servicos;
}
