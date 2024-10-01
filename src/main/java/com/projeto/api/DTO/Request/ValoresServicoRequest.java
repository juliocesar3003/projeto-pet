package com.projeto.api.DTO.Request;

import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;

public record ValoresServicoRequest(Porte porte,Pelagem pelagem, Long Idservico, Double valor) {
}
