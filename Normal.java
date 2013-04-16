import java.awt.Graphics;
import java.awt.Color;

public class Normal implements Mode
{
  
  public void draw (Graphics g, int mapSize, int unit, Grid grid)
  {    
    g.setColor(Color.white);
    g.fillRect(unit, unit, unit * mapSize, unit * mapSize);
    
    g.drawString("Episode: #" + grid.getEpisodes(), 20,20);
    
    int average = getAverageMoves(grid);
    
    if(average == 0)
    {
      //do nothing.
    } 
    else 
    {
      g.drawString("Average number of steps to goal: " + average, 20,40);
    }
    
    if(grid.getAgent() != null)
      g.drawString("Speed: " + grid.getAgent().getSpeed(), 20,60);
      
    
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
    
  }
  
  private int getAverageMoves(Grid grid)
  {
    if(grid.getEpisodes() == 0)
      return 0;
    
    return grid.getStepsTaken() / grid.getEpisodes();
    
  }
  
}