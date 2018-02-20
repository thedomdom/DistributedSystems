package timeservicermi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TimeServiceClient {

    public static void main(String[] args) {
        try {
            TimeService_Interface ts = (TimeService_Interface) Naming.lookup("rmi://localhost/TimeService");
            System.out.println(ts.getDateAndTime());
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
