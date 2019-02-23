package Controller.Requests;

import Model.Components.Passenger;
import Model.Components.Reservation;
import Model.Managers.ReservationManager;

import java.util.List;

/**
 * This class is a command that will retrieve a reservation
 *
 * @author Amber Harding
 * @author Mark Vittozzi
 * @author Ian Randman
 */

public class RetrieveReservationRequest implements Request {

    private Passenger passenger;
    private ReservationManager reservationManager;
    private String origin;
    private String destination;

    /**
     * The RetrieveReservationRequest constructor
     *
     * @param command the command to be processed
     */
    public RetrieveReservationRequest(String[] command, ReservationManager reservationManager) {
        this.passenger = new Passenger(command[2]);
        this.reservationManager = reservationManager;
        if (command.length == 4) {
            try {
                this.origin = "";
                this.destination = command[3];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());

            }
        } else if (command.length == 5) {
            this.origin = command[3];
            this.destination = command[4];
        } else {
            this.origin = "";
            this.destination = "";
        }
    }

    @Override
    public String execute() {

        List<Reservation> reservations = reservationManager.getReservations(passenger, origin, destination);

        StringBuilder info = new StringBuilder("retrieve," + reservations.size());

        for (Reservation r : reservations) {
            info.append(r.getData());
        }

        return info.toString();
    }


}
