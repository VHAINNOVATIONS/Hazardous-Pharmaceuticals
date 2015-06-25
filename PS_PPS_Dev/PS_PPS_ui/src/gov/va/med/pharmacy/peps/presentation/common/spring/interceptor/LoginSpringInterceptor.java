/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.interceptor;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import gov.va.med.authentication.kernel.LoginUserInfoVO;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * LoginSpringInterceptor's brief summary
 * 
 * Details of LoginSpringInterceptor's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class LoginSpringInterceptor extends PepsSpringInterceptor {

    /** PEPS_LOCAL_MANAGER */
    public static final String PEPS_LOCAL_MANAGER = "PEPS LOCAL MANAGER";

    /** PEPS_LOCAL_ADMIN */
    public static final String PEPS_LOCAL_ADMIN = "PEPS LOCAL ADMIN";

    /** PEPS_LOCAL_VIEWER */
    public static final String PEPS_LOCAL_VIEWER = "PEPS LOCAL VIEWER";

    /** PSS_PPSN_MIGRATOR */
    public static final String PSS_PPSN_MIGRATOR = "PSS_PPSN_MIGRATOR";

    /** PSS_PPSN_MANAGER */
    public static final String PSS_PPSN_MANAGER = "PSS_PPSN_MANAGER";

    /** PSS_PPSN_SECOND_APPROVER */
    public static final String PSS_PPSN_SECOND_APPROVER = "PSS_PPSN_SECOND_APPROVER";

    /** PSS_PPSN_SUPERVISOR */
    public static final String PSS_PPSN_SUPERVISOR = "PSS_PPSN_SUPERVISOR";

    /** PSS_PPSN_VIEWER */
    public static final String PSS_PPSN_VIEWER = "PSS_PPSN_VIEWER";

    private static final long serialVersionUID = 1L;

