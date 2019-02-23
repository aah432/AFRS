package Controller.Sorting;

import Model.Components.Itinerary;

import java.util.List;

/**
 * Algorithm for sorting the flight itineraries based on arrival time
 *
 * @author Jonathon Chierchio
 * @author Ian Randman
 */
public class ArrivalSort implements Sort {

    /**
     * method for performing a sort on arrival times
     *
     * @param itineraries list of itineraries being sorted
     */
    public void sort(List<Itinerary> itineraries) {

        int length = itineraries.size();

        for (int index = 0; index < length; index++) {
            int pos = index;
            for (int j = index; j < length; j++) {
                if (itineraries.get(j).getArrivalTime() < itineraries.get(pos).getArrivalTime())
                    pos = j;
            }

            Itinerary min = itineraries.get(pos);
            itineraries.set(pos, itineraries.get(index));
            itineraries.set(index, min);

        }
    }
}
