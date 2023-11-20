package lk.ijse.LibraSys.controller;


import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.LibraSys.dto.BookDto;
import lk.ijse.LibraSys.dto.PlaceBooksSupplierOrderDto;
import lk.ijse.LibraSys.dto.tm.SupplierCartTm;
import lk.ijse.LibraSys.model.BookModel;
import lk.ijse.LibraSys.model.PlacebookSupplierModel;
import lk.ijse.LibraSys.model.SupplierModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierFormController {

    @FXML
    private AnchorPane Root;

    @FXML
    private JFXComboBox<String> cmbBookISBN;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colBookISBN;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblTotalBooksCount;

    @FXML
    private TableView<?> tblSupplierDetail;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplyQuantity;

    private BookModel bookModel = new BookModel();
    private SupplierModel supplierModel = new SupplierModel();
    private ObservableList<SupplierCartTm> obList = FXCollections.observableArrayList();
    private PlacebookSupplierModel placebookSupplierModel = new PlacebookSupplierModel();



    public  void initialize() {
        loadAllBookISBNs();

        setCellValueFactory();
    }

    private void setCellValueFactory() {

        colBookISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
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
    void btnAddSupplierCartOnAction(ActionEvent event) {
        String ISBN = cmbBookISBN.getValue();
        String bookName = lblBookName.getText();
        int Qty = Integer.parseInt(txtSupplyQuantity.getText());
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);
        //btn.setStyle("-fx-border-color: pink;");

        var SupplierCartTm = new SupplierCartTm(ISBN,bookName,Qty,btn);
        System.out.println("add to cart success!!");
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

    private void calculateTotal() {
        int booksTotal = 0;
        for(int i=0 ;i < tblSupplierDetail.getItems().size();i++ ){
            booksTotal += (int)colQty.getCellData(i);
        }
        lblTotalBooksCount.setText(String.valueOf(booksTotal));
    }

    @FXML
    void PlaceBooksOrderOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supName  = txtSupplierName.getText();
        String contactNumber =  txtContactNumber.getText();

        List<SupplierCartTm> supplierCartTmList = new ArrayList<>();
        for (int i =0 ; i < tblSupplierDetail.getItems().size(); i++){
            SupplierCartTm supplierCartTm = obList.get(i);
            supplierCartTmList.add(supplierCartTm);

        }
        System.out.println("Place Books supplier order from controller: "+ supplierCartTmList);

        var placeBooksSupplierOrderDto = new PlaceBooksSupplierOrderDto(supplierId,supName,contactNumber,supplierCartTmList);
        try {
            boolean isSuccess = placebookSupplierModel.placeBooksOrder(placeBooksSupplierOrderDto);
            if (isSuccess){
                new Alert(Alert.AlertType.CONFIRMATION,"Order success!!!").show();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnNewBookOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBookOnAction(ActionEvent event) {
        String ISBN = (String) cmbBookISBN.getValue();

        txtSupplyQuantity.requestFocus();
        try {
            BookDto dto = bookModel.searchBook(ISBN);
            lblBookName.setText(dto.getBookName());
            lblQtyOnHand.setText(dto.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSuppliyQuantityOnAction(ActionEvent actionEvent) {
        btnAddSupplierCartOnAction(actionEvent);
    }
}


