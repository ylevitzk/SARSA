import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Wall extends Cell{
  
  /**
   * @param Set item.
   **/
  public Wall (int x, int y, Grid grid, Algorithm algorithm)
  {
    super(x,y,grid, algorithm);
    this.value  = 0;
    this.priority = HIGHEST;
  }
  
  protected void setColor ()
  {
    cellColor = new Color(139, 137, 137, 255);
  }
  
  protected void setActions ()
  {
    action = new Action(grid, this);
  }
  
  public boolean interact (Agent agent) { return false; }
  
  public boolean scalable ()
  {
    return false;
  }
  
  public String toString()
  {
	  return "Wall";
  }
  
  
}

