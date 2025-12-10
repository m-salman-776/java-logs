package Project101.Chess.PieceImpl;

import Project101.Chess.Board;
import Project101.Chess.Piece;
import Project101.Chess.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class King extends Piece {
    boolean castled ;
    @Override
    public boolean validMove(Board board, Position source, Position dest) {
        if (board.getPiece(dest) != null && board.getPiece(dest).getColor() == this.getColor()) {
            return false;
        }
        if (!(Math.abs(source.getX() - dest.getX()) <= 1 &&  Math.abs(source.getY()-dest.getY()) <= 1)){
            return false;
        }
        // TODO Check if under attack
        return true;
    }
}
class Knight extends Piece{
    @Override
    public boolean validMove(Board board, Position source, Position dest) {
        if (board.getPiece(dest) != null && board.getPiece(dest).getColor() == this.getColor()) {
            return false;
        }
        if (Math.abs(source.getX() - dest.getX()) * Math.abs(source.getY()-dest.getY()) != 2){
            return false;
        }
        return true;
    }
}
class Bishop extends Piece{
    // todo path check
    @Override
    public boolean validMove(Board board, Position source, Position dest) {
        if (board.getPiece(dest) != null && board.getPiece(dest).getColor() == this.getColor()) {
            return false;
        }
        if (Math.abs(source.getX() - dest.getX()) != Math.abs(source.getY()-dest.getY())){
            return false;
        }
        return true;
    }

    private boolean pathCheck(Board board, Position source, Position dest){
        int pathLen = Math.abs(source.getX() - dest.getX());
        for (int start=1;start<pathLen;start++){
            Position position = new Position(source.getX()+start,source.getY()+start);
            if (board.getPiece(position) != null) return false;
        }
        return true;
    }
}

class Rock extends Piece {
    // todo path check

    @Override
    public boolean validMove(Board board, Position source, Position dest) {
        if (board.getPiece(dest) != null && board.getPiece(dest).getColor() == this.getColor()) {
            return false;
        }
        if (!(source.getX() == dest.getX() || source.getY() == dest.getY())){
            return false;
        }
        return true;
    }

    private boolean pathCheck(Board board ,Position source, Position dest){
        int diff[] = {0,0};
//        diff[0] = (dest.getX()-source.getX() > 0 ;
//        diff[1] = dest.getY()-source.getY();
        return true;
    }
}
@Getter
@Setter
class Pawn extends Piece{
    boolean canMoveTwoStep;
    @Override
    public boolean validMove(Board board, Position source, Position dest) {
        if (board.getPiece(dest) != null && board.getPiece(dest).getColor() == this.getColor()) {
            return false;
        }
        if (source.getX() - dest.getX() != 0){
            if (board.getPiece(dest).getColor() == this.getColor()) return false;
        }
        int xDiff = Math.abs(source.getY() - dest.getY());
        if (xDiff > 2) return  false;
        if (xDiff == 2){
            if (this.canMoveTwoStep) {
                this.setCanMoveTwoStep(false);
            }
            else return false;
        }
        return true;
    }
}

class Queen extends Piece{
    // todo path check
    @Override
    public boolean validMove(Board board, Position source, Position dest) {
        if (board.getPiece(dest) != null && board.getPiece(dest).getColor() == this.getColor()) {
            return false;
        }
        // bishop's move
        if (Math.abs(source.getX() - dest.getX()) != Math.abs(source.getY()-dest.getY())){
            return false;
        }
        // rock's move
        if (!(source.getX() == dest.getX() || source.getY() == dest.getY())){
            return false;
        }
        return true;
    }
    private boolean pathCheck(Board board ,Position position, Position dest){
        return true;
    }
}