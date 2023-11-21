package lk.ijse.LibraSys.dto.tm;

import javafx.scene.control.Button;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierCartTm {

    private String ISBN;
    private String bookName;
    private int qty;
    private Button btn;

}
