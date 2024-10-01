package com.projeto.api.DTO.Request;

import com.projeto.api.entidades.sobreAgendamento.FormaDePagamento;
import com.projeto.api.entidades.sobreAgendamento.StatusAgendamento;

import java.time.LocalDateTime;
import java.util.List;

public record AgendamentoRequest(
        LocalDateTime horarioEntrada,
        LocalDateTime horarioEntradaRealizada,
        LocalDateTime horarioSaida,
        LocalDateTime horarioSaidaRealizada,
        FormaDePagamento formaDePagamento,
        StatusAgendamento status,
        Long cliente,
        Long animal,
        List<Long> servicos

) {
}
