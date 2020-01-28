/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.project;

import javax.swing.JLabel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for Board class
 * @author Evan
 */
public class BoardTest {
    
    private static boolean[][] boardState1; //5 unique board states
    private static boolean[][] boardState2;
    private static boolean[][] boardState3;
    private static boolean[][] boardState4;
    private static boolean[][] boardState5;
    
    @Before
    public void setUp()
    {
        boardState1 = new boolean[50][50]; //Declare 5 unique board states
        boardState2 = new boolean[50][50];
        boardState3 = new boolean[50][50];
        boardState4 = new boolean[50][50];
        boardState5 = new boolean[50][50];
        
        for (int i = 0; i < 50; i++) //Board State 1: Full
        {
        
            for (int j = 0; j < 50; j++)
            {
                boardState1[i][j] = true;
            }
        }
        
        for (int i = 0; i < 50; i++) //Board State 2: Empty
        {
            for (int j = 0; j < 50; j++)
            {
                boardState2[i][j] = false;
            }
        }
        
        boolean b = false; //Board State 3: Stripes
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                b = !b;
                boardState3[i][j] = b;
            }
        }
        
        for (int i = 0; i < 50; i++) //Board State 4: Box
        {
            for (int j = 0; j < 50; j++)
            {
                boardState4[i][j] = false;
            }
        }
        boardState4[3][3] = true;
        boardState4[3][4] = true;
        boardState4[4][3] = true;
        boardState4[4][4] = true;
        
        for (int i = 0; i < 50; i++) //Board State 5: Glider
        {
            for (int j = 0; j < 50; j++)
            {
                boardState5[i][j] = false;
            }
        }
        boardState5[4][4] = true;
        boardState5[4][5] = true;
        boardState5[4][6] = true;
        boardState5[3][6] = true;
        boardState5[2][5] = true;
    }

    /**
     * Test of getOneState method, of class Board.
     */
    @Test
    public void testGetOneState() {
        System.out.println("getOneState");
        Board board = new Board(50, 50); //Declare a board
        board.setCellStates(boardState1); //Set the board's state equal to a created one
        for (int i = 0; i < 10; i++)
        {
            assertTrue(board.getOneState(4, i)); //Check the first 10 cells in column 4 (Should all be true)
        }
        board.setCellStates(boardState2); //Repeat
        for (int i = 0; i < 10; i++)
        {
            assertFalse(board.getOneState(4, i)); //Should all be false
        }
        board.setCellStates(boardState3);
        for (int i = 0; i < 10; i+=2)
        {
            assertTrue(board.getOneState(4, i)); //Should alternate
        }
        for (int i = 1; i < 10; i+=2)
        {
            assertFalse(board.getOneState(4, i));
        }
        board.setCellStates(boardState4);
        for (int i = 0; i < 10; i++)
        {
            if (i == 3 || i == 4)
            {
                assertTrue(board.getOneState(4, i)); //Only 3 and 4 should be true
            }
            else
            {
                assertFalse(board.getOneState(4, i));
            }
        }
        board.setCellStates(boardState5);
        for (int i = 0; i < 10; i++)
        {
            if (i == 4 || i == 5 || i == 6) //Only 4, 5, and 6 should be true
            {
                assertTrue(board.getOneState(4, i));
            }
            else
            {
                assertFalse(board.getOneState(4, i));
            }
        }
    }

    /**
     * Test of setOneState method, of class Board.
     */
    @Test
    public void testSetOneState() {
        System.out.println("setOneState");
        Board board = new Board(50, 50); //Declare board
        board.setCellStates(boardState1); //Set the board's state equal to the full one
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                board.setOneState(i, j, false); //Set every cell to empty and check if it worked
                assertFalse(board.getOneState(i, j));
            }
        }
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                board.setOneState(i, j, true); //Set every cell back to full and check if it worked
                assertTrue(board.getOneState(i, j));
            }
        }
    }

    /**
     * Test of toggleState method, of class Board.
     */
    @Test
    public void testToggleState() {
        System.out.println("toggleState");
        Board board = new Board(50, 50); //Declare board
        board.setCellStates(boardState1); //Set the board's state equal to the full one
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                board.toggleState(i, j); //Toggle every cell from full to empty and check if it worked
                assertFalse(board.getOneState(i, j));
            }
        }
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                board.toggleState(i, j); //Toggle every cell from empty to full and check if it worked
                assertTrue(board.getOneState(i, j));
            }
        }
    }

    /**
     * Test of livingNeighbourCount method, of class Board.
     */
    @Test
    public void testLivingNeighbourCount() {
        System.out.println("livingNeighbourCount");
        Board board = new Board(50, 50); //Declare a board
        board.setCellStates(boardState1); //Set the board's state equal to a created one
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                assertEquals(8, board.livingNeighbourCount(i, j)); //Check if each neighbor count is correct (Should all be 8 in this case)
            }
        }
        board.setCellStates(boardState2); //Repeat
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                assertEquals(0, board.livingNeighbourCount(i, j)); //Should all be 0
            }
        }
        board.setCellStates(boardState3);
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                if (j%2 == 0)
                {
                    assertEquals(2, board.livingNeighbourCount(i, j)); //Should be two for cells in the living stripes...
                }
                else
                {
                    assertEquals(6, board.livingNeighbourCount(i, j)); //...and 6 for cells in the dead stripes
                }
            }
        }
        board.setCellStates(boardState4);
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                if (i < 2 || i > 5)
                {
                    if (j < 2 || j > 5)
                    {
                        assertEquals(0, board.livingNeighbourCount(i, j)); //Should be 0 for cells not near the box
                    }
                }
            }
        }
        assertEquals(1, board.livingNeighbourCount(2, 2)); //1 for cells next to the box at a corner
        assertEquals(1, board.livingNeighbourCount(2, 5));
        assertEquals(1, board.livingNeighbourCount(5, 2));
        assertEquals(1, board.livingNeighbourCount(5, 5));
        assertEquals(3, board.livingNeighbourCount(3, 3)); //3 for cells in the box
        assertEquals(3, board.livingNeighbourCount(3, 4));
        assertEquals(3, board.livingNeighbourCount(4, 3));
        assertEquals(3, board.livingNeighbourCount(4, 4));
        assertEquals(2, board.livingNeighbourCount(2, 3)); //2 for cells next to the box at an edge
        assertEquals(2, board.livingNeighbourCount(2, 4));
        assertEquals(2, board.livingNeighbourCount(5, 3));
        assertEquals(2, board.livingNeighbourCount(5, 4));
        assertEquals(2, board.livingNeighbourCount(3, 2));
        assertEquals(2, board.livingNeighbourCount(3, 5));
        assertEquals(2, board.livingNeighbourCount(4, 2));
        assertEquals(2, board.livingNeighbourCount(4, 5));
        board.setCellStates(boardState5);
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                if (i < 1 || i > 5)
                {
                    if (j < 3 || j > 7)
                    {
                        assertEquals(0, board.livingNeighbourCount(i, j)); //Should be 0 for cells not near the glider
                    }
                }
            }
        }
        assertEquals(0, board.livingNeighbourCount(1, 3)); //More specific values for cells in or near the glider
        assertEquals(1, board.livingNeighbourCount(1, 4));
        assertEquals(1, board.livingNeighbourCount(1, 5));
        assertEquals(1, board.livingNeighbourCount(1, 6));
        assertEquals(0, board.livingNeighbourCount(1, 7));
        assertEquals(0, board.livingNeighbourCount(2, 3));
        assertEquals(1, board.livingNeighbourCount(2, 4));
        assertEquals(1, board.livingNeighbourCount(2, 5));
        assertEquals(2, board.livingNeighbourCount(2, 6));
        assertEquals(1, board.livingNeighbourCount(2, 7));
        assertEquals(1, board.livingNeighbourCount(3, 3));
        assertEquals(3, board.livingNeighbourCount(3, 4));
        assertEquals(5, board.livingNeighbourCount(3, 5));
        assertEquals(3, board.livingNeighbourCount(3, 6));
        assertEquals(2, board.livingNeighbourCount(3, 7));
        assertEquals(1, board.livingNeighbourCount(4, 3));
        assertEquals(1, board.livingNeighbourCount(4, 4));
        assertEquals(3, board.livingNeighbourCount(4, 5));
        assertEquals(2, board.livingNeighbourCount(4, 6));
        assertEquals(2, board.livingNeighbourCount(4, 7));
        assertEquals(1, board.livingNeighbourCount(5, 3));
        assertEquals(2, board.livingNeighbourCount(5, 4));
        assertEquals(3, board.livingNeighbourCount(5, 5));
        assertEquals(2, board.livingNeighbourCount(5, 6));
        assertEquals(1, board.livingNeighbourCount(5, 7));        
    }
    
}
