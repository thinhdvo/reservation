import java.util.*;

/**
 * A class representing the main core of the plane This plane has 2 classes
 * (economy and first) which are full of seats
 * 
 * @author Karan Bhargava
 * @version 1.5718
 */
public class Airplane {
    private static ArrayList<Seat> firstClassSeats;
    private static ArrayList<Seat> economySeats;
    private static final int E_COLUMNS = 6;
    private static final int E_ROWS = 20;
    private static final int E_START_SEAT = 10;
    private static final int E_SIZE = (E_ROWS * E_COLUMNS);
    private int economyRemainingSeats = E_SIZE;
    private static final int F_ROWS = 2;
    private static final int F_COLUMNS = 4;
    private static final int F_SIZE = (F_ROWS * F_COLUMNS);
    private static int firstRemainingSeats = F_SIZE;
    public static int FILE_PARAMETER = 4; // Each passenger has 4 parameters when reading from the file

    /**
     * A constructor to populate the plane
     */
    public Airplane() {
        firstClassSeats = populateFirst();
        economySeats = populateEconomy();
    }

    /**
     * A private helper method to populate the seats in economy class Each seat is
     * set up specifically according to the instructions
     * 
     * @return economySeats - an arrayList representing
     */
    private static ArrayList<Seat> populateEconomy() {
        economySeats = new ArrayList<Seat>();
        String seat = "";
        // loop to create the seats
        for (int i = E_START_SEAT; i <= E_ROWS + E_START_SEAT; i++) {
            for (int k = 1; k <= E_COLUMNS; k++) {
                seat += i;
                if (k == 6)
                    seat += "F";
                else if (k == 5)
                    seat += "E";
                else if (k == 4)
                    seat += "D";
                else if (k == 3)
                    seat += "C";
                else if (k == 2)
                    seat += "B";
                else if (k == 1)
                    seat += "A";
                // add the seats to the arrayList
                economySeats.add(new Seat(seat, "E"));
                seat = ""; // reset the loop
            }
        }
        return economySeats;
    }

    /**
     * A private helper method to populate the seats in first class Each seat is set
     * up specifically according to the instructions
     * 
     * @return firstClassSeats - an array with the seats in first class
     */
    private static ArrayList<Seat> populateFirst() {
        firstClassSeats = new ArrayList<Seat>();
        String seat = "";
        // loop to initialize the seats
        for (int i = 1; i <= F_ROWS; i++) {
            for (int k = 1; k <= F_COLUMNS; k++) {
                seat += i;
                if (k == 4)
                    seat += "D";
                else if (k == 3)
                    seat += "C";
                else if (k == 2)
                    seat += "B";
                else if (k == 1)
                    seat += "A";
                // add the seats to the arrayList
                firstClassSeats.add(new Seat(seat, "F"));
                // reset the seat
                seat = "";
            }
        }
        return firstClassSeats;
    }

    /**
     * Adds previously loaded passengers from the provided file (if any)
     * 
     * @param p    - the passenger to be seated
     * @param seat - the passenger's seat
     */
    public void addInfo(Passenger p, int seat) {
        if (p.getClassPref().equalsIgnoreCase("E")) {
            economySeats.get(seat).setPassenger(p);
            economyRemainingSeats--;
        } else if (p.getClassPref().equalsIgnoreCase("F")) {
            firstClassSeats.get(seat).setPassenger(p);
            firstRemainingSeats--;
        }
    }

    /**
     * A getter method to get the plane's manifest
     * 
     * @param classPref - the class preference the user wants
     * @return returnManifest - the manifest to return
     */
    public String getPlaneManifest(String classPref) {
        String returnManifest = "";
        int x = 0;
        // adds the manifest of the economy class
        if (classPref.equalsIgnoreCase("Economy")) {
            returnManifest += "Economy\n";
            for (x = 0; x < E_SIZE; x++) {
                if (economySeats.get(x).getPassenger() != null) {
                    returnManifest += economySeats.get(x).getSeatNumber() + ": ";
                    returnManifest += economySeats.get(x).getPassenger().getPassengerName() + "\n";
                }
            }
            // adds the manifest of the first class
        } else if (classPref.equalsIgnoreCase("First")) {
            returnManifest += "First\n";
            for (x = 0; x < F_SIZE; x++) {
                if (firstClassSeats.get(x).getPassenger() != null) {
                    returnManifest += firstClassSeats.get(x).getSeatNumber() + ": ";
                    returnManifest += firstClassSeats.get(x).getPassenger().getPassengerName() + "\n";
                }
            }
        }
        return returnManifest;
    }

