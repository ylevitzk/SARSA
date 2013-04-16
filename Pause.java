import java.awt.Graphics;
import java.awt.Color;

public class Pause implements Mode
{
  
  public void draw (Graphics g, int mapSize, int unit, Grid grid)
  {   
    g.setColor(Color.white);
    g.fillRect(unit, unit, unit * mapSize, unit * mapSize);
    
    
    for(int i = 0; i < mapSize; i++) 
    {
      for(int j = 0; j < mapSize; j++) 
      {        
        grid.get(i,j).draw(unit,g);       
        grid.drawAgents(g);
        
        g.setColor(Grid.BACKGROUND_COLOR);
        g.drawRect(unit + unit * i, unit + unit * j, unit, unit);
      }
    }    
    
    Color prevColor = g.getColor();
    g.setColor(new Color(0,0,0,105));
    g.fillRect(unit, unit, mapSize*unit, mapSize*unit);
    g.setColor(prevColor);
    g.drawString("Paused", 300, 300);
  }
  
}