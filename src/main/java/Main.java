import com.google.common.collect.ImmutableList;
import com.mysql.cj.jdbc.Driver;
import io.stardate.extensions.Parse;
import io.stardate.stardateweb.StardateWeb;
import io.stardate.stardateweb.WebComponent;
import io.stardate.stardateweb.galaxymaps.GalaxyMaps;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;

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
        var galaxyMapDB = new BasicDataSource();
        galaxyMapDB.setDriverClassName(Driver.class.getName());
        galaxyMapDB.setUrl(connectionString + "GalaxyMaps");
        galaxyMapDB.setUsername(mySqlUser);
        galaxyMapDB.setPassword(mySqlPassword);
        
        var config = new DefaultConfiguration();
        config.set(new DataSourceConnectionProvider(galaxyMapDB));
        config.set(SQLDialect.MYSQL);
        var galaxyMaps = GalaxyMaps.create(config);
        
        var components = 
                new ImmutableList.Builder<WebComponent>()
                        .add(galaxyMaps)
                        .build();

        StardateWeb.create(components).start(port);
    }
}
