package test.Projedata.Industria.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequestDto {

    private String name;


    private String code;


    private BigDecimal price;
}
