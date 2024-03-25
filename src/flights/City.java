package flights;

public class City {

    private String c_name;
    private String country;

    public City(String c_name, String country){
        this.c_name = c_name;
        this.country = country;
    }

    public String getCityName(){
        return c_name;
    }

    public String getCountry(){
        return country;
    }

    public void setCityName(String c_name){
        this.c_name = c_name;
    }

    public void setCountry(String country){
        this.country = country;
    }
}
