package flights;

public class Temperature {

    private double expectedTemp;
    private double actualTemp;

    public Temperature() {
        this.expectedTemp = 0;
        this.actualTemp = 0;
    }

    public Temperature(double expectedTemp, double actualTemp) {
        this.expectedTemp = expectedTemp;
        this.actualTemp = actualTemp;
    }

    public double getExpectedTemp() {
        return expectedTemp;
    }

    public void setExpectedTemp(double expectedTemp) {
        this.expectedTemp = expectedTemp;
    }

    public double getActualTemp() {
        return actualTemp;
    }

    public void setActualTemp(double actualTemp) {
        this.actualTemp = actualTemp;
    }

}
