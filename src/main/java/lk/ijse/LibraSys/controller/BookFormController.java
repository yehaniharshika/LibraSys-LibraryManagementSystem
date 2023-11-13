package lk.ijse.LibraSys.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.dto.BookRackDto;
import lk.ijse.LibraSys.model.BookRackModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookFormController {

    @FXML
    private JFXComboBox<String> cmbRackCode;

    @FXML
    private TableColumn<?, ?> colBookdName;

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
    private TableView<?> tblBook;

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

    public void initialize(){
        loadRackCodes();
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

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        String bookName = txtBookName.getText();
        String category = txtCategory.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String rackCode = cmbRackCode.getValue();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String ISBN = txtISBN.getText();
        String bookName = txtBookName.getText();
        String category = txtCategory.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String rackCode = cmbRackCode.getValue();

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

    @FXML
    void txtISBNOnAction(ActionEvent event) {

    }

}
