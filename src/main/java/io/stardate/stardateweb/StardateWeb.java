package io.stardate.stardateweb;

import io.javalin.Javalin;

import java.util.Collection;

public class StardateWeb {
    
    public static Javalin create(Collection<WebComponent> components) {
        var app = Javalin.create();
        components.forEach(c -> c.configureRoutes(app));
        return app;
    }
}
