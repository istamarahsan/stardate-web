import com.google.common.collect.ImmutableList;
import io.stardate.extensions.Parse;
import io.stardate.stardateweb.galaxymaps.GalaxyMaps;
import io.stardate.stardateweb.StardateWeb;
import io.stardate.stardateweb.WebComponent;
import io.stardate.stardateweb.galaxymaps.JooqSql;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConnectionProvider;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
    
    public static void main(String[] args) throws SQLException {
        var port = Optional.ofNullable(System.getenv("PORT"))
                .flatMap(Parse::tryParseInt)
                .orElse(8080);
        
        var mySqlHost = Optional.ofNullable(System.getenv("MYSQLHOST")).get();
        var mySqlPort = Optional.ofNullable(System.getenv("MYSQLPORT")).get();
        var mySqlUser = Optional.ofNullable(System.getenv("MYSQLUSER")).get();
        var mySqlPassword = Optional.ofNullable(System.getenv("MYSQLPASSWORD")).get();
        
        var connectionString = "jdbc:mysql://" + mySqlHost + ":" + mySqlPort + "/";
        var conn = DriverManager.getConnection(connectionString, mySqlUser, mySqlPassword);
        
        var data = new JooqSql(new DefaultConnectionProvider(conn), SQLDialect.MYSQL);
        var galaxyMaps = GalaxyMaps.create(data);
        
        var components = 
                new ImmutableList.Builder<WebComponent>()
                        .add(galaxyMaps)
                        .build();

        StardateWeb.create(components).getApp().start(port);
    }
}
