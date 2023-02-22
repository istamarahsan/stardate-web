package io.stardate.stardateweb.galaxymaps.models;

import java.util.List;

public record GalaxyMap(String id, String name, String author, String description, List<PlanetarySystem> systems, List<Hyperlane> hyperlanes) {}
