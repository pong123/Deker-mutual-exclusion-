import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Process {
	
	/* 
	 * najpierw nalezy uruchpmic w jendym okienku rmiregistry
	 * potem w drugim okienku nalezy uruchomic java SharedMemoryServer
	 * potem obydwa procesy w osobnych okienkach linii komend:
	 * java Process 0
	 * java Process 1
	 */

	public static void main(String[] args) {
		try {
			SharedMemory serwer = (SharedMemory) java.rmi.Naming.lookup("rmi://localhost/SERVER");
			int res = serwer.getValue(0);
			System.out.println("Test serwera : " + res);
			
			int p = Integer.parseInt(args[0]);
			int q = (p == 0) ? 1 : 0;
			System.out.println("Process : " + p);
			
			/* ALGORYTM DEKKERA */
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Process " + p + " rozpoczyna algorytm.");
				serwer.setWantsToEnter(p, true);
				while( serwer.getWantsTeEnter(q)) {
					if( serwer.getFavouredProcess() == q ) {
						serwer.setWantsToEnter(p, false);
						while( serwer.getFavouredProcess() == q );
						serwer.setWantsToEnter(p, true);
					}
				}
				System.out.println("Process " + p + " wchodzi do sekcji krytycznej.");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Process " + p + " wychodzi z sekcji krytycznej.");
				serwer.setFavouredProcess(q);
				serwer.setWantsToEnter(p, false);
				System.out.println("Process " + p + " zakonczyl algorytm.");
			}

			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
