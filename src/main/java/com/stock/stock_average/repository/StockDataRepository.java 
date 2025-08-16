// src/main/java/com/stock/stock_average/repository/StockDataRepository.java
package com.stock.stock_average.repository;

import com.stock.stock_average.entity.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockDataRepository extends JpaRepository<StockData, Long> {

    boolean existsBySymbolAndDate(String symbol, LocalDate date);

    @Query("SELECT s FROM StockData s WHERE s.symbol = :symbol AND s.date BETWEEN :startDate AND :endDate ORDER BY s.date")
    List<StockData> findBySymbolAndDateBetween(@Param("symbol") String symbol,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM StockData s WHERE s.symbol = :symbol AND YEAR(s.date) = :year AND MONTH(s.date) = :month")
    List<StockData> findBySymbolAndYearAndMonth(@Param("symbol") String symbol,
                                                @Param("year") int year,
                                                @Param("month") int month);
}
