/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.SearchTemplateGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintTemplateDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchCriteriaDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchCriteriaDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchTemplateDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDoKey;


/**
 * Creates stock instances for objects to handle cases where the types must be used, but the state isn't important for the
 * test.
 */
public class DomainTestHelper {

    /**
     * DomainTestHelper
     */
    private DomainTestHelper() {
        
    }
    
    /**
     * Creates test EplVadfLovDo instance.
     * 
     * @return EplVadfLovDo
     */
    public static EplVadfLovDo createTestLov() {
        EplVadfLovDo lov = new EplVadfLovDo();
        EplVadfLovDoKey lovKey = new EplVadfLovDoKey();

        lovKey.setVadfIdFk(1L);
        lovKey.setListValue("OUTPATIENT");
        lov.setKey(lovKey);

        return lov;
    }

    /**
     * Helper class for User related tests.
     */
    public static class Users {

        /** NATIONAL_ADMIN_USERNAME */
        public static final String NATIONAL_ADMIN_USERNAME = "nationaladmin";

        /** LOCAL_ADMIN_USERNAME */
        public static final String LOCAL_ADMIN_USERNAME = "localadmin";

        /**
         * Creates a stubbed user for testing
         * 
         * @return UserVo
         */
        public static UserVo createNationalAdminUser() {
            UserVo user = new UserVo();
            user.setId(RandomGenerator.nextLong());
            user.setUsername(NATIONAL_ADMIN_USERNAME);
            user.setFirstName("Jane");
            user.setLastName("Administrator");
            user.setLocation("national");
            user.addRole(Role.PSS_PPSN_SUPERVISOR); //NATIONAL_ADMINISTRATIVE_MANAGER

            return user;
        }

        /**
         * Creates a stubbed user for testing
         * 
         * @return UserVo
         */
        public static UserVo createLocalAdminUser() {
            UserVo user = new UserVo();
            user.setId(RandomGenerator.nextLong());
            user.setUsername(LOCAL_ADMIN_USERNAME);
            user.setFirstName("Joe");
            user.setLastName("Admin");
            user.setLocation("local");
            user.addRole(Role.LOCAL_ADMINISTRATIVE_MANAGER);

            return user;
        }

    }


    /**
     * Helper class for Search Template related tests.
     */
    public static class SearchTemplates {

        /** CURRENT_USER */
        public static final String CURRENT_USER = "bbthorton";

        /** TEMPLATE_NAME */
        public static final String TEMPLATE_NAME = "Template Name";

        /** TEMPLATE_TYPE */
        public static final String TEMPLATE_TYPE = TemplateType.LOCAL.toString();

        /** ADVANCED_SEARCH_Y_N */
        public static final boolean ADVANCED_SEARCH_Y_N = true;

        /** DEFAULT_Y_N */
        public static final boolean DEFAULT_Y_N = true;

        /** NOTES */
        public static final String NOTES = "These are my handy-dandy notes";

        /** SEARCH_TEMPLATE_EPL_ID */
        public static final Long SEARCH_TEMPLATE_EPL_ID = 42L;

        /** TEMPLATE_LOCATION */
        public static final TemplateLocation TEMPLATE_LOCATION = TemplateLocation.NDC_SEARCH;

        /** DOSAGE_FORM */
        public static final String DOSAGE_FORM = "PILL";

        /** STRENGTH */
        public static final String STRENGTH = "200";

        /**
         * Description
         * 
         * @return UserVo
         */
        public static UserVo createTemplateUserVo() {
            UserVo user = Users.createNationalAdminUser();

            user.setUsername(CURRENT_USER);

            return user;
        }

        /**
         * Creates test instance of EplSearchTemplateDo using static values.
         * 
         * @return EplSearchTemplateDo
         */
        public static EplSearchTemplateDo createEplSearchTemplateDo() {
            EplSearchTemplateDo templateDo = new EplSearchTemplateDo();

            templateDo.setTemplateName(TEMPLATE_NAME);
            templateDo.setEplId(SEARCH_TEMPLATE_EPL_ID);
            templateDo.setTemplateType(TEMPLATE_TYPE);
            templateDo.setAdvancedSearchYn("Y");
            templateDo.setAndSearchYn("N");
            templateDo.setDefaultYn("Y");
            templateDo.setNotes(NOTES);
            templateDo.setEplPrintTemplate(createPrintTemplateDo());

            return templateDo;
        }

