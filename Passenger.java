
/**
 * A class representing a potential passenger for the airplane
 * 
 *
 */
public class Passenger {
    private String passengerName;
    private String classPref;
    private String groupMembers;

    /**
     * A constructor to make the passenger
     * 
     * @param name            - name of the passenger
     * @param classPreference - First class or economy
     * @param group           - Individual or group name
     */
    public Passenger(String name, String classPreference, String group) {
        this.passengerName = name;
        this.classPref = classPreference;
        this.groupMembers = group;
    }

    /**
     * Getter method for the passenger's name
     * 
     * @return passengerName - the name of the passenger
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Getter method to get the passenger's class preference
     * 
     * @return classPref - the class preference
     */
    public String getClassPref() {
        return classPref;
    }

    /**
     * Getter method for the group
     * 
     * @return groupMembers - the passenger's group (null if no group)
     */
    public String getGroup() {
        return groupMembers;
    }
}
