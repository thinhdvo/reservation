import java.util.*;
import java.io.*;

/**
 * A class representing a system for making an Airplane Reservation. Takes in
 * user input and can also take in input from command line
 *
 */
public class ReservationSystem {
    public static void main(String[] args) throws FileNotFoundException {
        Airplane plane = new Airplane(); // make the plane
        if (args.length == 1) {
            readFile(plane, args[0]); // read the file from terminal
        }
        Scanner in = new Scanner(System.in); // scanner to read the input
        String read = "";
        while (true) { // main loop
            System.out.println("[P]assenger, [C]ancel Reservation, [G]roup, [M]anifest, [A]vailabilty, [Q]uit");
            System.out.print("Please enter a letter as shown above: ");
            read = in.nextLine().toUpperCase(); // ignore case (if entered wrong)
            // if no input is entered
            if (read.length() == 0) {
                System.out.println("Invalid input. Please try again.");
            } else if (read.charAt(0) == 'G') {
                addGroup(plane, in); // add group to the plane
            } else if (read.charAt(0) == 'P') {
                addPassenger(plane, in); // add a passenger to the plane
            } else if (read.charAt(0) == 'A') {
                // if the user wants to see the plane's availability
                read = in.nextLine();
                if (read.equalsIgnoreCase("Economy")) {
                    System.out.println(plane.getAvail("Economy"));
                } else if (read.equalsIgnoreCase("First")) {
                    System.out.println(plane.getAvail("First"));
                } else {
                    System.out.println("Invalid input.");
                }

            } else if (read.charAt(0) == 'C') {
                cancel(plane, in); // cancel a reservation (group or individual)
            } else if (read.charAt(0) == 'M') {
                getManifest(plane, in); // get the manifest of the plane
            } else if (read.charAt(0) == 'Q') {
                // save the file if user enters Q
                if (args.length == 1) {
                    saveInformation(plane, args[0]);
                } else {
                    System.out.println("Unable to save flight data.");
                }
                System.exit(0); // exit the always true while loop
            } else {
                // invalid input entered
                System.out.println("Please enter a valid input.");
            }
        }
    }

    /**
     * Method for taking in input through the command line
     * 
     * @param plane  - the plane in the file
     * @param string - name of the file entered through command line
     *
     */
    public static void readFile(Airplane plane, String string) {
        File myFile = new File(string);
        // if file already exists, add data from the file
        if (myFile.exists()) {
            String read;
            String theGroup;
            int seatNumber;
            String[] dataPerLine = new String[4];
            // try to read the file
            try {
                Scanner readTheFile = new Scanner(myFile);
                readTheFile.nextLine();
                while (readTheFile.hasNextLine()) {
                    read = readTheFile.nextLine();
                    dataPerLine = read.split(",");
                    seatNumber = Integer.parseInt(dataPerLine[0].substring(0, 1));
                    if (dataPerLine.length == plane.FILE_PARAMETER - 1) {
                        theGroup = null;

                    } else {
                        theGroup = dataPerLine[plane.FILE_PARAMETER];
                    }
                    plane.addInfo(new Passenger(dataPerLine[2], dataPerLine[0], theGroup), seatNumber);
                }
                // catch if the file is not found
            } catch (FileNotFoundException notFound) {
                System.out.println("File not found!");
            }
        }
    }

    /**
     * A method to interact with the input and output of adding a passenger
     * 
     * @param plane  - the plane in the file
     * @param reader - the scanner to read the input
     */
    public static void addPassenger(Airplane plane, Scanner reader) {
        String name;
        String classPref;
        String seatPref;
        String returnStr;
        // make sure there are no commas in the name
        do {
            System.out.println("Enter your name: ");
            name = reader.nextLine();
            if (name.contains(",")) {
                System.out.println("Please enter your name without a comma");
            }
        } while (name.contains(","));
        // Enter a valid class
        do {
            System.out.println("Please enter a desired class: ");
            classPref = reader.nextLine().toUpperCase();
            if (!classPref.equals("First") && !classPref.equals("Economy")) {
                System.out.println("Seat Preference: ");
            }
        } while ((classPref.charAt(0) != 'F') && (classPref.charAt(0) != 'E'));
        // Enter a valid seat preference
        do {
            if (classPref.substring(0, 1) == "E") {
                System.out.println("Enter a seat preference: [W]indow, [A]isle, or [C]enter");

            } else if (classPref.substring(0, 1) == "F") {
                System.out.println("Enter a seat preference: [W]indow, or [A]isle");
            }
            seatPref = reader.nextLine().toUpperCase();
            Passenger p = new Passenger(name, classPref, null);
            returnStr = plane.addPassenger(p, seatPref);
            // where the passenger is seated if he/she is seated at all.
            if (returnStr.equals("")) {
                System.out.println(
                        "Sorry, no seats available for your given preference. Please try a different preference");
            } else if (returnStr.equals("Sorry, there are no seats left in this plane!")) {
                System.out.println("Sorry, plane is full!");
            } else {
                System.out.println("Passenger seated at: " + returnStr + "\n");
            }
        } while (returnStr.equals(""));
    }

