package test.Projedata.Industria.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.Projedata.Industria.exception.MaterialAlredyExistsException;
import test.Projedata.Industria.model.RawMaterial;
import test.Projedata.Industria.repositories.RawMaterialRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RawMaterialService {

    private final RawMaterialRepository repo;

    public RawMaterial createRawMaterial(RawMaterial rawMaterial) {
        if (repo.existsByName(rawMaterial.getName())) {
            throw new MaterialAlredyExistsException();        }

        return repo.save(rawMaterial);
    }



}
