package Controller.Requests;

import Model.Components.Itinerary;
import Model.Components.Passenger;
import Model.Managers.ReservationManager;

/**
 * This class performs all actions for deleting a reservation
 *
 * @author Amber Harding
 * @author Mark Vittozzi
 * @author Ian Randman
 */
public class DeleteReservationRequest implements UndoableRequest {
    private final static String DELETE_SUCCESSFUL = "delete successful";
    private final static String DELETE_ERROR = "error reservation not found";

    private Passenger passenger;
    private ReservationManager reservationManager;
    private String origin;
    private String destination;
    private Itinerary itinerary;

    /**
     * The DeleteReservationRequest Constructor
     *
     * @param command            a string array representing the information from the user
     * @param reservationManager the reservation manager to be used
     */
    public DeleteReservationRequest(String[] command, ReservationManager reservationManager) {
        this.passenger = new Passenger(command[2]);
        this.reservationManager = reservationManager;
        this.origin = command[3];
        this.destination = command[4];
    }

    /**
     * Has the reservation manager delete intended reservation
     *
     * @return a string indicating if the reservation deletion was successful
     */
    @Override
    public String execute() {
        itinerary = reservationManager.getReservations(passenger, origin, destination).get(0).getItinerary();

        if (reservationManager.removeReservation(passenger, origin, destination))
            return DELETE_SUCCESSFUL;
        else
            return DELETE_ERROR;
    }

    /**
     * Has the reservation manager undo the deletion of a reservation
     *
     * @return a message containing the passenger and itinerary
     */
    @Override
    public String undo() {
        reservationManager.addReservation(passenger, itinerary);
        return "delete," + passenger.getName() + "," + itinerary;
    }
}
