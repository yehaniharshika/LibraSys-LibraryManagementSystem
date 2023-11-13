package lk.ijse.LibraSys.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.dto.SignupDto;
import lk.ijse.LibraSys.model.SignupModel;

import java.io.IOException;
import java.sql.SQLException;

public class SignupFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private TextField txtFristname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtServiceNumber;

    @FXML
    private TextField txtUserName;

    private SignupModel signupModel = new SignupModel();
    @FXML
    void btnCreateAccountOnAction(ActionEvent event) {
        String sNumber = txtServiceNumber.getText();
        String fristName =  txtFristname.getText();
        String lastName = txtLastname.getText();
        String nic = txtNIC.getText();
        String eAddress = txtEmailAddress.getText();
        String username = txtUserName.getText();
        String pw = txtPassword.getText();

        var dto = new SignupDto(sNumber,fristName,lastName,nic,eAddress,username,pw);
        try {
            boolean isRegistered = signupModel.registerLibrarian(dto);
            if (isRegistered){
                new Alert(Alert.AlertType.CONFIRMATION,"Registration is successful!!!").show();

            }else{
                new Alert(Alert.AlertType.ERROR,"Registration not successfully!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void hyperLoginHereOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_Form.fxml"));

        Scene scene = new Scene(rootNode);

        root.getScene().getWindow();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
    }
}
