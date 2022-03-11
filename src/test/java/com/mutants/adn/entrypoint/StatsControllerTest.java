package com.mutants.adn.entrypoint;

import com.mutants.adn.domain.dto.DNAStatsDTO;
import com.mutants.adn.domain.service.StatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatsController.class)
class StatsControllerTest {

    @MockBean
    private StatsService statsService;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({"40,100,0.4", "10,10,1", "4,2,2"})
    void getStats(int totalMutants, int totalHumans, float ratio) throws Exception {
        when(statsService.getStats()).thenReturn(DNAStatsDTO.builder().totalHumans(totalHumans).totalMutants(totalMutants).ratio(ratio).build());

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count_mutant_dna").value(totalMutants))
                .andExpect(jsonPath("$.count_human_dna").value(totalHumans))
                .andExpect(jsonPath("$.ratio").value(ratio));;

        verify(statsService).getStats();
    }
}