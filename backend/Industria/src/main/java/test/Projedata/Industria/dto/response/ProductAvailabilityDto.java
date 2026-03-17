package test.Projedata.Industria.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAvailabilityDto {

    private Long productId;
    private String productName;
    private BigDecimal producibleQuantity;
    private String productcode;
    private BigDecimal totalProductionValue;
}
