package com.projeto.api.entidades.sobreAgendamento;

import com.projeto.api.entidades.sobreUsuario.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "tb_MaquinaCartao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaquinaCartao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double taxaDamaquininha;

    private Double valorPorTaxa;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresaAssociada;
}
