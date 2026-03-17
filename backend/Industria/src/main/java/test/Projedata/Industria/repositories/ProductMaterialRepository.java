package test.Projedata.Industria.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import test.Projedata.Industria.model.ProductMaterial;

import java.util.List;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductMaterial pm WHERE pm.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);

    List<ProductMaterial> findByProductId(Long productId);

}


