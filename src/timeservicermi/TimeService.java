package timeservicermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

public interface TimeService extends Remote {

    Date getDateAndTime() throws RemoteException;

    void addEvent(Event e) throws RemoteException;

    Vector<Event> getAllEvents() throws RemoteException;

    Event getNextEvent() throws RemoteException;

    Vector<Event> getFutureEvents() throws RemoteException;

    void addEventListener(EventListener el) throws RemoteException;

    void removeEventListener(EventListener el) throws RemoteException;
}
