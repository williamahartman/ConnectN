package connectn;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Akshay on 1/21/2016.
 */
public class RefWrangler {

    private Scanner scanner;

    private String name;
    private String opponentName;

    private int boardWidth;
    private int boardHeight;
    private int numWin;

    private long timeLimit;

    private Board board;

    private boolean playingFirst;

    public RefWrangler(String name){
        scanner = new Scanner(System.in);
        this.name = name;
    }

    public void initGame(){
        System.out.println(name);

        String[] playerNames = scanner.nextLine().split(" ");
        int playerNumber = playerNames[1].equals(name) ? 1 : 2;

        boardHeight = scanner.nextInt();
        boardWidth = scanner.nextInt();
        numWin = scanner.nextInt();
        int firstPlayer = scanner.nextInt();
        timeLimit = scanner.nextInt() * 1000;

        playingFirst = firstPlayer == playerNumber;

        board = new Board(boardWidth, boardHeight, numWin);
    }

    public void waitForOpponent() {
        try {
            int column = scanner.nextInt();
            int moveType = scanner.nextInt();
            board = board.move(Action.get(Board.OPPONENT, column, moveType));
        } catch (InputMismatchException e) {
            System.err.println("Unexpected output from ref!");
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean isPlayingFirst() {
        return playingFirst;
    }

    public void declareMove(Action action) {
        board = board.move(action);
        System.out.println(action.column + " " + action.moveType);
    }

    public int getBoardWidth(){
        return boardWidth;
    }

    public int getBoardHeight(){
        return boardHeight;
    }

    public long getTimeLimitMs(){
        return timeLimit;
    }

    public int getNumWin(){
        return numWin;
    }
}
