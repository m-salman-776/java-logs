package Project101.Chess;

public class Board {
    private final Box[][] board;
    public Board(){
        this.board = new Box[8][8];
    }
    public void setPiece(Piece piece,Box position){
        this.board[position.getCoordinate().getX()][position.getCoordinate().getY()].setPiece(piece);
    }
    public Piece getPiece(Position position){
        return this.board[position.getX()][position.getY()].getPiece();
    }
    public void DisplayBoard(){
        // TODO
    }
}



