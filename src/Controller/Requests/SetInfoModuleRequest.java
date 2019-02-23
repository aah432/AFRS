package Controller.Requests;

import Model.Managers.AirportManager;

/**
 * This class performs all actions for changing the info server a client is using
 *
 * @author Mark Vittozzi
 */

public class SetInfoModuleRequest implements Request {


    private String server;
    private AirportManager airportManager;
    private int id;

    /**
     * The AirportInfoRequest Constructor
     *
     * @param command        a string array representing the information from the user
     * @param airportManager the airport manager to be used
     */
    public SetInfoModuleRequest(String[] command, AirportManager airportManager) {
        this.server = command[2];
        this.id = Integer.parseInt(command[0]);
        this.airportManager = airportManager;
    }

    /**
     * Calls the switch client method on its airport manager and returns a string
     * alerting the client that the server was successfully changed
     */
    @Override
    public String execute() {
        airportManager.switchClientModule(id, server);
        return "Server,Successful";

    }
}
