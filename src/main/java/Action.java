import java.util.HashMap;

/**
 * Created by Akshay on 1/21/2016.
 */
public class Action {

    public static final int MOVE_DROP = 1;
    public static final int MOVE_POP = 2;

    //player, columns, movetype
    private static HashMap<Byte, HashMap<Integer, HashMap<Integer, Action>>> actions;


    public static void initActions(int width){
        actions = new HashMap<Byte, HashMap<Integer, HashMap<Integer, Action>>>();
        for(int i = 0; i < 2; i++){
            HashMap<Integer, HashMap<Integer, Action>> columns = new HashMap<Integer, HashMap<Integer, Action>>();
            for(int j = 0; i < width; j++){
                HashMap<Integer, Action> moveTypes = new HashMap<Integer, Action>();
                moveTypes.put(MOVE_DROP, new Action((byte)(i+1), j, MOVE_DROP));
                moveTypes.put(MOVE_DROP, new Action((byte)(i+1), j, MOVE_POP));
                columns.put(j, moveTypes);
            }
            actions.put((byte)(i+1), columns);
        }
    }

    public static Action getAction(byte player, int column, int moveType){
        return actions.get(player).get(column).get(moveType);
    }

    public final byte player;
    public final int column;
    public final int moveType;

    public Action(byte player, int column, int moveType){
        this.player = player;
        this.column = column;
        this.moveType = moveType;
    }

}
