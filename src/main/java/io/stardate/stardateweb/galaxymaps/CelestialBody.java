package io.stardate.stardateweb.galaxymaps;

import java.util.Optional;

record CelestialBody(String id, String name, String description, Optional<Activity> activity) { }
