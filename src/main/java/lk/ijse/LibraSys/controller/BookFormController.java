package lk.ijse.LibraSys.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.dto.BookDto;
import lk.ijse.LibraSys.dto.BookRackDto;
import lk.ijse.LibraSys.dto.tm.BookTm;
import lk.ijse.LibraSys.model.BookModel;
import lk.ijse.LibraSys.model.BookRackModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookFormController {

    @FXML
    private JFXComboBox<String> cmbRackCode;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colISBN;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colRackcode;

    @FXML
    private Label lblCategoryType;

    @FXML
    private Label lblNameOfBooks;

    @FXML
    private TableView<BookTm> tblBook;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private AnchorPane Root;
    private BookRackModel bookRackModel = new BookRackModel();
    private BookModel bookModel = new BookModel();

    public void initialize(){
        loadRackCodes();
        loadAllBooks();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colRackcode.setCellValueFactory(new PropertyValueFactory<>("rackCode"));
    }

    private void loadAllBooks() {
        ObservableList<BookTm> obList = FXCollections.observableArrayList();

        try {
            List<BookDto> bookList = bookModel.getAllBooks();

            for(BookDto dto : bookList){
                obList.add(new BookTm(
                        dto.getISBN(),
                        dto.getBookName(),
                        dto.getCategory(),
                        dto.getQtyOnHand(),
                        dto.getRackCode()
                ));
            }
            tblBook.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRackCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<BookRackDto> codeList  = bookRackModel.getAllBookRack();

            for (BookRackDto bookRackDto : codeList){
                obList.add(bookRackDto.getRackCode());
            }
            cmbRackCode.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) Root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtISBN.setText("");
        txtBookName.setText("");
        txtCategory.setText("");
        txtQtyOnHand.setText("");
        cmbRackCode.setValue("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();

        try {
            boolean isDeleted = bookModel.deleteBook(ISBN);
            
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Book deleted Successfully!!!").show();
                loadAllBooks();
            }else{
                new Alert(Alert.AlertType.ERROR,"not book deleted!!!").show();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void txtISBNOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        
        try {
            BookDto dto = bookModel.searchBook(ISBN);
            
            if(dto != null){
                txtISBN.setText(dto.getISBN());
                txtBookName.setText(dto.getBookName());
                txtCategory.setText(dto.getCategory());
                txtQtyOnHand.setText(dto.getQtyOnHand());
                cmbRackCode.setValue(dto.getRackCode());
            }else{
                new Alert(Alert.AlertType.ERROR,"Book not found!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        String bookName = txtBookName.getText();
        String category = txtCategory.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String rackCode = cmbRackCode.getValue();

        var dto = new BookDto(ISBN,bookName,category,qtyOnHand,rackCode);

        try {
            boolean isSaved = bookModel.saveBook(dto);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Book saved successfully!!!").show();
                clearFields();
                loadAllBooks();
                setCellValueFactory();
            }else{
                new Alert(Alert.AlertType.ERROR,"ohh,Book not Saved!!!").show();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        String bookName = txtBookName.getText();
        String category = txtCategory.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String rackCode = cmbRackCode.getValue();

        var dto = new BookDto(ISBN,bookName,category,qtyOnHand,rackCode);

        try {
            boolean isUpdated = bookModel.updateBook(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"book updated successfully").show();
                clearFields();
                loadAllBooks();
            }else{
                new Alert(Alert.AlertType.ERROR,"book not updated!!!").show();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbRackCodeOnAction(ActionEvent event) {
        String rackCode = String.valueOf(cmbRackCode.getValue());

        try {
            BookRackDto bookRackDto = bookRackModel.serchBookRack(rackCode);
            lblCategoryType.setText(bookRackDto.getCategoryOfBooks());
            lblNameOfBooks.setText(bookRackDto.getNameOfBooks());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    

}
