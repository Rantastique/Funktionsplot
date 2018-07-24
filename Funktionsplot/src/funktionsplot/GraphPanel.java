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
import java.util.List;

import javax.swing.JPanel;

public class GraphPanel extends JPanel implements MouseMotionListener, MouseListener {
	/**
	 * autogenerated version id
	 */
	private static final long serialVersionUID = -7250288945960637817L;
	
	protected Point mousePrevPos = new Point(0,0);	
	List<Funktion> funktionen;	
	private Boundaries boundaries;
	public void setBoundaries(double left, double right, double top, double bottom) {
		boundaries = new Boundaries(left, right, top, bottom);
		this.repaint();
	}
	
	public GraphPanel() {
		this.setBackground(Color.WHITE);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		setBoundaries(-30,10,100,-300);		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
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
        int yAxisPos = (int)(-boundaries.left*(w/(boundaries.right-boundaries.left)));
        g2.drawLine(yAxisPos, 0,  yAxisPos, h);
        
        int xAxisPos = (int)(boundaries.top*(h/(boundaries.top-boundaries.bottom)));
        g2.drawLine(0, xAxisPos, w, xAxisPos);
        
        g2.setStroke(raster);
        // erzeugt Raster
        for (int i = 10; i <= h - 10; i = i + 10) {
        	g2.drawLine(0, i, w, i);
        }
        
        // Achsenbezeichnung
        g2.setStroke(defaultStroke);
        // Bezeichnungen weiß hinterlegen
        g2.setColor(Color.WHITE);
        g2.fillRect(w - 30, h/2 + 10, 20, 20);
        g2.fillRect(w/2 - 20, 10, 20, 20);
        
        g2.setColor(Color.DARK_GRAY);
        // Font ändern (fürs erste defaultFont zwischenspeichern)
        Font defaultFont = g2.getFont();
        g2.setFont(new Font("Bold", Font.BOLD, 18));
        
        
        g2.drawString("x", w - 30, xAxisPos + 20);
        g2.drawString("y", yAxisPos - 20, 20);
        

        
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println(mousePrevPos.y + "   " + e.getY());
		double xOffset = ((mousePrevPos.x-e.getX())*(boundaries.right-boundaries.left))/this.getWidth();
		double yOffset = ((e.getY()-mousePrevPos.y)*(boundaries.top-boundaries.bottom))/this.getHeight();

		mousePrevPos.x = e.getX();
		mousePrevPos.y = e.getY();
		
		setBoundaries(boundaries.left+xOffset, boundaries.right+xOffset, boundaries.top+yOffset, boundaries.bottom+yOffset);

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
		mousePrevPos = new Point(e.getX(), e.getY());;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
