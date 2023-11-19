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
public class SupplierDto {
    private String contactNumber;
    private List<SupplierCartTm> supplierCartTmList = new ArrayList<>();
}
