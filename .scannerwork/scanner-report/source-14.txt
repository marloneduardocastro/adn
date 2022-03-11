package com.mutants.adn.domain.service;

import com.mutants.adn.repository.dna_validation.DnaValidationDocument;
import com.mutants.adn.repository.dna_validation.DnaValidationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MutantServiceTest {

    @Mock
    private DnaValidationRepository dnaValidationRepository;

    @InjectMocks
    private MutantService mutantService;

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void dna_is_valid_and_is_mutant() {
        when(dnaValidationRepository.findByDna(any())).thenReturn(Optional.empty());
        when(dnaValidationRepository.save(any())).thenReturn(DnaValidationDocument.builder().isMutant(true).build());
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

        boolean isMutant = mutantService.isMutant(dna);

        assertTrue(isMutant);
        verify(dnaValidationRepository).save(any());
    }

    @Test
    void dna_is_valid_and_is_not_mutant() {
        when(dnaValidationRepository.findByDna(any())).thenReturn(Optional.empty());
        when(dnaValidationRepository.save(any())).thenReturn(DnaValidationDocument.builder().isMutant(true).build());
        String[] dna = {"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};

        boolean isMutant = mutantService.isMutant(dna);

        assertFalse(isMutant);
        verify(dnaValidationRepository).save(any());
    }

    @Test
    void dna_previously_validated() {
        when(dnaValidationRepository.findByDna(any())).thenReturn(Optional.of(DnaValidationDocument.builder().isMutant(true).build()));
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

        boolean isMutant = mutantService.isMutant(dna);

        assertTrue(isMutant);
        verify(dnaValidationRepository, times(0)).save(any());
    }

    @Test
    void dna_is_not_valid() {
        String[] dna = {"XVBM","CAG","TTAT","AGAG","CCCC","TG"};

        boolean isMutant = mutantService.isMutant(dna);

        assertFalse(isMutant);
        verify(dnaValidationRepository, times(0)).findByDna(any());
    }
}