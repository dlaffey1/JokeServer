package Project_files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void requestJoke() {
        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
    
            // Send request for a joke
            writer.println("JOKE");
    
            // Read and print the response
            String joke = reader.readLine();
            System.out.println("Received joke: " + joke);
    
            // Introduce a delay between requests
            Thread.sleep(1000);
    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        int numClients = 2; // Number of client instances to create

        String host = "localhost";
        int port = 1234;

        for (int i = 0; i < numClients; i++) {
            Thread thread = new Thread(() -> {
                Client client = new Client(host, port);
                client.requestJoke();
            });
            thread.start();
        }
    }
}
