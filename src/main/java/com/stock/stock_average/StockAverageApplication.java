// src/main/java/com/stock/stock_average/StockAverageApplication.java
package com.stock.stock_average;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockAverageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockAverageApplication.class, args);
    }
}
