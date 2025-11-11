package LLD.TicTacToe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    private Board board;
    private Status state;
    private Queue<Player> queue;
    private Scanner scanner;
    private int moves;
    private final int maxMoves;

    public Game(int size,Player player,Player player2) { // TODO player can be var args
        board = new Board(size);
        state = Status.INITIALISED;
        queue = new LinkedList<>();
        queue.add(player);
        queue.add(player2);
        scanner = new Scanner(System.in);
        maxMoves = size * size;
        moves = 0;
    }
    void setState(Status state){
        this.state = state;
    }
    public void startGame(){
        Player winningPlayer = null;
        while (!stopGame()) {
            moves++;
            Player currentPlayer = queue.poll();
            System.out.println("Player " + currentPlayer.name + "'s turn. Enter row and column:");
            int posX = scanner.nextInt();
            int posY = scanner.nextInt();
            try {
                winningPlayer = board.makeMove(currentPlayer,posX,posY);
                if (winningPlayer != null) {
                    setState(Status.WON);
                } else if (moves == maxMoves) {
                    setState(Status.DRAW);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                moves--; // Don't count an invalid move
            }
            queue.add(currentPlayer);
        }
        printWinner(winningPlayer);
    }
    private boolean stopGame(){
        return state.equals(Status.WON) || state.equals(Status.DRAW);
    }
    private void printWinner(Player winningPlayer){
        System.out.println("Game ended With Status :" + state);
        if (winningPlayer != null) {
            System.out.println("Player : " + winningPlayer.name + " won!");
        }
    }
}
