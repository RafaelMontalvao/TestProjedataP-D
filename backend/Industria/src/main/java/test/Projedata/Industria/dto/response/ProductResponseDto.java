package test.Projedata.Industria.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



    @Data
    public class ProductResponseDto {



        private Long id;

        private String name;

        private String code;

        private BigDecimal price;

        private List<ProductMaterialResponseDto> materials = new ArrayList<>();
}
