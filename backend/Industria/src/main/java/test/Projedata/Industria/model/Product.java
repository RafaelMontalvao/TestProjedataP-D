package test.Projedata.Industria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name="product")
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
