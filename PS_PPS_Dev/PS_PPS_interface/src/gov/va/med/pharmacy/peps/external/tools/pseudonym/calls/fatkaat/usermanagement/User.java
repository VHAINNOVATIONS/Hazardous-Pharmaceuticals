/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.fatkaat.usermanagement;


import java.util.Locale;


/**
 * Class User stores user attributes
 */
public class User {

    private String userName;
    private String password;
    private String duz;
    private String vpid;
    private String division;
    private String firstName;
    private String lastName;

    /**
     * Class constructor
     */
    User() {

    }

    /**
     * Returns the user name
     * @return returns user name as string
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Sets the user name
     * @param userNameIn incoming userName.
     */
    public void setUserName(String userNameIn) {
        this.userName = userNameIn;
    }
    
    /**
     * Sets the Password
     * @param passwordIn incoming userName.
     */
    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }


    /**
     * Checks the password 
     * @param passwordIn the string to check the user's password against
     * @return returns boolean true if input parameter matches user password
     */
    public boolean checkPassword(String passwordIn) {
        return this.password.equals(passwordIn);
    }

    /**
     * Returns user's duz
     * @return returns duz 
     */
    public String getDuz() {
        return duz;
    }

    /**
     * Returns user's VPID
     * @return returns VPID 
     */
    public String getVpid() {
        return vpid;
    }

    /**
     * Returns user's division
     * @return returns division 
     */
    public String getDivision() {
        return division;
    }

    /**
     * Returns user's first name
     * @return returns firstName
     */
    public String getFirstName() {
        return firstName;

    }

    /**
     * Return's user's last name
     * @return returns lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns user's full name
     * @return Returns a string with the user's full name.  
     * Format for full name is "FirstName LastName".
     */
    public String getFullName() {
        String fullName = firstName + " " + lastName;

        return fullName;
    }

    /**
     * Returns the user's last name in uppercase
     * @return returns lastName in uppercase 
     */
    public String getLastNameCaps() {
        return lastName.toUpperCase(Locale.US);

    }

    /**
     * Returns the user's first name in uppercase
     * @return returns firstName in uppercase
     */
    public String getFirstNameCaps() {
        return firstName.toUpperCase(Locale.US);
    }

    /**
     * Returns a reverse formatted name using all caps (ex  "LASTNAME,FIRSTNAME").
     * @return returns reverse formatted name
     */
    public String getReverseFormattedName() {

        String reverseFormattedName = getLastNameCaps() + "," + getFirstNameCaps();

        return reverseFormattedName;
    }

}
