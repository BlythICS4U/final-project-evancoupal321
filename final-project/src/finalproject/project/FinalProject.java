/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.project;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * Main Class
 * 
 * @author Evan
 */
public class FinalProject {
    
    public static int cellSize; //Size of each cell in pixels
    public static int xCells; //Number of columns of cells
    public static int yCells; //Number of rows of cells
    public static int boardWidth; //Width of the board in pixels
    public static int boardHeight; //Height of the board in pixels
    public static int generations; //Number of generations that have passed
    public static Board board; //The board containing the cell states
    public static boolean playing; //Is the simulation active
    public static ConwayJPanel mainPanel = new ConwayJPanel(); //JPanel for the main simulation
    
    /**
     * Sets the sizes of the cells and board
     */
    private static void setSizes(int c, int x, int y)
    {
        cellSize = c;
        xCells = x;
        yCells = y;
        boardWidth = setBoardWidth();
        boardHeight = setBoardHeight();
    }
    
    /**
     * Calculates the width of the board based on cell size and number of columns
     * 
     * @return Width of the board in pixels
     */
    private static int setBoardWidth()
    {
        return xCells * (cellSize + 1); //For every column, add the cell size plus one pixel for the line between cells
    }
    
    /**
     * Calculates the height of the board based on cell size and number of rows
     * 
     * @return Height of the board in pixels
     */
    private static int setBoardHeight()
    {
        return yCells * (cellSize + 1); //For every column, add the cell size plus one pixel for the line between cells
    }
    
    
    /**
     * Declares and initializes Swing objects
     */
    private static void createAndShowGUI()
    {
        JFrame mainSim = new JFrame("Conway's Game of Life"); //Main JFrame
        mainSim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Program stops if the X is pressed
        mainSim.setSize(boardWidth+7, boardHeight+71); //Sets the size of the frame based on the size of the board
        mainPanel.setLayout(new BorderLayout()); //Set the JPanel's layout type to Border
        mainPanel.createComponents(); //Declare and initialize buttons, labels, etc.
        mainSim.add(mainPanel); //Add the panel to the frame
        mainSim.setVisible(true); //Makes the frame visible and not resizable
        mainSim.setResizable(false);
    }

    /**
     * Main method
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        generations = 0; //Defaults the number of passed generations at 0
        playing = true; //Simulation starts as active
        setSizes(10, 175, 80); //Set cellSize to 10 and board to 50x50 cells
        board = new Board(xCells, yCells);  //Initialize board
        Timer timer = new Timer(100, new ActionListener() { //Timer for simulation to move every 100 milliseconds
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playing) //If the simulation is active
                {
                    board.setCellStates(simStep()); //Change cells based on cellular rules
                    generations++; //Increase generation count by 1
                    mainPanel.setGenNum(generations); //Update generation label
                    mainPanel.repaint(); //Redraw the panel
                }
            }
        
        });
        
        timer.start(); //Let the timer start counting
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                createAndShowGUI(); //Activates the UI
            }
        });
        
    }
    
    /**
     * Changes the states of the cells based on the previous generation
     * 
     * @return New cell states after the rules have been applied
     */
    public static boolean[][] simStep()
    {
        boolean[][] after = new boolean[xCells][yCells]; //Declare new generation
        for (int i = 0; i < xCells; i++) //For every cell
        {
            for (int j = 0; j < yCells; j++)
            {
                int neighbours = board.livingNeighbourCount(i, j); //Count the living neighbours
                after[i][j] = false; //Set to false by default
                if (board.getOneState(i, j)) //If the cell is alive...
                {
                    if (neighbours == 2 || neighbours == 3) //...and has 2 or 3 neighbours
                    {
                        after[i][j] = true; //Make the cell alive
                    }
                }
                else //If the cell is dead...
                {
                    if (neighbours == 3) //And has 3 neighbours
                    {
                        after[i][j] = true; //Make the cell alive
                    }
                }
            }
        }
        return after; //Return the new generation
    }
    
    /**
     * Starts the simulation
     */
    public static void play()
    {
        playing = true;
    }
    
    /**
     * Stops the simulation
     */
    public static void stop()
    {
        playing = false;
        
    }
    
    /**
     * Passes through several generations instantaneously
     * 
     * @param n Number of generations to pass through
     */
    public static void multiStep(int n)
    {
        if (!playing) //Only works if the simulation is not active
        {
            for (int i = 0; i < n; i++) //Pass through a generation n times
            {
                board.setCellStates(simStep());;
            }
            generations += n; //Add the appropriate number of generations
            mainPanel.setGenNum(generations); //Set the generation label to the updated amount
            
        }
    }
    
    /**
     * Sets all cells to a random state
     */
    public static void randomCells()
    {
        board.setRandomCells(); //Sets cells
        generations = 0; //Resets generations
        mainPanel.setGenNum(generations); //Set the generation label to the updated amount
    }
    
    /**
     * Sets every cell's state to dead
     */
    public static void clearBoard()
    {
        playing = false; //Stops the simulation
        for (int i = 0; i < xCells; i++) //Sets every cell's state to dead
        {
            for (int j = 0; j < yCells; j++)
            {
                board.cellStates[i][j] = false;
            }
        }
        generations = 0; //Resets generations
        mainPanel.setGenNum(generations); //Set the generation label to the updated amount
    }
    
    /**
     * Gets coordinates of a mouse click and changes the state of the cell that was clicked on
     * 
     * @param x
     * @param y 
     */
    public static void getMouseClick(double x, double y)
    {
        int[] cellPos = getCellPos(x, y); //Calculates cell to toggle
        if (cellPos.length == 2) //If there is an error, getCellPos returns a string of length 1 and the change in cell state does not happen
        {
            board.toggleState(cellPos[0], cellPos[1]); //Toggles the state of the given cell
            mainPanel.repaint(); //Redraws the panel
        }
    }
    
    /**
     * Calculates cell position based on mouse click coordinates
     * 
     * @param x Mouse click x position
     * @param y Mouse click y position
     * @return Array with x and y index of a cell
     */
    public static int[] getCellPos(double x, double y)
    {
        if (x < 0 || x > xCells*(cellSize+1) || y < 0 || y > yCells*(cellSize+1)) //If the click was outside of the range
        {
            int[] n = {-1}; //Return exception handling array
            return n;
        }
        int[] cellPos = new int[2];
        cellPos[0] = (int)x / (cellSize+1); //Sets x index to the correct cell
        cellPos[1] = (int)y / (cellSize+1); //Sets y index to the correct cell
        return cellPos; //Return the indexes
    }
    
    /**
     * Get the number of passed generations
     * 
     * @return number of generations
     */
    public static int getGens()
    {
        return generations;
    }
    
}
