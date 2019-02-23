package Model.Components.Weather;

import java.util.Collections;
import java.util.List;

/**
 * An object to hold information from JSON
 *
 * @author Mark Vittozzi
 */


public class WebAirport {
    private String Name;
    private int DelayCount;
    private Weather Weather;
    private List<Status> Status;

    /**
     * @return the list of statuses as a single string
     */
    private String getStatus() {
        StringBuilder statusString = new StringBuilder();
        for (int i = 0; i < Status.size(); i++) {
            if (i == Status.size() - 1)
                statusString.append(i + 1).append(" ").append(Status.get(i));
            else
                statusString.append(i + 1).append(" ").append(Status.get(i)).append("\n");
        }
        return statusString.toString();
    }


    /**
     * @return a string representing the class
     */
    @Override
    public String toString() {
        Collections.sort(Status);
        if (DelayCount > 0)
            return Name + "," + Weather + ",Delay count: " + DelayCount + "\nDelays: \n" + getStatus();
        else
            return Name + "," + Weather + ",Delay count: " + DelayCount;
    }
}
