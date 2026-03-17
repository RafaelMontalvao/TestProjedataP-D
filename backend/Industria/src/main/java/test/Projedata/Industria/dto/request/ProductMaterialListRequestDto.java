package test.Projedata.Industria.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductMaterialListRequestDto {
    private List<ProductMaterialRequestDto> materials;
}
