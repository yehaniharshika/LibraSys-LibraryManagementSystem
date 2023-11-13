package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.SignupDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupModel {
    public  boolean registerLibrarian(SignupDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO librarian VALUES(?,?,?,?,?,?,?)");
        pstm.setString(1, dto.getSNumber());
        pstm.setString(2, dto.getFirstName());
        pstm.setString(3, dto.getLastName());
        pstm.setString(4, dto.getNic());
        pstm.setString(5, dto.getEAddress());
        pstm.setString(6, dto.getUsername());
        pstm.setString(7, dto.getPw());

        boolean isRegistered = pstm.executeUpdate() > 0;

        return isRegistered;
    }
}
