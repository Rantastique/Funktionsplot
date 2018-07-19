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
        // Test
        g.setColor(Color.CYAN);
        g.drawLine(20, 20, 100, 100);
        g.setColor(Color.GREEN);
        g.drawLine(20, 20, 100, 20);
        
        // ich habe gelsen, das man das irgendwie noch casten sollte, aber kP, bleibt erstmal als Kommentar
        // Graphics2D g2 = (Graphics2D) g;
        // g2.setColor(Color.WHITE);
	}

}
