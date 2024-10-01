package com.projeto.api.entidades.sobreAgendamento;

public enum FormaDePagamento {

    CARTAO(1L),
    DINHEIRO(2L),
    PIX(3L);


    Long id;
    FormaDePagamento(Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }
}
