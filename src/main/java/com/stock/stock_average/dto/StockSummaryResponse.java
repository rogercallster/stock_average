// src/main/java/com/stock/stock_average/dto/StockSummaryResponse.java
package com.stock.stock_average.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockSummaryResponse {
    private String symbol;
    private int year;
    private int month;
    private BigDecimal monthlyAverage;
    private BigDecimal monthlyHighest;
    private BigDecimal monthlyLowest;
    private Long averageVolume;
}