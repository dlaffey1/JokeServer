package Project_files;
//David Laffey
// import Project_files.ReadCSV;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;
// import java.util.Scanner;

public class ServerHost {
    //Declaring variables
    private String host;
    private int port;
    private ServerSocket serverSocket;
    private boolean isRunning;

//ServerHost initialises port and host and sets isRunning to true
    public ServerHost(String host, int port) {
        this.host = host;
        this.port = port;
        this.isRunning = true;
    }
//Starts the server then goes in loop to accept clients
    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on " + host + ":" + port);
//isRunning was initialised earlier as true so this loop will run until isRunning is set to false
            while (isRunning) {
                //

                Socket clientSocket = serverSocket.accept();
                //Prints out the IP address of the client
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Handle the client socket (e.g., create a new thread to handle the client request)
                Thread thread = new Thread(new MyClientHandler(clientSocket));
                thread.start();
            }
        //Catches any errors and prints them out
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }
//Function created to stop the server
    public void stopServer() {
        try {
        //stop server socket so no more clients can connect
            serverSocket.close();
            System.out.println("Server stopped.");
        } catch (IOException e) {
            //Catch statement to print out errors if cant stop server properly
            System.out.println("Error stopping the server: " + e.getMessage());
        }
    }

    private class MyClientHandler implements Runnable {
        private Socket clientSocket;
    
        public MyClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
    
        @Override
        public void run() {
            try {
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    
                String command = reader.readLine();
                if (command.equals("JOKE")) {
                    String joke = generateJoke();
                    writer.println(joke);
                } else if (command.equals("END")) {
                    writer.println("Server is shutting down...");
                    isRunning = false;
                } else {
                    writer.println("Invalid command");
                }
    
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.out.println("Error handling client request: " + e.getMessage());
            }
        }
    
        private String generateJoke() {
            String joke = ReadCSV.ReadJokeFromCSV();
            if (joke == null) {
                return "No jokes available";
            }
            return joke;
        }
    }
}
        