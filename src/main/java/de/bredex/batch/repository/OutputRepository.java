package de.bredex.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bredex.batch.model.Output;

@Repository
public interface OutputRepository extends JpaRepository<Output, Long> {

}
