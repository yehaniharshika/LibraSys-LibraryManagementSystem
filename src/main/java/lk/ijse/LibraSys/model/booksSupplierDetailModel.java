package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.tm.SupplierCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class booksSupplierDetailModel {
     public  boolean saveBooksSupplierDetails(List<SupplierCartTm> supplierCartTmList){
         for (SupplierCartTm tm : supplierCartTmList){
             if (!saveBooksSupplierDetails){
                 return  true;
             }
         }
         return false;
     }


     private  boolean saveBooksSupplierDetails(List<SupplierCartTm> supplierCartTmList) throws SQLException {
         Connection connection = DbConnection.getInstance().getConnection();
         PreparedStatement pstm = connection.prepareStatement("INSERT INTO  bookSupplier_detail VALUES (?,?,?,?)");




     }
}
