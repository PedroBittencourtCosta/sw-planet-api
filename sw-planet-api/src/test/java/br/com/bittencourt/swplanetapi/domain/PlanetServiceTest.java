package br.com.bittencourt.swplanetapi.domain;

import static br.com.bittencourt.swplanetapi.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Test
    void getPlanetByName_ByExistingName_ReturnsPlanet() {

        when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.getPlanetByName(PLANET.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    void getPlanetByName_ByUnexistingName_ReturnsEmpty(){

        when(planetRepository.findByName(anyString())).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getPlanetByName("unexisting name");

        assertThat(sut).isEmpty();
    }

    @Test
    void listPlanets_ReturnsAllPlanets(){

        List<Planet> planets = new ArrayList<>(){
            {
                add(PLANET);
            }
        };

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));

        when(planetRepository.findAll(query)).thenReturn(planets);

        List<Planet> sut = planetService.listPlanets(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);

    }

    @Test
    void listPlanets_ReturnsEmpty(){

        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.listPlanets(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isEmpty();
    }

    @Test
    void removePlanetById_WithExistingId_DoesNotThrowAnyException(){

        assertThatCode(() -> planetService.deletePlanets(1L)).doesNotThrowAnyException();
    }

    @Test
    void removePlanetById_WithUnexistingId_ThrowsException(){

        doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);

        assertThatThrownBy(() -> planetService.deletePlanets(99L)).isInstanceOf(RuntimeException.class);
    }
}
