
/*

*/
import java.util.*;
import java.io.*;

public class ReservationSystem {
    public static void main(String[] args) throws FileNotFoundException {
        FlightSystem plane = new FlightSystem();
        if (args.length == 1) {
            readFile(plane, args[0]);
        }
        Scanner in = new Scanner(System.in);
        String read = "";
        while (true) {
            System.out.println("[P]assenger, [C]ancel Reservation, [G]roup, [M]anifest, [A]vailabilty, [Q]uit");
            System.out.print("Please enter a letter as shown above: ");
            read = in.nextLine().toUpperCase();
            if (read.length() == 0) {
                System.out.println("Invalid input. Please try again.");
            } else if (read.charAt(0) == 'G') {
                addGroup(plane, in);
            } else if (read.charAt(0) == 'P') {
                addPassenger(plane, in);
            } else if (read.charAt(0) == 'A') {
                System.out.println("Enter class [economy/first]: ");
                read = in.nextLine();
                if (read.equalsIgnoreCase("Economy")) {
                    System.out.println(plane.getAvail("Economy"));
                } else if (read.equalsIgnoreCase("First")) {
                    System.out.println(plane.getAvail("First"));
                } else {
                    System.out.println("Invalid input.");
                }

            } else if (read.charAt(0) == 'C') {
                cancel(plane, in);
            } else if (read.charAt(0) == 'M') {
                getManifest(plane, in);
            } else if (read.charAt(0) == 'Q') {
                if (args.length == 1) {
                    saveInformation(plane, args[0]);
                } else {
                    System.out.println("Unable to save flight data.");
                }
                System.exit(0);
            } else {
                System.out.println("Please enter a valid input.");
            }
        }
    }

    public static void readFile(FlightSystem plane, String string) {
        File myFile = new File(string);
        if (myFile.exists()) {
            String read;
            String theGroup;
            int seatNumber;
            String[] dataPerLine = new String[4];
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
            } catch (FileNotFoundException notFound) {
                System.out.println("File not found!");
            }
        }
    }
    public static void addPassenger(FlightSystem plane, Scanner reader) {
        String name;
        String classPref;
        String seatPref;
        String returnStr;
        do {
            System.out.println("Enter your name: ");
            name = reader.nextLine();
            if (name.contains(",")) {
                System.out.println("Please enter your name without a comma");
            }
        } while (name.contains(","));
        do {
            System.out.println("Please enter a desired class: ");
            classPref = reader.nextLine().toUpperCase();
            if (!classPref.equals("First") && !classPref.equals("Economy")) {
                System.out.println("Seat Preference: ");
            }
        } while ((classPref.charAt(0) != 'F') && (classPref.charAt(0) != 'E'));
        do {
            if (classPref.substring(0, 1) == "E") {
                System.out.println("Enter a seat preference: [W]indow, [A]isle, or [C]enter");

            } else if (classPref.substring(0, 1) == "F") {
                System.out.println("Enter a seat preference: [W]indow, or [A]isle");
            }
            seatPref = reader.nextLine().toUpperCase();
            Passenger p = new Passenger(name, classPref, null);
            returnStr = plane.addPassenger(p, seatPref);
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

    public static void addGroup(FlightSystem plane, Scanner reader) {
        String nameOfGroup;
        String classPref;
        String groupMembers;
        do {
            System.out.println("Enter a group name: ");
            nameOfGroup = reader.nextLine();
            if (nameOfGroup.contains(",")) {
                System.out.println("Invalid. Group names cannot contain commas");
            }
        } while (nameOfGroup.contains(","));
        System.out.println("Enter the names of people in the group separated by commas: ");
        groupMembers = reader.nextLine();
        do {
            System.out.println("Enter a service class: [E]conomy or [F]irst: ");
            classPref = reader.nextLine();
            if ((classPref.charAt(0) != 'F') && (classPref.charAt(0) != 'E')) {
                System.out.println("Invalid. Please enter a valid service class.");
            }
        } while ((classPref.charAt(0) != 'F') && (classPref.charAt(0) != 'E'));
        int totalNumOfNames = 1;
        for (int x = 0; x < groupMembers.length(); x++) {
            if (groupMembers.charAt(x) == ',') {
                totalNumOfNames++;
            }
        }
        String[] arrayOfNames = new String[totalNumOfNames];
        arrayOfNames = groupMembers.split(",");
        ArrayList<Passenger> groupToAdd = new ArrayList<Passenger>();
        for (int x = 0; x < totalNumOfNames; x++) {
            arrayOfNames[x] = arrayOfNames[x].trim();
            groupToAdd.add(new Passenger(arrayOfNames[x], classPref, nameOfGroup));
        }
        String returnStr = plane.add_Group(groupToAdd);
        if (returnStr != null) {
            System.out.println(returnStr);
        } else {
            System.out.println("The service class is full. \n");
        }
    }

    public static void cancel(FlightSystem plane, Scanner reader) {
        String read;
        String isReserved = "";
        System.out.println("Which cancellation would you like? [I]ndividual or [G]roup? ");
        read = reader.nextLine().toUpperCase();
        if (read.charAt(0) == 'G') {
            System.out.println("Enter the name of the group: ");
            read = reader.nextLine();
            isReserved = plane.cancelGroup(read);
        } else if (read.charAt(0) == 'I') {
            System.out.println("Enter your name: ");
            read = reader.nextLine();
            isReserved = plane.cancelReservation(read);
        }
        if (isReserved.equals("Your reservation has been cancelled.")) {
            System.out.println("Reservation cancelled for: " + read + ".\n");
        } else {
            System.out.println("Sorry. Cancelling failed.");
        }
    }
    public static void getManifest(FlightSystem plane, Scanner reader) {
        String classPref;
        System.out.println("Which class' manifest would you like? ");
        classPref = reader.nextLine();
        System.out.println(plane.getPlaneManifest(classPref));
    }

    public static void saveInformation(FlightSystem plane, String file) {
        File output = new File(file);
        try {
            PrintWriter saver = new PrintWriter(new FileWriter(file));

            String flightInformation = plane.getFlightInformation();
            saver.print(flightInformation);
            saver.close();
        }
        catch (IOException e) {
            System.out.println("Sorry, file not found!");
        }
        System.out.println("\n Flight data saved to: " + file);
    }
}
