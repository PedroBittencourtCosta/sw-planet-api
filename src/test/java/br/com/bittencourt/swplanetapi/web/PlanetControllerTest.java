package br.com.bittencourt.swplanetapi.web;

import static br.com.bittencourt.swplanetapi.common.PlanetConstants.INVALIDPLANET;
import static br.com.bittencourt.swplanetapi.common.PlanetConstants.PLANET;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.bittencourt.swplanetapi.domain.Planet;
import br.com.bittencourt.swplanetapi.domain.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetService planetService;

    @Test
    void createPlanet_WithValidData_ReturnsCreated() throws Exception {

        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc.perform(
                post("/planets")
                    .content(objectMapper.writeValueAsString(PLANET))
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {

        Planet emptyPlanet = new Planet();

        mockMvc.perform(
                post("/planets")
                    .content(objectMapper.writeValueAsString(emptyPlanet))
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());

        mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(INVALIDPLANET))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getPlanet_ByExistingId_ReturnsPlanet() throws Exception {

        when(planetService.getPlanetById(anyLong())).thenReturn(Optional.of(PLANET));

        mockMvc.perform(
                get("/planets/2")
        ).andExpect(status().isOk()).andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    void getPlanet_ByUnexistingId_ReturnsNotFound() throws Exception {

        when(planetService.getPlanetById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
                get("/planets/2")
        ).andExpect(status().isNotFound());
    }
}
