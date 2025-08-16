// src/main/java/com/stock/stock_average/dto/AlphaVantageResponse.java
package com.stock.stock_average.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class AlphaVantageResponse {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Monthly Time Series")
    private Map<String, MonthlyData> monthlyTimeSeries;

    @Data
    public static class MetaData {
        @JsonProperty("1. Information")
        private String information;

        @JsonProperty("2. Symbol")
        private String symbol;

        @JsonProperty("3. Last Refreshed")
        private String lastRefreshed;

        @JsonProperty("4. Time Zone")
        private String timeZone;
    }

    @Data
    public static class MonthlyData {
        @JsonProperty("1. open")
        private String open;

        @JsonProperty("2. high")
        private String high;

        @JsonProperty("3. low")
        private String low;

        @JsonProperty("4. close")
        private String close;

        @JsonProperty("5. volume")
        private String volume;
    }
}
