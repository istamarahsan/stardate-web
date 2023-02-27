package io.stardate.stardateweb.galaxymaps;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

import java.util.Optional;

import static org.jooq.impl.DSL.*;

public class GetPlanetarySystemHandler implements Handler {

    private final DSLContext db;

    public GetPlanetarySystemHandler(DSLContext db) {
        this.db = db;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        var systemId = context.pathParam("planetarySystemId");

        var getSystemDetails = db
                .select(field("id"),
                        field("display_name"),
                        field("description"))
                .from(table("planetary_system"))
                .where(field("id").eq(systemId))
                .getQuery();

        var getCelestialBodies = db
                .select(field("id"),
                        field("display_name"),
                        field("description"),
                        field("activity"))
                .from(table("celestial_body"))
                .where(field("planetary_system_id").eq(systemId))
                .getQuery();

        getSystemDetails
                .fetch()
                .stream()
                .findFirst()
                .map(systemRecord -> new PlanetarySystem(
                                (String) systemRecord.value1(),
                                (String) systemRecord.value2(),
                                (String) systemRecord.value3(),
                                getCelestialBodies.fetch().map(record -> new CelestialBody(
                                                (String) record.value1(),
                                                (String) record.value2(),
                                                (String) record.value3(),
                                                Optional.ofNullable(record.value4())
                                                        .map(v -> (String) v)
                                                        .orElse("none")
                                        )
                                )
                        )
                )
                .ifPresentOrElse(
                        context::json,
                        () -> context.status(404)
                );
    }
}
