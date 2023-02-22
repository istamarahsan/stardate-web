package io.stardate.stardateweb.galaxymaps;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.stardate.stardateweb.WebComponent;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class GalaxyMaps implements WebComponent {
    
    private final GalaxyMapsData data;
    
    private GalaxyMaps(GalaxyMapsData data) {
        this.data = data;
    }
    
    public static GalaxyMaps create(GalaxyMapsData data) {
        return new GalaxyMaps(data);
    }
    
    @Override
    public void configureRoutes(Javalin app) {
        app.routes(() -> {
            path("galaxyMaps", () -> {
                path("maps", () -> {
                    path("{galaxyMapId}", () -> {
                        get(this::getGalaxyMap);
                    });
                });
                path("all", () -> {
                    get(this::all);
                });
                path("preview", () -> {
                    path("{galaxyMapId}", () -> {
                        get(this::preview);
                    });
                });
            });
        });
    }

    public void getGalaxyMap(Context context) {
        var id = context.pathParam("galaxyMapId");
        context.json(data.get(id));
    }

    public void preview(Context context) {
        var id = context.pathParam("galaxyMapId");
        context.json(data.getPreview(id));
    }

    public void all(Context context) {
        context.json(data.allAsPreview());
    }
}
