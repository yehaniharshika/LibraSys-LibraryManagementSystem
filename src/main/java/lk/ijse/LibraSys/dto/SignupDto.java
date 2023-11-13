package lk.ijse.LibraSys.dto;

import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class SignupDto {
    private String sNumber;
    private String firstName;
    private String lastName;
    private String nic;
    private String eAddress;
    private String username;
    private String pw;

   // SignupDto(TextField txtServiceNumber, TextField txtUserName, TextField txtPassword){}

    public SignupDto(String sNumber, String firstName, String lastName, String nic, String eAddress, String username, String pw){
        this.sNumber = sNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.eAddress = eAddress;
        this.username = username;
        this.pw = pw;

    }
}
