package connectn;

/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

public class Util {

    public static void printBoard(Board board) {
        byte[][] state = board.copyState();
        for (int y = state[0].length - 1; y >= 0; y--) {
            for (int x = 0; x < state.length; x++) {
                System.out.print(state[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String boardString(Board board) {
        String stateString = "";
        byte[][] state = board.copyState();
        for (int y = state[0].length - 1; y >= 0; y--) {
            stateString += "\t";
            for (int x = 0; x < state.length; x++) {
                stateString += String.valueOf(state[x][y] + " ");
            }

            if(y != 0) {
                stateString += "\n";
            }
        }
        return stateString;
    }
}
