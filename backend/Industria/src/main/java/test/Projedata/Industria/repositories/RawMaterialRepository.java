package test.Projedata.Industria.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import test.Projedata.Industria.model.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

    boolean existsByName(String name);

}
