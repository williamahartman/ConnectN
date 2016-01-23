package debugplayer;

import connectn.Action;
import connectn.Board;
import connectn.RefWrangler;

import javax.swing.*;

/**
 * Created by will on 1/23/16.
 */
public class DebugPlayerMain {
    public static Action getActionFromPrompt() {
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Enter a command (column + move type)",
                "Debug player",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        int col = Integer.parseInt(s.split(" ")[0]);
        int type = Integer.parseInt(s.split(" ")[1]);

        return Action.get(Board.PLAYER, col, type);
    }

    public static boolean isTerminating(Board board, int numWin, byte player){
        return board.countRegions(Board.PLAYER, numWin) > 0
                || board.countRegions(Board.OPPONENT, numWin) > 0
                || board.getActions(player).size() == 0;
    }

    public static void main(String[] args) {

        String name = "DEBUG_PLAYER";
        RefWrangler wrangler = new RefWrangler(name);
        wrangler.initGame();
        Action.initActions(wrangler.getBoardWidth());

        if(wrangler.isPlayingFirst()) {
            wrangler.declareMove(getActionFromPrompt());
        }

        while (!isTerminating(wrangler.getBoard(), wrangler.getNumWin(), Board.PLAYER)) {
            wrangler.waitForOpponent();



            wrangler.declareMove(getActionFromPrompt());
        }
    }
}