    /**
     * A getter method to get all available seats in the plane
     * 
     * @param classPref - the preferred class to view the availability for
     * @return returnStr - the availability for that specific class
     */
    public String getAvail(String classPref) {
        boolean emptySeat = false;
        String returnStr = "";
        int x = 0;
        if (classPref.equalsIgnoreCase("First")) {
            returnStr = "First:";
            for (int row = 1; row <= F_ROWS; row++) {
                emptySeat = false;
                // loop over the arrayList and find empty seats
                for (int column = 1; column <= F_COLUMNS; column++) {
                    if (firstClassSeats.get(x).getPassenger() == null) {
                        if (emptySeat == false) {
                            returnStr += ("\n" + row + ": ");
                            emptySeat = true;
                        } else {
                            returnStr += ",";
                        }
                        returnStr += firstClassSeats.get(x).getSeatNumber().charAt(1); // adds availability to the
                                                                                       // return string
                    }
                    x++;
                }
            }
        } else if (classPref.equalsIgnoreCase("Economy")) {
            returnStr += "Economy:";
            x = 0;
            // loop over the arrayList and find empty seats
            for (int row = E_START_SEAT; row < E_ROWS + E_START_SEAT; row++) {
                emptySeat = false;
                for (int column = 1; column <= E_COLUMNS; column++) {
                    if (economySeats.get(x).getPassenger() == null) {
                        if (emptySeat == false) {
                            returnStr += ("\n" + row + ": ");
                            emptySeat = true;
                        } else {
                            returnStr += ",";
                        }
                        returnStr += economySeats.get(x).getSeatNumber().charAt(2); // adds availability to the return
                                                                                    // string
                    }
                    x++;
                }
            }
            returnStr += "\n";
        }
        return returnStr;
    }

    /**
     * Add a passenger from the user input
     * 
     * @param p        - the passenger to add
     * @param seatPref - the seat preference the passenger wants
     * @return a string that either returns the seat a passenger - is seated at or
     *         that no seats available
     */
    public String addPassenger(Passenger p, String seatPref) {
        int x;
        if (p.getClassPref().equalsIgnoreCase("Economy")) {
            if (economyRemainingSeats == 0) {
                return "Sorry, there are no seats left in this plane!"; // if no seats are left in given class
            }
            for (x = 0; x < E_SIZE; x++) {
                // loop and set the passenger to a specific seat
                if (economySeats.get(x).getPassenger() == null && economySeats.get(x).getSeatType().equals(seatPref)) {
                    economyRemainingSeats--;
                    economySeats.get(x).setPassenger(p);
                    // returns the seat given to that passenger
                    return "Seat Given: " + economySeats.get(x).getSeatNumber();
                }
            }
            // no seats available
            return "Sorry no seat available for your given preference";
        } else if (p.getClassPref().equalsIgnoreCase("First")) {
            if (firstRemainingSeats == 0) {
                return "Sorry, there are no seats left in this plane!"; // if no seats are left in given class
            }
            for (x = 0; x < F_SIZE; x++) {
                // loop and set the passenger to a specific seat
                if (firstClassSeats.get(x).getPassenger() == null
                        && firstClassSeats.get(x).getSeatType().equalsIgnoreCase(seatPref)) {
                    firstRemainingSeats--;
                    firstClassSeats.get(x).setPassenger(p);
                    // returns the seat given to that passenger
                    return "Seat Given: " + firstClassSeats.get(x).getSeatNumber();
                }
            }
            // if no seat is available for Window, Aisle, or Center
            return "Sorry no seat available for your given preference.";
        }
        // If a wrong class selection was entered other than economy or first
        return "Sorry you entered a wrong class selection.";
    }

