package lk.ijse.LibraSys.dto;

import lk.ijse.LibraSys.dto.tm.SupplierCartTm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlaceBooksSupplierOrderDto {
    private String supplierId;
    private String SupName;
    private String ContactNumber;

    private List<SupplierCartTm> supplierCartTmList = new ArrayList<>();

}
