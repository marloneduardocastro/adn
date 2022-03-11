package com.mutants.adn.repository.dna_validation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaValidationRepository extends CrudRepository<DnaValidationDocument, String> {

    Optional<DnaValidationDocument> findByDna(String[] dna);

    long countByIsMutant(boolean isMutant);

}
