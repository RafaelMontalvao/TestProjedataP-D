package test.Projedata.Industria.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.Projedata.Industria.exception.ProductAlredyExistException;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.repositories.ProductRepository;

import java.util.List;

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


}
