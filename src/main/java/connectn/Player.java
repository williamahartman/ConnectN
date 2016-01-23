package connectn;

import java.util.List;

/**
 * Created by will on 1/21/16.
 */
public class Player {

    private int numWin;
    private long timeLimit;


    public Player(int numWin, long timeLimit){
        this.numWin = numWin;
        this.timeLimit = timeLimit;
    }

    public Action makeMove(Board board) {
        Action playerMove = minimax(board, 3);
        return playerMove;
    }

    /**
     * Checks of the given board state is terminating for the given player
     * @param board
     * @param player
     * @return
     */
    public boolean isTerminating(Board board, byte player){
        return board.countRegions(Board.PLAYER, numWin) > 0
                || board.countRegions(Board.OPPONENT, numWin) > 0
                || board.getActions(player).size() == 0;

    }

    public Action minimax(Board board, int maxDepth) {

        if(isTerminating(board, Board.PLAYER)) {
            throw new RuntimeException("No possible moves. The game is already a draw!");
        }

        int currentBestHeuristic = Integer.MIN_VALUE;
        Action currentBestAction = null;
        for(Action action: board.getActions(Board.PLAYER)) {
            int childValue = min(board.move(action), 1, maxDepth);
            if(childValue >= currentBestHeuristic) {
                currentBestAction = action;
            }
        }

        return currentBestAction;
    }


    private int max(Board board, int currentDepth, int maxDepth){
        if(currentDepth >= maxDepth && isTerminating(board, Board.PLAYER)){
            return heuristic(board);
        }

        int maxValue = Integer.MIN_VALUE;

        for(Action action:board.getActions(Board.PLAYER)){
            maxValue = Math.max(maxValue, min(board.move(action), currentDepth+1, maxDepth));
        }
        return maxValue;
    }

    private int min(Board board, int currentDepth, int maxDepth){
        if(currentDepth >= maxDepth && isTerminating(board, Board.OPPONENT)){
            return heuristic(board);
        }

        int minValue = Integer.MAX_VALUE;

        for(Action action: board.getActions(Board.OPPONENT)){
            minValue = Math.min(minValue, max(board.move(action), currentDepth + 1, maxDepth));
        }
        return minValue;
    }

    public int heuristic(Board board) {
        return 0;
    }
}
