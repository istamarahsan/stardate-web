package io.stardate.stardateweb.galaxymaps;

import io.stardate.stardateweb.galaxymaps.models.GalaxyMap;
import io.stardate.stardateweb.galaxymaps.models.GalaxyMapPreview;

import java.util.Collection;
import java.util.Optional;

public interface GalaxyMapsData {
    Optional<GalaxyMap> get(String id);
    Optional<GalaxyMapPreview> getPreview(String id);
    Collection<GalaxyMapPreview> allAsPreview();
}
