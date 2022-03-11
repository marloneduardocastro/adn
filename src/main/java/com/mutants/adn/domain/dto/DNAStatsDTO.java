package com.mutants.adn.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class DNAStatsDTO {

    @JsonProperty("count_mutant_dna")
    private int totalMutants;
    @JsonProperty("count_human_dna")
    private int totalHumans;
    private float ratio;
}
