public class CellFactory
{
  
  public static Cell createCell (char type, int x, int y, Grid grid, Algorithm algorithm)
  {
    switch(type)
    {
      case '.' :
        return new BaseCell(x, y, grid, algorithm);
      case '|' :
        return new Wall(x, y, grid, algorithm);
      case 'd' :
        return new DeliciousFood(x, y, grid, algorithm);
      case 'p' :
        return null;
      default  :
        return null;
    }
  }
}

