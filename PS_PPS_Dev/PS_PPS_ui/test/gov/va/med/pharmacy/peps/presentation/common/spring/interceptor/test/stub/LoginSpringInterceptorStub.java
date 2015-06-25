/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.interceptor.test.stub;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;
import gov.va.med.pharmacy.peps.presentation.common.spring.interceptor.PepsSpringInterceptor;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * LoginInterceptor used during development when we're not using KAAJEE.
 */
public class LoginSpringInterceptorStub extends PepsSpringInterceptor {

    private static final long serialVersionUID = 1L;

    private static final Map<String, Role> ROLES = new HashMap<String, Role>();
    private static final String USER_LOGIN_ACTION = "/login.go";
    private static final String USER_LOGOUT_ACTION = "/logout.go";
    private static final String USER_PREFIX = "user.";

    private static final String USERNAME_PARAMETER = USER_PREFIX + FieldKey.USERNAME.toAttributeName();
    private static final String PASSWORD_PARAMETER = USER_PREFIX + FieldKey.PASSWORD.toAttributeName();
    private static final String DEFAULT_STATION_NUMBER = "500";
    private static final String USER_KEY = "user";

    private static final String COMBINED_ACCOUNT = "pnc1n1";

    private UserService userService;
    private EnvironmentUtility environmentUtility;

