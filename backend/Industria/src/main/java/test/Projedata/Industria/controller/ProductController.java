package test.Projedata.Industria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import test.Projedata.Industria.dto.request.ProductRequestDto;
import test.Projedata.Industria.dto.request.ProductUpdateRequestDto;
import test.Projedata.Industria.dto.response.ProductResponseDto;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("products")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class ProductController {

    private final ModelMapper mapper;
    private final ProductService productService;



    @PostMapping("/create")
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

    @GetMapping("/get")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {

        List<ProductResponseDto> response = productService
                .getAllProducts()
                .stream()
                .map(product -> mapper.map(product, ProductResponseDto.class))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResponseDto productResponseDto = mapper.map(product, ProductResponseDto.class);
        return ResponseEntity.ok(productResponseDto);

    }





    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id, @RequestBody @Valid ProductUpdateRequestDto productRequestDto){
        Product  product = mapper.map(productRequestDto, Product.class);
        product = productService.updateProduct(id,productRequestDto);
        ProductResponseDto productResponseDto = mapper.map(product, ProductResponseDto.class);
        return ResponseEntity.ok(productResponseDto);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
