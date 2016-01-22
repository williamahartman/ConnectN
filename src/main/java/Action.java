/**
 * Created by Akshay on 1/21/2016.
 */
public class Action {

    public static final int MOVE_DROP = 1;
    public static final int MOVE_POP = 2;

    public byte player;
    public int column;
    public int moveType;

    public Action(byte player, int column, int moveType){
        this.player = player;
        this.column = column;
        this.moveType = moveType;
    }

}
