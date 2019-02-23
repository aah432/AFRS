package Model.Managers;

import Model.Components.Flight;
import Model.Components.Itinerary;
import Model.Components.Passenger;
import Model.Components.Reservation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class handles all the Reservations for the AFRS
 *
 * @author Matt Antantis
 */
public class ReservationManager extends Manager {

    private ArrayList<Reservation> reservations;

    /**
     * The constructor for the ReservationManager
     */
    public ReservationManager() {
        reservations = new ArrayList<>();

        try {
            buildList();
            System.out.println("..done");
        } catch (FileNotFoundException e) {
            System.out.println("Data not found");

        }
    }

    /**
     * Builds the reservations from a saved text file
     *
     * @throws FileNotFoundException if reservations.txt is not found
     */
    private void buildList() throws FileNotFoundException {
        System.out.print("restoring reservations\t");
        reader = new Scanner(new FileReader("data/reservations.txt"));

        while (reader.hasNext()) {
            String[] line = reader.nextLine().split(",");
            Passenger tempPassenger = new Passenger(line[0]);
            Flight[] tempFlights = new Flight[Integer.parseInt(line[1])];

            int index = 0;
            for (int i = 2; i < line.length; i += 6) {
                tempFlights[index] = (new Flight(Arrays.copyOfRange(line, i, i + 6)));
                index++;
            }

            reservations.add(new Reservation(tempPassenger, new Itinerary(tempFlights)));

        }
    }

    /**
     * Adds a new Reservation to the reservations
     *
     * @param passenger the passenger for the reservation
     * @param itinerary the itinerary for the reservation
     * @return if the reservation was successful
     */
    public boolean addReservation(Passenger passenger, Itinerary itinerary) {

        Reservation temp = new Reservation(passenger, itinerary);

        if (reservations.contains(temp)) {
            return false;
        }
        reservations.add(temp);
        saveData();
        return true;
    }

    /**
     * Removes a reservation from the list
     *
     * @param passenger   the passenger to be removed
     * @param origin      the reservation's starting location
     * @param destination the reservation's ending location
     * @return if the reservation was successfully removed
     */
    public boolean removeReservation(Passenger passenger, String origin, String destination) {
        for (Reservation r : reservations) {
            if (passenger.equals(r.getPassenger())) {
                if (origin.equals(r.getOrigin()) && destination.equals(r.getDestination())) {
                    reservations.remove(r);
                    saveData();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the reservations for a passenger based on the origin and destination
     *
     * @param passenger   the passenger with reservations
     * @param origin      the passenger's origin
     * @param destination the passenger's destination
     * @return all the passenger's reservations
     */
    public ArrayList<Reservation> getReservations(Passenger passenger, String origin, String destination) {
        ArrayList<Reservation> reserved = new ArrayList<>();
        if (origin.equals("")) {
            if (destination.equals("")) {

                for (Reservation r : reservations) {
                    if (passenger.equals(r.getPassenger()))
                        reserved.add(r);
                }
            } else {

                for (Reservation r : reservations) {
                    if (passenger.equals(r.getPassenger()) && destination.equals(r.getDestination()))
                        reserved.add(r);
                }
            }
        } else if (destination.equals("")) {

            for (Reservation r : reservations) {
                if (passenger.equals(r.getPassenger()) && origin.equals(r.getOrigin()))
                    reserved.add(r);
            }
        } else {

            for (Reservation r : reservations) {
                if (passenger.equals(r.getPassenger())) {
                    if (origin.equals(r.getOrigin()) && destination.equals(r.getDestination())) {
                        reserved.add(r);

                    }
                }
            }
        }

        return reserved;
    }

    /**
     * Saves the current reservations to a text file
     */
    private void saveData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/reservations.txt"));
            for (Reservation r : reservations) {
                writer.write(r.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
