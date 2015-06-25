/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IfcapItemNumberVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayFinishingAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesAutoCreate;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReducedCopayVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.SpecimenTypeVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayFinishAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;


/**
 * NationalProductDomainCapabilityTest
 */
public class NationalProductDomainCapabilityTest extends DomainCapabilityTestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            NationalProductDomainCapabilityTest.class);
    
    private static final String I9991 = "9991";
    private static final String I9992 = "9992";
    private static final String CMOP = "Z0001";
    private static final Long THIRTYONEL = 31L;
    private ProductDomainCapability productDomainCapability;
    


    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.productDomainCapability = getNationalCapability(ProductDomainCapability.class);
       
    }

    /**
     * testAddProductVoNational
     * @throws ValidationException ValidationException
     */
    public void testAddProductVoNational() throws ValidationException {

        ProductVo productVo = buildProductVo();

        ProductVo newVO = productDomainCapability.create(productVo, getTestUser());
        
     //   ProductVo newVO2 = productDomainCapability.retrieve(newVO.getId());

      
        
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, productVo.getVaProductName(), newVO.getVaProductName());

    }

    /**
     * testUpdateProductNational.
     * @throws ValidationException ValidationException
     */
    public void testUpdateProductNational() throws ValidationException {

        // first retrieve

        String productId = I9991;

        ProductVo testVo = productDomainCapability.retrieve(productId);
        testVo.setVaProductName("updatedAcet");
        testVo.setNationalFormularyName("updateNFM");
        testVo.setVaPrintName("Updated VAPringName");
        testVo.setCmopId(CMOP);
        
        Collection<ReducedCopayVo> reducedCopayVoList = new ArrayList<ReducedCopayVo>();
        ReducedCopayVo reducedCopayVo = new ReducedCopayVo();
        reducedCopayVo.setReducedCopayStartDate(new Date());
        reducedCopayVoList.add(reducedCopayVo);
        testVo.setReducedCopay(reducedCopayVoList);
        

        // update labs and vitals

        LOG.debug("returned vo in testupdate product national");
        LOG.debug(testVo);

        ProductVo returnedVo = productDomainCapability.update(testVo, getTestUser());
        ProductVo retrievedVo = productDomainCapability.retrieve(productId);

        assertTrue("Product Name was not updated", 
                returnedVo.getVaProductName().equalsIgnoreCase(retrievedVo.getVaProductName()));
       
        assertTrue("Formulary Name was not updated", 
                returnedVo.getNationalFormularyName().equalsIgnoreCase(retrievedVo.getNationalFormularyName()));
        assertTrue("Print Name was not updated", 
                returnedVo.getVaPrintName().equalsIgnoreCase(retrievedVo.getVaPrintName()));
        assertTrue("CMOP ID was not updated", 
                returnedVo.getCmopId().equalsIgnoreCase(retrievedVo.getCmopId()));
    }

    /**
     * testRetrieveByIdNational.
     * @throws ItemNotFoundException 
     */
    public void testRetrieveByIdNational() throws ItemNotFoundException {
        String productId = I9991;

        ProductVo testVo = productDomainCapability.retrieve(productId);

        LOG.debug("TEST RETRIEVE PRODUCTS: " + testVo.toString());
        assertNotNull("Returned Item Result not returned", testVo);
        assertNotNull("NDC should not be null", testVo.getNdcs());
    }

    /**
     * testRevisionNumber
     * @throws ItemNotFoundException 
     * @throws Exception
     */
    public void testRevisionNumber() throws ItemNotFoundException {
        String ndcId = I9991;
        long revNumber = productDomainCapability.getRevisionNumber(ndcId);

        assertNotNull("Version number not returned", revNumber);
    }

    /**
     * @return ProductVo
     */
    private ProductVo buildProductVo() {
        ProductVo productVo = new ProductVo();

        OrderableItemVo orderVo = new OrderableItemVo();
        orderVo.setId(I9992);

        productVo.setOrderableItem(orderVo);
        productVo.setCmopId(CMOP);
        productVo.setLocalPrintName("localProntName");
        productVo.setAtcChoice(null);
        productVo.setAtcMnemonic("mnemonic");
        productVo.setOneAtcCanister(THIRTYONEL);
       
        productVo.setCreatePossibleDosage(true);
        productVo.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.NO_POSSIBLE_DOSAGES);
        
       
        ListDataField<String> applicationPackageUse = DataField.newInstance(FieldKey.APPLICATION_PACKAGE_USE);
        String appPkgString = "O-OUTPATIENT";
        applicationPackageUse.setDataFieldId(1L);
        applicationPackageUse.addSelection(appPkgString);
        applicationPackageUse.setEditable(true);
        productVo.getVaDataFields().setDataField(applicationPackageUse);

        SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_SINGLE);
        productVo.setSingleMultiSourceProduct(singleMultiSourceProduct);
        
        Collection<ReducedCopayVo> reducedCopayVoList = new ArrayList<ReducedCopayVo>();
        ReducedCopayVo reducedCopayVo = new ReducedCopayVo();
        reducedCopayVo.setReducedCopayStartDate(new Date());
        reducedCopayVoList.add(reducedCopayVo);
        productVo.setReducedCopay(reducedCopayVoList);

        productVo.setNationalFormularyName("formurlarytestName");
        productVo.setVaProductName("umasnewProduct" + RandomGenerator.nextAlphabetic(PPSConstants.I4));
        productVo.setVuid("vuid");
        productVo.setMasterEntryForVuid(true);
        productVo.setRequestItemStatus(RequestItemStatus.PENDING);
        productVo.setItemStatus(ItemStatus.INACTIVE);
        productVo.setLocalUse(true);
        productVo.setLocalSpecialHandling("te");
        productVo.setNationalFormularyIndicator(true);
        productVo.setVaPrintName("vaPrintName");

        Collection<IfcapItemNumberVo> items = new ArrayList<IfcapItemNumberVo>();
        IfcapItemNumberVo item = new IfcapItemNumberVo();
        item.setValue("ifcapvalue");
        items.add(item);
        productVo.setIfcapItemNumber(items);

        VitalsDisplayAdministrationVo vitalsAdminVo = new VitalsDisplayAdministrationVo();
        vitalsAdminVo.setValue("disp");
        productVo.getVitalsDisplayAdministration().add(vitalsAdminVo);

        VitalsDisplayFinishAnOrderVo vitalsFinishVo = new VitalsDisplayFinishAnOrderVo();
        vitalsFinishVo.setValue("order");
        productVo.getVitalsDisplayFinishAnOrder().add(vitalsFinishVo);

        VitalsDisplayOrderEntryVo vitalsOrderVo = new VitalsDisplayOrderEntryVo();
        vitalsOrderVo.setValue("orderEntry");
        productVo.getVitalsDisplayOrderEntry().add(vitalsOrderVo);

        LabDisplayAdministrationVo labAdminVo = new LabDisplayAdministrationVo();
        labAdminVo.setValue("adming");
        productVo.getLabDisplayAdministration().add(labAdminVo);

        LabDisplayFinishingAnOrderVo labFinishVo = new LabDisplayFinishingAnOrderVo();
        labFinishVo.setValue("finsit");
        productVo.getLabDisplayFinishingAnOrder().add(labFinishVo);

        LabDisplayOrderEntryVo labOrderVo = new LabDisplayOrderEntryVo();
        labOrderVo.setValue("entry");
        productVo.getLabDisplayOrderEntry().add(labOrderVo);

        SpecimenTypeVo specimenType = new SpecimenTypeVo();
        specimenType.setValue("newSpecTYpe");

        productVo.setSpecimenType(specimenType);
        
        
        return buildProductPart2(productVo);
    }
    
    /**
     * buildProductPart2
     * @param productVo productVo
     * @return productVo
     */
    private ProductVo buildProductPart2(ProductVo productVo) {
        
//        AtcCanisterVo atc = new AtcCanisterVo();
//        atc.setAtcCanister(TENL);
//        WardGroupForAtcVo wardGroupForAtc = new WardGroupForAtcVo();
//        wardGroupForAtc.setValue("Ward Group 1");
//
//        atc.setWardGroupForAtc(wardGroupForAtc);
//        
//        Collection<AtcCanisterVo> atcCanisters = new ArrayList<AtcCanisterVo>();
//        atcCanisters.add(atc);
//
//        atc = new AtcCanisterVo();
//        atc.setAtcCanister(TWENTYL);
//
//        wardGroupForAtc = new WardGroupForAtcVo();
//        wardGroupForAtc.setValue("Ward Group 2");
//
//        atc.setWardGroupForAtc(wardGroupForAtc);
//
//        atcCanisters.add(atc);
//
//        atc = new AtcCanisterVo();
//        atc.setAtcCanister(THIRTYL);
//
//        wardGroupForAtc = new WardGroupForAtcVo();
//        wardGroupForAtc.setValue("Ward Group 3");
//
//        atc.setWardGroupForAtc(wardGroupForAtc);
//
//        atcCanisters.add(atc);
//        productVo.setAtcCanisters(atcCanisters);

        DispenseUnitVo disp = new DispenseUnitVo();
        disp.setId(I9991);
        productVo.setDispenseUnit(disp);

        DoseUnitVo doseUnit = new DoseUnitVo();
        doseUnit.setId(I9991);
        productVo.setDoseUnitVo(doseUnit);

        Collection<DrugClassGroupVo> durgs = new ArrayList<DrugClassGroupVo>();
        DrugClassGroupVo drug = new DrugClassGroupVo();
        DrugClassVo drugClassification = new DrugClassVo();
        drugClassification.setId(I9992);

        drug.setDrugClass(drugClassification);
        drug.setPrimary(true);

        durgs.add(drug);
        productVo.setDrugClasses(durgs);

        Collection<ActiveIngredientVo> activeIngredients = new ArrayList<ActiveIngredientVo>();
        ActiveIngredientVo activeIngredient = new ActiveIngredientVo();
        IngredientVo ingredientName = new IngredientVo();

        ingredientName.setId(I9991);

        activeIngredient.setIngredient(ingredientName);
        activeIngredient.setStrength("newStrenght");
        activeIngredient.setActive(true);

        DrugUnitVo unit = new DrugUnitVo();
        unit.setId(I9991);
        activeIngredient.setDrugUnit(unit);
        activeIngredients.add(activeIngredient);

        CsFedScheduleVo sch = new CsFedScheduleVo();
        sch.setId(I9991);
        productVo.setCsFedSchedule(sch);
        
        VuidStatusHistoryVo statusVo = new VuidStatusHistoryVo();
        statusVo.setEffectiveDtmStatus(ItemStatus.ACTIVE);
        statusVo.setEffectiveDateTime(new Date());
        List<VuidStatusHistoryVo> list = new ArrayList<VuidStatusHistoryVo>();
        list.add(statusVo);
        productVo.setEffectiveDates(list);

        return productVo;

    }
}
