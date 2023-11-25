package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.ReservationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {

    public static String getBookBorrowCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT COUNT(reservationId) FROM  reservation");

        String count =  null;
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            count = resultSet.getString(1);
        }
        return count;
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

    public  boolean deleteReservation(String reservationId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM reservation WHERE reservationId=?");

        pstm.setString(1,reservationId);

        boolean isDeleted = pstm.executeUpdate() > 0;

        return isDeleted;
    }

    public ReservationDto searchReservation(String reservationId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM reservation WHERE reservationId=?");
        pstm.setString(1,reservationId);

        ResultSet resultSet = pstm.executeQuery();

        ReservationDto dto= null;
        if(resultSet.next()){
            dto = new ReservationDto(
                  resultSet.getString(1),
                  resultSet.getString(2),
                  resultSet.getString(3),
                  resultSet.getString(4),
                  resultSet.getString(5),
                  resultSet.getDouble(6),
                  resultSet.getString(7),
                  resultSet.getString(8)
            );
        }
        return dto;
    }
    public  String generateNextReservationId(String reservationId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT reservationId FROM reservation ORDER BY reservationId DESC LIMIT 1");

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return  splitReservationId(resultSet.getString(1));
        }
       return splitReservationId(null);
    }

    private String splitReservationId(String currentReservationId) {
        if(currentReservationId != null){
            String[] split = currentReservationId.split("[R]");

            int reservationId = Integer.parseInt(split[1]);
            reservationId++;
            return "R" + String.format("%03d", reservationId);
        }else {
            return "R001";
        }
    }

    public List<ReservationDto> getAllReservation() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM  reservation");

        List<ReservationDto> reservationList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            reservationList.add(new ReservationDto(
                 resultSet.getString(1),
                 resultSet.getString(2),
                 resultSet.getString(3),
                 resultSet.getString(4),
                 resultSet.getString(5),
                 resultSet.getDouble(6),
                 resultSet.getString(7),
                 resultSet.getString(8)
            ));
        }
        return reservationList;
    }
}
