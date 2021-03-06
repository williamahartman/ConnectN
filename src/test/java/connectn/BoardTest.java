package connectn;

/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
        Board empty = new Board(width, height, 4);
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
        Board board4 = new Board(new byte[][]{
                {1,1,2,1},
                {1,1,1,2},
                {1,1,2,2},
                {1,1,1,1},
        }, true, true, 4);

        assertEquals(4, board4.countRegions(Board.PLAYER, 4, 4));

//        Board board5 = new Board(new byte[][]{
//                {1, 2, 1, 2, 1, 2},
//                {0, 0, 0, 0, 0, 0},
//                {1, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0},
//        }, true, true);
//        Util.printBoard(board5);
//        assertEquals(4, board5.countRegions(Board.PLAYER, 1, 4));

        Board board6 = new Board(new byte[][]{
                {1, 2, 1, 2, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
        }, true, true, 4);
        Util.printBoard(board6);
        assertEquals(1, board6.countRegions(Board.PLAYER, 2, 4));

        Board board7 = new Board(new byte[][]{
                {1, 2, 2, 2, 2, 0},
                {1, 1, 1, 2, 1, 2},
                {2, 0, 0, 0, 0, 0},
                {2, 2, 2, 1, 2, 2},
                {2, 0, 0, 0, 0, 0},
                {1, 1, 1, 2, 1, 0},
                {1, 1, 1, 2, 1, 1},
        }, true, true, 4);
        Util.printBoard(board7);
        assertEquals(1, board7.countRegions(Board.OPPONENT, 4, 4));
    }

    @Test
    public void testCountRegionsDirectional(){
        Board empty = new Board(width, height, 4);
        assertEquals(0, empty.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_HORIZONTAL, numWin, 4));

        Board region3 = empty
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 3, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 4, Action.MOVE_DROP));

        Util.printBoard(region3);

        assertEquals(0, region3.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 1, 4));
        assertEquals(0, region3.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 2, 4));
        assertEquals(1, region3.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_HORIZONTAL, 3, 4));

        Board region4 = region3
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 2, Action.MOVE_DROP))
                .move(Action.get(Board.PLAYER, 3, Action.MOVE_DROP));

        Util.printBoard(region4);

        assertEquals(1, region4.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_VERTICAL, 1, 4));
        assertEquals(0, region4.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_VERTICAL, 3, 4));
        assertEquals(1, region4.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_VERTICAL, 4, 4));
        assertEquals(1, region4.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_DIAGONAL_POSITIVE, 2, 4));
        assertEquals(1, region4.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_DIAGONAL_NEGATIVE, 2, 4));
        assertEquals(1, region4.countRegionsDirectional(Board.PLAYER, Board.DIRECTION_DIAGONAL_NEGATIVE, 3, 4));
    }

    @Test
    public void testGetActions(){
        Board empty = new Board(width, height, 4);
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

        Board board8 = new Board(new byte[][]{
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 2, 0, 0, 0, 0},
        }, true, true, 4);

        Player p = new Player(4, 0);
        for(Action a: board8.getActions(Board.PLAYER)) {
            System.out.println(a.column + (a.moveType == Action.MOVE_DROP ? " Drop" : " Pop"));
            System.out.println("Heuristic Val: " + p.heuristic(board8.move(a)));
            System.out.println();
        }

        Board board9 = new Board(new byte[][]{
                {2, 1, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 2, 2, 1, 2, 2},
                {2, 0, 0, 0, 0, 0},
                {2, 1, 2, 2, 0, 0},
                {1, 1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0},
        }, true, true, 4);

        for(Action a: board9.getActions(Board.PLAYER)) {
            System.out.println(a.column + (a.moveType == Action.MOVE_DROP ? " Drop" : " Pop"));
            System.out.println("Heuristic Val: " + p.heuristic(board9.move(a)));
            System.out.println();
        }
    }
}