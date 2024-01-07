package com.hilal.evaexchangeproject.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShareDto {

    @NotNull
    private Long portfolioId;
    @NotNull
    @Pattern(regexp = "[A-Z]{3}", message = "Share symbol must be three uppercase letters")
    private String symbol;

    @NotNull
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private BigDecimal purchasePrice= BigDecimal.ZERO;
    @NotNull
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private BigDecimal salePrice= BigDecimal.ZERO;

    @NotNull
    @DecimalMin(value = "0", message = "Quantity must be at least 0")
    private Integer saleQuantity=0;

    @NotNull
    @DecimalMin(value = "0", message = "Quantity must be at least 0")
    private Integer quantity=0;

    private Integer purchaseQuantity=0;


    public ShareDto(String symbol, BigDecimal purchasePrice, BigDecimal salePrice, Integer saleQuantity, Integer purchaseQuantity, Long portfolioId) {

        this.symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleQuantity = saleQuantity;
        this.purchaseQuantity = purchaseQuantity;
        this.portfolioId = portfolioId;
    }
}
