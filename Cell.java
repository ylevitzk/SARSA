import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public abstract class Cell
{

	/**
	 * Priority list:
	 * */
	protected static final int HIGHEST = 4;
	protected static final int    HIGH = 3;
	protected static final int  MEDIUM = 2;
	protected static final int     LOW = 1;
	protected static final int    NONE = 0;

	protected static final int NEUT = 3;
	protected static final int PREV = 0;
	protected static final int CURR = 1;
	protected static final int NEXT = 2;

	public int status = NEUT;

	/* *
	 * The grid this cell is in.
	 * */
	protected Grid grid;

	/* *
	 * The algorithm used for policy making.
	 * */
	protected Algorithm algorithm;

	/* *
	 * x - coordinate of cell.
	 * */
	protected int x;

	/* *
	 * y - coordinate of cell.
	 * */
	protected int y;

	/* *
	 * The actions available at this state.
	 * */
	protected Action action;

	/**
	 * Color of the cell.
	 * */
	protected Color cellColor;

	/**
	 * The priority of the cell determines whether it can be replaced by another cell.
	 * If the priority of the new cell is equal or higher, then it can.
	 * */
	protected int priority;

	/**
	 * The reward value of being in this cell.
	 * */
	protected int value;

	/* *
	 * Will be true if interacted with, then false after some time.
	 * */
	protected boolean interacted = false;

	/* *
	 * Used to keep track of time based graphics.
	 * */
	protected int timeStep = 0;

	protected Cell (int x, int y, Grid grid, Algorithm algorithm)
	{
		this.grid = grid;
		this.algorithm = algorithm;
		this.x = x;
		this.y = y;
		setColor();
		setActions();
	}

	/* *
	 * Instantiates the actions.
	 * */
	protected abstract void setActions();

	/**
	 * Set the value of cellColor.
	 * */
	protected abstract void setColor ();

	/* *
	 * What happens when we land in this state.
	 * */
	public abstract boolean interact (Agent agent);

	/* *
	 * True iff this artifact can be crossed.
	 * */
	public abstract boolean scalable ();

	/**
	 * Accessors for private fields:
	 * */
	public int getX         ()                { return x;               }
	public int getY         ()                { return y;               }
	public int priority     ()                { return priority;        }
	public int value        ()                { return value;           }
	public void setX        (int x)           { this.x = x;             }
	public void setY        (int y)           { this.y = y;             }
	public void setPriority (int newPriority) { priority = newPriority; }
	public void setValue    (int newValue)    { value = newValue;       }
	public Action getAction ()                { return action;          }

	/**
	 * Draw's the graphics assoicated with this cell.
	 **/
	public void draw (int size, Graphics graphics)
	{
		if(status == NEXT)
			graphics.setColor(Color.red);
		else if(status == PREV)
			graphics.setColor(Color.blue);
		else if(status == CURR)
			graphics.setColor(Color.green);
		else
			graphics.setColor(cellColor);
		
		graphics.fillRect((x + 1) * size, (y + 1) * size, size, size);

		graphics.setColor(Color.black);
		if(algorithm == null)
			algorithm = grid.getAlgorithm();
		else
		{
			algorithm.drawState(graphics, size, value, x, y, -50);
			graphics.setColor(Color.red);
			if(algorithm.toString().equals("Sarsa"))
				algorithm.drawProbs(graphics, size, action.rewards, x, y, 0);
			else
				algorithm.drawProbs(graphics, size, action.probs, x, y, 0);
		}
	}

	public Move getMove ()
	{
		return action.getMove();
	}
	/**
	 * Compares priorities of interactables.
	 * */
	public int compareTo (Cell i)
	{
		if(this.priority() > i.priority())
			return 1;
		else if(this.priority < i.priority() || i == null)
			return -1;
		else
			return 0;
	}

	public abstract String toString();

}

