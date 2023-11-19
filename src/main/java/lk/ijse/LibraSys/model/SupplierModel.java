package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierModel {
    public boolean saveSupplier(String supplierId,String supplierName,String contactNumber) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO supplier VALUES (?,?,?)");

        pstm.setString(1,supplierId);
        pstm.setString(2,supplierName);
        pstm.setString(3,contactNumber);

        return pstm.executeUpdate() > 0;
    }
}
