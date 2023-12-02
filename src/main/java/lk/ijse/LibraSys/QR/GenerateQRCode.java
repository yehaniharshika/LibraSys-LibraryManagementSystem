package lk.ijse.LibraSys.QR;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Paths;

public class GenerateQRCode {
    private String data;
    private String path;

    public void setData(String data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public  void getGenerator() throws IOException,WriterException {

        path = "/home/yehani/Desktop/Untitled "  + data +".png";

        BitMatrix encode = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,500,500);
        java.nio.file.Path path1 = Paths.get(path);
        MatrixToImageWriter.writeToPath(encode,"PNG", path1);
        new Alert(Alert.AlertType.INFORMATION,data+": QR successfully generate").show();
    }
}
