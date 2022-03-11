package com.mutants.adn.repository.dna_validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@Document
public class DnaValidationDocument {
    @Id
    private String id;
    private String[] dna;
    @Indexed
    private boolean isMutant;
}
