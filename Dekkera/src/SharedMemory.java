import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedMemory extends Remote {

	/*
	 * do testowania serwera
	 */
	public int getValue(int p) throws RemoteException;
	
	/*
	 * do obslugi pamieci wspoldzielonej w algorytmie Dekkera
	 */
	public void setWantsToEnter(int p, boolean value) throws RemoteException;
	public boolean getWantsTeEnter(int p) throws RemoteException;
	public void setFavouredProcess(int p) throws RemoteException;
	public int getFavouredProcess() throws RemoteException;
}
