package com.mutants.adn.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutants.adn.domain.dto.DNASequenceDTO;
import com.mutants.adn.domain.service.MutantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @MockBean
    private MutantService mutantService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void is_Mutant() throws Exception {
        when(mutantService.isMutant(any())).thenReturn(true);
        String[] str = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        DNASequenceDTO dnaSequence = DNASequenceDTO.builder().dna(str).build();
        String body = new ObjectMapper().writeValueAsString(dnaSequence);

        mockMvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());

        verify(mutantService).isMutant(any());
    }

    @Test
    void is_Not_Mutant() throws Exception {
        when(mutantService.isMutant(any())).thenReturn(false);
        String[] str = {"ATGA","CGTGC","TTAGT","AGAAG","CCTA","TCATG"};
        DNASequenceDTO dnaSequence = DNASequenceDTO.builder().dna(str).build();
        String body = new ObjectMapper().writeValueAsString(dnaSequence);

        mockMvc.perform(post("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());

        verify(mutantService).isMutant(any());
    }
}