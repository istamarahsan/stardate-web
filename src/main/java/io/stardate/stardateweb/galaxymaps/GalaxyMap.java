package io.stardate.stardateweb.galaxymaps;

import java.util.List;

record GalaxyMap(String id, String name, String authorName, String description, List<String> systemIds, List<Hyperlane> hyperlanes) {}
