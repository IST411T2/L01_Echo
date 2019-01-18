package simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple Echo Server
 * This Server is working in a blocking mode (i. e. serves only one client at a time).
 * When a client disconnects, the Server waits for other client to connect.
 * @author Team2(IST411)
 */
public class Server {

    public static void main(String[] args) {
        System.out.println("Simple Echo Server");

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(12345);) {
                
                System.out.println("Waiting for connection.....");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client");

                try (PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));) {

                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println("Server: " + inputLine);
                    out.println(inputLine);
                }
               
                }
            } catch (IOException ex) {
                System.out.println("Server error: " + ex.getMessage());
            }

        }
    }
}
