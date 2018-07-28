package funktionsplot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;

/*
 * Klasse (bis auf wenige Änderungen) übernommen von:
 * http://www.bryanesmith.com/docs/rounded-jbuttons/
 * (zuletzt aufgerufen am 27.08.18) 
 */


public class RoundedCornerButton extends JButton {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoundedCornerButton(String title) {
	   super(title);
	   init();
	 }

	 public RoundedCornerButton() {
	   super();
	   init();
	 }

	 private void init() {
	   setBackground(null);
	   setBorder(null);
	   setFocusable(false);
	 }

	 public void paint(Graphics g) {
		 // Farben festlegen
		 Color hintergrund = new Color(79, 182, 187);
		 Color akzent = new Color(118, 56, 3);
		 
		 // Don't draw the button or border
		 this.setContentAreaFilled(false);
		 this.setBorderPainted(false);

		 Graphics2D g2d = (Graphics2D)g;
	      
		 // Anti-aliased lines and text
		 g2d.setRenderingHint(
				 RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
		 g2d.setRenderingHint(
				 RenderingHints.KEY_TEXT_ANTIALIASING,
				 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    
		 // This is needed on non-Mac so text
		 // is repainted correctly!
		 super.paint(g);

		 // Farben setzen
		 g2d.setColor(hintergrund);
		 g2d.fillRoundRect(0,0,getWidth(),getHeight(),18,18);
		 g2d.setColor(akzent);
		 g2d.drawRoundRect(0,0,getWidth()-1,getHeight()-1,18,18);

		 // Determine the label size so can center it
		 FontRenderContext frc = new FontRenderContext(null, false, false);
		 Rectangle2D r = getFont().getStringBounds(getText(), frc);

		 float xMargin = (float)(getWidth()-r.getWidth())/2;
		 float yMargin = (float)(getHeight()-getFont().getSize())/2;
		 
		 // Farbe für Text setzen
		 g2d.setColor(akzent);
		 // Draw the text in the center
		 g2d.drawString(getText(),xMargin,
	     (float)getFont().getSize()+yMargin);
	 }
}
