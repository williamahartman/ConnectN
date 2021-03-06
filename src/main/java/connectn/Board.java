package connectn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

/**
 * The board class represents a given state in connect-N. Moves can be made (returning decendant boards),
 * and information about the board can be computed.
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
    private static final int[] DY = {1, 0, 1, -1};

    private final int numWin;

    private final boolean playerCanPop;
    private final boolean opponentCanPop;

    private final int width;
    private final int height;
    private final byte[][] state;



    public Board(int width, int height, int numWin) {
        this(new byte[width][height], true, true, numWin);
    }

    public Board(Board base) {
        this(base.copyState(), base.playerCanPop, base.opponentCanPop, base.numWin);
    }

    public Board(byte[][] state, boolean playerCanPop, boolean opponentCanPop, int numWin) {
        this.state = state;
        this.width = state.length;
        this.height = state[0].length;
        this.playerCanPop = playerCanPop;
        this.opponentCanPop = opponentCanPop;
        this.numWin = numWin;
    }

    public Board move(Action action) {

        byte[][] newState = copyState();
        boolean playerPop = this.playerCanPop;
        boolean opponentPop = this.opponentCanPop;

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
                playerPop = false;
            } else {
                opponentPop = false;
            }
        }
        return new Board(newState, playerPop, opponentPop, numWin);
    }

    /**
     * Returns a list of possible actions for the given player
     * @param player
     * @return
     */
    public List<Action> getActions(byte player){
        List<Action> actions = new ArrayList<Action>();

        for(int i = 0; i < width; i++){
            if(state[i][height-1] == EMPTY){
                actions.add(Action.get(player, i, Action.MOVE_DROP));
            }
        }

        if((player == PLAYER && playerCanPop) || (player == OPPONENT && opponentCanPop)){
            for(int i = 0; i < width; i++){
                if(state[i][0] == player){
                    actions.add(Action.get(player, i, Action.MOVE_POP));
                }
            }
        }

        return actions;
    }


    public int countRegions(byte player, int length, int numWin){
        return countRegionsDirectional(player, DIRECTION_HORIZONTAL, length, numWin)
                + countRegionsDirectional(player, DIRECTION_VERTICAL, length, numWin)
                + countRegionsDirectional(player, DIRECTION_DIAGONAL_POSITIVE, length, numWin)
                + countRegionsDirectional(player, DIRECTION_DIAGONAL_NEGATIVE, length, numWin);
    }

    /**
     * Count the number of regions the player has in the given direction of the specified length
     * @param player
     * @param direction
     * @param length
     * @return
     */
    public int countRegionsDirectional(byte player, int direction, int length, int numWin){
        //get director vector
        int dx = DX[direction];
        int dy = DY[direction];

        //count to regions found
        int count = 0;
        //iterate over every position
        for(int x = 0; x < width; x++){
            //only need to check vertically until the first empty spot
            for(int y = 0; y < height && state[x][y] != EMPTY; y++){
                //check if the position contains the player's piece, and if a matching region would fit in the board
                if(state[x][y] == player && checkBounds(x + (length-1)*dx, y + (length-1)*dy)){
                    boolean region = true;
                    //check LENGTH pieces in the direction of the region to see if it is filled with the player's pieces
                    for(int i = 0; i < length; i++){
                        //if there is a piece that is not the player's, it's not a region
                        if(state[x + i*dx][y + i*dy] != player ){
                            region = false;
                            break;
                        }
                    }


                    if(region) {
                        if(length == numWin){
                            count++;
                        } else {
                            //check that region is not part of a larger region
                            //if the slot before or after the region has a piece, it is part of a larger region and should not be counted
                            int xBefore = x - dx;
                            int yBefore = y - dy;
                            int xAfter = x + length * dx;
                            int yAfter = y + length * dy;

                            boolean beforeOpen = (checkBounds(xBefore, yBefore) && state[xBefore][yBefore] == EMPTY);
                            boolean afterOpen = (checkBounds(xAfter, yAfter) && state[xAfter][yAfter] == EMPTY);
                            boolean beforeIncluded = (checkBounds(xBefore, yBefore) && state[xBefore][yBefore] == player);
                            boolean afterIncluded = (checkBounds(xAfter, yAfter) && state[xAfter][yAfter] == player);

                            if (!(beforeIncluded || afterIncluded) && (beforeOpen || afterOpen)) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * Check if the given coordinate is on the board
     * @param x
     * @param y
     * @return
     */
    public boolean checkBounds(int x, int y){
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public byte[][] copyState(){
        byte[][] newState = new byte[width][height];
        for(int i = 0; i < width; i++){
            System.arraycopy(state[i], 0, newState[i], 0, height);
        }
        return newState;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
