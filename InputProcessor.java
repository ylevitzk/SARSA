import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class InputProcessor extends InputHandler
{
  
  /* *
   * The grid.
   * */
  private Grid grid;
  
  public InputProcessor (Grid grid)
  {
    super(grid);
    this.grid = grid;
  }
  
  protected void clicked       (MouseEvent e)
  {
    if(fancyPanel.getTimer().isRunning())
    {
      grid.setMode(new Pause());
      grid.stopTimer();
      grid.repaint();
    }
    else
    {
      grid.startTimer();
      grid.setMode(new Normal());
    }
  }
  
  protected void keyPressed    (KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT)
      grid.getAgent().speedUp();
    else if(e.getKeyCode() == KeyEvent.VK_LEFT)
      grid.getAgent().slowDown();
  }
  
}