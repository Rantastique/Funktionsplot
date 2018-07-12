package funktionsplot;

import java.util.Map.Entry;
import java.util.TreeMap;

import scanner_parser.Knoten;

public class Funktion implements IFunktion{
	private Knoten wurzel;
	
	public Funktion(Knoten wurzel) {
		this.wurzel = wurzel;
	}

	@Override
	public Double calcAt(double x) {
		return wurzel.calcAt(x);
	}

	@Override
	public TreeMap<Double,Double> berechneWertetabelle(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte) {
		TreeMap<Double,Double> plot = new TreeMap<>();
		double x=linkeIntervallgrenze;
		double schrittweite=(rechteIntervallgrenze-linkeIntervallgrenze)/schritte;
		for (int i=0; i< schritte; i++) {
			plot.put(x, this.calcAt(x));
			x+=schrittweite;
		}
		return plot;
	}

	@Override
	public Double maximumIn(TreeMap<Double, Double> values) {
		Double max = null;
		for (Entry<Double, Double> entry : values.entrySet()) {
			if( max==null || entry.getValue()>max) {
				max = entry.getValue();
			}
		}
		return max;
	}

	@Override
	public Double minimumIn(TreeMap<Double, Double> values) {
		Double min = null;
		for (Entry<Double, Double> entry : values.entrySet()) {
			if( min==null || entry.getValue()>min) {
				min = entry.getValue();
			}
		}
		return min;
	}

	@Override
	public Funktion ableitung() {
		return new Funktion(wurzel.ableitung());
	}
	
	//für debugging:
	public void print()
	{
		wurzel.print();
	}

	@Override
	public TreeMap<Integer, Integer> autoplot(double linkeIntervallgrenze, double rechteIntervallgrenze, int breite,
			int höhe) {
		HashMap<> werteTabelle = this.berechneWertetabelle(linkeIntervallgrenze, rechteIntervallgrenze, breite)
	}

}
