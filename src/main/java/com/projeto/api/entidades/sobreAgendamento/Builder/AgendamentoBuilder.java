package com.projeto.api.entidades.sobreAgendamento.Builder;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.entidades.sobreAgendamento.FormaDePagamento;
import com.projeto.api.entidades.sobreAgendamento.StatusAgendamento;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AgendamentoBuilder {

    protected Agendamentos agendamento;

    public AgendamentoBuilder(){
        agendamento = new Agendamentos();
    }

    public AgendamentoBuilder fromExisting(Agendamentos existingAgendamento) {
        this.agendamento = existingAgendamento;
        return this;
    }

    public AgendamentoBuilder BuildHoraChegadaEsperada(LocalDateTime horaChegadaEsperada){
        agendamento.setHorarioEntrada(horaChegadaEsperada);
        return this;
    }
    public AgendamentoBuilder BuildHoraChegada(LocalDateTime horaChegada){
        agendamento.setHorarioEntradaRealizada(horaChegada);
        return this;
    }
    public AgendamentoBuilder BuildHoraSaidaEsperada(LocalDateTime horarioSaidaEsperada){
        agendamento.setHorarioSaida(horarioSaidaEsperada);
        return this;
    }
    public AgendamentoBuilder BuildHoraSaida(LocalDateTime horarioSaida){
        agendamento.setHorarioSaidaRealizada(horarioSaida);
        return this;
    }
    public AgendamentoBuilder BuildCliente(Clientes cliente){
        agendamento.setCliente(cliente);
        return this;
    }
    public AgendamentoBuilder BuildAnimais(Animais animal){
        agendamento.setAnimal(animal);
        return this;
    }
    public AgendamentoBuilder BuildFormaDePagamento(FormaDePagamento formaDePagamento){
        agendamento.setFormaDePagamento(formaDePagamento);
        return this;
    }
    public AgendamentoBuilder BuildStatus(StatusAgendamento status){
        agendamento.setStatus(status);
        return this;
    }
    public AgendamentoBuilder BuildListaServicos(List<ValoresPorServico> servico){
       for(ValoresPorServico i  : servico){
           agendamento.addServico(i);
       }
       return this;
    }
    public AgendamentoBuilder BuildEmpresa(Empresa empresa){
        agendamento.setEmpresaAssociada(empresa);
        return this;
    }
    public AgendamentoBuilder BuildValorTotal(Double valorTotal){
        agendamento.setValorTotal(valorTotal);
        return this;
    }

    public Agendamentos build(){
        return agendamento;
    }
}
