package br.com.bittencourt.swplanetapi.common;

import br.com.bittencourt.swplanetapi.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALIDPLANET = new Planet("", "", "");
}
