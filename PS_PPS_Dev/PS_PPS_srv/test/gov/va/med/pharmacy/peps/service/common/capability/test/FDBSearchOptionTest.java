/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;


import java.util.Collection;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionResultVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;

import junit.framework.TestCase;


/**
 * Test the base class of DefaultRulesCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class FDBSearchOptionTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(FDBSearchOptionTest.class);
    private DrugReferenceCapability drugReferenceCapability;


    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.info("---------- " + getName() + " ----------");

        this.drugReferenceCapability = SpringTestConfigUtility.getNationalSpringBean(DrugReferenceCapability.class);

    }

    /**
     * getTestUser
     * @return UserVo used during testing
     */
    protected UserVo getTestUser() {
        UserVo user = new UserGenerator().getNationalManagerOne();
        
        user.setFirstName("Domain Test Case First");
        user.setLastName("Domain Test Case Last");
        user.setLocation("Domain Test Case Location");
        user.setStationNumber("666");
        user.setUsername("domainTestCase");
        
        return user;
    }
    
  

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testNDCSearch() {

        String searchString = "52569013252"; //"63739023";
        FDBSearchOptionVo fdbSearchOptionVo = new FDBSearchOptionVo(FDBSearchOptionType.NDC_SEARCH, searchString);

        try {
            fdbSearchOptionVo = drugReferenceCapability.performFDBSearchOption(fdbSearchOptionVo, getTestUser());

            if (fdbSearchOptionVo == null) {
                fail("The fdbSearchOptionVo is null.");
            } else {
                Collection<FDBSearchOptionResultVo> fdbSearchOptionResults = fdbSearchOptionVo.getSearchOptionResults();

                if (fdbSearchOptionResults == null || fdbSearchOptionVo.getSearchOptionResults().size() < 1) {
                    fail("NO results returned for NDC: " + searchString);
                } else {
                    LOG.info(" The Result set for " + searchString + " has: " + fdbSearchOptionResults.size()
                                       + " elements. ");
                    int i = 0;

                    for (FDBSearchOptionResultVo result : fdbSearchOptionResults) {
                        i++;
                        LOG.info("Result " + i);
                        LOG.info("GCN Seq No:  " + result.getGCNSeqNo());
                        LOG.info("Generic Name:  " + result.getGenericName());
                        LOG.info("Label Name:  " + result.getLabelName());
                        LOG.info("Label Name25:  " + result.getLabelName25());
                        LOG.info("NDC:  " + result.getNDC());

                    }
                }

            }
        } catch (Exception e) {
            fail("Should have thrown a ValueObjectValidationException, not just a ValidationException!" + e.getMessage());
        }
    }

    /**
    * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
    */
    public void testFDBAdd() {

        String searchString = "52569013252"; //"63739023";

        try {
            FdbAddVo fdbAddVo = drugReferenceCapability.populateFdbAddVoFields(searchString);

            if (fdbAddVo == null) {
                LOG.debug("No results found for " + searchString);
            } else {
                LOG.debug("Information for ndc: " + searchString);
                LOG.info("GCN Seq No:  " + fdbAddVo.getGcnSeqno());
                LOG.info("Generic Name:  " + fdbAddVo.getFdbProductName());
                LOG.info("TradeName:  " + fdbAddVo.getTradeName());
            }
        } catch (Exception e) {
            fail("Should have thrown a ValueObjectValidationException, not just a ValidationException!" + e.getMessage());
        }
    }
    
    
    /**
     * Test the GCN Seq No search
     */
    public void testGCNSeqNoSearch() {

        String searchString = "16"; // 266
        FDBSearchOptionVo fdbSearchOptionVo = new FDBSearchOptionVo(FDBSearchOptionType.GCNSEQNO_SEARCH, searchString);

        try {
            fdbSearchOptionVo = drugReferenceCapability.performFDBSearchOption(fdbSearchOptionVo, getTestUser());

            if (fdbSearchOptionVo == null) {
                fail("the search result vo is null");
            } else {
                Collection<FDBSearchOptionResultVo> fdbSearchOptionResults = fdbSearchOptionVo.getSearchOptionResults();

                if (fdbSearchOptionResults == null || fdbSearchOptionResults.size() < 1) {
                    fail("No results returned for GCNSeqNo: " + searchString);
                } else {

                    for (FDBSearchOptionResultVo result : fdbSearchOptionResults) {
                        LOG.info("NDC:            " + result.getNDC());
                        LOG.info("GCNSeqNo        " + result.getGCNSeqNo());
                        LOG.info("________________________________________________________ ");

                    }
                }

            }
        } catch (Exception e) {
            fail("This method Should have thrown a ValueObjectValidationException, not just a ValidationException " 
                 + e.getMessage());
        }

    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testGenericSearch() {

        String searchString = "HYDROMO";
        FDBSearchOptionVo fdbSearchOptionVo = new FDBSearchOptionVo(FDBSearchOptionType.GENERIC_SEARCH, searchString);

        try {
            fdbSearchOptionVo = drugReferenceCapability.performFDBSearchOption(fdbSearchOptionVo, getTestUser());

            if (fdbSearchOptionVo == null) {
                fail("The enforceRules() method should have thrown a ValueObjectValidationException");
            } else {
                Collection<FDBSearchOptionResultVo> fdbSearchOptionResults = fdbSearchOptionVo.getSearchOptionResults();

                if (fdbSearchOptionResults == null || fdbSearchOptionResults.size() < 1) {
                    fail("NO results returned for " + searchString);
                } else {
                    LOG.info("Result  set for " + searchString + " has-- " + fdbSearchOptionResults.size()
                                       + "-elements.");

                    for (FDBSearchOptionResultVo result : fdbSearchOptionResults) {

                        LOG.info(" Additional Description: " + result.getAdditionalDescriptor());
                        LOG.info(" Brand Name: " + result.getBrandName());
                        LOG.info(" Color: " + result.getColor());
                        LOG.info(" DEA Code: " + result.getDEACode());
                        LOG.info(" Drug Class: " + result.getDrugClassification());
                        LOG.info(" Drug Form Code: " + result.getDrugFormCode());
                        LOG.info(" Dose Form Description: " + result.getDosageFormDescription());
                        LOG.info(" Drug Strenth Description: " + result.getDrugStrengthDescription());
                        LOG.info(" DuplicateTherapyClasses: " + result.getDuplicateTherapies());
                        LOG.info(" Flavor: " + result.getFlavor());
                        LOG.info(" GCN  Seq No: " + result.getGCNSeqNo());
                        LOG.info(" Generic Name: " + result.getGenericName());
                        LOG.info(" Label Name: " + result.getLabelName());
                        LOG.info(" Label Name25: " + result.getLabelName25());
                        LOG.info(" ManDistrib: " + result.getManufacturerDistributorName());
                        LOG.info(" NDC : " + result.getNDC());
                        LOG.info(" NDCFormatCode: " + result.getNDCFormatCode());
                        LOG.info(" Obsolete Date: " + result.getObsoleteDate());
                        LOG.info(" OTC/RX Indicator: " + result.getOTCIndicator());
                        LOG.info(" Package Description: " + result.getPackageDescription());
                        LOG.info(" Package Size: " + result.getPackageSize());
                        LOG.info(" Previous NDC: " + result.getPreviousNDC());
                        LOG.info(" Replacement NDC: " + result.getReplacementNDC());
                        LOG.info(" Shape: " + result.getShape());
                        LOG.info(" Top 200 Rank: " + result.getTop200Rank());
                        LOG.info(" Top 50 Gen Rank: " + result.getTop50GenRank());
                        LOG.info(" Unit Dose Indicator: " + result.getUnitDoseIndicator());
                        LOG.info(" ---------------------------------------------------");
                    }
                }

            }
        } catch (Exception e) {
            fail("Should have thrown the ValueObjectValidationException, not just a ValidationException!" + e.getMessage());
        }
    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testLabelNameSearch() {

        String searchString = "LANOXIN 0.25 MG/ML AMPUL";
        FDBSearchOptionVo fdbSearchOptionVo = new FDBSearchOptionVo(FDBSearchOptionType.LABEL_SEARCH, searchString);

        try {
            fdbSearchOptionVo = drugReferenceCapability.performFDBSearchOption(fdbSearchOptionVo, getTestUser());

            if (fdbSearchOptionVo == null) {
                fail("Search option returned was null.");
            } else {
                Collection<FDBSearchOptionResultVo> fdbSearchOptionResults = fdbSearchOptionVo.getSearchOptionResults();

                if (fdbSearchOptionResults == null || fdbSearchOptionResults.size() < 1) {
                    fail("No results returned for " + searchString);
                } else {
                    LOG.info("Result set for " + searchString + " has " + fdbSearchOptionResults.size()
                                       + " elements.");

                    for (FDBSearchOptionResultVo result : fdbSearchOptionResults) {

                        LOG.info("GCN Seq No: " + result.getGCNSeqNo());
                        LOG.info("NDC: " + result.getNDC());
                        LOG.info("________________________________________________________");

                    }
                }

            }
        } catch (Exception e) {
            fail(" Should have thrown a ValueObjectValidationException, not just a ValidationException");
        }
    }
    
    
    /**
     * testAllSearch
    */
    public void testAllSearch() {
        
        String searchString = "16";
        
        FDBSearchOptionVo vo = new FDBSearchOptionVo(FDBSearchOptionType.ALL, searchString);

        try {
            vo = drugReferenceCapability.
                performFDBSearchOption(vo, FDBSearchOptionType.ALL, getTestUser());
            
            assertNotNull("search results is null", vo.getSearchOptionResults());
            
            for (FDBSearchOptionResultVo result : vo.getSearchOptionResults()) {
                LOG.info("GCN  Seq No: " + result.getGCNSeqNo());
                LOG.info("Generic Name: " + result.getGenericName());
                LOG.info("Label Name: " + result.getLabelName());
                LOG.info("Label Name25: " + result.getLabelName25());
                LOG.info("NDC : " + result.getNDC());
            }
            
            
        } catch (Exception e) {
            fail("Failed Search 'ALL'  " + e.getMessage());
        }
        
    }

}
