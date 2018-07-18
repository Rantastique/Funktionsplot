package funktionsplot;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlotGUI extends JFrame {
	
	// Elemente deklarieren
	private JPanel p;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem exit, reset;
	private JLabel lTerm, lFarbeGraph, lIntervall, lXAchseVon, lXAchseBis, lYAchseVon, lYAchseBis, lSchnittpunkte,  lNullstellen, lSchnittY, lAbleitung, lFarbeAbleitung;
	private JTextField term, xAchseVon, xAchseBis, yAchseVon, yAchseBis, nullstellen, schnittY;
	private JButton plot, intervallAnpassen, berechnen, anzeigenAbleitung, ableitung;
	private JComboBox farbauswahlGraph, farbauswahlAbleitung;
	
	
	// x-Werte
	private final int X1 = 20;
	private final int X2 = 100;
	private final int X3 = 120;
	private final int X4 = 150;
	private final int X5 = 200;
	private final int X6 = 285;
	
	// width
	private final int W1 = 70;
	private final int W2 = 100;
	private final int W3 = 150;
	private final int W4 = 200;
	
	// height
	private final int H1 = 20;
	private final int H2 = 40;
	
	
	PlotGUI() {
		
		// Frame und Panel konfigurieren
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p=(JPanel) this.getContentPane();
		p.setLayout(null);
		
		// Menue konfigurieren
		menubar = new JMenuBar();
		menu = new JMenu("Menü");
		exit = new JMenuItem("Plotter verlassen");
		reset = new JMenuItem("Zuruecksetzen");
		menubar.add(menu);
		menu.add(reset);
		menu.add(exit);
		
		setJMenuBar(menubar);
		
		// Elemente initialisieren
		// Label
		lTerm = new JLabel("Funktionsterm:");
		lFarbeGraph = new JLabel("Farbe:");
		lIntervall = new JLabel("Intervall festlegen:");
		lXAchseVon = new JLabel("x-Achse von:");
		lXAchseBis = new JLabel("x-Achse bis:");
		lYAchseVon = new JLabel("y-Achse von:");
		lYAchseBis = new JLabel("y-Achse bis:");
		lSchnittpunkte = new JLabel("Schnittpunkte berechnen:");
		lNullstellen = new JLabel("Nullstellen:");
		lSchnittY = new JLabel("mit y-Achse:");
		lAbleitung =  new JLabel("Graph der Ableitung:");
		lFarbeAbleitung = new JLabel("Farbe:");
		
		// TextFields
		term = new JTextField(1);
		xAchseVon = new JTextField(1);
		xAchseBis = new JTextField(1);
		yAchseVon = new JTextField(1);
		yAchseBis = new JTextField(1);
		nullstellen = new JTextField(1);
		schnittY = new JTextField(1);
		
		// Buttons
		plot = new JButton("Plot!");
		intervallAnpassen = new JButton("Anpassen");
		berechnen = new JButton("Berechnen");
		anzeigenAbleitung = new JButton("Anzeigen");
		
		// ComboBoxes
		String[] farben = {"rot", "blau", "gruen", "orange"};
		farbauswahlGraph = new JComboBox(farben);
		farbauswahlAbleitung = new JComboBox(farben);
		
		
		
		// Elemente positionieren im GridLayout
		
		// Linke Seite
		
		// Elemente positionieren 
		// Label
		lTerm.setBounds(X1, 20, W2, H1);
		lFarbeGraph.setBounds(X1, 50, W2, H1);
		lIntervall.setBounds(X1, 140, W3, H1);
		lXAchseVon.setBounds(X1, 170, W2, H1);
		lXAchseBis.setBounds(X5, 170, W2, H1);
		lYAchseVon.setBounds(X1, 200, W2, H1);
		lYAchseBis.setBounds(X5, 200, W2, H1);
		lSchnittpunkte.setBounds(X1, 280, W4, H1);
		lNullstellen.setBounds(X1, 310, W2, H1);
		lSchnittY.setBounds(X1, 340, W2, H1);
		lAbleitung.setBounds(X1, 420, W4, H1);
		lFarbeAbleitung.setBounds(X1, 450, W2, H1);
		
		// TextFields
		term.setBounds(X4, 20, W3, H1);
		xAchseVon.setBounds(X3, 170, W1, H1);
		xAchseBis.setBounds(X6, 170, W1, H1);
		yAchseVon.setBounds(X3, 200, W1, H1);
		yAchseBis.setBounds(X6, 200, W1, H1);
		nullstellen.setBounds(X4, 310, W4, H1);
		schnittY.setBounds(X4, 340, W4, H1);
		
		// Buttons
		plot.setBounds(X2, 80, W2, H2);
		intervallAnpassen.setBounds(X2, 230, W2, H2);
		berechnen.setBounds(X2, 370, W2, H2);
		anzeigenAbleitung.setBounds(X2, 480, W2, H2);
		
		// ComboBoxes
		farbauswahlGraph.setBounds(X4, 50, W3, H1);
		farbauswahlAbleitung.setBounds(X4, 450, W3, H1);
		
		// Elemente dem Panel hinzufuegen
		// Label
		p.add(lTerm);
		p.add(lFarbeGraph);
		p.add(lIntervall);
		p.add(lXAchseVon);
		p.add(lXAchseBis);
		p.add(lYAchseVon);
		p.add(lYAchseBis);
		p.add(lSchnittpunkte);
		p.add(lNullstellen);
		p.add(lSchnittY);
		p.add(lAbleitung);
		p.add(lFarbeAbleitung);
		
		// TextFields
		p.add(term);
		p.add(xAchseVon);
		p.add(xAchseBis);
		p.add(yAchseVon);
		p.add(yAchseBis);
		p.add(nullstellen);
		p.add(schnittY);
		
		// Buttons
		p.add(plot);
		p.add(intervallAnpassen);
		p.add(berechnen);
		p.add(anzeigenAbleitung);
		
		// ComboBoxes
		p.add(farbauswahlGraph);	
		p.add(farbauswahlAbleitung);		
	}
	
	
}
