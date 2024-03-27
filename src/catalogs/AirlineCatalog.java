package catalogs;
import flights.Airline;

import java.util.ArrayList;

public class AirlineCatalog {

    private ArrayList<Airline> airlineCatalog;

    public AirlineCatalog() { 
    }

    public AirlineCatalog(ArrayList<Airline> airlineCatalog) {
        this.airlineCatalog = airlineCatalog;
    }

    public ArrayList<Airline> getAirlineCatalog() {
        return airlineCatalog;
    }

    public void setAirlineCatalog(ArrayList<Airline> airlineCatalog) {
        this.airlineCatalog = airlineCatalog;
    }

    
}
