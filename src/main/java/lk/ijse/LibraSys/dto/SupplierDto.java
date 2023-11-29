package lk.ijse.LibraSys.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplierDto {
    private String supplierId;
    private String supplierName;
    private String contactNumber;
    private String  email;
}
