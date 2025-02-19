package org.iesvdm.heroes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Poder {

    @Id
    private int id_poder;

    @Column(length=45)
    private String nombre;

    @Builder.Default
    @ManyToMany
    Set<Poder> poderes = new HashSet<>();
}
