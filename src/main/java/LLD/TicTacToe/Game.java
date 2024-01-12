package LLD.TicTacToe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    private Board board;
    private Status state;
    private Queue<Player> queue;
    private Scanner scanner;
    public Game(int size,Player player,Player player2) { // TODO player can be var args
        board = new Board(size);
        state = Status.INITIALISED;
        queue = new LinkedList<>();
        queue.add(player);
        queue.add(player2);
        scanner = new Scanner(System.in);
    }
    void setState(Status state){
        this.state = state;
    }
    public void startGame(){
        int posX,posY;
        while (!stopGame()) {
            Player currentPlayer = queue.poll();
            posX = scanner.nextInt();
            posY = scanner.nextInt();
            Player winningPlayer = board.makeMove(currentPlayer,posX,posY);
            if (winningPlayer != null) {
                setState(Status.WON);
                break;
            }
            queue.remove();
            queue.add(currentPlayer);
        }
        printWinner();
    }
    private boolean stopGame(){
        return state.equals(Status.WON);
    }
    private void printWinner(){
        System.out.println("Won Was ended With Status :"+state+"Player : "+queue.peek().name);
    }
}
