package com.projeto.api.entidades.sobreAgendamento.Stage;

import com.projeto.api.DTO.Request.AgendamentoRequest;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.sobreAgendamento.Builder.AgendamentoBuilder;
import com.projeto.api.entidades.sobreAgendamento.StatusAgendamento;
import com.projeto.api.entidades.sobreUsuario.Empresa;

import java.util.List;

public class StageCancelado implements AgendamentoStage {

    @Override
    public void setAtributosAgendado(AgendamentoBuilder builder, AgendamentoRequest request, Clientes cliente, Animais animal, List<ValoresPorServico> servico, Empresa empresa,Double valor) {

    }

    @Override
    public void setAtributosEmAndamento(AgendamentoBuilder builder, AgendamentoRequest request, List<ValoresPorServico> servico,Double valor) {

    }

    @Override
    public void setAtributosFinalizado(AgendamentoBuilder builder, AgendamentoRequest request,Double valor) {
        builder.BuildStatus(StatusAgendamento.CANCELADO)
                .BuildValorTotal(valor)
                .build();
    }
}
