// src/main/java/com/stock/stock_average/service/AlphaVantageService.java
package com.stock.stock_average.service;

import com.stock.stock_average.dto.AlphaVantageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AlphaVantageService {

    @Value("${alphavantage.api-key}")
    private String apiKey;

    @Value("${alphavantage.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public AlphaVantageService() {
        this.restTemplate = new RestTemplate();
    }

    public AlphaVantageResponse getMonthlyTimeSeries(String symbol) {
        String url = String.format("%s?function=TIME_SERIES_MONTHLY&symbol=%s&apikey=%s",
                baseUrl, symbol, apiKey);

        log.info("Fetching data from Alpha Vantage for symbol: {}", symbol);

        try {
            AlphaVantageResponse response = restTemplate.getForObject(url, AlphaVantageResponse.class);
            log.info("Successfully fetched data for symbol: {}", symbol);
            return response;
        } catch (Exception e) {
            log.error("Error fetching data from Alpha Vantage for symbol: {}", symbol, e);
            throw new RuntimeException("Failed to fetch data from Alpha Vantage", e);
        }
    }
}
