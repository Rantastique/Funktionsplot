package funktionsplot;



import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import scanner_parser.FuncParser;
public class PlotGUI extends JFrame implements MouseMotionListener, MouseListener {

	
	// Elemente deklarieren
	private JPanel p;
	private GraphPanel g;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem exit, reset;
	private JLabel lTerm, lFarbeGraph, lIntervall, lXAchseVon, lXAchseBis, lYAchseVon, lYAchseBis, lSchnittpunkte,  lNullstellen, lSchnittY, lAbleitung, lFarbeAbleitung;
	private JTextField term, xAchseVon, xAchseBis, yAchseVon, yAchseBis, nullstellen, schnittY;
	private JButton plot, intervallAnpassen, berechnen, anzeigenAbleitung, ableitung;
	private JComboBox farbauswahlGraph, farbauswahlAbleitung;
	
	// zum Testen
	final JLabel lTest;
	
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
		this.setSize(930, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p=(JPanel) this.getContentPane();
		p.setLayout(null);
		
		// Panel für den Graphen
		g = new GraphPanel();
		g.setBounds(400, 20, 500, 500);	
		// MouseListener um die TextFields für X- und Y-Achse zu aktualisieren
		g.addMouseListener(this);
		
		p.add(g);
		
		// Menue konfigurieren
		menubar = new JMenuBar();
		menu = new JMenu("Menue");
		exit = new JMenuItem("Plotter verlassen");
		reset = new JMenuItem("Zuruecksetzen");
		menubar.add(menu);
		menu.add(reset);
		menu.add(exit);
		
		setJMenuBar(menubar);
		
		// Test
		lTest = new JLabel("Hier könnte Ihre Testnachricht stehen");
		lTest.setBounds(X1, 510, 600, 50);
		p.add(lTest);
		
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
		String[] farben = {"schwarz", "rot", "gruen", "blau", "orange"};
		//Color[] farben = {Color.red, Color.blue, Color.green, Color.orange};
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
		
		// ActionListener hinzufuegen
		plot.addActionListener(e -> plot());
		intervallAnpassen.addActionListener(e -> anpassen());
		berechnen.addActionListener(e -> berechnen());
		anzeigenAbleitung.addActionListener(e -> anzeigen());
		
		
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
	
	// Methoden für das Event Handling beim Klicken der Buttons

	private void plot() {
		String funcString = term.getText();
		String farbe = farbauswahlGraph.getSelectedItem().toString();
		Color farbauswahl = Color.BLACK; // um Variable zu initialisieren
		switch (farbe) {
		case "schwarz": 
			farbauswahl = Color.BLACK;
			break;
		case "rot": 
			farbauswahl = Color.RED;
			break;
		case "gruen": 
			farbauswahl = Color.GREEN;
			break;
		case "blau":
			farbauswahl = Color.BLUE;
			break;
		case "orange":
			farbauswahl = Color.ORANGE;
			break;
		}
		
		lTest.setText(String.format("Plot! wurde gedrueckt. Da steht: %s. Wunschfarbe: %s", funcString, farbe));
		
		if(!funcString.isEmpty()) {
			Funktion f = FuncParser.theParser().parse(funcString);
			if (FuncParser.theParser().getErrcnt() == 0) {
				f.plotColor = farbauswahl;
				g.addFunction(f);
				return;
			}
			String msg = "Fehler bei der Eingabe!";
			JOptionPane.showMessageDialog(this.getParent(), msg, "Eingabefehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String msg = "Das Eingabefeld ist leer.";
		JOptionPane.showMessageDialog(this.getParent(), msg, "Eingabefehler", JOptionPane.ERROR_MESSAGE);
	}
	
	private void anpassen() {
		// aktuellen Inhalt der TextFields abfragen und in double-Werte parsen
		try {
			double xVon = Double.parseDouble(xAchseVon.getText());
			double xBis = Double.parseDouble(xAchseBis.getText());
			double yVon = Double.parseDouble(yAchseVon.getText());
			double yBis = Double.parseDouble(yAchseBis.getText());
			
			// Intervallgrenzen neu setzen
			g.setBoundaries(xVon, xBis, yBis, yVon);
				
			lTest.setText(String.format("Anpassen wurde gedrueckt. Da steht: %s, %s, %s, %s", xVon, xBis, yVon, yBis));
		}
		// Fehlerbehandlung bei Parsing-Fehler
		catch(NumberFormatException e ) {
			String msg = "Bitte Intervallgrenze im Format #.## angeben";
			JOptionPane.showMessageDialog(this.getParent(), msg, "Eingabefehler", JOptionPane.ERROR_MESSAGE);			
		}	
	}
	
	private void berechnen() {
		lTest.setText("Berechnen wurde gedrueckt");
	}
	
	private void anzeigen() {
		String farbe = farbauswahlAbleitung.getSelectedItem().toString();
		lTest.setText("Anzeigen wurde gedrueckt. Wunschfarbe: " + farbe);
	}
	
	// Methoden für den MouseListener

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Format für Ausgabe der Intervallgrenzen (double) festelegen
		DecimalFormat df = new DecimalFormat("#.#");
		// runden
		df.setRoundingMode(RoundingMode.CEILING);
		
		// aktuelle Intervallgrenzen abfragen
		double left = g.getBoundaries().left;
		double right = g.getBoundaries().right;
		double top = g.getBoundaries().top;
		double bottom = g.getBoundaries().bottom;
		
		// Intervallgrenzen in die entsprechenden TextFields eintragen
		xAchseVon.setText(String.format("%s", df.format(left)));
		xAchseBis.setText(String.format("%s", df.format(right)));
		yAchseVon.setText(String.format("%s",df.format(bottom)));
		yAchseBis.setText(String.format("%s", df.format(top)));
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
}
