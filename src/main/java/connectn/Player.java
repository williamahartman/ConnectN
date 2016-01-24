package connectn;

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
        Action playerMove = minimax(board, 5);
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
            int childValue = min(board.move(action), Integer.MIN_VALUE, Integer.MAX_VALUE, 1, maxDepth);
            if(childValue >= currentBestHeuristic) {
                currentBestAction = action;
            }
        }

        return currentBestAction;
    }

    private int max(Board board, int alpha, int beta, int currentDepth, int maxDepth){
        if(currentDepth >= maxDepth || isTerminating(board, Board.PLAYER)){
            return heuristic(board);
        }

        int localAlpha = alpha;
        int maxValue = Integer.MIN_VALUE;

        for(Action action:board.getActions(Board.PLAYER)){
            maxValue = Math.max(maxValue, min(board.move(action), localAlpha, beta, currentDepth+1, maxDepth));
            localAlpha = Math.max(localAlpha, maxValue);
            if(beta <= localAlpha) {
                DebugPrinter.println("Pruning branch (val: " + maxValue + ", alpha: " + localAlpha + ", beta: " + beta + ")");
                break;
            }
        }
        return maxValue;
    }

    private int min(Board board, int alpha, int beta, int currentDepth, int maxDepth){
        if(currentDepth >= maxDepth || isTerminating(board, Board.OPPONENT)){
            return heuristic(board);
        }

        int localBeta = beta;
        int minValue = Integer.MAX_VALUE;

        for(Action action: board.getActions(Board.OPPONENT)){
            minValue = Math.min(minValue, max(board.move(action), alpha, localBeta, currentDepth + 1, maxDepth));
            localBeta = Math.min(localBeta, minValue);
            if(localBeta <= alpha) {
                DebugPrinter.println("Pruning branch (val: " + minValue + ", alpha: " + alpha + ", beta: " + localBeta + ")");
                break;
            }
        }
        return minValue;
    }

    public int heuristic(Board board) {
        int value = 0;

        for(int i = 1; i < numWin; i++){
            value += Math.pow(board.countRegions(Board.PLAYER, i), 3);
            value -= Math.pow(board.countRegions(Board.OPPONENT, i), 3);
        }
        return value;
    }
}
