package lk.ijse.LibraSys.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.BookDto;
import lk.ijse.LibraSys.dto.MemberDto;
import lk.ijse.LibraSys.dto.ReservationDto;
import lk.ijse.LibraSys.dto.tm.ReservationTm;
import lk.ijse.LibraSys.model.BookModel;
import lk.ijse.LibraSys.model.MemberModel;
import lk.ijse.LibraSys.model.ReservationModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationFormController {

    @FXML
    private AnchorPane Root;

    @FXML
    private JFXComboBox<String> cmbISBN;

    @FXML
    private JFXComboBox<String> cmbMemberId;

    @FXML
    private TableView<ReservationTm> tblReservation;

    @FXML
    private TableColumn<?, ?> colBookIsbn;

    @FXML
    private TableColumn<?, ?> colBookReturnDate;

    @FXML
    private TableColumn<?, ?> colBorrowedDate;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colFineAmount;

    @FXML
    private TableColumn<?, ?> colFineStatus;

    @FXML
    private TableColumn<?, ?> colMemberId;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblMemberName;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private TextField txtBorrowedDate;

    @FXML
    private DatePicker txtDueDate;

    @FXML
    private TextField txtFineAmount;

    @FXML
    private TextField txtFineStatus;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtReturnDate;
    private BookModel bookModel = new BookModel();
    private MemberModel memberModel = new MemberModel();
    private ReservationModel reservationModel = new ReservationModel();

    public void initialize(){
        generateNextReservationId();
        loadMemberIds();
        loadBookISBN();
        setDate();
        loadAllReservation();
        setCellValueFactory();
        tableListener();

    }

    private void tableListener() {
        tblReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);

        });
    }

    private void setData(ReservationTm row) {
        txtReservationId.setText(row.getReservationId());
        txtBorrowedDate.setText(row.getBorrowedDate());
        txtDueDate.setValue(LocalDate.parse(row.getDueDate()));
        txtReturnDate.setText(row.getBookReturnDate());
        txtFineStatus.setText(row.getFineStatus());
        txtFineAmount.setText(String.valueOf(row.getFineAmount()));
        cmbMemberId.setValue(row.getMid());
        cmbISBN.setValue(row.getISBN());
    }


    private void setCellValueFactory() {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colBookReturnDate.setCellValueFactory(new PropertyValueFactory<>("bookReturnDate"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatus"));
        colFineAmount.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colBookIsbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    }

    private void loadAllReservation() {
        ObservableList<ReservationTm> obList = FXCollections.observableArrayList();

        try {
            List<ReservationDto> reservationList = reservationModel.getAllReservation();

            for(ReservationDto dto: reservationList){
                obList.add(new ReservationTm(
                        dto.getReservationId(),
                        dto.getBorrowedDate(),
                        dto.getDueDate(),
                        dto.getBookReturnDate(),
                        dto.getFineStatus(),
                        dto.getFineAmount(),
                        dto.getMid(),
                        dto.getISBN()
                ));
            }
            tblReservation.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setDate() {
        txtBorrowedDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextReservationId() {
        try {
            String reservationId = reservationModel.generateNextReservationId(txtReservationId.getText());
            txtReservationId.setText(reservationId);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void loadBookISBN() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<BookDto> ISBNlist = bookModel.getAllBooks();

            for (BookDto dto : ISBNlist){
                obList.add(dto.getISBN());
            }
            cmbISBN.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMemberIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<MemberDto> mIdList = memberModel.getAllMember();

            for(MemberDto dto : mIdList){
                obList.add(dto.getMid());
            }
            cmbMemberId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnAddReservationOnAction(ActionEvent event) {
        boolean isValidated = validateReservation();
        if (isValidated){
            String reservationId = txtReservationId.getText();
            String borrowedDate = txtBorrowedDate.getText();
            String dueDate = String.valueOf(txtDueDate.getValue());
            String bookReturnDate = txtReturnDate.getText();
            String fineStatus = txtFineStatus.getText();
            double fineAmount = Double.parseDouble(txtFineAmount.getText());
            String mid = cmbMemberId.getValue();
            String ISBN = cmbISBN.getValue();


            var dto = new ReservationDto(reservationId,borrowedDate,dueDate,bookReturnDate,fineStatus,fineAmount,mid,ISBN);

            try {
                boolean isAdd = reservationModel.addReservation(dto);
                if(isAdd){
                    new Alert(Alert.AlertType.CONFIRMATION,"Adding reservation successfully!!!").show();
                    clearFields();
                    loadAllReservation();
                    setCellValueFactory();
                    setDate();
                    generateNextReservationId();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Reservation failed!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }

        }

    }

    private boolean validateReservation(){
        String reservationId = txtReservationId.getText();
        Pattern compile = Pattern.compile("[R][0-9]{3,}");
        Matcher matcher = compile.matcher(reservationId);
        boolean matches = matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"Invalid reservation Id!!!").show();
            return false;
        }

        String fineStatus = txtFineStatus.getText();
        boolean matches1 =  Pattern.matches("Yes|No" ,fineStatus);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"Invalid fine status!!!").show();
        }
        return true;
    }

    @FXML
    void btnDeleteReservationOnAction(ActionEvent event) {
        String  reservationId = txtReservationId.getText();

        try {
            boolean isDeleted = reservationModel.deleteReservation(reservationId);
            if (isDeleted){

                new Alert(Alert.AlertType.INFORMATION,"reservation deleted successfully!!").showAndWait();
                loadAllReservation();
            }else {
                new Alert(Alert.AlertType.ERROR,"reservation not deleted!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void txtReservationIdOnAction(ActionEvent event) {
        String  reservationId = txtReservationId.getText();

        try {
            ReservationDto dto =reservationModel.searchReservation(reservationId);

            if (dto != null){
                txtReservationId.setText(dto.getReservationId());
                txtBorrowedDate.setText(dto.getBorrowedDate());
                txtDueDate.setValue(LocalDate.parse(dto.getDueDate()));
                txtReturnDate.setText(dto.getBookReturnDate());
                txtFineStatus.setText(dto.getFineStatus());
                txtFineAmount.setText(String.valueOf(dto.getFineAmount()));
                cmbMemberId.setValue(dto.getMid());
                cmbISBN.setValue(dto.getISBN());
            }else{
                new Alert(Alert.AlertType.ERROR,"Reservation not found!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void clearFields() {
        txtReservationId.setText("");
        txtBorrowedDate.setText("");
        txtDueDate.setAccessibleText("");
        txtReturnDate.setText("");
        txtFineStatus.setText("");
        txtFineAmount.setText("");
        cmbMemberId.setValue("");
        cmbISBN.setValue("");
        lblMemberName.setText("");
        lblBookName.setText("");
        lblQtyOnHand.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextReservationId();
        setDate();

    }

    @FXML
    void btnNewBookOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/book_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Book Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnNewMemberOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/member_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Member Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnUpdateReservationOnAction(ActionEvent event) {
        String reservationId = txtReservationId.getText();
        String borrowedDate = txtBorrowedDate.getText();
        String dueDate = String.valueOf(txtDueDate.getValue());
        String bookReturnDate = txtReturnDate.getText();
        String fineStatus = txtFineStatus.getText();
        double fineAmount = Double.parseDouble(txtFineAmount.getText());
        String mid = cmbMemberId.getValue();
        String ISBN = cmbISBN.getValue();

        var dto = new ReservationDto(reservationId,borrowedDate,dueDate,bookReturnDate,fineStatus,fineAmount,mid,ISBN);

        try {
            boolean isUpdated = reservationModel.updateReservation(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Reservation updated successfully!!!").show();
                loadAllReservation();
            }else {
                new Alert(Alert.AlertType.ERROR,"updated not found!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbBookOnAction(ActionEvent event) {
        System.out.println("-------------------------------------");
        String ISBN = cmbISBN.getValue();
        if(ISBN!=null){
            try {
                BookDto dto = bookModel.searchBook(ISBN);
                if(dto!=null){
                    System.out.println("dto : "+dto);
                    lblBookName.setText(dto.getBookName());
                    lblQtyOnHand.setText(dto.getQtyOnHand());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void cmbMemberOnAction(ActionEvent event) {
        String mid = String.valueOf(cmbMemberId.getValue());
        try {
            MemberDto dto = memberModel.searchMember(mid);
            if(dto!=null)           lblMemberName.setText(dto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
