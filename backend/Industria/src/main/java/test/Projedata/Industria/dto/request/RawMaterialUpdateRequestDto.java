package test.Projedata.Industria.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RawMaterialUpdateRequestDto {



    private String name;


    private BigDecimal stockQuantity;
}

