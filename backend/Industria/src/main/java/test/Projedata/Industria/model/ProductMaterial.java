package test.Projedata.Industria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;



    @Data
    @Entity
    @Table(name="product_material")
    @AllArgsConstructor
    @NoArgsConstructor
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




        public ProductMaterial(Product product, RawMaterial rawMaterial, BigDecimal requiredQuantity) {
            this.product = product;
            this.rawMaterial = rawMaterial;
            this.requiredQuantity = requiredQuantity;
        }
    }
