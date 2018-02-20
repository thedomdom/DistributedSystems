package timeservicermi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class MyEvent extends UnicastRemoteObject implements Event{

    private Date date;
    private String description;

    MyEvent(Date date, String description) throws RemoteException {
        this.date = date;
        this.description = description;
    }

    public Date getDate() throws RemoteException {
        return date;
    }

    @Override
    public void print() throws RemoteException {
        System.out.println("MyEvent{" +
                "date=" + date +
                ", description='" + description + '\'' +
                '}');
    }
}