        /**
         * Creates test instance of EplSearchCriteriaDo.
         * 
         * @param searchFieldName name of field to create
         * @param searchFieldValue the field's value
         * @return EplSearchCriteriaDo
         */
        public static EplSearchCriteriaDo createEplSearchCriteriaDo(String searchFieldName, String searchFieldValue) {
            EplSearchCriteriaDo searchCriteria = new EplSearchCriteriaDo();
            EplSearchCriteriaDoKey key = new EplSearchCriteriaDoKey();

            key.setSearchFieldName(searchFieldName);
            key.setEplIdSearchTemplateFk(SEARCH_TEMPLATE_EPL_ID);

            searchCriteria.setKey(key);
            searchCriteria.setSearchFieldValue(searchFieldValue);

            return searchCriteria;
        }

        /**
         * Description
         * 
         * @return PrintTemplateVo
         */
        public static PrintTemplateVo createPrintTemplateVo() {
            PrintTemplateVo printTemplateVo = new PrintTemplateVo();

            printTemplateVo.setTemplateLocation(TEMPLATE_LOCATION);
            printTemplateVo.setTemplateName(TEMPLATE_NAME);

            return printTemplateVo;
        }

        /**
         * Description
         * 
         * @return EplPrintTemplateDo
         */
        public static EplPrintTemplateDo createPrintTemplateDo() {
            EplPrintTemplateDo printTemplateDo = new EplPrintTemplateDo();

            printTemplateDo.setTemplateLocation(TEMPLATE_LOCATION.toString());
            printTemplateDo.setTemplateName(TEMPLATE_NAME);

            return printTemplateDo;
        }

        /**
         * Description
         * 
         * @param printTemplateId template id
         * @param printFieldName field name
         * @param templateFieldType field type
         * @return EplPrintFieldDo
         */
        public static EplPrintFieldDo createPrintFieldDo(Long printTemplateId, String printFieldName,
                                                         String templateFieldType) {
            EplPrintFieldDo printFieldDo = new EplPrintFieldDo();
            EplPrintFieldDoKey printFieldDoKey = new EplPrintFieldDoKey();

            printFieldDoKey.setEplIdPrintTemplateFk(printTemplateId);
            printFieldDoKey.setPrintFieldName(printFieldName);
            printFieldDo.setKey(printFieldDoKey);
            printFieldDo.setTemplateFieldType(templateFieldType);

            return printFieldDo;
        }

        /**
         * Description
         * 
         * @return EplSearchTemplateDo
         */
        public static SearchTemplateVo createSearchTemplateVo() {
            return createSearchTemplateVo(true);
        }

        /**
         * Description
         * 
         * @param useDefaultId if true will use SEARCH_TEMPLATE_EPL_ID
         * @return SearchCriteriaVo
         */
        public static SearchTemplateVo createSearchTemplateVo(boolean useDefaultId) {
            SearchTemplateGenerator generator = new SearchTemplateGenerator();
            SearchTemplateVo templateVo = generator.getFirst();

            templateVo.setTemplateName(TEMPLATE_NAME);
            templateVo.setNotes(NOTES);

            if (useDefaultId) {
                templateVo.setId(SEARCH_TEMPLATE_EPL_ID.toString());
            }
            
            templateVo.setEntityType(EntityType.NDC);
            templateVo.setPrintTemplate(createPrintTemplateVo());

            // templateVo.setAdvanced(ADVANCED_SEARCH_Y_N);
            // templateVo.isAdvanced(true);
            return templateVo;
        }

        /**
         * Description
         * 
         * @param fieldKeyName name of field key
         * @param value value of search term
         * @return SearchTermVo
         */
        public static SearchTermVo createSearchTermVo(String fieldKeyName, String value) {
            SearchTermVo searchTerm = new SearchTermVo();

            searchTerm.setSearchField(new SearchFieldVo(fieldKeyName));
            searchTerm.setValue(value);

            return searchTerm;
        }

    }


    /**
     * Test helper class
     */
    public static class LocalMedRoutes {

        /** DISPLAY_ON_IVP_IVPB */
        public static final String DISPLAY_ON_IVP_IVPB = "Y";

        /** EPL_ID */
        public static final Long EPL_ID = 9999L;

        /** INACTIVATION_DATE */
        public static final Date INACTIVATION_DATE = new java.util.Date();

        /** ITEM_STATUS */
        public static final ItemStatus ITEM_STATUS = ItemStatus.INACTIVE;

