package com.projeto.api.entidades.sobreAgendamento;

public enum StatusAgendamento {

    AGENDADO(1L),
    EM_ANDAMENTO(2L),
    FINALIZADO(3L),
    CANCELADO(4L);

    Long id;
    StatusAgendamento(Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }
}
