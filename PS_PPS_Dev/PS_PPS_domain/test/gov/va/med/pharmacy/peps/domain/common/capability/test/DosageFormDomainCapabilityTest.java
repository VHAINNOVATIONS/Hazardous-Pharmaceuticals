/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.ExcludeDosageCheck;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;


/**
 * DosageFormDomainCapabilityTest
 */
public class DosageFormDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private DosageFormDomainCapability nationaldosageFormDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
        this.nationaldosageFormDomainCapability = getNationalCapability(DosageFormDomainCapability.class);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllDoseNational() throws Exception {

        List<DosageFormVo> doseCollection = nationaldosageFormDomainCapability.retrieve();
        DosageFormVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
        nationaldosageFormDomainCapability.create(dataVo, getTestUser());

        assertTrue("Collection returned correct number", doseCollection.size() + 1 > doseCollection.size());
    }

    /**
     * This method buidlsVO
     * 
     * @param name String
     * @return DosageFormVo
     */
    private DosageFormVo buildVo(String name) {

        DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setDosageFormName(name);
        dosageForm.setInactivationDate(new Date());
        dosageForm.setItemStatus(ItemStatus.INACTIVE);
        dosageForm.setRequestItemStatus(RequestItemStatus.PENDING);
        dosageForm.setRejectionReasonText("rejected");
        dosageForm.setRevisionNumber(PPSConstants.I3);
        dosageForm.setExcludeFromDosageChks(ExcludeDosageCheck.YES);
        DispenseUnitPerDoseVo dfuVo = new DispenseUnitPerDoseVo();
        dfuVo.setStrDispenseUnitPerDose(String.valueOf(RandomGenerator.nextDouble(PPSConstants.I5, PPSConstants.I4)));

        PossibleDosagesPackageVo possibleDosagesPackage = new PossibleDosagesPackageVo();
        possibleDosagesPackage.setValue("O-Outpatient");
        List<PossibleDosagesPackageVo> lstPossibleDosagesPackage = new ArrayList<PossibleDosagesPackageVo>();
        lstPossibleDosagesPackage.add(possibleDosagesPackage);
        dfuVo.setPackages(lstPossibleDosagesPackage);

        dosageForm.getDfDispenseUnitsPerDose().add(dfuVo);



        DosageFormUnitVo unit = new DosageFormUnitVo();
        DrugUnitVo drugUnit = new DrugUnitVo();
        drugUnit.setId("99983");
        drugUnit.setValue("MCG");

        unit.setDrugUnit(drugUnit);
        unit.setPackages(lstPossibleDosagesPackage);
        Collection<DosageFormUnitVo> units = new ArrayList<DosageFormUnitVo>();
        units.add(unit);
        dosageForm.setDfUnits(units);

        dosageForm.setVaDataFields(new DataFields<DataField>());

        return dosageForm;
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//  /*  public void testCreateDosageFormLocal() throws Exception {
//        DosageFormVo dosageForm = buildVo(RandomGenerator.nextAlphabetic(5));
//        DosageFormVo returnedVo = localdosageFormDomainCapability.create(dosageForm, getTestUser());
//        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
//    }*/

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateDosageFormNational() throws Exception {

        DosageFormVo dosageForm = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
        DosageFormVo returnedVo = nationaldosageFormDomainCapability.create(dosageForm, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
// /*   public void testUpdateDosageFormDataFieldsLocal() throws Exception {
//
//        DosageFormVo toBeUpDatedVo = localdosageFormDomainCapability.retrieve("9991");
//
//        DataFields<DataField> dfs = toBeUpDatedVo.getDataFields();
//        DataField<String> verb = dfs.getDataField(FieldKey.VERB);
//        verb.selectValue("UPDAGEDVERB");
//
//        localdosageFormDomainCapability.update(toBeUpDatedVo, getTestUser());
//        DosageFormVo retrievedUpdated = localdosageFormDomainCapability.retrieve("9991");
//
//        assertEquals("Should be equal", "UPDAGEDVERB", retrievedUpdated.getDataFields().getDataField(FieldKey.VERB)
//            .getValue());
//
//    }*/

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
// /*   public void testUpdateDosageFormRejectReasonLocal() throws Exception {
//
//        List<DosageFormVo> names = localdosageFormDomainCapability.retrieve();
//
//        DosageFormVo form = localdosageFormDomainCapability.retrieve(names.get(0).getId());
//
//        form.setRejectionReasonText("updatedRejectREasonTExt");
//
//        localdosageFormDomainCapability.update(form, getTestUser());
//
//        DosageFormVo retrievedUpdated = localdosageFormDomainCapability.retrieve(form.getId());
//
//        assertEquals("Should be equal", "updatedRejectREasonTExt", retrievedUpdated.getRejectionReasonText());
//
//    }*/
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
///*    public void testUpdateDosageFormNounUnitsLocal() throws Exception {
//
//        DosageFormVo dosageForm = localdosageFormDomainCapability.retrieve("9992");
//
//        int unitPerDose = dosageForm.getDfDispenseUnitsPerDose().size();
//        int noun = dosageForm.getDfNouns().size();
//        int unit = dosageForm.getDfUnits().size();
//        int disp = dosageForm.getDfDispenseUnitsPerDose().size();
//        int localMedRoute = dosageForm.getLocalMedRoutes().size();
//
//        System.out.println("noun size = " + noun);
//        System.out.println("unit size = " + unit);
//        System.out.println("dispense unit size = " + disp);
//        System.out.println("localMedRoutes size = " + localMedRoute);
//
//        PossibleDosagesPackageVo possibleDosagesPackageN = new PossibleDosagesPackageVo();
//        possibleDosagesPackageN.setValue("I-Inpatient");
//        List<PossibleDosagesPackageVo> lstPossibleDosagesPackageN = new ArrayList<PossibleDosagesPackageVo>();
//        lstPossibleDosagesPackageN.add(possibleDosagesPackageN);
//
//        DosageFormUnitVo dosageFormUnit = new DosageFormUnitVo();
//        DrugUnitVo drugUnit = new DrugUnitVo();
//        drugUnit.setId("9999");
//        dosageFormUnit.setDrugUnit(drugUnit);
//        dosageFormUnit.setPackages(lstPossibleDosagesPackageN);
//        dosageForm.getDfUnits().add(dosageFormUnit);
//
//        DosageFormNounVo nounVo = new DosageFormNounVo();
//        nounVo.setNoun(UUID.randomUUID().toString());
//        nounVo.setOtherLanguageNoun("other");
//
//        nounVo.setPackages(lstPossibleDosagesPackageN);
//
//        dosageForm.getDfNouns().add(nounVo);
//
//        DispenseUnitPerDoseVo dfuVo = new DispenseUnitPerDoseVo();
//        dfuVo.setStrDispenseUnitPerDose(String.valueOf(RandomGenerator.nextDouble(5, 4)));
//        PossibleDosagesPackageVo possibleDosagesPackage = new PossibleDosagesPackageVo();
//        possibleDosagesPackage.setValue("O-Outpatient");
//        List<PossibleDosagesPackageVo> lstPossibleDosagesPackage = new ArrayList<PossibleDosagesPackageVo>();
//        lstPossibleDosagesPackage.add(possibleDosagesPackage);
//        dfuVo.setPackages(lstPossibleDosagesPackage);
//        dosageForm.getDfDispenseUnitsPerDose().add(dfuVo);
//
//        LocalMedicationRouteVo localMedVo = new LocalMedicationRouteVo();
//        localMedVo.setId("6713");
//        dosageForm.getLocalMedRoutes().add(localMedVo);
//
//        System.out.println(dosageForm);
//        localdosageFormDomainCapability.update(dosageForm, getTestUser());
//
//        DosageFormVo retrievedUpdated = localdosageFormDomainCapability.retrieve("9992");
//
//        assertEquals("Should be equal nouns", noun + 1, retrievedUpdated.getDfNouns().size());
//        assertEquals("Should be equal units per Dose", unitPerDose + 1, retrievedUpdated.getDfDispenseUnitsPerDose().size());
//
//    }*/

    
    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateDosageFormRejectReasonNational() throws Exception {

        List<DosageFormVo> names = nationaldosageFormDomainCapability.retrieve();

        DosageFormVo form = nationaldosageFormDomainCapability.retrieve(names.get(0).getId());

        form.setRejectionReasonText(PPSConstants.TEST_REASON_TEXT);

        nationaldosageFormDomainCapability.update(form, getTestUser());

        DosageFormVo retrievedUpdated = nationaldosageFormDomainCapability.retrieve(form.getId());

        assertEquals("Should be equal", PPSConstants.TEST_REASON_TEXT, retrievedUpdated.getRejectionReasonText());

    }
    
    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchDosageFormNational() throws Exception {
        int intialCount = 0;
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.DOSAGE_FORM_NAME, EntityType.DOSAGE_FORM));
        searchTerm.setValue("%");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        // create searchterms for the the testSearchDosageFormNational test.
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.DOSAGE_FORM_NAME);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(1);

        PaginatedList<DosageFormVo> names = (PaginatedList<DosageFormVo>) nationaldosageFormDomainCapability
            .search(searchCriteria);
        intialCount = names.getFullSize();

        DosageFormVo dosageForm = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
        nationaldosageFormDomainCapability.create(dosageForm, getTestUser());

        names = (PaginatedList<DosageFormVo>) nationaldosageFormDomainCapability.search(searchCriteria);
        assertEquals("Returned data", intialCount + 1, names.getFullSize());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsDosageFormNational() throws Exception {

        DosageFormVo dosageForm = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
        dosageForm.setItemStatus(ItemStatus.ACTIVE);
        nationaldosageFormDomainCapability.create(dosageForm, getTestUser());
        boolean exists = nationaldosageFormDomainCapability.existsByUniquenessFields(dosageForm);
        assertTrue("exists", exists);
    }

}
