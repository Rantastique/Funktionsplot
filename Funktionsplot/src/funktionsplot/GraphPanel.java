package funktionsplot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	
	public GraphPanel() {
		this.setBackground(Color.WHITE);
		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // erzeugt schwarzen Rand um das Panel
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
        
        // Tests
        g2.setColor(Color.CYAN);
        g2.drawLine(20, 20, 100, 100);
        g2.setColor(Color.GREEN);
        g2.drawLine(20, 20, 100, 20);
        
	}

}
