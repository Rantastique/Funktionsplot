package funktionsplot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	
	public GraphPanel() {
		this.setBackground(Color.WHITE);
		
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
        
        // erzeugt Achsen-Rohlinge
        g2.drawLine(w/2, 0,  w/2, h);
        g2.drawLine(0, h/2, w, h/2);
        
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
        
        
        g2.drawString("x", w - 30, h/2 + 20);
        g2.drawString("y", w/ 2 - 20, 20);
        

        
	}
	

}
