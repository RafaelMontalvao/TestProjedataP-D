package test.Projedata.Industria.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMaterialRequestDto {

    private Long rawMaterialId;

    private BigDecimal quantityNeeded;
}
