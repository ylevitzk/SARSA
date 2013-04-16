import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class InputReceiver implements MouseListener, KeyListener
{
  /* *
   * The grid associated with this input handler. 
   * Input will only be applied to this grid.
   * */
  protected GUIPanel fancyPanel;
  
  /* *
   * The object that responds appropriately to
   * input.
   * */
  private InputHandler handler;
  
  InputReceiver (GUIPanel fancyPanel, InputHandler handler) 
  {
    this.fancyPanel = fancyPanel;
    fancyPanel.addMouseListener(this);
    fancyPanel.addKeyListener(this);
    fancyPanel.requestFocusInWindow();
    
    this.handler = handler;
  }
  
  public void mouseExited   (MouseEvent e) { handler.exited(e);        }
  public void mouseEntered  (MouseEvent e) { handler.entered(e);       }
  public void mouseReleased (MouseEvent e) { handler.mouseReleased(e); }
  public void mousePressed  (MouseEvent e) { handler.mousePressed(e);  }
  public void mouseClicked  (MouseEvent e) { handler.clicked(e);       }
  
  public void keyReleased   (KeyEvent e)   { handler.keyReleased(e);   }
  public void keyPressed    (KeyEvent e)   { handler.keyPressed(e);    }
  public void keyTyped      (KeyEvent e)   { handler.typed(e);         }
  
}