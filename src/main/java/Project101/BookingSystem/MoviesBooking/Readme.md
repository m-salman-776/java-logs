TABLE Cinema (
id INT PRIMARY KEY,
name VARCHAR,
address VARCHAR
)

TABLE Hall (
id INT PRIMARY KEY,
cinemaId INT REFERENCES Cinema(id),
name VARCHAR
)

TABLE Movie (
id INT PRIMARY KEY,
name VARCHAR
)

TABLE Seat (
id INT PRIMARY KEY,
hallId INT REFERENCES Hall(id),
seatNumber VARCHAR
)

TABLE Show (
id INT PRIMARY KEY,
hallId INT REFERENCES Hall(id),
movieId INT REFERENCES Movie(id),
showTime TIMESTAMP
)

TABLE ShowSeat (
id INT PRIMARY KEY,
showId INT REFERENCES Show(id),
seatId INT REFERENCES Seat(id),
status ENUM('available', 'reserved', 'booked'),
version INT
)

Booking
- id
- userId
- showSeat :[]
- status