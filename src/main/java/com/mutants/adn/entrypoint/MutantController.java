package com.mutants.adn.entrypoint;

import com.mutants.adn.domain.dto.DNASequenceDTO;
import com.mutants.adn.domain.service.MutantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("mutant")
public class MutantController {

    private final MutantService mutantService;

    @PostMapping("/")
    public ResponseEntity<Void> isMutant(@RequestBody DNASequenceDTO dnaSequenceDTO) {
        boolean isMutant = mutantService.isMutant(dnaSequenceDTO.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
