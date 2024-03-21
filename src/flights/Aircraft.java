package flights;

public class Aircraft {
    private String model;
    private String registrationNumber;
    public Status status;

    public Aircraft(String model, String registrationNumber, Status status) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.status = status;
    }

    // Method used in RegisteredUser class
    public Aircraft(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Aircraft Model: " + model + ", Registration Number: " + registrationNumber + ", Status: " + status;
    }

}
