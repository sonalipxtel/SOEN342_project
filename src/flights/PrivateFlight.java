package flights;

public class PrivateFlight extends FlightDetails {

    public PrivateFlight(String f_number, Airport source, Airport destination, Airline airline, Aircraft aircraft,
            String scheduled_dep, String actual_dep, String scheduled_arr, String actual_arr, City city) {
        super(f_number, source, destination, airline, aircraft, scheduled_dep, actual_dep, scheduled_arr, actual_arr, city);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
