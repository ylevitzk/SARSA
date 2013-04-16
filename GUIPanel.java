import javax.swing.*;
import javax.swing.Timer.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public abstract class GUIPanel extends JPanel{
  
  /**
   * Menu Bar for panel.
   **/
  private JMenuBar bar;
  
  /**
   * Associated GUIFrame.
   **/
  private JFrame frame;
  
  /**
   * How often the timer is updated.
   **/
  private static final int DELAY = 50; //in ms.
  
  /**
   * Timer to update the graphics.
   **/
  private javax.swing.Timer timer;
  
  /**
   * Constructor for GUIPanel.
   * @param1 sets the associated frame for this GUIPanel.
   * @param2 sets the bar for this GUIPanel.
   * @param3 & @param4 width and length of window.
   **/
  public GUIPanel (JFrame frame, JMenuBar bar, int width, int length)
  {
    this.frame = frame;
    this.bar   =   bar; 
    
    this.setPreferredSize(new Dimension(width, length));
    
    frame.setResizable(false);
  }
  
  /**
   * Accessors:
   **/
  public JFrame getFrame              () { return frame;           }
  public JMenuBar getJMenuBar         () { return bar;             }
  public void setFrame    (JFrame frame) { this.frame = frame;     }  
  public void setJMenuBar (JMenuBar bar) { this.bar = bar;
                                           frame.setJMenuBar(bar);
                                                                   }  
  public javax.swing.Timer getTimer () { return timer; }
  
  /**
   * Adds objects to container.
   * @param obj is inserted into container.
   **/
  public void addToPanel (Component obj) { this.add(obj); }
  
  /* *
   * Removes object from container.
   * */
  public void removeFromPanel (Component obj) { this.remove(obj); }
  
  /**
   * Packs and sets the Panel into the Frame.
   **/
  public void packFrame ()
  {
    frame.add(this);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTimer();
  }
    
  /**
   * Instantiates the timer.
   **/
  public void setTimer ()
  {
   
    timer = new javax.swing.Timer(DELAY, new ActionListener(){
      public void actionPerformed (ActionEvent e)
      {
        repaint();
        update();
      }
    });
    
  }
  
  /**
   * Start/Stop the timer.
   **/
  public void startTimer () { timer.start(); }
  public void stopTimer  () { timer.stop (); }
  
  /**
   * This method produces the unique graphics for each instance of this class.
   * @param java Graphics.
   **/
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    compose(g);
  }
  
  /* *
   * Requests focus for this window.
   */
  public void focus ()
  {
	  this.requestFocusInWindow();
  }
  
  abstract void compose (Graphics g);
  abstract void update  ();
  
}

