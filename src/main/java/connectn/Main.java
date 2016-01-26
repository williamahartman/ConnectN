package connectn;

/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

import java.util.Date;

public class Main {
    private static final int NAME_LENGTH = 10;

    public static void main(String[] args) {
        //DebugPrinter.println("Starting game at " + new Date().toString());

        String name = generateName();
        RefWrangler wrangler = new RefWrangler(name);
        wrangler.initGame();
        Action.initActions(wrangler.getBoardWidth());
        Player player = new Player(wrangler.getNumWin(), wrangler.getTimeLimitMs());

        if(wrangler.isPlayingFirst()) {
            Action move = player.makeMove(wrangler.getBoard());
            wrangler.declareMove(move);
        }

        while (!player.isTerminating(wrangler.getBoard(), Board.PLAYER)) {
            wrangler.waitForOpponent();
            Action move = player.makeMove(wrangler.getBoard());
            wrangler.declareMove(move);
        }
    }

    public static String generateName(){
        String name = "";
        while(name.length() < NAME_LENGTH){
            name += String.valueOf((int)(Math.random()*10));
        }
        return name;
    }
}
