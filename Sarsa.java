import java.awt.Graphics;
import CustomMath.Math;

public class Sarsa extends Algorithm
{
	/* *
	 * The learning rate.
	 */
	public static final double ALPHA = 0.1;

	/* *
	 * The discount factor.
	 */
	public static final double GAMMA = 1.0 / 1.2;

	public void draw      (Graphics g) {}
	public void drawState (Graphics g, int size, double value, int x, int y, int offset) 
	{ 
		g.drawString(""+value,(x + 1) * size + size/2,(y + 1) * size + size/2 - offset);
	}
	public void drawProbs (Graphics g, int size, double[] probs, int x, int y, int offset)
	{
		for(int i = 0; i < 4; i++)
			g.drawString(""+Math.decimalRound(probs[i], 2),(x + 1) * size + size/2,(y + 1) * size + size/2 - offset + 10*i);
	}

	public void update (Cell prevCell, Cell currCell, Cell nextCell, Move prevMove, Move currMove, Move nextMove)
	{		
		int preVal = (int) prevCell.getAction().getActionValue(currMove);
		int nexVal = (int) currCell.getAction().getActionValue(nextMove);
		int Q_Val  = (int) (preVal + ALPHA * (GAMMA * nexVal - preVal));
		
		prevCell.getAction().setActionValue(currMove, Q_Val);
		
		if(currCell.getAction().getActionValue(nextMove) > prevCell.getAction().getActionValue(currMove))
		{
			incProb(prevCell, currCell, currMove, nextMove);
		}
		else if(currCell.getAction().getActionValue(nextMove) <= prevCell.getAction().getActionValue(currMove))
				//&& prevCell.getAction().getActionValue(currMove) > 0)
		{
			decProb(prevCell, currCell, currMove, nextMove);
		}
	}

	/* *
	 * Updates probability for current state.
	 * */
	private void incProb (Cell prevCell, Cell currCell, Move currMove, Move nextMove)
	{
		prevCell.getAction().incProb(currMove);
	}
	
	private void decProb (Cell prevCell, Cell currCell, Move currMove, Move nextMove)
	{		
		prevCell.getAction().decProb(currMove);
	}

	public String toString ()
	{
		return "Sarsa";
	}

	public void dealWithLoop (Cell prevCell, Cell currCell, Cell nextCell, Move prevMove, Move currMove, Move nextMove)
	{
		//nextCell.getAction().resetProbs();
		nextCell.getAction().resetProbs();
		nextCell.setValue((int)(nextCell.value() * Sarsa.GAMMA * Sarsa.ALPHA));
	}
}