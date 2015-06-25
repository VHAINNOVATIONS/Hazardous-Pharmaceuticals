/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Generate a List of users
 */
public class UserGenerator extends VoGenerator<UserVo> {
    private static final String I999 = "999";
    private static final Long ID_PLM1L1 = 10000000192l;
    private static final Long ID_PLA1L1 = 10000000190l;
    private static final Long ID_PLM2L2 = 10000000193l;
    private static final Long ID_PLA2L2 = 10000000191l;
    private static final Long ID_PNM1N1 = 10000000188l;
    private static final Long ID_PNA1N1 = 10000000184l;
    private static final Long ID_PNM2N2 = 10000000189l;
    private static final Long ID_PNA2N2 = 10000000187l;

    /**
     * Generate user
     * 
     * @return List of generated UserVo
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    public List<UserVo> generate() {
        List<UserVo> users = new ArrayList<UserVo>();

        UserVo user = new UserVo();
        user.setFirstName("First Name");
        user.setLastName("Last Name");
        user.setLocation("Location");
        user.setPassword("password");

        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.LOCAL_SERVICE_MANAGER);

        roles.add(Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER
        roles.add(Role.LOCAL_ADMINISTRATIVE_MANAGER);

        roles.add(Role.PSS_PPSN_SUPERVISOR); //NATIONAL_ADMINISTRATIVE_MANAGER
        user.setRoles(roles);

        user.setUsername("username");
        users.add(user);
        users.add(pseudoRandom());
        users.add(pseudoRandom());

        return users;
    }

    /**
     * Create plm1l1
     * 
     * @return UserVo
     */
    public UserVo getLocalManagerOne() {
        UserVo user = new UserVo();
        user.setId(ID_PLM1L1);
        user.setFirstName("Test PLM1L1");
        user.setLastName("Local Last");
        user.setLocation("Local 1");
        user.setStationNumber("672");
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setUsername("plm1l1");
        user.addRole(Role.LOCAL_SERVICE_MANAGER);

        return user;
    }

    /**
     * Create pla1l1
     * 
     * @return UserVo
     */
    public UserVo getLocalAdministratorOne() {
        UserVo user = new UserVo();
        user.setId(ID_PLA1L1);
        user.setFirstName("Test PLA1L1");
        user.setLastName("Local LastName2");
        user.setLocation("Local-1");
        user.setStationNumber("671");
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setUsername("pla1l1");
        user.addRole(Role.LOCAL_ADMINISTRATIVE_MANAGER);

        return user;
    }

    /**
     * Create plm2l2
     * 
     * @return UserVo
     */
    public UserVo getLocalManagerTwo() {
        UserVo user = new UserVo();
        user.setId(ID_PLM2L2);
        user.setFirstName("Test PLM2L2");
        user.setLastName("Local LastName L2");
        user.setLocation("Local-2a");
        user.setStationNumber("682");
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setUsername("plm2l2");
        user.addRole(Role.LOCAL_SERVICE_MANAGER);

        return user;
    }

    /**
     * Create pla2l2
     * 
     * @return UserVo
     */
    public UserVo getLocalAdministratorTwo() {
        UserVo user = new UserVo();
        user.setId(ID_PLA2L2);
        user.setFirstName("Test PLA2L2");
        user.setLastName("Local");
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setLocation("Local-2");
        user.setStationNumber("681");
        user.setUsername("pla2l2");
        user.addRole(Role.LOCAL_ADMINISTRATIVE_MANAGER);

        return user;
    }

    /**
     * Create pnm1n1
     * 
     * @return UserVo
     */
    public UserVo getNationalManagerOne() {
        UserVo user = new UserVo();
        user.setId(ID_PNM1N1);
        user.setFirstName("Test PNM1N1");
        user.setLastName("National Last");
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setLocation("National Location");
        user.setStationNumber(I999);
        user.setUsername("pnm1n1");
        user.addRole(Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER

        return user;
    }

    /**
     * Create pna1n1
     * 
     * @return UserVo
     */
    public UserVo getNationalAdministratorOne() {
        UserVo user = new UserVo();
        user.setId(ID_PNA1N1);
        user.setFirstName("My Test PNA1N1");
        user.setLastName("Nationial");
        user.setLocation("National14");
        user.setStationNumber(I999);
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setUsername("pna1n1");
        user.addRole(Role.PSS_PPSN_SUPERVISOR); //NATIONAL_ADMINISTRATIVE_MANAGER

        return user;
    }

    /**
     * Create pnm2n2
     * 
     * @return UserVo
     */
    public UserVo getNationalManagerTwo() {
        UserVo user = new UserVo();
        user.setId(ID_PNM2N2);
        user.setFirstName("Test PNM2N2");
        user.setLastName("National15");
        user.setLocation("National16");
        user.setStationNumber(I999);
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setUsername("pnm2n2");
        user.addRole(Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER

        return user;
    }

    /**
     * Create pna2n2
     * 
     * @return UserVo
     */
    public UserVo getNationalAdministratorTwo() {
        UserVo user = new UserVo();
        user.setId(ID_PNA2N2);
        user.setFirstName("Test PNA1N1");
        user.setLastName("National17");
        user.setLocation("National18");
        user.setStationNumber(I999);
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));
        user.setUsername("pna2n2");
        user.addRole(Role.PSS_PPSN_SUPERVISOR); //NATIONAL_ADMINISTRATIVE_MANAGER

        return user;
    }

    /**
     * Generate a pseudo-random (not all fields are actually random) UserVo.
     * 
     * @return UserVo
     */
    public UserVo pseudoRandom() {

        UserVo user = new UserVo();

        user.setId(RandomGenerator.nextLong());
        user.setLastName("McTest");
        user.setFirstName("User");
        user.setLocation("UserLoc");
        user.setPassword("password123");
        user.setDuz(RandomGenerator.nextLong(PPSConstants.I10));


        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.LOCAL_SERVICE_MANAGER);

        roles.add(Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER
        roles.add(Role.LOCAL_ADMINISTRATIVE_MANAGER);

        roles.add(Role.PSS_PPSN_SUPERVISOR); //NATIONAL_ADMINISTRATIVE_MANAGER
        user.setRoles(roles);

        user.setUsername(UUID.randomUUID().toString());

        return user;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
