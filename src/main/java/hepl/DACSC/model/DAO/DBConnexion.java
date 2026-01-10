package hepl.DACSC.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnexion {
    private static Connection instance;

    public static Connection getInstance() {
        return instance;
    }

    public DBConnexion(String url, String user, String password, String driver) {
        try{
            System.out.println("Chargement du driver : " + driver);
            Class.forName(driver);

            System.out.println("Connexion à : " + url);
            instance = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection()
    {
        try{
            if(instance != null && !instance.isClosed()){
                instance.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
