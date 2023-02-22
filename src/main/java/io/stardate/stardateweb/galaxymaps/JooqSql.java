package io.stardate.stardateweb.galaxymaps;

import io.stardate.stardateweb.galaxymaps.models.GalaxyMap;
import io.stardate.stardateweb.galaxymaps.models.GalaxyMapPreview;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.util.Collection;
import java.util.Optional;

public class JooqSql implements GalaxyMapsData {
    
    private final DSLContext db;
    
    public JooqSql(ConnectionProvider connectionProvider, SQLDialect dialect) {
        db = DSL.using(connectionProvider, dialect);
    }
    
    @Override
    public Optional<GalaxyMap> get(String id) {
        
        return Optional.empty();
    }

    @Override
    public Optional<GalaxyMapPreview> getPreview(String id) {
        return Optional.empty();
    }

    @Override
    public Collection<GalaxyMapPreview> allAsPreview() {
        return null;
    }
}
