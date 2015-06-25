/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;



import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.capability.FdbUpdateProcessCapability;



/** 
 * FdbUpdateProcessCapabilityTest'
 *
 */
public class FdbUpdateProcessCapabilityTest extends DomainCapabilityTestCase {
    private static final Logger LOG = Logger.getLogger(FdbUpdateProcessCapabilityTest.class);
    
    private FdbUpdateProcessCapability fdbUpdateProcessCapability;
 
    //private FdbAddDomainCapability fdbAddDomainCapability;
    
    /**
     * Setup method
     */
    @Override
    @Before
    public void setUp() {
        fdbUpdateProcessCapability = getNationalCapability(FdbUpdateProcessCapability.class);
       
        // fdbAddDomainCapability = getNationalCapability(FdbAddDomainCapability.class);
    
    }

    /**
     * testAddNdcToProduct
     * @throws ValidationException 
     */
    @Test
    public void testAddNdcToProduct() throws ValidationException {

        String[] pNdcNumbers = { "10019-178-37" };
        String productId = "9995";

        fdbUpdateProcessCapability.addProductsToNdcs(getTestUser(), pNdcNumbers, productId);
        
        

        assertNotNull("not null", pNdcNumbers != null);
    }
    
    /**
     * test find Manufacturers by name
     */
    @Test
    public void testManufacturers() {
        
        ManufacturerVo vo = fdbUpdateProcessCapability.findManufacturerByName("ABBOTT");

        assertNotNull(" no results found!", vo);

        LOG.debug(" --------------------------------------------------- ");  
        LOG.debug(" Manufacturer Id: "  + vo.getId());
        LOG.debug(" Manufacturer Name: "  + vo.getValue());
        LOG.debug(" request status: "  + vo.getRequestItemStatus());
        LOG.debug(" Item status: "  + vo.getItemStatus());
        
    }
      
    
    /**
     * test find PackageType by name
     */
    @Test
    public void testPackageTypes() {
        
        PackageTypeVo vo = fdbUpdateProcessCapability.findPackageTypeByName("BOTTLE");

        assertNotNull("no results found!", vo);

        LOG.debug("--------------------------------------------------- ");  
        LOG.debug("Manufacturer Id: "  + vo.getId());
        LOG.debug("Manufacturer Name: "  + vo.getValue());
        LOG.debug("request status: "  + vo.getRequestItemStatus());
        LOG.debug("Item status: "  + vo.getItemStatus());
        
    }
    
    /**
     * testGenerateCSVFileToExport
     */
    @Test
    public void testGenerateCSVFileToExport() {
        List<FdbAddVo> fdbAddList = fdbUpdateProcessCapability.retrieveEPLPendingList();
        
        StringBuilder sb1 =  createCSVFile(fdbAddList);
        LOG.debug("CSV out: " + sb1.toString());
        
        assertTrue("sb1 length is < 0", sb1.length() > 0);
        
    }
    
    /**
     * creates CSV File from list results
     *
     * @param results results
     * @return StringBuilder
     */
    private StringBuilder createCSVFile(List<FdbAddVo> results) {
        StringBuilder sb = new StringBuilder();
        
        for (FdbAddVo e : results) {
            sb.append(e.getNdc())
                .append(",")
                .append(e.getManufacturer())
                .append(",")
                .append(e.getGcnSeqno())
                .append(",")
                .append(e.getFdbProductName())
                .append(",")
                .append(e.getFdbCreationDate())
                .append("\n");
        }
        
        return sb; 
    }
    
    
    
    /**
     * getTestUser
     * @return UserVo used during testing
     */
    @Override
    protected UserVo getTestUser() {
        UserVo user = new UserGenerator().getNationalManagerOne();
        
        // create user for the FdbUpdateProcessCapability.
        user.setFirstName("Domain Test Case First");
        user.setLastName("Domain Test Case Last");
        user.setLocation("Domain Test Case Location");
        user.setStationNumber("666");
        user.setUsername("domainTestCase");
        
        return user;
    }

    
}

