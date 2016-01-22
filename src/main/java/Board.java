import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 1/21/2016.
 */
public class Board {

    public static final byte EMPTY = 0;
    public static final byte PLAYER = 1;
    public static final byte OPPONENT = 2;

    private int width;
    private int height;
    private int numWin;

    private boolean playerCanPop = true;
    private boolean opponentCanPop = true;

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
        this.playerCanPop = base.playerCanPop;
        this.opponentCanPop = base.opponentCanPop;
    }

    public Board(byte[][] state, int numWin, boolean playerCanPop, boolean opponentCanPop) {
        this.state = state;
        this.width = state.length;
        this.height = state[0].length;
        this.numWin = numWin;
        this.playerCanPop = playerCanPop;
        this.opponentCanPop = opponentCanPop;
    }

    public Board move(Action action) {

        byte[][] newState = copyState();
        byte[] columnState = newState[action.column];

        if (action.moveType == Action.MOVE_DROP) {

            for(int i = 0; i < height; i++){
                if(columnState[i] == EMPTY){
                    columnState[i] = action.player;
                    break;
                }
            }
        } else if (action.moveType == Action.MOVE_POP) {
            for(int i = 0; i < height-1; i++){
                columnState[i] = columnState[i+1];
            }
            columnState[height-1] = EMPTY;

            if(action.player == PLAYER){
                playerCanPop = false;
            } else {
                opponentCanPop = false;
            }
        }
        return new Board(newState, numWin, playerCanPop, opponentCanPop);
    }

    public List<Action> getActions(byte player){
        List<Action> actions = new ArrayList<Action>();

        for(int i = 0; i < width; i++){
            if(state[i][height-1] == EMPTY){
                actions.add(Action.getAction(player, i, Action.MOVE_DROP));
            }
        }

        if((player == PLAYER && playerCanPop) || (player == OPPONENT && opponentCanPop)){
            for(int i = 0; i < width; i++){
                if(state[i][0] == player){
                    actions.add(Action.getAction(player, i, Action.MOVE_POP));
                }
            }
        }

        return actions;
    }

    private byte[][] copyState(){
        byte[][] newState = new byte[width][height];
        for(int i = 0; i < width; i++){
            System.arraycopy(state[i], 0, newState[i], 0, height);
        }
        return newState;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
