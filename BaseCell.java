import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class BaseCell extends Cell{
  
  /**
   * @param Set item.
   **/
  public BaseCell (int x, int y, Grid grid, Algorithm algorithm)
  {
    super(x,y,grid, algorithm);
    this.value  = 0;
    this.priority = NONE;
  }
  
  protected void setColor ()
  {
    cellColor = new Color(255, 255, 255, 255); //white
  }
  
  protected void setActions ()
  {
    action = new Action(grid, this);
  }
  
  public boolean interact (Agent agent) { return false; }
  
  public boolean scalable ()
  {
    return true;
  }
  
  public String toString()
  {
	  return "Base";
  }
  
  
}

