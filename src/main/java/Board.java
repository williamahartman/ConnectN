/**
 * Created by Akshay on 1/21/2016.
 */
public class Board {

    public static final byte EMPTY = 0;
    public static final byte PLAYER = 1;
    public static final byte OPPONENT = 2;

    public static final int MOVE_DROP = 1;
    public static final int MOVE_POP = 2;

    private int width;
    private int height;
    private int numWin;

    private byte[][] state;

    public Board(int width, int height, int numWin) {
        state = new byte[width][height];
        this.width = width;
        this.height = height;
        this.numWin = numWin;
    }

    public Board(Board base) {
        this.state = base.state;
        this.width = base.width;
        this.height = base.height;
        this.numWin = base.numWin;
    }

    public Board(byte[][] state, int numWin){
        this.state = state;
        this.width = state.length;
        this.height = state[0].length;
        this.numWin = numWin;
    }

    public Board move(byte player, int column, int moveType) {

        byte[][] newState = copyState();
        byte[] columnState = newState[column];

        if (moveType == MOVE_DROP) {

            for(int i = 0; i < height; i++){
                if(columnState[i] == EMPTY){
                    columnState[i] = player;
                    break;
                }
            }
        } else if (moveType == MOVE_POP) {
            for(int i = 0; i < height-1; i++){
                columnState[i] = columnState[i+1];
            }
            columnState[height-1] = EMPTY;
        }
        return new Board(newState, numWin);
    }

    private byte[][] copyState(){
        byte[][] newState = new byte[width][height];
        for(int i = 0; i < width; i++){
            System.arraycopy(state[i], 0, newState[i], 0, height);
        }
        return newState;
    }

}
