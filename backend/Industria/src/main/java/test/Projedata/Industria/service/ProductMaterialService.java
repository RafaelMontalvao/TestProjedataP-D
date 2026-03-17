package test.Projedata.Industria.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import test.Projedata.Industria.dto.request.ProductMaterialRequestDto;
import test.Projedata.Industria.dto.response.ProductMaterialResponseDto;
import test.Projedata.Industria.exception.AssociationAlredyExistsException;
import test.Projedata.Industria.exception.AssociationNotFoundException;
import test.Projedata.Industria.exception.MaterialNotFoundException;
import test.Projedata.Industria.exception.ProductNotFoundException;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.model.ProductMaterial;
import test.Projedata.Industria.model.RawMaterial;
import test.Projedata.Industria.repositories.ProductMaterialRepository;
import test.Projedata.Industria.repositories.ProductRepository;
import test.Projedata.Industria.repositories.RawMaterialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductMaterialService {

    private final ProductRepository productRepo;
    private final ProductMaterialRepository ProductMaterialRepo;
    private final RawMaterialRepository materialRepo;
    private final ModelMapper mapper;

    public List<ProductMaterialResponseDto> associateMaterials(Long productId, List<ProductMaterialRequestDto> materialRequests) {
        Optional<Product> productOpt = productRepo.findById(productId);

        if (productOpt.isEmpty())
            throw new ProductNotFoundException();

        Product product = productOpt.get();

        List<ProductMaterialResponseDto> responseList = new ArrayList<>();

        for (ProductMaterialRequestDto req : materialRequests) {
            Optional<RawMaterial> materialOpt = materialRepo.findById(req.getRawMaterialId());
            if (materialOpt.isEmpty()) throw new MaterialNotFoundException();

            if (ProductMaterialRepo.existsByProductIdAndRawMaterialId(productId, req.getRawMaterialId()))
                throw new AssociationAlredyExistsException();

                ProductMaterial association = new ProductMaterial();
                association.setProduct(product);
                association.setRawMaterial(materialOpt.get());
                association.setRequeiredQuantity(req.getQuantityNeeded());

            ProductMaterialRepo.save(association);

            ProductMaterialResponseDto respDto = mapper.map(association, ProductMaterialResponseDto.class);
            responseList.add(respDto);

        }
            return responseList;
    }

    public ProductMaterial updateMaterial(Long id, ProductMaterialRequestDto productMaterialRequestDto){
        Optional<ProductMaterial> material = ProductMaterialRepo.findById(id);

        if(material.isEmpty())
            throw new AssociationNotFoundException();
        ProductMaterial productMaterialBd = material.get();

        if(productMaterialRequestDto.getQuantityNeeded()!= null){
            productMaterialBd.setRequeiredQuantity(productMaterialRequestDto.getQuantityNeeded());
        }
        if(productMaterialRequestDto.getRawMaterialId() != null){
            productMaterialBd.setId(productMaterialRequestDto.getRawMaterialId());
        }
        return  ProductMaterialRepo.save(productMaterialBd);
    }

    public void delete(Long id){
        boolean existAssociation = ProductMaterialRepo.existsById(id);
        if(!existAssociation)
            throw new AssociationNotFoundException();
        ProductMaterialRepo.deleteById(id);
    }

}
