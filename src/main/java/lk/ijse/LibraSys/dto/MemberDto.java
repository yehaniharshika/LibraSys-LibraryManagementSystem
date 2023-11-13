package lk.ijse.LibraSys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class MemberDto {

    private String mid;
    private String name;
    private String address;
    private String gender;
    private String tel;
    private String feeId;
    private String sNumber;


    MemberDto(){

    }

    public MemberDto( String mid, String name, String address, String gender, String tel,String feeId, String sNumber ){
        this.mid = mid;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.tel = tel;
        this.feeId = feeId;
        this.sNumber = sNumber;
    }

    public String getsNumber() {
        return sNumber;
    }

    public String getFeeId() {
        return feeId;
    }

    public String getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getTel() {
        return tel;
    }

    public void setsNumber(String sNumber) {
        this.sNumber = sNumber;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "mid='" + mid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", feeId='" + feeId + '\'' +
                ", sNumber='" + sNumber + '\'' +
                '}';
    }
}
