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

        Board move1 = empty.move(Action.getAction(Board.PLAYER, 3, Action.MOVE_DROP));
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
                .move(Action.getAction(Board.OPPONENT, 3, Action.MOVE_DROP))
                .move(Action.getAction(Board.PLAYER, 5, Action.MOVE_DROP));

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



}