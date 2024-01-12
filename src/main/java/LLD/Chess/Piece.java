package LLD.Chess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Piece {
    Color color;
    public abstract boolean validMove(Board board,Position source , Position dest);
}
