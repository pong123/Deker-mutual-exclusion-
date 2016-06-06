import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SharedMemoryServer extends UnicastRemoteObject implements SharedMemory {

	protected SharedMemoryServer() throws RemoteException {
		super();
		System.out.println("Konstruktor serwera pamieci wspoldzielonej.");
		this.p[0] = false;
		this.p[1] = false;
		this.favoured = 0;
	}

	private static final long serialVersionUID = 8252127657559213455L;

	@Override
	public int getValue(int p)  throws RemoteException{
		return 11;
	}
	
	public static void main(String[] args) {
		try {
			SharedMemoryServer tre = new SharedMemoryServer();
			java.rmi.Naming.rebind("SERVER", tre);
			System.out.println("Server started.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private boolean p[] = new boolean[2];
	private int favoured;
	
	@Override
	public synchronized void setWantsToEnter(int p, boolean value) throws RemoteException {
		this.p[p] = value;
	}

	@Override
	public synchronized boolean getWantsTeEnter(int p) throws RemoteException {
		return this.p[p];
	}

	@Override
	public synchronized void setFavouredProcess(int p) throws RemoteException {
		this.favoured = p;
	}

	@Override
	public synchronized int getFavouredProcess() throws RemoteException {
		return this.favoured;
	}

}
