/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemPmisLanguage;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemWarningLabelSource;
import gov.va.med.pharmacy.peps.domain.common.model.EplPharmacySystemDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PharmacySystemConverter;

import junit.framework.TestCase;


/**
 * Test the {@link PharmacySystemConverter}
 */
public class PharmacySystemConverterTest extends TestCase {

    private static final String ERROR_MSG = "These values should be equal.";
    private static final String VALUE = "Field value here".toUpperCase();
    private static final Long EPL_ID = 9999L;
    private static final String AND = "and";
    private static final Integer SITE_NUMBER = new Integer(67);
    private static final PharmacySystemWarningLabelSource WARNING_LABEL_SOURCE = PharmacySystemWarningLabelSource.PEPS;
    private static final String DAYS = "days";
    private static final String EXCEPT = "except";
    private static final String EIGHT = "eight";
    private static final String FIVE = "five";
    private static final String FOR = "for";
    private static final String FOUR = "four";
    private static final String HOURS = "hours";
    private static final String MINUTES = "minutes";
    private static final String MONTHS = "months";
    private static final String NINE = "nine";
    private static final String ONE = "one";
    private static final String ONE_FOURTH = "one_fourth";
    private static final String ONE_HALF = "one_half";
    private static final String ONE_THIRD = "one_third";

    private static final PharmacySystemPmisLanguage PHARMACY_SYSTEM_PMIS_LANGUAGE = PharmacySystemPmisLanguage.ENGLISH;
    private static final String PMIS_PRINTER = "pmis_printer";
    private static final String PMISSECTION_DELETE = "pmissection_delete";
    private static final String SECONDS = "seconds";
    private static final String SEVEN = "seven";
    private static final String SIX = "six";
    private static final String TEN = "ten";
    private static final String THEN = "then";
    private static final String THREE = "three";
    private static final String THREE_FOURTHS = "three_fourths";
    private static final String TWO = "two";
    private static final String TWO_THIRDS = "two_thirds";

    private static final String WEEKS = "weeks";
    private static final Date INACTIVATION_DATE = new Date(67);
    private static final ItemStatus ITEM_STATUS = ItemStatus.ACTIVE;
    private static final long REVISION_NUMBER = 3L;
    private PharmacySystemConverter pharmacySystemConverter = new PharmacySystemConverter();

