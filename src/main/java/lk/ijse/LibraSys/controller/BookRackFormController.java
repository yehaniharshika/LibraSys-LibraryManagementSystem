package lk.ijse.LibraSys.controller;

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
import lk.ijse.LibraSys.dto.BookRackDto;
import lk.ijse.LibraSys.dto.tm.BookRackTm;
import lk.ijse.LibraSys.model.BookRackModel;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookRackFormController {

    @FXML
    private AnchorPane Pane;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colNameOfBooks;

    @FXML
    private TableColumn<?, ?> colQuantityBooks;

    @FXML
    private TableView<BookRackTm> tblBookrack;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtCategoryOfBooks;

    @FXML
    private TextArea txtNameOfBooks;
    private BookRackModel bookRackModel = new BookRackModel();

    public void initialize(){
        loadAllBookRacks();
        setCellValueFactory();
        tableListener();
    }

    private void tableListener() {
        tblBookrack.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);

        });
    }

    private void setData(BookRackTm row) {
        txtCode.setText(row.getRackCode());
        txtQuantity.setText(String.valueOf(row.getQtyBooks()));
        txtCategoryOfBooks.setText(row.getCategoryOfBooks());
        txtNameOfBooks.setText(row.getNameOfBooks());

    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) Pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("rackCode"));
        colQuantityBooks.setCellValueFactory(new PropertyValueFactory<>("qtyBooks"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryOfBooks"));
        colNameOfBooks.setCellValueFactory(new PropertyValueFactory<>("nameOfBooks"));
    }

    private void loadAllBookRacks() {
        ObservableList<BookRackTm> obList = FXCollections.observableArrayList();

        try {
            List<BookRackDto> rackList = bookRackModel.getAllBookRack();

            for(BookRackDto dto : rackList){
                obList.add(
                        new BookRackTm(
                                dto.getRackCode(),
                                dto.getQtyBooks(),
                                dto.getCategoryOfBooks(),
                                dto.getNameOfBooks()

                ));
            }
            tblBookrack.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtCode.setText("");
        txtQuantity.setText("");
        txtCategoryOfBooks.setText("");
        txtNameOfBooks.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();

        try {
            boolean isDeleted = bookRackModel.deleteBookRack(rackCode);

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Deleting Successfully!!!").show();
                loadAllBookRacks();
            }else{
                new Alert(Alert.AlertType.ERROR,"not deleted!!!").show();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isValidate = validateBookrack();
        if (isValidate){
            String rackCode = txtCode.getText();
            int qtyBooks = Integer.parseInt(txtQuantity.getText());
            String categoryOfBooks = txtCategoryOfBooks.getText();
            String nameOfBooks = txtNameOfBooks.getText();

            var dto = new BookRackDto(rackCode,qtyBooks,categoryOfBooks,nameOfBooks);

            try {
                boolean isSaved = bookRackModel.saveBookRack(dto);
                if(isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Book Rack Adding Successfully!!!").show();
                    clearFields();
                    loadAllBookRacks();
                    setCellValueFactory();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Book rack Adding failed!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }

    }

    private boolean validateBookrack(){
        String rackCode = txtCode.getText();
        Pattern compile = Pattern.compile("[R][0-9]{3,}");
        Matcher matcher = compile.matcher(rackCode);
        boolean matches = matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"Rack code invalid!!!").show();
            return  false;
        }

        String qtyBooks = txtQuantity.getText();
        boolean matches1 = Pattern.matches("[0-9]{1,}",qtyBooks);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"books quantity invalid!!!").show();
            return  false;
        }

        String categoryOfBooks = txtCategoryOfBooks.getText();
        boolean matches2 = Pattern.matches("[A-Za-z\\s]{2,}",categoryOfBooks);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Books category invalid!!!").show();
            return  false;
        }

      return  true;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();
        int qtyBooks = Integer.parseInt(txtQuantity.getText());
        String categoryOfBooks = txtCategoryOfBooks.getText();
        String nameOfBooks = txtNameOfBooks.getText();

        var dto = new BookRackDto(rackCode,qtyBooks,categoryOfBooks,nameOfBooks);

        try {
            boolean isUpdated = bookRackModel.updateBookRack(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Updated successfully!!!").show();
                clearFields();
                loadAllBookRacks();
            }else{
                new Alert(Alert.AlertType.ERROR,"not updated!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtCodeOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();

        try {
            BookRackDto dto = bookRackModel.serchBookRack(rackCode);

            if (dto != null){
                txtCode.setText(dto.getRackCode());
                txtQuantity.setText(String.valueOf(dto.getQtyBooks()));
                txtCategoryOfBooks.setText(dto.getCategoryOfBooks());
                txtNameOfBooks.setText(dto.getNameOfBooks());
            }else{
                new Alert(Alert.AlertType.ERROR,"not found!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
