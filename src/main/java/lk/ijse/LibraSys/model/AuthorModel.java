package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.AuthorDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorModel {

    public String generateNextAuthorId(String authorId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT authorId FROM author ORDER BY authorId DESC LIMIT 1");
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return splitAuthorId(resultSet.getString(1));
        }
        return splitAuthorId(null);
    }

    private String splitAuthorId(String currentAuthorId) {
        if (currentAuthorId != null){
            String[] split = currentAuthorId.split("[A]");
            int authorId = Integer.parseInt(split[1]);
            authorId++;
            return "A00" + authorId;

        }
        return "A001";
    }

    public boolean saveAuthor(AuthorDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO author VALUES (?,?,?,?,?)");

        pstm.setString(1, dto.getAuthorId());
        pstm.setString(2, dto.getAuthorName());
        pstm.setString(3, dto.getText());
        pstm.setString(4, dto.getNationality());
        pstm.setInt(5,dto.getCurrentlyBooksWrittenQty());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public  boolean updateAuthor(AuthorDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE author SET authorName=?,text=?,nationality=?,currentlyBooksWrittenQty=? WHERE authorId=?");

        pstm.setString(1, dto.getAuthorName());
        pstm.setString(2, dto.getText());
        pstm.setString(3, dto.getNationality());
        pstm.setInt(4,dto.getCurrentlyBooksWrittenQty());
        pstm.setString(5, dto.getAuthorId());

        boolean isUpdated = pstm.executeUpdate() > 0;
        return isUpdated;
    }

    public boolean deleteAuthor(String authorId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE  FROM  author WHERE authorId =?");
        pstm.setString(1,authorId);

        boolean isDeleted = pstm.executeUpdate() > 0;

        return isDeleted;
    }

    public AuthorDto searchAuthor(String authorId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM  author WHERE authorId=?");
        pstm.setString(1,authorId);

        ResultSet resultSet = pstm.executeQuery();

        AuthorDto dto = null;
        if (resultSet.next()){
            dto = new AuthorDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getInt(5)
            );
        }
        return dto;
    }

    public List<AuthorDto> getAllAuthors() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM author");

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<AuthorDto> authorDtoList = new ArrayList<>();
        while (resultSet.next()){
            authorDtoList.add(new AuthorDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
            ));
        }
        return authorDtoList;
    }

}
