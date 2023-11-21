package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.BookDto;
import lk.ijse.LibraSys.dto.tm.SupplierCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookModel {
    public  String generateNextBookISBN(String ISBN) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT ISBN FROM book ORDER BY ISBN DESC LIMIT 1");
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return splitBookISBN(resultSet.getString(1));

        }
        return splitBookISBN(null);
    }

    private String splitBookISBN(String currentBookISBN) {
        if (currentBookISBN != null){
            String[] split = currentBookISBN.split("[B]");
            int ISBN= Integer.parseInt(split[1]);
            ISBN++;
            return "B0" + ISBN;
        }
        return "B001";
    }

    public  boolean saveBook(BookDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?)");
        pstm.setString(1, dto.getISBN());
        pstm.setString(2, dto.getBookName());
        pstm.setString(3, dto.getCategory());
        pstm.setString(4, dto.getQtyOnHand());
        pstm.setString(5,dto.getRackCode());

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateBook(BookDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE book SET bookName=?, category =?,qtyOnHand=?, rackCode=? WHERE ISBN=?");
        pstm.setString(1, dto.getBookName());
        pstm.setString(2, dto.getCategory());
        pstm.setString(3, dto.getQtyOnHand());
        pstm.setString(4, dto.getRackCode());
        pstm.setString(4, dto.getISBN());

        boolean isUpdated = pstm.executeUpdate() > 0;
        return isUpdated;
    }

    public boolean deleteBook(String ISBN) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE  FROM  book WHERE ISBN=?");
        pstm.setString(1,ISBN);

        boolean isDeleted = pstm.executeUpdate() > 0;
        return isDeleted;
    }

    public BookDto searchBook(String ISBN) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM book WHERE ISBN=?");
        pstm.setString(1,ISBN);

        ResultSet resultSet = pstm.executeQuery();

        BookDto dto= null;
        if(resultSet.next()){
            dto = new BookDto(
                 resultSet.getString(1),
                 resultSet.getString(2),
                 resultSet.getString(3),
                 resultSet.getString(4),
                 resultSet.getString(5)
            );
        }
        return dto;
    }

    public List<BookDto> getAllBooks() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT  * FROM  book");

        List<BookDto> bookList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            bookList.add(new BookDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5)
            ));
        }
        return bookList;
    }

    //transaction ekata
    public  boolean updateBooks(List<SupplierCartTm> supplierCartTmList) throws SQLException {
        for (SupplierCartTm Tm : supplierCartTmList){
            System.out.println("Book : "+ Tm);

            if (!updateQty(Tm.getISBN(), Tm.getQty())){
                return false;
            }
        }
        return true;
    }
    public  boolean updateQty(String ISBN , int  qtyOnHand) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="UPDATE book SET qtyOnHand = qtyOnHand + ? WHERE ISBN = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1,qtyOnHand);
        pstm.setString(2,ISBN);

        return  pstm.executeUpdate() > 0;
    }
}
