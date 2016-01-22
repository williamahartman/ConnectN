package test;


import connectn.Action;
import connectn.Board;
import connectn.Util;
import org.junit.Before;
import org.junit.Test;

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
        Board empty = new Board(width, height, numWin);
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
        Board empty = new Board(width, height, numWin);
        assertEquals(0, empty.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, numWin));

        Board region3 = empty
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 3, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 4, Action.MOVE_DROP));

        Util.printBoard(region3);

        assertEquals(0, region3.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 1));
        assertEquals(0, region3.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 2));
        assertEquals(1, region3.countRegions(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 3));

        Board region4 = region3
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 3, Action.MOVE_DROP));

        Util.printBoard(region4);

        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_VERTICAL, 1));
        assertEquals(0, region4.countRegions(Board.PLAYER, Board.DIRECTION_VERTICAL, 3));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_VERTICAL, 4));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_DIAGONAL_POSITIVE, 2));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_DIAGONAL_NEGATIVE, 2));
        assertEquals(1, region4.countRegions(Board.PLAYER, Board.DIRECTION_DIAGONAL_NEGATIVE, 3));
    }



}