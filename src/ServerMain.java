import Controller.Server.AFRS;
import Controller.Server.ProxyServer;

/**
 * This class handles the starting of the server
 */
public class ServerMain {

    /**
     * The main method to run the server
     *
     * @param args the arguments from the command line
     */
    public static void main(String[] args) {
        System.out.println("Starting server");
        AFRS afrs = new AFRS();
        ProxyServer proxy = new ProxyServer(afrs);
        proxy.run();
        System.out.println("Server is running");
    }
}
