package timeservicermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface TimeService_Interface extends Remote{

    Date getDateAndTime() throws RemoteException;

}
