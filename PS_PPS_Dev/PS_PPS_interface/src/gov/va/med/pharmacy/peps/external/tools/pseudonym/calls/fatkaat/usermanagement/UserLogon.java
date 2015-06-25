/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.fatkaat.usermanagement;


import java.util.HashMap;
import java.util.Map;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymRuntimeException;


/**
 * Handles User Authentication
 */
public class UserLogon extends DefaultHandler {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UserLogon.class);
    private static final String USER = "user";
    private static final String USER_NAME = "userName";
    private static final UserLogon INSTANCE = new UserLogon();
    private static final ThreadLocal THREAD_LOCAL_USER = new ThreadLocal();

    private final String passwordPath = System.getProperty("password.commands", getLogonFile());
    private Map<String, User> userKey = new HashMap<String, User>();

    
    /**
     * Default constructor
     */
    UserLogon() {
        loadUsers();
    }
    
    /**
     * Method that returns a static instance of the UserLogon class.
     * 
     * @return returns static instance of UserLogon
     */
    public static UserLogon instance() {
        return INSTANCE;
    }

   

    /**
     * 
     * @return the logon file
     */
    private String getLogonFile() {
        String prefix = "xml/";
        String postfix = ".xml";
        String filename = prefix + this.getClass().getName().replace('.', '/') + postfix;
        LOG.debug("filename=" + filename);

        return filename;
    }

    /**
     * Stores User object on a per-connection basis
     * 
     * @param authenticatedUser expects User object.
     */
    public void setAuthenticatedUser(User authenticatedUser) {
        THREAD_LOCAL_USER.set(authenticatedUser);

    }

    /**
     * Method used to determine if the authenticated user is set or not
     * 
     * @return boolean true if the user is set, false if the user is not set.
     */
    public boolean isAuthenticatedUserSet() {
        return THREAD_LOCAL_USER.get() != null;
    }

    /**
     * Retrieves User object on per-connection basis
     * 
     * @return returns the previously authenticated user
     * @throws NoUserSetException exception thrown if the connection does not have an authenticated user set yet
     */
    public User getAuthenticatedUser() throws NoUserSetException {
        if (THREAD_LOCAL_USER.get() == null) {
            throw new NoUserSetException("This connection has not set an authenticated user yet");
        }

        return (User) THREAD_LOCAL_USER.get();
    }

    /**
     * Loads user information from external xml file
     */
    private void loadUsers() {
        LOG.info("loading user info from " + passwordPath);
        userKey.clear();

        try {
            UserLogon saxHandler = this;
            SAXParser parser = new SAXParser();
            parser.setContentHandler(saxHandler);
            parser.setErrorHandler(saxHandler);
            parser.parse(new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(passwordPath)));
        } catch (Exception e) {
            throw new PseudonymRuntimeException("Unable to load user information from " + passwordPath, e);
        }
    }

    /**
     * Takes a string representing the username/password combination and determines if it is a valid login pair
     * 
     * @param decryptedString method parses name/password from input string where string format is username;password
     * @return true if the user and password are valid, otherwise false
     */
    public boolean isValid(String decryptedString) {
        String[] values = decryptedString.split(";");

        return userKey.containsKey(values[0]) && ((User) userKey.get(values[0])).checkPassword(values[1]);

    }

    /**
     * Verifies user name/password combination by searching through the users that were loaded into memory from the users
     * file.
     * 
     * @param decryptedString method parses name/password from input string where string format is username;password
     * @return User object if Valid name/password, Null otherwise
     * @throws PseudonymException thrown if the user passed in to check does not exist or has an invalid password
     */
    public User verifyLogon(String decryptedString) throws PseudonymException {

        String[] decryptedValues = decryptedString.split(";");
        LOG.debug("Verifying user: " + decryptedValues[0] + " has password: " + decryptedValues[1]);

        if (userKey.containsKey(decryptedValues[0])
            && ((User) userKey.get(decryptedValues[0])).checkPassword(decryptedValues[1])) {

            return (User) userKey.get(decryptedValues[0]);
        }

        throw new PseudonymException("user " + decryptedValues[0] + " does not exist");

    }

    /**
     * This method is called by the parser when it encounters the start of an element in the XML file being parsed. It
     * overrides a method in the superclass. It then creates and populates the user based on the attributes in the XML
     * element.
     * 
     * @param uri not used in this implementation
     * @param localName name of element that is parsed
     * @param rawName not used in this implementation
     * @param attributes list of attributes in element
     * 
     * @see org.xml.sax.ContentHandler#startElement (java.lang.String, java.lang.String, java.lang.String,
     *      org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String rawName, Attributes attributes) {

        if (USER.equals(localName)) {
            User curUser = new User();

            for (int i = 0; i < attributes.getLength(); i++) {
                try {
                    LOG.debug("Setting " + attributes.getQName(i) + " to " + attributes.getValue(i));
                    
                    if (attributes.getQName(i).equals(USER_NAME)) {
                        curUser.setUserName(attributes.getValue(i));
                    }
                    
                    if (attributes.getQName(i).equals("password")) {
                        curUser.setPassword(attributes.getValue(i));
                    }
                        
//                    Field field = curUser.getClass().getDeclaredField(attributes.getQName(i));
//                    field.set(curUser, attributes.getValue(i));
                } catch (SecurityException e) {
                    LOG.error(e);
                    
//                } catch (NoSuchFieldException e) {
//                    LOG.error("The field " + attributes.getQName(i) + " does not exist ", e);
                } catch (Exception e) {
                    LOG.error(e);
                }
            }

            userKey.put(attributes.getValue(attributes.getIndex(USER_NAME)), curUser);
        }
    }
}
