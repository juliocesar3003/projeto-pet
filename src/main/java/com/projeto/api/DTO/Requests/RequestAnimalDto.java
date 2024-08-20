package com.projeto.api.DTO.Requests;

import lombok.NonNull;


public record RequestAnimalDto(
       @NonNull String nome,
       @NonNull String raca,
       @NonNull String obs,
       @NonNull String porte,
       @NonNull String pelagem) {
}
