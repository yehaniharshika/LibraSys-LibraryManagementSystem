package lk.ijse.LibraSys.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.database.Db;

import java.io.IOException;

public class LoginFormController {


    public TextField txtServiceNumber;
    public TextField txtUserName;
    public TextField txtPassword;
    public AnchorPane root;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String SNumber = txtServiceNumber.getText();
        String userName = txtUserName.getText();
        String pw = txtPassword.getText();

        if(Db.USER_NAME.equals(userName) && Db.PW.equals(pw) && Db.S_NUMBER.equals(SNumber)){
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_Form.fxml"));

            Scene scene = new Scene(rootNode);

            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);

            primaryStage.setTitle("Dashboard");
            primaryStage.centerOnScreen();
            primaryStage.show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Credentials are wrong!!!").show();
        }
    }
}
