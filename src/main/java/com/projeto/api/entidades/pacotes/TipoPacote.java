package com.projeto.api.entidades.pacotes;


public enum TipoPacote {

    MENSAL(1L),
    QUINZENAL(2L);


    Long id;
    TipoPacote(Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }
}

