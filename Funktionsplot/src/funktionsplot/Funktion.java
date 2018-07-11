package funktionsplot;

import java.util.Map.Entry;
import java.util.TreeMap;

import scanner_parser.Knoten;

public class Funktion implements IFunktion{
	private Knoten Wurzel;
	
	@Override
	public Double calcAt(double x) {
		return Wurzel.calcAt(x);
	}

	@Override
	public TreeMap<Double,Double> getValues(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte) {
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

}
