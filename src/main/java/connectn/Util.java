package connectn;

/**
 * Created by Akshay on 1/22/2016.
 */
public class Util {


    public static void printBoard(Board board){
        byte[][] state = board.copyState();
        for(int y = state.length-1; y > 0; y--){
            for (int x = 0; x < state.length; x++){
                System.out.print(state[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String boardString(Board board){
        String stateString = "";
        byte[][] state = board.copyState();
        for(int y = state.length-1; y > 0; y--){
            for (int x = 0; x < state.length; x++){
                stateString += String.valueOf(state[x][y]);
            }
        }
        return stateString;
    }
}
