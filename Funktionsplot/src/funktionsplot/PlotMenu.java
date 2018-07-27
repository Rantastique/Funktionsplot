package funktionsplot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JMenu;


public class PlotMenu extends JMenu {
	
	public PlotMenu(String title) {
		   super(title);
		   init();
		 }
	
	private void init() {
		   setBackground(Color.BLACK);
		   setBorder(null);
		   setFocusable(false);
		 }
	
	@Override
    protected void paintComponent(Graphics g) {
		Color hintergrund = new Color(79, 182, 187);
		Color schrift = new Color(118, 56, 3);
		// Don't draw the button or border
				 this.setContentAreaFilled(false);
				 this.setBorderPainted(false);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(hintergrund);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Determine the label size so can center it
 		 FontRenderContext frc = new FontRenderContext(null, false, false);
 		 Rectangle2D r = getFont().getStringBounds(getText(), frc);

 		 float xMargin = (float)(getWidth()-r.getWidth())/2;
 		 float yMargin = (float)(getHeight()-getFont().getSize())/2;
 		 
 		 // Farbe für Text setzen
 		 g2d.setColor(schrift);
 		 g2d.setFont(new Font("Bold", Font.BOLD, 14));
 		 // Draw the text in the center
 		 g2d.drawString(getText(),xMargin,
 	     (float)getFont().getSize()+yMargin);

    }

}
