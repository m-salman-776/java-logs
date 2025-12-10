package Project101.SnakeLadder;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    Dice dice;
    Board board;
    Status status;
    Queue<Player> playerQueue;
    Scanner scanner;
    public Game(Board board, Dice dice){
        this.board = board;
        this.dice = dice;
        playerQueue = new LinkedList<Player>();
        scanner = new Scanner(System.in);
    }
    void  addPlayers(List<Player> players){
        this.playerQueue.addAll(players);
    }
    public void startGame(){
        this.status = Status.RUNNING;
        while(playerQueue.size() > 1) {
            Player currentPlayer = playerQueue.poll();
            makeMove(currentPlayer);
            if (currentPlayer.getPosition() == board.getTotalCell()) {
                System.out.println("Player : " + currentPlayer.getName() + "Has WON");
            }else {
                playerQueue.add(currentPlayer);
            }
        }
        this.status = Status.FINISHED;
    }
    private void makeMove(Player player){
        System.out.println(); // TODO decorations purpose
        board.printBoard();
        System.out.println(player.getName()+"'s turn. Current Position : "+player.getPosition());
        System.out.println("Press anything to roll the dice.");
        scanner.next().charAt(0) ; // todo blocking call; just to hold
        int position = player.getPosition();
        int diceNumber = dice.rollUp();
        System.out.println(" Hey you got " + diceNumber + " on Dice");
        int targetPosition = position + diceNumber;
        if (targetPosition > board.getTotalCell()) {
            System.out.println("Out of Board");
            return;
        }
        if (board.checkForSnakeOrLadder(targetPosition)){
            Entity snakeOrLadder = board.getSnakeOrLadder(targetPosition);
            targetPosition = snakeOrLadder.getDestinationPosition();
        }
        player.setPosition(targetPosition);
    }
}
