package debugplayer;

import connectn.Action;
import connectn.Board;
import connectn.RefWrangler;

import javax.swing.*;

/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

public class DebugPlayerMain {
    public static Action getActionFromPrompt() {
        String s = (String) JOptionPane.showInputDialog(
                null,
                "<html>Enter a command (column + move type)." +
                        "<br>Drop - 1" +
                        "<br>Pop - 0" +
                        "<html/>",
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
        return board.countRegions(Board.PLAYER, numWin, numWin) > 0
                || board.countRegions(Board.OPPONENT, numWin, numWin) > 0
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
