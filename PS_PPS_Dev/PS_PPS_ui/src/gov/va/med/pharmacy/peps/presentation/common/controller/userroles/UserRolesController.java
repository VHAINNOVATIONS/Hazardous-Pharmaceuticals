/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.userroles;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.PepsController;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * UserRolesController
 *
 */
@RequestMapping(value = "/userRoles.go")
@Controller
public class UserRolesController extends PepsController {

    private static final String USER_ROLES_BEAN = "userRolesBean";

    private static final String USERS = "users";

    @Autowired
    private UserService userService;

    /**
     * Returns a list of all the users
     *
     * @return a list of all the users
     * @throws AuthenticationException the exception
     */
    @ModelAttribute(USERS)
    public List<UserVo> getUsers() throws AuthenticationException {

        return (ArrayList<UserVo>) userService.retrieveAll();
    }

    /**
     * Returns an UpdateUserRolesBean 
     *
     * @param users the users
     * @return an UpdateUserRolesBean
     */
    @ModelAttribute(USER_ROLES_BEAN)
    public UpdateUserRolesBean getUpdateUserRolesBean(@ModelAttribute(USERS) List<UserVo> users) {

        Map<Long, Map<String, Boolean>> userRoles = new HashMap<Long, Map<String, Boolean>>();

        for (UserVo user : users) {
            Map<String, Boolean> roles = new HashMap<String, Boolean>();
            roles.put(Role.PSS_PPSN_MIGRATOR.toString(), user.getRoleMigApvdFlag());
            roles.put(Role.PSS_PPSN_SECOND_APPROVER.toString(), user.getRolePsrApvdFlag());
            roles.put(Role.PSS_PPSN_MANAGER.toString(), user.getRolePnmApvdFlag());
            roles.put(Role.PSS_PPSN_SUPERVISOR.toString(), user.getRolePnsApvdFlag());
            userRoles.put(user.getId(), roles);
        }

        UpdateUserRolesBean bean = new UpdateUserRolesBean();
        bean.setUserRoles(userRoles);

        return bean;
    }

    /**
     * A get to display user roles
     *
     * @param model the model
     * @return the user roles view
     * @throws AuthenticationException the exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public String displayUserRoles(Model model)
        throws AuthenticationException {

        pageTrail.clearTrail();
        pageTrail.addPage("userRoles", "User Roles", true);

        return "user.roles";
    }

    /**
     * Updates the user roles
     *
     * @param users the users
     * @param userRolesBean the user roles bean
     * @return redirect to display user roles
     * @throws AuthenticationException the exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public String updateUserRoles(@ModelAttribute(USERS) List<UserVo> users,
        @ModelAttribute(USER_ROLES_BEAN) UpdateUserRolesBean userRolesBean) throws AuthenticationException {

        for (UserVo user : users) {
            Long userId = user.getId();
            Map<String, Boolean> requestedRoles = userRolesBean.getUserRoles().get(userId);

            boolean changed = false;

            if (requestedRoles.containsKey(Role.PSS_PPSN_MIGRATOR.toString())
                && requestedRoles.get(Role.PSS_PPSN_MIGRATOR.toString()) != user.getRoleMigApvdFlag()) {
                changed = true;
                user.setRoleMigApvdFlag(requestedRoles.get(Role.PSS_PPSN_MIGRATOR.toString()));
            }

            if (requestedRoles.containsKey(Role.PSS_PPSN_SECOND_APPROVER.toString())
                && requestedRoles.get(Role.PSS_PPSN_SECOND_APPROVER.toString()) != user.getRolePsrApvdFlag()) {
                changed = true;
                user.setRolePsrApvdFlag(requestedRoles.get(Role.PSS_PPSN_SECOND_APPROVER.toString()));
            }

            if (requestedRoles.containsKey(Role.PSS_PPSN_MANAGER.toString())
                && requestedRoles.get(Role.PSS_PPSN_MANAGER.toString()) != user.getRolePnmApvdFlag()) {
                changed = true;
                user.setRolePnmApvdFlag(requestedRoles.get(Role.PSS_PPSN_MANAGER.toString()));
            }

            if (requestedRoles.containsKey(Role.PSS_PPSN_SUPERVISOR.toString())
                && requestedRoles.get(Role.PSS_PPSN_SUPERVISOR.toString()) != user.getRolePnsApvdFlag()) {
                changed = true;
                user.setRolePnsApvdFlag(requestedRoles.get(Role.PSS_PPSN_SUPERVISOR.toString()));
            }

            if (changed) {
                userService.update(user);
            }

        }

        return REDIRECT + "userRoles.go";
    }
}
