package br.com.bittencourt.swplanetapi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {

        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet){

        return planetRepository.save(planet);
    }

    public Optional<Planet> getPlanetById(long id){
        return planetRepository.findById(id);
    }

}
