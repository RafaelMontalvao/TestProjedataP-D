package test.Projedata.Industria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import test.Projedata.Industria.dto.request.RawMaterialRequestDto;
import test.Projedata.Industria.dto.response.RawMaterialResponseDto;
import test.Projedata.Industria.dto.request.RawMaterialUpdateRequestDto;
import test.Projedata.Industria.model.RawMaterial;
import test.Projedata.Industria.service.RawMaterialService;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/raw_material")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class RawMaterialController {

    private final ModelMapper mapper;
    private final RawMaterialService service;


    @PostMapping("/create")
    public ResponseEntity<RawMaterialResponseDto> createMaterial(@RequestBody @Valid RawMaterialRequestDto request) {
        RawMaterial material = mapper.map(request, RawMaterial.class);
        material = service.createRawMaterial(material);
        RawMaterialResponseDto responseDto = mapper.map(material, RawMaterialResponseDto.class);
        URI uri = UriComponentsBuilder
                .fromPath("/api/raw_material/{id}")
                .buildAndExpand(material.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDto);

    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<RawMaterialResponseDto> updateRawMaterial(@PathVariable Long id, @RequestBody @Valid RawMaterialUpdateRequestDto request) {
        RawMaterial  material = mapper.map(request, RawMaterial.class);
        material = service.updateRawMaterial(id, request);
        RawMaterialResponseDto responseDto = mapper.map(material, RawMaterialResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/get")
    public ResponseEntity<List<RawMaterial>> getAllMaterials() {
        return ResponseEntity.ok(service.getAllMaterials());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RawMaterialResponseDto> getMaterialById(@PathVariable Long id) {
        RawMaterial material = service.getMaterialById(id);
        RawMaterialResponseDto responseDto = mapper.map(material, RawMaterialResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }


}
