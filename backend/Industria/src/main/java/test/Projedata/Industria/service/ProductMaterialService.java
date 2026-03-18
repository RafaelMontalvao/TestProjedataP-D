package test.Projedata.Industria.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final ProductMaterialRepository productMaterialRepo;
    private final RawMaterialRepository materialRepo;
    private final ModelMapper mapper;

    @Transactional
    public List<ProductMaterialResponseDto> associateMaterials(Long productId, List<ProductMaterialRequestDto> materialRequests) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());


        productMaterialRepo.deleteByProductId(productId);

        List<ProductMaterialResponseDto> responseList = new ArrayList<>();

        for (ProductMaterialRequestDto req : materialRequests) {
            RawMaterial material = materialRepo.findById(req.getRawMaterialId())
                    .orElseThrow(() -> new MaterialNotFoundException());


            ProductMaterial association = new ProductMaterial();
            association.setProduct(product);
            association.setRawMaterial(material);
            association.setRequiredQuantity(req.getQuantityNeeded());

            productMaterialRepo.save(association);

            responseList.add(mapper.map(association, ProductMaterialResponseDto.class));
        }
        return responseList;
    }

    @Transactional
    public ProductMaterial updateMaterial(Long id, ProductMaterialRequestDto productMaterialRequestDto){
        Optional<ProductMaterial> material = productMaterialRepo.findById(id);

        if(material.isEmpty())
            throw new AssociationNotFoundException();
        ProductMaterial productMaterialBd = material.get();

        if(productMaterialRequestDto.getQuantityNeeded()!= null){
            productMaterialBd.setRequiredQuantity(productMaterialRequestDto.getQuantityNeeded());
        }

        return  productMaterialRepo.save(productMaterialBd);
    }


    @Transactional
    public void delete(Long id){
        boolean existAssociation = productMaterialRepo.existsById(id);
        if(!existAssociation)
            throw new AssociationNotFoundException();
        productMaterialRepo.deleteById(id);
    }


    @Transactional
    public void deleteByProduct(Long id){
        boolean existProduct = productRepo.existsById(id);
        if(!existProduct)
            throw new ProductNotFoundException();

        productMaterialRepo.deleteByProductId(id);
    }

}
