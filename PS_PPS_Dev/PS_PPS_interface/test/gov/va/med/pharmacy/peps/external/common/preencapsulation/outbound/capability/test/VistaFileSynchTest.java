/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.test;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.AtcCanisterVo;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ExcludeDosageCheck;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageSizeVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.WardGroupForAtcVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;

import junit.framework.TestCase;


/**
 * The VistaFileSychronizationTest
 * 
 */
public class VistaFileSynchTest extends TestCase {

    private static final String S90123 = "90123";
    private static final String S1234 = "1234";
    
    private static final String TAB = "Tab";
    private VistaFileSynchCapability capability;

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
            "classpath*:xml/spring/test/*Context.xml", "classpath*:xml/local/spring/test/*Context.xml",
            "classpath*:xml/local/spring/test/CommonContext-Local-1.xml", "classpath*:xml/spring/test/Callback.xml" });
        this.capability = (VistaFileSynchCapability) context.getBean("vistaFileSynchCapability");
    }

    /**
     * The Send DispenseUnit Test
     */
    public void testSendDispenseUnit() {

        try {
            DispenseUnitVo dispenseUnitVo = getDispenseUnitVo();
            capability.sendNewItemToVista(dispenseUnitVo, null, true, false);
            assertNotNull("VA Dispense Unit IEN not null", dispenseUnitVo.getDispenseUnitIen());
            assertEquals("VA Dispense Unit IEN not 90123", S90123, dispenseUnitVo.getDispenseUnitIen());
        } catch (Exception e) {
            fail("Error sending dispense unit to vista. " + e.getMessage());
        }
    }

    /**
     * The Send DosageForm Test
     */
    public void testSendDosageForm() {

        try {
            DosageFormVo dosageFormVo = getDosageFormVo();
            capability.sendNewItemToVista(dosageFormVo, null, true, false);
            assertNotNull("1.Dosage Form IEN returned as NULL", dosageFormVo.getDosageFormIen());
            assertEquals("1.Dosage Form IEN not equal to 90123", S90123, dosageFormVo.getDosageFormIen());
        } catch (Exception e) {
            fail("1.Error sending Dosage Form to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Full DosageForm Test
     */
    public void testSendFullDosageForm() {

        try {
            DosageFormVo dosageFormVo = getFullDosageFormVo();
            capability.sendNewItemToVista(dosageFormVo, null, true, false);
            assertNotNull("Dosage Form IEN returned as NULL", dosageFormVo.getDosageFormIen());
            assertEquals("Dosage Form IEN not equal to 90123", S90123, dosageFormVo.getDosageFormIen());
        } catch (Exception e) {
            fail("Error sending Dosage Form to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Full Drug Class Test
     */
    public void testSendFullDrugClass() {
        try {
            DrugClassVo drugClassVo = getFullDrugClassVo();
            capability.sendNewItemToVista(drugClassVo, null, true, false);
            assertNotNull("1.Drug Class IEN is Null", drugClassVo.getDrugClassIen());
            assertEquals("1.Drug Class IEN not equal to 90123", S90123, drugClassVo.getDrugClassIen());
        } catch (Exception e) {
            fail("1.Error sending DrugClassVo to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Drug Class Test
     */
    public void testSendDrugClass() {
        try {
            DrugClassVo drugClassVo = getDrugClassVo();
            capability.sendNewItemToVista(drugClassVo, null, true, false);
            assertNotNull("Drug Class IEN is Null", drugClassVo.getDrugClassIen());
            assertEquals("Drug Class IEN not equal to 90123", S90123, drugClassVo.getDrugClassIen());
        } catch (Exception e) {
            fail("Error sending DrugClassVo to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Drug Unit Test
     */
    public void testSendDrugUnit() {
        try {
            DrugUnitVo drugUnitVo = getDrugUnitVo();
            capability.sendNewItemToVista(drugUnitVo, null, true, false);
            assertNotNull("IEN was not added", drugUnitVo.getDrugUnitIen());
            assertEquals("Returned IEN not equal to 90123", S90123, drugUnitVo.getDrugUnitIen());
        } catch (Exception e) {
            fail("Error sending drugUnitVo to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Ingredient Test
     */
    public void testSendIngredient() {
        try {
            IngredientVo ingredientVo = getIngredientVo();
            capability.sendNewItemToVista(ingredientVo, null, true, false);
            assertNotNull("1.Drug Ingredient IEN is Null", ingredientVo.getNdfIngredientIen());
            assertEquals("1.Drug Ingredient IEN not equal to 90123", S90123, ingredientVo.getNdfIngredientIen());
        } catch (Exception e) {
            fail("Error sending ingredientVo to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Full Ingredient Test
     */
    public void testSendFullIngredient() {
        try {
            IngredientVo ingredientVo = getFullIngredientVo();
            capability.sendNewItemToVista(ingredientVo, null, true, false);
            assertNotNull("Drug Ingredient IEN is Null", ingredientVo.getNdfIngredientIen());
            assertEquals("Drug Ingredient IEN not equal to 90123", S90123, ingredientVo.getNdfIngredientIen());
        } catch (Exception e) {
            fail("Error sending full ingredientVo to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Manufacturer Test
     */
    public void testSendManufacturer() {
        try {
            ManufacturerVo manufacturerVo = getManufacturerVo();
            capability.sendNewItemToVista(manufacturerVo, null, true, false);

            if (StringUtils.isBlank(manufacturerVo.getManufacturerIen())) {
                fail("IEN was not populated.");
            }
        } catch (Exception e) {
            fail("Error sending manufacturerVo to vista. " + e.getMessage());
        }
    }

    /**
     * Verify that a queued message is returned after an NdcItem is sent to VistA.
     */
    public void testSendNdcItem() {
        try {
            NdcVo ndcItem = getNdcVo();
            capability.sendNewItemToVista(ndcItem, null, true, false);
            assertNotNull("NDC IEN is null", ndcItem.getNdcIen());
            assertEquals("NDC IEN is not 90123", S90123, ndcItem.getNdcIen());
        } catch (Exception e) {
            fail("Error sending ndcItem to vista. " + e.getMessage());
        }

    }

    /**
     * The Send Product Item test
     */
    public void testSendProductItem() {
        try {
            ProductVo productItem = getProductVo();
            capability.sendNewItemToVista(productItem, null, true, false);
            assertNotNull("Product IEN Null", productItem.getNdfProductIen());
        } catch (Exception e) {
            fail("Error sending productItem to vista. " + e.getMessage());
        }

    }

    /**
     * The Send VAGeneric test
     */
    public void testSendVaGenericName() {
        try {
            GenericNameVo genericNameVo = getGenericNameVo();
            capability.sendNewItemToVista(genericNameVo, null, true, false);
            assertNotNull("1.Generic Name IEN Null", genericNameVo.getGenericIen());
            assertEquals("1.Generic Name IEN not equal to 90123", S90123, genericNameVo.getGenericIen());
        } catch (Exception e) {
            fail("1.Error sending genericNameVo to vista. " + e.getMessage());
        }
    }

    /**
     * The Send Full VAGeneric test
     */
    public void testSendFullVaGenericName() {
        try {
            GenericNameVo genericNameVo = getFullGenericNameVo();
            capability.sendNewItemToVista(genericNameVo, null, true, false);
            assertNotNull("Generic Name IEN Null", genericNameVo.getGenericIen());
            assertEquals("Generic Name IEN not equal to 90123", S90123, genericNameVo.getGenericIen());
        } catch (Exception e) {
            fail("Error sending genericNameVo to vista. " + e.getMessage());
        }
    }

    /**
     * create a Dispense Unit
     * 
     * @return DispenseUnitVo
     */
    private DispenseUnitVo getDispenseUnitVo() {
        DispenseUnitVo dispenseUnitVo = new DispenseUnitVo();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2008, Calendar.JANUARY, 1);
        dispenseUnitVo.setValue("mg");
        dispenseUnitVo.setInactivationDate(calendar1.getTime());
        dispenseUnitVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return dispenseUnitVo;
    }

    /**
     * Create a Dosage Form
     * 
     * @return DosageFromVo
     */
    private DosageFormVo getDosageFormVo() {
        DosageFormVo dosageFormVo = new DosageFormVo();

        dosageFormVo.setDosageFormName(TAB);
        dosageFormVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return dosageFormVo;
    }

    /**
     * Create a Dosage Form
     * 
     * @return DosageFromVo
     */
    private DosageFormVo getFullDosageFormVo() {
        DosageFormVo dosageFormVo = new DosageFormVo();

        // Calender information for inactivation date
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        // Main Dosage Form Contents
        dosageFormVo.setDosageFormName(TAB);
        dosageFormVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        dosageFormVo.setInactivationDate(calendar1.getTime());
        dosageFormVo.setExcludeFromDosageChks(ExcludeDosageCheck.YES);

        // Prep for Dosage Form Unit Collection
        ArrayList<DosageFormUnitVo> dosageFormUnitCollection1 = new ArrayList<DosageFormUnitVo>();
        DosageFormUnitVo dosageFormUnit1 = new DosageFormUnitVo();
        DosageFormUnitVo dosageFormUnit2 = new DosageFormUnitVo();
        DrugUnitVo drugUnitVo1 = getDrugUnitVo();
        drugUnitVo1.setDrugUnitIen("8766");
        dosageFormUnit1.setDrugUnit(drugUnitVo1);
        DrugUnitVo drugUnitVo2 = getDrugUnitVo();
        drugUnitVo2.setValue("newName66");
        drugUnitVo2.setDrugUnitIen("7655");
        dosageFormUnit2.setDrugUnit(drugUnitVo2);
        PossibleDosagesPackageVo possiblePackage1 = new PossibleDosagesPackageVo();
        possiblePackage1.setValue("INPATIENT");
        ArrayList<PossibleDosagesPackageVo> possiblePackageCollection1 = new ArrayList<PossibleDosagesPackageVo>();
        possiblePackageCollection1.add(possiblePackage1);
        dosageFormUnit1.setPackages(possiblePackageCollection1);
        dosageFormUnitCollection1.add(0, dosageFormUnit1);
        dosageFormUnitCollection1.add(1, dosageFormUnit2);
        dosageFormVo.getDfUnits().addAll(dosageFormUnitCollection1);

        // Prep for Dosage Form Dispense Unit Collection
        ArrayList<DispenseUnitPerDoseVo> dispenseUnitsPerDoseCollection = new ArrayList<DispenseUnitPerDoseVo>();
        DispenseUnitPerDoseVo dispenseUnitPerDoseVo1 = new DispenseUnitPerDoseVo();
        DispenseUnitPerDoseVo dispenseUnitPerDoseVo2 = new DispenseUnitPerDoseVo();
        dispenseUnitPerDoseVo1.setStrDispenseUnitPerDose("2");
        dispenseUnitPerDoseVo2.setStrDispenseUnitPerDose("3");
        PossibleDosagesPackageVo possiblePackage2 = new PossibleDosagesPackageVo();
        possiblePackage2.setValue("OUTPATIENT");
        ArrayList<PossibleDosagesPackageVo> possiblePackageCollection2 = new ArrayList<PossibleDosagesPackageVo>();
        possiblePackageCollection2.add(possiblePackage1);
        possiblePackageCollection2.add(possiblePackage2);
        dispenseUnitPerDoseVo1.setPackages(possiblePackageCollection2);
        dispenseUnitsPerDoseCollection.add(0, dispenseUnitPerDoseVo1);
        dispenseUnitsPerDoseCollection.add(0, dispenseUnitPerDoseVo2);
        dosageFormVo.getDfDispenseUnitsPerDose().addAll(dispenseUnitsPerDoseCollection);

        return dosageFormVo;
    }

    /**
     * Create a drug class
     * 
     * @return DrugClassVo
     */
    private DrugClassVo getDrugClassVo() {
        DrugClassVo drugClassVo = new DrugClassVo();

        drugClassVo.setVuid("32433");
        drugClassVo.setCode("AN202");
        drugClassVo.setClassification("Allergen1");
        drugClassVo.setDescription("Test description.");
        DrugClassificationTypeVo type = new DrugClassificationTypeVo();
        type.setValue("1 - Minor");
        drugClassVo.setClassificationType(type);

        // Generate Effective Date
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2007, Calendar.APRIL, 1);
        calendar2.set(PPSConstants.I2008, Calendar.JANUARY, 1);
        
        // set the VuidStatusHistoryVo objects for the getDrugClassesVo Test
        VuidStatusHistoryVo effectiveDateTime1 = new VuidStatusHistoryVo();
        VuidStatusHistoryVo effectiveDateTime2 = new VuidStatusHistoryVo();
        effectiveDateTime1.setItemStatus(ItemStatus.ACTIVE);
        effectiveDateTime2.setItemStatus(ItemStatus.INACTIVE);
        effectiveDateTime1.setEffectiveDateTime(calendar1.getTime());
        effectiveDateTime2.setEffectiveDateTime(calendar2.getTime());
        ArrayList<VuidStatusHistoryVo> effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();
        effectiveDateTimes.add(effectiveDateTime1);
        effectiveDateTimes.add(effectiveDateTime2);
        drugClassVo.setEffectiveDates(effectiveDateTimes);

        drugClassVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return drugClassVo;
    }

    /**
     * Create a fulldrug class
     * 
     * @return DrugClassVo
     */
    private DrugClassVo getFullDrugClassVo() {
        DrugClassVo drugClassVo = new DrugClassVo();

        // Generate Effective Date
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2007, Calendar.APRIL, 1);
        calendar2.set(PPSConstants.I2008, Calendar.JANUARY, 1);
        VuidStatusHistoryVo effectiveDateTime1 = new VuidStatusHistoryVo();
        VuidStatusHistoryVo effectiveDateTime2 = new VuidStatusHistoryVo();
        effectiveDateTime1.setItemStatus(ItemStatus.ACTIVE);
        effectiveDateTime2.setItemStatus(ItemStatus.INACTIVE);
        effectiveDateTime1.setEffectiveDateTime(calendar1.getTime());
        effectiveDateTime2.setEffectiveDateTime(calendar2.getTime());
        ArrayList<VuidStatusHistoryVo> effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();
        effectiveDateTimes.add(effectiveDateTime1);
        effectiveDateTimes.add(effectiveDateTime2);
        drugClassVo.setEffectiveDates(effectiveDateTimes);
        drugClassVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        // Create Parent Drug Class
        drugClassVo.setParentDrugClass(getDrugClassVo());
        drugClassVo.getParentDrugClass().setDrugClassIen("5680");

        // Set other values
        drugClassVo.setVuid("45678");
        drugClassVo.setCode("AN204");
        drugClassVo.setClassification("Allergen");
        drugClassVo.setDescription("Full Test description.");
        DrugClassificationTypeVo type = new DrugClassificationTypeVo();
        type.setValue("0 - Major");
        drugClassVo.setClassificationType(type);

        return drugClassVo;
    }

    /**
     * Create a DrugUnit
     * 
     * @return DrugUnitVo
     */
    private DrugUnitVo getDrugUnitVo() {
        DrugUnitVo drugUnitVo = new DrugUnitVo();

        drugUnitVo.setValue("mg");
        drugUnitVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return drugUnitVo;
    }

    /**
     * Create a Generic Name
     * 
     * @return GenericNameVo
     */
    private GenericNameVo getGenericNameVo() {

        GenericNameVo genericNameVo = new GenericNameVo();

        genericNameVo.setValue("Aspirin2");
        genericNameVo.setVuid("3839022");
        List<VuidStatusHistoryVo> vuidStatusHistoryList = new ArrayList<VuidStatusHistoryVo>();
        VuidStatusHistoryVo vuidStatusHistoryVo = new VuidStatusHistoryVo();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2012, 1, 1, 1, 1);
        vuidStatusHistoryVo.setEffectiveDateTime(calendar1.getTime());
        vuidStatusHistoryVo.setItemStatus(ItemStatus.ACTIVE);
        vuidStatusHistoryList.add(vuidStatusHistoryVo);
        genericNameVo.setEffectiveDates(vuidStatusHistoryList);
        genericNameVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return genericNameVo;

    }

    /**
     * Create a Fully populated Generic Name
     * 
     * @return GenericNameVo
     */
    private GenericNameVo getFullGenericNameVo() {

        GenericNameVo genericNameVo = new GenericNameVo();

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2007, Calendar.APRIL, 1);
        calendar2.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        // set the effective date times for the test
        VuidStatusHistoryVo effectiveDateTime1 = new VuidStatusHistoryVo();
        VuidStatusHistoryVo effectiveDateTime2 = new VuidStatusHistoryVo();
        effectiveDateTime1.setItemStatus(ItemStatus.ACTIVE);
        effectiveDateTime2.setItemStatus(ItemStatus.INACTIVE);
        effectiveDateTime1.setEffectiveDateTime(calendar1.getTime());
        effectiveDateTime2.setEffectiveDateTime(calendar2.getTime());
        ArrayList<VuidStatusHistoryVo> effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();
        effectiveDateTimes.add(effectiveDateTime1);
        effectiveDateTimes.add(effectiveDateTime2);
        genericNameVo.setEffectiveDates(effectiveDateTimes);

        genericNameVo.setValue("Aspirin3");
        genericNameVo.setVuid("3839023");
        genericNameVo.setMasterEntryForVuid(true);
        genericNameVo.setInactivationDate(calendar2.getTime());
        genericNameVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return genericNameVo;

    }

    /**
     * Create an Ingredient
     * 
     * @return IngredientVo
     */
    private IngredientVo getIngredientVo() {
        IngredientVo ingredientVo = new IngredientVo();

        // Set Effective Dates and Inactivation Date
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        
        // create the effective dates for the ingredientVo
        calendar1.set(PPSConstants.I2007, Calendar.APRIL, 1);
        calendar2.set(PPSConstants.I2008, Calendar.JANUARY, 1);
        ingredientVo.setInactivationDate(calendar2.getTime());
        VuidStatusHistoryVo effectiveDateTime1 = new VuidStatusHistoryVo();
        VuidStatusHistoryVo effectiveDateTime2 = new VuidStatusHistoryVo();
        
        // create the statuses for the ingredientVo
        effectiveDateTime1.setItemStatus(ItemStatus.ACTIVE);
        effectiveDateTime2.setItemStatus(ItemStatus.INACTIVE);
        effectiveDateTime1.setEffectiveDateTime(calendar1.getTime());
        effectiveDateTime2.setEffectiveDateTime(calendar2.getTime());
        ArrayList<VuidStatusHistoryVo> effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();
        effectiveDateTimes.add(effectiveDateTime1);
        effectiveDateTimes.add(effectiveDateTime2);
        ingredientVo.setEffectiveDates(effectiveDateTimes);

        ingredientVo.setVuid("vuid");
        ingredientVo.setValue("value");
        ingredientVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return ingredientVo;
    }

    /**
     * Create a Full Ingredient
     * 
     * @return IngredientVo
     */
    private IngredientVo getFullIngredientVo() {
        IngredientVo ingredientVo = new IngredientVo();

        // Set Effective Dates and Inactivation Date
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2007, Calendar.APRIL, 1);
        calendar2.set(PPSConstants.I2008, Calendar.JANUARY, 1);
        ingredientVo.setInactivationDate(calendar2.getTime());
        VuidStatusHistoryVo effectiveDateTime1 = new VuidStatusHistoryVo();
        VuidStatusHistoryVo effectiveDateTime2 = new VuidStatusHistoryVo();
        effectiveDateTime1.setItemStatus(ItemStatus.ACTIVE);
        effectiveDateTime2.setItemStatus(ItemStatus.INACTIVE);
        effectiveDateTime1.setEffectiveDateTime(calendar1.getTime());
        effectiveDateTime2.setEffectiveDateTime(calendar2.getTime());
        ArrayList<VuidStatusHistoryVo> effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();
        effectiveDateTimes.add(effectiveDateTime1);
        effectiveDateTimes.add(effectiveDateTime2);
        ingredientVo.setEffectiveDates(effectiveDateTimes);

        // Set Other Data
        ingredientVo.setVuid("23456");
        ingredientVo.setValue("FullValue");
        ingredientVo.setPrimaryIngredient(getIngredientVo());
        ingredientVo.getPrimaryIngredient().setIngredientIen("345");
        ingredientVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return ingredientVo;
    }

    /**
     * Create a Manufacturer
     * 
     * @return ManufacturerVo
     */
    private ManufacturerVo getManufacturerVo() {
        ManufacturerVo manufacturerVo = new ManufacturerVo();

        manufacturerVo.setValue("Smithklein1");
        manufacturerVo.setInactivationDate(new Date());
        manufacturerVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return manufacturerVo;
    }

    /**
     * Create an NDOC
     * 
     * @return NdcVo
     */
    private NdcVo getNdcVo() {

        // NdcGenerator ndcGenerator = new NdcGenerator();

        NdcVo ndcVo = new NdcVo();
        ManufacturerVo manufacturerVo = new ManufacturerVo();
        PackageTypeVo packageTypeVo = new PackageTypeVo();
        PackageSizeVo packageSizeVo = new PackageSizeVo();
        OtcRxVo otcRxVo = new OtcRxVo();
        ProductVo productVo = new ProductVo();

        ndcVo.setNdc("000677117201");
        ndcVo.setUpcUpn("28492930");
        ndcVo.setTradeName("Aspirin");
        manufacturerVo.setValue("Smithklein");
        manufacturerVo.setInactivationDate(new Date());
        manufacturerVo.setManufacturerIen("9876");
        packageSizeVo.setValue("NA");
        packageTypeVo.setValue("NA");
        packageTypeVo.setPackagetypeIen("8765");
        otcRxVo.setValue(PPSConstants.PRESCRIPTION);
        productVo.setVaProductName("Aspirin 81mg TAB");
        productVo.setNdfProductIen(new Long("7658"));

        ndcVo.setManufacturer(manufacturerVo);
        ndcVo.setPackageSize(new Double("3.67"));
        ndcVo.setPackageType(packageTypeVo);
        ndcVo.setOtcRxIndicator(otcRxVo);
        ndcVo.setProduct(productVo);
        ndcVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        return ndcVo;
    }

    /**
     * Create a product
     * 
     * @return ProductVo
     */
    private ProductVo getProductVo() {

        // ************* Instantiate VO's ***************
        ProductVo productVo = new ProductVo();
        RxConsultVo rxConsultVo = new RxConsultVo();
        OrderUnitVo orderUnitVo = new OrderUnitVo();
        DrugClassVo drugClassVo = new DrugClassVo();
        LocalMedicationRouteVo localMedRouteVo = new LocalMedicationRouteVo();
        DrugTextVo drugTextVo = new DrugTextVo();
        IngredientVo ingredientVo = new IngredientVo();
        DrugUnitVo drugUnitVo = new DrugUnitVo();
        DosageFormVo dosageFormVo = new DosageFormVo();
        DoseUnitVo doseUnitVo = new DoseUnitVo();
        GenericNameVo genericNameVo = new GenericNameVo();
        DispenseUnitVo dispenseUnitVo = new DispenseUnitVo();
        OrderableItemVo orderableVo = new OrderableItemVo();
        StandardMedRouteVo stdMedRouteVo = new StandardMedRouteVo();
        DrugClassificationTypeVo type = new DrugClassificationTypeVo();

        // *************** Set Values ********************
        DataField<Boolean> transmitToCmop = DataField.newInstance(FieldKey.TRANSMIT_TO_CMOP);
        transmitToCmop.setDataFieldId(new Long("1111"));
        transmitToCmop.setDefaultValue(false);
        transmitToCmop.selectValue(Boolean.TRUE);
        transmitToCmop.setEditable(true);
        productVo.getVaDataFields().setDataField(transmitToCmop);

        productVo.setVaProductName("Aspirin 325mg tab");
        productVo.setVaPrintName("Va Print Name");
        productVo.setVuid("Prod VUID");

        productVo.setGcnSequenceNumber("123456");
        productVo.setId("124");
        productVo.setMaxDosePerDay(Long.valueOf(1));
        productVo.setNationalFormularyIndicator(true);
        productVo.setNationalFormularyName("Prod Natl Form Name");

        productVo.setRejectionReasonText("Prod Rejection Reason");
        productVo.setTradeName("Prod Trade Name");
        productVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        orderableVo.setOiName("Test OI");
        orderableVo.setDosageForm(dosageFormVo);
        productVo.setOrderableItem(orderableVo);
        rxConsultVo.setValue("RX Consult Name");
        rxConsultVo.setConsultText("Text");
        rxConsultVo.setSpanishTranslation("Text (SP)");

        orderUnitVo.setAbbrev("Order Unit abbr.");

        drugClassVo.setCode("Drug Class Code");
        drugClassVo.setClassification("Drug Class Classification");
        drugClassVo.setDrugClassIen("125");
        type.setValue("DrugClass Type");
        drugClassVo.setClassificationType(type);
        drugClassVo.setDescription("Drug Class Description");
        drugClassVo.setVuid("Drug Class VUID");

        localMedRouteVo.setValue("Route Name");
        localMedRouteVo.setAbbreviation("Route Abbrev.");

        drugTextVo.setValue("Drug Text Name");
        drugTextVo.setTextLocal("Drug Text...Text");

        ingredientVo.setValue("Ingred Name");
        ingredientVo.setVuid("Ingred VUID");
        ingredientVo.setIngredientIen("2345");

        drugUnitVo.setValue("Drug Unit Name");
        drugUnitVo.setDrugUnitIen("3456");

        dosageFormVo.setDosageFormName(TAB);
        dosageFormVo.setDosageFormIen("4567");
        dosageFormVo.setInactivationDate(new Date());

        doseUnitVo.setId("ID");
        doseUnitVo.setDoseUnitName("ID Value");

        genericNameVo.setValue("VA Gen. Name");
        genericNameVo.setGenericIen("5678");
        genericNameVo.setVuid("Gen Name VUID");

        dispenseUnitVo.setValue("Disp. Name");
        dispenseUnitVo.setDispenseUnitIen("6789");

        stdMedRouteVo.setValue("Std Med Route Name");
        stdMedRouteVo.setFirstDatabankMedRoute("FDB Med route");
        stdMedRouteVo.setVuid("Std Med Rt VUID");

        // Set Effective Date Time
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2007, Calendar.APRIL, 1);
        calendar2.set(PPSConstants.I2008, Calendar.JANUARY, 1);

        VuidStatusHistoryVo effectiveDateTime1 = new VuidStatusHistoryVo();
        VuidStatusHistoryVo effectiveDateTime2 = new VuidStatusHistoryVo();
        effectiveDateTime1.setItemStatus(ItemStatus.ACTIVE);
        effectiveDateTime2.setItemStatus(ItemStatus.INACTIVE);
        effectiveDateTime1.setEffectiveDateTime(calendar1.getTime());
        effectiveDateTime2.setEffectiveDateTime(calendar2.getTime());
        ArrayList<VuidStatusHistoryVo> effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();
        effectiveDateTimes.add(effectiveDateTime1);
        effectiveDateTimes.add(effectiveDateTime2);
        productVo.setEffectiveDates(effectiveDateTimes);

        // *************** Add Sub-VO's ******************
        productVo.setDoseUnitVo(doseUnitVo);
        productVo.setGenericName(genericNameVo);
        productVo.setDispenseUnit(dispenseUnitVo);

        localMedRouteVo.setStandardMedRoute(stdMedRouteVo);

        SynonymVo synonymVo = new SynonymVo();
        synonymVo.setSynonymName("Test Name");
        IntendedUseVo intendedUse = new IntendedUseVo();
        intendedUse.setIntendedUse("0-TRADE NAME");
        OrderUnitVo orderUnit = new OrderUnitVo();
        orderUnit.setValue("mgg");
        synonymVo.setSynonymIntendedUse(intendedUse);
        synonymVo.setNdcCode("12345");
        synonymVo.setSynonymVsn(S1234);
        synonymVo.setSynonymOrderUnit(orderUnit);
        synonymVo.setSynonymPricePerOrderUnit(PPSConstants.D1POINT23);
        synonymVo.setSynonymDispenseUnitPerOrderUnit(new Double(S1234));
        synonymVo.setSynonymPricePerDispenseUnit(PPSConstants.D1POINT23);
        synonymVo.setSynonymVendor("Vendor");

        productVo.setSynonyms(new LinkedList<SynonymVo>());
        productVo.getSynonyms().add(synonymVo);

        WardGroupForAtcVo wardGroupForAtc = new WardGroupForAtcVo();
        wardGroupForAtc.setValue("Test ward");
        AtcCanisterVo atcVo = new AtcCanisterVo();
        atcVo.setAtcCanister(new Long("123"));
        atcVo.setWardGroupForAtc(wardGroupForAtc);

        productVo.setAtcCanisters(new LinkedList<AtcCanisterVo>());
        productVo.getAtcCanisters().add(atcVo);
        productVo.setVuid("123123");

        return getProductVoHelper(productVo);
    }
    
    /**
     * Create a product
     * @param product This is the productVo.
     * @return ProductVo
     */
    private ProductVo getProductVoHelper(ProductVo product) {
        ProductVo productVo = product;
        
        CsFedScheduleVo csFedScheduleVo = new CsFedScheduleVo();
        csFedScheduleVo.setId("1");
        csFedScheduleVo.setValue("1 - blah");

        productVo.setCsFedSchedule(csFedScheduleVo);
        productVo.setSingleMultiSourceProduct(new SingleMultiSourceProductVo());
        productVo.getSingleMultiSourceProduct().setValue("M - Multi Source");
        
        Collection<DrugClassGroupVo> durgs = new ArrayList<DrugClassGroupVo>();
        DrugClassGroupVo drug = new DrugClassGroupVo();
        DrugClassVo drugClassification = new DrugClassVo();
        drugClassification.setId("2");
        drugClassification.setValue("AD000-ANTIDOTES,DETERRENTS AND POISON CONTROL");
        drugClassification.setCode("CN105");
        drugClassification.setClassification("Drug Class Abc");
        drugClassification.setDrugClassIen("4321");
        drug.setDrugClass(drugClassification);
        drug.setPrimary(true);
        durgs.add(drug);
        productVo.setDrugClasses(durgs);
        
        
        Collection<ActiveIngredientVo> activeIngredients = new ArrayList<ActiveIngredientVo>();
        ActiveIngredientVo activeIngredient = new ActiveIngredientVo();
        IngredientVo ingredientName = new IngredientVo();
        ingredientName.setId("1");
        ingredientName.setValue("TestIngredient");
        ingredientName.setIngredientIen("5432");
        activeIngredient.setIngredient(ingredientName);
        DrugUnitVo unit = new DrugUnitVo();
        unit.setId("1");
        unit.setValue("mg");
        unit.setDrugUnitIen("7654");
        activeIngredient.setDrugUnit(unit);
        activeIngredient.setStrength("222");
        activeIngredient.setActive(true);
        activeIngredients.add(activeIngredient);
        productVo.setActiveIngredients(activeIngredients);
        
        return productVo;
    }
}
