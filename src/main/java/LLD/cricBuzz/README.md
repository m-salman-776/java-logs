# Cricinfo Database Schema

## Team Table

| TeamID | Name       |
|--------|------------|
| 1      | Team A     |
| 2      | Team B     |
| 3      | Team C     |
| 4      | Team D     |

## Player Table

| PlayerID | Name       |
|----------|------------|
| 101      | Player 1   |
| 102      | Player 2   |
| 103      | Player 3   |
| 201      | Player 4   |

## Tournament Table

| TournamentID | Name          |
|--------------|---------------|
| 1            | Tournament A  |
| 2            | Tournament B  |

## Match Table

| MatchID | TournamentID | MatchType | DateTime           | Team1 | Team2 |
|---------|--------------|-----------|--------------------|-------|-------|
| 1       | 1            | ODI       | 2023-01-05 10:00:00| 1     | 2     |
| 2       | 1            | ODI       | 2023-01-08 10:00:00| 2     | 3     |
| 3       | 1            | ODI       | 2023-01-12 10:00:00| 1     | 3     |
| 4       | 2            | T20       | 2023-01-20 15:00:00| 1     | 3     |
| 5       | 2            | T20       | 2023-01-25 15:00:00| 2     | 3     |
| 6       | 2            | T20       | 2023-01-28 15:00:00| 1     | 2     |

## Innings Table

| InningID | MatchID | TeamID | RunsScored | WicketsLost | OversPlayed |
|----------|---------|--------|------------|-------------|-------------|
| 1        | 1       | 1      | 320        | 5           | 50          |
| 2        | 1       | 2      | 280        | 8           | 50          |
| 3        | 2       | 3      | 200        | 4           | 20          |
| 4        | 2       | 4      | 180        | 9           | 20          |

## Over Table

| OverID | MatchID | InningID | OverNumber | BowlerID | Ball1Outcome | ... | Ball6Outcome |
|--------|---------|----------|------------|----------|--------------|-----|--------------|
| 1      | 1       | 1        | 1          | 101      | 4            | ... | W            |
| 2      | 1       | 1        | 2          | 102      | 0            | ... | 1            |
| ...    | ...     | ...      | ...        | ...      | ...          | ... | ...          |
| 599    | 2       | 4        | 99         | 401      | 1            | ... | 0            |
| 600    | 2       | 4        | 100        | 402      | 0            | ... | 0            |

## Over Table

| OverID | MatchID | InningID | OverNumber | BowlerID | BallOutcomes (JSONB)                                                                                                              |
|--------|---------|----------|------------|----------|-----------------------------------------------------------------------------------------------------------------------------------|
| 1      | 1       | 1        | 1          | 101      | {"Ball1": {"Runs": 4, "Type": "Boundary"}, "Ball2": {"Runs": null, "Type": null}, "Ball3": {"Runs": null, "Type": null}, "Ball4": {"Runs": null, "Type": null}, "Ball5": {"Runs": null, "Type": null}, "Ball6": {"Runs": null, "Type": "Wicket"}} |
| 2      | 1       | 1        | 2          | 102      | {"Ball1": {"Runs": 0, "Type": "Dot"}, "Ball2": {"Runs": null, "Type": null}, "Ball3": {"Runs": null, "Type": null}, "Ball4": {"Runs": null, "Type": null}, "Ball5": {"Runs": null, "Type": null}, "Ball6": {"Runs": 1, "Type": "Single"}}        |
| ...    | ...     | ...      | ...        | ...      | ...                                                                                                                               |
| 599    | 2       | 4        | 99         | 401      | {"Ball1": {"Runs": 1, "Type": "Single"}, "Ball2": {"Runs": null, "Type": null}, "Ball3": {"Runs": null, "Type": null}, "Ball4": {"Runs": null, "Type": null}, "Ball5": {"Runs": null, "Type": null}, "Ball6": {"Runs": 0, "Type": "Dot"}}            |
| 600    | 2       | 4        | 100        | 402      | {"Ball1": {"Runs": 0, "Type": "Dot"}, "Ball2": {"Runs": null, "Type": null}, "Ball3": {"Runs": null, "Type": null}, "Ball4": {"Runs": null, "Type": null}, "Ball5": {"Runs": null, "Type": null}, "Ball6": {"Runs": 0, "Type": "Dot"}}             |


## Commentary Table

| CommentaryID | MatchID | BallNumber | BallDescription               | CommentaryText                            |
|--------------|---------|------------|-------------------------------|-------------------------------------------|
| 1            | 1       | 1          | First ball of the match       | Good length delivery, defended by batsman|
| 2            | 1       | 2          | Second ball of the match      | Swing and a miss!                         |
| 3            | 1       | 3          | Third ball of the match       | Four runs! Excellent shot!                |
| ...          | ...     | ...        | ...                           | ...                                       |


## Denormalized PlayerStats Table

| PlayerStatsID | PlayerID | MatchID | TournamentID | Stats (JSONB)                           |
|---------------|----------|---------|--------------|-----------------------------------------|
| 1             | 101      | 1       | 1            | {"RunsScored": 56, "WicketsTaken": 2}   |
| 2             | 102      | 1       | 1            | {"RunsScored": 102, "Centuries": 1}     |
| 3             | 101      | 2       | 1            | {"RunsScored": 48, "WicketsTaken": 1}   |
| ...           | ...      | ...     | ...          | ...                                     |
