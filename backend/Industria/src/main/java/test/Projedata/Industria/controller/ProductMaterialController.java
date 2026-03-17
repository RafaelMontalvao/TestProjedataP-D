package test.Projedata.Industria.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.Projedata.Industria.dto.request.ProductMaterialListRequestDto;
import test.Projedata.Industria.dto.request.ProductMaterialRequestDto;
import test.Projedata.Industria.dto.response.ProductMaterialResponseDto;
import test.Projedata.Industria.model.ProductMaterial;
import test.Projedata.Industria.service.ProductMaterialService;

import java.util.List;

@RestController
@RequestMapping("product_material")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class ProductMaterialController {

    private final ModelMapper mapper;
    private final ProductMaterialService service;





    @PostMapping("/{productId}/materials")
    public ResponseEntity<List<ProductMaterialResponseDto>> associateMaterials(
            @PathVariable Long productId,
            @RequestBody List<@Valid ProductMaterialRequestDto> request) {

        List<ProductMaterialResponseDto> response = service.associateMaterials(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/edit/{id}")
    public  ResponseEntity<ProductMaterialResponseDto> updateMaterial(@PathVariable Long id, @RequestBody @Valid ProductMaterialRequestDto productRequestDto) {
        ProductMaterial material = mapper.map(productRequestDto, ProductMaterial.class);
        material = service.updateMaterial(id,productRequestDto);
        ProductMaterialResponseDto responseDto = mapper.map(material, ProductMaterialResponseDto.class);

        return ResponseEntity.ok(responseDto);

    }






}
