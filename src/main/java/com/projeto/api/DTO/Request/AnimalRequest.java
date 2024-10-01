package com.projeto.api.DTO.Request;

import lombok.NonNull;


public record AnimalRequest(
       @NonNull String nome,
       @NonNull String raca,
       @NonNull String obs,
       @NonNull String porte,
       @NonNull String pelagem) {
}
