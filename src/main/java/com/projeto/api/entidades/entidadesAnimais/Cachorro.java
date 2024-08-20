package com.projeto.api.entidades.entidadesAnimais;
import com.projeto.api.entidades.Agendamentos;
import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_animal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cachorro extends Animais{

    @Enumerated(EnumType.STRING)
    private Porte porte;

    @Enumerated(EnumType.STRING)
    private Pelagem pelagem;

    public Cachorro(String nome, String raca, String observacao, Clientes cliente, Porte porte, Pelagem pelagem) {
        super(nome, raca, observacao, cliente);
        this.porte = porte;
        this.pelagem = pelagem;
    }
}
