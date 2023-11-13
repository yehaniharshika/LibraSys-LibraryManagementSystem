package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.BookDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookModel {

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
}
