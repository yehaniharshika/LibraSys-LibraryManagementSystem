package lk.ijse.LibraSys.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BookRackTm {
    private  String rackCode;
    private  int qtyBooks;
    private String nameOfBooks;
}
