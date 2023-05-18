
import java.util.Scanner;
import Project_files.ReadCSV;
import Project_files.Client;
import Project_files.ServerHost;

public class Main {
    public static void main(String[] args) {
        final String host = "localhost";
        final int port = 666;
        final ServerHost server = new ServerHost(host, port);

        // Start the server in a separate thread
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.startServer();
            }
        });
        serverThread.start();

        // Wait for the server to start before requesting a joke
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Create multiple client instances and request jokes
        int numClients = 2; // Number of client instances to create
        for (int i = 0; i < numClients; i++) {
            final int clientIndex = i;
            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Client client = new Client(host, port);
                    client.requestJoke();
                }
            });
            clientThread.start();
        }

        // Wait for user input to interact with the server
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Enter a command (JOKE or END):");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("JOKE")) {
                Thread clientThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Client client = new Client(host, port);
                        client.requestJoke();
                    }
                });
                clientThread.start();
            } else if (input.equalsIgnoreCase("END")) {
                server.stopServer();
                break;
            }
        }
    }
}
