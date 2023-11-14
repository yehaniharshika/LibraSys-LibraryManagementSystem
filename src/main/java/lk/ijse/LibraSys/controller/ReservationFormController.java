package lk.ijse.LibraSys.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.LibraSys.dto.BookDto;
import lk.ijse.LibraSys.dto.MemberDto;
import lk.ijse.LibraSys.model.BookModel;
import lk.ijse.LibraSys.model.MemberModel;

import java.sql.SQLException;
import java.util.List;

public class ReservationFormController {

    @FXML
    private AnchorPane Root;

    @FXML
    private JFXComboBox<String> cmbISBN;

    @FXML
    private JFXComboBox<String> cmbMemberId;

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

    public void initialize(){
        generateNextReservationId();
        loadMemberIds();
        loadBookISBN();
    }

    private void generateNextReservationId() {


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
        String reservationId = txtReservationId.getText();
        String borrowedDate = txtBorrowedDate.getText();
        String dueDate = String.valueOf(txtDueDate.getValue());
        String bookReturnDate = txtReturnDate.getText();
        String fineStatus = txtFineStatus.getText();
        double fineAmount = Double.parseDouble(txtFineAmount.getText());
        String mid = cmbMemberId.getValue();
        String ISBN = cmbISBN.getValue();
        Button btn = new Button("Remove Reservation");

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewBookOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewMemberOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateReservationOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBookOnAction(ActionEvent event) {
        String ISBN = String.valueOf(cmbISBN.getValue());

        try {
            BookDto dto = bookModel.searchBook(ISBN);
            lblBookName.setText(dto.getBookName());
            lblQtyOnHand.setText(dto.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbMemberOnAction(ActionEvent event) {
        String mid = String.valueOf(cmbMemberId.getValue());
        
        try {
            MemberDto dto = memberModel.searchMember(mid);
            lblMemberName.setText(dto.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
