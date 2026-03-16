package test.Projedata.Industria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

public class ProductRequestDto {
    @Data
    public class RawMaterialRequestDto {

        @NotBlank(message = "O nome não pode ser vazio")
        private String name;

        @NotNull(message = "A quantidade em estoque é obrigatória")
        private BigDecimal stockQuantity;
    }
}
