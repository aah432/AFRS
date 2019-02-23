package Controller.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class allows the server to always be listening for requests coming in from clients. To accomplish this, it is
 * run as a thread.
 */
public class ServerThread extends Thread {
    private Server server;
    private Socket socket;
    private ObjectInputStream netIn;
    private ObjectOutputStream out;

    /**
     * Set up the input and output streams for the client-server connection.
     *
     * @param socket the server's connection to the client
     * @param server the main server
     */
    ServerThread(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;

        try {
            this.netIn = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Alternate between receiving a request from the Client and send a response back to the Client.
     */
    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String[] command = (String[]) (netIn.readUnshared());

                String response = server.runCommand(command);

                String[] brokenResponse = response.split(",");
                out.writeUnshared(response);

                if (brokenResponse[1].equals(Server.DISCONNECT_REQUEST)) {
                    out.close();
                    netIn.close();
                    socket.close();
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
