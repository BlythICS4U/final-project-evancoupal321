/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.project;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.*;

/**
 * JPanel that displays the main simulation
 * 
 * @author Evan
 */
public class ConwayJPanel extends JPanel
{
    public JLabel generations; //Label to show the number of generations passed
    
    /**
     * Updates the JPanel by drawing the correct image based on cell states
     * 
     * @param g Graphics object
     */
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.LIGHT_GRAY); //"Clears" the panel by drawing over it
        g.fillRect(0, 0, getWidth(), getHeight());

        //Get variables from main method
        int cellSize = FinalProject.cellSize; //Size of each cell
        int xCells = FinalProject.xCells; //Number of columns of cells
        int yCells = FinalProject.yCells; //Number of rows of cells
        int boardWidth = FinalProject.boardWidth; //Width of board, calculated with cellSize and xCells
        int boardHeight = FinalProject.boardHeight; //Height of board, calculated with cellSize and yCells
        boolean[][] cellStates = FinalProject.board.getCellStates(); //The states of the cells

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, boardWidth, boardHeight); //Border lines
        for (int i = 1; i <= xCells; i++)
        {
            g.drawLine(i*(cellSize+1), 0, i*(cellSize+1), boardHeight); //Vertical lines

        }
        for (int i = 1; i <= yCells; i++)
        {
            g.drawLine(0, i*(cellSize+1), boardWidth, i*(cellSize+1)); //Horizontal lines
        }

        for (int i = 0; i < xCells; i++) //Draws the cells
        {
            for (int j = 0; j < yCells; j++)
            {
                if (cellStates[i][j])
                {
                    g.setColor(Color.RED); //Draw using red if the cell is alive...
                }
                else
                {
                    g.setColor(Color.WHITE); //...or white if the cell is dead
                }
                g.fillRect(i*(cellSize+1)+1, j*(cellSize+1)+1, cellSize, cellSize); //Draw the square in the appropriate spot
            }
        }
    }
    
    /**
     * Sets the generations JLabel to display the correct number of generations past
     * 
     * @param gens the number of generations
     */
    public void setGenNum(int gens)
    {
        generations.setText("Generation " + gens);
    }
    
    
    /**
     * Declares and initializes all Swing components
     * 
     * @param ui Keeps track of where to place buttons, text fields, etc.
     */
    public void createComponents(int ui)
    {
        MouseEventListener listener = new MouseEventListener(); //Mouse Event Detector
        addMouseListener(listener);
        
        //Components
        JButton start = new JButton("Start"); //Button to start the simulation
        JButton stop = new JButton("Stop"); //Button to start the simulation
        JButton step = new JButton("Step:"); //Button to move the simulation a given number of generations
        JTextField stepTextField = new JTextField(); //Text field used to set the number of generations to move
        JButton random = new JButton("Random"); //Button to set the cells to random states
        JButton clear = new JButton("Clear"); //Button to set all cells to be dead
        JButton settings = new JButton("Settings"); //Button to enter the settings UI
        generations = new JLabel("Generation " + FinalProject.getGens()); //Label to show the number of generations passed
        
        start.addActionListener(new java.awt.event.ActionListener() { //Activates if Start button is pressed
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalProject.play();
            }
        });
        stop.addActionListener(new java.awt.event.ActionListener() { //Activates if Stop button is pressed
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalProject.stop();
            }
        });
        step.addActionListener(new java.awt.event.ActionListener() { //Activates if Step button is pressed
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try
                {
                    FinalProject.multiStep(Integer.parseInt(stepTextField.getText()));
                    repaint(); //Redraws the panel
                }
                catch (NumberFormatException ex) //If the number is not a number (i.e. non-numeral characters)
                {
                    stepTextField.setText("ERROR"); //Error message put in text field
                }
            }
        });
        random.addActionListener(new java.awt.event.ActionListener() { //Activates if Random button is pressed
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalProject.randomCells();
                repaint(); //Redraws the panel
            }
        });
        clear.addActionListener(new java.awt.event.ActionListener() { //Activates if Clear button is pressed
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalProject.clearBoard();
                repaint(); //Redraws the panel
            }
        });
        settings.addActionListener(new java.awt.event.ActionListener() { //Activates if Settings button is pressed
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalProject.openSettings(); //Opens the settings UI
            }
        });
                
        start.setBounds(10, FinalProject.boardHeight+5, 50, 20); //Set locations of components based on UI value
        switch (ui)
        {
            case 1:
                stop.setBounds(70, FinalProject.boardHeight+5, 50, 20);
                step.setBounds(130, FinalProject.boardHeight+5, 50, 20);
                stepTextField.setBounds(190, FinalProject.boardHeight+5, 50, 20);
                random.setBounds(250, FinalProject.boardHeight+5, 50, 20);
                clear.setBounds(310, FinalProject.boardHeight+5, 50, 20);
                settings.setBounds(370, FinalProject.boardHeight+5, 50, 20);
                generations.setBounds(430, FinalProject.boardHeight+5, 100, 20);
                break;
            case 2:
                stop.setBounds(70, FinalProject.boardHeight+5, 50, 20);
                step.setBounds(130, FinalProject.boardHeight+5, 50, 20);
                stepTextField.setBounds(190, FinalProject.boardHeight+5, 50, 20);
                random.setBounds(10, FinalProject.boardHeight+35, 50, 20);
                clear.setBounds(70, FinalProject.boardHeight+35, 50, 20);
                settings.setBounds(130, FinalProject.boardHeight+35, 50, 20);
                generations.setBounds(190, FinalProject.boardHeight+35, 100, 20);
                break;
            case 3:
                stop.setBounds(70, FinalProject.boardHeight+5, 50, 20);
                step.setBounds(130, FinalProject.boardHeight+5, 50, 20);
                stepTextField.setBounds(10, FinalProject.boardHeight+35, 50, 20);
                random.setBounds(70, FinalProject.boardHeight+35, 50, 20);
                clear.setBounds(130, FinalProject.boardHeight+35, 50, 20);
                settings.setBounds(10, FinalProject.boardHeight+65, 50, 20);
                generations.setBounds(70, FinalProject.boardHeight+65, 100, 20);
                break;
            case 5:
                stop.setBounds(70, FinalProject.boardHeight+5, 50, 20);
                step.setBounds(10, FinalProject.boardHeight+35, 50, 20);
                stepTextField.setBounds(70, FinalProject.boardHeight+35, 50, 20);
                random.setBounds(10, FinalProject.boardHeight+65, 50, 20);
                clear.setBounds(70, FinalProject.boardHeight+65, 50, 20);
                settings.setBounds(10, FinalProject.boardHeight+95, 50, 20);
                generations.setBounds(10, FinalProject.boardHeight+125, 100, 20);
                break;
            default:
                stop.setBounds(10, FinalProject.boardHeight+35, 50, 20);
                step.setBounds(10, FinalProject.boardHeight+65, 50, 20);
                stepTextField.setBounds(10, FinalProject.boardHeight+95, 50, 20);
                random.setBounds(10, FinalProject.boardHeight+125, 50, 20);
                clear.setBounds(10, FinalProject.boardHeight+155, 50, 20);
                settings.setBounds(10, FinalProject.boardHeight+185, 50, 20);
                generations.setBounds(10, FinalProject.boardHeight+215, 100, 20);
                break;
        }
        
        start.setMargin(new Insets(0, -4, 0, -4)); //Set margins for buttons
        stop.setMargin(new Insets(0, -4, 0, -4));
        step.setMargin(new Insets(0, -4, 0, -4));
        random.setMargin(new Insets(0, -4, 0, -4));
        clear.setMargin(new Insets(0, -4, 0, -4));
        settings.setMargin(new Insets(0, -4, 0, -4));
        
        add(start, BorderLayout.SOUTH); //Adds components to the panel
        add(stop, BorderLayout.SOUTH);
        add(step, BorderLayout.SOUTH);
        add(stepTextField, BorderLayout.SOUTH);
        add(random, BorderLayout.SOUTH);
        add(clear, BorderLayout.SOUTH);
        add(generations, BorderLayout.SOUTH);
        add(settings, BorderLayout.SOUTH);
        
        add(new JLabel(), BorderLayout.SOUTH); //Empty JLabel so nothing takes up the entire bottom area
    }

}
