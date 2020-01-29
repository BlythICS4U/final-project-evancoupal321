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
 * Test cases for main class
 * 
 * @author Evan
 */
public class FinalProjectTest {
    
    private static boolean[][] boardState1; //5 unique board states
    private static boolean[][] boardState2;
    private static boolean[][] boardState3;
    private static boolean[][] boardState4;
    private static boolean[][] boardState5;
    
    @Before
    public void setUp()
    {
        FinalProject.ruleSet = "Conway";
        FinalProject.xCells = 50; //Declare main class's fields
        FinalProject.yCells = 50;
        FinalProject.cellSize = 10;
        FinalProject.playing = false;
        FinalProject.generations = 100;
        FinalProject.mainPanel = new ConwayJPanel();
        FinalProject.mainPanel.generations = new JLabel();
        FinalProject.board = new Board(50, 50);
        
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
     * Test of simStep method, of class FinalProject.
     */
    @Test
    public void testSimStep() {
        System.out.println("simStep");
        FinalProject.board.setCellStates(boardState1); //Sets real cell states to each created cell state 
        boolean[][] result1 = FinalProject.simStep(); //Create cell state array for after 1 passed generation
        FinalProject.board.setCellStates(boardState2);
        boolean[][] result2 = FinalProject.simStep();
        FinalProject.board.setCellStates(boardState3);
        boolean[][] result3 = FinalProject.simStep();
        FinalProject.board.setCellStates(boardState4);
        boolean[][] result4 = FinalProject.simStep();
        FinalProject.board.setCellStates(boardState5);
        boolean[][] result5 = FinalProject.simStep();
        
        boolean[][] expResult = new boolean[50][50]; //Set expected result for the only cell state that doesn't match one of the created ones
        expResult[3][4] = true;
        expResult[3][6] = true;
        expResult[4][5] = true;
        expResult[4][6] = true;
        expResult[5][5] = true;
        
        assertArrayEquals(boardState2, result1); //First and second array should be empty
        assertArrayEquals(boardState2, result2); 
        assertArrayEquals(boardState3, result3); //Third and fourth array should not change
        assertArrayEquals(boardState4, result4);
        assertArrayEquals(expResult, result5); //Fifth array should change into expResult
    }

    /**
     * Test of multiStep method, of class FinalProject.
     */
    @Test
    public void testMultiStep() {
        System.out.println("multiStep");
        FinalProject.board.setCellStates(boardState1); //Sets real cell states to each created cell state 
        FinalProject.multiStep(10); //Create cell state array for after 10 passed generations
        boolean[][] result1 = FinalProject.board.getCellStates();
        FinalProject.board.setCellStates(boardState2);
        FinalProject.multiStep(10);
        boolean[][] result2 = FinalProject.board.getCellStates();
        FinalProject.board.setCellStates(boardState3);
        FinalProject.multiStep(10);
        boolean[][] result3 = FinalProject.board.getCellStates();
        FinalProject.board.setCellStates(boardState4);
        FinalProject.multiStep(10);
        boolean[][] result4 = FinalProject.board.getCellStates();
        FinalProject.board.setCellStates(boardState5);
        FinalProject.multiStep(10);
        boolean[][] result5 = FinalProject.board.getCellStates();
        
        boolean[][] expResult = new boolean[50][50]; //Set expected result for the only cell state that doesn't match one of the created ones
        expResult[5][8] = true;
        expResult[6][6] = true;
        expResult[6][8] = true;
        expResult[7][7] = true;
        expResult[7][8] = true;
        
        assertArrayEquals(boardState2, result1); //First and second array should be empty
        assertArrayEquals(boardState2, result2);
        assertArrayEquals(boardState3, result3);  //Third and fourth array should not change
        assertArrayEquals(boardState4, result4);
        assertArrayEquals(expResult, result5); //Fifth array should change into expResult
    }

    /**
     * Test of randomCells method, of class FinalProject.
     */
    @Test
    public void testRandomCells() {
        System.out.println("randomCells");
        FinalProject.randomCells(); //Only non-random part of this method is clearing the generation count, that is still tested
        assertEquals(0, FinalProject.generations);
    }

    /**
     * Test of clearBoard method, of class FinalProject.
     */
    @Test
    public void testClearBoard() {
        System.out.println("clearBoard");
        boolean[][] expResult = new boolean[50][50]; //Expected result is an empty array
        FinalProject.board.setCellStates(boardState1); //Set board state to created board state
        FinalProject.clearBoard(); //Clear it
        assertArrayEquals(expResult, FinalProject.board.getCellStates()); //Check if the new cell state is empty
        assertEquals(0, FinalProject.generations); //(First one only) check if generation count was cleared
        FinalProject.board.setCellStates(boardState2); //Repeat tests for every board state
        FinalProject.clearBoard();
        assertArrayEquals(expResult, FinalProject.board.getCellStates());
        FinalProject.board.setCellStates(boardState3);
        FinalProject.clearBoard();
        assertArrayEquals(expResult, FinalProject.board.getCellStates());
        FinalProject.board.setCellStates(boardState4);
        FinalProject.clearBoard();
        assertArrayEquals(expResult, FinalProject.board.getCellStates());
        FinalProject.board.setCellStates(boardState5);
        FinalProject.clearBoard();
        assertArrayEquals(expResult, FinalProject.board.getCellStates());
    }
    
    
    /**
     * Test of getCellPos method, of class FinalProject.
     */
    @Test
    public void testGetCellPos()
    {
        System.out.println("getCellPos");
        int[][] expResult = new int[10][2];
        expResult[0][0] = 0; //Set expected results
        expResult[0][1] = 0;
        expResult[1][0] = 3;
        expResult[1][1] = 4;
        expResult[2][0] = 5;
        expResult[2][1] = 6;
        expResult[3][0] = 7;
        expResult[3][1] = 8;
        expResult[4][0] = 25;
        expResult[4][1] = 35;
        expResult[5][0] = 49;
        expResult[5][1] = 49;
        int[][] result = new int[10][2];
        result[0] = FinalProject.getCellPos(0, 0); //Should hit cell 0, 0
        result[1] = FinalProject.getCellPos(36, 50); //Should hit cell 3, 4
        result[2] = FinalProject.getCellPos(61, 75); //Should hit cell 5, 6
        result[3] = FinalProject.getCellPos(77, 90); //Should hit cell 7, 8
        result[4] = FinalProject.getCellPos(280, 395); //Should hit cell 25, 35
        result[5] = FinalProject.getCellPos(539, 549); //Should hit cell 49, 49
        result[6] = FinalProject.getCellPos(10, -10); //Should catch exception handler
        result[7] = FinalProject.getCellPos(10, 1000); //Should catch exception handler
        result[8] = FinalProject.getCellPos(-10, 10); //Should catch exception handler
        result[9] = FinalProject.getCellPos(1000, 10); //Should catch exception handler
        for (int i = 0; i < 6; i++) //For first 6
        {
            assertArrayEquals(expResult[i], result[i]); //Check if expected result matches result
        }
        for (int i = 6; i < 10; i++) //For last 4
        {
            assertNotEquals(expResult[i].length, result[i].length); //Check if length is 1 instead of 2 (this means exception handling worked)
        }
    }
    
}
