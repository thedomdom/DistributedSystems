package timeservicermi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Collectors;

public class MyTimeService extends UnicastRemoteObject implements TimeService {

    private Vector<Event> events;
    private Vector<EventListener> eventListeners;
    private Thread eventHandlerThread;

    private MyTimeService() throws RemoteException {
        events = new Vector<>();
        eventListeners = new Vector<>();

        Runnable eventHandler = () -> {
            while (true) {
                try {
                    Event nextEvent = this.getNextEvent();
                    if (nextEvent != null) {
                        Thread.sleep(nextEvent.getDate().getTime() - new Date().getTime());
                        for (int i = 0; i < eventListeners.size(); i++) {
                            eventListeners.elementAt(i).handleEvent(nextEvent);
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Reloading EventHandler");
                } catch (RemoteException e) {
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
    public synchronized void addEvent(Event e) throws RemoteException {
        eventHandlerThread.interrupt();
        events.add(e);
    }

    @Override
    public synchronized Vector<Event> getAllEvents() throws RemoteException {
        return events;
    }

    @Override
    public synchronized Event getNextEvent() throws RemoteException {
        Vector<Event> futureEvents = getFutureEvents();
        if (futureEvents.size() > 0) return getFutureEvents().get(0);
        else return null;
    }

    @Override
    public synchronized Vector<Event> getFutureEvents() throws RemoteException {
        return this.events
                .stream()
                .filter(event -> {
                    try {
                        return event.getDate().after(new Date());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .sorted((e1, e2) -> {
                    try {
                        return (e1.getDate().compareTo(e2.getDate()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return 0;
                })
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public synchronized void addEventListener(EventListener el) throws RemoteException {
        eventListeners.add(el);
    }

    @Override
    public synchronized void removeEventListener(EventListener el) throws RemoteException {
        eventListeners.remove(el);
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        MyTimeService ts = new MyTimeService();
        registry.bind("MyTimeService", ts);
        System.out.println("RMI started on port 1099");
    }

}
