package funktionsplot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuBar;

public class PlotMenubar extends JMenuBar {
	
	private static final long serialVersionUID = 408330153904498774L;

	@Override
    protected void paintComponent(Graphics g) {
		Color hintergrund = new Color(79, 182, 187);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(hintergrund);
        g2d.fillRect(0, 0, getWidth(), getHeight() - 1);

    }
}
