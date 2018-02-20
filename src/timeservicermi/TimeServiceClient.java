package timeservicermi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;

public class TimeServiceClient {

    public static void main(String[] args) {
        try {
            TimeService ts = (TimeService) Naming.lookup("rmi://localhost/MyTimeService");
            System.out.println(ts.getDateAndTime());

            ts.addEventListener(new MyEventListener());

            ts.addEvent(new MyEvent(new Date(new Date().toInstant().plusSeconds(10).toEpochMilli()),
                    "10 Seconds"));
            ts.addEvent(new MyEvent(new Date(new Date().toInstant().plusSeconds(5).toEpochMilli()),
                    "5 Seconds"));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
