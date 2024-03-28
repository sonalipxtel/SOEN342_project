package flights;

public class City {
    private String c_name;
    private String country;
    private int temperature;

    public City(){
        this.c_name = "";
        this.country = "";
        this.temperature = 0;
    }
    public City(String c_name, String country, int temperature){
        this.c_name = c_name;
        this.country = country;
        this.temperature = temperature;
    }
    public String getCityName(){
        return c_name;
    }
    public String getCountry(){
        return country;
    }
    public int getTemperature() { return temperature; }
    public void setCityName(String c_name){
        this.c_name = c_name;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public void setTemperature(int temperature) { this.temperature = temperature; }
}
