package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class LoginModel {
    public  boolean checkCredentials(String sNumber,String  username,String pw) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM librarian WHERE sNumber =?  and username=?  and pw=?");
        pstm.setString(1,sNumber);
        pstm.setString(2,username);
        pstm.setString(3,pw);
        ResultSet resultSet =pstm.executeQuery();

        String ServiceNumber = null;
        String UserName = null;
        String Password = null;

        while (resultSet.next()){
            ServiceNumber = resultSet.getString(1);
            UserName = resultSet.getString(6);
            Password = resultSet.getString(7);
        }
        if (sNumber.equals(ServiceNumber) && username.equals(UserName) && pw.equals(Password)){
            return  true;
        }

        return false;
    }
}
