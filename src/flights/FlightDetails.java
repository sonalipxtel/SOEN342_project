package flights;

public class FlightDetails extends Flight {
    private Airline airline;
    private Aircraft aircraft;
    private String scheduled_dep;
    private String actual_dep;
    private String scheduled_arr;
    private String actual_arr;

    public FlightDetails(String f_number, Airport source, Airport destination, Airline airline, Aircraft aircraft,
            String scheduled_dep, String actual_dep, String scheduled_arr, String actual_arr) {
        super(f_number, source, destination);
        this.airline = airline;
        this.aircraft = aircraft;
        this.scheduled_dep = scheduled_dep;
        this.actual_dep = actual_dep;
        this.scheduled_arr = scheduled_arr;
        this.actual_arr = actual_arr;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getScheduled_dep() {
        return scheduled_dep;
    }

    public void setScheduled_dep(String scheduled_dep) {
        this.scheduled_dep = scheduled_dep;
    }

    public String getActual_dep() {
        return actual_dep;
    }

    public void setActual_dep(String actual_dep) {
        this.actual_dep = actual_dep;
    }

    public String getScheduled_arr() {
        return scheduled_arr;
    }

    public void setScheduled_arr(String scheduled_arr) {
        this.scheduled_arr = scheduled_arr;
    }

    public String getActual_arr() {
        return actual_arr;
    }

    public void setActual_arr(String actual_arr) {
        this.actual_arr = actual_arr;
    }

    public String toString() {
        return super.toString() + "\n" +
                "Airline: " + airline + "\n" +
                "Aircraft: " + aircraft + "\n" +
                "Scheduled Departure: " + scheduled_dep + "\n" +
                "Actual Departure: " + actual_dep + "\n" +
                "Scheduled Arrival: " + scheduled_arr + "\n" +
                "Actual Arrival: " + actual_arr;
    }

}