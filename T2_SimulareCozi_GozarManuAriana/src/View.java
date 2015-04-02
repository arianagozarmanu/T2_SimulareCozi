import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends JFrame {


	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel dataLabel;
	private JPanel simulationPanel;
	private JLabel setDataLabel;
	private JLabel queueNrLabel;
	private JLabel simulationTimeLabel;
	private JLabel minSimulationTimeLabel;
	private JLabel servingTimeMinLabel;
	private JLabel servingTimeMaxLabel;
	private JLabel clientLabel;
	private JLabel representation;
	private JLabel timeLabel;
	private JLabel simulationSecLabel;
	private JLabel simulationSecLabel2;
	private JLabel nrClientLabel;
	private JTextField nrQueueField;
	private JTextField maxSimulationTimeField;
	private JTextField minSimulationTimeField;
	private JTextField nrClientField;
	private JTextField servingTimeMin;
	private JTextField servingTimeMax;
	private JButton startSimulationButton;
	private JButton adaugCasa,stergeCasa;
	//array cu casele
	private ArrayList<JLabel> cozi = new ArrayList<JLabel>();
	//array cu clientii de la coada
	private ArrayList<JButton> clienti = new ArrayList<JButton>();
	//array cu clientii din stanga reprezentarii
	private ArrayList<JButton> clientsInMarket = new ArrayList<JButton>();

	public View() {

		//Fereastra principala
		frame = new JFrame("");
		frame.setSize(1350, 700);
		frame.setVisible(true);
		frame.setLayout(null); //Pentru a nu se suprapune Panel-urile
		
		//Panel pentru introducere a datelor
		dataLabel = new JPanel();
		dataLabel.setBounds(0, 0, 1350, 110);
		frame.add(dataLabel);
		Color colour = new Color(178,255,102);
		dataLabel.setBackground(colour);
		dataLabel.setLayout(null); 
		
		//Panel pentru simulare interactiva
		simulationPanel = new JPanel();
		simulationPanel.setBounds(0, 120, 1350, 580);
		frame.add(simulationPanel);
		simulationPanel.setLayout(null);
		
		//Introducere date
		setDataLabel = new JLabel("INTRODUCETI DATELE NECESARE:");
		//setBounds(distanta_din_stanga, distanta_de_sus, lungimea_pe_caractere,
		setDataLabel.setBounds(10, 5, 200, 20);
		dataLabel.add(setDataLabel);
		
		//Numar cozi 
		queueNrLabel = new JLabel("Numar maxim cozi:");
		queueNrLabel.setBounds(10, 45, 120, 20);
		dataLabel.add(queueNrLabel);
		
		//Timp minim servire
		servingTimeMinLabel= new JLabel("Timp minim de servire:");
		servingTimeMinLabel.setBounds(10,70,220,20);
		dataLabel.add(servingTimeMinLabel);
		
		//Timp maxim servire
		servingTimeMaxLabel= new JLabel("Timp maxim de servire:");
		servingTimeMaxLabel.setBounds(300,70,220,20);
		dataLabel.add(servingTimeMaxLabel);
		
		//Numar clienti
		nrClientLabel = new JLabel("Numar clienti:");
		nrClientLabel.setBounds(300, 45, 120, 20);
		dataLabel.add(nrClientLabel);
				
		//Timpul maxim de sosire a clientilor in unitate
		simulationTimeLabel = new JLabel("Timp maxim sosire client:");
		simulationTimeLabel.setBounds(580, 45, 240, 20);
		dataLabel.add(simulationTimeLabel);
		
		//Timp minim sosire clienti
		minSimulationTimeLabel=new JLabel("Timp minim sosire client:");
		minSimulationTimeLabel.setBounds(580,70,220,20);
		dataLabel.add(minSimulationTimeLabel);
		
		//reprezentare "sec"
		simulationSecLabel = new JLabel("sec.");
		simulationSecLabel.setBounds(805, 45, 50, 20);
		dataLabel.add(simulationSecLabel);

		simulationSecLabel2 = new JLabel("sec.");
		simulationSecLabel2.setBounds(805, 70, 50, 20);
		dataLabel.add(simulationSecLabel2);
		
		//TextField pentru introducerea numarului de cozi
		nrQueueField = new JTextField();
		nrQueueField.setBounds(125, 45, 80, 20);
		dataLabel.add(nrQueueField); 
		
		servingTimeMin=new JTextField();
		servingTimeMin.setBounds(144, 70, 60, 20);
		dataLabel.add(servingTimeMin); 
		
		
		//TextField pentru introducerea timpului maxim de sosire a clientilor la cozi
		maxSimulationTimeField = new JTextField();
		maxSimulationTimeField.setBounds(730, 45, 70, 20);
		dataLabel.add(maxSimulationTimeField);

		minSimulationTimeField = new JTextField();
		minSimulationTimeField.setBounds(730, 70, 70, 20);
		dataLabel.add(minSimulationTimeField);
		
		//TextField pentru introducerea numarului de clienti
		nrClientField = new JTextField();
		nrClientField.setBounds(385, 45, 100, 20);
		dataLabel.add(nrClientField);
		
		servingTimeMax=new JTextField();
		servingTimeMax.setBounds(440, 70, 46, 20);
		dataLabel.add(servingTimeMax); 
		
		//Buton Simulare
		startSimulationButton = new JButton("SIMULARE");
		startSimulationButton.setBounds(1000, 35, 300, 40);
		dataLabel.add(startSimulationButton);
		
		//Buton adaug casa
		adaugCasa = new JButton("Deschide Casa!");
		adaugCasa.setBounds(10,500,150,30);
		simulationPanel.add(adaugCasa);
		
		//Buton sterge casa
		stergeCasa = new JButton("Sterge Casa!");
		stergeCasa.setBounds(170, 500 , 150, 30);
		simulationPanel.add(stergeCasa);

		//Clienti - reprezentare dinamica
		clientLabel = new JLabel("CLIENTII");
		clientLabel.setBounds(100, 2, 100, 50);
		simulationPanel.add(clientLabel);

		//Timp
		timeLabel = new JLabel("Timp:");
		timeLabel.setBounds(600, 2, 100, 20);
		simulationPanel.add(timeLabel);

		representation = new JLabel("REPREZENTARE");
		representation.setBounds(1000, 2, 100, 50);
		simulationPanel.add(representation);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	//fac invizibili clientii din intreprindere care sunt la coada la case
	public void clearclientsInMarket() {
		for (int i = 0; i < clientsInMarket.size(); i++) {
			clientsInMarket.get(i).setVisible(false);
		}
		for (int i = 0; i < clientsInMarket.size(); i++) {
			clientsInMarket.remove(i);;
		}
	}
	
	//sterg o casa
	public void undrawCasa(int poz){
		cozi.get(poz).setVisible(false);
		cozi.remove(poz);
		simulationPanel.repaint();
	}
	
	//sterg o coada de clienti
	public void undrawClients(String nume, int arrivalTime, int servingTime){
		String numeB=nume + ":A:" + Integer.toString(arrivalTime) + " S:" + Integer.toString(servingTime);
		for(int i=0;i<clienti.size();i++){
			JButton button=clienti.get(i);
			String aux=button.getText();
			if(numeB.equals(aux)){
				clienti.get(i).setVisible(false);
				clienti.remove(i);
			}
		}
		
	}

	//desenez clientii din stanga reprezentarii (~cei ce sunt in incinta magazinului)
	public void drawClientsInMarket(String nume, int arrivalTime,int servingTime) {
		String numeLabel = nume + ":A:" + Integer.toString(arrivalTime) + " S:" + Integer.toString(servingTime);
		Color col = new Color(204,153,255);
		clientsInMarket.add(new JButton(numeLabel));
		//il tot ia pe ultimul adaugat si il deseneaza
		clientsInMarket.get(clientsInMarket.size() - 1).setBackground(col);
		clientsInMarket.get(clientsInMarket.size() - 1).setBounds(100 * (clientsInMarket.size() % 2 + 1),clientsInMarket.size() % 2 * 20 + clientsInMarket.size() * 20,90,30);
		simulationPanel.add(clientsInMarket.get(clientsInMarket.size() - 1));
		simulationPanel.repaint();
	}

	// metoda de desenare a caselor
	public void drawQueue(int numberQueue) {
		for (int i = 0; i < numberQueue; i++) {
			JLabel casa = new JLabel("  Casa " + i);
			casa.setBounds(1200, 50 + i * 60, 70, 30);
			casa.setBackground(Color.ORANGE);
			casa.setOpaque(true);
			Font newLabelFont = new Font("Lucida", Font.PLAIN, 16);
			casa.setFont(newLabelFont);
			simulationPanel.add(casa);
			cozi.add(casa);
			simulationPanel.repaint();
		}

	}
	
	//desenez o casa pentru adaugare casa
	public void drawCasa(int last){
		JLabel casa = new JLabel("  Casa " + last);
		casa.setBounds(1200, 50 + last * 60, 70, 30);
		casa.setBackground(Color.ORANGE);
		casa.setOpaque(true);
		Font newLabelFont = new Font("Lucida", Font.PLAIN, 16);
		casa.setFont(newLabelFont);
		simulationPanel.add(casa);
		cozi.add(casa);
		simulationPanel.repaint();
	}
	

	// metoda de desenare a clientilor la cozi
	public void drawClients(String nume, int arrivalTime, int servingTime,int numarCoada, int locationCoada) {
		String numeB = nume + ":A:" + Integer.toString(arrivalTime) + " S:" + Integer.toString(servingTime);
		Color col = new Color(204,153,255);
		clienti.add(new JButton(numeB));
		clienti.get(clienti.size() - 1).setBackground(col);
		clienti.get(clienti.size() - 1).setBounds(1100 - 100 * locationCoada, 50 + numarCoada * 60, 90, 30);
		simulationPanel.add(clienti.get(clienti.size() - 1));
		simulationPanel.repaint();
		
	}


	//sterge Label-ul clientului care nu exista (a fost servit) in coada cu clienti de la case
	public void removeLabelClients(Queue q[]) {
		for (int i = 0; i < clienti.size(); i++) {
			Boolean existInQueue = false;
			//extrage numele clientului, identificand indexul pana unde e reprezentat si aplicand metoda substring
			JButton b=clienti.get(i);
			String s=b.getText();
			int index = s.indexOf(":");
			String name = s.substring(0, index);
			for (int j = 0; j < q.length; j++) {
				//parcurge fiecare element din cozi sa vada daca numele fiecarui client din lista clienti cu Label-uri exista in Vectorul cu clienti
				int size=q[j].getQueueClient().size();
				for (int k = 0; k < size; k++) {
					Client c=q[j].getQueueClient().get(k);
					if (name.equals(c.getNume())) {
						existInQueue = true;
					}
				}
			}
			//daca nu a fost gasit, stergem Label-ul din lista si-i deplasam pe ceilalti
			if (existInQueue == false) {
				clienti.get(i).setVisible(false);
				JButton button = clienti.get(i);
				//ia pozitia in care se afla Label-ul pentru a putea deplasa spre dreapta toate Label-urile ce reprezinta coada 
				//aflata la distanta y fata de partea superioara a frame-ului
				int coadaLoc = button.getLocation().y;
				clienti.remove(i);
				deplasareClientiLaCoada(coadaLoc);
			}
		}

	}

	// Metoda de deplasare a etichetelor clientilor care stau la coada
	public void deplasareClientiLaCoada(int position) {
		for (int i = 0; i < clienti.size(); i++) {
			int x=clienti.get(i).getLocation().x;
			int y=clienti.get(i).getLocation().y;
			//tuturor Label-urilor aflate la distanta y, fata de partea superioara, li se schimba pozitia spre dreapta cu 100
			if ( y == position) {
				clienti.get(i).setLocation( x + 100, y);
			}
		}
	}

	// Metoda folosita pentru adaugarea ActionListener
	// primeste un obiect de clasa SimulareController ce mosteneste ActionListener 
	public void ButtonsListener(ActionListener addListener, ActionListener addListener2,ActionListener addListener3) {
		startSimulationButton.addActionListener(addListener);
		adaugCasa.addActionListener(addListener2);
		stergeCasa.addActionListener(addListener3);
		//in Clasa imbricata SimulareController e descrisa actiunea dupa apasarea butonului SIMULARE
	}

	//clienti e lista de JLabel-uri cu numele fiecarui client, timpul de sosire la coada si cel de servire
	//metoda compara numele trimis ca parametru cu numele din lista cu clienti pentru a putea returna caracteristicile clientului cu acel nume
	public JButton getFirstClient(String nume) {
		JButton result = new JButton();
		for (int i = 0; i < clienti.size(); i++) {
			String aux=clienti.get(i).getText();
			//extrag numele fiecarui client
			int index = aux.indexOf(":");
			String name = aux.substring(0, index);
			//System.out.println("Numele clientului : " + name);
			//daca numele este egal cu numele dat, returnez toate caracteristicile clientului
			if (nume.equals(name)) {
				result = clienti.get(i);
			}
		}
		return result;
	}

	public JTextField getnrClientField() {
		return nrClientField;
	}

	public JTextField getnrQueueField() {
		return nrQueueField;
	}
	
	public JTextField getServingTimeMin() {
		return servingTimeMin;
	}
	
	public JTextField getServingTimeMax() {
		return servingTimeMax;
	}
	
	public JTextField getmaxSimulationTimeField() {
		return maxSimulationTimeField;
	}
	
	public JTextField getminSimulationTimeField() {
		return minSimulationTimeField;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

}
