package test.Projedata.Industria.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.Projedata.Industria.component.ProductAvailabilityMapper;
import test.Projedata.Industria.dto.request.ProductUpdateRequestDto;
import test.Projedata.Industria.dto.response.ProductAvailabilityDto;
import test.Projedata.Industria.exception.ProductAlredyExistException;
import test.Projedata.Industria.exception.ProductNotFoundException;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.model.ProductMaterial;
import test.Projedata.Industria.model.RawMaterial;
import test.Projedata.Industria.repositories.ProductRepository;
import test.Projedata.Industria.repositories.RawMaterialRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository materialRepository;

    private final ProductAvailabilityMapper productAvailabilityMapper;




    public Product create(Product product) {
        if(productRepository.existsByCode(product.getCode()) || productRepository.existsByName(product.getName()))
            throw new ProductAlredyExistException();
        return productRepository.save(product);
    }



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty())
            throw new ProductNotFoundException();
        Product product = productOptional.get();
        return product;

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

    public List<ProductAvailabilityDto> getProductsProducible() {
        List<Product> products = productRepository.findAll();
        Map<Long, BigDecimal> stock = createFakeStock();

        // 1. Ordenação por Rentabilidade (Densidade de Valor)
        // Filtramos produtos sem materiais e ordenamos do mais rentável para o menos
        List<Product> prioritizedProducts = products.stream()
                .filter(p -> p.getMaterial() != null && !p.getMaterial().isEmpty())
                .sorted((p1, p2) -> calculateValueDensity(p2).compareTo(calculateValueDensity(p1)))
                .toList();

        List<ProductAvailabilityDto> result = new ArrayList<>();

        // 2. Loop Linear - Processa cada produto uma única vez
        for (Product product : prioritizedProducts) {
            BigDecimal qty = calculateMaxQuantity(product, stock);

            if (qty.compareTo(BigDecimal.ZERO) > 0) {
                // Usa o seu mapper para converter para DTO
                result.add(productAvailabilityMapper.toDto(product, qty));

                // Abate o que foi "usado" do estoque local para o próximo produto
                consume(product, qty, stock);
            }
        }

        return result;
    }

    private BigDecimal calculateValueDensity(Product p) {
        // Soma quanto de material TOTAL esse produto gasta
        BigDecimal totalNeeded = p.getMaterial().stream()
                .map(m -> m.getRequiredQuantity()) // Nome conforme seu código anterior
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalNeeded.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        // Preço / Total de Materiais = Quanto cada unidade de material rende
        return p.getPrice().divide(totalNeeded, 8, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMaxQuantity(Product product, Map<Long, BigDecimal> stock) {
        BigDecimal minPossible = null;

        for (ProductMaterial m : product.getMaterial()) {
            // Pega o ID do material bruto
            Long materialId = m.getRawMaterial().getId();
            BigDecimal available = stock.getOrDefault(materialId, BigDecimal.ZERO);
            BigDecimal required = m.getRequiredQuantity();

            if (required.compareTo(BigDecimal.ZERO) <= 0) continue;

            // Divisão inteira: disponível / necessário
            BigDecimal possible = available.divide(required, 0, RoundingMode.FLOOR);

            if (minPossible == null || possible.compareTo(minPossible) < 0) {
                minPossible = possible;
            }
        }
        return (minPossible == null) ? BigDecimal.ZERO : minPossible;
    }

    private void consume(Product product, BigDecimal qty, Map<Long, BigDecimal> stock) {
        for (ProductMaterial m : product.getMaterial()) {
            Long id = m.getRawMaterial().getId();
            BigDecimal used = m.getRequiredQuantity().multiply(qty);

            BigDecimal current = stock.get(id);
            stock.put(id, current.subtract(used));
        }
    }


    private Map<Long, BigDecimal> createFakeStock() {

        Map<Long, BigDecimal> stock = new HashMap<>();

        for (RawMaterial m : materialRepository.findAll()) {
            stock.put(m.getId(), m.getStockQuantity());
        }

        return stock;
    }















}







