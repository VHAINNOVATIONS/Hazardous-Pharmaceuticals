/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.test;


import gov.va.med.pharmacy.peps.common.utility.test.generator.NotificationTypeGenerator;
import gov.va.med.pharmacy.peps.common.vo.NotificationTypeVo;


/**
 * Creates persisted entities
 */
public class NotificationTypeDomainGenerator extends DomainGenerator<NotificationTypeVo> {
    
    /**
     * Constructor
     * 
     * @param domainCapability associated domain capability (for VO)
     */
    public NotificationTypeDomainGenerator(Object domainCapability) {
        super(domainCapability);
    }

    /**
     * creates new transient instance
     * @return UserVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.test.DomainGenerator#generate()
     */
    @Override
    protected NotificationTypeVo generate() {
        return new NotificationTypeGenerator().pseudoRandom();
    }
}