    /**
     * PharmacySystemConverterTest
     */
    public PharmacySystemConverterTest() {
        super();
    }
    
    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplPharmacySystemDo createDo() {
        EplPharmacySystemDo dataDo = new EplPharmacySystemDo();

        dataDo.setSiteNumber(SITE_NUMBER);
        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus("ACTIVE");

        dataDo.setPsAnd(AND);
        dataDo.setPsCmopWarningLabelSource(WARNING_LABEL_SOURCE.toString());
        dataDo.setPsDays(DAYS);
        dataDo.setPsExcept(EXCEPT);
        dataDo.setPsEight(EIGHT);
        dataDo.setPsFive(FIVE);
        dataDo.setPsFor(FOR);
        dataDo.setPsFour(FOUR);
        dataDo.setPsHours(HOURS);
        dataDo.setPsMinutes(MINUTES);
        dataDo.setPsMonths(MONTHS);
        dataDo.setPsNine(NINE);
        dataDo.setPsOne(ONE);
        dataDo.setPsOneFourth(ONE_FOURTH);
        dataDo.setPsOneHalf(ONE_HALF);
        dataDo.setPsOneThird(ONE_THIRD);
        dataDo.setPsOpaiWarningLabelSource(WARNING_LABEL_SOURCE.toString());
        dataDo.setPsPmisLanguage(PHARMACY_SYSTEM_PMIS_LANGUAGE.toString());
        dataDo.setPsPmisPrinter(PMIS_PRINTER);
        dataDo.setPsPmisSectionDelete(PMISSECTION_DELETE);
        dataDo.setPsSeconds(SECONDS);
        dataDo.setPsSeven(SEVEN);

        dataDo.setPsSix(SIX);
        dataDo.setPsTen(TEN);
        dataDo.setPsThen(THEN);
        dataDo.setPsThree(THREE);
        dataDo.setPsThreeFourths(THREE_FOURTHS);
        dataDo.setPsTwo(TWO);
        dataDo.setPsTwoThirds(TWO_THIRDS);
        dataDo.setPsWarningLabelSource(WARNING_LABEL_SOURCE.toString());
        dataDo.setPsWeeks(WEEKS);

        dataDo.setPsSiteName(VALUE);

        dataDo.setSiteNumber(SITE_NUMBER);

        dataDo.setRevisionNumber(REVISION_NUMBER);

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToPharmacySystemVoHasAllAttributes() {
        EplPharmacySystemDo dataDo = createDo();
        PharmacySystemVo objectVo = pharmacySystemConverter.convert(dataDo);

        assertEquals(ERROR_MSG, INACTIVATION_DATE, objectVo.getInactivationDate());
        assertEquals(ERROR_MSG, ITEM_STATUS, objectVo.getItemStatus());
        assertEquals(ERROR_MSG, AND, objectVo.getPsAnd());
        assertEquals(ERROR_MSG, WARNING_LABEL_SOURCE, objectVo.getPsCmopWarningLabelSource());
        assertEquals(ERROR_MSG, DAYS, objectVo.getPsDays());
        assertEquals(ERROR_MSG, EXCEPT, objectVo.getPsExcept());
        assertEquals(ERROR_MSG, EIGHT, objectVo.getPsEight());
        assertEquals(ERROR_MSG, FIVE, objectVo.getPsFive());
        assertEquals(ERROR_MSG, FOR, objectVo.getPsFor());
        assertEquals(ERROR_MSG, FOUR, objectVo.getPsFour());
        assertEquals(ERROR_MSG, HOURS, objectVo.getPsHours());
        assertEquals(ERROR_MSG, MINUTES, objectVo.getPsMinutes());
        assertEquals(ERROR_MSG, MONTHS, objectVo.getPsMonths());
        assertEquals(ERROR_MSG, NINE, objectVo.getPsNine());
        assertEquals(ERROR_MSG, ONE, objectVo.getPsOne());
        assertEquals(ERROR_MSG, ONE_FOURTH, objectVo.getPsOneFourth());
        assertEquals(ERROR_MSG, ONE_HALF, objectVo.getPsOneHalf());
        assertEquals(ERROR_MSG, ONE_THIRD, objectVo.getPsOneThird());
        assertEquals(ERROR_MSG, WARNING_LABEL_SOURCE, objectVo.getPsOpaiWarningLabelSource());
        assertEquals(ERROR_MSG, PHARMACY_SYSTEM_PMIS_LANGUAGE, objectVo.getPsPmisLanguage());
        assertEquals(ERROR_MSG, PMIS_PRINTER, objectVo.getPsPmisPrinter());
        assertEquals(ERROR_MSG, PMISSECTION_DELETE, objectVo.getPsPmisSectionDelete());
        assertEquals(ERROR_MSG, SECONDS, objectVo.getPsSeconds());
        assertEquals(ERROR_MSG, SEVEN, objectVo.getPsSeven());

        assertEquals(ERROR_MSG, SIX, objectVo.getPsSix());
        assertEquals(ERROR_MSG, TEN, objectVo.getPsTen());
        assertEquals(ERROR_MSG, THEN, objectVo.getPsThen());
        assertEquals(ERROR_MSG, THREE, objectVo.getPsThree());
        assertEquals(ERROR_MSG, THREE_FOURTHS, objectVo.getPsThreeFourths());
        assertEquals(ERROR_MSG, TWO, objectVo.getPsTwo());
        assertEquals(ERROR_MSG, TWO_THIRDS, objectVo.getPsTwoThirds());
        assertEquals(ERROR_MSG, WARNING_LABEL_SOURCE, objectVo.getPsWarningLabelSource());
        assertEquals(ERROR_MSG, WEEKS, objectVo.getPsWeeks());

        assertEquals(ERROR_MSG, VALUE, objectVo.getValue());
        assertEquals(ERROR_MSG, SITE_NUMBER, objectVo.getPsSiteNumber());

        assertEquals(ERROR_MSG, REVISION_NUMBER, objectVo.getRevisionNumber());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private PharmacySystemVo createVo() {
        PharmacySystemVo objectVo = new PharmacySystemVo();

        objectVo.setPsSiteNumber(SITE_NUMBER);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setItemStatus(ITEM_STATUS);
        
//        objectVo.setRequestItemStatus(REQUEST_STATUS);
//        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
//        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);


        objectVo.setPsAnd(AND);
        objectVo.setPsCmopWarningLabelSource(WARNING_LABEL_SOURCE);
        objectVo.setPsDays(DAYS);
        objectVo.setPsExcept(EXCEPT);
        objectVo.setPsEight(EIGHT);
        objectVo.setPsFive(FIVE);
        objectVo.setPsFor(FOR);
        objectVo.setPsFour(FOUR);
        objectVo.setPsHours(HOURS);
        objectVo.setPsMinutes(MINUTES);
        objectVo.setPsMonths(MONTHS);
        objectVo.setPsNine(NINE);
        objectVo.setPsOne(ONE);
        objectVo.setPsOneFourth(ONE_FOURTH);
        objectVo.setPsOneHalf(ONE_HALF);
        objectVo.setPsOneThird(ONE_THIRD);
        objectVo.setPsOpaiWarningLabelSource(WARNING_LABEL_SOURCE);
        objectVo.setPsPmisLanguage(PHARMACY_SYSTEM_PMIS_LANGUAGE);
        objectVo.setPsPmisPrinter(PMIS_PRINTER);
        objectVo.setPsPmisSectionDelete(PMISSECTION_DELETE);
        objectVo.setPsSeconds(SECONDS);
        objectVo.setPsSeven(SEVEN);

        objectVo.setPsSix(SIX);
        objectVo.setPsTen(TEN);
        objectVo.setPsThen(THEN);
        objectVo.setPsThree(THREE);
        objectVo.setPsThreeFourths(THREE_FOURTHS);
        objectVo.setPsTwo(TWO);
        objectVo.setPsTwoThirds(TWO_THIRDS);
        objectVo.setPsWarningLabelSource(WARNING_LABEL_SOURCE);
        objectVo.setPsWeeks(WEEKS);
        objectVo.setRevisionNumber(REVISION_NUMBER);
        objectVo.setValue(VALUE);
        objectVo.setId(EPL_ID.toString());


        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        PharmacySystemVo objectVo = createVo();
        EplPharmacySystemDo dataDo = pharmacySystemConverter.convert(objectVo);
        assertEquals(ERROR_MSG, AND, dataDo.getPsAnd());
        assertEquals(ERROR_MSG, WARNING_LABEL_SOURCE.toString(), dataDo.getPsCmopWarningLabelSource());
        assertEquals(ERROR_MSG, DAYS, dataDo.getPsDays());
        assertEquals(ERROR_MSG, EXCEPT, dataDo.getPsExcept());
        assertEquals(ERROR_MSG, EIGHT, dataDo.getPsEight());
        assertEquals(ERROR_MSG, FIVE, dataDo.getPsFive());
        assertEquals(ERROR_MSG, FOR, dataDo.getPsFor());
        assertEquals(ERROR_MSG, FOUR, dataDo.getPsFour());
        assertEquals(ERROR_MSG, HOURS, dataDo.getPsHours());
        assertEquals(ERROR_MSG, MINUTES, dataDo.getPsMinutes());
        assertEquals(ERROR_MSG, MONTHS, dataDo.getPsMonths());
        assertEquals(ERROR_MSG, NINE, dataDo.getPsNine());
        assertEquals(ERROR_MSG, ONE, dataDo.getPsOne());
        assertEquals(ERROR_MSG, ONE_FOURTH, dataDo.getPsOneFourth());
        assertEquals(ERROR_MSG, ONE_HALF, dataDo.getPsOneHalf());
        assertEquals(ERROR_MSG, ONE_THIRD, dataDo.getPsOneThird());
        assertEquals(ERROR_MSG, WARNING_LABEL_SOURCE.toString(), dataDo.getPsOpaiWarningLabelSource());
        assertEquals(ERROR_MSG, PHARMACY_SYSTEM_PMIS_LANGUAGE.toString(), dataDo.getPsPmisLanguage());
        assertEquals(ERROR_MSG, PMIS_PRINTER, dataDo.getPsPmisPrinter());
        assertEquals(ERROR_MSG, PMISSECTION_DELETE, dataDo.getPsPmisSectionDelete());
        assertEquals(ERROR_MSG, SECONDS, dataDo.getPsSeconds());
        assertEquals(ERROR_MSG, SEVEN, dataDo.getPsSeven());

        assertEquals(ERROR_MSG, SIX, dataDo.getPsSix());
        assertEquals(ERROR_MSG, TEN, dataDo.getPsTen());
        assertEquals(ERROR_MSG, THEN, dataDo.getPsThen());
        assertEquals(ERROR_MSG, THREE, dataDo.getPsThree());
        assertEquals(ERROR_MSG, THREE_FOURTHS, dataDo.getPsThreeFourths());
        assertEquals(ERROR_MSG, TWO, dataDo.getPsTwo());
        assertEquals(ERROR_MSG, TWO_THIRDS, dataDo.getPsTwoThirds());
        assertEquals(ERROR_MSG, WARNING_LABEL_SOURCE.toString(), dataDo.getPsWarningLabelSource());
        assertEquals(ERROR_MSG, WEEKS, dataDo.getPsWeeks());

        assertEquals(ERROR_MSG, VALUE, dataDo.getPsSiteName());

        assertEquals(ERROR_MSG, INACTIVATION_DATE, dataDo.getInactivationDate());

        assertEquals(ERROR_MSG, SITE_NUMBER, new Integer(dataDo.getSiteNumber()));

        assertEquals(ERROR_MSG, REVISION_NUMBER, dataDo.getRevisionNumber().longValue());
    }

}
