package com.projeto.api.entidades.sobreAgendamento;

import java.time.Duration;
import java.time.LocalTime;


public interface TaxaDeAtraso {

    //FUNÇÃO QUE IRA CALCULAR A TAXA QUE SERA COBRADO POR ATRASO PARA O ATENDIMENTO
    // tempoEsperado seria o horario esperado da chegada do cliente e tempo realizado seria o horario que ele realmente chegou

    public static Double calcularTaxaDeAtrasoEntrada(LocalTime horarioEsperado, LocalTime horarioRealizado){
        
        if(horarioEsperado == null || horarioRealizado == null) {
            throw new IllegalArgumentException("Os horários não podem ser nulos.");
        }

        if(horarioEsperado.equals(horarioRealizado)){
            return 0.00;
        }

        Duration tolerancia = Duration.ofMinutes(20);
        LocalTime horarioMaximoChegada = horarioEsperado.plus(tolerancia);

        if (horarioRealizado.isBefore(horarioMaximoChegada)) {
            return 0.00;
        }

        Double valorTaxaPorMinuto = 0.50;

        Long minutos = Duration.between(horarioMaximoChegada,horarioRealizado).toMinutes();

        return valorTaxaPorMinuto * minutos;
    }

    public static Double calcularTaxaDeAtrasoSaida(LocalTime horarioEsperado, LocalTime horarioRealizado){

        if(horarioEsperado == null || horarioRealizado == null) {
            throw new IllegalArgumentException("Os horários não podem ser nulos.");
        }

        if(horarioEsperado.equals(horarioRealizado)){
            return 0.00;
        }

        Duration tolerancia = Duration.ofMinutes(20);
        LocalTime horarioMaximoChegada = horarioEsperado.plus(tolerancia);

        if (horarioRealizado.isBefore(horarioMaximoChegada)) {
            return 0.00;
        }

        Double valorTaxaPorMinuto = 0.50;

        Long minutos = Duration.between(horarioMaximoChegada,horarioRealizado).toMinutes();

        return valorTaxaPorMinuto * minutos;
    }
}
