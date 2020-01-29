/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.project;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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
    public static JFrame mainSim; //Main JFrame
    public static ConwayJPanel mainPanel; //JPanel for the main simulation
    public static SettingsWindow settingsWindow = new SettingsWindow(); //Window to change the settings
    public static Timer timer; //Simulation timer
    public static String ruleSet; //Rule set that is currently being used
    public static int buttonSetter; //Manages the position of the buttons based on board width
    public static boolean[] customRules = new boolean[18]; //Custom ruleset (0-8 for alive cell neighbour count, 9-17 for dead cell neighbour count
    
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
        mainSim = new JFrame("Conway's Game of Life"); //Initialize main JFrame
        mainSim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Program stops if the X is pressed
        mainSim.setSize(boardWidth+7, boardHeight+41+(30*buttonSetter)); //Sets the size of the frame based on the size of the board
        mainPanel = new ConwayJPanel(); //Initialize main JPanel
        mainPanel.setLayout(new BorderLayout()); //Set the JPanel's layout type to Border
        mainPanel.createComponents(buttonSetter); //Declare and initialize buttons, labels, etc.
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
        cellSize = 10; //Sets defaults
        xCells = 50;
        yCells = 50;
        ruleSet = "Conway";
        
        runEverything(); //Runs simulation
    }
    
    /**
     * Sets the UI value based on boardWidth
     * 
     * @return UI value
     */
    public static int setButtonSetter()
    {
        if (boardWidth > 540)
        {
            return 1;
        }
        else if (boardWidth > 290)
        {
            return 2;
        }
        else if (boardWidth > 180)
        {
            return 3;
        }
        else if (boardWidth > 120)
        {
            return 5;
        }
        return 8;
    }
    
    /**
     * Starts and runs the simulation
     */
    public static void runEverything()
    {
        generations = 0; //Defaults the number of passed generations at 0
        playing = true; //Simulation starts as active
        boardWidth = setBoardWidth();
        boardHeight = setBoardHeight();
        buttonSetter = setButtonSetter();
        board = new Board(xCells, yCells);  //Initialize board
        timer = new Timer(100, new ActionListener() { //Timer for simulation to move every 100 milliseconds
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
                switch (ruleSet) {
                    case "Conway": //If the ruleset is Conway's
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
                        break;
                    case "Evan": //If the ruleset is Evan's
                        if (board.getOneState(i, j)) //If the cell is alive...
                        {
                            if (neighbours == 3 || neighbours == 4) //...and has 2 or 3 neighbours
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
                        break;
                    default: //If the ruleset is user-created
                        if (board.getOneState(i, j)) //If the cell is alive...
                        {
                            if (customRules[neighbours]) //...and matches the given ruleset
                            {
                                after[i][j] = true; //Make the cell alive
                            }
                        }
                        else //If the cell is dead...
                        {
                            if (customRules[neighbours+9]) //...and matches the given ruleset
                            {
                                after[i][j] = true; //Make the cell alive
                            }
                        }
                        break;
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
    
    /**
     * Opens the settings UI and closes the simulation
     */
    public static void openSettings()
    {
        playing = false; //Stops the simulation
        timer.stop(); //Stops the timer
        mainSim.setVisible(false); //Closes and resets the simulation
        mainSim.removeAll();
        mainSim.dispose();
        settingsWindow.setVisible(true); //Opens the settings UI
        settingsWindow.setResizable(false);
    }
    
    /**
     * Closes the settings and restarts the simulation
     * 
     * @param c Given cell size
     * @param w Given number of columns
     * @param h Given number of rows
     * @param r Given rule set
     */
    public static void resetSim(int c, int w, int h, String r)
    {
        settingsWindow.dispose(); //Closes the settings UI
        mainSim.dispose();
        cellSize = c; //Sets the variables to the user inputted amounts
        xCells = w;
        yCells = h;
        ruleSet = r;
        runEverything(); //Restarts the simulation
    }
    
    /**
     * Creates a custom rule set from user input
     * 
     * @param living User input string for living check
     * @param dead User input string for dead check
     */
    public static void setCustomRules(String living, String dead)
    {
        for (int i = 0; i < 18; i++)
        {
            customRules[i] = false; //Set everything to false
        }
        for (int i = 0; i <= 8; i++)
        {
            for (int j = 0; j < living.length(); j++)
            {
                if (Character.toString(living.charAt(j)).equals(Integer.toString(i))) //If the living string contains each number
                {
                    customRules[i] = true; //Assign it to the appropriate spot in the rule set
                }
            }
        }
        for (int i = 0; i <= 8; i++)
        {
            for (int j = 0; j < dead.length(); j++)
            {
                if (Character.toString(dead.charAt(j)).equals(Integer.toString(i))) //If the dead string contains each number
                {
                    customRules[i+9] = true; //Assign it to the appropriate spot in the rule set
                }
            }
        }
    }
    
}
