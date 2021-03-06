package funktionsplot;

import java.awt.Color;
import java.util.Map;
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
			if( min==null || entry.getValue()<min) {
				min = entry.getValue();
			}
		}
		return min;
	}

	@Override
	public Funktion ableitung() {
		return new Funktion(wurzel.ableitung());
	}
	
	//fuer debugging:
	public void print()
	{
		wurzel.print();
	}

	@Override
	public TreeMap<Double, Double> nullstellen(double linkeIntervallgrenze, double rechteIntervallgrenze,int schritte) {
		
		double linksX = 0; //Intervall, wo sich die Nullstelle befindet
		double rechtsX = 0;
		
		double abweichung = 0.0006;
		double runden = 100.0; // minimum
		
		
		// wie genau die NST gerundet werden m�ssen, abh�ngig vom aktuellen Intervall
		double sw = (rechteIntervallgrenze - linkeIntervallgrenze)/schritte*1.0; //schrittweite
		sw = sw*100;
		int potenz=2; //minimale Genauigkeit
		
		while(sw<=1) {
			sw*=10;
			potenz++;
		}
		
		runden = Math.pow(10, potenz)*1.0;
		
		
		TreeMap<Double, Double> WT = berechneWertetabelle(linkeIntervallgrenze, rechteIntervallgrenze, schritte);
		TreeMap<Double, Double> NST = new TreeMap<Double, Double>();
		// Da die Abweichung nur fuer die Beruehrungspunkte passt, und nicht fuer die normale Schnittpunkte
				// sollte es noch ein Verfahren geben, das zuerst den Vorzeichenwechsel findet und dann in diesem Bereich die Nullstelle
				
				double oldKey = WT.firstKey(); // fuer VZW-Suche
				double oldValue = WT.get(WT.firstKey());
						
		
		for (Map.Entry<Double, Double> entry: WT.entrySet()) {
			if (Math.abs(entry.getValue()) < abweichung) {	// wenn der Punkt sich im bereich der NST befindet
				if(linksX == 0) {				// wenn der Punkt der erste im Intervall ist - > 
					linksX = entry.getKey();	// - > linke Grenze setzen
				}
				rechtsX = entry.getKey();		// immer die rechte Grenze setzen
			}else{								// wenn der Punkt sich (noch/schon) nicht im Intervall befindet
				                                
											     	// wenn man vor kurzen aus dem Intervall weg ist
				if(linksX != 0 && rechtsX != 0) {	// (NST im Intervall noch nicht gesucht)
					
					// such Minimum von abs im Intervall --- > Nullstelle
					Double minY = null;
					Double minX = null;
					TreeMap<Double, Double> values = berechneWertetabelle(linksX, rechtsX, schritte*10);
					
					for (Entry<Double, Double> entry2 : values.entrySet()) {
						if((minY==null || Math.abs(entry2.getValue())<minY)) {
							minY = Math.abs(entry2.getValue());
							minX = entry2.getKey();
						}
					}
					// Nullstelle zur Liste hinzufuegen:
					if( minX != linkeIntervallgrenze)
						NST.put((double) (Math.round(minX*runden)/runden), minY);
					
					// Intervall ist abgearbeitet:
					linksX = 0;
					rechtsX = 0;
			    }
			}
			
			// fuer normale Scnittpunkte (x^(ungeradeZahl) passen auch rein, theoretisch kann zur Doppelnullstellen fuehren)
			
			//letzte Bedingung : Abweichung um die Pollstellen auszuschliessen
			if((oldValue<=0 && entry.getValue()>0 || oldValue>=0 && entry.getValue()<0) && Math.abs(oldValue) < 10000) { 
				
				// such Minimum von abs im Intervall --- > Nullstelle
				
				Double minYY = null;
				Double minXX = null;
				TreeMap<Double, Double> values = berechneWertetabelle(oldKey, entry.getKey(), schritte);
				
				for (Entry<Double, Double> entry2 : values.entrySet()) {
					if( ( minYY==null || Math.abs(entry2.getValue())<minYY)) {
						minYY = Math.abs(entry2.getValue());
						minXX = entry2.getKey();
					}
				}
				// Nullstelle zur Liste hinzufuegen:
				 if( minXX != values.firstKey())  // ohne Raender (wegen Exponentialfunktionen)
					 NST.put((double) (Math.round(minXX*runden)/runden), minYY);
			}
			
			oldKey = entry.getKey();
			oldValue = entry.getValue();
		}
        
		return NST;
	}

	@Override
	public TreeMap<Double, Double> Extremstellen(double linkeIntervallgrenze, double rechteIntervallgrenze,	int schritte) {
		return null; 
	}
}
