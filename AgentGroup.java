import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class AgentGroup
{
  /**
   * ArrayList holding all the agents.
   * */
  private ArrayList<Agent> critters = new ArrayList<Agent>(0);
  
  /**
   * Update all critters by one time step.
   * */
  public void update ()
  {
    for(Agent a : critters)
      a.update();
  }
  
  public void draw (int size, Graphics g)
  {
    for(Agent a : critters)
      a.draw(size, g);
  }
      
  
  /**
   * Add an agent.
   * */
  public void add (Agent a)
  {
    critters.add(a);
  }
  
  /**
   * Get how many agents in group.
   * */
  public int size ()
  {
    return critters.size();
  }
  
  /**
   * Return all the agents in an array.
   * */
  public Agent[] getAgents ()
  {
    Agent[] a = new Agent[size()];
    
    for(int i = 0; i < size(); i++)
    {
      a[i] = critters.get(i);
    }
    
    return a;
  }
  
}