//    private static final String USER_LOGOUT_ACTION = "/logout.go";
//
//    private static final String USER_LOGOUT_ACTION2 = "/logout";

    private static final Logger LOG = Logger.getLogger(LoginSpringInterceptor.class);

    private UserService userService;
    private EnvironmentUtility environmentUtility;

    /**
     * KAAJEE actually authenticates the user, this interceptor just maps the KAAJEE LoginUserInfoVO into our {@link UserVo}
     * and maps the KAAJEE keys/roles into our {@link Role}.
     * 
     * @param request current HttpServletRequest
     * @param response current HttpServletResponse
     * @param handler The handler
     * @return true if the user is logged in, else false
     * @throws ValidationException validationException
     * @throws IOException IOexception
     * 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(HttpServletRequest,
     *          HttpServletResponse, Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws ValidationException, IOException {

        boolean sendToIndexPage = false;
        
        HttpSession session = request.getSession();

        // Since we can't make it to this interceptor without KAAJEE having authenticating the user, its LoginUserVO
        // ought to always be in the session.
        LoginUserInfoVO kaajeeUser = (LoginUserInfoVO) session.getAttribute(LoginUserInfoVO.SESSION_KEY);

        UserContext userContext = getUserContext(session);

        // Convert KAAJEE LoginUserInfoVO into UserVo and put user into context.
        // If this is a logout action, the KAAJEE user may be null (since the session has been invalidated).
        // If this is a login action, the userContext's user property should be null, so we need to set it, also send the
        // user to their preferred home page.
        if (kaajeeUser != null && userContext.getUser() == null) {
            UserVo user = toUser(kaajeeUser, request);
            userContext.setUser(user);
            sendToIndexPage = true;
        }

        //Set the user attribute on the session so the controller can use it
        setUserContext(request, session, userContext);
        String user = "user";

        // User logout is now handled via UserController.
        
        
        // If this request was to logout, clear the user and invalidate the session.
//        if (USER_LOGOUT_ACTION.equals(request.getServletPath()) || USER_LOGOUT_ACTION2.equals(request.getServletPath())) {
//            
//            if (request == null) {
//                LOG.debug("request is null after preHandle.");
//            } else {
//                LOG.debug("request is NOT null after preHandle.");
//                Enumeration e = request.getAttributeNames();
//                
//                while (e.hasMoreElements()) {
//                    String name = (String) e.nextElement();
//                    LOG.debug("request[" + name + "]: " + request.getAttribute(name));
//                }
//                
//                e = request.getHeaderNames();
//                
//                while (e.hasMoreElements()) {
//                    String name = (String) e.nextElement();
//                    LOG.debug("requestHeader[" + name + "]: " + request.getHeader(name));
//                }
//            }
//            
//            if (session == null) {
//                LOG.info("Session is null after preHandle.");
//            } else {
//                LOG.error("Session is NOT null after preHandle.");
//                Enumeration e = session.getAttributeNames();
//                
//                while (e.hasMoreElements()) {
//                    String name = (String) e.nextElement();
//                    LOG.debug("session[" + name + "]: " + session.getAttribute(name));
//                }
//            }
//            
//
//
//            session.setAttribute(LoginUserInfoVO.SESSION_KEY, null);
//            
//
//            LOG.debug("Logout via LoginSpringInterceptor.");
//
////          try {
////              userService.updatePreferences(userContext.getUser());
////          } catch (ValueObjectValidationException e) {
////              LOG.error("Unable to save user preferences.", e);
////          }
//
//            userService.logout(userContext.getUser());
//            userContext.setUser(null);
//
//            session.setAttribute(user, null); /* new UserVo()*/
////            session.removeAttribute(LoginUserInfoVO.SESSION_KEY);
//            session.invalidate();
//
////            Date now = new Date();
////            String redirectURL = request.getContextPath() + "/home.go?time=" 
////                                 + URLEncoder.encode("" + now.getTime(), "UTF-8");
////            LOG.debug("redirecting to: " + redirectURL);
////            
////            
////            response.sendRedirect(redirectURL);
////
////            return false;
//        }

        if (userContext != null && userContext.getUser() != null) {
            session.setAttribute(user, userContext.getUser());
        }

        if (sendToIndexPage) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        
        // everything is fine, return true
        return true;
    }


    /**
     * Pulls user info out of KAAJEE session object and builds PEPS UserVo. Then, puts UserVO into context.
     * 
     * @param kaajeeUser KAAJEE session object
     * @param request HttpServletRequest for current request
     * @return UserVo user created from KAAJEE LoginUserInfoVO
     * 
     * @throws ValidationException if the user didn't exist in the database and the newly created {@link UserVo} is not valid
     */
    private UserVo toUser(LoginUserInfoVO kaajeeUser, HttpServletRequest request) throws ValidationException {

        Long duz = Long.parseLong(kaajeeUser.getUserDuz());
        LOG.debug("User DUZ is " + kaajeeUser.getUserDuz());
        boolean create = false;

        UserVo user = userService.retrieve(duz, kaajeeUser.getLoginStationNumber());

        if (user == null) {
            create = true;
            LOG.debug("User does not exist");
            user = new UserVo();
            user.setDuz(duz);
            user.setUsername(kaajeeUser.getUserName01());
        } else {
            LOG.debug("User Exists");
        }

        user.setDuz(duz);
        user.setFirstName(kaajeeUser.getUserFirstName());
        user.setLastName(kaajeeUser.getUserLastName());
        user.setLocation(kaajeeUser.getLoginStationNumber());
        user.setUsername(kaajeeUser.getUserNameDisplay());

        // Map the KAAJEE security keys into our PEPS Roles (not stored in the EPL database)
        if (request.isUserInRole(PSS_PPSN_VIEWER)) {
            LOG.error("request.isUserInRol " + PSS_PPSN_VIEWER + " is true. ");
            user.addRole(Role.PSS_PPSN_VIEWER);
        }

        if (request.isUserInRole(PSS_PPSN_MANAGER)) {
            user.setRolePnmFlag(true);
        } else {
            user.setRolePnmFlag(false);
        }

        if (request.isUserInRole(PSS_PPSN_SUPERVISOR)) {
            user.setRolePnsFlag(true);
        } else {
            user.setRolePnsFlag(false);
        }

        if (request.isUserInRole(PSS_PPSN_SECOND_APPROVER)) {
            user.setRolePsrFlag(true);
        } else {
            user.setRolePsrFlag(false);
        }

        if (request.isUserInRole(PSS_PPSN_MIGRATOR)) {
            user.setRoleMigFlag(true);
        } else {
            user.setRoleMigFlag(false);
        }

        if (create) {
            user = userService.create(user);
        } else {
            user = userService.update(user);
        }

        user.setStationNumber(kaajeeUser.getLoginStationNumber());

        // Map the KAAJEE security keys into our PEPS Roles (not stored in the EPL database)
        if (user.getRolePnmFlag() && user.getRolePnmApvdFlag()) {
            logRole(PSS_PPSN_MANAGER);
            user.addRole(Role.PSS_PPSN_MANAGER);
        }

        if (user.getRolePnsFlag() && user.getRolePnsApvdFlag()) {
            logRole(PSS_PPSN_SUPERVISOR);
            user.addRole(Role.PSS_PPSN_SUPERVISOR);
        }

        if (user.getRolePsrFlag() && user.getRolePsrApvdFlag()) {
            logRole(PSS_PPSN_SECOND_APPROVER);
            user.addRole(Role.PSS_PPSN_SECOND_APPROVER);
        }

        if (user.getRoleMigFlag() && user.getRoleMigApvdFlag()) {
            logRole(PSS_PPSN_MIGRATOR);
            user.addRole(Role.PSS_PPSN_MIGRATOR);
        }

        // if the user has no role, then they get the viewer role by default.
        if (user.getRoles().size() == 0) {
            user.addRole(Role.PSS_PPSN_VIEWER);
        }

        return user;
    }

    /**
     * logRole
     *
     * @param role String
     */
    private void logRole(String role) {
        LOG.debug("Getting Role " + role + " is true.");
    }

    /**
     * set userService 
     * @param userService userService property
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * get the environmentUtility
     * 
     * @return the environmentUtility
     */
    public EnvironmentUtility getEnvironmentUtility() {
        return environmentUtility;
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

}
