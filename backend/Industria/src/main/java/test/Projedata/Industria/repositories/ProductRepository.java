package test.Projedata.Industria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.Projedata.Industria.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);
    boolean existsByName (String name);
}
