package lk.ijse.LibraSys.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookRackDto {
    private  String rackCode;
    private  int qtyBooks;
    private String categoryOfBooks;
    private String nameOfBooks;
}
