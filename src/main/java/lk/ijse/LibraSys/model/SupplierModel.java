package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierModel {

    public String getSupplierCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT COUNT(supplierId) FROM supplier");

        String count = null;
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            count = resultSet.getString(1);
        }
        return count;

    }
    public boolean saveSupplier(String supplierId,String supName,String contactNumber,String email) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO supplier VALUES (?,?,?,?)");

        pstm.setString(1,supplierId);
        pstm.setString(2,supName);
        pstm.setString(3,contactNumber);
        pstm.setString(4,email);

        return pstm.executeUpdate() > 0;
    }

    public SupplierDto searchSupplier(String supplierId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT  * FROM supplier WHERE supplierId=?");
        pstm.setString(1,supplierId);

        ResultSet resultSet = pstm.executeQuery();
        SupplierDto dto = null;
        if (resultSet.next()){
            dto = new SupplierDto(
              resultSet.getString(1),
              resultSet.getString(2),
              resultSet.getString(3)
            );
        }
        return dto;
    }
}
