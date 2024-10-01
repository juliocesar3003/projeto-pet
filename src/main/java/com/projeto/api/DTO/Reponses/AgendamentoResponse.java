package com.projeto.api.DTO.Reponses;


import java.time.LocalDateTime;
import java.util.List;

public record AgendamentoResponse
        (
         Long id,
         LocalDateTime horaEntrada,
         LocalDateTime horaEntradaRealizada,
         LocalDateTime horarioSaida,
         LocalDateTime horarioSaidaRealizada,
         String formaDePagamento,
         String status,
         String cliente ,
         ResponseAnimais animalResponse,
         List<ValoresResponse> valoresResponse,
         Double valor
        ) {
}
