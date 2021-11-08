package xyz.geik.plugins.database;

import xyz.geik.plugins.Main;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionPool {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + Main.HOST_ADRESS + ":" + Main.HOST_PORT + "/" + Main.DATABASE_NAME,
                    Main.USER_NAME, Main.PASSWORD);
            return con;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
