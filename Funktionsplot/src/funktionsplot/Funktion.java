package funktionsplot;

import scanner_parser.Knoten;

public class Funktion implements IFunktion{
	private Knoten Wurzel;
	
	@Override
	public double calcAt(Double x) {
		return Wurzel.calcAt(x);
	}

	@Override
	public double[] plot(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte) {
		double[] plot = new double[schritte];
		double x=linkeIntervallgrenze;
		double schrittweite=(rechteIntervallgrenze-linkeIntervallgrenze)/schritte;
		for (int i=0; i< schritte; i++) {
			plot[i]=this.calcAt(x);
			x+=schrittweite;
		}
		return plot;
	}
}
