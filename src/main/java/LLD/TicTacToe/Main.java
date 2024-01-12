package LLD.TicTacToe;

public class Main {
    public static void main(String[] args){
        Player player1 = new Player("Salman",'O');
        Player player2 = new Player("Ansari",'X');
        Game game = new Game(3,player1,player2);
        game.startGame();
    }
}
