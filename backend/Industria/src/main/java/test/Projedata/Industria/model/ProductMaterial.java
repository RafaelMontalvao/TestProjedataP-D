package test.Projedata.Industria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;



    @Data
    @Entity
    @Table(name="product_material")
    public class ProductMaterial {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "product_id")
        private Product product;

        @ManyToOne
        @JoinColumn(name="raw_material_id")
        private RawMaterial rawMaterial;

        private BigDecimal requiredQuantity;

    }
