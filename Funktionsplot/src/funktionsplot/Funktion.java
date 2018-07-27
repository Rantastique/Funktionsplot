package funktionsplot;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

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
	public TreeMap<Double,Double> nullstellen(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte) {
		
		TreeMap<Double, Double> WT = berechneWertetabelle(linkeIntervallgrenze, rechteIntervallgrenze, schritte);
		TreeMap<Double, Double> NST = new TreeMap<Double, Double>();
		
		double altKey = WT.firstKey();
		double altValue = WT.get(WT.firstKey());
		double abweichung = 0.0001;

		for (Map.Entry<Double, Double> entry: WT.entrySet()) {
			if (Math.abs(entry.getValue()) < abweichung) 
			{
				NST.put(entry.getKey(), entry.getValue());				
			}else 
			{
				//if(altValue*entry.getValue()<0) 
				if(altValue<0 && entry.getValue()>0 || altValue>0 && entry.getValue()<0)
				{
					System.out.println("Nullstellenbereich: " + altKey +": " + this.calcAt(altKey) + " - " +entry.getKey()+": " + this.calcAt(entry.getKey()));
					double links = altKey;
					double rechts = entry.getKey();
					double mitte;
					
					do {
						mitte = (links+rechts)/2.0;
						//System.out.println(links + " - "+ this.calcAt(links)+ "       " + mitte + " - " + this.calcAt(mitte)+ "       " + rechts + " - " + this.calcAt(rechts));
						if(((this.calcAt(mitte)) > 0 && (this.calcAt(links) < this.calcAt(rechts))) || ((this.calcAt(mitte) < 0) &&  (this.calcAt(links) > this.calcAt(rechts)))) {
							rechts = mitte;
						}else {
							links = mitte;
						}
						mitte = (links+rechts)/2.0;
					}while(Math.abs(this.calcAt(mitte)) > abweichung);
					
					NST.put(mitte, this.calcAt(mitte));
					
				}
			}
			altKey = entry.getKey();
			altValue = entry.getValue();			
		}
		//Zur Kontrolle:
        System.out.println("Nullstellen:");
		System.out.println();
		
		Iterator<Map.Entry<Double, Double> > it2 = NST.entrySet().iterator();
        while(it2.hasNext()){
            Map.Entry<Double, Double> en = it2.next();
            System.out.println(  en.getKey() + "\" - \"" + en.getValue()  );
        }
		return NST;
	}

}