    static {
        ROLES.put("pna1n1", Role.PSS_PPSN_SUPERVISOR);
        ROLES.put("pna2n2", Role.PSS_PPSN_SUPERVISOR); //NATIONAL_ADMINISTRATIVE_MANAGER
        ROLES.put("pnm1n1", Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER
        ROLES.put("pnm2n2", Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER
        ROLES.put("pnv1n1", Role.PSS_PPSN_VIEWER); //NATIONAL_READ_ONLY
        ROLES.put("pnv2n2", Role.PSS_PPSN_VIEWER); //NATIONAL_READ_ONLY
        ROLES.put("pla1l1", Role.LOCAL_ADMINISTRATIVE_MANAGER);
        ROLES.put("pla2l2", Role.LOCAL_ADMINISTRATIVE_MANAGER);
        ROLES.put("plm1l1", Role.LOCAL_SERVICE_MANAGER);
        ROLES.put("plm2l2", Role.LOCAL_SERVICE_MANAGER);
        ROLES.put("plv1l1", Role.LOCAL_READ_ONLY);
        ROLES.put("plv2l2", Role.LOCAL_READ_ONLY);
        ROLES.put("psr1n1", Role.PSS_PPSN_SECOND_APPROVER); //SECOND_REVIEWER
        ROLES.put("psr2n2", Role.PSS_PPSN_SECOND_APPROVER); //SECOND_REVIEWER
        ROLES.put("pmm1n1", Role.PSS_PPSN_MIGRATOR);
        ROLES.put(COMBINED_ACCOUNT, Role.PSS_PPSN_MIGRATOR);
    }

    /**
     * Description
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @exception Exception e
     * @return boolean true/false
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, 
     *          javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserContext userContext = getUserContext(session);

        // If the user didn't provide a correct username/password, force the user to login again.
        if (!verifyLogin(userContext, request)) {

            // If the user wasn't already on the user login page, redirect them there            
            if (!USER_LOGIN_ACTION.equals(request.getServletPath())) {
                response.sendRedirect(request.getContextPath() + USER_LOGIN_ACTION);
            }

            return true;
        }

        //Set the user attribute on the request so the controller can set it on the PepsCommand
        setUserContext(request, session, userContext);

        // If this request was to logout, clear the user and invalidate the session.
//        if (USER_LOGOUT_ACTION.equals(request.getServletPath())) {
//
//            LOG.debug("Logout via LoginSpringInterceptorStub.");
//
////            try {
////                userService.updatePreferences(userContext.getUser());
////            } catch (ValueObjectValidationException e) {
////                LOG.error("Unable to save user preferences.", e);
////            }
//
//            userService.logout(userContext.getUser());
//            userContext.setUser(null);
//            request.setAttribute(USER_KEY, null);
//            session.invalidate();
//
//            response.sendRedirect(request.getContextPath() + USER_LOGIN_ACTION);
//
//            return false;
//        }

        if (userContext != null && userContext.getUser() != null) {
            session.setAttribute(USER_KEY, userContext.getUser());
        }

        // everything is fine, so don't return a result code
        return true;
    }

    /**
     * Verify the user is logged in. If the user is not logged in and the request has the username parameter, attempt a
     * login. If the login is unsuccessful, return false so that the user can be forwarded to the login page.
     * 
     * @param userContext UserContext for current user
     * @param request HttpServletRequest for current request
     * @return true if the user is logged in, otherwise false
     */
    private boolean verifyLogin(UserContext userContext, HttpServletRequest request) {
        boolean loggedIn = true;
        UserVo user = userContext.getUser();

        if (user == null) {
            String loginAttempt = request.getParameter(USERNAME_PARAMETER);

            if (loginAttempt != null && loginAttempt.trim().length() > 0) {
                try {
                    user = new UserVo();
                    user.setUsername(request.getParameter(USERNAME_PARAMETER));
                    user.setPassword(request.getParameter(PASSWORD_PARAMETER));
                    user.setStationNumber(DEFAULT_STATION_NUMBER);
                    user.setLocation(environmentUtility.getSiteNumber());

                    Role role = ROLES.get(user.getUsername().toLowerCase());

                    if (role == null) {
                        user.addRole(Role.LOCAL_ADMINISTRATIVE_MANAGER);
                        user.addRole(Role.LOCAL_SERVICE_MANAGER);
                        user.addRole(Role.PSS_PPSN_SUPERVISOR); //NATIONAL_ADMINISTRATIVE_MANAGER
                        user.setRolePnsFlag(true);
                        user.setRolePnsApvdFlag(true);
                        user.addRole(Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER
                        user.setRolePnmFlag(true);
                        user.setRolePnmApvdFlag(true);
                        user.addRole(Role.PSS_PPSN_SECOND_APPROVER); //SECOND_REVIEWER
                        user.setRolePsrFlag(true);
                        user.setRolePsrApvdFlag(true);
                        user.addRole(Role.PSS_PPSN_MIGRATOR);
                        user.setRoleMigFlag(true);
                        user.setRoleMigApvdFlag(true);
                    }

                    user = userService.login(user);

                    if (COMBINED_ACCOUNT.equals(user.getUsername().toLowerCase())) {
                        if (!user.getRoles().contains(Role.PSS_PPSN_MIGRATOR)) {
                            user.setRoleMigFlag(true);
                            user.setRoleMigApvdFlag(true);
                            user.addRole(Role.PSS_PPSN_MIGRATOR);
                        }

                        if (!user.getRoles().contains(Role.PSS_PPSN_SUPERVISOR)) {
                            user.setRolePnsFlag(true);
                            user.setRolePnsApvdFlag(true);
                            user.addRole(Role.PSS_PPSN_SUPERVISOR);
                        }
                    }

                    if (role != null) {
                        if (role.equals(Role.PSS_PPSN_MANAGER)) {
                            user.setRolePnmFlag(true);
                            user.setRolePnmApvdFlag(true);
                        }

                        if (role.equals(Role.PSS_PPSN_SECOND_APPROVER)) {
                            user.setRolePsrFlag(true);
                            user.setRolePsrApvdFlag(true);
                        }

                        if (role.equals(Role.PSS_PPSN_MIGRATOR)) {
                            user.setRoleMigFlag(true);
                            user.setRoleMigApvdFlag(true);
                        }

                        if (role.equals(Role.PSS_PPSN_SUPERVISOR)) {
                            user.setRolePnsFlag(true);
                            user.setRolePnsApvdFlag(true);
                        }

                        if (!(user.getRoles().contains(role))) {
                            user.addRole(role);
                        }
                    }

                    userContext.setUser(user);
                } catch (AuthenticationException e) {
                    List<String> errors = new ArrayList<String>();
                    errors.add(e.getLocalizedMessage(request.getLocale()));
                    request.setAttribute("errors", errors);
                    loggedIn = false;
                }
            } else { // user not authenticated and no login info available, send user to login page
                loggedIn = false;
            }
        } else {
            user = setNonNullUser(user);
        }

        return loggedIn;
    }
    
    /**
     * sets the nonNull user values.
     * @param userIn the User
     * @return the user with the role information set.
     */
    private UserVo setNonNullUser(UserVo userIn) {
        UserVo myUser = userIn;
        
        if (myUser.getRolePnmApvdFlag()) {
            if (!(myUser.getRoles().contains(Role.PSS_PPSN_MANAGER))) {
                myUser.addRole(Role.PSS_PPSN_MANAGER);
            }
        }

        if (myUser.getRolePsrApvdFlag()) {
            if (!(myUser.getRoles().contains(Role.PSS_PPSN_SECOND_APPROVER))) {
                myUser.addRole(Role.PSS_PPSN_SECOND_APPROVER);
            }
        }

        if (myUser.getRoleMigApvdFlag()) {
            if (!(myUser.getRoles().contains(Role.PSS_PPSN_MIGRATOR))) {
                myUser.addRole(Role.PSS_PPSN_MIGRATOR);
            }
        }

        if (myUser.getRolePnsApvdFlag()) {
            if (!(myUser.getRoles().contains(Role.PSS_PPSN_SUPERVISOR))) {
                myUser.addRole(Role.PSS_PPSN_SUPERVISOR);
            }
        }

        if (COMBINED_ACCOUNT.equals(myUser.getUsername().toLowerCase())) {
            if (!myUser.getRoles().contains(Role.PSS_PPSN_MIGRATOR)) {
                myUser.addRole(Role.PSS_PPSN_MIGRATOR);
            }

            if (!myUser.getRoles().contains(Role.PSS_PPSN_SUPERVISOR)) {
                myUser.addRole(Role.PSS_PPSN_SUPERVISOR);
            }
        }
        
        return myUser;
    }

    /**
     * Setter injected by Spring.
     * 
     * @param userService userService property
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Setter injected by Spring.
     * 
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }
}
