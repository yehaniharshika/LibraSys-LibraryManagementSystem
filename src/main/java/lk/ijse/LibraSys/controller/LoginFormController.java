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
import lk.ijse.LibraSys.model.LoginModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtServiceNumber;

    @FXML
    private TextField txtUserName;
    private LoginModel loginModel = new LoginModel();




    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String sNumber = txtServiceNumber.getText();
        String username = txtUserName.getText();
        String pw = txtPassword.getText();

        try {
            boolean dto = loginModel.checkCredentials(sNumber,username,pw);

            if(dto){
                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_Form.fxml"));

                Scene scene = new Scene(rootNode);

                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(scene);

                primaryStage.setTitle("Dashboard");
                primaryStage.centerOnScreen();
                primaryStage.show();
            } else {
                new Alert(Alert.AlertType.ERROR,"Credentials are wrong!!!!!").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
    }

    @FXML
    void hyperSignUpOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signUp_form.fxml"));

        Scene scene = new Scene(rootNode);

        root.getScene().getWindow();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Signup Form");
        primaryStage.show();
    }
}



