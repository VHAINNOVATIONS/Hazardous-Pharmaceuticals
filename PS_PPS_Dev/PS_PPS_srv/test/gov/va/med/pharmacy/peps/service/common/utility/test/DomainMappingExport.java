/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.test;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingService;
import gov.va.med.pharmacy.peps.service.common.utility.DomainMappingCsvFile;

import junit.framework.TestCase;


/**
 * DomainMappingExport
 */
public class DomainMappingExport extends TestCase {

    private static final Logger LOG = Logger.getLogger(DomainMappingExport.class);

    private FdbMappingService fdbMappingService;
    private DomainService eplDomainService;

    private List<FdbDomainVo> fdbDrugClassList;
    private List<FdbDomainVo> fdbDrugIngredientList;
    private List<FdbDomainVo> fdbDrugUnitList;
    private List<FdbDomainVo> fdbDosageFormList;
    private List<FdbDomainVo> fdbGenericNameList;
    private UserVo user;

    /**
     * DomainMappingExport
     */
    public DomainMappingExport() {
        super();

    }

    @Override
    protected void setUp() {

        LOG.info("---------- " + getName() + " ----------");
        fdbMappingService = SpringTestConfigUtility.getNationalSpringBean(FdbMappingService.class);
        eplDomainService = SpringTestConfigUtility.getNationalSpringBean(DomainService.class);
    }

