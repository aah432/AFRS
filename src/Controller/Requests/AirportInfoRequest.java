package Controller.Requests;

import Model.Managers.AirportManager;

/**
 * This class performs all actions for finding an airports info
 *
 * @author Amber Harding
 * @author Mark Vittozzi
 */
public class AirportInfoRequest implements Request {

    private String airportCode;
    private AirportManager airportManager;
    private int id;

    /**
     * The AirportInfoRequest Constructor
     *
     * @param command        a string array representing the information from the user
     * @param airportManager the airport manager to be used
     */
    public AirportInfoRequest(String[] command, AirportManager airportManager) {
        this.airportCode = command[2];
        this.id = Integer.parseInt(command[0]);
        this.airportManager = airportManager;
    }

    /**
     * gets required airport information from AirportManager
     *
     * @return info: information to display about intended airport.
     */
    @Override
    public String execute() {
        return airportManager.getAirportInfo(airportCode, id);
    }


}
