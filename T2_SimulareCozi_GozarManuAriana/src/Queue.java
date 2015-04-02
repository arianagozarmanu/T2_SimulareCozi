import java.util.Vector;


public class Queue{
	//Vector e sincronizat si este util cand folosim threaduri, pentru a nu actiona
	//mai multe threaduri asupra unui element
	private Vector<Client> queue = new Vector<Client>();

	//adauga client la coada
	public void addQueue(Client client) {
		queue.addElement(client);
	}

	//decrementeaza coada
	public Client decQueue() {
		Client aux = queue.firstElement();
		queue.removeElement(aux);
		return aux;
	}

	public Client getClientForSale()  {
		return queue.firstElement();
	}

	//returnare coada
	public Vector<Client> getQueueClient() {
		return this.queue;
	}

	//actualizare coada
	public void setQueueClient(Vector<Client> queueClient) {
		this.queue = queueClient;
	}

}