    /**
     * Adds a group from user input
     * 
     * @param groupToAdd - ArrayList of passengers to be seated adjacently
     * @return a String that returns the passenger's name, and where they are
     *         seated. If there is no room at all, the group is not seated
     */
    public String add_Group(ArrayList<Passenger> groupToAdd) {
        int a, b;
        int c = 0;
        int maxEmpty = 0;
        int adjacentEmpty = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        String whereGroupSeated = "";
        // If there are no seats are available to sit the group
        if (groupToAdd.get(0).getClassPref().equals("Economy") && economyRemainingSeats <= groupToAdd.size()) {
            return "Sorry, no seats available.";
        } else if (groupToAdd.get(0).getClassPref().equalsIgnoreCase("First")
                && firstRemainingSeats <= groupToAdd.size()) {
            return "Sorry, no seats available!";
        }
        // groups are seated starting from the front
        if (groupToAdd.get(0).getClassPref().equalsIgnoreCase(("Economy"))) {
            // Try to seat the whole group at once
            for (a = groupToAdd.size(); a > 0; a--) {
                for (b = 0; b < E_SIZE; b++) {
                    // Check each row to be able to seat passenger
                    if ((b % E_COLUMNS == 0) || (b == E_SIZE - 1)) {
                        if (adjacentEmpty > maxEmpty) {
                            maxEmpty = adjacentEmpty;
                            // seat from the front of the plane
                            for (c = maxEmpty; c >= 1; c--)
                                list.add(Math.abs(c - b));
                        }
                        if (maxEmpty >= a) {
                            while (!list.isEmpty() && !groupToAdd.isEmpty()) {
                                // adds the passengers to the list
                                economySeats.get(list.get(0)).setPassenger(groupToAdd.get(0));
                                whereGroupSeated += (groupToAdd.get(0).getPassengerName() + " is seated at: "
                                        + economySeats.get(list.get(0)).getSeatNumber() + "\n");
                                list.remove(0); // remove the first passenger
                                groupToAdd.remove(0); // remove the first passenger
                            }
                        }
                        maxEmpty = 0;
                        adjacentEmpty = 0; // reset
                        list.clear(); // reset
                    }
                    // check for adjacent empty seats
                    if (economySeats.get(b).getPassenger() != null) {
                        if (adjacentEmpty > maxEmpty) {
                            list.clear(); // clear the list
                            maxEmpty = adjacentEmpty;
                            for (c = 1; c <= maxEmpty; c++)
                                list.add(b - c);
                        }
                        adjacentEmpty = 0; // reset
                    } else {
                        adjacentEmpty++;
                    }
                }
            }
            // groups are seated starting from the front
        } else if (groupToAdd.get(0).getClassPref().equalsIgnoreCase("First")) {
            // Try to seat the whole group at once
            for (a = groupToAdd.size(); a > 0; a--) {
                for (b = 0; b < F_SIZE; b++) {
                    // Check each row to be able to seat passenger
                    if ((b == F_SIZE - 1) || ((b % F_COLUMNS) == 0)) {
                        if (adjacentEmpty > maxEmpty) {
                            maxEmpty = adjacentEmpty;
                            // seat from the front the of the row
                            for (c = maxEmpty; c >= 1; c--)
                                list.add(Math.abs(c - b));
                        }
                        if (maxEmpty >= a) {
                            while (!list.isEmpty() && !groupToAdd.isEmpty()) {
                                // adds the passengers to the list
                                firstClassSeats.get(list.get(0)).setPassenger(groupToAdd.get(0));
                                whereGroupSeated += (groupToAdd.get(0).getPassengerName() + " is seated at: "
                                        + firstClassSeats.get(list.get(0)).getSeatNumber() + "\n");
                                groupToAdd.remove(0); // remove the first passenger
                                list.remove(0); // remove the first passenger
                            }
                        }
                        list.clear(); // empty the list
                        maxEmpty = 0;
                        adjacentEmpty = 0;
                    }
                    // check for adjacent seats
                    if (firstClassSeats.get(b).getPassenger() != null) {
                        if (adjacentEmpty > maxEmpty) {
                            list.clear();
                            maxEmpty = adjacentEmpty;
                            for (c = 1; c <= maxEmpty; c++) {
                                list.add(b - c);
                            }
                        }
                        adjacentEmpty = 0; // no adjacent seats
                    } else {
                        adjacentEmpty++; // there are adjacent seats
                    }
                }
            }
        }
        return whereGroupSeated; // where the group is seated
    }

