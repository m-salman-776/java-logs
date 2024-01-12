package LLD.TicTacToe;

public class Board {
    private char [][] board;
    private int [] rowSum;
    private int [] colSum;
    private int diaSum,revDiaSum;
    int size;
    public Board(int n) {
        size = n;
        board = new char[size][size];
        colSum = new int[n];
        rowSum = new int[n];
    }
    public Player makeMove(Player player,int row, int col){
        if (row < 0 || row >= size || col < 0 || col>=size) {
            // TODO
        }
        int val = player.symbol == 'X' ? -1 : 1;
        board[row][col]  = player.symbol;
        this.rowSum[row] += val;
        this.colSum[col] += val;
        if (row == col) diaSum += val;
        if (row + col + 1 == size) revDiaSum += val;
        if (Math.abs(rowSum[row]) == size || Math.abs(colSum[col]) == size || Math.abs(diaSum) == size || Math.abs(revDiaSum) == size){
            return  player;
        }
        return null;
    }
}
