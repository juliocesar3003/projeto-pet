package com.projeto.api.entidades.sobreAgendamento.Stage;

import com.projeto.api.DTO.Request.AgendamentoRequest;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.entidades.sobreAgendamento.Builder.AgendamentoBuilder;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendamentoDirector {

    private Agendamentos agendamento;

    @Getter
    @Setter
    private AgendamentoBuilder builder;

    @Getter
    @Setter
    private AgendamentoStage stage;

    public AgendamentoDirector(@Lazy AgendamentoBuilder builder) {
        this.builder = builder;
    }

    public Agendamentos saveCurrentStateAgendado(AgendamentoRequest dto, Clientes cliente, Animais animal, List<ValoresPorServico> servicos, Empresa empresa,Double valor) {

        stage.setAtributosAgendado(builder, dto, cliente, animal, servicos,empresa,valor);
        Agendamentos agendamento = builder.build();
        return agendamento;
    }
    public Agendamentos saveCurrentStateEmAndamento(AgendamentoRequest dto,List<ValoresPorServico> servico,Double valor) {

        stage.setAtributosEmAndamento(builder, dto,servico,valor);
        Agendamentos agendamento = builder.build();
        return agendamento;
    }
    public Agendamentos saveCurrentStateFinalizado(AgendamentoRequest dto,Double valor) {

        stage.setAtributosFinalizado(builder, dto,valor);
        Agendamentos agendamento = builder.build();
        return agendamento;
    }
    public Agendamentos saveCurrentStateCancelado(AgendamentoRequest dto,Double valor) {

        stage.setAtributosFinalizado(builder,dto,valor);
        Agendamentos agendamento = builder.build();
        return agendamento;
    }

}