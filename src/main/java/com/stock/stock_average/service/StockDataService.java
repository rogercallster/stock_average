// src/main/java/com/stock/stock_average/service/StockDataService.java
package com.stock.stock_average.service;

import com.stock.stock_average.dto.AlphaVantageResponse;
import com.stock.stock_average.dto.StockSummaryResponse;
import com.stock.stock_average.entity.StockData;
import com.stock.stock_average.repository.StockDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockDataService {

    private final StockDataRepository stockDataRepository;
    private final AlphaVantageService alphaVantageService;

    @Scheduled(cron = "0 0 2 * * ?") // Run daily at 2 AM
    @Transactional
    public void fetchAndStoreStockData() {
        log.info("Starting scheduled stock data fetch for IBM");
        fetchAndStoreStockData("IBM");
    }

    public void fetchAndStoreStockData(String symbol) {
        try {
            AlphaVantageResponse response = alphaVantageService.getMonthlyTimeSeries(symbol);

            if (response.getMonthlyTimeSeries() == null) {
                log.warn("No monthly time series data found for symbol: {}", symbol);
                return;
            }

            int savedCount = 0;
            for (Map.Entry<String, AlphaVantageResponse.MonthlyData> entry :
                    response.getMonthlyTimeSeries().entrySet()) {

                LocalDate date = LocalDate.parse(entry.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Check if data already exists
                if (!stockDataRepository.existsBySymbolAndDate(symbol, date)) {
                    StockData stockData = convertToStockData(symbol, date, entry.getValue());
                    stockDataRepository.save(stockData);
                    savedCount++;
                }
            }

            log.info("Successfully saved {} new records for symbol: {}", savedCount, symbol);

        } catch (Exception e) {
            log.error("Error fetching and storing stock data for symbol: {}", symbol, e);
            throw new RuntimeException("Failed to fetch and store stock data", e);
        }
    }

    private StockData convertToStockData(String symbol, LocalDate date,
                                         AlphaVantageResponse.MonthlyData monthlyData) {
        StockData stockData = new StockData();
        stockData.setSymbol(symbol);
        stockData.setDate(date);
        stockData.setOpenPrice(new BigDecimal(monthlyData.getOpen()));
        stockData.setHighPrice(new BigDecimal(monthlyData.getHigh()));
        stockData.setLowPrice(new BigDecimal(monthlyData.getLow()));
        stockData.setClosePrice(new BigDecimal(monthlyData.getClose()));
        stockData.setVolume(Long.parseLong(monthlyData.getVolume()));
        return stockData;
    }

    public StockSummaryResponse getMonthlyStockSummary(String symbol, int year, int month) {
        List<StockData> monthlyData = stockDataRepository.findBySymbolAndYearAndMonth(symbol, year, month);

        if (monthlyData.isEmpty()) {
            throw new RuntimeException("No data found for " + symbol + " in " + year + "-" + month);
        }

        // Calculate averages and extremes
        BigDecimal avgClose = monthlyData.stream()
                .map(StockData::getClosePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(monthlyData.size()), 2, RoundingMode.HALF_UP);

        BigDecimal highest = monthlyData.stream()
                .map(StockData::getHighPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal lowest = monthlyData.stream()
                .map(StockData::getLowPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        Long avgVolume = (long) monthlyData.stream()
                .mapToLong(StockData::getVolume)
                .average()
                .orElse(0.0);

        return new StockSummaryResponse(symbol, year, month, avgClose, highest, lowest, avgVolume);
    }
}
