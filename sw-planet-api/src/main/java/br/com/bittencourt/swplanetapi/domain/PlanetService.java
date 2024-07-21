package br.com.bittencourt.swplanetapi.domain;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Optional<Planet> getPlanetByName(String name){

        return planetRepository.findByName(name);
    }

    public List<Planet> listPlanets(String terrain, String climate){

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate, terrain));
        return planetRepository.findAll(query);

    }

    public void deletePlanets(Long id){

        planetRepository.deleteById(id);

    }
}
