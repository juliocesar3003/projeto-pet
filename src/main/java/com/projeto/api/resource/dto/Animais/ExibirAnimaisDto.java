package com.projeto.api.resource.dto.Animais;

import java.time.LocalDateTime;
import java.util.List;

import com.projeto.api.resource.dto.AgendamentosDTO;
import com.projeto.api.resource.dto.InforServicosDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExibirAnimaisDto {

	private String nome;
	private String observacoes;
	private String raca;
	private String porte;
}
