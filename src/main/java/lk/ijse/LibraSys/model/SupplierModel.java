package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public String generateNextSupplierId(String supplierId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT supplierId FROM supplier ORDER BY supplierId DESC LIMIT 1");

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return splitSupplierId(resultSet.getString(1));

        }
        return  splitSupplierId(null);
    }


        private String splitSupplierId(String currentId) {
            if(currentId != null) {
                String[] strings = currentId.split("SP0");
                int id = Integer.parseInt(strings[1]);
                id++;
                String ID = String.valueOf(id);
                int length = ID.length();
                if (length < 2){
                    return "SP00"+id;
                }else {
                    if (length < 3){
                        return "SP0"+id;
                    }else {
                        return "SP"+id;
                    }
                }
            }
            return "SP001";
        }



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



    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE supplier SET supplierName=?,contactNumber=?,email=? WHERE supplierId=?");

        pstm.setString(1, dto.getSupplierName());
        pstm.setString(2, dto.getContactNumber());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getSupplierId());

        boolean isUpdated = pstm.executeUpdate()>0;
        return  isUpdated;
    }

    public boolean deleteSupplier(String supplierId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE  FROM  supplier WHERE  supplierId=?");
        pstm.setString(1,supplierId);

        boolean isDeleted = pstm.executeUpdate()>0;
        return  isDeleted;
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
              resultSet.getString(3),
              resultSet.getString(4)
            );
        }
        return dto;
    }


    public List<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM supplier");

        List<SupplierDto> supplierList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            supplierList.add(new SupplierDto(
                 resultSet.getString(1),
                 resultSet.getString(2),
                 resultSet.getString(3),
                 resultSet.getString(4)
            ));
        }
        return supplierList;
    }


}
