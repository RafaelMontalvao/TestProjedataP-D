package test.Projedata.Industria.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.Projedata.Industria.dto.request.RawMaterialUpdateRequestDto;
import test.Projedata.Industria.exception.*;
import test.Projedata.Industria.model.RawMaterial;
import test.Projedata.Industria.repositories.ProductMaterialRepository;
import test.Projedata.Industria.repositories.RawMaterialRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RawMaterialService {

    private final RawMaterialRepository repo;
    private final ProductMaterialRepository productRepository;

    public RawMaterial createRawMaterial(RawMaterial rawMaterial) {
        if (repo.existsByName(rawMaterial.getName())) {
            throw new MaterialAlredyExistsException();        }

        return repo.save(rawMaterial);
    }

    public RawMaterial updateRawMaterial(Long id, RawMaterialUpdateRequestDto request) {
        Optional<RawMaterial> rawMaterialOptional = repo.findById(id);
        if(!rawMaterialOptional.isPresent()) {
            throw new MaterialNotFoundException();
        }
        RawMaterial rawMaterial = rawMaterialOptional.get();
        if(request.getName() != null && !request.getName().isBlank()) {
            rawMaterial.setName(request.getName());
        }
        if(request.getStockQuantity() != null ) {
            rawMaterial.setStockQuantity(request.getStockQuantity());
        }
        return repo.save(rawMaterial);
    }

    public List<RawMaterial> getAllMaterials() {
        return repo.findAll();
    }

    public RawMaterial getMaterialById(Long id) {
        Optional<RawMaterial> rawMaterialOptional = repo.findById(id);

        if(rawMaterialOptional.isEmpty())
            throw new MaterialNotFoundException();

        RawMaterial rawMaterial = rawMaterialOptional.get();
        return rawMaterial;

    }

    public void delete (Long id){
        boolean existsMaterial = repo.existsById(id);
        boolean existAssociation = productRepository.existsByRawMaterialId(id);
        if(!existsMaterial)
            throw new ProductNotFoundException();
        if(existAssociation)
            throw new HasAssociationException();
        repo.deleteById(id);


    }

}
