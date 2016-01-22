/**
 * Created by will on 1/21/16.
 */
public class Player {

    public Player() {

    }

    public Action makeMove(Board board) {
        int column = (int) (Math.random() * board.getWidth());

        Action playerMove = new Action(Board.PLAYER, column, Action.MOVE_DROP);
        board.move(playerMove);

        return playerMove;
    }
}
