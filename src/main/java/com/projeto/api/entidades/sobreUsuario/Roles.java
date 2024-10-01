package com.projeto.api.entidades.sobreUsuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "tb_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String nome;

    public enum Values {

        ADMIN(1L),
        BASIC(2L);


        Long id;

        Values(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }
}
