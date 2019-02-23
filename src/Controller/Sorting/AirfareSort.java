package Controller.Sorting;

import Model.Components.Itinerary;

import java.util.List;

/**
 * Algorithm for sorting the flight itineraries based on airfare
 *
 * @author Jonathon Chierchio
 * @author Ian Randman
 */
public class AirfareSort implements Sort {

    /**
     * method for performing a sort on total airfare of itineraries
     *
     * @param itineraries list of itineraries being sorted
     */
    public void sort(List<Itinerary> itineraries) {

        int length = itineraries.size();

        for (int index = 0; index < length; index++) {
            int pos = index;
            for (int j = index; j < length; j++) {
                if (itineraries.get(j).getAirfare() < itineraries.get(pos).getAirfare())
                    pos = j;
            }

            Itinerary min = itineraries.get(pos);
            itineraries.set(pos, itineraries.get(index));
            itineraries.set(index, min);

        }

    }
}
