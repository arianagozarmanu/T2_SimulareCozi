
public class Main {

	//utilizare metoda MVC
 public static void main(String[] args) {
		// TODO Auto-generated method stub
		View interfata = new View();
		Controller controlThread=new Controller(interfata);
		//incep firul de executie
		controlThread.run();

	}

}
