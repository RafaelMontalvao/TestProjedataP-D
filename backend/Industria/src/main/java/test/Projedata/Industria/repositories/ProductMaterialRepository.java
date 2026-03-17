package test.Projedata.Industria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import test.Projedata.Industria.model.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {

    boolean existsByProductIdAndRawMaterialId(Long productId, Long rawMaterialId);
    boolean existsByProductId(Long productId);
    boolean existsByRawMaterialId(Long rawMaterialId );
}
