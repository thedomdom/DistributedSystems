package timeservicermi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;

public class TimeService extends UnicastRemoteObject implements TimeService_Interface {

    private Vector<Event> events;

    private TimeService() throws RemoteException {
    }

    public Date getDateAndTime() {
        return new Date();
    }

    @Override
    public void addEvent(Event e) throws RemoteException {
        events.add(e);
    }

    @Override
    public Vector<Event> getAllEvents() throws RemoteException {
        return events;
    }

    @Override
    public Event getNextEvent() throws RemoteException {
        Event next = null;
        for (int i = 0; i < events.size(); i++) {
            Event event = events.elementAt(i);
            if (event.getDate().after(new Date())) {
                if (next == null) {
                    next = event;
                }
                if (event.getDate().before(next.getDate())) {
                    next = event;
                }
            }
        }
        return next;
    }

    @Override
    public Vector<Event> getFutureEvents() throws RemoteException {
        Vector<Event> futureEvents = new Vector<>();
        for (int i = 0; i < events.size(); i++) {
            Event event = events.elementAt(i);
            if (event.getDate().after(new Date())) {
                futureEvents.add(event);
            }
        }
        return futureEvents;
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        TimeService ts = new TimeService();
        registry.bind("TimeService", ts);
        System.out.println("RMI started on port 1099");
    }

}
