package com.projeto.api.resource;

import com.projeto.api.repository.UsuarioRepository;
import com.projeto.api.resource.dto.LoginRequest;
import com.projeto.api.resource.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class tokenResource {

    private final JwtEncoder jwtEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private  BCryptPasswordEncoder bCryptPassWorldEncoder;


    public tokenResource(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var usuario = repository.findByusuario(loginRequest.username());

        if(usuario.isEmpty() || !usuario.get().isLoginCorreto(loginRequest,bCryptPassWorldEncoder)){
            throw new BadCredentialsException("usuario ou senha invalidos!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("backEnd-pet")
                .subject(usuario.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue,expiresIn));
    }
}
