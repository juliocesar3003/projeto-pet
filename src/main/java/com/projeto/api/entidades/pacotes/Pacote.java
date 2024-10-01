package com.projeto.api.entidades.pacotes;

import com.projeto.api.entidades.Servicos.ValoresPorServico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_pacotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pacote {

    private LocalDateTime inicioPacote;
    private LocalDateTime finalPacote;
    private Long id;
    private TipoPacote tipoPacote;
    private Animais animal;
    private List<ValoresPorServico> servico;
    private List<Agendamentos> agendamentos;
    private Double valor;

}
