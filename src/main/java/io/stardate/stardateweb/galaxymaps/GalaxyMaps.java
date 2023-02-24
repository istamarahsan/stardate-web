package io.stardate.stardateweb.galaxymaps;

import io.javalin.Javalin;
import io.stardate.stardateweb.WebComponent;
import org.jooq.Configuration;
import org.jooq.impl.DSL;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class GalaxyMaps implements WebComponent {

    private final ListAllMapsHandler listAllMapsHandler;
    private final GetGalaxyMapHandler getGalaxyMapHandler;
    
    private GalaxyMaps(ListAllMapsHandler listAllMapsHandler, GetGalaxyMapHandler getGalaxyMapHandler) {
        this.listAllMapsHandler = listAllMapsHandler;
        this.getGalaxyMapHandler = getGalaxyMapHandler;
    }

    public static GalaxyMaps create(Configuration dbConfig) {
        var db = DSL.using(dbConfig);
        return new GalaxyMaps(new ListAllMapsHandler(db), new GetGalaxyMapHandler(db));
    }

    @Override
    public void configureRoutes(Javalin app) {
        app.routes(() -> {
            path("galaxyMaps", () -> {
                path("maps", () -> {
                    path("{galaxyMapId}", () -> {
                        get(getGalaxyMapHandler);
                    });
                });
                path("all", () -> {
                    get(listAllMapsHandler);
                });
                path("preview", () -> {
                    path("{galaxyMapId}", () -> {
//                        get(this::preview);
                    });
                });
            });
        });
    }
}
