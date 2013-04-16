import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class ConcreteAgent extends Agent{
	
	/* *
	 * How many times we've repeated the last move.
	 */
	private int loopCount = 0;

	private ConcreteAgent (Grid grid, Algorithm algorithm, int startX, int startY)
	{
		super(grid, algorithm, startX, startY);
	}

	/* *
	 * Returns the only singleton of this class.
	 * */
	public static Agent getAgent () { return THEAGENT; }

	/* *
	 * The singleton object for this class. 
	 * There need not be more than one agent.
	 * */
	private static Agent THEAGENT;

	/* * 
	 * Instantiates and returns THEAGENT.
	 * */
	public static Agent createAgent (AgentType type, Grid grid, Algorithm algorithm, int startX, int startY)
	{
		switch(type)
		{
		case STANDARD:
			THEAGENT = new ConcreteAgent(grid, algorithm, startX, startY);
			return THEAGENT;
		default:
			return null;
		}
	}

	/* *
	 * Instantiates and returns THEAGENT with the given speed.
	 * */
	public static Agent createAgent (AgentType type, Grid grid, Algorithm algorithm, int speed, int startX, int startY)
	{
		switch(type)
		{
		case STANDARD:
			THEAGENT = new ConcreteAgent(grid, algorithm, startX, startY);
			THEAGENT.threshold = speed;
			return THEAGENT;
		default:
			return null;
		}
	}

	/**
	 * Updates to next timestep.
	 * */
	public void update ()
	{
		timestep++;
		if(timestep >= threshold)
		{
			prevMove = currMove;
			currMove = nextMove;

			nextMove = currCell.getMove();

			if(move(nextMove, currCell))
			{				
				nextCell = grid.get(x, y); //nextCell.status = Cell.NEXT;
			}
			else 
			{
				nextCell = currCell; //nextCell.status = Cell.NEXT;
			}

			if(prevCell != null && currCell != null)
			{
				algorithm.update(prevCell, currCell, nextCell, prevMove, currMove, nextMove);
			}
			
			currCell.interact(this);
			prevCell = currCell;
			currCell = nextCell;
			timestep = 0;
		}
	}
	
	private boolean inLoop ()
	{
		if(nextCell == prevCell)
			loopCount++;
		if(loopCount >= 3)
			return true;
		else return false;
	}

	/**
	 * Move through the environment.
	 * */
	private boolean move (Move m, Cell cell)
	{    
		switch(m)
		{
		case North: 
			if (canMoveUp())    
			{
				y--; 
				return true;
			} else {
				cell.getAction().decProb(m);
				return false;
			}
		case South: 
			if (canMoveDown())    
			{
				y++; 
				return true;
			} else {
				cell.getAction().decProb(m);
				return false;
			}
		case West : 
			if (canMoveLeft())    
			{
				x--;
				return true;
			} else {
				cell.getAction().decProb(m);
				return false;
			}
		case East :
			if (canMoveRight())    
			{
				x++; 
				return true;
			} else {
				cell.getAction().decProb(m);
				return false;
			}
		default: return false;
		}
	}
}

