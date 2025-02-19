package org.iesvdm.heroes.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = false)
@ToString(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Mision {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id_mision;

    private String descripcion;

    @Column(length = 45)
    private String villano;



    @OneToMany(mappedBy="mision", fetch = FetchType.EAGER )
    @Builder.Default
    private Set<Heroe> heroes =  new HashSet<>();

}
