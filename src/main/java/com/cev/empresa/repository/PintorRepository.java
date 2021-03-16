package com.cev.empresa.repository;

import com.cev.empresa.domain.Pintor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pintor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PintorRepository extends JpaRepository<Pintor, Long> {
}
