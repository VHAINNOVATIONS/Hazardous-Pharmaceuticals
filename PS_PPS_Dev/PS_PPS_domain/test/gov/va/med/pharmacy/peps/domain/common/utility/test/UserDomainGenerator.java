/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.test;


import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Creates persisted entities
 */
public class UserDomainGenerator extends DomainGenerator<UserVo> {
    
    /**
     * Constructor
     * 
     * @param domainCapability associated domain capability (for VO)
     */
    public UserDomainGenerator(Object domainCapability) {
        super(domainCapability);
    }

    /**
     * creates new transient instance
     * @return UserVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.test.DomainGenerator#generate()
     */
    @Override
    protected UserVo generate() {
        return new UserGenerator().pseudoRandom();
    }
}
