package catalogs;
import flights.Airport;

import java.util.ArrayList;

public class AirportCatalog {
    private ArrayList<Airport> airportCatalog;

    public AirportCatalog() {
    }

    public AirportCatalog(ArrayList<Airport> airportCatalog) {
        this.airportCatalog = airportCatalog;
    }

    public ArrayList<Airport> getAirportCatalog() {
        return airportCatalog;
    }

    public void setAirportCatalog(ArrayList<Airport> airportCatalog) {
        this.airportCatalog = airportCatalog;
    }
    
}
