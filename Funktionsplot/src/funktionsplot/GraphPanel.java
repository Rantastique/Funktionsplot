package funktionsplot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JPanel;

public class GraphPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
	/**
	 * autogenerated version id
	 */
	private static final long serialVersionUID = -7250288945960637817L;
	
	Graphics2D g2;
	private Point Offset = new Point(0,0);
	protected Point mousePressPos = new Point(0,0);	
	private Boundaries boundaries;
	public void setBoundaries(double left, double right, double top, double bottom) {
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
  
	private List<Color> plotcolors = new ArrayList<Color>();
	private List<Funktion> funktionen = new ArrayList<Funktion>();
	private List<int[]> plots = new ArrayList<int[]>();
	public void addFunction(Funktion f) {
		funktionen.add(f);
		plot(f);
		this.repaint();
	}
	
	public void reset() {
		funktionen.clear();
		plotcolors.clear();
		plots.clear();
		repaint();
	}
	
	//Umrechnung Funktionswerte->Pixel
	private Point koordinatesToPixel(double x, double y) {
		return new Point(xToPixel(x),yToPixel(y));
	}
	
	///Umrechnung x-Wert->Pixel-x-Wert
	private int xToPixel(double x) {
		return (int)((x-boundaries.left)*(getWidth()/(boundaries.right-boundaries.left)));
	}
	
	//umrechnung y-Wert->Pixel-y-Wert
	private int yToPixel(double y) {
		return (int)(getHeight()-(y-boundaries.bottom)*(getHeight()/(boundaries.top-boundaries.bottom)));
	}
	
	//Berechnung der Pixelwerte einer Funkntion
	private void plot(Funktion f) {		
		TreeMap<Double, Double> wertetabelle = f.berechneWertetabelle(boundaries.left, boundaries.right, getWidth());
		
		//zum Debugging von der Funktion Nullstellen
		f.nullstellen(boundaries.left, boundaries.right, getWidth());
		//
		
		
		int x = 0;
		int[] y = new int[getWidth()];
		double yStepValue = getHeight()/(boundaries.top-boundaries.bottom);
		for (double value : wertetabelle.values()) {
			y[x] = yToPixel(value);

			x++;
		}
		plots.add(y);
		plotcolors.add(f.plotColor);
	}


	
	public GraphPanel() {
		this.setBackground(Color.WHITE);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		setBoundaries(-30,30,100,-100);		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        
        // Strokes definieren
        // fancy Spielerei, kann/muss man evtl der Achsen-Skalierung anpassen
        // Array für gepunkteten Stroke definieren (1 Pixel Farbe, 10 Pixel "durchsichtig")
        float[] werte = {1f, 9f};
        // "normalen" Stroke zwischenspeichern (derzeit nicht gebraucht, aber man weiß ja nie)
        Stroke defaultStroke = g2.getStroke();
        // Stroke für Rasterlinie erstellen
        // Inspiration: http://www.java2s.com/Tutorials/Java/Graphics/Graphics_Settings/Use_dashed_stroke_to_draw_dashed_line_in_Java.htm
        BasicStroke raster = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0.0f, werte, 0.0f);
            
        
        // erzeugt schwarzen Rand um das Panel
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, w-1, h-1);
        
        // erzeugt Achsen
        g2.drawLine(xToPixel(0)+Offset.x, 0,  xToPixel(0)+Offset.x, h);
        
        
        g2.drawLine(0, yToPixel(0)+Offset.y, w, yToPixel(0)+Offset.y);
         
        //Markierungen x-Achse
        double schrittweite = 1;
        double intervallbreite = boundaries.right-boundaries.left;
        if(intervallbreite >= 20) {
        	while(intervallbreite/schrittweite>20) {
        		schrittweite*=10;
        	}
        }
        else {
        	while((intervallbreite/schrittweite)<2) {
        		schrittweite/=10;
        	}
        }
    	if (intervallbreite/schrittweite<5) {
    		schrittweite/=2;
    	}		
        Double xStrich=(double) 0;
        while(xStrich<boundaries.right) {
        	xStrich+=schrittweite;
        	g2.drawLine(xToPixel(xStrich)+Offset.x, yToPixel(0)-3+Offset.y, xToPixel(xStrich)+Offset.x, yToPixel(0)+3+Offset.y);
        	g2.drawString(xStrich.toString(), xToPixel(xStrich)- 4 +Offset.x, yToPixel(0)+ 15+Offset.y);
        }
        xStrich=(double) 0;
        while(xStrich>boundaries.left) {
        	xStrich-=schrittweite;
        	g2.drawLine(xToPixel(xStrich)+Offset.x, yToPixel(0)-3+Offset.y, xToPixel(xStrich)+Offset.x, yToPixel(0)+3+Offset.y);
        	g2.drawString(xStrich.toString(), xToPixel(xStrich)- 4 +Offset.x, yToPixel(0)+ 15+Offset.y);
        }
        
        //Markierungen Y-Achse
        schrittweite = 1;
        double intervallhoehe = boundaries.top-boundaries.bottom;
        if(intervallhoehe >= 20) {
        	while(intervallhoehe/schrittweite>20) {
        		schrittweite*=10;
        	}
        }
        else {
        	while((intervallhoehe/schrittweite)<2) {
        		schrittweite/=10;
        	}
        }
    	if (intervallhoehe/schrittweite<5) {
    		schrittweite/=2;
    	}		
        Double yStrich=(double) 0;
        while(yStrich<boundaries.top) {
        	yStrich+=schrittweite;
        	g2.drawLine(xToPixel(0)+3+Offset.x, yToPixel(yStrich)+Offset.y,xToPixel(0)-3+Offset.x,yToPixel(yStrich)+Offset.y);
        	g2.drawString(yStrich.toString(), xToPixel(0)+15+Offset.x, yToPixel(yStrich)-4+Offset.y);
        }
        yStrich=(double) 0;
        while(yStrich>boundaries.bottom) {
        	yStrich-=schrittweite;
        	g2.drawLine(xToPixel(0)+3+Offset.x, yToPixel(yStrich)+Offset.y,xToPixel(0)-3+Offset.x,yToPixel(yStrich)+Offset.y);
        	g2.drawString(yStrich.toString(), xToPixel(0)+15+Offset.x, yToPixel(yStrich)-4+Offset.y);
        }
        
        /*
        g2.setStroke(raster);
        // erzeugt Raster
        for (int i = 10; i <= h - 10; i = i + 10) {
        	g2.drawLine(0, i, w, i);
        }
        */
        
        //zeichne Funktionen ein
        for(int i = 0; i < plots.size(); i++) {
        	g2.setColor(plotcolors.get(i));
        	for(int x = 0; x < getWidth(); x++) {
        		g.drawRect(x+Offset.x, plots.get(i)[x]+Offset.y, 1, 1);
        	}
        }
        
        // Achsenbezeichnung
        g2.setStroke(defaultStroke);
        // Bezeichnungen weiß hinterlegen
        g2.setColor(Color.WHITE);
        g2.fillRect(w - 30, yToPixel(0) + 10 + Offset.y, 20, 20);
        g2.fillRect(xToPixel(0) - 20 + Offset.x, 10, 20, 20);
        
        g2.setColor(Color.DARK_GRAY);
        // Font ändern (fürs erste defaultFont zwischenspeichern)
        Font defaultFont = g2.getFont();
        g2.setFont(new Font("Bold", Font.BOLD, 18));
        
        
        g2.drawString("x", w - 30, yToPixel(0) + 20 + Offset.y);
        g2.drawString("y", xToPixel(0) - 20 + Offset.x, 20);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Offset.x = -(mousePressPos.x-e.getX());
		Offset.y = (e.getY()-mousePressPos.y);
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
		double xShift = -(Offset.x*(boundaries.right-boundaries.left))/this.getWidth();
		double yShift = (Offset.y*(boundaries.top-boundaries.bottom))/this.getHeight();
		
		setBoundaries(boundaries.left+xShift, boundaries.right+xShift, boundaries.top+yShift, boundaries.bottom+yShift);
		Offset.x=0;
		Offset.y=0;
		this.repaint();
				
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


}
