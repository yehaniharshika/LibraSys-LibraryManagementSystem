package lk.ijse.LibraSys.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationTm {
    private String reservationId;
    private String borrowedDate;
    private  String  dueDate;
    private String bookReturnDate;
    private  String fineStatus;
    private double fineAmount;

    private String mid;
    private  String ISBN;
    private Button btn;


}
