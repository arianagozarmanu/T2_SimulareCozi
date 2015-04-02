public class Client {
	private int arrivalTime;
	private int servingTime;
	private String nume;

	public Client(int arrivalTime, int servingTime, String nume) {
		this.arrivalTime = arrivalTime;
		this.servingTime = servingTime;
		this.nume=nume; 
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getServingTime() {
		return servingTime;
	}

	public void setServingTime(int servingTime) {
		if(servingTime >= 0)
			this.servingTime = servingTime;
	}

	public String getNume() {
		return nume;
	}

	//Afisare caracteristici clienti cand sunt serviti la case 
	public String toString() {
		return getNume() + ":" + "A:" + getArrivalTime() + " S:" + getServingTime(); 
	}
}
