package lk.ijse.LibraSys.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    public AnchorPane root;

    public void btnAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnMemberOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/member_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Member Manage Form");
        stage.centerOnScreen();
        stage.show();
    }

    public void btnReservationOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/reservation_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Manage Reservation Form");
        stage.centerOnScreen();
    }

    public void btnBookOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnBookRackOnAction(ActionEvent actionEvent) {
    }

    public void btnMemberFeeOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/memberFee_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Member Fee Manage Form");
        stage.centerOnScreen();
        stage.show();
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) {
    }
}
