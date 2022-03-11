package com.mutants.adn.domain.service;

import com.mutants.adn.domain.enums.DNABaseEnum;
import com.mutants.adn.domain.utility.MutantProperties;
import com.mutants.adn.repository.dna_validation.DnaValidationDocument;
import com.mutants.adn.repository.dna_validation.DnaValidationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaValidationRepository dnaValidationRepository;

    public boolean isMutant(String[] dna) {
        log.info("Start MutantService.isMutant(dna={})", Arrays.toString(dna));
        if (!isDNAChainValid(dna)) return false;
        log.info("DNA isValid");
        Optional<DnaValidationDocument> dnaValidationBD = dnaValidationRepository.findByDna(dna);
        if (dnaValidationBD.isPresent()){
            log.info("DNA chain has already been previously validated");
            return dnaValidationBD.get().isMutant();
        }

        long numberSequenceMutantMatch = countMatchMutantSequence(getDnaChains(dna));
        boolean isMutant = numberSequenceMutantMatch >= MutantProperties.DEFAULT_COUNT_SEQUENCES_MATCH_MUTANT;
        log.info("After validate DNA isMutant={}", isMutant);

        dnaValidationRepository.save(DnaValidationDocument.builder().dna(dna).isMutant(isMutant).build());

        return isMutant;
    }

    private boolean isDNAChainValid(String[] dna) {
        log.info("validating DNAChain...");
        var dnaBase = Arrays.stream(DNABaseEnum.values())
                .map(Enum::name)
                .collect(Collectors.joining());
        String regex = String.join("",".*[^", dnaBase, "].*");
        return Arrays.stream(dna).allMatch(dnaChain -> dnaChain.toCharArray().length == dna.length)
                && !String.join("", dna).toUpperCase().matches(regex);
    }

    private long countMatchMutantSequence(List<String[]> dnaChainsList) {
        log.info("Start countMatchMutantSequence");
        return dnaChainsList.stream()
                .flatMap(Arrays::stream)
                .flatMap(dnaSequence -> Arrays.stream(DNABaseEnum.values())
                        .map(Enum::name)
                        .map(dnaSequenceBase -> dnaSequenceBase.repeat(MutantProperties.DEFAULT_SEQUENCE_SIZE_MUTANT))
                        .filter(dnaSequence::contains)
                )
                .count();
    }

    private List<String[]> getDnaChains(String[] dnaHorizontal) {
        log.info("Start getDnaChains");
        String[] dnaVertical = new String[dnaHorizontal.length];
        String[] dnaDiagonal = new String[1];
        AtomicInteger row = new AtomicInteger();
        List<String[]> dnaChainsList = new ArrayList<>();

        Arrays.stream(dnaHorizontal)
                .collect(Collectors.toList())
                .stream()
                .map(String::toCharArray)
                .forEach(dnaChain -> {
                    IntStream.range(0, dnaChain.length)
                            .forEach(column -> {
                                dnaVertical[column] = Objects.requireNonNullElse(dnaVertical[column], "").concat(String.valueOf(dnaChain[column]));
                                if (row.get() == column) dnaDiagonal[0] =  Objects.requireNonNullElse(dnaDiagonal[0], "").concat(String.valueOf(dnaChain[row.get()]));
                            });
                    row.getAndIncrement();
                });
        dnaChainsList.add(dnaVertical);
        dnaChainsList.add(dnaDiagonal);
        dnaChainsList.add(dnaHorizontal);
        return dnaChainsList;
    }
}
