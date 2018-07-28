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
		double schrittweite=(rechteIntervallgrenze-linkeIntervallgrenze)/schritte*1.0;
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
	
	//fï¿½r debugging:
	public void print()
	{
		wurzel.print();
	}

	@Override
	public TreeMap<Double,Double> nullstellen(double linkeIntervallgrenze, double rechteIntervallgrenze, int schritte) {
		
		TreeMap<Double, Double> WT = berechneWertetabelle(linkeIntervallgrenze, rechteIntervallgrenze, schritte*2);
		TreeMap<Double, Double> NST = new TreeMap<Double, Double>();
		TreeMap<Double, Double> hilfWT = new TreeMap<Double, Double>();
		
		double altKey = WT.firstKey();
		double altValue = WT.get(WT.firstKey());
		double abweichung = 0.0005; // getestet (optimale Abweichung für Parabeln mit Verschiebung)
		double abweichung2 = 0.000007; // auch getestet (aber nicht bewiesen)
		double schrittweite=(rechteIntervallgrenze-linkeIntervallgrenze)/schritte*1.0;

		for (Map.Entry<Double, Double> entry: WT.entrySet()) 
		{
			System.out.println(entry.getValue());
			
			//Berührungspunkte 
			if (Math.abs(entry.getValue()) < abweichung) 
			{
				System.out.println("gelandet");
				Funktion ableitung = ableitung();
				hilfWT = ableitung.berechneWertetabelle(altKey - schrittweite, entry.getKey()+5*schrittweite, schritte);
				// prüfen, ob Intervall groß genug ist und ein Vorzeichenwechsel vorkommt
				System.out.println("Vorzeichenwechsel? (Ableitung) zwischen: " + hilfWT.get(hilfWT.firstKey()) + " und " + hilfWT.get(hilfWT.lastKey()));
				if(  ((hilfWT.get(hilfWT.firstKey())>0) && (hilfWT.get(hilfWT.lastKey())<0)) || ((hilfWT.firstKey()<0 && hilfWT.get(hilfWT.lastKey())>0 ))   )
				{
					
					System.out.println("Vorzeichenwechsel! (Ableitung) zwischen: " + hilfWT.get(hilfWT.firstKey()) + " und " + hilfWT.get(hilfWT.lastKey()));
					
					double links = altKey - schrittweite;
					double rechts = entry.getKey() + 5*schritte;
					double mitte;
					
					do {
						mitte = (links+rechts)/2.0;
						//System.out.println(links + " - "+ this.calcAt(links)+ "       " + mitte + " - " + this.calcAt(mitte)+ "       " + rechts + " - " + this.calcAt(rechts));
						if(((ableitung.calcAt(mitte)) > 0 && (ableitung.calcAt(links) < ableitung.calcAt(rechts))) || ((ableitung.calcAt(mitte) < 0) &&  (ableitung.calcAt(links) > ableitung.calcAt(rechts)))) {
							rechts = mitte;
						}else {
							links = mitte;
						}
						mitte = (links+rechts)/2.0;
					}while(Math.abs(ableitung.calcAt(mitte)) > abweichung2);
					
					NST.put((double) (Math.round(mitte*10))/10, ableitung.calcAt(mitte));
					System.out.println("put : " + (double) (Math.round(mitte*10))/10);
				}
			
			}
				/**
			{
				System.out.println("gelandet");
				hilfWT = berechneWertetabelle(entry.getKey()-schrittweite, entry.getKey() + schrittweite, 50 ); //um genaure Stelle zu finden
				
				System.out.println("/innere Wertetabelle - x-Bereich: von " + (entry.getKey()-schrittweite) + " bis " + (entry.getKey() + schrittweite) + " /");
				for (Map.Entry<Double, Double> entry2: hilfWT.entrySet()) {
					System.out.println(entry2.getValue());
					if (Math.abs(entry2.getValue()) < abweichung2) {
						NST.put((double) Math.round(entry2.getKey()*10)/10, entry2.getValue()); //nach 10el runden um mehrere Nullstellen zu vermeiden
						System.out.println("add: " + entry2.getValue());
					}
				}
				
				System.out.println("ENDE DER INNEREN WERTETABELLE");
									
			} **/
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
					
					NST.put((double) (Math.round(mitte*10))/10, this.calcAt(mitte));
					System.out.println("put : " + (double)(Math.round(mitte*10))/10);
					
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
