package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationModel {

    public String generateNextReservationId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT reservationId FROM reservation ORDER BY  reservationId LIMIT 1");
    }
}
