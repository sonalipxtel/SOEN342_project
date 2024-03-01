package flights;

public class Airport {
    private String ap_name;
    private String ap_code;

    public Airport(String ap_name, String ap_code) {
        this.ap_name = ap_name;
        this.ap_code = ap_code;
    }

    public String getAp_name() {
        return ap_name;
    }

    public void setAp_name(String ap_name) {
        this.ap_name = ap_name;
    }

    public String getAp_code() {
        return ap_code;
    }

}
