package com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum;

public enum Pelagem {

    CURTO(1L),
    MEDIO(2L),
    LONGO(3L);

    private Long id;

    Pelagem(Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }
}
