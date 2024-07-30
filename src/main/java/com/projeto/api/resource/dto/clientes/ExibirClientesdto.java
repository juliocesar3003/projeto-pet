package com.projeto.api.resource.dto.clientes;

import java.util.ArrayList;
import java.util.List;

import com.projeto.api.resource.dto.Animais.ExibirAnimaisDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExibirClientesdto {

	private String nome;
	private Long celular;
	private List<ExibirAnimaisDto> listaAnimais = new ArrayList<>();
	
}
