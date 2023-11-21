package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.BookSupplierDetailDto;
import lk.ijse.LibraSys.dto.PlaceBooksSupplierOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlacebookSupplierModel {
    private SupplierModel supplierModel = new SupplierModel();
    private BookModel bookModel = new BookModel();
    private BooksSupplierDetailModel booksSupplierDetailModel = new BooksSupplierDetailModel();
    private BookSupplierDetailDto bookSupplierDetailDto = new BookSupplierDetailDto();

    public  boolean placeBooksOrder(PlaceBooksSupplierOrderDto palceBooksSupplierOrderDto) throws SQLException {
        String supplierId = palceBooksSupplierOrderDto.getSupplierId();
        String supName = palceBooksSupplierOrderDto.getSupName();
        String contactNumber = palceBooksSupplierOrderDto.getContactNumber();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSupplierSaved = supplierModel.saveSupplier(supplierId,supName,contactNumber);

            if(isSupplierSaved){
                System.out.println("1"+isSupplierSaved);
                boolean isUpdated = bookModel.updateBooks(palceBooksSupplierOrderDto.getSupplierCartTmList());

                if (isUpdated){
                    System.out.println("2 :"+isUpdated);

                    boolean isBooksSupplierDetailSaved = booksSupplierDetailModel.saveBooksSupplierDetail(palceBooksSupplierOrderDto.getSupplierId(),palceBooksSupplierOrderDto.getSupplierDate(),palceBooksSupplierOrderDto.getSupplierCartTmList());
                    if (isBooksSupplierDetailSaved){
                        System.out.println("3:"+isBooksSupplierDetailSaved);
                        connection.commit();
                    }else {
                        connection.rollback();
                    }
                }else{
                    connection.rollback();
                }

            }else{
                connection.rollback();
            }
            //connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
