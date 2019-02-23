package Controller.Server;

import Controller.Requests.*;
import Model.Components.Itinerary;
import Model.Managers.AirportManager;
import Model.Managers.FlightManager;
import Model.Managers.ReservationManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Coordinates requests from the client
 * and finds the necessary information
 *
 * @author Amber Harding
 * @author Mark Vittozzi
 * @author Ian Randman
 * @author Matt Antantis
 */
public class AFRS implements Server {


    private AirportManager airportManager;
    private FlightManager flightManager;
    private ReservationManager reservationManager;

    private Map<Integer, List<Itinerary>> itineraryMap;
    private Map<Integer, UndoRedoRequestHandler> undoRedoRequestHandlerMap;

    private int clientIDIndex;


    /**
     * Construction for Controller.Server.AFRS. Initializes Model.Managers
     */
    public AFRS() {
        this.airportManager = new AirportManager();
        this.flightManager = new FlightManager(airportManager);
        this.reservationManager = new ReservationManager();

        this.itineraryMap = new HashMap<>();
        this.undoRedoRequestHandlerMap = new HashMap<>();
        this.clientIDIndex = 1;

    }

    /**
     * Adds a connection to the map of connections
     */
    synchronized int addClient() {
        itineraryMap.put(clientIDIndex, null);
        undoRedoRequestHandlerMap.put(clientIDIndex, new UndoRedoRequestHandler());
        clientIDIndex++;
        return clientIDIndex - 1;
    }

    /**
     * Reads in a string, from this a request in created and executed.
     *
     * @param command a string array that is used to create and execute a request
     */
    public synchronized String runCommand(String[] command) {
        Request request;
        String response = "";
        int index = Integer.parseInt(command[0]);

        switch (command[1]) {
            case INFO_REQUEST:
                //create flight info request
                request = new FlightInfoRequest(command, airportManager, flightManager);
                response = request.execute();
                itineraryMap.put(index, ((FlightInfoRequest) request).getRequestedItineraries());
                request = null;
                break;

            case RESERVE_REQUEST:
                //create reservation request
                request = new MakeReservationRequest(command, reservationManager, itineraryMap.get(index));
                undoRedoRequestHandlerMap.get(index).addRequest((MakeReservationRequest) request);

                break;

            case RETRIEVE_REQUEST:
                //create retrieve reservation request
                request = new RetrieveReservationRequest(command, reservationManager);
                break;

            case DELETE_REQUEST:
                //create delete reservation request
                request = new DeleteReservationRequest(command, reservationManager);
                undoRedoRequestHandlerMap.get(index).addRequest((DeleteReservationRequest) request);
                break;

            case AIRPORT_REQUEST:
                //create airport request
                request = new AirportInfoRequest(command, airportManager);
                break;

            case DISCONNECT_REQUEST:
                itineraryMap.remove(index);
                undoRedoRequestHandlerMap.remove(index);
                System.out.println(index + " disconnected");
                return DISCONNECT_REQUEST;

            case UNDO_REQUEST:
                request = null;
                response = undoRedoRequestHandlerMap.get(index).undo();
                break;

            case REDO_REQUEST:
                request = null;
                response = undoRedoRequestHandlerMap.get(index).redo();
                break;
            case CHANGE_MODULE_REQUEST:
                request = new SetInfoModuleRequest(command, airportManager);
                break;

            default:
                request = null;
                response = "error, Unknown request";
                break;
        }

        if (request != null) {
            response = request.execute();
        }

        return response;
    }
}
