package io.stardate.stardateweb.galaxymaps;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

import static org.jooq.impl.DSL.*;

public class GetGalaxyMapHandler implements Handler {
    
    private final DSLContext db;
    
    public GetGalaxyMapHandler(DSLContext db) {
        this.db = db;
    }
    
    @Override
    public void handle(@NotNull Context context) throws Exception {
        var galaxyId = context.pathParam("galaxyMapId");
        
        var idsOfSystemsInGalaxy = db
                .select(field("system_id"))
                .from(table("system_membership"))
                .where(field("galaxy_id").eq(galaxyId))
                .fetch()
                .map(rec -> (String) rec.value1());
        
        var hyperlanes = db
                .select(field("system_a_id"),
                        field("system_b_id"))
                .from(table("hyperlane"))
                .where(field("galaxy_id").eq(galaxyId))
                .fetch()
                .map(rec -> new Hyperlane((String) rec.value1(), (String) rec.value2()));

        var galaxyMap = db
                .select(field("id"),
                        field("display_name"),
                        field("author_name"),
                        field("description"))
                .from(table("galaxy_map"))
                .where(field("id").eq(galaxyId))
                .fetch()
                .map(rec -> new GalaxyMap(
                        (String) rec.value1(),
                        (String) rec.value2(),
                        (String) rec.value3(),
                        (String) rec.value4(),
                        idsOfSystemsInGalaxy,
                        hyperlanes))
                .stream()
                .findFirst();
        
        galaxyMap.ifPresentOrElse(
                context::json,
                () -> context.status(404)
        );
    }
}
