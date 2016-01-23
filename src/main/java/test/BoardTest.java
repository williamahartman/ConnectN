package test;


import connectn.Action;
import connectn.Board;
import connectn.Player;
import connectn.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Akshay on 1/22/2016.
 */
public class BoardTest {

    int width = 7;
    int height = 6;
    int numWin = 4;

    @Before
    public void setUp(){
        Action.initActions(width);
    }

    @Test
    public void testMoveDrop(){
        Board empty = new Board(width, height);
        assertArrayEquals(new byte[][]{
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
        }, empty.copyState());

        Board move1 = empty.move(Action.get(Board.PLAYER, 3, Action.MOVE_DROP));
        assertArrayEquals(new byte[][]{
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {1,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
        }, move1.copyState());

        Board move4 = move1
                .move(Action.get(Board.OPPONENT, 3, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 5, Action.MOVE_DROP));

        assertArrayEquals(new byte[][]{
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {1,2,0,0,0,0},
                {0,0,0,0,0,0},
                {1,0,0,0,0,0},
                {0,0,0,0,0,0},
        }, move4.copyState());

    }

    @Test
    public void testCountRegions(){
        Board empty = new Board(width, height);
        assertEquals(0, empty.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, numWin));

        Board region3 = empty
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 3, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 4, Action.MOVE_DROP));

        //Util.printBoard(region3);

        assertEquals(0, region3.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 1));
        assertEquals(0, region3.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 2));
        assertEquals(1, region3.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 3));

        Board region4 = region3
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 3, Action.MOVE_DROP));

        //Util.printBoard(region4);

        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_VERTICAL, 1));
        assertEquals(0, region4.countRegions(Board.PLAYER, Board.DIRECTION_VERTICAL, 3));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_VERTICAL, 4));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_DIAGONAL_POSITIVE, 2));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_DIAGONAL_NEGATIVE, 2));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_DIAGONAL_NEGATIVE, 3));
    }

    @Test
    public void testGetActions(){
        Board empty = new Board(width, height);
        assertEquals(width, empty.getActions(Board.PLAYER).size());

        Board pop2 = empty
                .move(Action.get(Board.PLAYER, 0, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 1, Action.MOVE_DROP))
                .move(Action.get(Board.OPPONENT, 3, Action.MOVE_DROP))
                .move(Action.get(Board.OPPONENT, 4, Action.MOVE_DROP));

        Board drop6pop3 = pop2;
        for(int i = 0; i < height; i++){
            drop6pop3 = drop6pop3.move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP));
        }

        assertEquals(9, drop6pop3.getActions(Board.PLAYER).size());
        assertEquals(8, drop6pop3.getActions(Board.OPPONENT).size());

        Board disablePlayerPop = drop6pop3.move(Action.get(Board.PLAYER, 0, Action.MOVE_POP));
        Board disableOpponentPop = drop6pop3.move(Action.get(Board.OPPONENT, 4, Action.MOVE_POP));

        List<Action> playerActions = disablePlayerPop.getActions(Board.PLAYER);
        List<Action> oppActions = disableOpponentPop.getActions(Board.OPPONENT);

        assertEquals(6, playerActions.size());
        assertEquals(6, oppActions.size());
    }

}