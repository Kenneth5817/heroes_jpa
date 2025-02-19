package org.iesvdm.heroes.repository;

import org.iesvdm.heroes.domain.Heroe;
import org.iesvdm.heroes.domain.Poder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroeHasPoderRepository extends JpaRepository<Heroe, Poder> {
}
