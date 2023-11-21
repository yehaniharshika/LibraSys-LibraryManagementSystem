package lk.ijse.LibraSys.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookSupplierDetailDto {
    private String supplierId;
    private String ISBN;
    private  String bookName;
    private String qty;


}
