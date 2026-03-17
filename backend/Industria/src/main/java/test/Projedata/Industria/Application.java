package test.Projedata.Industria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import test.Projedata.Industria.dto.response.ProductMaterialResponseDto;
import test.Projedata.Industria.dto.response.ProductResponseDto;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.model.ProductMaterial;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Mapeia ProductMaterial → ProductMaterialResponseDto
        mapper.typeMap(ProductMaterial.class, ProductMaterialResponseDto.class)
                .addMapping(src -> src.getRawMaterial().getId(), ProductMaterialResponseDto::setRawMaterialId)
                .addMapping(src -> src.getRawMaterial().getName(), ProductMaterialResponseDto::setRawMaterialName)
                .addMapping(ProductMaterial::getRequiredQuantity, ProductMaterialResponseDto::setQuantityNeeded)
                .addMapping(src -> src.getProduct().getId(), ProductMaterialResponseDto::setProductId);

        // Mapeia Product → ProductResponseDto e já transforma a lista
        mapper.typeMap(Product.class, ProductResponseDto.class)
                .addMapping(src -> src.getMaterial(), ProductResponseDto::setMaterials);

        return mapper;
    }
}
