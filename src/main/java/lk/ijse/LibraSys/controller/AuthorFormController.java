package lk.ijse.LibraSys.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.AuthorDto;
import lk.ijse.LibraSys.dto.tm.AuthorTm;
import lk.ijse.LibraSys.model.AuthorModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private TableView<AuthorTm> tblAuthor;

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

    private AuthorModel authorModel = new AuthorModel();


    public void initialize(){
        loadAllAuthors();
        setCellValueFactory();
        generateNextAuthorId();
        tableListener();
    }

    private void tableListener() {
        tblAuthor.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);

        });
    }

    private void setData(AuthorTm row) {
        txtAuthorId.setText(row.getAuthorId());
        txtAuthorName.setText(row.getAuthorName());
        txtText.setText(row.getText());
        txtNationality.setText(row.getNationality());
        txtCurrentlyBooksWrittenQty.setText(String.valueOf(row.getCurrentlyBooksWrittenQty()));

    }
    private void generateNextAuthorId() {
        try {
            String authorId = authorModel.generateNextAuthorId(txtAuthorId.getText());
            txtAuthorId.setText(authorId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void setCellValueFactory() {
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        colText.setCellValueFactory(new PropertyValueFactory<>("text"));
        colNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        colCurrentlyBooksWrittenQty.setCellValueFactory(new PropertyValueFactory<>("currentlyBooksWrittenQty"));
    }

    private void loadAllAuthors() {
        ObservableList<AuthorTm> obList = FXCollections.observableArrayList();

        try {
            List<AuthorDto> authorList = authorModel.getAllAuthors();

            for (AuthorDto dto : authorList){
                obList.add(new AuthorTm(
                        dto.getAuthorId(),
                        dto.getAuthorName(),
                        dto.getText(),
                        dto.getNationality(),
                        dto.getCurrentlyBooksWrittenQty()
                ));
            }
            tblAuthor.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }

    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextAuthorId();
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

        try {
            boolean isDeleted = authorModel.deleteAuthor(authorId);
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Author deleted successfully!!!").show();
                loadAllAuthors();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isValidated = validateAuthor();
        if (isValidated){
            String authorId = txtAuthorId.getText();
            String authorName = txtAuthorName.getText();
            String text = txtText.getText();
            String nationality = txtNationality.getText();
            int currentlyBooksWrittenQty = Integer.parseInt(txtCurrentlyBooksWrittenQty.getText());

            var dto = new AuthorDto(authorId,authorName,text,nationality,currentlyBooksWrittenQty);

            try {
                boolean isSaved = authorModel.saveAuthor(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Author adding successfully!!!").show();
                    loadAllAuthors();
                    setCellValueFactory();
                    clearFields();
                    generateNextAuthorId();
                }else {
                    new Alert(Alert.AlertType.ERROR,"oooh,,,Author adding not successfully!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }

    }

    private  boolean validateAuthor(){
        String authorId = txtAuthorId.getText();
        Pattern compile = Pattern.compile("[A][0-9]{3,}");
        Matcher matcher = compile.matcher(authorId);
        boolean isAuthorIdValidated = matcher.matches();

        if (!isAuthorIdValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid author ID!!!").show();
            return  false;
        }

        String authorName = txtAuthorName.getText();
        boolean isAuthorNameValidated = Pattern.matches("[A-Za-z\\s]{1,}" , authorName);
        if (!isAuthorNameValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid author name!!!").show();
            return  false;
        }

        String text = txtText.getText();
        boolean isTextValidated = Pattern.matches("Mr|Mrs|Miss|Thero" , text);
        if (!isTextValidated){
            new Alert(Alert.AlertType.ERROR,"Not valid type!!!").show();
            return  false;
        }

        String nationality = txtNationality.getText();
        boolean isNationalityValidated = Pattern.matches("[A-Z][a-z]{3,}" ,nationality);
        if (!isNationalityValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid Nationality!!!").show();
            return  false;
        }
        return true;
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText();
        String authorName = txtAuthorName.getText();
        String text = txtText.getText();
        String nationality = txtNationality.getText();
        int currentlyBooksWrittenQty = Integer.parseInt(txtCurrentlyBooksWrittenQty.getText());

        var dto = new AuthorDto(authorId,authorName,text,nationality,currentlyBooksWrittenQty);

        try {
            boolean isUpdated = authorModel.updateAuthor(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Author updated successfully!!!").show();
                loadAllAuthors();
            }else{
                new Alert(Alert.AlertType.ERROR,"Author not updated!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtAuthorIdOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText();

        try {
            AuthorDto dto = authorModel.searchAuthor(authorId);

            if (dto != null){
                txtAuthorId.setText(dto.getAuthorId());
                txtAuthorName.setText(dto.getAuthorName());
                txtText.setText(dto.getText());
                txtNationality.setText(dto.getNationality());
                txtCurrentlyBooksWrittenQty.setText(String.valueOf(dto.getCurrentlyBooksWrittenQty()));
            }else {
                new Alert(Alert.AlertType.ERROR,"Author not found!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
