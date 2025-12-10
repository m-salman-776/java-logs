## Vehicle Table

| VehicleID | Type     | Barcode | ParkingStall | Status   |
|-----------|----------|---------|--------------|----------|
| 1         | Car      | ABC123  | A1           | Available|
| 2         | Truck    | XYZ456  | B3           | Rented   |
| 3         | SUV      | DEF789  | C2           | Available|
| ...       | ...      | ...     | ...          | ...      |

## Member Table

| MemberID | Name     | Email           | Phone         |
|----------|----------|-----------------|---------------|
| 101      | John Doe | john@example.com| +1234567890   |
| 102      | Jane Doe | jane@example.com| +1987654321   |
| ...      | ...      | ...             | ...           |

## Reservation Table

| ReservationID | MemberID | VehicleID | PickupDate  | ReturnDate  | LateFeeID |
|---------------|----------|-----------|-------------|-------------|-----------|
| 201           | 101      | 1         | 2024-02-21  | 2024-02-25  | 301       |
| 202           | 102      | 3         | 2024-02-22  | 2024-02-26  | NULL      |
| ...           | ...      | ...       | ...         | ...         | ...       |

## LateFee Table

| LateFeeID | ReservationID | FeeAmount | PaymentStatus |
|-----------|---------------|-----------|---------------|
| 301       | 201           | 20.00     | Unpaid        |
| 302       | ...           | ...       | ...           |

## Equipment Table

| EquipmentID | Name         | Description   | RentalPrice |
|-------------|--------------|---------------|-------------|
| 401         | Navigation   | GPS navigator | 10.00       |
| 402         | Child Seat   | Baby car seat | 15.00       |
| ...         | ...          | ...           | ...         |

## Service Table

| ServiceID | Name               | Description               | Price   |
|-----------|--------------------|---------------------------|---------|
| 501       | Roadside Assistance| Vehicle towing assistance| 25.00   |
| 502       | Wifi               | In-car WiFi hotspot       | 5.00/day|
| ...       | ...                | ...                       | ...     |

## VehicleLog Table

| LogID | VehicleID | Action           | Description               | Timestamp          |
|-------|-----------|------------------|---------------------------|--------------------|
| 601   | 1         | Reservation Start| Reserved by Member ID 101 | 2024-02-21 09:00:00|
| 602   | 1         | Reservation End  | Returned by Member ID 101 | 2024-02-25 15:00:00|
| ...   | ...       | ...              | ...                       | ...                |
