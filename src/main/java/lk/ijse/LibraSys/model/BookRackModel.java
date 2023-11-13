package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.BookRackDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRackModel {
    public boolean saveBookRack(BookRackDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO  bookRack VALUES (?,?,?,?)");
        pstm.setString(1,dto.getRackCode());
        pstm.setString(2, String.valueOf(dto.getQtyBooks()));
        pstm.setString(3, dto.getCategoryOfBooks());
        pstm.setString(4, dto.getNameOfBooks());

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;
    }

    public  boolean updateBookRack(BookRackDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE bookRack SET qtyBooks=?,nameOfBooks =? ,categoryOfBooks=? WHERE rackCode =?");
        pstm.setString(1, String.valueOf(dto.getQtyBooks()));
        pstm.setString(2, dto.getNameOfBooks());
        pstm.setString(3, dto.getCategoryOfBooks());
        pstm.setString(4, dto.getRackCode());

        boolean isUpdated = pstm.executeUpdate() > 0;
        return isUpdated;
    }

    public boolean deleteBookRack(String rackCode) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM bookRack WHERE rackCode=?");
        pstm.setString(1,rackCode);

        boolean isDeleted = pstm.executeUpdate() > 0;
        return isDeleted;
    }

    public BookRackDto serchBookRack(String rackCode) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM bookRack WHERE rackCode=?");
        pstm.setString(1,rackCode);

        ResultSet resultSet = pstm.executeQuery();

        BookRackDto dto = null;

        if(resultSet.next()){
            dto = new BookRackDto(
                  resultSet.getString(1),
                  resultSet.getInt(2),
                  resultSet.getString(3),
                  resultSet.getString(4)
            );
        }
        return dto;
    }

    public List<BookRackDto> getAllBookRack() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM bookRack");

        List<BookRackDto> rackList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while(resultSet.next()){
            rackList.add(new BookRackDto(
                resultSet.getString(1),
                resultSet.getInt(2),
                resultSet.getString(3),
                resultSet.getString(4)
            ));

        }
        return rackList;
    }
}
