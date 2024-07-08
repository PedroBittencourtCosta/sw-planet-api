package br.com.bittencourt.swplanetapi.domain;

import static br.com.bittencourt.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)  // utilizando apenas o mockito para teste
//@SpringBootTest(classes = PlanetService.class)  utilizando o spring para teste
public class PlanetServiceTest {

    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Test
    void createPlanet_WithValidData_ReturnsPlanet() {

        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        Planet sut = planetService.create(PLANET);

        assertThat(sut).isEqualTo(PLANET);
    }
}
