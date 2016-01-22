package connectn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 1/21/2016.
 */
public class Board {

    public static final byte EMPTY = 0;
    public static final byte PLAYER = 1;
    public static final byte OPPONENT = 2;

    public static final int DIRECTION_VERTICAL = 0;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_DIAGONAL_POSITIVE = 2;
    public static final int DIRECTION_DIAGONAL_NEGATIVE = 3;

    private static final int[] DX = {0, 1, 1, 1};
    private static final int[] DY = {1, 0, -1, 1};

    private int numWin;

    private boolean playerCanPop = true;
    private boolean opponentCanPop = true;


    private int width;
    private int height;
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
        //byte[] columnState = newState[action.column];

        if (action.moveType == Action.MOVE_DROP) {

            for(int i = 0; i < height; i++){
                if(newState[action.column][i] == EMPTY){
                    newState[action.column][i] = action.player;
                    break;
                }
            }
        } else if (action.moveType == Action.MOVE_POP) {
            for(int i = 0; i < height-1; i++){
                newState[action.column][i] = newState[action.column][i+1];
            }
            newState[action.column][height-1] = EMPTY;

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

    //needs testing
    public int countRegions(byte player, int direction, int length){
        int dx = DX[direction];
        int dy = DY[direction];

        boolean[][] regionMarkers = new boolean[width][height];

        int count = 0;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(state[x][y] == player && checkBounds(x + length*dx, y + length*dy)){
                    boolean region = true;
                    for(int i = 0; i < length; i++){
                        if(state[x + i*dx][y + i*dy] != player || regionMarkers[x + i*dx][y + i*dy]){
                            region = false;
                            break;
                        }
                    }

                    //region is not part of a larger region
                    int xBefore = x - dx;
                    int yBefore = y - dy;
                    int xAfter = x + length*dx;
                    int yAfter = y + length*dy;
                    boolean beforeNotIncluded = (!checkBounds(xBefore, yBefore) || state[xBefore][yBefore] != player);
                    boolean afterNotIncluded = (!checkBounds(xAfter, yAfter) || state[xAfter][yAfter] != player);

                    region &= (beforeNotIncluded || afterNotIncluded);

                    if(region){
                        count++;
                        for(int i = 0; i < length; i++){
                            regionMarkers[x + i*dx][y + i*dy] = true;
                        }
                    }
                }
            }
        }
        return count;
    }

    private boolean checkBounds(int x, int y){
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public byte[][] copyState(){
        byte[][] newState = new byte[width][height];
        for(int i = 0; i < width; i++){
            System.arraycopy(state[i], 0, newState[i], 0, height);
        }
        return newState;
    }
}
