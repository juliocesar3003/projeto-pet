package com.projeto.api.entidades.sobreUsuario;

import com.projeto.api.DTO.Request.LoginRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.Set;
@Entity
@Table(name = "tb_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresaAssociada;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Roles> roles;

    public boolean isLoginCorreto(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
      return  passwordEncoder.matches(loginRequest.password(), this.senha);
    }
}
