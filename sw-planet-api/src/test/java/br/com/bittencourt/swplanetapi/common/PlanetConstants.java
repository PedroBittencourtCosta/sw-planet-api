package br.com.bittencourt.swplanetapi.common;

import br.com.bittencourt.swplanetapi.domain.Planet;
import org.springframework.data.domain.Example;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALIDPLANET = new Planet("", "", "");
}
