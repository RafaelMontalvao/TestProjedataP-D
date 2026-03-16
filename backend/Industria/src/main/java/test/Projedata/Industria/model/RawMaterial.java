package test.Projedata.Industria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


    @Data
    @Entity
    @Table(name="raw_material")
    @NoArgsConstructor
    @AllArgsConstructor
    public class RawMaterial {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Integer id;


        private String name;

        private BigDecimal stockQuantity;

    }
