package LLD.Chess;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    Board board;
    Status status;
    Queue<Player> playerQueue ;
    Scanner scanner ;
    public Game(Board board){
        this.board = board;
        playerQueue = new LinkedList<>();
        scanner = new Scanner(System.in);
    }
    public void addPlayer(List<Player> players){
        playerQueue.addAll(players);
    }
    public void startGame(){
        status = Status.ACTIVE;
        while (status.equals(Status.ACTIVE)){
            Player currentPlayer = playerQueue.poll();
            System.out.println("Enter Co ords");
            int xCorSrc = scanner.nextInt();
            int yCorSrc = scanner.nextInt();
            int xCorDest = scanner.nextInt();
            int yCorDest = scanner.nextInt();
            Position source = new Position(xCorSrc,yCorSrc);
            Position dest = new Position(xCorDest,yCorDest);
            makeMove(source,dest);
            if (isCheckmate(currentPlayer) || isStalemate(currentPlayer)){
                status = Status.BLACK_WIN;
            }
        }
    }
    private void makeMove(Position source,Position dest){
        Piece sourcePiece = board.getPiece(source);
        if (sourcePiece == null) {
            System.out.println("Invalid Move");
            return;
        }
        Piece destPiece = board.getPiece(dest);
        if (destPiece != null && destPiece.color == sourcePiece.color) {
            System.out.println("Invalid Move");
            return;
        }

    }

    private boolean isCheckmate(Player player){
        return true;
    }

    private boolean isStalemate(Player player){
        return true;
    }

}
