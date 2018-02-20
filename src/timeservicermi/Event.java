package timeservicermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

interface Event extends Remote {

    Date getDate() throws RemoteException;

    void print() throws RemoteException;

}
