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
import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_Form.fxml"));

        Scene scene = new Scene(rootNode);

        root.getScene().getWindow();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }


    //jasper Report
    @FXML
    void printReservationReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/reservationDetail.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        null,
                        DbConnection.getInstance().getConnection()

                );
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void printAuthorListOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/AuthorList.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint,false);

    }

    @FXML
    void printBookRackListOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/bookRackList.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint);
    }

    @FXML
    void printBookReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/bookList.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        null,
                        DbConnection.getInstance().getConnection()

                );
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void printBookSupplierDetailsListOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/bookSupplierDetail.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        null,
                        DbConnection.getInstance().getConnection()

                );
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void printMemberListOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/memberDetail.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        null,
                        DbConnection.getInstance().getConnection()

                );
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void printMembershipFeeListOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/membershipFee.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint,false);
    }


    @FXML
    void printSupplierListOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/SupplierList.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint,false);
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
