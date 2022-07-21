class Slot {
    static int counter = 1;
    private int lotNumber;
    private Vehicle vehicle;

    public Slot() {
        lotNumber = counter;
        counter++;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public int getSlotNumber() {
        return this.lotNumber;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public Boolean isEmpty() {
        return this.vehicle == null ? true : false;
    }

    public void freeUpLot() {
        this.vehicle = null;
    }
}