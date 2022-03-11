package com.mutants.adn.domain.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DNASequenceDTO implements Serializable {

    private String[] dna;

}
