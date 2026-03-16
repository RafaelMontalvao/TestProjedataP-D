package test.Projedata.Industria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import test.Projedata.Industria.dto.response.ProductMaterialResponseDto;
import test.Projedata.Industria.model.ProductMaterial;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(ProductMaterial.class, ProductMaterialResponseDto.class)
                .addMapping(src -> src.getRawMaterial().getId(),
                        ProductMaterialResponseDto::setRawMaterialId)
                .addMapping(src -> src.getRawMaterial().getName(),
                        ProductMaterialResponseDto::setRawMaterialName)
                .addMapping(ProductMaterial::getRequeiredQuantity,
                        ProductMaterialResponseDto::setQuantityNeeded);

        return mapper;
    }
}
