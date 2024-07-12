package br.com.bittencourt.swplanetapi.domain;

import static br.com.bittencourt.swplanetapi.common.PlanetConstants.INVALIDPLANET;
import static br.com.bittencourt.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Test
    void createPlanet_WithInvalidData_ThrowsException() {

        when(planetRepository.save(INVALIDPLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALIDPLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void getPlanetById_ByExistingId_ReturnsPlanet() {

        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.getPlanetById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);

    }

    @Test
    void getPlanetById_ByUnexistingId_ReturnsEmpty() {

        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getPlanetById(0L);

        assertThat(sut).isEmpty();
    }
}
