package View;

import Controller.Server.AFRS;
import Controller.Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;

/**
 * Handles the interactions with the user
 * and communicates with the server
 *
 * @author Matt Antantis
 * @author Mark Vittozzi
 * @author Amber Harding
 */
public class Client extends Observable {

    private String response;
    private boolean partial;
    private Scanner console;
    private String command;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream netIn;
    private int id;


    /**
     * Constructor for the View.Client
     */
    public Client() {
        this.response = "";
        this.partial = false;
        this.console = new Scanner(System.in);
        this.command = "";
        try {
            socket = new Socket("localhost", 4567);
            out = new ObjectOutputStream(socket.getOutputStream());
            netIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Create a new Client a start the exchange process.
     *
     * @param args none
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    /**
     * Handles asking for a user input
     * and displaying the response
     */
    private void run() {
        while (!socket.isClosed()) {
            response = "";
            makeRequest();
        }
    }

    /**
     * Handles the user input for the program
     */
    private void makeRequest() {
        System.out.print(">");
        String value = console.next();
        sendCommand(value);
    }

    /**
     * Sends a command over the network
     *
     * @param value the command to be sent
     */
    void sendCommand(String value) {

        // Checks if the last command sent was partial
        if (!partial) {
            command = value;

            // Checks if the command is not a connect request or does not have an id
            if (!(command.equals(Server.CONNECT_REQUEST + ';') || Character.isDigit(command.charAt(0)))) {
                command = id + "," + command;
            }
        } else {
            command += value;
        }

        // Splits the command
        String[] splitCommand = command.split(",");

        // Checks if the command was not a connect request
        if (!command.equals(Server.CONNECT_REQUEST + ';')) {
            splitCommand[0] = String.valueOf(id);
        }

        // Writes the split command to the network
        try {
            out.writeUnshared(splitCommand);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        getResponse();
    }

    /**
     * Sets the response for the most recent command
     **/
    private void getResponse() {

        // Attempts to get a response from the network
        try {
            response = (String) netIn.readUnshared();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        // Checks if the response was a partial
        if (this.response.equals(id + "," + AFRS.PARTIAL_REQUEST)) {
            System.out.println("Partial Request, Please finish request");
            partial = true;
        } else {
            String[] split = response.split(",");

            // Checks if the response was a connection
            if (split[0].equals("connect")) {
                id = Integer.parseInt(split[1]);

            } else if (split.length > 1) {
                if (split[1].equals(Server.DISCONNECT_REQUEST)) {
                    disconnect();
                }
            }
            partial = false;
            System.out.println(response);

        }

        super.setChanged();
        super.notifyObservers(response);
    }

    /**
     * Disconnects the client from the network
     */
    void disconnect() {
        try {
            out.close();
            netIn.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
