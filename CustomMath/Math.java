package CustomMath;

public class Math {

	public static double decimalRound(double d, int sigfig)
	{
		String powerString = "1";
		for(int i = 0; i < sigfig; i++)
		{
			powerString += '0';
		}
		
		int power    = Integer.parseInt(powerString);
		
		double temp  = java.lang.Math.round(d * power);
		
		return temp / power;
	}
}
