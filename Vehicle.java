class Vehicle {
    private String number;
    private int driverAge;

    public Vehicle(String number, int age) {
        this.number = number;
        driverAge = age;
    }

    public int getDriverAge() {
        return this.driverAge;
    }

    public String getNumber() {
        return this.number;
    }
}