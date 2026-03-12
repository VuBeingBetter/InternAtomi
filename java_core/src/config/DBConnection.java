package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();

        // Read database properties from the properties file
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("database.properties")){
            props.load(input);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.username"),
            props.getProperty("db.password")
        );
    }
}
