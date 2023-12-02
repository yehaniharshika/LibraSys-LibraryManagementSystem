package lk.ijse.LibraSys.controller;

import com.google.zxing.WriterException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.LibraSys.QR.GenerateQRCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QrGeneratorFormController  implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnGenerate;

    @FXML
    private ImageView pic;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnController(true);

    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtText.clear();
        btnController(true);
        pic.setImage(new Image(new File("view/assets/9358350_4152212.jpg").toURI().toString()));
    }

    private void btnController(boolean flag) {
        btnGenerate.setDisable(flag);
        btnClear.setDisable(flag);
    }

    @FXML
    void btnGenerateOnAction(ActionEvent event) {
        if (!txtText.getText().isEmpty()) {
            GenerateQRCode generateQRCode = new GenerateQRCode();

            generateQRCode.setData(txtText.getText());
            try {
                generateQRCode.getGenerator();

                File file = new File(generateQRCode.getPath());

                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    pic.setImage(image);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to load the generated QR code image.").show();
                }

            } catch (IOException | WriterException e) {
                new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Input data first!").show();
        }

    }

    @FXML
    void onAction(ActionEvent event) {

    }

    @FXML
    void onKeyReleased(KeyEvent event) {
        if (!txtText.getText().isEmpty()){
            btnController(false);
        }else{
            btnController(true);
        }
    }

}
