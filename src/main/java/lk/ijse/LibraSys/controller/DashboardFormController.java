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
import lk.ijse.LibraSys.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
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

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label lblMemberCount;

    @FXML
    private Label lblBorrowCount;
    @FXML
    private Label lblBookCount;

    @FXML
    private Label lblAuthorCount;

    @FXML
    private Label lblSupplierCount;

    private MemberModel memberModel = new MemberModel();
    private MemberFormController memberFormController = new MemberFormController();

    private ReservationModel reservationModel = new ReservationModel();
    private BookModel bookModel = new BookModel();
    private AuthorModel authorModel = new AuthorModel();
    
    private SupplierModel supplierModel = new SupplierModel();

    public void initialize(){
        updateTime();
        setDateandTime();
        setMemberCount();
        setBookBorrowCount();
        setNewBookCount();
        setAuthorCount();
        setSupplierCount();

    }

    private void setSupplierCount() {
        try {
            lblSupplierCount.setText(supplierModel.getSupplierCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAuthorCount() {
        try {
            lblAuthorCount.setText(authorModel.getAuthorCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNewBookCount() {
        try {
            lblBookCount.setText(bookModel.getBookCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setBookBorrowCount() {
        try {
            lblBorrowCount.setText(reservationModel.getBookBorrowCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //member count update
    private void setMemberCount() {

        try {
            lblMemberCount.setText(memberModel.getMemberCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    @FXML
    void btnSendingEmailOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/sendingMail_form.fxml"));

        this.anchor.getChildren().clear();
        this.anchor.getChildren().add(node);
    }

}
