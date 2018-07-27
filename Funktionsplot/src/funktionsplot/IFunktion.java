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
	public TreeMap<Double,Double> berechneWertetabelle(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte);
	
	
	/*
	 * gibt den maximalen Wert einer Wertetabelle zur�ck
	 * @param values: TreeMap mit den Funktionswerten
	 * 
	 * @return: h�chster Wert der TreeMap
	 */
	public Double maximumIn(TreeMap<Double, Double> wertetabelle);
	
	/*
	 * gibt den minimalen Wert einer Wertetabelle zur�ck
	 * @param values: TreeMap mit den Funktionswerten
	 * 
	 * @return: niedrigster Wert der TreeMap
	 */
	public Double minimumIn(TreeMap<Double, Double> wertetabelle);
	
	/*
	 * bildet die Ableitung der Funktion
	 * 
	 * @return: Ableitung der Funktion
	 */
	public Funktion ableitung();
	
	//Vorschl�ge>:
	public TreeMap<Double,Double> nullstellen(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte);
	//public double[] Extremstellen();
	//public double[] Wendestellen();
	//public double globalMax();
	//public double globalMin();
	
}
