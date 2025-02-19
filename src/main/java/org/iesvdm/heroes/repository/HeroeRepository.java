package org.iesvdm.heroes.repository;

import org.iesvdm.heroes.domain.Heroe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroeRepository extends JpaRepository<Heroe, Long> {
}
