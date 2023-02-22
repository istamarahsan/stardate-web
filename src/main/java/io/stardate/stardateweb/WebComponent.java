package io.stardate.stardateweb;

import io.javalin.Javalin;

public interface WebComponent {
    void configureRoutes(Javalin app);
}
