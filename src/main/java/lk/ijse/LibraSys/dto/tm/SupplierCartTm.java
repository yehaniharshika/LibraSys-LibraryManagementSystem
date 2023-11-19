package lk.ijse.LibraSys.dto.tm;

import javafx.scene.control.Button;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierCartTm {
    private String SupplierId;
    private String supName;
    private String ISBN;
    private String bookName;
    private int Qty;
    private Button btn;

}
