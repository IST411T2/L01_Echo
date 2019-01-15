package lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A simple Echo Server using Java 8 syntax (lambda expressions)
 * This Server is working in a blocking mode (i. e. serves only one client at a time).
 * When a client disconnects, the Server waits for other client to connect.
 * @author Team2(IST411)
 */
public class Server_J8 {

    public static void main(String[] args) {
        System.out.println("Simple Echo Server");

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(12345);) {
                
                System.out.println("Waiting for connection.....");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client");

                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

                    Supplier<String> socketInput = () -> {
                        try {
                            return br.readLine();
                        } catch (IOException ex) {
                            return null;
                        }
                    };

                    Stream<String> stream = Stream.generate(socketInput);
                    stream
                            .map(s -> {
                                if (s != null) {
                                    System.out.println("Client request: " + s);
                                    out.println(s);
                                }

                                return s;
                            })
                            .allMatch(s -> s != null);              

                }
            } catch (IOException ex) {
                System.out.println("Server error: " + ex.getMessage());
            }

        }
    }
}
