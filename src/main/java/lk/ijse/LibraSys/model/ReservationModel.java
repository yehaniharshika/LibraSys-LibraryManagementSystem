package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.ReservationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationModel {

    public String generateNextReservationId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT reservationId FROM reservation ORDER BY  reservationId LIMIT 1");
        return null;
    }

    public  boolean addReservation(ReservationDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO reservation VALUES (?,?,?,?,?,?,?,?)");
        pstm.setString(1, dto.getReservationId());
        pstm.setString(2,dto.getBorrowedDate());
        pstm.setString(3,dto.getDueDate());
        pstm.setString(4,dto.getBookReturnDate());
        pstm.setString(5,dto.getFineStatus());
        pstm.setString(6, String.valueOf(dto.getFineAmount()));
        pstm.setString(7,dto.getMid());
        pstm.setString(8,dto.getISBN());

        boolean isAdd = pstm.executeUpdate() > 0;
        return isAdd;
    }

    public boolean updateReservation(ReservationDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE  reservation SET borrowedDate=?,dueDate=?,bookReturnDate=?,fineStatus=?,fineAmount=?,mid=?,ISBN=? WHERE reservationId=?");
        pstm.setString(1, dto.getBorrowedDate());
        pstm.setString(2,dto.getDueDate());
        pstm.setString(3,dto.getBookReturnDate());
        pstm.setString(4,dto.getFineStatus());
        pstm.setString(5, String.valueOf(dto.getFineAmount()));
        pstm.setString(6,dto.getMid());
        pstm.setString(7,dto.getISBN());
        pstm.setString(8,dto.getReservationId());

        boolean isUpdated = pstm.executeUpdate() > 0;

        return isUpdated;
    }
}
