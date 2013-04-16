import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Grid extends GUIPanel
{

	/* *
	 * Used to spawn random drops.
	 * */
	private long timeStep = 0;  

	/* *
	 * A map with ASCII characters representing different Items in different cells.
	 * */
	private char[][] map;  

	/* *
	 * Stores the items where they belong in the environment.
	 * */
	private Cell[][] artifacts;

	/* *
	 * Agents associated with this grid.
	 * */
	private Agent agent;

	/* *
	 * The algorithm the agent uses for policy making.
	 * */
	private Algorithm algorithm;

	/* *
	 * The main space object.
	 */
	private Space space;

	/* *
	 * Name of the text file from where the map is read.
	 * */
	private String fileName;

	/**
	 * Dimension of the map.
	 **/
	private int mapSize;

	/**
	 * The width and length of the frame.
	 **/
	private int edgeLength;

	/**
	 * How far the grid appears from the edge of the frame, and also the length of a unit square in the grid.
	 **/
	private int unit;

	/**
	 * Background color.
	 **/
	public static final Color BACKGROUND_COLOR = new Color(0,0,0,255); //black

	/**
	 * Grid color.
	 **/
	public static final Color GRID_COLOR = new Color(255,255,255,0); //white

	/* *
	 * The episode number we're on.
	 * */
	private int episode = 0;

	/* *
	 * The number of steps taken by the agent.
	 * */
	private int stepsTaken = 0;

	/* *
	 * Determines what's drawn on the screen.
	 * */
	private Mode mode = new Normal();

	//     -------------------- Graphical items --------------------     //

	private JRadioButton SARSA = new JRadioButton("Sarsa", false);
	private JRadioButton AC    = new JRadioButton("Actor-Critic", false);

	private JButton LOWER = new JButton("Lower Reward");
	private JButton RAISE = new JButton("Raise Reward");
	private JButton RANDO = new JButton("Randomize Start");

	private ButtonGroup buttonGroup = new ButtonGroup();

	private ActionListener listener = new ActionListener ()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == AC)
			{
				algorithm = new ActorCritic();
				space.theRest(getAlgorithm());
			}
			else if(e.getSource() == SARSA)
			{
				algorithm = new Sarsa();
				space.theRest(getAlgorithm());
			}
			else if(e.getSource() == LOWER)
			{
				setFinalReward(getFinalReward() / 2);
			}
			else if(e.getSource() == RAISE)
			{
				setFinalReward(getFinalReward() + 100);
				if(getFinalReward() > 500)
					setFinalReward(500);
			}
			else if(e.getSource() == RANDO)
			{
				agent.randomize();
			}
			if(e.getSource() == AC || e.getSource() == SARSA)
			{
				AC.removeActionListener(listener);
				AC.setVisible(false);
				SARSA.removeActionListener(listener);
				SARSA.setVisible(false);
				
				LOWER.setVisible(true);
				LOWER.addActionListener(listener);
				
				RAISE.setVisible(true);
				RAISE.addActionListener(listener);
				
				//RANDO.setVisible(true);
				//RANDO.addActionListener(listener);
			}
			focus();
		}
	};

	//     -------------------- Graphical items --------------------     //

	/**
	 * Constructs a Grid instance.
	 * @param1 associated JFrame with this JPanel.
	 * @param2 associated JMenuBar with this JPanel/JFrame.
	 * @param3 the dimension of the frame. dim x dim.
	 * @param4 the value of GRID_SIZE.
	 **/
	public Grid(Space space, JFrame frame, JMenuBar bar, int dimension, String fileName)
	{
		super(frame, bar, dimension, dimension);

		// Graphical:
		buttonGroup.add(SARSA);
		buttonGroup.add(AC);

		this.addToPanel(SARSA);
		this.addToPanel(AC);
		
		this.addToPanel(LOWER);
		this.addToPanel(RAISE);
		this.addToPanel(RANDO);

		AC.addActionListener(listener);
		SARSA.addActionListener(listener);
		
		LOWER.setVisible(false);
		RAISE.setVisible(false);
		RANDO.setVisible(false);

		this.space = space;

		//fill in the map array
		try
		{
			makeMap(this.fileName = fileName);
		} catch(IOException e) {} ;

		edgeLength = dimension;

		// the length of the cell, the border is equal to the width of one cell
		unit = ( edgeLength / ( mapSize + 2) );

		// set the background black
		this.setBackground(BACKGROUND_COLOR);
	}

	/**
	 * Create the environment and its cells.
	 **/
	private void makeMap (String fileName) 
	throws IOException
	{    
		Scanner reader = new Scanner(new FileReader(fileName+".txt"));

		//First entry in the file is a number, the size of the map.
		mapSize = reader.nextInt();

		//Skip to next line
		reader.nextLine();

		//Create the map array
		map = new char[mapSize][mapSize];
		artifacts = new Cell[mapSize][mapSize];

		for(int row = 0;  row < mapSize; row++)
		{
			String currentLine = reader.nextLine();
			for(int column = 0; column < currentLine.length(); column++) 
			{
				map[row][column] = currentLine.charAt(column);

				artifacts[row][column] = CellFactory.createCell(map[row][column], column, row, this, algorithm);
			}
		}

		reader.close();
	}

	/**
	 * Returns the element at this position in the map.
	 * The origin is located at the upper left.
	 * @param1 the x-coordinate of the map.
	 * @param2 the y-coordinate of the map.
	 **/
	public Cell get(int x, int y) 
	{ 
		return artifacts[y][x]; 
	}

	/**
	 * Clears the map.
	 **/
	private void clearMap ()       
	{
		map = new char[0][0];        
	}

	/**
	 * return map size.
	 * */
	public int getMapSize ()
	{
		return mapSize;
	}

	/* *
	 * Returns the number of episodes.
	 * */
	public int getEpisodes ()
	{
		return episode;
	}

	/* *
	 * Increments how many steps taken.
	 * */
	public void updateSteps ()
	{
		stepsTaken++;
	}

	/* * 
	 * Get how many steps taken so far.
	 * */
	public int getStepsTaken ()
	{
		return stepsTaken;
	}

	/* *
	 * Sets what mode we're in.
	 * */
	public void setMode(Mode newMode)
	{
		mode = newMode;
	}

	/**
	 * Draws the grid space, the items in them, and all obstacles.
	 **/
	void compose(Graphics g)
	{
		mode.draw(g, mapSize, unit, this);
	}

	/**
	 * Updates time based components of grid.
	 * */
	public void update ()
	{
		while(agent == null)
		{
			agent = ConcreteAgent.getAgent();
		}
		agent.update();
		timeStep++;
		if(timeStep >= 50)
		{
			//spawnItem();
			timeStep = 0;
		}
	}

	/* *
	 * Spawns a random item somewhere on the grid.
	 * */
	public void spawnItem ()
	{
		if(artifacts[0][0].priority() == Cell.NONE)
		{
			artifacts[0][0] = new DeliciousFood(0,0,this, algorithm);
		} 
		else if(artifacts[mapSize - 1][mapSize - 1].priority() == Cell.NONE)
		{
			artifacts[mapSize - 1][mapSize - 1] = new DeliciousFood(mapSize - 1,mapSize - 1,this, algorithm);
		}
	}

	/* *
	 * Resets this episode, and begins a new one.
	 * */
	public void reset ()
	{
		timeStep = 0;
		episode++;
		agent.randomize();
		agent = ConcreteAgent.createAgent(AgentType.STANDARD, this, agent.getAlgorithm(), agent.getSpeed(), 
				agent.getStartX(), agent.getStartY());
	}

	/**
	 * @param An artifact to be placed into the grid.
	 * @param2,3 the coordinate of the artifact.
	 * */
	public void addArtifact (Cell artifact, int x, int y)
	{    
		if (artifacts[y][x] == null || artifact.compareTo(artifacts[y][x]) >= 0)
		{
			artifact.setX( x + 1 );
			artifact.setY( y + 1 );
			artifacts[y][x] = artifact;
		}
	}

	/**
	 * Removes an artifact when needed, for example, when a food is eaten.
	 * */
	public Cell removeArtifact (int x, int y)
	{
		artifacts[y][x] = new BaseCell(x, y, this, algorithm);
		return artifacts[y][x];
	}

	/**
	 * Draws all agents in the critters agentGroup.
	 * @param a Graphics object that is passed down all the way from paintcomponent.
	 * */
	public void drawAgents (Graphics g)
	{
		if(agent != null)
			agent.draw(unit, g);
	}

	/* *
	 * Returns the current agent.
	 * */
	public Agent getAgent ()
	{
		return agent;
	}

	/* *
	 * Returns the algorithm used by the agent for policy making.
	 * */
	public Algorithm getAlgorithm ()
	{
		return algorithm;
	}
	
	/* *
	 * Sets the final reward.
	 */
	public void setFinalReward (int r)
	{
		get(0,0).setValue(r);
		if(algorithm.toString().equals("Sarsa"))
		{
			get(0,0).getAction().setActionValue(Move.North, r);
			get(0,0).getAction().setActionValue(Move.South, r);
			get(0,0).getAction().setActionValue(Move.West, r);
			get(0,0).getAction().setActionValue(Move.East, r);
		}
	}
	
	/* *
	 * Returns the final reward.
	 */
	public int getFinalReward ()
	{
		return get(0,0).value();
	}

}

