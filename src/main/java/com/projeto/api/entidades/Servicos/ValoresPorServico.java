package com.projeto.api.entidades.Servicos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;
import com.projeto.api.entidades.sobreAgendamento.Agendamentos;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "tb_valores_Padrão_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValoresPorServico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Porte porte;

    @Enumerated(EnumType.STRING)
    private Pelagem pelagem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "servico_id",referencedColumnName = "id")
    private Servico servico;

    private Double valor;

    @JsonIgnore
    @ManyToMany(mappedBy = "servicos")
    private List<Agendamentos> agendamento;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresaAssociada;


}
