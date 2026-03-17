package test.Projedata.Industria.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import test.Projedata.Industria.component.ProductAvailabilityMapper;
import test.Projedata.Industria.dto.response.ProductAvailabilityDto;
import test.Projedata.Industria.model.Product;
import test.Projedata.Industria.model.ProductMaterial;
import test.Projedata.Industria.model.RawMaterial;
import test.Projedata.Industria.repositories.ProductRepository;
import test.Projedata.Industria.repositories.RawMaterialRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductMaterialServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository materialRepository;

    @Mock
    private ProductAvailabilityMapper productAvailabilityMapper;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setup() {
        // Isso protege TODOS os testes. Se o service chamar o mapper,
        // ele retorna um DTO válido em vez de null.
        when(productAvailabilityMapper.toDto(any(Product.class), any(BigDecimal.class)))
                .thenAnswer(invocation -> {
                    Product p = invocation.getArgument(0);
                    BigDecimal q = invocation.getArgument(1);
                    ProductAvailabilityDto dto = new ProductAvailabilityDto();
                    dto.setProductName(p.getName());
                    dto.setProducibleQuantity(q);
                    return dto;
                });
    }

    // Helper para criar materiais rapidamente nos testes
    private RawMaterial createMaterial(Long id, String qty) {
        RawMaterial m = new RawMaterial();
        m.setId(id);
        m.setStockQuantity(new BigDecimal(qty));
        return m;
    }

    @Test
    @DisplayName("Scenario 1: Should calculate production limited by the limit material")
    void testLimitingMaterial() {
        // Arrange
        RawMaterial couro = createMaterial(1L, "10.00"); // Daria 5 sapatos
        RawMaterial cola = createMaterial(2L, "4.00");   // Daria 2 sapatos (Gargalo)

        Product sapato = new Product();
        sapato.setName("Sapato");
        sapato.setPrice(new BigDecimal("200.00"));


        sapato.setMaterial(List.of(
                new ProductMaterial(sapato, couro, new BigDecimal("2.00")),
                new ProductMaterial(sapato, cola, new BigDecimal("2.00"))
        ));

        when(materialRepository.findAll()).thenReturn(List.of(couro, cola));
        when(productRepository.findAll()).thenReturn(List.of(sapato));


        ProductAvailabilityDto dto = new ProductAvailabilityDto();
        dto.setProductName("Sapato");
        dto.setProducibleQuantity(new BigDecimal("2"));
        when(productAvailabilityMapper.toDto(eq(sapato), eq(new BigDecimal("2")))).thenReturn(dto);

        // Act
        List<ProductAvailabilityDto> result = service.getProductsProducible();

        // Assert
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getProducibleQuantity().intValue());
        verify(productAvailabilityMapper).toDto(any(), eq(new BigDecimal("2")));
    }

    @Test
    @DisplayName("Scenario 2: Should prioritize profit (Value Density) when stock is contested")
    void testPriorityProfit() {
        // Arrange
        RawMaterial metal = createMaterial(1L, "10.00");


        Product prodA = new Product();
        prodA.setName("Produto A");
        prodA.setPrice(new BigDecimal("1000.00"));
        prodA.setMaterial(List.of(new ProductMaterial(prodA, metal, new BigDecimal("10.00"))));


        Product prodB = new Product();
        prodB.setName("Produto B");
        prodB.setPrice(new BigDecimal("200.00"));
        prodB.setMaterial(List.of(new ProductMaterial(prodB, metal, new BigDecimal("1.00"))));

        when(materialRepository.findAll()).thenReturn(List.of(metal));
        when(productRepository.findAll()).thenReturn(List.of(prodA, prodB));

        // Act
        service.getProductsProducible();

        // Assert

        verify(productAvailabilityMapper, times(1)).toDto(eq(prodB), eq(new BigDecimal("10")));
        verify(productAvailabilityMapper, never()).toDto(eq(prodA), any());
    }

    @Test
    @DisplayName("Scenario 3: Should return empty list if there is insufficient stock for even one unit")
    void testStockIsInsufficient() {
        // Arrange
        RawMaterial madeira = createMaterial(1L, "1.00");
        Product mesa = new Product();
        mesa.setPrice(new BigDecimal("50.00"));
        mesa.setMaterial(List.of(new ProductMaterial(mesa, madeira, new BigDecimal("5.00"))));

        when(materialRepository.findAll()).thenReturn(List.of(madeira));
        when(productRepository.findAll()).thenReturn(List.of(mesa));

        // Act
        List<ProductAvailabilityDto> result = service.getProductsProducible();

        // Assert
        assertTrue(result.isEmpty());
        verify(productAvailabilityMapper, never()).toDto(any(), any());
    }

    @Test
    @DisplayName("Scenario 4: Should maximize total profit by producing more profitable items first")
    void testMaximizeTotalProfitWithContestedMaterials() {
        RawMaterial aco = createMaterial(1L, "12.00");
        when(materialRepository.findAll()).thenReturn(List.of(aco));

        Product lux = new Product();
        lux.setName("Luxo");
        lux.setPrice(new BigDecimal("1000.00"));
        lux.setMaterial(List.of(new ProductMaterial(lux, aco, new BigDecimal("10.00"))));

        Product popular = new Product();
        popular.setName("Popular");
        popular.setPrice(new BigDecimal("300.00"));
        popular.setMaterial(List.of(new ProductMaterial(popular, aco, new BigDecimal("2.00"))));

        when(productRepository.findAll()).thenReturn(List.of(lux, popular));

        List<ProductAvailabilityDto> result = service.getProductsProducible();

        assertNotNull(result);
        boolean madePopular = result.stream()
                .anyMatch(p -> "Popular".equals(p.getProductName())
                        && p.getProducibleQuantity().compareTo(new BigDecimal("6")) == 0);

        boolean madeLux = result.stream().anyMatch(p -> "Luxo".equals(p.getProductName()));

        assertTrue(madePopular);
        assertFalse(madeLux);
    }

}