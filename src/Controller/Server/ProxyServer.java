package Controller.Server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * The Proxy for all commands being sent to the server
 *
 * @author Ian Randman
 * @author Matt Antantis
 */
public class ProxyServer implements Server {

    private final static int PORT = 4567;
    private AFRS afrs;
    private ServerSocket serverSocket;

    /**
     * The constructor for the ProxyServer
     *
     * @param afrs the real server to defer commands to
     */
    public ProxyServer(AFRS afrs) {
        this.afrs = afrs;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Runs the proxy server
     */
    public void run() {

        // While the client is connected
        while (!serverSocket.isClosed()) {
            try {
                ServerThread temp = new ServerThread(serverSocket.accept(), this);
                temp.start();
                System.out.println("new connection made");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    /**
     * Executes a command from the client
     *
     * @param command the command to be executed
     * @return the response from the command
     */
    @Override
    public synchronized String runCommand(String[] command) {

        String response;
        int index;

        String end = command[command.length - 1];

        if (!command[0].contains(CONNECT_REQUEST) || command[0].contains(DISCONNECT_REQUEST)) {
            index = Integer.parseInt(command[0]);
        } else {
            index = 0;
        }

        // Test if command is not complete
        if (end.charAt(end.length() - 1) != ';') {
            return index + "," + PARTIAL_REQUEST;
        } else {
            // Cleans the command for the server
            command = getCorrectCommand(command);

            // Checks if client is not connected and wants to connect
            if (command[0].equals(CONNECT_REQUEST)) {
                index = afrs.addClient();
                response = Server.CONNECT_REQUEST + "," + index;
            }
            // Checks if the user is not connected
            else if (index == 0) {
                response = "error,invalid connection";
            }
            // Checks if the user is connected and wants to connect again
            else if (command[1].equals(CONNECT_REQUEST)) {
                response = "error,connection limit reached";
            }
            // Otherwise, the command can be sent to the server
            else {
                response = index + "," + afrs.runCommand(command);
            }
        }
        return response;
    }

    /**
     * Returns command string array without the semicolon at the end
     *
     * @param command a string array containing input
     * @return the same string array with the last char from the last index removed
     */
    private String[] getCorrectCommand(String[] command) {
        String holder = command[command.length - 1];
        command[command.length - 1] = holder.substring(0, holder.length() - 1);
        return command;
    }
}
