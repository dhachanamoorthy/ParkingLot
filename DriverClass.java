import java.util.*;
import java.util.stream.*;

// Driver class to the business logic
class DriverClass {

    // constants for each operations
    public final String CREATE_SLOT = "Create_parking_lot";
    public final String PARK = "Park";
    public final String DRIVER_AGE = "driver_age";
    public final String SLOT_NUMBER_FOR_CAR = "Slot_number_for_car_with_number";
    public final String SLOT_NUMBER_FOR_DR_AGE = "Slot_numbers_for_driver_of_age";
    public final String REG_NUM_BY_DR_AGE = "Vehicle_registration_number_for_driver_of_age";
    public final String LEAVE = "Leave";


    Map<Integer, Slot> slots = new TreeMap<>();
    List<String> outputs = new ArrayList<>(); // to store all the outputs

    public void run() {
        FileService fileService = new FileService();
        List<String> inputs = fileService.readInputs();
        for (String input : inputs) {
            List<String> query = Arrays.asList(input.split(" "));
            switch (query.get(0)) {
                case CREATE_SLOT:
                    int lotSize = Integer.valueOf(query.get(1));
                    createLotsWith(lotSize);
                    break;
                case PARK:
                    String vehicleNumber = query.get(1);
                    int age = Integer.valueOf(query.get(3));
                    Vehicle vehicle = new Vehicle(vehicleNumber, age);
                    assignSlotTo(vehicle);
                    break;
                case LEAVE:
                    int lotNumber = Integer.valueOf(query.get(1));
                    unassignSlotWith(lotNumber);
                    break;
                case SLOT_NUMBER_FOR_CAR:
                    vehicleNumber = query.get(1);
                    getLotNumberOf(vehicleNumber);
                    break;
                case REG_NUM_BY_DR_AGE:
                    age = Integer.valueOf(query.get(1));
                    getRegNumByDrAge(age);
                    break;
                case SLOT_NUMBER_FOR_DR_AGE:
                    age = Integer.valueOf(query.get(1));
                    break;
                default:
                    System.out.println(query.get(0) +" was not handled!!!");
                    break;
            }

        }
        // To write output in "output.txt"
        // fileService.writeOutput(outputs);  
        for(String output: outputs){
            System.out.println(output);
        }
      
    }

    public void createLotsWith(int n) {
        while (n > 0) {
            Slot lot = new Slot();
            slots.put(lot.getSlotNumber(), lot);
            n--;
        }
        String output = "Created parking of " + slots.size() + " slots";
        outputs.add(output);
    }

    public void assignSlotTo(Vehicle vehicle) {
        Integer lotNumber = slots.entrySet()
                .stream()
                .filter(slotEntry -> slotEntry.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null); // to get nearest lot number
        if (lotNumber != null) {
            Slot currentSlot = slots.get(lotNumber);
            currentSlot.setVehicle(vehicle);
            slots.put(lotNumber, currentSlot);
            String output = "Car with vehicle registration number " + vehicle.getNumber()
                    + " has been parked at slot number " + lotNumber;
            outputs.add(output);
        }
        else{
          outputs.add("No Free Slots Found!!!");
        }
    }

    public void unassignSlotWith(int lotNumber) {
        Slot currentSlot = slots.get(lotNumber);
        if(currentSlot.isEmpty()){
            outputs.add("Slot already Empty!!!");
            return;
        }
        String output = "Slot number " + lotNumber + " vacated, the car with vehicle registration number "
                + currentSlot.getVehicle().getNumber() + " left the space, the driver of the car was of age "
                + currentSlot.getVehicle().getDriverAge();
        outputs.add(output);
        currentSlot.freeUpLot();
    }

    public void getLotNumberOf(String vehicleNumber) {
        Integer slot = slots.entrySet()
                .stream()
                .filter(entry -> entry.getValue()
                        .getVehicle()
                        .getNumber()
                        .equalsIgnoreCase(vehicleNumber))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        if(slot == null){
            outputs.add("Vehicle not found in any of the slot!!!");
        }
        outputs.add(String.valueOf(slot));
    }

    public void getRegNumByDrAge(int age) {
        List<Slot> filteredSlots = slots.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getVehicle() != null &&
                        entry.getValue()
                                .getVehicle()
                                .getDriverAge() == age)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        
        if(filteredSlots.size()==0){
            outputs.add("No vehicles has driver of age "+ age);
            return;
        }
        String vehicles = "";
        for (Slot slot : filteredSlots) {
            vehicles += slot.getVehicle().getNumber() + ",";
        }
        outputs.add(vehicles);
    }

    public void getSlotByAge(int age) {
        List<Integer> filteredSlots = slots.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getVehicle() != null &&
                        entry.getValue()
                                .getVehicle()
                                .getDriverAge() == age)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if(filteredSlots.size()==0){
            outputs.add("No Slots occupied by driver of age "+ age);
            return;
        }
        String slotNumbers = "";
        for (Integer slot : filteredSlots) {
            slotNumbers += String.valueOf(slot) + ",";
        }

        outputs.add(slotNumbers);
    }
}