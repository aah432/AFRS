package Controller.Requests;

import Model.Components.Itinerary;
import Model.Components.Passenger;
import Model.Managers.ReservationManager;

import java.util.List;

/**
 * This class performs all actions for making a reservation
 *
 * @author Amber Harding
 * @author Matt Antantis
 * @author Mark Vittozzi
 * @author Ian Randman
 */
public class MakeReservationRequest implements UndoableRequest {

    private static final String RESERVE_SUCCESSFUL = "reserve,successful";
    private static final String DUPLICATE_ERROR = "error, duplicate reservation";
    private static final String INVALID_ID_ERROR = "error, invalid id";

    private int id;
    private Passenger passenger;
    private ReservationManager reservationManager;
    private List<Itinerary> requestedItineraries;
    private String origin;
    private String destination;
    private Itinerary itinerary;

    /**
     * Constructor for MakeReservationRequest
     *
     * @param command              a string array representing the information from the user
     * @param reservationManager   The reservation manager needed for this request
     * @param requestedItineraries The list of itineraries that a reservation will be selected from
     */
    public MakeReservationRequest(String[] command, ReservationManager reservationManager,
                                  List<Itinerary> requestedItineraries) {
        this.reservationManager = reservationManager;
        this.requestedItineraries = requestedItineraries;
        this.id = Integer.parseInt(command[2]);
        this.passenger = new Passenger(command[3]);

    }


    /**
     * Has reservation manager make a reservation from the list of itineraries
     *
     * @return a string that indicates if the reservation was successfully created
     */
    @Override
    public String execute() {
        String info;
        try {
            itinerary = requestedItineraries.get(id - 1);
            origin = itinerary.getOrigin();
            destination = itinerary.getDestination();
            boolean status = reservationManager.addReservation(passenger, itinerary);
            if (status)
                info = RESERVE_SUCCESSFUL;
            else
                info = DUPLICATE_ERROR;


        } catch (IndexOutOfBoundsException e) {
            info = INVALID_ID_ERROR;
        }
        return info;
    }

    /**
     * Has the reservation manager undo the making of a reservation
     *
     * @return a message containing the passenger and itinerary unreserved
     */
    @Override
    public String undo() {
        reservationManager.removeReservation(passenger, origin, destination);

        return "make," + passenger.getName() + "," + itinerary;

    }
}
