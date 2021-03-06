package funktionsplot;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;

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
public class PlotGUI extends JFrame implements MouseMotionListener, MouseListener, MouseWheelListener {
	
	private static final long serialVersionUID = 3834256902728876215L;
	// Utilties deklarieren
	// DecimalFormat für Formatieren von Dezimalenzahlen 
	// (für double-Werte gebraucht, weil länderabhängige Verwendung von . oder , als Trennzeichen)
	DecimalFormat df;
	DecimalFormatSymbols dfs;
	// Farben für Design
	Color hintergrund;
	Color schrift;
	Color schriftDunkel;

	// Elemente deklarieren
	private JPanel p;
	private GraphPanel g;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem exit, reset;
	private JLabel lTerm, lFarbeGraph, lIntervall, lXAchseVon, lXAchseBis, lYAchseVon, lYAchseBis, lSchnittpunkte,  lNullstellen, lSchnittY, lAbleitung, lFarbeAbleitung;
	private JTextField term, xAchseVon, xAchseBis, yAchseVon, yAchseBis, nullstellen, schnittY;
	private JButton plot, intervallAnpassen, berechnen, anzeigenAbleitung, zeigeAlle;
	private JComboBox<String> farbauswahlGraph, farbauswahlAbleitung;
	private ActionListener nstListener;
	
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
		// Dezimal-Format konfigurieren
		df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance());
		dfs = df.getDecimalFormatSymbols();

	    dfs.setDecimalSeparator(',');
	    df.setDecimalFormatSymbols(dfs);
	    
	    // Farben definieren
	    hintergrund = new Color(255, 153, 66);
	    schrift = new Color(2, 68, 71);
	    schriftDunkel = new Color(118, 56, 3);
		
		// Frame und Panel konfigurieren
		this.setSize(930, 590);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p=(JPanel) this.getContentPane();
		p.setBackground(hintergrund);
		p.setLayout(null);
		
		// Panel für den Graphen
		g = new GraphPanel();
		g.setBounds(400, 20, 500, 500);	
		// MouseListener um die TextFields für X- und Y-Achse zu aktualisieren
		g.addMouseListener(this);
		g.addMouseMotionListener(this);
		g.addMouseWheelListener(this);
		
		p.add(g);
		
		// Menue konfigurieren
		menubar = new PlotMenubar();
		menu = new PlotMenu("Menue");
		exit = new JMenuItem("Plotter verlassen");
		reset = new JMenuItem("Zuruecksetzen");
				
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
		plot = new RoundedCornerButton("Plot");
		intervallAnpassen = new RoundedCornerButton("Anpassen");
		berechnen = new RoundedCornerButton("Berechnen");
		anzeigenAbleitung = new RoundedCornerButton("Anzeigen");
		zeigeAlle = new RoundedCornerButton("Zeige Alle");
		
		// ComboBoxes
		String[] farben = {"schwarz", "rot", "gruen", "blau", "orange"};
		farbauswahlGraph = new JComboBox<String>(farben);
		farbauswahlAbleitung = new JComboBox<String>(farben);
		
		// Elemente einfaerben
		// Menue
		exit.setForeground(schriftDunkel);
		reset.setForeground(schriftDunkel);

		// Label
		lTerm.setForeground(schrift);
		lFarbeGraph.setForeground(schrift);
		lIntervall.setForeground(schrift);
		lXAchseVon.setForeground(schrift);
		lXAchseBis.setForeground(schrift);
		lYAchseVon.setForeground(schrift);
		lYAchseBis.setForeground(schrift);
		lSchnittpunkte.setForeground(schrift);
		lNullstellen.setForeground(schrift);
		lSchnittY.setForeground(schrift);
		lAbleitung.setForeground(schrift);
		lFarbeAbleitung.setForeground(schrift);
		
		// ComboBoxes
		farbauswahlGraph.setForeground(schrift);
		farbauswahlGraph.setForeground(schrift);

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
		zeigeAlle.setBounds(250, 370, W2, H2);
		
		// ComboBoxes
		farbauswahlGraph.setBounds(X4, 50, W3, H1);
		farbauswahlAbleitung.setBounds(X4, 450, W3, H1);
		
		// ActionListener hinzufuegen
		// Menue
		exit.addActionListener(e -> exit());
		reset.addActionListener(e -> reset());
		
		// Buttons
		plot.addActionListener(e -> plot());
		intervallAnpassen.addActionListener(e -> anpassen());
		berechnen.addActionListener(e -> berechnen());
		anzeigenAbleitung.addActionListener(e -> anzeigen());
		
		
		// Elemente dem Panel hinzufuegen
		// Menue
		menubar.add(menu);
		menu.add(reset);
		menu.add(exit);
		setJMenuBar(menubar);
		
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
		p.add(zeigeAlle);
		
		// ComboBoxes
		p.add(farbauswahlGraph);	
		p.add(farbauswahlAbleitung);
		
		// Sichtbarkeit des Buttons zum Anzeigen aller Nullstellen auf unsichtbar setzen
		zeigeAlle.setVisible(false);
		
		//zeige Boundaries des GraphPanels an
		showBoundaries();
	}
	
	// Methoden für das Event Handling beim Klicken der Menü-Items
	
	private void exit() {
		String msg = "Wollen Sie den Plotter wirklich verlassen?";
		String[] optionen = {"Ja", "Nein", "Abbrechen"}; 
		int n = JOptionPane.showOptionDialog(this.getParent(), msg, "Plotter verlassen?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionen, optionen[0] );
		switch(n) {
		case JOptionPane.YES_OPTION:
			System.exit(0);
			break;
		case JOptionPane.NO_OPTION:
			return;
		case JOptionPane.CANCEL_OPTION:
			return;
			
		}
	}
	
	private void reset() {
		String msg = "Wollen Sie den aktuellen Graphen verwerfen?";
		String[] optionen = {"Ja", "Nein", "Abbrechen"}; 
		int n = JOptionPane.showOptionDialog(this.getParent(), msg, "Graph zuruecksetzen?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionen, optionen[0] );
		
		switch(n) {
		case JOptionPane.YES_OPTION:
			g.reset();
			g.resetBoundaries();
			showBoundaries();
			term.setText(null);
			farbauswahlGraph.setSelectedIndex(0);
			nullstellen.setText(null);
			schnittY.setText(null);
			farbauswahlAbleitung.setSelectedIndex(0);
			zeigeAlle.setVisible(false);
			zeigeAlle.removeActionListener(nstListener);
			break;
		case JOptionPane.NO_OPTION:
			return;
		case JOptionPane.CANCEL_OPTION:
			return;	
		}
	}
	
	// Methoden für das Event Handling beim Klicken der Buttons

	private void plot() {
		// Boundaries zurücksetzen um optimales Einzeichnen zu ermöglichen
		// (durch Verschieben der Boundaries durch Setzen/Zoomen kann es sonst zu suboptimalen Graphen kommen)
		g.resetBoundaries();
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
		
		if(!funcString.isEmpty()) {
			FuncParser.theParser().resetErrcnt();
			g.reset();
			Funktion f = FuncParser.theParser().parse(funcString);
			if (FuncParser.theParser().getErrcnt() == 0) {
				// Elemente mit Informationen über den letzten Graph zurücksetzen
				nullstellen.setText(null);
				zeigeAlle.setVisible(false);
				schnittY.setText(null);
				zeigeAlle.removeActionListener(nstListener);
				farbauswahlAbleitung.setSelectedIndex(0);
				
				showBoundaries();
				f.plotColor = farbauswahl;
				g.addFunction(f);
				showBoundaries();
				return;
			}
			String msg = "Fehler bei der Eingabe!" + FuncParser.theParser().getErrMsg();
			JOptionPane.showMessageDialog(this.getParent(), msg, "Eingabefehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String msg = "Das Eingabefeld ist leer.";
		JOptionPane.showMessageDialog(this.getParent(), msg, "Eingabefehler", JOptionPane.ERROR_MESSAGE);	
	}
	
	private void anpassen() {
		try {
			double xVon = df.parse(xAchseVon.getText()).doubleValue();
			double xBis = df.parse(xAchseBis.getText()).doubleValue();
			double yVon = df.parse(yAchseVon.getText()).doubleValue();
			double yBis = df.parse(yAchseBis.getText()).doubleValue();
			
			g.setBoundaries(xVon, xBis, yBis, yVon);
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			String msg = "Bitte Intervallgrenze im Format #,# angeben";
			JOptionPane.showMessageDialog(this.getParent(), msg, "Eingabefehler", JOptionPane.ERROR_MESSAGE);			
		}
	}

	private void berechnen() {
		// evtl vorher gesetzte ActionListener entfernen um versehentlich mehrfaches Aufrufen von "showNst(nst)" zu verhindern
		zeigeAlle.removeActionListener(nstListener);
		
		// Nullstellen anzeigen
		ArrayList<Double> nst = g.getNst();
		nstListener = e -> showNst(nst);
		if (g.hasNst()) {
			StringBuffer buffer = new StringBuffer();
			if (nst.size() > 1) {
				for (int i = 0; i < nst.size() - 1; i++) {
					buffer.append(Double.toString(nst.get(i)) + ", ");
				}
			}
			// falls es mehr als 4 Nullstellen gibt: Button anzeigen, der beim Klicken ein OptionPanel aufruft, das alle Nullstellen enthält
			if (nst.size() > 4) {
				zeigeAlle.setVisible(true);
				zeigeAlle.addActionListener(nstListener);	
			}
			// letzte Nullstelle aus der ArrayList dem Buffer hinzufügen (so wird bei mehreren Nullstellen kein Komma hinter die letzte Nst gesetzt
			buffer.append(Double.toString(nst.get(nst.size()-1)));
			nullstellen.setText(buffer.toString());
		}
		else {
			nullstellen.setText("keine Nullstellen vorhanden");
		}
		
		// Schnittpunkt mit y-Achse anzeigen
		try {
			schnittY.setText(Double.toString(g.schnittY()));
		} catch (Exception e1) {
			schnittY.setText("Keine Schnittstellen");
		}
		
		g.repaint();
	}
	
	private void anzeigen() {
		String farbe = farbauswahlAbleitung.getSelectedItem().toString();
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
		
		g.zeigeAbleitung(farbauswahl);
	}
	
	// Hilfs-Methoden
	
	// Methode zum Anzeigen der aktuellen Intervallgrenzen in den entsprechenden TextFields
	private void showBoundaries() {
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
	
	// Methode zum Anzeigen vieler Nullstellen in einem JOptionPane
	private void showNst(ArrayList<Double> nst) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < nst.size() - 1; i++) {
			buffer.append(Double.toString(nst.get(i)) + ", ");
			
		}
		buffer.append(Double.toString(nst.get(nst.size()-1)));
		nullstellen.setText(buffer.toString());
		
		String msg = buffer.toString();
		
		// JOptionPane		
		String[] options = {"OK"};
		JOptionPane.showOptionDialog
        				(null, msg, "Alle Nullstellen", 
        				JOptionPane.DEFAULT_OPTION, 
        				JOptionPane.INFORMATION_MESSAGE, 
					    null, options, options[0]);
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
		showBoundaries();
		
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
		showBoundaries();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		showBoundaries();
		
	}

}
