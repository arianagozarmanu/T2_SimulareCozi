import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;
import javax.swing.JOptionPane;

public class Controller implements Runnable {
	private View view;
	//array de cozi (~case)
	private Queue cozi[];
	private int arrivalTimeMax;
	//pentru a copia cozi (utilizat in situatiile in care adaug sau inchid case)
	private Queue aux[];
	private boolean stop=true;
	private boolean start = false;
	//Vector de clienti care sunt in intreprindere (partea stanga a reprezentarii dinamice)
	private Vector<Client> waitingClients = new Vector<Client>();
	//utilizat pentru a copia elementele din cozi pentru a le sterge
	private Vector<Client> clientAux=new Vector<Client>();
	//dupa ce sunt raspanditi la case, clientii sunt introdusi intr-un vector pentru a fi stersi din intreprindere (Vectorul waitingClients)
	private Vector<Client> deleteList = new Vector<Client>();
	public Controller(View interfata) {
		view = interfata;
		//apelez metoda prin care activez butoanele cu ajutorul clasei ActionListener
		view.ButtonsListener(new SimulareController(), new SimulareController2(),new SimulareController3());	
	}

	//metoda suprascrisa din Runnable
	public void run() {
	//se trec clientii din intreprindere la cozi cand timpul de simulare ajunge la timpul lor de mers la coada
	//sterge fiecare client care a mers la casa din lista de clienti din intreprindere
	//elimin toate elementele din Vector-ul cu clienti care trebuie eliminati din intreprindere dupa ce s-au asezat la cozi
	//tratez cazul cand timpul de servire e 0
		int counter = 0; 
		while (stop) {
			try {
				//start se activeaza la apasarea butonului SIMULARE
				if (start == false) {
					Thread.sleep(1000);
				} 
				else {
					//Reprezinta durata simularii in secunde
					view.getTimeLabel().setText("Timp: " + counter);
					for (int i = 0; i < waitingClients.size(); i++) {
						//daca clientul in asteptare are timpul de sosire egal cu secundele trecute de la inceputul simularii
						int arr=waitingClients.get(i).getArrivalTime();
						if (arr == counter) {
							//se obtine indexul unei case optime pentru el, prin adunarea timpului de servire de la fiecare coada
							//cozi e un array de Vectori ce reprezinta cozile de la case
							int nrCasa = getOptimalPlace(cozi);
							//se pune in coada de asteptare clientul 
							cozi[nrCasa].addQueue(waitingClients.get(i));
							System.out.println("S-a adaugat clientul "+ waitingClients.get(i).getNume() + " cu timpul de sosire "+ waitingClients.get(i).getArrivalTime()+ " si cu timpul de servire "+ waitingClients.get(i).getServingTime());
							//deseneaza un nou client la coada
							view.drawClients(waitingClients.get(i).getNume(), waitingClients.get(i).getArrivalTime(), waitingClients.get(i).getServingTime(), nrCasa, cozi[nrCasa].getQueueClient().size() - 1);
							//adauga clientul care a mers la casa la lista de sters clienti din intreprindere
							deleteList.add(waitingClients.get(i));
						}
					}
					
					//sterg fiecare client care a mers la casa din lista de clienti din intreprindere
					for (int x = 0; x < deleteList.size(); x++) {
						waitingClients.removeElement(deleteList.get(x));
						//fac invizibili toti clientii din market
						view.clearclientsInMarket();
						//si ii redesenez 
						for (int k = 0; k < waitingClients.size(); k++) {
							view.drawClientsInMarket(waitingClients.get(k).getNume(), waitingClients.get(k).getArrivalTime(), waitingClients.get(k).getServingTime());
						}
					}
					//elimin toate elementele din Vector-ul cu clienti care trebuie eliminati din intreprindere dupa ce s-au asezat la cozi
					deleteList.clear();
					for (int i = 0; i < cozi.length; i++) {
						//daca la casa exista vreun client
						if (cozi[i].getQueueClient().size() != 0) {
							//clientForSale ia valoarea primului element din Vector (cel ce e servit la casa)
							Client clientForSale = cozi[i].getClientForSale();
							//scade timpul de servire
							clientForSale.setServingTime(clientForSale.getServingTime() - 1);
							//ia Labelul primului client si il actualizeaza cu cel nou in care s-a scazut timpul de servire
							view.getFirstClient(clientForSale.getNume()).setText(clientForSale.toString());

							//daca s-a ajuns la 0 cu timpul de servire
							if (clientForSale.getServingTime() == 0) {
								//actualizeaza Label-ul
								view.getFirstClient(clientForSale.getNume()).setText(clientForSale.toString());
								//decrementeaza coada de care apartine, adica il elimina
								Client clientElliminat = cozi[i].decQueue();
								System.out.println("Clientul " + clientElliminat.getNume() + " a fost servit");
								//sterge si Label-ul din reprezentarea grafica apartinator clientului servit
								view.removeLabelClients(cozi);
							}
						}
					}
					Thread.sleep(1000);
					//trece la secunda urmatoare
					counter++;
					//daca s-a servit si ultimul client venit se incheie simularea
					int check=counter-1;
					if (check >= arrivalTimeMax && queuesAreEmpty(cozi)) {
						int aux=counter;
						aux--;
						JOptionPane.showMessageDialog(null,"Simulare s-a incheiat!\n" + "Durata: " + aux + " s.","", 1);
						stop=false;
					}
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	// Returneaza indicelei casei in care este cel mai optim de adaugat clienti
	public int getOptimalPlace(Queue cozi[]) {
		int index = 0;
		int minim = Integer.MAX_VALUE;

		//cauta in care coada timpul de servire e cel mai mic
		for (int i = 0; i < cozi.length; i++) {
			int counter = 0;
			int size=cozi[i].getQueueClient().size();
	
			for (int j = 0; j < size ; j++) {
				Client c=cozi[i].getQueueClient().get(j);
				counter = counter + c.getServingTime();
			}
			if (counter < minim) {
				minim = counter;
				index = i;
			}
		}
		return index;
	}

	// Metoda care verifica daca cozile sunt goale
	public boolean queuesAreEmpty(Queue queue[]) {
		boolean ok = true;
		for (int i = 0; i < queue.length; i++) {
			if (!queue[i].getQueueClient().isEmpty()) {
				ok = false;
			}
		}
		return ok;
	}

	// Metoda care creeaza clienti cu timp de sosire la case si de servire random
	public void createClients(int numarMaximClienti, int arrivalTimeMin,int arrivalTimeMax, int serviceTimeMin, int serviceTimeMax) {
		Random r = new Random();
		for (int i = 0; i < numarMaximClienti; i++) {
			int arrivalTime = r.nextInt(arrivalTimeMax - arrivalTimeMin) + arrivalTimeMin;
			int serviceTime = r.nextInt(serviceTimeMax - serviceTimeMin) + serviceTimeMin;
			//numele clientilor va fi indicele lor
			Client c = new Client(arrivalTime, serviceTime,Integer.toString(i));
			waitingClients.addElement(c);
		}
	}

	//ActionListener pentru butonul SIMULARE
	private class SimulareController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				//iau datele introduse de utilizator
				int queueNr;
				int clientNr;
				int servingTimeMin;
				int servingTimeMax;
				int arrivalTimeMin;
				String auxString=view.getnrClientField().getText();
				clientNr = Integer.parseInt(auxString);
				auxString=view.getmaxSimulationTimeField().getText();
				//avem nevoie de arrivalTimeMax si la metoda run()
				arrivalTimeMax = Integer.parseInt(auxString);
				auxString=view.getminSimulationTimeField().getText();
				arrivalTimeMin=Integer.parseInt(auxString);
				auxString=view.getnrQueueField().getText();
				queueNr = Integer.parseInt(auxString);
				auxString=view.getServingTimeMin().getText();
				servingTimeMin=Integer.parseInt(auxString);
				auxString=view.getServingTimeMax().getText();
				servingTimeMax=Integer.parseInt(auxString);
				if (queueNr > 8) {
					JOptionPane.showMessageDialog(null,"Se pot reprezenta maxim 8 cozi! ","", 0);
				} 
				else if(clientNr > 22){
					JOptionPane.showMessageDialog(null,"Se pot reprezenta maxim 22 clienti! ","", 0);
				}
				else
				{
					cozi = new Queue[queueNr];
					//metoda e deasupra : createClients(nr clienti, timp min de sosire la case, nr maxim de sosire la case, timp de servire minim, timp de servire maxim)
					createClients(clientNr,arrivalTimeMin , arrivalTimeMax, servingTimeMin, servingTimeMax);
					for (int i = 0; i < waitingClients.size(); i++) {
						//gettere din clasa Client
						String name=waitingClients.get(i).getNume();
						int arrTime=waitingClients.get(i).getArrivalTime();
						int servTime=waitingClients.get(i).getServingTime();
						view.drawClientsInMarket(name, arrTime , servTime);
					}
					for (int i = 0; i < queueNr; i++) {
						cozi[i] = new Queue();
					}
					start = true;
					view.drawQueue(queueNr);
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"Ati introdus gresit datele de intrare! Introduceti numere intregi!","", 2);
			}

		}
	}
	
	private class SimulareController2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(cozi.length<8){
					aux=new Queue[cozi.length];
					for(int i=0;i<cozi.length;i++)
						aux[i]=cozi[i];
					cozi=new Queue[aux.length+1];
					for(int i=0;i<aux.length;i++)
						cozi[i]=aux[i];
					cozi[cozi.length-1]=new Queue();
					view.drawCasa(cozi.length-1);
				}
				else{
					JOptionPane.showMessageDialog(null,"Nu se pot reprezenta mai multe Case!","", 0);
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,ex,"", 2);
			}

		}
	}
	
	private class SimulareController3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(cozi.length>2){
					view.undrawCasa(cozi.length-1);
					clientAux=cozi[cozi.length-1].getQueueClient();
					for(int i=0;i<clientAux.size();i++){
						String s=clientAux.get(i).getNume();
						int arr=clientAux.get(i).getArrivalTime();
						int serv=clientAux.get(i).getServingTime();
						view.undrawClients(s, arr, serv);
					}
					
					aux=new Queue[cozi.length];
					for(int i=0;i<cozi.length;i++)
						aux[i]=cozi[i];
					cozi=new Queue[aux.length-1];
					for(int i=0;i<cozi.length;i++)
						cozi[i]=aux[i];
					
					for(int i=0;i<clientAux.size();i++){
						int nrCasa = getOptimalPlace(cozi);
						//se pune in coada de asteptare clientul 
						cozi[nrCasa].addQueue(clientAux.get(i));
						String s=clientAux.get(i).getNume();
						int arr=clientAux.get(i).getArrivalTime();
						int serv=clientAux.get(i).getServingTime();
						int size=cozi[nrCasa].getQueueClient().size() - 1;
						System.out.println("S-a adaugat clientul "+ s + " cu timpul de sosire "+ arr + " si cu timpul de servire "+ serv);
						//deseneaza un nou client la coada
						view.drawClients(s, arr, serv, nrCasa, size);
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"E nevoie de cel putin 2 case pentru 22 de persoane!","", 0);
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,ex,"", 2);
			}

		}
	}


	
}
