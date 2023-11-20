package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.tm.SupplierCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksSupplierDetailModel {

    public  boolean saveBooksSupplierDetail(String supplierId, List<SupplierCartTm> supplierCartTmList) throws SQLException {
        for (SupplierCartTm tm : supplierCartTmList){
            if (!saveBooksSupplierDetail(supplierId, tm)){
                return false;
            }
        }

        return true;
    }

    private boolean saveBooksSupplierDetail(String supplierId, SupplierCartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO booksSupplier_details VALUES(?,?,?,?) ");

        pstm.setString(1,supplierId);
        pstm.setString(2,tm.getISBN());
        pstm.setString(3,tm.getISBN());
        pstm.setInt(4,tm.getQty());

        return pstm.executeUpdate() > 0;
    }


}
