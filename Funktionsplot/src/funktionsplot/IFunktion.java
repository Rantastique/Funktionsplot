package funktionsplot;


import java.util.TreeMap;

public interface IFunktion {
	
	/*
	 * Berechnet den Wert der Funktion an der Selle x
	 * 
	 * @param double x: x Wert
	 * @return: f(x)
	 */
	public Double calcAt(double x);
	
	/*
	 * Erstellt eine Hashmap mit diskreten Funktionswerten
	 * 
	 * @param double linkeIntervallgrenze: linke Intervallgrenze
	 * @param double rechteIntervallgrenze: rechte Intervallgrenze
	 * @param int schritte: Anzahl der Schritte im Intervall -> Anzahl zu berechnenden Stellen
	 * 
	 * @return: HashMap mit diskreten Funktionswerten
	 */
	public TreeMap<Double,Double> getValues(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte);
	public Double maximumIn(TreeMap<Double, Double> values);
	public Double minimumIn(TreeMap<Double, Double> values);
	
	//Vorschläge>:
	//public double maximumIn(double linkeIntervallsgrenze, double rechteIntervallgrenze);
	//public double minimumIn(double linkeIntervallsgrenze, double rechteIntervallgrenze);
	public Funktion ableitung();
	//public double[] nullstellen();
	//public double[] Extremstellen();
	//public double[] Wendestellen();
	//public double globalMax();
	//public double globalMin();
	
}
