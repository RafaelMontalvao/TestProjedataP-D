package test.Projedata.Industria.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String code;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductMaterial > material ;
}
