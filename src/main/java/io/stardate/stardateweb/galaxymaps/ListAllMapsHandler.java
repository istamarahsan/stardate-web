package io.stardate.stardateweb.galaxymaps;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;

import static org.jooq.impl.DSL.*;

public class ListAllMapsHandler implements Handler {
    
    private final DSLContext db;
    
    public ListAllMapsHandler(DSLContext db) {
        this.db = db;
    }
    
    @Override
    public void handle(@NotNull Context context) throws Exception {
        
        var system_count = db
                .select(field("galaxy_id"), count(field("system_id")).as("number_of_systems"))
                .from(table("system_membership"))
                .groupBy(field("galaxy_id"))
                .asTable();
        
        var result = db
                .select(field("galaxy_map.id").as("id"), 
                        field("galaxy_map.display_name").as("display_name"), 
                        field("galaxy_map.description").as("description"), 
                        field("galaxy_map.author_name").as("author_name"), 
                        ifnull(field("system_count.number_of_systems"), 0).as("size"))
                .from(table("galaxy_map")
                        .leftJoin(system_count.as("system_count"))
                        .on(field("galaxy_map.id").eq(field("system_count.galaxy_id"))))
                .fetch()
                .stream()
                .map(this::galaxyMapFromRecord)
                .toList();
        
        context.json(result);
    }
    
    private GalaxyMapPreview galaxyMapFromRecord(Record record) {
        return new GalaxyMapPreview(
                (String) record.getValue(field("id")),
                (String) record.getValue(field("display_name")),
                (String) record.getValue(field("description")),
                (String) record.getValue(field("author_name")),
                ((Long) record.getValue(field("size"))).intValue()
        );
    }
}
