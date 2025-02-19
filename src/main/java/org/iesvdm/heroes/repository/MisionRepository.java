package org.iesvdm.heroes.repository;

import org.iesvdm.heroes.domain.Mision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MisionRepository extends JpaRepository<Mision, Long> {
}
