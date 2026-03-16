package test.Projedata.Industria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import test.Projedata.Industria.dto.request.ProductRequestDto;
import test.Projedata.Industria.dto.response.ProductResponseDto;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.service.ProductService;

import java.net.URI;

@RestController
@RequestMapping("products")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class ProductController {

    private final ModelMapper mapper;
    private final ProductService productService;




    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto){
        Product product = mapper.map(productRequestDto, Product.class);
        product = productService.create(product);
        ProductResponseDto productResponseDto = mapper.map(product, ProductResponseDto.class);
        URI uri = UriComponentsBuilder
                .fromPath("/api/products/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri).body(productResponseDto);
    }

}
