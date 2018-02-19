package timeservice;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeServiceClient {

    public static String fromServer(String address, String type) {
        String date = "";

        try {
            Socket timeServiceSocket = new Socket(address, 75);
            PrintWriter out = new PrintWriter(timeServiceSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(timeServiceSocket.getInputStream()));

            in.readLine();
            out.println(type);
            date = in.readLine();

            out.close();
            in.close();
            timeServiceSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String timeFromServer(String address) {
        return fromServer(address, "time");
    }

    public static String dateFromServer(String address) {
        return fromServer(address, "date");
    }

    public static void main(String[] args) {
        System.out.println(TimeServiceClient.timeFromServer("localhost"));
        System.out.println(TimeServiceClient.dateFromServer("localhost"));
    }
}
