package lk.ijse.LibraSys.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DashboardFormController {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane Root;

    public void initialize(){
        updateTime();
        setDateandTime();

    }



    private void updateTime() {
        LocalTime now = LocalTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        lblTime.setText(formattedTime);
    }

    private void setDateandTime() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        DateFormat date = new SimpleDateFormat("yyy:MM:dd");
        Calendar cal = Calendar.getInstance();

        int year;
        int month;
        int datee;
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        datee = cal.get(Calendar.DATE);
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void btnAuthorsOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/author_Form.fxml"));

        this.Root.getChildren().clear();
        this.Root.getChildren().add(node);

    }

    @FXML
    void btnBookOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/book_Form.fxml"));

        this.Root.getChildren().clear();
        this.Root.getChildren().add(node);

    }

    @FXML
    void btnBookrackOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/bookRack_Form.fxml"));

        this.Root.getChildren().clear();
        this.Root.getChildren().add(node);

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnMemberOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/member_Form.fxml"));

        this.Root.getChildren().clear();
        this.Root.getChildren().add(node);
    }

    @FXML
    void btnMembershipFeeOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/membershipFee_Form.fxml"));

        this.Root.getChildren().clear();
        this.Root.getChildren().add(node);
    }

    @FXML
    void btnReservationOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/reservation_Form.fxml"));

        this.Root.getChildren().clear();
        this.Root.getChildren().add(node);

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/supplier_Form.fxml"));

        this.Root.getChildren().clear();
        this.Root.getChildren().add(node);

    }

}