    /**
     * A method to remove a single passenger from the plane
     * 
     * @param name - the passenger's name
     * @return a String that returns either that the reservation is cancelled or
     *         that the reservation could not be cancelled
     */
    public String cancelReservation(String name) {
        boolean isReserved = false;
        String returnStr = "";
        // Check if the passenger is in first
        for (int x = 0; x < F_SIZE; x++) {
            if (firstClassSeats.get(x).getPassenger() != null) {
                if (name.equalsIgnoreCase(firstClassSeats.get(x).getPassenger().getPassengerName())) {
                    firstRemainingSeats++;
                    firstClassSeats.get(x).setPassenger(null);
                    isReserved = true;
                }
            }
        }
        // Check if the passenger is in economy
        for (int x = 0; x < E_SIZE; x++) {
            if (economySeats.get(x).getPassenger() != null) {
                if (name.equalsIgnoreCase(economySeats.get(x).getPassenger().getPassengerName())) {
                    economyRemainingSeats++;
                    economySeats.get(x).setPassenger(null);
                    isReserved = true;
                }
            }
        }
        // Returns the String if reservation is cancelled or not
        if (isReserved) {
            returnStr = "Your reservation has been cancelled.";
        } else {
            returnStr = "Sorry, reservation not found";
        }
        return returnStr;
    }

    /**
     * A method to cancel the group's reservation based on their name
     * 
     * @param groupName - the group's name
     * @return a String that returns either that the reservation is cancelled or
     *         that the reservation could not be cancelled
     */
    public String cancelGroup(String groupName) {
        String returnStr = "";
        boolean isReserved = false;
        // Check if the group is in economy and cancel
        for (int i = 0; i < E_SIZE; i++) {
            if (economySeats.get(i).getPassenger() != null) {
                if (groupName.equalsIgnoreCase(economySeats.get(i).getPassenger().getGroup())) {
                    economySeats.get(i).setPassenger(null);
                    economyRemainingSeats++;
                    isReserved = true;
                }
            }
        }
        // Check if the group is in first and cancel
        for (int i = 0; i < F_SIZE; i++) {
            if (firstClassSeats.get(i).getPassenger() != null) {
                if (groupName.equalsIgnoreCase(firstClassSeats.get(i).getPassenger().getGroup())) {
                    firstClassSeats.get(i).setPassenger(null);
                    firstRemainingSeats++;
                    isReserved = true;
                }
            }
        }
        // Returns the String if reservation is cancelled or not
        if (isReserved) {
            returnStr = "Your reservation has been cancelled.";
        } else {
            returnStr = "Sorry, reservation not found";
        }
        return returnStr;
    }

    /**
     * A method to get all the data saved in the database - to be outputted to the
     * file
     * 
     * @return a String with all the flight information that is in the database
     */
    public String getFlightInformation() {
        String returnStr = "";
        // loop over all first class to get the information
        for (int x = 0; x < F_SIZE; x++) {
            if (firstClassSeats.get(x).getPassenger() != null) {
                returnStr += (firstClassSeats.get(x).getSeatNumber() + ", ");
                if (firstClassSeats.get(x).getPassenger().getGroup() == null) {
                    returnStr += ("I");
                } else {
                    returnStr += ("G, ");
                    returnStr += (firstClassSeats.get(x).getPassenger().getGroup());
                }
                returnStr += (", " + firstClassSeats.get(x).getPassenger().getPassengerName());
                returnStr += "\n";
            }
        }
        // loop over all the economy class to get the information
        for (int x = 0; x < E_SIZE; x++) {
            if (economySeats.get(x).getPassenger() != null) {
                returnStr += (economySeats.get(x).getSeatNumber() + ",");
                if (economySeats.get(x).getPassenger().getGroup() == null) {
                    returnStr += ("I");
                } else {
                    returnStr += ("G, ");
                    returnStr += (economySeats.get(x).getPassenger().getGroup());
                }
                returnStr += (", " + economySeats.get(x).getPassenger().getPassengerName());
                returnStr += "\n";
            }
        }
        return returnStr;
    }
}