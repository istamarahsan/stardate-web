package io.stardate.stardateweb.galaxymaps.models;

import java.util.Optional;

public record CelestialBody(String id, String name, String description, Optional<Activity> activity) { }
