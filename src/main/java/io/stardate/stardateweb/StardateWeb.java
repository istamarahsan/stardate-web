package io.stardate.stardateweb;

import io.javalin.Javalin;
import lombok.Getter;

import java.util.Collection;

public class StardateWeb {
    
    @Getter
    private final Javalin app;

    private StardateWeb(Javalin app) {
        this.app = app;
    }
    
    public static StardateWeb create(Collection<WebComponent> components) {
        var app = Javalin.create();
        components.forEach(c -> c.configureRoutes(app));
        return new StardateWeb(app);
    }
}
