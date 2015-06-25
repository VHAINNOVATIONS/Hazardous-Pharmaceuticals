/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NotificationConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NotificationStatusConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.UserConverter;


/**
 * ItemAuditHistoryDomainCapabilityTest.
 */
public class ItemAuditHistoryDomainCapabilityTest extends DomainCapabilityTestCase {

//    private ItemAuditHistoryDomainCapability localItemAuditHistoryDomainCapability;
//    private NotificationDomainCapability localNotificationDomainCapability;
    private ItemAuditHistoryDomainCapability nationalItemAuditHistoryDomainCapability;

    private NotificationConverter notificationConverter;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
        this.nationalItemAuditHistoryDomainCapability = getNationalCapability(ItemAuditHistoryDomainCapability.class);

        this.notificationConverter = new NotificationConverter();
        NotificationStatusConverter notificationStatusConverter = new NotificationStatusConverter();
        notificationStatusConverter.setUserConverter(new UserConverter());
        notificationConverter.setNotificationStatusConverter(notificationStatusConverter);
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testNonExistingItemAuditHistoryNational() throws PharmacyException {

        boolean exists = nationalItemAuditHistoryDomainCapability.exists("98");

        assertFalse("Exists", exists);
    }

//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveItemAuditHistoryLocal() throws Exception {
//
//        Collection<ItemAuditHistoryVo> insertedVo = localItemAuditHistoryDomainCapability.retreiveByAuditItemId("9991",
//            EntityType.ORDERABLE_ITEM.name());
//
//        assertFalse("Returned item audit with id", insertedVo.isEmpty());
//    }
}
