package lk.ijse.LibraSys.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class BookRackFormController {

    @FXML
    private AnchorPane Pane;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colNameOfBooks;

    @FXML
    private TableColumn<?, ?> colQuantityBooks;

    @FXML
    private TableView<?> tblBookrack;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtnameOfBooks;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String rackCode = txtCode.getText();
        int qtyBooks = Integer.parseInt(txtQuantity.getText());
        String nameOfBooks = txtnameOfBooks.getText();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtCodeOnAction(ActionEvent event) {

    }

}
