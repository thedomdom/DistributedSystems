package timeservicermi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Collectors;

public class TimeService extends UnicastRemoteObject implements TimeService_Interface {

    private Vector<Event> events;
    private Vector<EventListener> eventListeners;
    private Thread eventHandlerThread;

    private TimeService() throws RemoteException {
        Runnable eventHandler = () -> {
            while (true) {
                try {
                    Event nextEvent = this.getNextEvent();
                    Thread.sleep(nextEvent.getDate().getTime() - new Date().getTime());
                    for (int i = 0; i < eventListeners.size(); i++) {
                        eventListeners.elementAt(i).handleEvent(nextEvent);
                    }
                } catch (InterruptedException | RemoteException e) {
                    e.printStackTrace();
                }

            }
        };
        eventHandlerThread = new Thread(eventHandler);
        eventHandlerThread.start();
    }

    @Override
    public Date getDateAndTime() {
        return new Date();
    }

    @Override
    public void addEvent(Event e) throws RemoteException {
        eventHandlerThread.interrupt();
        events.add(e);
    }

    @Override
    public Vector<Event> getAllEvents() throws RemoteException {
        return events;
    }

    @Override
    public Event getNextEvent() throws RemoteException {
        return getFutureEvents().get(0);
    }

    @Override
    public Vector<Event> getFutureEvents() throws RemoteException {
        return this.events
                .stream()
                .filter(event -> event.getDate().after(new Date()))
                .sorted((e1,e2) -> (e1.getDate().compareTo(e2.getDate())))
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public void addEventListener(EventListener el) throws RemoteException {
        eventListeners.add(el);
    }

    @Override
    public void removeEventListener(EventListener el) throws RemoteException {
        eventListeners.remove(el);
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        TimeService ts = new TimeService();
        registry.bind("TimeService", ts);
        System.out.println("RMI started on port 1099");
    }

}
