package flights;

public class Airline {
    private String al_name;
    private String al_code;

    public Airline(String al_name, String al_code) {
        this.al_name = al_name;
        this.al_code = al_code;

    }

    public String getAl_name() {
        return al_name;
    }

    public void setAl_name(String al_name) {
        this.al_name = al_name;
    }

    public String getAl_code() {
        return al_code;
    }

    public void setAl_code(String al_code) {
        this.al_code = al_code;
    }

    public String toString() {
        return "Airline Name: " + al_name + ", Airline Code: " + al_code;
    }

}
