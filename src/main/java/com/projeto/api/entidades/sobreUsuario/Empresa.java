package com.projeto.api.entidades.sobreUsuario;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.Servicos.Servico;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.Servicos.ValoresPorServico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="tb_empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne(mappedBy = "empresaAssociada")
    private Usuario usuario;

    @OneToMany(mappedBy = "empresaAssociada")
    private List<Clientes> Clientes = new ArrayList<>();

    @OneToMany(mappedBy = "empresaAssociada")
    private List<Animais> animais = new ArrayList<>();

    @OneToMany(mappedBy = "empresaAssociada")
    private List<Servico> servicos = new ArrayList<>();

    @OneToMany(mappedBy = "empresaAssociada")
    private List<ValoresPorServico> valorServico = new ArrayList<>();

}
