package com.projeto.api.entidades.Servicos;

import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


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

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresaAssociada;
}
