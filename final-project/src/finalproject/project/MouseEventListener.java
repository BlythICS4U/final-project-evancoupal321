/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.project;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Listens for mouse actions
 * 
 * @author Evan
 */
public class MouseEventListener implements MouseListener
{
    public void mousePressed(MouseEvent e){} //Unused but required mouse event methods
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    
    /**
     * Detects a mouse click and sends the coordinates to the main class
     * 
     * @param e Mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Point p = e.getPoint(); //Gets the coordinates
        FinalProject.getMouseClick(p.getX(), p.getY()); //Changes the state of the cell at the given coordinates
    }
}
