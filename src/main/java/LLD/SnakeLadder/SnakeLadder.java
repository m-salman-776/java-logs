package LLD.SnakeLadder;



import LLD.SnakeLadder.EntityImple.Ladder;
import LLD.SnakeLadder.EntityImple.Snake;

import java.util.ArrayList;
import java.util.Arrays;

public class SnakeLadder {
    public static void main(String []args){
        Entity snake1 = new Snake(12, 28);
        Entity snake2 = new Snake(34, 78);
        Entity snake3 = new Snake(6, 69);
        Entity snake4 = new Snake(65, 84);

        Entity ladder1 = new Ladder(24, 56);
        Entity ladder2 = new Ladder(43, 83);
        Entity ladder3 = new Ladder(3, 31);
        Entity ladder4 = new Ladder(72, 91);


        Board board = new Board(10);
        board.addSnakeOrLadder(snake1);
        board.addSnakeOrLadder(snake2);
        board.addSnakeOrLadder(snake3);
        board.addSnakeOrLadder(snake4);

        board.addSnakeOrLadder(ladder1);
        board.addSnakeOrLadder(ladder2);
        board.addSnakeOrLadder(ladder3);
        board.addSnakeOrLadder(ladder4);

        Dice dice = new Dice(6);

        Game game = new Game(board, dice);

        Player player1 = new Player("p1");
        Player player2 = new Player("p2");
        Player player3 = new Player("p3");

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1,player2,player3));
        game.addPlayers(players);

        game.startGame();
    }
}
