/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Before;

import gov.va.med.pharmacy.peps.common.vo.DrugMonographVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbMonographPemDomainCapability;


/**
 * FdbGenericNameDomainCapabilityTest
 *
 */
public class FdbMonographPemDomainCapabilityTest extends DomainCapabilityTestCase {
    private static final Logger LOG = Logger.getLogger(FdbMonographPemDomainCapabilityTest.class);
    private static final int NUMBER_ROWS = 120;
    private FdbMonographPemDomainCapability fdbMonographPemDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        fdbMonographPemDomainCapability = getNationalCapability(FdbMonographPemDomainCapability.class);
    }

    /**
     * testDeleteAll
     */
    public void testDeleteAll() {
        try {
            fdbMonographPemDomainCapability.deleteAll();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * testCreate in FdbGcenseqnoPemDomainCapabilityTest
     */
    public void testCreate() {
        try {   
            for (Integer x = NUMBER_ROWS; x > 0; x--) {

                DrugMonographVo vo = new DrugMonographVo();
                vo.setMonographId(x.longValue());
                vo.setEnglishTitle("EnglishTitle" + String.valueOf(x));
                vo.setSpanishTitle("SpanishTitle" + String.valueOf(x));
                vo.setEnglishBrandName("EnglishBrandName" + String.valueOf(x));
                vo.setSpanishBrandName("SpanishBrandName" + String.valueOf(x));
                vo.setEnglishMissedDose("EnglishMissedDose" + String.valueOf(x));
                vo.setSpanishMissedDose("SpanishMissedDose" + String.valueOf(x));
                vo.setEnglishPhonetics("EnglishPhonetics" + String.valueOf(x));
                vo.setSpanishPhonetics("SpanishPhonetics" + String.valueOf(x));
                vo.setEnglishHowToTake("EnglishHowToTake" + String.valueOf(x));
                vo.setSpanishHowToTake("SpanishHowToTake" + String.valueOf(x));
                vo.setEnglishDrugInteractions("EnglishDrugInteractions" + String.valueOf(x));
                vo.setSpanishDrugInteractions("SpanishDrugInteractions" + String.valueOf(x));
                vo.setEnglishMedicalAlerts("EnglishMedicalAlerts" + String.valueOf(x));
                vo.setSpanishMedicalAlerts("SpanishMedicalAlerts" + String.valueOf(x));
                vo.setEnglishNotes("EnglishNotes" + String.valueOf(x));
                vo.setSpanishNotes("SpanishNotes" + String.valueOf(x));
                vo.setEnglishOverdose("EnglishOverdose" + String.valueOf(x));
                vo.setSpanishOverdose("SpanishOverdose" + String.valueOf(x));
                vo.setEnglishPrecautions("EnglishPrecautions" + String.valueOf(x));
                vo.setSpanishPrecautions("SpanishPrecautions" + String.valueOf(x));
                vo.setEnglishStorage("EnglishStorage" + String.valueOf(x));
                vo.setSpanishStorage("SpanishStorage" + String.valueOf(x));
                vo.setEnglishSideEffects("EnglishSideEffects" + String.valueOf(x));
                vo.setSpanishSideEffects("SpanishSideEffects" + String.valueOf(x));
                vo.setEnglishUses("EnglishUses" + String.valueOf(x));
                vo.setSpanishUses("SpanishUses" + String.valueOf(x));
                vo.setEnglishWarnings("EnglishWarnings" + String.valueOf(x));
                vo.setSpanishWarnings("SpanishWarnings" + String.valueOf(x));
                vo.setEnglishDisclaimer("EnglishDisclaimer" + String.valueOf(x));
                vo.setSpanishDisclaimer("SpanishDisclaimer" + String.valueOf(x));
                vo.setCreatedBy(getTestUser().getUsername());
                vo.setCreatedDate(new Date());
                fdbMonographPemDomainCapability.create(vo, getTestUser());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * testRetreiveAll in FdbGcenseqnoPemDomainCapabilityTest
     */
    public void testRetreiveAll() {
        
        try {   
            List<DrugMonographVo> vos = fdbMonographPemDomainCapability.retrieveAll();
            LOG.debug("Size of all values is " + vos.size());
        
            for (int x = 0; x < vos.size(); x++) {
                DrugMonographVo vo = vos.get(x);
                LOG.debug("VO is " + vo.getMonographId() + ":" + vo.getEnglishTitle());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * testRetreive in FdbGcenseqnoPemDomainCapabilityTest
     */
    public void testRetreiveOne() {
        
        try {   
            List<DrugMonographVo> vos = fdbMonographPemDomainCapability.retrieveAll();
            LOG.debug("Size of all values is " + vos.size());
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(vos.size());
            DrugMonographVo vo = vos.get(randomInt);
            LOG.debug("MonographId is " + vo.getMonographId());
            LOG.debug("EnglishTitle is " + vo.getEnglishTitle());
            LOG.debug("SpanishTitle is " + vo.getSpanishTitle());
            LOG.debug("EnglishBrandName is " + vo.getEnglishBrandName());
            LOG.debug("SpanishBrandName is " + vo.getSpanishBrandName());
            LOG.debug("EnglishMissedDose is " + vo.getEnglishMissedDose());
            LOG.debug("SpanishMissedDose is " + vo.getSpanishMissedDose());
            LOG.debug("EnglishPhonetics is " + vo.getEnglishPhonetics());
            LOG.debug("SpanishPhonetics is " + vo.getSpanishPhonetics());
            LOG.debug("EnglishHowToTake is " + vo.getEnglishHowToTake());
            LOG.debug("SpanishHowToTake is " + vo.getSpanishHowToTake());
            LOG.debug("EnglishDrugInteractions is " + vo.getEnglishDrugInteractions());
            LOG.debug("SpanishDrugInteractions is " + vo.getSpanishDrugInteractions());
            LOG.debug("EnglishMedicalAlerts is " + vo.getEnglishMedicalAlerts());
            LOG.debug("SpanishMedicalAlerts is " + vo.getSpanishMedicalAlerts());
            LOG.debug("EnglishNotes is " + vo.getEnglishNotes());
            LOG.debug("SpanishNotes is " + vo.getSpanishNotes());
            LOG.debug("EnglishOverdose is " + vo.getEnglishOverdose());
            LOG.debug("SpanishOverdose is " + vo.getSpanishOverdose());
            LOG.debug("EnglishPrecautions is " + vo.getEnglishPrecautions());
            LOG.debug("SpanishPrecautions is " + vo.getSpanishPrecautions());
            LOG.debug("EnglishStorage is " + vo.getEnglishStorage());
            LOG.debug("SpanishStorage is " + vo.getSpanishStorage());
            LOG.debug("EnglishSideEffects( is " + vo.getEnglishSideEffects());
            LOG.debug("SpanishSideEffects( is " + vo.getSpanishSideEffects());
            LOG.debug("EnglishEnglishUses is " + vo.getEnglishUses());
            LOG.debug("SpanishEnglishUses is " + vo.getSpanishUses());
            LOG.debug("EnglishWarnings is " + vo.getEnglishWarnings());
            LOG.debug("SpanishWarnings is " + vo.getSpanishWarnings());
            LOG.debug("EnglishDisclaimer is " + vo.getEnglishDisclaimer());
            LOG.debug("SpanishDisclaimer is " + vo.getSpanishDisclaimer());
            
            assertNotNull("Monograph Id should not be null ", vo.getMonographId());
        } catch (Exception e) {
            fail(e.toString());
        }
    }
    
    /**
     * deleteOne
     */
    public void testDeleteOne() {
        
        try {   
            List<DrugMonographVo> vos = fdbMonographPemDomainCapability.retrieveAll();
            LOG.debug("Size of all values is " + vos.size());
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(vos.size());
            DrugMonographVo vo = vos.get(randomInt);
            
            fdbMonographPemDomainCapability.delete(vo.getMonographId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    
}
