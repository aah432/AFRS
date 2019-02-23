package Model.Components.Weather;

/**
 * A object to hold information from JSON
 *
 * @author Mark Vittozzi
 */
public class Status implements Comparable {
    private String Type;
    private String Reason;
    private String AvgDelay;
    private String EndTime;
    private String MinDelay;
    private String MaxDelay;

    /**
     * Determines which passed in strings are null
     * Non null strings are added to return statement
     *
     * @return a string representing this delay
     */
    @Override
    public String toString() {

        String type = "";
        String reason = "";
        String avg = "";
        String end = "";
        String minDelay = "";
        String maxDelay = "";

        if (Type != null)
            type = "Type: " + Type + ", ";
        if (Reason != null && !Reason.equals(""))
            reason = "Reason " + reason + ", ";
        if (AvgDelay != null)
            avg = "AvgDelay: " + AvgDelay + ", ";
        if (EndTime != null)
            end = "EndTime: " + EndTime + ", ";
        if (MinDelay != null)
            minDelay = "MinTime: " + MinDelay + ", ";
        if (MaxDelay != null)
            maxDelay = "MaxTime: " + MaxDelay;
        return type + reason + avg + end + minDelay + maxDelay;

    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof Status) {
            return Integer.parseInt(this.MaxDelay) - Integer.parseInt(((Status) o).MaxDelay);
        } else
            return 0;
    }
}
