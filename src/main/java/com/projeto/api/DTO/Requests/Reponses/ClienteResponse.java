package com.projeto.api.DTO.Requests.Reponses;

import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;

import java.util.List;


public record ClienteResponse(String nome,
                              Long celular,
                              String endereco,
                              List<Cachorro> pet,
                              List<Agendamentos> agendamentos) {
}
