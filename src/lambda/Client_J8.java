package lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;


/**
 * A simple Echo Client using Java 8 syntax (lambda expressions)
 * @author Team2(IST411)
 */
public class Client_J8 {

    public static void main(String args[]) {
        System.out.println("Simple Echo Client");

        try {
            System.out.println("Waiting for connection.....");
            InetAddress localAddress = InetAddress.getLocalHost();
            try (Socket clientSocket = new Socket(localAddress, 12345);
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()))) {

                System.out.println("Connected to server");
                Scanner scanner = new Scanner(System.in);

                Supplier<String> scannerInput = () -> scanner.nextLine();
                System.out.print("Enter text: ");
                
                Stream.generate(scannerInput)
                        .map(s -> {
                            out.println(s);
                            try {
                                System.out.println("Server response: " + br.readLine());
                            } catch (IOException ex) {
                                return null;
                            }                           
                            if (!"quit".equalsIgnoreCase(s)) System.out.print("Enter text: ");
                            return s;
                        })
                        .allMatch(s -> !"quit".equalsIgnoreCase(s));

            }
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
        }

    }
}