        /** IV_FLAG */
        public static final String IV_FLAG = "Y";

        /** MED_ROUTE_ABBREV */
        public static final String MED_ROUTE_ABBREV = "CRSHORL";

        /** MED_ROUTE_NAME */
        public static final String MED_ROUTE_NAME = "Crushed Oral".toUpperCase();

        /** OUTPATIENT_EXPANSION */
        public static final String OUTPATIENT_EXPANSION = "Crushed Oral Expansion";

        /** OTHER_LANG_EXPANSION */
        public static final String OTHER_LANG_EXPANSION = "todo en una otra idioma";

        /** INJECTION_SITE_PROMPT */
        public static final String INJECTION_SITE_PROMPT = "Y";

        /** REJECT_REASON_TEXT */
        public static final String REJECT_REASON_TEXT = "TestRejectReason";

        /** REQUEST_STATUS */
        public static final RequestItemStatus REQUEST_STATUS = RequestItemStatus.REJECTED;

        /** REVISION_NUMBER */
        public static final Long REVISION_NUMBER = 3L;

        /**
         * Description
         * 
         * @param useDefaultId indicates if a fake test ID should be used.
         * @return EplLocalMedRouteDo
         */
        public static EplLocalMedRouteDo createLocalMedRoutesDo(boolean useDefaultId) {
            EplLocalMedRouteDo medRoute = new EplLocalMedRouteDo();

            if (useDefaultId) {
                medRoute.setEplId(EPL_ID);
            }

            // medRoute.setEplPackageUseAssocs(eplPackageUseAssocs) //defer
            // medRoute.setEplStandardMedRoute(eplStandardMedRoute) //defer

            medRoute.setDisplayOnIvpIvpb(DISPLAY_ON_IVP_IVPB);
            medRoute.setInactivationDate(INACTIVATION_DATE);
            medRoute.setItemStatus(ITEM_STATUS.name());
            medRoute.setIvFlag(IV_FLAG);
            medRoute.setMedRouteAbbreviation(MED_ROUTE_ABBREV);
            medRoute.setMedRouteName(MED_ROUTE_NAME);
            medRoute.setMedRouteOutpatientExpansion(OUTPATIENT_EXPANSION);
            medRoute.setOtherLanguageExpansion(OTHER_LANG_EXPANSION);
            medRoute.setPromptForInjectionSite(INJECTION_SITE_PROMPT);
            medRoute.setRejectReasonText(REJECT_REASON_TEXT);
            medRoute.setRequestStatus(REQUEST_STATUS.name());
            medRoute.setRevisionNumber(REVISION_NUMBER);

            return medRoute;
        }

        /**
         * Description
         * 
         * @param useDefaultId indicates if a fake test ID should be used.
         * @return EplLocalMedRouteDo
         */
        public static LocalMedicationRouteVo createLocalMedRoutesVo(boolean useDefaultId) {
            LocalMedicationRouteVo medRoute = new LocalMedicationRouteVo();

            if (useDefaultId) {
                medRoute.setId(EPL_ID.toString());
            }

            // medRoute.setEplPackageUseAssocs(eplPackageUseAssocs) //defer
            // medRoute.setEplStandardMedRoute(eplStandardMedRoute) //defer

            medRoute.setDisplayOnIvp(toBoolean(DISPLAY_ON_IVP_IVPB));
            
            medRoute.setItemStatus(ITEM_STATUS);
            medRoute.setInactivationDate(INACTIVATION_DATE);
            medRoute.setIvFlag(toBoolean(IV_FLAG));
            medRoute.setAbbreviation(MED_ROUTE_ABBREV);
            medRoute.setValue(MED_ROUTE_NAME);
            medRoute.setOutpatientExpansion(OUTPATIENT_EXPANSION);
            medRoute.setOtherLanguageExpansion(OTHER_LANG_EXPANSION);
            medRoute.setPromptForInjectionSite(toBoolean(INJECTION_SITE_PROMPT));
            medRoute.setRejectionReasonText(REJECT_REASON_TEXT);
            medRoute.setRequestItemStatus(REQUEST_STATUS);
            medRoute.setRevisionNumber(REVISION_NUMBER);

            return medRoute;
        }
        
        
        /**
         * Convert a "Y" or "N" to a boolean true or false.
         * 
         * @param yOrN String
         * @return boolean
         */
        private static boolean toBoolean(String yOrN) {
            return "Y".equals(yOrN);
        }
    }
}
