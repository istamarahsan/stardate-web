package io.stardate.stardateweb.galaxymaps;

import java.util.List;

record PlanetarySystem(String id, String name, String description, List<CelestialBody> bodies) {}
