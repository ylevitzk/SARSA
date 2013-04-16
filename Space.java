import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Space{

	/**
	 * The grid that represents the simulated environment.
	 **/
	private Grid grid;

	/**
	 * Make and launch the GUI grid.
	 **/
	private void makeGrid () 
	{
		JFrame frame = new JFrame("GridWorld");
		grid = new Grid(this, frame, new JMenuBar(), 650, "map");
		grid.packFrame();
	}

	/**
	 * Make and add the agent to the grid.
	 * */
	private void makeAgent(Algorithm algorithm)
	{
		Agent critter = ConcreteAgent.createAgent(AgentType.STANDARD, grid, algorithm, grid.getMapSize() - 1, grid.getMapSize() -1);
	}

	public Space ()
	{
		makeGrid();
		Algorithm a = grid.getAlgorithm();
	}
	
	public void theRest (Algorithm a)
	{
		makeAgent(a);
		InputProcessor inputProcessor = new InputProcessor(grid);
		grid.startTimer();
	}

	public static void main (String[] arguments)
	{
		Space space = new Space();
	}

}

