package connectn;

/**
 * Created by will on 1/21/16.
 */
public class Main {


    public static void main(String[] args) {

        RefWrangler wrangler = new RefWrangler("q");
        wrangler.initGame();

        Player p = new Player();

        if(wrangler.isPlayingFirst()) {
            Action move = p.makeMove(wrangler.getBoard());
            wrangler.declareMove(move);
        }

        for (int i = 0; i < 100; i++){
            wrangler.waitForOpponent();
            Action move = p.makeMove(wrangler.getBoard());
            wrangler.declareMove(move);
        }
    }
}
