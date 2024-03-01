package flights;

public class Flight {

    private String f_number;
    private Airport source;
    private Airport destination;

    public Flight(String f_number, Airport source, Airport destination) {
        this.f_number = f_number;
        this.source = source;
        this.destination = destination;
    }

    public String getF_number() {
        return f_number;
    }

    public void setF_number(String f_number) {
        this.f_number = f_number;
    }

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

}
