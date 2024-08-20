package com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum;

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
