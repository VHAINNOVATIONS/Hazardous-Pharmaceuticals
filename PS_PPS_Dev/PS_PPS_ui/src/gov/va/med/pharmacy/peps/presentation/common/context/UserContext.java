/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.context;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Data concerning the context in which a user is running the PEPS system.
 */
public class UserContext implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserVo user;
    private Map<String, Object> session = new HashMap<String, Object>();
    private String previousActionName;
    private Map previousActionParameters;

    /**
     * This method will return the in-context user.
     * @return user property
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * setUser
     * @param user user property
     */
    public void setUser(UserVo user) {
        this.user = user;
    }

    /**
     * Current user "session" to store objects from one action invocation to another.
     * 
     * @return session property
     */
    public Map<String, Object> getSession() {
        return session;
    }

    /**
     * Current user "session" to store objects from one action invocation to another.
     * 
     * @param session session property
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * Set a user "session" attribute.
     * 
     * @param key String
     * @param value Object
     */
    public void setSessionAttribute(String key, Object value) {
        session.put(key, value);
    }

    /**
     * Get a user "session" attribute.
     * 
     * If the attribute will no longer be used after calling this method, it is preferable to call
     * {@link #removeSessionAttribute(String)} so that the attribute no longer takes up space in the HttpSession.
     * 
     * @param key String
     * @return Object value
     * 
     * @see #removeSessionAttribute(String)
     */
    public Object getSessionAttribute(String key) {
        return session.get(key);
    }

    /**
     * Remove a user "session" attribute.
     * 
     * @param key String
     * @return Object value
     */
    public Object removeSessionAttribute(String key) {
        return session.remove(key);
    }

    /**
     * The String name of the Action that was running before the {@link UserContextInterceptor} found that a login was
     * required. This allows the action to be executed once the login process is complete.
     * 
     * @return previousActionName property
     */
    public String getPreviousActionName() {
        return previousActionName;
    }

    /**
     * The String name of the Action that was running before the {@link UserContextInterceptor} found that a login was
     * required. This allows the action to be executed once the login process is complete.
     * 
     * @param previousActionName previousActionName property
     */
    public void setPreviousActionName(String previousActionName) {
        this.previousActionName = previousActionName;
    }

    /**
     * The parameters of the Action request that was running before the {@link UserContextInterceptor} found that a login was
     * required. This allows the action to be executed once the login process is complete.
     * 
     * @return previousActionParameters property
     */
    public Map getPreviousActionParameters() {
        return previousActionParameters;
    }

    /**
     * The parameters of the Action request that was running before the {@link UserContextInterceptor} found that a login was
     * required. This allows the action to be executed once the login process is complete.
     * 
     * @param previousActionParameters previousActionParameters property
     */
    public void setPreviousActionParameters(Map previousActionParameters) {
        this.previousActionParameters = previousActionParameters;
    }
}
