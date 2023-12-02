package lk.ijse.LibraSys.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.awt.image.BufferedImage;

public class QrReaderFormController {

    @FXML
    private Button btnEnd;

    @FXML
    private Button btnStart;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private AnchorPane miniPanel;

    @FXML
    private TextArea txtArea;

    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private boolean isReading = false;

    @FXML
    void btnEndOnAction(ActionEvent event) {
        stopWebcam();

    }

    private boolean stopWebcam() {
        if (webcamPanel != null){
            webcamPanel.stop();
            webcamPanel = null;
        }
        if (webcam != null){
            webcam.close();
            webcam = null;
        }
        return false;
    }

    public  void initialize(){
        startWebcam();
    }
    @FXML
    void btnStartOnAction(ActionEvent event) {
        isReading=(!isReading) ? startWebcam() : stopWebcam();
    }

    private boolean startWebcam() {
        if (webcam == null){
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getDefault();
            webcam.setViewSize(size);

            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setPreferredSize(size);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setMirrored(true);

            SwingNode swingNode = new SwingNode();
            swingNode.setContent(webcamPanel);

            miniPanel.getChildren().clear();
            miniPanel.getChildren().add(swingNode);
        }

        Thread thread = new Thread(() ->{
            while (isReading){
                try {
                    Thread.sleep(1000);
                    BufferedImage image = webcam.getImage();
                    if (image != null){
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                        Result result = new MultiFormatReader().decode(binaryBitmap);
                        Platform.runLater(() -> {
                            if (result != null) {
                                webcam.close();
                                txtArea.appendText(result.getText() + "\n");
                                new Alert(Alert.AlertType.INFORMATION, "Data Scanned Successfully!").showAndWait();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "No Data Found!").showAndWait();
                            }
                        });
                    }

                } catch (InterruptedException | NotFoundException | RuntimeException ignored) {

                }
            }

        });
        thread.start();
        return true;
    }

    @FXML
    void txtAreaClearOnAction(ActionEvent event) {
        txtArea.clear();

    }

}
