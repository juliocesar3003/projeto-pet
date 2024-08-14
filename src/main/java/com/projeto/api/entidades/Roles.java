package com.projeto.api.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String name;

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
