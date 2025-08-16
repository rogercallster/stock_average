# Stock Average Application - Setup and Usage Guide

## 10. Running the Application

### Start PostgreSQL:
```bash
docker-compose up -d
```

Logs as below 
``
docker-compose up -d
WARN[0000] ~/dev/java/stock_average/docker-compose.yml: the attribute `version` is obsolete, it will be ignored, please remove it to avoid potential confusion
[+] Running 12/15
⠋ postgres [⣿⣿⣿⣤⣿⣿⡀⣿⣿⣿⣿⣿⣿⣿] 48.23MB / 155.7MB Pulling``

### Get Alpha Vantage API Key:
- Sign up at [www.alphavantage.co](https://www.alphavantage.co)
- Replace `YOUR_API_KEY_HERE` in `application.yml`

### Run the application:
```bash
./gradlew bootRun
```

## 11. API Usage Examples

### Fetch stock data manually:
```bash
curl -X POST http://localhost:8080/api/stocks/IBM/fetch
```

### Get monthly summary:
```bash
curl "http://localhost:8080/api/stocks/IBM/summary?year=2024&month=12"
```

### Example JSON Response:
```json
{
  "symbol": "IBM",
  "year": 2024,
  "month": 12,
  "monthlyAverage": 185.50,
  "monthlyHighest": 195.25,
  "monthlyLowest": 175.80,
  "averageVolume": 3500000
}
```

## Application Features

The application will automatically fetch IBM stock data daily at 2 AM and store it in PostgreSQL. The REST API provides monthly summaries with average, highest, lowest prices, and average volume.