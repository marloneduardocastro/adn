package com.mutants.adn.domain.service;

import com.mutants.adn.domain.dto.DNAStatsDTO;
import com.mutants.adn.repository.dna_validation.DnaValidationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatsService {

    private final DnaValidationRepository dnaValidationRepository;

    public DNAStatsDTO getStats() {
        log.info("Start StatsService.getStats()");
        return Stream.of(DNAStatsDTO.builder().build())
                .map(dnaStatsDTO -> dnaStatsDTO.toBuilder()
                        .totalHumans((int)dnaValidationRepository.countByIsMutant(Boolean.FALSE))
                        .totalMutants((int)dnaValidationRepository.countByIsMutant(Boolean.TRUE))
                        .build()
                )
                .map(dnaStatsDTO -> dnaStatsDTO.toBuilder()
                        .ratio(calculateRatio(dnaStatsDTO))
                        .build()
                )
                .findAny()
                .orElse(DNAStatsDTO.builder().build());
    }

    private float calculateRatio(DNAStatsDTO dnaStatsDTO) {
        log.info("Calculate Ratio of {}", dnaStatsDTO);
        return dnaStatsDTO.getTotalHumans() == 0
                ? dnaStatsDTO.getTotalMutants()
                : (float) dnaStatsDTO.getTotalMutants() / dnaStatsDTO.getTotalHumans();
    }
}
