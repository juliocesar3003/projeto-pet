package com.projeto.api.entidades.porteEnum;

public enum Porte {

	PEQUENO(1L),
	MEDIO(2L),
	GRANDE(3L);

	Long id;

	Porte(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
