package timeservicermi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyEventListener extends UnicastRemoteObject implements EventListener {
    MyEventListener() throws RemoteException {
    }

    @Override
    public void handleEvent(Event e) throws RemoteException {
        e.print();
    }
}
