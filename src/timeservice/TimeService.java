package timeservice;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class TimeService {

    private final Logger logger = Logger.getLogger(TimeService.class.getName());

    public static void main(String[] args) {
        new TimeService().run();
    }

    private void run() {

        try {
            // establish a connection
            int GIVEN_PORT = 75;
            ServerSocket server = new ServerSocket(GIVEN_PORT);
            logger.info("Server running and listening on port : " + GIVEN_PORT);

            Socket socket = server.accept();
            doTheService(socket, logger);


        } catch (IOException e) {
            logger.warning("Shutting down the server..");
        }

    }

    public static void doTheService(Socket socket, Logger logger) throws IOException {


        OutputStream output = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        logger.info("New Connection");
        writer.write("time service\n");
        writer.flush();

        String userInput;
        while ((userInput = reader.readLine()) != null) {
            logger.info("handling command: " + userInput);
            if (userInput.equals("date")) {
                writer.write(Clock.date() + "\n");
            }
            if (userInput.equals("time")) {
                writer.write(Clock.time() + "\n");
            }
            writer.flush();
        }

        socket.close();
    }

}
