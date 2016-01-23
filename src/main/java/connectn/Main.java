package connectn;

/**
 * Created by will on 1/21/16.
 */
public class Main {

    private static final int NAME_LENGHT = 10;


    public static void main(String[] args) {

        String name = generateName();
        RefWrangler wrangler = new RefWrangler(name);
        wrangler.initGame();
        Action.initActions(wrangler.getBoardWidth());
        Player player = new Player(wrangler.getNumWin(), wrangler.getTimeLimitMs());

        if(wrangler.isPlayingFirst()) {
            Action move = player.makeMove(wrangler.getBoard());
            wrangler.declareMove(move);
        }

        for (int i = 0; i < 100; i++){
            wrangler.waitForOpponent();
            Action move = player.makeMove(wrangler.getBoard());
            wrangler.declareMove(move);
        }
    }

    public static String generateName(){
        String name = "";
        while(name.length() < NAME_LENGHT){
            name += String.valueOf((int)(Math.random()*10));
        }
        return name;
    }
}
