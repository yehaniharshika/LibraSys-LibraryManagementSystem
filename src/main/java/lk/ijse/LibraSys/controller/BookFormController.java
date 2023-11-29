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
import lk.ijse.LibraSys.dto.AuthorDto;
import lk.ijse.LibraSys.dto.BookDto;
import lk.ijse.LibraSys.dto.BookRackDto;
import lk.ijse.LibraSys.dto.tm.BookTm;
import lk.ijse.LibraSys.model.AuthorModel;
import lk.ijse.LibraSys.model.BookModel;
import lk.ijse.LibraSys.model.BookRackModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookFormController {

    @FXML
    private JFXComboBox<String> cmbRackCode;

    @FXML
    private JFXComboBox<String> cmbAuthorId;


    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colISBN;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colAuthorId;

    @FXML
    private Label lblCategoryType;

    @FXML
    private Label lblAuthorName;

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

    private AuthorModel authorModel = new AuthorModel();


    public void initialize(){
        generateNextBookISBN();
        loadRackCodes();
        loadAllBooks();
        setCellValueFactory();
        loadAllAuthorIds();

    }


    private void generateNextBookISBN() {
        try {
            String ISBN = bookModel.generateNextBookISBN(txtISBN.getText());
            txtISBN.setText(ISBN);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    private void setCellValueFactory() {
        colISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
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
                        dto.getAuthorId()
                ));
            }
            tblBook.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllAuthorIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<AuthorDto> authorDtos = authorModel.getAllAuthors();

            for (AuthorDto dto : authorDtos){
                obList.add(dto.getAuthorId());

            }

            cmbAuthorId.setItems(obList);
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
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextBookISBN();
    }

    private void clearFields() {
        txtISBN.setText("");
        txtBookName.setText("");
        txtCategory.setText("");
        txtQtyOnHand.setText("");
        cmbRackCode.setValue("");
        cmbAuthorId.setValue("");
        lblAuthorName.setText("");
        lblCategoryType.setText("");
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
        boolean isValidate = validateBooks();
        if (isValidate){
            String ISBN = txtISBN.getText();
            String bookName = txtBookName.getText();
            String category = txtCategory.getText();
            String qtyOnHand = txtQtyOnHand.getText();
            String rackCode = cmbRackCode.getValue();
            String authorId = cmbAuthorId.getValue();

            var dto = new BookDto(ISBN,bookName,category,qtyOnHand,rackCode,authorId);

            try {
                boolean isSaved = bookModel.saveBook(dto);
                if(isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Book saved successfully!!!").show();
                    clearFields();
                    loadAllBooks();
                    setCellValueFactory();
                    generateNextBookISBN();
                    bookRackModel.updateQtyBooks(rackCode, Integer.parseInt(qtyOnHand));
                    //bookRackModel.updatenameOfBooks(rackCode, bookName);
                }else{
                    new Alert(Alert.AlertType.ERROR,"ohh,Book not Saved!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private  boolean validateBooks(){
        String ISBN = txtISBN.getText();
        Pattern compile = Pattern.compile("[B][0-9]{3,}");
        Matcher matcher = compile.matcher(ISBN);
        boolean matches = matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"Invalid book ID!!!").show();
            return  false;
        }

        String  bookName = txtBookName.getText();
        boolean matches1 = Pattern.matches("[A-Za-z\\s]{2,}", bookName);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"Invalid book name!!!").show();
            return  false;
        }

        String  category = txtCategory.getText();
        boolean matches2 = Pattern.matches("[A-Za-z\\s]{3,}" , category);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid book category!!!").show();
            return false;
        }


        return  true;
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        String bookName = txtBookName.getText();
        String category = txtCategory.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String rackCode = cmbRackCode.getValue();
        String authorId = cmbAuthorId.getValue();

        var dto = new BookDto(ISBN,bookName,category,qtyOnHand,rackCode,authorId);

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
        String rackCode = cmbRackCode.getValue();

        try {
            BookRackDto bookRackDto = bookRackModel.serchBookRack(rackCode);
            lblCategoryType.setText(bookRackDto.getCategoryOfBooks());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbAuthorIdOnAction(ActionEvent event) {
        String authorId = cmbAuthorId.getValue();

        try {
            AuthorDto authorDto = authorModel.searchAuthor(authorId);
            lblAuthorName.setText(authorDto.getAuthorName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
