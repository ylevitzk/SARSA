public class Action
{
	public double[] rawProbs = {  1,   1,   1,   1};
	public double[] probs    = {.25, .25, .25, .25};
	private Move[]  moves    = {Move.North, Move.South, Move.West, Move.East};
	public double[] rewards;
	
	/* *
	 * The grid.
	 */
	private Grid grid;
	
	/* *
	 * The cell from which these actions are available. 
	 */
	private Cell cell;
	
	public Action (Grid grid, Cell cell) 
	{
		if(cell.toString().equals("Delicious food"))
			rewards = new double[]{500, 500, 500, 500};
		else
			rewards = new double[]{  0,   0,   0,   0};
		
		this.grid = grid;
		this.cell = cell;
	}

	/* *
	 * Returns a move based on some probabilities.
	 * */
	public Move getMove()
	{
		if(allSame(probs))
			return randomMove();
		
		int index = -1;
		while(index == -1)
		{
			double guess = Math.random();
			//System.out.println("In while loop. Guess: " + guess);
			for(int i = 0; i < 4; i++)
			{
				if(probs[i] > guess)
				{
					if(index == -1)
						index = i;
					else
						if(Math.random() >= 0.5)
							index = i;
				}
			}
		}
		return moves[index];
	}

	/* *
	 * Increase the probability of the move at index,
	 * while decrementing the others accordingly.
	 * */
	public void incProb (Move m)
	{
		int index = -1;
		switch(m)
		{
		case North: index = 0; break;
		case South: index = 1; break;
		case West : index = 2; break;
		case East : index = 3; break;
		}

		rawProbs[index]++;
		
		probs = normalize(rawProbs);
		}

	/* *
	 * Decrease the probability of the move at index,
	 * while incrementing the others accordingly.
	 * */
	public void decProb (Move m)
	{
		int index = -1;
		switch(m)
		{
		case North: index = 0; break;
		case South: index = 1; break;
		case West : index = 2; break;
		case East : index = 3; break;
		}

		rawProbs[index] -= 10;
		if(rawProbs[index] < 1)
			rawProbs[index] = 1;
		probs = normalize(rawProbs);
	}

	/* *
	 * Returns the value of this state-action pair.
	 * */
	public double getActionValue (Move m) 
	{
		int index = -1;
		switch(m)
		{
		case North: index = 0; break;
		case South: index = 1; break;
		case West : index = 2; break;
		case East : index = 3; break;
		}
		return rewards[index];
	}

	/* *
	 * Sets the value of this state-action pair.
	 * */
	public void setActionValue (Move m, double newValue) 
	{
		int index = -1;
		switch(m)
		{
		case North: index = 0; break;
		case South: index = 1; break;
		case West : index = 2; break;
		case East : index = 3; break;
		}
		rewards[index] = newValue;
	}

	/* *
	 * Returns true if this is the highest valued action.
	 * */
	public boolean isHighest (Move m)
	{
		int index = -1;
		switch(m)
		{
		case North: index = 0; break;
		case South: index = 1; break;
		case West : index = 2; break;
		case East : index = 3; break;
		}

		for(int i = 0; i < 4; i++)
		{
			if(i != index && rewards[i] >= rewards[index])
				return false;
		}
		return true;
	}

	/* *
	 * Returns the highest probability.
	 * */
	public double highestProb ()
	{
		double highest = Double.MAX_VALUE;
		for(int i = 0; i < 4; i++)
		{
			if(probs[i] > highest)
			{
				highest = probs[i];
			}
		}
		return highest;
	}
	
	/* *
	 * Returns the lowest probability.
	 * */
	public double lowestProb ()
	{
		double lowest = -Double.MAX_VALUE;
		for(int i = 0; i < 4; i++)
		{
			if(probs[i] < lowest)
			{
				lowest = probs[i];
			}
		}
		return lowest;
	}
	
	/* *
	 * Returns the normalization factor for the probabilities.
	 * */
	public double normalizer (double[] arr)
	{
		if(!allZeroes(arr))
			return arr[0] + arr[1] + arr[2] + arr[3];
		else return 1;
	}
	
	/* *
	 * Returns true iff all probabilities are zero.
	 * */
	 public boolean allZeroes (double[] a)
	 {
		 for(int i = 0; i < 4; i++)
			 if(a[i] != 0)
				 return false;
		 return true;
	 }
		
	 /* *
	  * Returns true iff all probabilities are zero.
	  * */
	 public boolean allSame (double[] a)
	 {
		 for(int i = 0; i < 4; i++)
			 if(a[i] != a[0])
				 return false;
		 return true;
	 }
	 
	 /* *
	  * Returns a random move.
	  * */
	 public Move randomMove ()
	 {
		 int index = (int)(Math.random()*4);
		 return moves[index];
	 }
	 
	 /* *
	  * Reset's probabilities.
	  */
	 public void resetProbs ()
	 {
		probs[0] = probs[1] = probs[2] = probs[3] = 0.25; 
	 }
	 
	 /**
	  * Calculates and fills the probs array.
	  */
	 public void defcalculateProbabijkhkjhlities ()
	 {
		 int x = cell.getX();
		 int y = cell.getY();
		 double[] neighborValues = new double[4];
		 
		 if (y - 1 > 0)
			 neighborValues[0] = grid.get(x, y - 1).value();
		 else
			 neighborValues[0] = 0;
		 if (y + 1 < grid.getMapSize())
			 neighborValues[1] = grid.get(x, y + 1).value();
		 else
			 neighborValues[1] = 0;
		 if (x - 1 > 0)
			 neighborValues[2] = grid.get(x - 1, y).value();
		 else
			 neighborValues[2] = 0;
		 if (x + 1 < grid.getMapSize())
			 neighborValues[3] = grid.get(x + 1, y).value();
		 else
			 neighborValues[3] = 0;
		
		 double norm;
		 if(!allZeroes(neighborValues))
			 norm = normalizer(neighborValues);
		 else 
			 norm = 1;
		 
		 for (int i = 0; i < 4; i++)
		 {
			 probs[i] = neighborValues[i] / norm;
		 }
	 }
	 
	 /* *
	  * Normalizes probabilities.
	  */
	 private double[] normalize(double[] a)
	 {
		 double[] b = new double[a.length];
		 double n = normalizer(a);
		 for(int i = 0; i < a.length; i++)
		 {
			 b[i] = a[i] / n;
		 }
		 return b;
	 }

}