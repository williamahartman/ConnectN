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
        int column = (int) (Math.random() * board.getWidth());

        Action playerMove = Action.get(Board.PLAYER, column, Action.MOVE_DROP);
        board.move(playerMove);

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
        List<Action> possibleMoves = board.getActions(Board.PLAYER);

        if(possibleMoves.isEmpty()) {
            throw new RuntimeException("No possible moves. The game is already a draw!");
        }

        int currentBestHeuristic = Integer.MIN_VALUE;
        Action currentBestAction = null;
        for(Action action: possibleMoves) {
            Board newBoardState = new Board(board);
            newBoardState.move(action);
            int childValue = minimax(newBoardState, Board.OPPONENT, 1, maxDepth);

            if(childValue >= currentBestHeuristic) {
                currentBestAction = action;
            }
        }

        return currentBestAction;
    }

    private int minimax(Board board, byte currentPlayer, int currentDepth, int maxDepth) {
        List<Action> possibleMoves = board.getActions(currentPlayer);
        boolean isMax = currentPlayer == Board.PLAYER;

        //Base case, no moves or at maximum depth
        //Change "possibleMoves.isEmpty()" to  "board.isTerminating()"
        if(currentDepth >= maxDepth || possibleMoves.isEmpty()) {
            return heuristic(board);
        }

        //Other cases, minimax on all children return the best one
        int bestState = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for(Action action: possibleMoves) {
            Board newBoardState = new Board(board);
            newBoardState.move(action);
            int childValue = minimax(newBoardState, currentPlayer, currentDepth + 1, maxDepth);

            if(isMax && childValue >= bestState) {
                bestState = childValue;
            }
            if(!isMax && childValue <= bestState) {
                bestState = childValue;
            }
        }
        return bestState;
    }

    public int heuristic(Board board) {
        return 0;
    }
}
