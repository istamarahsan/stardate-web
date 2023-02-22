package io.stardate.stardateweb.galaxymaps.models;

import java.util.List;

public record PlanetarySystem(String id, String name, List<CelestialBody> bodies) {}
