/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.project;

import java.util.Random;

/**
 * The board of cells
 * 
 * @author Evan
 */
public class Board
{
    public static int xCells; //Number of columns of cells
    public static int yCells; //Number of rows of cells
    public static boolean[][] cellStates; //States of each cell
    
    /**
     * Constructor
     * 
     * @param x Number of columns
     * @param y Number of rows
     */
    public Board(int x, int y)
    {
        xCells = x; //Set column nubmer
        yCells = y; //Set row number
        cellStates = new boolean[x][y]; //Initialize cell states
        setRandomCells(); //Defaults to random states
    }
    
    /**
     * Gets the full array of cell states
     * 
     * @return the cell states
     */
    public boolean[][] getCellStates()
    {
        return cellStates;
    }
    
    /**
     * Get the state of a specific cell
     * 
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The state of the cell
     */
    public boolean getOneState(int x, int y)
    {
        return cellStates[x][y];
    }
    
    /**
     * Set the cell states to a completely new array
     * 
     * @param newStates The new cell states
     */
    public void setCellStates(boolean[][] newStates)
    {
        cellStates = newStates;
    }
    
    /**
     * Set the state of a specific cell
     * 
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @param b The new state of the cell
     */
    public void setOneState(int x, int y, boolean b)
    {
        cellStates[x][y] = b;
    }
    
    /**
     * Reverse the state of a specific cell
     * 
     * @param x The x position of the cell
     * @param y The y position of the cell
     */
    public void toggleState(int x, int y)
    {
        cellStates[x][y] = !cellStates[x][y];
    }
    
    /**
     * Set the state of every cell to a random one
     */
    public void setRandomCells()
    {
        Random rand = new Random(); //Random number generator
        for (int i = 0; i < xCells; i++) //For every cell
        {
            for (int j = 0; j < yCells; j++)
            {
                if (rand.nextInt(3) == 0) //Pick a number from 0 to 3
                {
                    setOneState(i, j, true); //Set the cell to be alive if the number is 0
                }
                else
                {
                    setOneState(i, j, false); //Or dead if it is 1, 2, or 3;
                }
            }
        }
    }
    
    /**
     * Count the number of living neighbors a cell has
     * 
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The number of neighbors
     */
    public int livingNeighbourCount(int x, int y)
    {
        int count = 0; //Number of neighbors
        int xAbove, xBelow, yAbove, yBelow; //x and y position of columns/rows above and below the cell
        
        if (x == 0) //If the cell is in the first column, above is in the last column and below is in the second
        {
            xAbove = xCells - 1;
            xBelow = 1;
        }
        else if (x == xCells - 1) //If the cell is in the last column, above is in the second last column and below is in the first
        {
            xAbove = xCells - 2;
            xBelow = 0;
        }
        else //Otherwise, above and below are simply the columns before and after
        {
            xAbove = x-1;
            xBelow = x+1;
        }
        
        if (y == 0) //Repeat for rows
        {
            yAbove = yCells - 1;
            yBelow = 1;
        }
        else if (y == yCells - 1)
        {
            yAbove = yCells - 2;
            yBelow = 0;
        }
        else
        {
            yAbove = y-1;
            yBelow = y+1;
        }
        
        if (cellStates[xAbove][yAbove]) //Add one to the neighbor count if each of the 8 neighboring cells is alive
        {
            count++;
        }
        if (cellStates[x][yAbove])
        {
            count++;
        }
        if (cellStates[xBelow][yAbove])
        {
            count++;
        }
        if (cellStates[xBelow][y])
        {
            count++;
        }
        if (cellStates[xBelow][yBelow])
        {
            count++;
        }
        if (cellStates[x][yBelow])
        {
            count++;
        }
        if (cellStates[xAbove][yBelow])
        {
            count++;
        }
        if (cellStates[xAbove][y])
        {
            count++;
        }
        return count; //Return the correct count
    }
}
