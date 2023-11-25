package lk.ijse.LibraSys.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@Setter
@ToString
public class MemberDto {

    private String mid;
    private String name;
    private String address;
    private String gender;
    private String tel;
    private String EmailAddress;
    private String IDNumber;
    private String feeId;
    private String sNumber;



    MemberDto(){

    }

    public MemberDto( String mid, String name, String address, String gender, String tel,String EmailAddress,String IDNumber,String feeId, String sNumber ){
        this.mid = mid;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.tel = tel;
        this.EmailAddress = EmailAddress;
        this.IDNumber = IDNumber;
        this.feeId = feeId;
        this.sNumber = sNumber;
    }

}
