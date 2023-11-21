package lk.ijse.LibraSys.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {
    private String ISBN;
    private String bookName;
    private String category;
    private String qtyOnHand;
    private String rackCode;
    private String authorId;



}
