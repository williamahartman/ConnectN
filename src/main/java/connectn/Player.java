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
}
