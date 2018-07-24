package funktionsplot;

import java.awt.Color;
import java.util.Map.Entry;
import java.util.TreeMap;

import scanner_parser.Knoten;

public class Funktion implements IFunktion{
	private Knoten wurzel;
	public Color plotColor;
	
	public Funktion(Knoten wurzel) {
		this.wurzel = wurzel;
		plotColor = Color.BLACK;
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
	
	//f�r debugging:
	public void print()
	{
		wurzel.print();
	}

	@Override
	public int[] autoplot(double linkeIntervallgrenze, double rechteIntervallgrenze, int breite,
			int hoehe) {
		int[] plot = new int[breite];
		Double maxplot;
		Double minplot;
		TreeMap<Double,Double> wertetabelle = this.berechneWertetabelle(linkeIntervallgrenze, rechteIntervallgrenze, breite);
		Double max = maximumIn(wertetabelle);
		Double min = minimumIn(wertetabelle);
		
		//finde geeignete skalierung der y-achse
		if(max.isNaN()||min.isNaN()) { //if (max == infinity or min == infinity) //TODO: check if infinity===NaN
			//TODO: find good values for min and max
			maxplot=10.0;
			minplot=-10.0;
		}
		else {
			maxplot=max+(Math.abs(max-min)/20);
			minplot=min-(Math.abs(max-min)/20);
		}
		double plotrange = Math.abs(maxplot-minplot);
		
		int i = 0;
		for(Double x : wertetabelle.values()) {
			plot[i] = (int)(hoehe-(((hoehe/plotrange)*x))-minplot); //stimmt vielleicht ':D
			
			i++;
		}
		
		
		return plot;
	}

}