    /** 
     * test Domain Mapping export
     */
    public void testExport() {

        assertNotNull("Service is null", fdbMappingService);

        fdbDrugClassList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_CLASS, null);
        fdbDrugIngredientList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_INGREDIENT, null);
        fdbDrugUnitList = fdbMappingService.getFdbDomains(FdbDomainType.DRUG_UNIT, null);
        fdbDosageFormList = fdbMappingService.getFdbDomains(FdbDomainType.DOSAGE_FORM, null);
        fdbGenericNameList = fdbMappingService.getFdbDomains(FdbDomainType.GENERIC_NAME, null);

        DomainMappingCsvFile domainMappingCsvFile = new DomainMappingCsvFile();

        try {
            domainMappingCsvFile.createFile();
        } catch (Exception e) {
            LOG.error("Could not open Domain Mapping file for export: " + e);
        }

        try {
            domainMappingCsvFile.printDomainMapping(fdbDrugClassList, FdbDomainType.DRUG_CLASS,
                getEplTerms(FdbDomainType.DRUG_CLASS));
            domainMappingCsvFile.printDomainMapping(fdbDrugIngredientList, FdbDomainType.DRUG_INGREDIENT,
                getEplTerms(FdbDomainType.DRUG_INGREDIENT));
            domainMappingCsvFile.printDomainMapping(fdbDrugUnitList, FdbDomainType.DRUG_UNIT,
                getEplTerms(FdbDomainType.DRUG_UNIT));
            domainMappingCsvFile.printDomainMapping(fdbDosageFormList, FdbDomainType.DOSAGE_FORM,
                getEplTerms(FdbDomainType.DOSAGE_FORM));
            domainMappingCsvFile.printDomainMapping(fdbGenericNameList, FdbDomainType.GENERIC_NAME,
                getEplTerms(FdbDomainType.GENERIC_NAME));
        } catch (Exception e) {
            LOG.error("Problem writing Fdb Domain Mapping list: " + e);
        }

        domainMappingCsvFile.closeExport();

    }

    /**
     * getEplTerms retrieves associated terms
     * @param domain domain
     * @return Map of terms
     */
    private Map<Long, String> getEplTerms(FdbDomainType domain) {

        Map<Long, String> eplTerms = new LinkedHashMap<Long, String>();
        switch (domain) {

            case DOSAGE_FORM:
                for (DosageFormVo dosageFormVo : eplDomainService.getDosageForms()) {
                    eplTerms.put(Long.valueOf(dosageFormVo.getId()), dosageFormVo.getDosageFormName());
                }

                break;
            case DRUG_CLASS:
                for (DrugClassVo drugClassVo : eplDomainService.getDrugClasses()) {
                    eplTerms.put(Long.valueOf(drugClassVo.getId()), drugClassVo.getValue());
                }

                break;
            case DRUG_INGREDIENT:
                for (IngredientVo ingredientVo : eplDomainService.getIngredientName()) {
                    eplTerms.put(Long.valueOf(ingredientVo.getId()), ingredientVo.getValue());
                }

                break;
            case DRUG_UNIT:
                for (DrugUnitVo drugUnitVo : eplDomainService.getUnits()) {
                    eplTerms.put(Long.valueOf(drugUnitVo.getId()), drugUnitVo.getValue());
                }

                break;
            case GENERIC_NAME:
                for (GenericNameVo genericNameVo : eplDomainService.getGenericNames()) {
                    eplTerms.put(Long.valueOf(genericNameVo.getId()), genericNameVo.getValue());
                }

                break;
            default:

        }

        return eplTerms;
    }

    /**
     * Returns map of Epl name to lookup the id
     * @param domain domain
     * @return Map<String, Long>
     */
    private Map<String, Long> getEplIds(FdbDomainType domain) {

        Map<String, Long> eplIds = new LinkedHashMap<String, Long>();
        switch (domain) {

            case DOSAGE_FORM:
                for (DosageFormVo dosageFormVo : eplDomainService.getDosageForms()) {
                    eplIds.put(dosageFormVo.getDosageFormName(), Long.valueOf(dosageFormVo.getId()));
                }

                break;
            case DRUG_CLASS:
                for (DrugClassVo drugClassVo : eplDomainService.getDrugClasses()) {
                    eplIds.put(drugClassVo.getValue(), Long.valueOf(drugClassVo.getId()));
                }

                break;
            case DRUG_INGREDIENT:
                for (IngredientVo ingredientVo : eplDomainService.getIngredientName()) {
                    eplIds.put(ingredientVo.getValue(), Long.valueOf(ingredientVo.getId()));
                }

                break;
            case DRUG_UNIT:
                for (DrugUnitVo drugUnitVo : eplDomainService.getUnits()) {
                    eplIds.put(drugUnitVo.getValue(), Long.valueOf(drugUnitVo.getId()));
                }

                break;
            case GENERIC_NAME:
                for (GenericNameVo genericNameVo : eplDomainService.getGenericNames()) {
                    eplIds.put(genericNameVo.getValue(), Long.valueOf(genericNameVo.getId()));
                }

                break;
            default:

        }

        return eplIds;
    }

    /**
     * Unit test for running domain mapping import
     */
    public void testImportNDCs() {

        String strFileName = "DomainMapping.csv";
        DomainMappingCsvFile domainMappingCsvFile = new DomainMappingCsvFile();

        // retrieve epl ids
        Map<String, Long> drugClassIds = getEplIds(FdbDomainType.DRUG_CLASS);
        Map<String, Long> ingredientIds = getEplIds(FdbDomainType.DRUG_INGREDIENT);
        Map<String, Long> dosageFormIds = getEplIds(FdbDomainType.DOSAGE_FORM);
        Map<String, Long> drugUnitIds = getEplIds(FdbDomainType.DRUG_UNIT);
        Map<String, Long> genericNameIds = getEplIds(FdbDomainType.GENERIC_NAME);

        List<FdbDomainVo> drugClassList = new ArrayList();
        List<FdbDomainVo> ingredientList = new ArrayList();
        List<FdbDomainVo> dosageFormList = new ArrayList();
        List<FdbDomainVo> drugUnitList = new ArrayList();
        List<FdbDomainVo> genericNameList = new ArrayList();
        FdbDomainVo domain = new FdbDomainVo();

        Integer count = 0;

        try {
            domainMappingCsvFile.openForImport(strFileName);
        } catch (Exception e) {
            fail("Problem importing Domain Mapping file :" + e);
        }

        do {
            domain = new FdbDomainVo();

            try {
                domainMappingCsvFile.getNextDomain(domain);
            } catch (Exception e) {
                fail("Could not get next domain mapping row: " + e);
            }

            if (domain.getDomainType() != null) {
                switch (domain.getDomainType()) {
                    case DRUG_CLASS:
                        domain.setEplDomainId(drugClassIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        drugClassList.add(domain);
                        LOG.debug(count + "-Drug Class row returned: " + domain.getName() + "-" + domain.getEplDomainId());
                        break;

                    case DRUG_INGREDIENT:
                        domain.setEplDomainId(ingredientIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        ingredientList.add(domain);
                        LOG.debug(count + "-Drug Ingredient row returned: " + domain.getName());
                        break;

                    case DOSAGE_FORM:
                        domain.setEplDomainId(dosageFormIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        dosageFormList.add(domain);
                        LOG.debug(count + "-Dosage Form row returned: " + domain.getName());
                        break;

                    case DRUG_UNIT:
                        domain.setEplDomainId(drugUnitIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        drugUnitList.add(domain);
                        LOG.debug(count + "-Drug Unit row returned: " + domain.getName());
                        break;

                    case GENERIC_NAME:
                        domain.setEplDomainId(genericNameIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        genericNameList.add(domain);
                        LOG.debug(count + "-Generic Name row returned: " + domain.getName());
                        break;

                    default:
                        LOG.debug("In Default: " + domain.toString());
                        break;
                }
            }

            count++;

        } while (domain.getDomainType() != null);

        LOG.debug("Drug Class Count: " + drugClassList.size());
        LOG.debug("Ingredient Count: " + ingredientList.size());
        LOG.debug("Dosage Form Count: " + dosageFormList.size());
        LOG.debug("Drug Unit Count: " + drugUnitList.size());
        LOG.debug("Generic Name Count: " + genericNameList.size());

        try {
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DRUG_CLASS, drugClassList, getUser());
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DRUG_INGREDIENT, ingredientList, getUser());
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DOSAGE_FORM, dosageFormList, getUser());
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DRUG_UNIT, drugUnitList, getUser());
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.GENERIC_NAME, genericNameList, getUser());
        } catch (ValidationException e) {
            LOG.error("Error: Validation Exception while importing domain mapping:" + e);
        }

    }

    /**
     * getUser
     * @return UserVo
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * setUser
     * @param user UserVo
     */
    public void setUser(UserVo user) {
        this.user = user;
    }
}
