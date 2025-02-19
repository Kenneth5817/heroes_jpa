package org.iesvdm.heroes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Heroe {

    @Id
    private int id_heroe;

    private String nombre;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaNac;
    private int mision_idmision;

    @Builder.Default
    @ManyToMany
    Set <Poder> poderes = new HashSet<>();

    @ManyToOne
    @ToString.Exclude
    private Mision mision;


}
