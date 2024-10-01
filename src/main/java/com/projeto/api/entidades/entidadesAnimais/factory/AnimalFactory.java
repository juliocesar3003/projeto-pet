package com.projeto.api.entidades.entidadesAnimais.factory;

import com.projeto.api.entidades.Clientes;
import com.projeto.api.entidades.entidadesAnimais.Animais;
import com.projeto.api.entidades.entidadesAnimais.Cachorro;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Pelagem;
import com.projeto.api.entidades.entidadesAnimais.ProprieadesAnimaisEnum.Porte;
import com.projeto.api.entidades.sobreUsuario.Empresa;
import com.projeto.api.infra.exception.exceptions.CustomIllegalArgumentException;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.JobImpressionsSupported;

@Component
public class AnimalFactory {

    public Animais criarPet(String tipo, String nome, String raca, String obs, Clientes dono, Empresa empresa, Porte porte, Pelagem pelagem){
        Animais pet = null;

        if ("Cachorro".equalsIgnoreCase(tipo)){
           pet = new Cachorro(nome,raca,obs,dono,empresa,porte,pelagem);
        }
        else {
            throw  new CustomIllegalArgumentException( tipo + " não é conhecido, escreva um tipo de animal valido ");
        }
        return pet;
    }
}
