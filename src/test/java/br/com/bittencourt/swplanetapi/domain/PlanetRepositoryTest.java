package br.com.bittencourt.swplanetapi.domain;

import static br.com.bittencourt.swplanetapi.common.PlanetConstants.INVALIDPLANET;
import static br.com.bittencourt.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void createPlanet_WithValidData_ReturnsPlanet(){

        Planet planet = planetRepository.save(PLANET);

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
        assertThat(sut).isInstanceOf(Planet.class);

    }

    @Test
    void createPlanet_WithInvalidData_ThrowsException(){

        Planet emptyPlanet = new Planet();

        assertThatThrownBy(() -> planetRepository.save(INVALIDPLANET)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);

    }

    @Test
    void createPlanet_WithExistingName_ThrowsException(){

        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);

        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);

    }
}
