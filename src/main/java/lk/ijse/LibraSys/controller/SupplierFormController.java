package lk.ijse.LibraSys.controller;


import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.dto.BookDto;
import lk.ijse.LibraSys.dto.PlaceBooksSupplierOrderDto;
import lk.ijse.LibraSys.dto.SupplierDto;
import lk.ijse.LibraSys.dto.tm.SupplierCartTm;
import lk.ijse.LibraSys.model.BookModel;
import lk.ijse.LibraSys.model.PlacebookSupplierModel;
import lk.ijse.LibraSys.model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierFormController {

    @FXML
    private AnchorPane Root;

    @FXML
    private JFXComboBox<String> cmbBookISBN;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colBookISBN;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colQty;


    @FXML
    private Label lblSupplierDate;


    @FXML
    private Label lblBookName;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblTotalBooksCount;

    @FXML
    private TableView<SupplierCartTm> tblSupplierDetail;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSupplyQuantity;

    private BookModel bookModel = new BookModel();
    private SupplierModel supplierModel = new SupplierModel();
    private ObservableList<SupplierCartTm> obList = FXCollections.observableArrayList();
    private PlacebookSupplierModel placebookSupplierModel = new PlacebookSupplierModel();



    public  void initialize() {
        generateNextSupplierId();
        loadAllBookISBNs();
        setDate();
        setCellValueFactory();

    }

    private void generateNextSupplierId() {
        try {
            String supplierId = supplierModel.generateNextSupplierId(txtSupplierId.getText());
            txtSupplierId.setText(supplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    private void clearAllFields() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtContactNumber.setText("");
        txtEmail.setText("");
        lblBookName.setText("");
        lblQtyOnHand.setText("");
        cmbBookISBN.setValue("");
        txtSupplyQuantity.setText("");

    }

    private void clearFields() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtContactNumber.setText("");
        txtEmail.setText("");
    }


    private void setDate() {
        lblSupplierDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setCellValueFactory() {

        colBookISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void loadAllBookISBNs() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<BookDto> ISBNList= bookModel.getAllBooks();

            for(BookDto dto : ISBNList){
                obList.add(dto.getISBN());
            }
            cmbBookISBN.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        try {
            boolean isDeleted = supplierModel.deleteSupplier(supplierId);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted Successfully!!!").show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"supplier not deleted!!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();

        var dto = new SupplierDto(supplierId,supplierName,contactNumber,email);

        try {
            boolean isUpdated = supplierModel.updateSupplier(dto);

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"supplier updated successfully!!!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"supplier not updated!!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddSupplierCartOnAction(ActionEvent event) {
        String ISBN = cmbBookISBN.getValue();
        String bookName = lblBookName.getText();
        int qty = Integer.parseInt(txtSupplyQuantity.getText());
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);
        btn.setStyle("-fx-background-color:#cf6a87; -fx-border-color: black; -fx-background-radius: 15; -fx-border-radius: 15; -fx-font-weight: bold");
        btn.setMinWidth(112);
        btn.setMinHeight(30);
        if (!obList.isEmpty()){
            for (int i=0 ; i<tblSupplierDetail.getItems().size() ; i++){
                if (colBookISBN.getCellData(i).equals(ISBN)){
                    int colsupplierQuantity = (int) colQty.getCellData(i);
                    qty += colsupplierQuantity;

                    obList.get(i).setQty(qty);

                    calculateTotal();
                    tblSupplierDetail.refresh();
                    return;
                }
            }

        }
        var SupplierCartTm = new SupplierCartTm(ISBN,bookName,qty,btn);
        obList.add(SupplierCartTm);
        tblSupplierDetail.setItems(obList);
        calculateTotal();
        txtSupplyQuantity.clear();

    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblSupplierDetail.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblSupplierDetail.refresh();
                calculateTotal();

            }
        });

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();
        generateNextSupplierId();
    }

    private void calculateTotal() {
        int booksTotal = 0;
        for(int i=0 ;i < tblSupplierDetail.getItems().size();i++ ){
            booksTotal += (int)colQty.getCellData(i);
        }
        lblTotalBooksCount.setText(String.valueOf(booksTotal));
    }

    @FXML
    void PlaceBooksOrderOnAction(ActionEvent event) {
            boolean isValidate = validateSupplier();
            if (isValidate){
                String supplierId = txtSupplierId.getText();
                String supName  = txtSupplierName.getText();
                String contactNumber =  txtContactNumber.getText();
                String email = txtEmail.getText();
                LocalDate supplierDate = LocalDate.parse(lblSupplierDate.getText());

                List<SupplierCartTm> supplierCartTmList = new ArrayList<>();
                for (int i =0 ; i < tblSupplierDetail.getItems().size(); i++){
                    SupplierCartTm supplierCartTm = obList.get(i);
                    supplierCartTmList.add(supplierCartTm);

                }
                //System.out.println("Place Books supplier order from controller: "+ supplierCartTmList);

                var placeBooksSupplierOrderDto = new PlaceBooksSupplierOrderDto(supplierId,supName,contactNumber,email,supplierDate,supplierCartTmList);
                try {
                    boolean isSuccess = placebookSupplierModel.placeBooksOrder(placeBooksSupplierOrderDto);
                    if (isSuccess){
                        new Alert(Alert.AlertType.CONFIRMATION,"Order success!!!").show();
                        clearAllFields();
                        generateNextSupplierId();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
    }

    private boolean validateSupplier(){
        String supplierId = txtSupplierId.getText();
        Pattern compile = Pattern.compile("SP[0-9]{3,}");
        Matcher matcher = compile.matcher(supplierId);
        boolean isSupplierIdValidated = matcher.matches();
        if (!isSupplierIdValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid supplierID!!!").show();
            return false;
        }

        String supName = txtSupplierName.getText();
        boolean isSupplierNameValidated = Pattern.matches("[A-Za-z\\s]{2,}" , supName);
        if (!isSupplierNameValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid supplier name!!!").show();
            return false;
        }

        String contactNumber = txtContactNumber.getText();
        boolean isContactNumberValidated = Pattern.matches("(070|071|072|074|075|076|077|078|034|081|054|027|066|038|091|035|065|011|025|031|047|063|055|057|032|033|051|021|024|067|037|023|041|045|026)\\d{7}",contactNumber);
        if (!isContactNumberValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid contact number!!!").show();
            return false;
        }

        String email = txtEmail.getText();
        boolean isEmailValidated = Pattern.matches("[A-Za-z]{2,}@[A-Za-z]{2,}\\.[A-Za-z]{2,}|[a-zA-Z0-9]{2,}@[a-zA-Z]{2,}\\.[A-Za-z]{2,}|(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])([a-zA-Z]+)$",email);
        if (!isEmailValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid Email!!!").show();
            return  false;
        }
        return  true;
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
    void cmbBookOnAction(ActionEvent event) {
        String ISBN = (String) cmbBookISBN.getValue();

        txtSupplyQuantity.requestFocus();
        try {
            BookDto dto = bookModel.searchBook(ISBN);
            if (dto != null){
                lblBookName.setText(dto.getBookName());
                lblQtyOnHand.setText(dto.getQtyOnHand());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void txtSuppliyQuantityOnAction(ActionEvent actionEvent) {
        btnAddSupplierCartOnAction(actionEvent);
    }
    @FXML
    void txtSupplierIdOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();

        try {
            SupplierDto dto =supplierModel.searchSupplier(supplierId);
            if (dto != null){
                txtSupplierId.setText(dto.getSupplierId());
                txtSupplierName.setText(dto.getSupplierName());
                txtContactNumber.setText(dto.getContactNumber());
                txtEmail.setText(dto.getEmail());
            }else{
                new Alert(Alert.AlertType.ERROR,"Supplier not found!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}


