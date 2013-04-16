import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public abstract class Agent{
  
  /* *
   * The grid this Agent is inhabiting.
   * */
  protected Grid grid;
  
  /* *
   * The algorithm used for learning.
   * */
  protected Algorithm algorithm;
  
  /* *
   * The timestep that we're on. Used to tell when to move.
   * */
  protected long timestep = 0;
  
  /* *
   * The amount of time that needs to pass before a move is made.
   * */
  protected int threshold = 5;
  
  /* *
   * x - coordinate of agent, and proper array index.
   * */
  protected int x, startX;
  
  /* *
   * y - coordinate of agent, and proper array index.
   * */
  protected int y, startY;
  
  /* *
   * The size of the gridworld.
   * */
  private int mapSize;
  
  /* *
   * The previously visited cell, and the current one.
   * */
  protected Cell prevCell, currCell, nextCell;
  
  /* *
   * The previous action taken.
   * */
  protected Move nextMove, currMove, prevMove;
  
  protected Agent (Grid grid, Algorithm algorithm, int startX, int startY)
  {
    this.grid = grid;
    this.algorithm = algorithm;
    this.mapSize = grid.getMapSize();
    this.startX = startX;
    this.startY = startY;
    this.x = startX;
    this.y = startY;
    
    prevCell  = null;
    currCell  = grid.get(x, y);
    nextCell  = null;
    prevMove  = null;
    currMove  = null;
    nextMove  = null;
  }
  
  /* *
   * Draw's the graphics assoicated with this agent.
   * */
  public void draw (int size, Graphics graphics)
  {
    Color previousColor = graphics.getColor();
    graphics.setColor(new Color(255, 215, 0, 255)); //gold
    int width = size / 3;
    graphics.fillOval((x + 1) * size + width, (y + 1) * size + width, width, width);
  }
  
  /* *
   * Accessors:
   * */
  public int getX () { return x; }
  public int getY () { return y; }
  public Move getcurrMove () { return currMove; }
  public Cell getcurrCell () { return currCell; }
  
  /**
   * Updates to next timestep.
   * */
  public abstract void update ();
  
  /**
   * Move through the environment.
   * */
  
  
  /* *
   * Makes the agent move faster in the grid.
   * */
  public void speedUp ()
  {
    if(threshold > 0)
      threshold--;
  }
  
  /* *
   * Makes the agent move slower in the grid.
   * */
  public void slowDown ()
  {
    if(threshold < 11)
      threshold++;
  }
  
  /* *
   * Returns the speed of the agent.
   * */
  public int getSpeed ()
  {
    return threshold;
  }
  
  /* *
   * Returns the start positions.
   */
  public int getStartX ()
  {
	  return startX;
  }
  public int getStartY ()
  {
	  return startY;
  }
  
  /* *
   * Returns the algorithm used for learning.
   * */
  public Algorithm getAlgorithm () 
  {
    return algorithm;
  }
  
  /* *
   * Randomizes the starting point.
   */
  public void randomize ()
  {
	  startX = (int)(Math.random()*mapSize);
	  startY = (int)(Math.random()*mapSize);
  }
  
  /**
   * Decide if you can move down/up/right/left.
   * Will we go out of bounds?
   * Is there an obstacle?
   * */
  protected boolean canMoveDown ()
  {
    if( y + 1 >= mapSize )
      return false;
    if(! grid.get(x, y+ 1).scalable() )
      return false;
    
    return true;
  }
  protected boolean canMoveUp ()
  {    
    if (y - 1 < 0 )
      return false;
    if (! grid.get(x, y - 1).scalable() )
      return false;
    
    return true;
  }
  protected boolean canMoveRight ()
  {
    if (x + 1 >= mapSize ){
      return false;
    }
    if (! grid.get(x + 1, y).scalable() )
      return false;
    
    return true;
  }
  protected boolean canMoveLeft ()
  {
    if ( x - 1 < 0 )
      return false;
    if (! grid.get(x - 1, y).scalable() )
      return false;
    
    return true;
  }
}
