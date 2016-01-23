package connectn;

/**
 * Created by will on 1/21/16.
 */
public class Player {

    private long timeLimit;

    public Player(long timeLimit){
        this.timeLimit = timeLimit;
    }

    public Action makeMove(Board board) {
        int column = (int) (Math.random() * board.getWidth());

        Action playerMove = Action.get(Board.PLAYER, column, Action.MOVE_DROP);
        board.move(playerMove);

        return playerMove;
    }
}
