import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class DeliciousFood extends Cell{
  
  /**
   * @param Set item.
   **/
  public DeliciousFood (int x, int y, Grid grid, Algorithm algorithm)
  {
    super(x,y,grid,algorithm);
    this.value  = 500;
    this.priority = HIGHEST;
  }
  
  protected void setColor ()
  {
    cellColor = new Color(255, 20, 147, 255);
  }
  
  protected void setActions ()
  {
    action = new Action(grid, this);
  }
  
  public boolean scalable ()
  {
    return true;
  }
  
  public boolean interact(Agent agent) 
  {
    grid.reset();
    return true;
  }
  
  public String toString()
  {
	  return "Delicious food";
  }
  
  
}

