package catalogs;
import flights.Flight;

import java.util.ArrayList;

public class FlightCatalog {
    private ArrayList<Flight> flightCatalog;

    public FlightCatalog() {
    }

    public FlightCatalog(ArrayList<Flight> flightCatalog) {
        this.flightCatalog = flightCatalog;
    }

    public ArrayList<Flight> getFlightCatalog() {
        return flightCatalog;
    }

    public void setFlightCatalog(ArrayList<Flight> flightCatalog) {
        this.flightCatalog = flightCatalog;
    }
    
}
