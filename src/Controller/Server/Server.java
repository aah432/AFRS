package Controller.Server;

/**
 * The interface for the proxy and subject servers
 *
 * @author Ian Randman
 * @author Matt Antantis
 * @author Mark Vittozzi
 */
public interface Server {

    String PARTIAL_REQUEST = "partial-request";
    String INFO_REQUEST = "info";
    String RESERVE_REQUEST = "reserve";
    String RETRIEVE_REQUEST = "retrieve";
    String DELETE_REQUEST = "delete";
    String AIRPORT_REQUEST = "airport";
    String CONNECT_REQUEST = "connect";
    String DISCONNECT_REQUEST = "disconnect";
    String UNDO_REQUEST = "undo";
    String REDO_REQUEST = "redo";
    String CHANGE_MODULE_REQUEST = "server";

    String runCommand(String[] command);
}
