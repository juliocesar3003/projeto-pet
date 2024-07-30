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
public class AgendamentosDTO {

	private Long idAnimal;
	private LocalDateTime data;
	
	private List<InforServicosDto> servicos;
	

}
