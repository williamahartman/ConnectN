package connectn;

/**
 * Created by Akshay on 1/21/2016.
 */
public class Action {

    public static final int MOVE_DROP = 1;
    public static final int MOVE_POP = 0;

    //player, columns, movetype
    private static Action[][][] actions;

    public static void initActions(int width){
        actions = new Action[2][7][2];

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < width; j++){
                actions[i][j][MOVE_DROP] = new Action((byte)(i+1), j, MOVE_DROP);
                actions[i][j][MOVE_POP] = new Action((byte)(i+1), j, MOVE_POP);
            }
        }
    }

    public static Action get(byte player, int column, int moveType){
        return actions[player-1][column][moveType];
    }

    public final byte player;
    public final int column;
    public final int moveType;

    private Action(byte player, int column, int moveType){
        this.player = player;
        this.column = column;
        this.moveType = moveType;
    }
}
