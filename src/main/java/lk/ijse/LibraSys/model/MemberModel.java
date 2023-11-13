package lk.ijse.LibraSys.model;

import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.MemberDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberModel {
    private MembershipFeeModel membershipFeeModel =new MembershipFeeModel();


    public boolean saveMember(MemberDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO member VALUES(?, ?, ?, ?, ?, ? ,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMid());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getGender());
        pstm.setString(5, dto.getTel());
        pstm.setString(6, dto.getFeeId());
        pstm.setString(7, dto.getsNumber());


        boolean isSaved = pstm.executeUpdate() >0;
        return isSaved;
    }

    public  boolean updateMember(MemberDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE member SET name=?, address=?, gender=? ,tel =? ,feeId=?,sNumber=? WHERE  mid=?");


        pstm.setString(1,dto.getMid());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4,dto.getGender());
        pstm.setString(5,dto.getTel());
        pstm.setString(6, dto.getFeeId());
        pstm.setString(7, dto.getsNumber());


        boolean isUpdated = pstm.executeUpdate() > 0;
        return isUpdated;
    }

    public boolean deleteMember(String mid) throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE  FROM  member WHERE mid=?");
        pstm.setString(1,mid);

        boolean isDeleted = pstm.executeUpdate() > 0;

        return isDeleted;
    }

    public MemberDto searchMember(String mid) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT  * FROM  member WHERE mid=?");
        pstm.setString(1,mid);

        ResultSet resultSet = pstm.executeQuery();

        MemberDto dto = null;

        if(resultSet.next()){
            dto = new MemberDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7)
            );
        }
        return dto;
    }

    public List<MemberDto> getAllMember() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM member");

        List<MemberDto> memberList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while(resultSet.next()){
           memberList.add(new MemberDto(
                  resultSet.getString(1),
                  resultSet.getString(2),
                  resultSet.getString(3),
                  resultSet.getString(4),
                  resultSet.getString(5),
                  resultSet.getString(6),
                  resultSet.getString(7)
           ));

        }
        return memberList;
    }

}
