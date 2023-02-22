package io.stardate.stardateweb;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class StartupTests {
    
    private final Javalin app = StardateWeb.create(new ArrayList<>()).getApp();
    
    @Test
    public void appStarts() {
        JavalinTest.test(
                app,
                (server, client) -> {}
        );
    }
    
    @Test
    public void rootEndpointDoesNotThrow() {
        JavalinTest.test(
                app,
                (server, client) -> Assertions.assertDoesNotThrow(() -> client.get("/"))
        );
    }
}
