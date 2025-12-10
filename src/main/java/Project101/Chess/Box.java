package Project101.Chess;

import lombok.Getter;
import lombok.Setter;

enum Color {
    WHITE,
    BLACK
}
@Getter
@Setter
public class Box {
    Position coordinate;
    Piece piece;
    Color color;
}
