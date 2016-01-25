package connectn;

/**
 * Created by will on 1/21/16.
 */
public class Player {

    public final int numWin;
    public final long timeLimit;


    public Player(int numWin, long timeLimit){
        this.numWin = numWin;
        this.timeLimit = timeLimit;
    }

    public Action makeMove(Board board) {
        //Action playerMove = minimax(board, 5, new Flag());
        IterativeDeepener deepener = new IterativeDeepener(this, 3, 1);
        return deepener.makeMove(board);
    }

    /**
     * Checks of the given board state is terminating for the given player
     * @param board
     * @param player
     * @return
     */
    public boolean isTerminating(Board board, byte player){
        return board.countRegions(Board.PLAYER, numWin, numWin) > 0
                || board.countRegions(Board.OPPONENT, numWin, numWin) > 0
                || board.getActions(player).size() == 0;
    }

    public Action minimax(Board board, int maxDepth, Flag stopFlag) {
        if(isTerminating(board, Board.PLAYER)) {
            throw new RuntimeException("No possible moves. The game is already a draw!");
        }

        int currentBestHeuristic = Integer.MIN_VALUE;
        Action currentBestAction = null;
        for(Action action: board.getActions(Board.PLAYER)) {
            int childValue = min(board.move(action), Integer.MIN_VALUE, Integer.MAX_VALUE, 1, maxDepth, stopFlag);
            if(childValue >= currentBestHeuristic) {
                currentBestAction = action;
            }
        }

        return currentBestAction;
    }

    private int max(Board board, int alpha, int beta, int currentDepth, int maxDepth, Flag stopFlag){
        if(currentDepth >= maxDepth || isTerminating(board, Board.PLAYER)){
            return heuristic(board);
        }

        int localAlpha = alpha;
        int maxValue = Integer.MIN_VALUE;

        for(Action action:board.getActions(Board.PLAYER)){
            maxValue = Math.max(maxValue, min(board.move(action), localAlpha, beta, currentDepth+1, maxDepth, stopFlag));
            localAlpha = Math.max(localAlpha, maxValue);
            if(beta <= localAlpha) {
                break;
            }

            if(stopFlag.get()){
                return 0;
            }
        }
        return maxValue;
    }

    private int min(Board board, int alpha, int beta, int currentDepth, int maxDepth, Flag stopFlag){
        if(currentDepth >= maxDepth || isTerminating(board, Board.OPPONENT)){
            return heuristic(board);
        }

        int localBeta = beta;
        int minValue = Integer.MAX_VALUE;

        for(Action action: board.getActions(Board.OPPONENT)){
            minValue = Math.min(minValue, max(board.move(action), alpha, localBeta, currentDepth + 1, maxDepth, stopFlag));
            localBeta = Math.min(localBeta, minValue);
            if(localBeta <= alpha) {
                break;
            }

            if(stopFlag.get()){
                return 0;
            }

        }
        return minValue;
    }

    public int heuristic(Board board) {
        int value = 0;

        //Add value of centerness
        double playerCenterness = countCenterness(Board.PLAYER, board.copyState());
        double opponeentCenterness = countCenterness(Board.OPPONENT, board.copyState());

        double centernessAdjustPlayer = (1 / (Math.abs(playerCenterness - board.getWidth() / 2.0))) * 25;
        double centernessAdjustOpponent = (1 / (Math.abs(opponeentCenterness - board.getWidth() / 2.0))) * -25;
        value += centernessAdjustPlayer;
        value += centernessAdjustOpponent;

        //Add value for chains of pieces
        int chainVal = 0;
        for(int i = 2; i < numWin; i++){
            chainVal += Math.pow(board.countRegions(Board.PLAYER, i, numWin), 3);
            chainVal -= Math.pow(board.countRegions(Board.OPPONENT, i, numWin), 3);
        }
        value += chainVal;

        //Debug prints
        DebugPrinter.println(Util.boardString(board));
        DebugPrinter.println("\tValue from regions: " + chainVal);
        DebugPrinter.println("\tCenterness adjust player: " + centernessAdjustPlayer);
        DebugPrinter.println("\tCenterness adjust opponent: " + centernessAdjustOpponent);
        DebugPrinter.println("\tFinal value: " + value);
        DebugPrinter.println("");

        return value;
    }

    private double countCenterness(byte playerTurn, byte[][] boardState) {
        double centerness = 0;
        for(int i = 0; i < boardState.length; i++) {
            int tallyInColumn = 0;
            for(int j = 0; j < boardState[0].length; j++) {
                if(boardState[i][j] == playerTurn) {
                    tallyInColumn++;
                }
            }
            centerness += tallyInColumn * (i + 1);
        }

        return centerness / boardState[0].length;
    }
}
