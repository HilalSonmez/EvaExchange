package com.hilal.evaexchangeproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String symbol;


    @Column(nullable = false)
    private BigDecimal purchasePrice= BigDecimal.ZERO;


    @Column(nullable = false)
    private BigDecimal salePrice= BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer quantity=0;

    @Column(nullable = false)
    private Integer saleQuantity=0;
    @Column
    private Integer purchaseQuantity=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolioId", nullable = false)
    private Portfolio portfolio;

}
