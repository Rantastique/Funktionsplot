package funktionsplot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;

public class GraphPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
	
	private static final long serialVersionUID = -7250288945960637817L;
	
	private Point mousePressPos = new Point(0,0);	
	private Boundaries boundaries;  
	private List<Color> plotcolors = new ArrayList<Color>();
	private List<Funktion> funktionen = new ArrayList<Funktion>();
	private List<int[]> plots = new ArrayList<int[]>();
	private TreeMap<Double, Double> nullstellen = new TreeMap<Double,Double>();
	private boolean nst = false;
	
	//Konstruktor
	public GraphPanel() {
		this.setBackground(Color.WHITE);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		setBoundaries(-30,30,100,-100);		
	}
	
	//getter&setter:
	
	public void addFunction(Funktion f) {
		funktionen.add(f);
		autoPlot(f);
		this.repaint();
	}

	public void setBoundaries(double left, double right, double top, double bottom) {
		if(right-left<0.01||right-left>100000) {
			return;
		}
		boundaries = new Boundaries(left, right, top, bottom);
		plots.clear();
		plotcolors.clear();
		for(Funktion f : funktionen) {
			plot(f);
		}		
		this.repaint();
	}
	
	public Boundaries getBoundaries() {
		return this.boundaries;
	}
	
	//reset everything
	public void reset() {
		funktionen.clear();
		plotcolors.clear();
		plots.clear();
		nullstellen.clear();
		nst = false;
		setBoundaries(-30,30,100,-100);	
	}
	
	///Umrechnung x-Wert->Pixel-x-Wert
	private int xToPixel(double x) {
		return (int)((x-boundaries.left)*(getWidth()/(boundaries.right-boundaries.left)));
	}
	
	//umrechnung y-Wert->Pixel-y-Wert
	private int yToPixel(double y) {
		return (int)(getHeight()-(y-boundaries.bottom)*(getHeight()/(boundaries.top-boundaries.bottom)));
	}
	
	//passt vor dem plotten die y-Achsenskalierung den minima und maxima der im Intervall an
	public void autoPlot(Funktion f) {
		TreeMap<Double, Double> wertetabelle = f.berechneWertetabelle(boundaries.left, boundaries.right, getWidth());
		Double max = f.maximumIn(wertetabelle);
		Double min = f.minimumIn(wertetabelle);
		if(min==max) {
			min-=2;
			max+=2;
		}
		if(min.isNaN()) {
			min = (double) -10;
		}
		if(max.isNaN()) {
			max = (double) 10;
		}

		double minplot = min - (max-min)/5;
		double maxplot = max + (max-min)/5;
		setBoundaries(boundaries.left, boundaries.right, maxplot, minplot);
	}
	
	//Berechnung der Pixelwerte einer Funkntion
	private void plot(Funktion f) {		
		TreeMap<Double, Double> wertetabelle = f.berechneWertetabelle(boundaries.left, boundaries.right, getWidth());
		
		//zum Debugging von der Funktion Nullstellen
		f.nullstellen(boundaries.left, boundaries.right, getWidth());
		//
		f.Extremstellen(boundaries.left, boundaries.right, getWidth());
		
		int x = 0;
		int[] y = new int[getWidth()];
		for (Double value : wertetabelle.values()) {
			y[x] = yToPixel(value);

			x++;
		}
		plots.add(y);
		plotcolors.add(f.plotColor);
	}
	
	public void findeNullstellen() {
		if(!funktionen.isEmpty()) {
			nullstellen = funktionen.get(0).nullstellen(boundaries.left, boundaries.right, getWidth());
			if (!nullstellen.isEmpty()) {
				nst = true;
			}	
		}
	}
	
	
	// "getter" für den nst-boolean, um ihn in der Klasse PlotGUI abzufragen
	public boolean hasNst() {
		if (nst == true) {
			return true;
		}
		return false;
	}
	
	public void zeigeAbleitung(Color color) {
		if (!funktionen.isEmpty()) {

			Funktion f = funktionen.get(0).ableitung();
			f.plotColor = color;
			
			addFunction(f);
		}
	}
	
	public Double schnittY() throws Exception {
		if(!funktionen.isEmpty()) {
			return funktionen.get(0).calcAt(0);
		}
		else {
			throw new Exception("'GraphPanel.ArrayList<Funktion> funktionen' ist leer");
		}
	}


	//Alles zeichnen -> aufruf �ber this.repaint();
	@Override
    protected void paintComponent(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
           
        // erzeugt schwarzen Rand um das Panel
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, w-1, h-1);
        
        // erzeugt Achsen
        int xAchsePos=yToPixel(0);
        int yAchsePos=xToPixel(0);
        int xAchseZifferAbst = 20;
        int yAchseZifferAbst = 15;
        if(yAchsePos<0) {
        	yAchsePos=0;
        }
        if(yAchsePos>getWidth()) {
        	yAchsePos=getWidth();
        }
        if(yAchsePos>getWidth()-40) {
        	yAchseZifferAbst = -40;
        }
        if(xAchsePos<0) {
        	xAchsePos=0;
        }
        if(xAchsePos>getHeight()) {
        	xAchsePos=getHeight();
        }
        if(xAchsePos>getHeight()-30) {
        	xAchseZifferAbst = -20;
        }
        g2.drawLine(yAchsePos, 0,  yAchsePos, h);        
        g2.drawLine(0, xAchsePos, w, xAchsePos);
         
        //Markierungen x-Achse
        double schrittwZaehler = 1;
        double schrittwNenner = 1; //workaround um Double-Ungenauigkeiten beim aufaddieren zu umgehen
        double intervallbreite = boundaries.right-boundaries.left;
        if(intervallbreite >= 15) {
        	while(intervallbreite/schrittwZaehler>15) {
        		schrittwZaehler*=10;
        	}
        }
        else {
        	while((intervallbreite*schrittwNenner)<1.5) {
        		schrittwNenner*=10;
        	}
        }
    	if (intervallbreite/(schrittwZaehler/schrittwNenner)<5) {
    		schrittwZaehler/=2;
    	}		
        Double xStrich = (double) 0;
        while(xStrich/schrittwNenner<boundaries.right) {
        	xStrich+=schrittwZaehler;
        	g2.drawLine(xToPixel(xStrich/schrittwNenner), xAchsePos-3, xToPixel(xStrich/schrittwNenner), xAchsePos+3 );
        	g2.drawString(((Double)(xStrich/schrittwNenner)).toString(), xToPixel(xStrich/schrittwNenner)- 4  , xAchsePos + xAchseZifferAbst );
        }
        xStrich= (double) 0;
        while(xStrich/schrittwNenner>boundaries.left) {
        	xStrich-=schrittwZaehler;
        	g2.drawLine(xToPixel(xStrich/schrittwNenner), xAchsePos-3, xToPixel(xStrich/schrittwNenner), xAchsePos+3 );
        	g2.drawString(((Double)(xStrich/schrittwNenner)).toString(), xToPixel(xStrich/schrittwNenner)- 4  , xAchsePos + xAchseZifferAbst );
        }
        
        //Markierungen Y-Achse
        schrittwZaehler = 1;
        schrittwNenner = 1;
        double intervallhoehe = boundaries.top-boundaries.bottom;
        if(intervallhoehe >= 15) {
        	while(intervallhoehe/schrittwZaehler>15) {
        		schrittwZaehler*=10;
        	}
        }
        else {
        	while((intervallhoehe*schrittwNenner)<1.5) {
        		schrittwNenner*=10;
        	}
        }
    	if (intervallhoehe/schrittwZaehler<5) {
    		schrittwZaehler/=2;
    	}		
        Double yStrich=(double) 0;
        while(yStrich/schrittwNenner<boundaries.top) {
        	yStrich+=schrittwZaehler;
        	g2.drawLine(yAchsePos+3, yToPixel(yStrich/schrittwNenner),yAchsePos-3,yToPixel(yStrich/schrittwNenner));
        	g2.drawString(((Double)(yStrich/schrittwNenner)).toString(), yAchsePos+yAchseZifferAbst, yToPixel(yStrich/schrittwNenner)-4);
        }
        yStrich=(double) 0;
        while(yStrich/schrittwNenner>boundaries.bottom) {
        	yStrich-=schrittwZaehler;
        	g2.drawLine(yAchsePos+3, yToPixel(yStrich/schrittwNenner),yAchsePos-3,yToPixel(yStrich/schrittwNenner));
        	g2.drawString(((Double)(yStrich/schrittwNenner)).toString(), yAchsePos+yAchseZifferAbst, yToPixel(yStrich/schrittwNenner)-4);
        }
        
        //zeichne Funktionen ein
        for(int i = 0; i < plots.size(); i++) {
        	g2.setColor(plotcolors.get(i));
        	int[] plot=plots.get(i);
        	for(int x = 0; x < getWidth(); x++) {
        		if(x==0)
        		{
            		g.drawRect(x, plot[x], 1, 1);
        		}
        		else {
        			g.drawLine(x, plot[x-1], x, plot[x]);
        		}
        	}
        }
        
        //Berechnet neu die Nullstellen, und zeichnet sie, falls nst ==true (der Knopf gedr�ckt)
        if(nst == true) {
			findeNullstellen();
        	Iterator<Map.Entry<Double, Double>> it = nullstellen.entrySet().iterator();
             int xPix = 0;
             int yPix = yToPixel(0);
             Color oldColor = g.getColor();
             g.setColor(Color.RED);
             
             while(it.hasNext()){
             	 Map.Entry<Double, Double> en = it.next();
             	 xPix = xToPixel(en.getKey());
             	 //g.drawLine(xPix, yPix+5, xPix, yPix-5);
             	 g.drawRect(xPix-1, yPix -5, 2, 10);
             	 g.drawString(en.getKey().toString(), xPix-8, yPix-15 );
             }
             g.setColor(oldColor);
        }
        
        // Achsenbezeichnung
        // Bezeichnungen weiß hinterlegen
        g2.setColor(Color.WHITE);
        g2.fillRect(w - 30, xAchsePos+xAchseZifferAbst-12, 20, 20);
        g2.fillRect(yAchsePos+yAchseZifferAbst, 10, 20, 20);
        
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Bold", Font.BOLD, 18));
        
        
        g2.drawString("x", w - 30, xAchsePos+xAchseZifferAbst);
        g2.drawString("y", yAchsePos+yAchseZifferAbst, 20);
	}

	//MouselListener-Methoden:
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//probeweise boundaries beim draggen updaten
		//aufr�umen falls es so bleiben soll
		Point Offset = new Point(0,0);
		Offset.x = -(mousePressPos.x-e.getX());
		Offset.y = (e.getY()-mousePressPos.y);
		
		double xShift = -(Offset.x*(boundaries.right-boundaries.left))/this.getWidth();
		double yShift = (Offset.y*(boundaries.top-boundaries.bottom))/this.getHeight();
		
		setBoundaries(boundaries.left+xShift, boundaries.right+xShift, boundaries.top+yShift, boundaries.bottom+yShift);
		mousePressPos.x=e.getX();
		mousePressPos.y=e.getY();
		Offset.x=0;
		Offset.y=0;
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressPos = new Point(e.getX(), e.getY());;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
				
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double zoomFactor = 1.5;
		double width = boundaries.right-boundaries.left;
		double height = boundaries.top-boundaries.bottom;
		double xDirection = (double)e.getX()/getWidth();
		double yDirection = (double)e.getY()/getHeight();
		if(e.getWheelRotation()==-1) {			
			setBoundaries(boundaries.left-((width*zoomFactor-width)*(xDirection)), boundaries.right+((width*zoomFactor-width)*(1-xDirection)),
					boundaries.top+((height*zoomFactor-height)*(yDirection)), boundaries.bottom-((height*zoomFactor-height)*(1-yDirection)));
		}
		else {
			setBoundaries(boundaries.left-(width/zoomFactor-width)*(xDirection), boundaries.right+(width/zoomFactor-width)*(1-xDirection),
					boundaries.top+(height/zoomFactor-height)*(yDirection), boundaries.bottom-(height/zoomFactor-height)*(1-yDirection));
		}
	}
	
	public ArrayList<Double> getNst() {
		findeNullstellen();
    	Iterator<Map.Entry<Double, Double>> it = nullstellen.entrySet().iterator();
    	ArrayList<Double> nst = new ArrayList<>();
         
         while(it.hasNext()){
         	 Map.Entry<Double, Double> en = it.next();
         	 nst.add(en.getKey());
         }
         return nst;
	}


}
