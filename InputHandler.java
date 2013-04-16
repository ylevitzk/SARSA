import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public abstract class InputHandler
{
  /* *
   * The grid associated with this input handler. 
   * Input will only be applied to this grid.
   * */
  protected GUIPanel fancyPanel;
  
  /* *
   * The InputReceiver actually receives the
   * events and actions.
   * */
  private InputReceiver inputReceiver;
  
  InputHandler (GUIPanel fancyPanel) 
  {
    this.fancyPanel = fancyPanel;
    inputReceiver = new InputReceiver(fancyPanel, this);
  }
  
  protected void exited        (MouseEvent e){};
  protected void entered       (MouseEvent e){};
  protected void mouseReleased (MouseEvent e){};
  protected void mousePressed  (MouseEvent e){};
  protected void clicked       (MouseEvent e){};
  
  protected void keyReleased   (KeyEvent e){};
  protected void keyPressed    (KeyEvent e){};
  protected void typed         (KeyEvent e){};
  
  
}