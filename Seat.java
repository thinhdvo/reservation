/**
 * A class representing seats in the plane

 */
public class Seat {
    private String seatType; // type
    private String seatNumber; // number
    private Passenger passenger;

    /**
     * A constructor to build the seats for the plane Includes specific instructions
     * on how the plane is set
     * 
     * @param number    - the seat number
     * @param classPref - the class in which the seat is
     */
    public Seat(String number, String classPref) {
        passenger = null; // every seat is null until a passenger is seated
        this.seatNumber = number; // set the seat number
        if (classPref.equalsIgnoreCase("E")) {
            if (number.contains("D") || number.contains("C")) {
                this.seatType = "A";
            } else if (number.contains("A") || number.contains("F")) {
                this.seatType = "W";
            } else {
                this.seatType = "C";
            }
        } else if (classPref.equalsIgnoreCase("F")) {
            if (number.contains("D") || number.contains("A")) {
                this.seatType = "W";
            } else {
                this.seatType = "A";
            }
        }
    }

    /**
     * A getter method to get the seat number
     * 
     * @return seatNumber - the seat number
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * A getter method to get the seat type 
     * 
     * @return seatType - (Window, Aisle or Center)
     */
    public String getSeatType() {
        return seatType;
    }

    /**
     * A getter method to get the passenger assigned to a seat
     * 
     * @return passenger - the passenger assigned
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * A setter method to set a passenger to a seat
     * 
     * @param occupant the passenger to set
     */
    public void setPassenger(Passenger occupant) {
        passenger = occupant;
    }
}
