package test.Projedata.Industria.component;

import org.springframework.stereotype.Component;
import test.Projedata.Industria.dto.response.ProductAvailabilityDto;
import test.Projedata.Industria.model.Product;

import java.math.BigDecimal;

@Component
public class ProductAvailabilityMapper {

    public ProductAvailabilityDto toDto(Product product, BigDecimal quantity) {

        ProductAvailabilityDto dto = new ProductAvailabilityDto();

        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setProductcode(product.getCode());
        dto.setProducibleQuantity(quantity);
        dto.setTotalProductionValue(
                quantity.multiply(product.getPrice())
        );

        return dto;
    }
}