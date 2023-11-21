package lk.ijse.LibraSys.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AuthorFormController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private TableColumn<?, ?> colAuthorId;

    @FXML
    private TableColumn<?, ?> colAuthorName;

    @FXML
    private TableColumn<?, ?> colCurrentlyBooksWrittenQty;

    @FXML
    private TableColumn<?, ?> colNationality;

    @FXML
    private TableColumn<?, ?> colText;

    @FXML
    private TableView<?> tblAuthor;

    @FXML
    private TextField txtAuthorId;

    @FXML
    private TextField txtAuthorName;

    @FXML
    private TextField txtCurrentlyBooksWrittenQty;

    @FXML
    private TextField txtNationality;

    @FXML
    private TextField txtText;

    @FXML
    void btnBackOnAction(ActionEvent event) {


    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtAuthorId.setText("");
        txtAuthorName.setText("");
        txtText.setText("");
        txtNationality.setText("");
        txtCurrentlyBooksWrittenQty.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText();
        String authorName = txtAuthorName.getText();
        String text = txtText.getText();
        String nationality = txtNationality.getText();
        int currentlyBooksWrittenQty = Integer.parseInt(txtCurrentlyBooksWrittenQty.getText());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText();
        String authorName = txtAuthorName.getText();
        String text = txtText.getText();
        String nationality = txtNationality.getText();
        int currentlyBooksWrittenQty = Integer.parseInt(txtCurrentlyBooksWrittenQty.getText());
    }

    @FXML
    void txtAuthorIdOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText();
    }

}
