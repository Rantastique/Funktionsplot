package funktionsplot;


public interface IFunktion {
	//Vorschl�ge
	public double calcAt(Double x);
	public double[] plot(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte); //R�ckgabewert lieber eine hashmap?
	//public double maximumIn(double[] plot);
	//public double minimumIn(double[] plot);
	//public double maximumIn(double linkeIntervallsgrenze, double rechteIntervallgrenze);
	//public double minimumIn(double linkeIntervallsgrenze, double rechteIntervallgrenze);
	//public Funktion ableitung();
	//public double[] nullstellen();
	//public double[] Extremstellen();
	//public double[] Wendestellen();
	//public double globalMax();
	//public double globalMin();
	
}
