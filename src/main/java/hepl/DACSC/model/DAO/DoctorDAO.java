package hepl.DACSC.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {
    private DBConnexion conn;

    public DoctorDAO(DBConnexion conn) {
        this.conn = conn;
    }

    public Boolean isDoctorPresent(String login) throws SQLException {
        String query = "SELECT * FROM doctors WHERE login = '" + login + "'";

        PreparedStatement ps = conn.getInstance().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public String getPasswordByLogin(String login) throws SQLException {
        String query = "SELECT password FROM doctors WHERE login ='" + login + "'";

        PreparedStatement ps = conn.getInstance().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getString(1) : null;
    }
}
