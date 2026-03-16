package test.Projedata.Industria.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.Projedata.Industria.dto.request.ProductUpdateRequestDto;
import test.Projedata.Industria.exception.ProductAlredyExistException;
import test.Projedata.Industria.exception.ProductNotFoundException;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;




    public Product create(Product product) {
        if(productRepository.existsByCode(product.getCode()) || productRepository.existsByName(product.getName()))
            throw new ProductAlredyExistException();
        return productRepository.save(product);
    }



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product updateProduct( Long  id , ProductUpdateRequestDto productRequestDto) {

        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty())
            throw new ProductNotFoundException();
        Product productBd =  product.get();

        if(productRequestDto.getCode() != null && !productRequestDto.getCode().isBlank()) {
            productBd.setCode(productRequestDto.getCode());
        }
        if(productRequestDto.getName() != null && !productRequestDto.getName().isBlank()) {
            productBd.setName(productRequestDto.getName());
        }
        if (productRequestDto.getPrice() != null ){
            productBd.setPrice(productRequestDto.getPrice());
        }
        return productRepository.save(productBd);

    }


    public void delete (Long id){
        boolean existsProduct = productRepository.existsById(id);

        if(!existsProduct)
            throw new ProductNotFoundException();

        productRepository.deleteById(id);


    }
}
