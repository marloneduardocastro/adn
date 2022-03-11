package com.mutants.adn.domain.service;

import com.mutants.adn.domain.dto.DNAStatsDTO;
import com.mutants.adn.repository.dna_validation.DnaValidationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class StatsServiceTest {

    @Mock
    private DnaValidationRepository dnaValidationRepository;

    @InjectMocks
    private StatsService statsService;

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({"100,40", "50,10", "80,80"})
    void get_Stats(long totalHuman, long totalMutant) {
        when(dnaValidationRepository.countByIsMutant(true)).thenReturn(totalMutant);
        when(dnaValidationRepository.countByIsMutant(false)).thenReturn(totalHuman);

        DNAStatsDTO response = statsService.getStats();

        assertNotNull(response);
        assertEquals(response.getRatio(), (float)totalMutant/totalHuman);
    }

    @Test
    void get_Stats_When_Total_Human_is_0() {
        long totalHuman = 0L;
        long totalMutant = 40L;
        when(dnaValidationRepository.countByIsMutant(true)).thenReturn(totalMutant);
        when(dnaValidationRepository.countByIsMutant(false)).thenReturn(totalHuman);

        DNAStatsDTO response = statsService.getStats();

        assertNotNull(response);
        assertEquals(response.getRatio(), (float)totalMutant);
    }
}