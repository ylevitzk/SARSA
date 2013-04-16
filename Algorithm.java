import java.awt.Graphics;

public abstract class Algorithm
{
  
  /* *
   * The state space.
   * */
  protected Grid grid;
  
  /* *
   * Used to draw anything specific to this algorithm.
   * */
  public abstract void draw      (Graphics g);
  public abstract void drawState (Graphics g, int size, double value, int x, int y, int offset);
  public abstract void drawProbs (Graphics g, int size, double[] probs, int x, int y, int offset);
  
  /* *
   * Updates the policy.
   * */
  public abstract void update  (Cell pprevCell, Cell prevCell, Cell currCell, Move pprevMove, Move prevMove, Move currMove);
  
  /* *
   * Deals with infinite loops.
   */
  public abstract void dealWithLoop (Cell prevCell, Cell currCell, Cell nextCell, Move prevMove, Move currMove, Move nextMove);
  
  public abstract String toString();
  
}