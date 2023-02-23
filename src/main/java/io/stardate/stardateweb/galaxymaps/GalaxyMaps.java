package io.stardate.stardateweb.galaxymaps;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.stardate.stardateweb.WebComponent;
import io.stardate.stardateweb.galaxymaps.models.GalaxyMap;
import io.stardate.stardateweb.galaxymaps.models.GalaxyMapPreview;

import java.util.function.Consumer;

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
        var result = data.get(id);
        result.map(this::respondMap).orElse(ctx -> ctx.status(404)).accept(context);
    }

    public void preview(Context context) {
        var id = context.pathParam("galaxyMapId");
        var result = data.getPreview(id);
        result.map(this::respondPreview).orElse(ctx -> ctx.status(404)).accept(context);
    }

    public void all(Context context) {
        context.json(data.allAsPreview());
    }

    private Consumer<Context> respondMap(GalaxyMap map) {
        return (ctx -> ctx.json(map));
    }

    private Consumer<Context> respondPreview(GalaxyMapPreview preview) {
        return (ctx -> ctx.json(preview));
    }
}
