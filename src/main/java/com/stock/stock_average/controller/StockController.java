// src/main/java/com/stock/stock_average/controller/StockController.java
package com.stock.stock_average.controller;

import com.stock.stock_average.dto.StockSummaryResponse;
import com.stock.stock_average.service.StockDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockDataService stockDataService;

    @GetMapping("/{symbol}/summary")
    public ResponseEntity<StockSummaryResponse> getMonthlyStockSummary(
            @PathVariable String symbol,
            @RequestParam int year,
            @RequestParam int month) {

        StockSummaryResponse summary = stockDataService.getMonthlyStockSummary(symbol, year, month);
        return ResponseEntity.ok(summary);
    }

    @PostMapping("/{symbol}/fetch")
    public ResponseEntity<String> fetchStockData(@PathVariable String symbol) {
        stockDataService.fetchAndStockData(symbol);
        return ResponseEntity.ok("Stock data fetch initiated for " + symbol);
    }
}
