package timeservicermi;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

public class TimeService implements Remote {

    Date getDateAndTime() {
        return new Date();
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        TimeService ts = new TimeService();
        registry.bind("TimeService", ts);
        System.out.println("RMI started on port 1099");
    }

}
