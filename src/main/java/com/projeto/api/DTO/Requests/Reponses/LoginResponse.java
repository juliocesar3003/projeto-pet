package com.projeto.api.DTO.Requests.Reponses;

public record LoginResponse(String acessToken, Long expiresIn) {
}
