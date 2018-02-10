package de.bredex.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bredex.batch.model.Input;

@Repository
public interface InputRepository extends JpaRepository<Input, Long> {

}
