package test.Projedata.Industria.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMaterialResponseDto {


    private Long id;
    private Long productId;
    private Long rawMaterialId;
    private String rawMaterialName;
    private BigDecimal quantityNeeded;


}
