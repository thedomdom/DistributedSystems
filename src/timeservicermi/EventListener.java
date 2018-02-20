package timeservicermi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EventListener extends Remote{

    void handleEvent(Event e) throws RemoteException;

}
