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


    public  String generateNextMemberId(String mid) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT mid FROM member ORDER BY  mid DESC LIMIT 1");

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return splitMemberId(resultSet.getString(1));
        }
        return splitMemberId(null);
    }


   //for dashboard update
    public static  String getMemberCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT COUNT(mid) FROM  member");
        ResultSet resultSet = pstm.executeQuery();

        String count = null;
        if (resultSet.next()){
            count = resultSet.getString(1);
        }
        return count;
    }
    private String splitMemberId(String currentMemberId) {
        if(currentMemberId != null) {
            String[] strings = currentMemberId.split("M0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "M00"+id;
            }else {
                if (length < 3){
                    return "M0"+id;
                }else {
                    return "M"+id;
                }
            }
        }
        return "M001";
    }

    public boolean saveMember(MemberDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO member VALUES(?, ?, ?, ?, ?, ? ,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMid());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getGender());
        pstm.setString(5, dto.getTel());
        pstm.setString(6,dto.getEmailAddress());
        pstm.setString(7,dto.getIDNumber());
        pstm.setString(8, dto.getFeeId());
        pstm.setString(9, dto.getSNumber());


        boolean isSaved = pstm.executeUpdate() >0;
        return isSaved;
    }

    public  boolean updateMember(MemberDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE member SET name=?, address=?, gender=? ,tel =? ,EmailAddress =?,IDNumber=?, feeId=?,sNumber=? WHERE  mid=?");


        pstm.setString(1,dto.getName());
        pstm.setString(2,dto.getAddress());
        pstm.setString(3,dto.getGender());
        pstm.setString(4,dto.getTel());
        pstm.setString(5,dto.getEmailAddress());
        pstm.setString(6,dto.getIDNumber());
        pstm.setString(7,dto.getFeeId());
        pstm.setString(8, dto.getSNumber());
        pstm.setString(9, dto.getMid());


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
                resultSet.getString(7),
                resultSet.getString(8),
                resultSet.getString(9)
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
                  resultSet.getString(7),
                  resultSet.getString(8),
                  resultSet.getString(9)
           ));

        }
        return memberList;
    }



}
