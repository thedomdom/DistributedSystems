package timeservice;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TimeServiceMultithreaded {

    private final Logger logger = Logger.getLogger(TimeService.class.getName());

    public static void main(String[] args) {
        new TimeServiceMultithreaded().startServer();
    }

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(75);
                    System.out.println("Waiting for clients to connect...");
                    while (!serverSocket.isClosed()) {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(clientSocket));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            logger.info("Got a client !");

            try {
                TimeService.doTheService(this.clientSocket, logger);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

