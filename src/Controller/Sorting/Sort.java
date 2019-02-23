package Controller.Sorting;

import Model.Components.Itinerary;

import java.util.List;

/**
 * Deals with the sorting algorithm needed to sort flight itineraries
 *
 * @author Amber Harding
 * @author Ian Randman
 */
public interface Sort {

    void sort(List<Itinerary> itineraries);
}
