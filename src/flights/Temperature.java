package flights;

public class Temperature {

    private double expected;
    private double actual;

    public Temperature(double expected, double actual){
        this.expected = expected;
        this.actual = actual;
    }

    public double getExpectedTemp(){
        return this.expected;
    }

    public double getActualTemp(){
        return this.actual;
    }

    public void setExpectedTemp(double expected){
        this.expected = expected;
    }

    public void setActualTemp(double actual){
        this.actual = actual;
    }
    
}
