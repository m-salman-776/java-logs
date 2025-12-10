## User Table

| UserID | Username | Email         | AccountBalance |
|--------|----------|---------------|----------------|
| 1      | user1    | user1@email.com | 10000          |
| 2      | user2    | user2@email.com | 5000           |
| ...    | ...      | ...           | ...            |

## Stock Table

| StockID | Symbol | CompanyName | CurrentPrice |
|---------|--------|-------------|--------------|
| 1       | AAPL   | Apple Inc.  | 150.00       |
| 2       | GOOGL  | Alphabet Inc| 2800.00      |
| ...     | ...    | ...         | ...          |

## Watchlist Table

| WatchlistID | UserID | Name        |
|-------------|--------|-------------|
| 1           | 1      | Tech Stocks |
| 2           | 2      | Finance     |
| ...         | ...    | ...         |

## TradeOrder Table

| OrderID | UserID | StockID | OrderType | Quantity | Price   | Status    |
|---------|--------|---------|-----------|----------|---------|-----------|
| 1       | 1      | 1       | Market    | 10       | NULL    | Executed  |
| 2       | 2      | 2       | Limit     | 5        | 2700.00 | Pending   |
| ...     | ...    | ...     | ...       | ...      | ...     | ...       |

## Lot Table

| LotID | UserID | StockID | Quantity | PurchasePrice | PurchaseDate |
|-------|--------|---------|----------|---------------|--------------|
| 1     | 1      | 1       | 5        | 145.00        | 2023-01-15   |
| 2     | 1      | 1       | 10       | 150.00        | 2023-02-05   |
| ...   | ...    | ...     | ...      | ...           | ...          |

## Transaction Table

| TransactionID | UserID | Type     | Amount | Method       | Date       |
|---------------|--------|----------|--------|--------------|------------|
| 1             | 1      | Deposit  | 1000   | Wire Transfer| 2023-01-10 |
| 2             | 1      | Withdraw | 500    | Electronic   | 2023-01-20 |
| ...           | ...    | ...      | ...    | ...          | ...        |

## Report Table

| ReportID | UserID | Type       | Date       | FilePath     |
|----------|--------|------------|------------|--------------|
| 1        | 1      | Quarterly  | 2023-03-31 | /path/to/file|
| 2        | 1      | Yearly     | 2023-12-31 | /path/to/file|
| ...      | ...    | ...        | ...        | ...          |

## Notification Table

| NotificationID | UserID | Message            | Date       | Status |
|----------------|--------|--------------------|------------|--------|
| 1              | 1      | Order Executed     | 2023-01-15 | Read   |
| 2              | 2      | Order Pending      | 2023-01-20 | Unread |
| ...            | ...    | ...                | ...        | ...    |