    /**
     * A method to interact with the input and output of adding a group to the plane
     * 
     * @param plane  - the plane in the file
     * @param reader - the scanner to read the file
     */
    public static void addGroup(Airplane plane, Scanner reader) {
        String nameOfGroup; // groupName
        String classPref; // service class preference
        String groupMembers; // names
        // make sure there is not a comma between first name and last name
        do {
            System.out.println("Enter a group name: ");
            nameOfGroup = reader.nextLine();
            if (nameOfGroup.contains(",")) {
                System.out.println("Invalid. Group names cannot contain commas");
            }
        } while (nameOfGroup.contains(","));
        System.out.println("Enter the names of people in the group separated by commas: ");
        groupMembers = reader.nextLine();
        // ask for the service class preference

        do {
            System.out.println("Enter a service class: [E]conomy or [F]irst: ");
            classPref = reader.nextLine();
            if ((classPref.charAt(0) != 'F') && (classPref.charAt(0) != 'E')) {
                System.out.println("Invalid. Please enter a valid service class.");
            }
        } while ((classPref.charAt(0) != 'F') && (classPref.charAt(0) != 'E'));
        // get the number of total group memebers to add
        int totalNumOfNames = 1; // always 1 group memeber to add
        for (int x = 0; x < groupMembers.length(); x++) {
            if (groupMembers.charAt(x) == ',') {
                totalNumOfNames++;
            }
        }
        // get all the names in the group
        String[] arrayOfNames = new String[totalNumOfNames];
        arrayOfNames = groupMembers.split(",");
        ArrayList<Passenger> groupToAdd = new ArrayList<Passenger>();
        // add the group to the arrayList
        for (int x = 0; x < totalNumOfNames; x++) {
            arrayOfNames[x] = arrayOfNames[x].trim();
            groupToAdd.add(new Passenger(arrayOfNames[x], classPref, nameOfGroup));
        }
        // add the group to the plane
        String returnStr = plane.add_Group(groupToAdd);
        if (returnStr != null) {
            System.out.println(returnStr);
        } else {
            System.out.println("The service class is full. \n");
        }
    }

    /**
     * A method to interact with the input and output of canceling a reservation
     * 
     * @param plane  - the plane in the file
     * @param reader - the scanner to read the input
     */
    public static void cancel(Airplane plane, Scanner reader) {
        String read;
        String isReserved = "";

        // What kind of cancellation the user wants
        System.out.println("Which cancellation would you like? [I]ndividual or [G]roup? ");
        read = reader.nextLine().toUpperCase();
        // if the user wants to cancel the group
        if (read.charAt(0) == 'G') {
            System.out.println("Enter the name of the group: ");
            read = reader.nextLine();
            isReserved = plane.cancelGroup(read);
            // if the user wants to cancel an individual reservation
        } else if (read.charAt(0) == 'I') {
            System.out.println("Enter your name: ");
            read = reader.nextLine();
            isReserved = plane.cancelReservation(read);
        }
        // check if the cancellation actually went through
        if (isReserved.equals("Your reservation has been cancelled.")) {
            System.out.println("Reservation cancelled for: " + read + ".\n");
        } else {
            System.out.println("Sorry. Cancelling failed.");
        }
    }

    /**
     * A method to interact with the input and output of getting the manifest
     * 
     * @param plane  - the plane in the file
     * @param reader - the scanner to read the input
     */
    public static void getManifest(Airplane plane, Scanner reader) {
        String classPref;
        // ask which class to view the manifest for
        System.out.println("Which class' manifest would you like? ");
        classPref = reader.nextLine();
        // print manifest for said class
        System.out.println(plane.getPlaneManifest(classPref));
    }

    /**
     * Saves all the passengers information in the file provided through command
     * line
     * 
     * @param plane - the plane in the file
     * @param file  - the file provided through the command line
     */
    public static void saveInformation(Airplane plane, String file) {
        // Use the print writer to save information to the file
        File output = new File(file);
        // try to save information to the flight
        try {
            PrintWriter saver = new PrintWriter(new FileWriter(file));

            String flightInformation = plane.getFlightInformation();
            saver.print(flightInformation);
            saver.close();
        }
        // catch if there is an exception (should never happen)
        catch (IOException e) {
            System.out.println("Sorry, file not found!");
        }
        // Print where the information is saved
        System.out.println("\n Flight data saved to: " + file);
    }
}
