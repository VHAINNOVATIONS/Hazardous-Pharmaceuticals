/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.LocalMedicationRouteConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.test.DomainTestHelper;

import junit.framework.TestCase;


/**
 * Test {@link LocalMedicationRouteConverter}.
 */
public class LocalMedicationRouteConverterTest extends TestCase {
    private static final String EQUAL = "should be equal";
    private LocalMedicationRouteConverter localMedicationRouteConverter = new LocalMedicationRouteConverter();

    private EplLocalMedRouteDo dataDo;
    private LocalMedicationRouteVo objectVo;

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        objectVo = DomainTestHelper.LocalMedRoutes.createLocalMedRoutesVo(true);
        dataDo = DomainTestHelper.LocalMedRoutes.createLocalMedRoutesDo(true);
    }

    /**
     * Test
     */
    public void testShouldMapDisplayOnIvpIvp() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.DISPLAY_ON_IVP_IVPB, dataDo.getDisplayOnIvpIvpb());
    }

    /**
     * Test
     */
    public void testShouldMapEplId() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.EPL_ID, new Long(dataDo.getEplId()));
    }

    /**
     * Test
     */
    public void testShouldBeAbleToMapNullMapEplIds() {
        objectVo = DomainTestHelper.LocalMedRoutes.createLocalMedRoutesVo(false);
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertNull("mapped value should be null and not raise exception", dataDo.getEplId());
    }

    /**
     * Test
     */
    public void testInactivationDate() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.INACTIVATION_DATE, dataDo.getInactivationDate());
    }

    /**
     * Test
     */
    public void testItemStatus() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.ITEM_STATUS.name(), dataDo.getItemStatus());
    }

    /**
     * Test
     */
    public void testIvFlag() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.IV_FLAG, dataDo.getIvFlag());
    }

    /**
     * Test
     */
    public void testMedRouteAbbreviation() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.MED_ROUTE_ABBREV, dataDo.getMedRouteAbbreviation());
    }

    /**
     * Test
     */
    public void testMedRouteName() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.MED_ROUTE_NAME, dataDo.getMedRouteName());
    }

    /**
     * Test
     */
    public void testOutpatientExpansion() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.OUTPATIENT_EXPANSION, dataDo
            .getMedRouteOutpatientExpansion());
    }

    /**
     * Test
     */
    public void testOtherLanguageExpansion() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.OTHER_LANG_EXPANSION, dataDo
            .getOtherLanguageExpansion());
    }

    /**
     * Test
     */
    public void testInjectionSitePrompt() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.INJECTION_SITE_PROMPT, dataDo
            .getPromptForInjectionSite());
    }

    /**
     * Test
     */
    public void testRejectionReasonText() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.REJECT_REASON_TEXT, dataDo.getRejectReasonText());
    }

    /**
     * Test
     */
    public void testRequestStatus() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.REQUEST_STATUS.name(), dataDo.getRequestStatus());
    }

    /**
     * Test
     */
    public void testRevisionNumber() {
        dataDo = localMedicationRouteConverter.convert(objectVo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.REVISION_NUMBER, dataDo.getRevisionNumber());
    }

    /**
     * Convert a "Y" or "N" to a boolean true or false.
     * 
     * @param yOrN String
     * @return boolean
     */
    private Boolean toBoolean(String yOrN) {
        return "Y".equals(yOrN);
    }

    /**
     * Test
     */
    public void testShouldMapDisplayOnIvpIvpVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, toBoolean(DomainTestHelper.LocalMedRoutes.DISPLAY_ON_IVP_IVPB), objectVo
            .getDisplayOnIvp());
    }

    /**
     * Test
     */
    public void testShouldMapEplIdVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.EPL_ID, new Long(objectVo.getId()));
    }

    /**
     * Test
     */
    public void testInactivationDateVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.INACTIVATION_DATE, objectVo.getInactivationDate());
    }

    /**
     * Test
     */
    public void testItemStatusVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.ITEM_STATUS, objectVo.getItemStatus());
    }

    /**
     * Test
     */
    public void testIvFlagVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, toBoolean(DomainTestHelper.LocalMedRoutes.IV_FLAG), objectVo.getIvFlag());
    }

    /**
     * Test
     */
    public void testMedRouteAbbreviationVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.MED_ROUTE_ABBREV, objectVo.getAbbreviation());
    }

    /**
     * Test
     */
    public void testMedRouteNameVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.MED_ROUTE_NAME, objectVo.getValue());
    }

    /**
     * Test
     */
    public void testOutpatientExpansionVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.OUTPATIENT_EXPANSION, objectVo
            .getOutpatientExpansion());
    }

    /**
     * Test
     */
    public void testOtherLanguageExpansionVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.OTHER_LANG_EXPANSION, objectVo
            .getOtherLanguageExpansion());
    }

    /**
     * Test
     */
    public void testInjectionSitePromptVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, toBoolean(DomainTestHelper.LocalMedRoutes.INJECTION_SITE_PROMPT), objectVo
            .getPromptForInjectionSite());
    }

    /**
     * Test
     */
    public void testRejectionReasonTextVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.REJECT_REASON_TEXT, objectVo
            .getRejectionReasonText());
    }

    /**
     * Test
     */
    public void testRequestStatusVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.REQUEST_STATUS, objectVo.getRequestItemStatus());
    }

    /**
     * Test
     */
    public void testRevisionNumberVo() {
        objectVo = localMedicationRouteConverter.convert(dataDo);

        assertEquals(EQUAL, DomainTestHelper.LocalMedRoutes.REVISION_NUMBER, new Long(objectVo
            .getRevisionNumber()));
    }

}
