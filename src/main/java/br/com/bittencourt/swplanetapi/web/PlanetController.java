package br.com.bittencourt.swplanetapi.web;

import br.com.bittencourt.swplanetapi.domain.Planet;
import br.com.bittencourt.swplanetapi.domain.PlanetService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody @Valid Planet planet){

        Planet planetCreated = planetService.create(planet);

        return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getPlanetById(@PathVariable("id") Long id) {

        return planetService.getPlanetById(id).map(planet -> ResponseEntity.ok(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> getPlanetByname(@PathVariable("name") String name){

        return planetService.getPlanetByName(name).map(planet -> ResponseEntity.ok(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Planet>> listPlanet(@RequestParam(required = false) String terrain,
        @RequestParam(required = false) String clima){

        List<Planet> planets = planetService.listPlanets(terrain, clima);

        return ResponseEntity.ok(planets);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable("id") Long id) {

        planetService.deletePlanets(id);
        return ResponseEntity.noContent().build();
    }
}
