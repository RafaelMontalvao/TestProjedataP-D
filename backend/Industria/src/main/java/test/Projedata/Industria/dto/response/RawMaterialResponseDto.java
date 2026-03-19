package test.Projedata.Industria.dto.response;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class RawMaterialResponseDto {

    private Long id;

    private String name;


    private BigDecimal stockQuantity;
}

