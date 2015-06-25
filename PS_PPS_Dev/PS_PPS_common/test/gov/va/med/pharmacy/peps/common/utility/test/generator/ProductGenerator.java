/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.AtcCanisterVo;
import gov.va.med.pharmacy.peps.common.vo.AtcChoice;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IfcapItemNumberVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LabTestMonitorVo;
import gov.va.med.pharmacy.peps.common.vo.LocalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.NationalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.SpecimenTypeVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.WardGroupForAtcVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;


/**
 * Generate a list of Products
 */
public class ProductGenerator extends VoGenerator<ProductVo> {

    private static final String I_INPATIENT = "I-INPATIENT";
    private static final String I9991 = "9991";
    private static final String I9992 = "9992";
    private static final String I9994 = "9994";

    private static final double D_10_2222 = 10.2222;
    private static final int I_80 = 80;
    private static final int I_88 = 88;

    /**
     * Configures canned ATC information onto the specified ProductVo.
     * 
     * @param productVo The product to configure.
     * @param atcChoice The ATC mode choice needed, one of AtcChoice, or null.
     */
    public static void setAtcFields(ProductVo productVo, AtcChoice atcChoice) {

        // Setup ATC info (other than canisters MDF).

        productVo.setAtcChoice(atcChoice);
        productVo.setAtcMnemonic("a" + Long.valueOf(new Date().getTime()).toString());
        productVo.setOneAtcCanister(PPSConstants.L3);

        // Setup ATC Canisters info.

        AtcCanisterVo atc = new AtcCanisterVo();
        atc.setAtcCanister(PPSConstants.L10);

        WardGroupForAtcVo wardGroupForAtc = new WardGroupForAtcVo();
        wardGroupForAtc.setValue("Ward Group 1");

        atc.setWardGroupForAtc(wardGroupForAtc);

        Collection<AtcCanisterVo> atcCanisters = new ArrayList<AtcCanisterVo>();
        atcCanisters.add(atc);

        atc = new AtcCanisterVo();
        atc.setAtcCanister(PPSConstants.L20);

        wardGroupForAtc = new WardGroupForAtcVo();
        wardGroupForAtc.setValue("Ward Group 2");

        atc.setWardGroupForAtc(wardGroupForAtc);

        atcCanisters.add(atc);

        atc = new AtcCanisterVo();
        atc.setAtcCanister(PPSConstants.L30);

        wardGroupForAtc = new WardGroupForAtcVo();
        wardGroupForAtc.setValue("Ward Group 3");

        atc.setWardGroupForAtc(wardGroupForAtc);

        atcCanisters.add(atc);
        productVo.setAtcCanisters(atcCanisters);
    }

    /**
     * Generate a list of Products
     * 
     * @return List of Products
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<ProductVo> generate() {

        List<ProductVo> products = new ArrayList<ProductVo>();

        products.add(buildProductVo());

        return products;
    }

    /**
     * Create a ProductVo with dummy values.
     * 
     * @return ProductVo
     */
    public ProductVo buildProductVo() {

        ProductVo productVo = new ProductVo();
        productVo.setId("1234");
        OrderableItemVo orderVo = new OrderableItemVo();
        orderVo.setId("");
        orderVo.setOiName("Test OI");
        DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setId("99964");
        dosageForm.setDosageFormName("TABLET");
        orderVo.setDosageForm(dosageForm);
        productVo.setLocalSpecialHandling("sp");

        productVo.setOrderableItem(orderVo);
        productVo.setLocalSpecialHandling("national formulary restriction sibani");

        // GCNSEQNO.
        productVo.setGcnSequenceNumber("011111");

        // Cs fed schedule
        CsFedScheduleVo fed = new CsFedScheduleVo();
        fed.setId("9997");
        productVo.setCsFedSchedule(fed);

        // Formulary.
        ListDataField<String> formulary = DataField.newInstance(FieldKey.FORMULARY);
        formulary.setDataFieldId(PPSConstants.L24);
        formulary.addDefaultSelection("");
        formulary.addSelection(PPSConstants.FORMULARY);
        productVo.getVaDataFields().setDataField(formulary);

        // CMOP ID
        productVo.setCmopId("12345");

        // CMOP dispense.
        productVo.setCmopDispense(true);

        // Do not mail.
        DataField<Boolean> doNotMail = DataField.newInstance(FieldKey.DO_NOT_MAIL);
        doNotMail.setDataFieldId(PPSConstants.L7);
        doNotMail.setDefaultValue(false);
        doNotMail.selectValue(Boolean.FALSE);
        doNotMail.setEditable(true);
        productVo.getVaDataFields().setDataField(doNotMail);

        DataField<Boolean> transmitToCmop = DataField.newInstance(FieldKey.TRANSMIT_TO_CMOP);
        transmitToCmop.setDataFieldId(2L);
        transmitToCmop.setDefaultValue(false);
        transmitToCmop.selectValue(Boolean.FALSE);
        transmitToCmop.setEditable(true);
        productVo.getVaDataFields().setDataField(transmitToCmop);

        ListDataField<String> applicationPackageUse = DataField.newInstance(FieldKey.APPLICATION_PACKAGE_USE);
        String appPkgString = I_INPATIENT;
        applicationPackageUse.setDataFieldId(1L);
        applicationPackageUse.addSelection(appPkgString);
        applicationPackageUse.setEditable(true);
        productVo.getVaDataFields().setDataField(applicationPackageUse);

        productVo.setNationalFormularyName("formurlarytestName");
        productVo.setVaProductName("mynewProduct");
        productVo.setVaPrintName("mynewProductPn");
        productVo.setLocalPrintName(productVo.getVaPrintName());
        productVo.setVuid("vuid");
        productVo.setRevisionNumber(PPSConstants.L3);

        SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_SINGLE);
        productVo.setSingleMultiSourceProduct(singleMultiSourceProduct);

        productVo.setRequestItemStatus(RequestItemStatus.PENDING);
        productVo.setItemStatus(ItemStatus.INACTIVE);
        productVo.setLocalUse((ItemStatus.INACTIVE.equals(productVo.getItemStatus()) ? false : true));
        productVo.setMaxDosePerDay(1L);

        DispenseUnitVo disp = new DispenseUnitVo();
        disp.setId("1");
        disp.setValue("mg");
        productVo.setDispenseUnit(disp);

        StandardMedRouteVo route = new StandardMedRouteVo();
        route.setId("1");
        productVo.setStandardMedicationRoute(route);

        return buildProductVo2(productVo);
    }

    /**
     * Create a ProductVo with dummy values.
     * @param productVo productVo
     * 
     * @return ProductVo
     */
    public ProductVo buildProductVo2(ProductVo productVo) {

        Collection<DrugClassGroupVo> drugs = new ArrayList<DrugClassGroupVo>();
        DrugClassGroupVo drug = new DrugClassGroupVo();
        DrugClassVo drugClassification = new DrugClassVo();
        drugClassification.setId("99927");
        drugClassification.setValue("AN100-ANTINEOPLASTICS,ALKYLATING AGENTS");
        drugClassification.setCode("AN100");

        drug.setDrugClass(drugClassification);
        drug.setPrimary(true);
        drugs.add(drug);
        productVo.setDrugClasses(drugs);

        GenericNameVo gn = new GenericNameVo();
        gn.setId("24");
        gn.setValue("someDrug");
        gn.setVuid("1234567890124567890");

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        List<VuidStatusHistoryVo> list = new ArrayList<VuidStatusHistoryVo>();

        try {
            Date date = df.parse("01/02/2010");
            VuidStatusHistoryVo effectiveDate = new VuidStatusHistoryVo();
            effectiveDate.setEffectiveDateTime(date);
            effectiveDate.setEffectiveDtmStatus(ItemStatus.INACTIVE);

            list.add(effectiveDate);

            Date date2 = df.parse("01/03/2010");
            VuidStatusHistoryVo effectiveDate2 = new VuidStatusHistoryVo();
            effectiveDate2.setEffectiveDateTime(date2);
            effectiveDate2.setEffectiveDtmStatus(ItemStatus.ACTIVE);
            list.add(effectiveDate2);

            gn.setEffectiveDates(list);
        } catch (ParseException e) {
            gn.setEffectiveDates(list);
        }

        productVo.setGenericName(gn);

        Collection<ActiveIngredientVo> activeIngredients = new ArrayList<ActiveIngredientVo>();
        ActiveIngredientVo activeIngredient = new ActiveIngredientVo();
        IngredientVo ingredientName = new IngredientVo();
        ingredientName.setId("1");
        ingredientName.setValue("TestIngredient");
        activeIngredient.setIngredient(ingredientName);
        DrugUnitVo unit = new DrugUnitVo();
        unit.setId("1");
        unit.setValue("mg");
        activeIngredient.setDrugUnit(unit);
        activeIngredient.setStrength("222");
        activeIngredient.setActive(true);
        activeIngredients.add(activeIngredient);
        productVo.setActiveIngredients(activeIngredients);

        setAtcFields(productVo, AtcChoice.ONE_ATC_MODE);

        LocalPossibleDosagesVo local = new LocalPossibleDosagesVo();
        local.setLocalPossibleDosage("34");
        DoseUnitVo doseUnit = new DoseUnitVo();
        doseUnit.setId(I9991);
        doseUnit.setDoseUnitName("du");
        local.setDoseUnit(doseUnit);
        local.setNumericDose(1.0);
        PossibleDosagesPackageVo possibleDosagesPackage = new PossibleDosagesPackageVo();
        possibleDosagesPackage.setValue(I_INPATIENT);
        List<PossibleDosagesPackageVo> lstPossibleDosagesPackage = new ArrayList<PossibleDosagesPackageVo>();
        lstPossibleDosagesPackage.add(possibleDosagesPackage);
        local.setPossibleDosagePackage(lstPossibleDosagesPackage);

        Collection<LocalPossibleDosagesVo> locals = new ArrayList<LocalPossibleDosagesVo>();
        locals.add(local);
        productVo.setLocalPossibleDosages(locals);

        // NationalPossibleDosagesVo national = new NationalPossibleDosagesVo();
        // national.setPossibleDosagesDispenseUnitsPerDose(new Double(23.22));
        // national.setPossibleDosagesPackage(lstPossibleDosagesPackage);
        // national.setDose(new Double(12.22));
        //
        // Collection<NationalPossibleDosagesVo> nationals = new ArrayList<NationalPossibleDosagesVo>();
        // nationals.add(national);
        //        productVo.setNationalPossibleDosages(nationals);

        CsFedScheduleVo sch = new CsFedScheduleVo();
        sch.setId("1");
        sch.setValue("2 - FedSch");
        productVo.setCsFedSchedule(sch);

        // DEA Schedule
        ListDataField<String> deaSchedule = DataField.newInstance(FieldKey.DEA_SCHEDULE);
        deaSchedule.setDataFieldId(PPSConstants.L6);
        deaSchedule.addDefaultSelection("");
        deaSchedule.addSelection("2-Schedule 2 Item");
        productVo.getVaDataFields().setDataField(deaSchedule);

        // Product type.
//        ListDataField<String> productType = DataField.newInstance(FieldKey.CATEGORIES);
//        productType.setDataFieldId(57L);
//        productType.addDefaultSelection(PPSConstants.SUPPLY);
//        productType.addSelection(PPSConstants.SUPPLY);
//        productVo.getVaDataFields().setDataField(productType);

        IntendedUseVo inUse = new IntendedUseVo();
        inUse.setId(I9991);

        OrderUnitVo order = new OrderUnitVo();
        order.setId(I9991);

        SynonymVo syn = new SynonymVo();
        syn.setSynonymName("newSynName");
        syn.setNdcCode("ndcCode");
        syn.setSynonymIntendedUse(inUse);
        syn.setSynonymOrderUnit(order);

        Collection<SynonymVo> syns = new ArrayList<SynonymVo>();
        syns.add(syn);
        productVo.setSynonyms(syns);

        DispenseUnitVo dispenseUnitVo = new DispenseUnitGenerator().pseudoRandom();
        productVo.setDispenseUnit(dispenseUnitVo);

        ListDataField<String> ncpdpDispenseUnit = DataField.newInstance(FieldKey.NCPDP_DISPENSE_UNIT);
        ncpdpDispenseUnit.addSelection("EA");
        ncpdpDispenseUnit.setEditable(true);
        productVo.getVaDataFields().setDataField(ncpdpDispenseUnit);

        DataField<String> ncpdpQuantityMultiplier = DataField.newInstance(FieldKey.NDCDP_QUANTITY_MULTIPLIER);
        ncpdpQuantityMultiplier.selectValue("1.0");
        productVo.getVaDataFields().setDataField(ncpdpQuantityMultiplier);

//        List<RxConsultVo> localRxConsultVos = new ArrayList<RxConsultVo>();
//        localRxConsultVos.add(new RxConsultGenerator().generateLocal());
//        List<RxConsultVo> nationalRxConsultVos = new ArrayList<RxConsultVo>();
//        nationalRxConsultVos.add(new RxConsultGenerator().generateNational());
//
//        productVo.getLocalWarningLabels().addAll(localRxConsultVos);
//        productVo.getNationalWarningLabels().addAll(nationalRxConsultVos);

        return productVo;
    }

    /**
     * Generate a pseudo-random (not all fields are actually random) ProductVo.
     * 
     * @return ProductVo
     */
    public ProductVo pseudoRandom() {

        ProductVo product = new ProductVo();
        product.setVaProductName(RandomGenerator.nextAlphabetic(I_10));
        product.setVaDataFields(new DataFields<DataField>());
        product.setDrugClasses(new DrugClassGenerator().getList());
        product.setGcnSequenceNumber(RandomGenerator.nextNumeric(I_6));
        product.setGenericName(new GenericNameGenerator().getFirst());

        // Question: should ID be set by default? I would image that generated Products should be transient
        product.setId(RandomGenerator.nextNumeric(I_6));
        product.setItemStatus(ItemStatus.ACTIVE);
        product.setNdcs(Collections.EMPTY_LIST);
        product.setOrderableItem(null);
        product.setRequestItemStatus(RequestItemStatus.PENDING);
        product.setStandardMedicationRoute(new RouteGenerator().getFirst());

        product.setVuid(RandomGenerator.nextNumeric(I_10));

        return product;
    }

    /**
     * Create a Product View Object
     * 
     * @param inProduct The productVo with some default values
     * @param local Indicates if this is a local submission
     * @param oi Indicates whether an OI should be generated as well
     * @param oneDrugClass Indicates whether to add secondardy drug classes.
     * @return ProductVo
     */
    public ProductVo generateProductVo(ProductVo inProduct, boolean local, boolean oi, boolean oneDrugClass) {

        ProductVo product = inProduct;

        if (inProduct == null) {
            product = buildProductVo();
        }

        // *******************Mandatory Fields**************
        // Non VA Data Fields
        // cs federal schedule
        CsFedScheduleVo csFedSchedule = new CsFedScheduleVo();
        csFedSchedule.setId(I9991);
        csFedSchedule.setValue("0 - UNSCHEDULED");
        product.setCsFedSchedule(csFedSchedule);

        // set drug class
        Collection<DrugClassGroupVo> drugs = new ArrayList<DrugClassGroupVo>();
        DrugClassGroupVo drugClass = new DrugClassGroupVo();
        DrugClassVo drugClassification = new DrugClassVo();

        if (!oneDrugClass) {
            drugClassification.setId(I9991);
            drugClassification.setValue("AD000-ANTIDOTES,DETERRENTS AND POISON CONTROL");
            drugClassification.setCode("AD000");

            drugClass.setDrugClass(drugClassification);
            drugClass.setPrimary(true);
            drugs.add(drugClass);
            drugClass = new DrugClassGroupVo();
            drugClassification = new DrugClassVo();
        }

        drugClassification.setId(I9992);
        drugClassification.setValue("AD100-ALCOHOL DETERRENTS");
        drugClassification.setCode("AD100");

        drugClass.setDrugClass(drugClassification);
        drugClass.setPrimary(false);
        drugs.add(drugClass);
        product.setDrugClasses(drugs);

        // set generic name
        GenericNameVo genericName = new GenericNameVo();
        genericName.setId("9995");
        genericName.setVuid("4017420");
        genericName.setValue("THYROID");
        genericName.setItemStatus(ItemStatus.ACTIVE);
        genericName.setRequestItemStatus(RequestItemStatus.APPROVED);
        product.setGenericName(genericName);

        // set active ingredient
        Collection<ActiveIngredientVo> activeIngredients = new ArrayList<ActiveIngredientVo>();
        ActiveIngredientVo activeIngredient = new ActiveIngredientVo();

        // set the ingredient name
        IngredientVo ingredientName = new IngredientVo();
        ingredientName.setId(I9991);
        ingredientName.setVuid("4017410");
        ingredientName.setValue("ATROPINE SULFATE");

        activeIngredient.setIngredient(ingredientName);

        // set the unit
        DrugUnitVo unit = new DrugUnitVo();
        unit.setId("99920");
        unit.setValue("MG");

        activeIngredient.setDrugUnit(unit);

        activeIngredient.setStrength("0.25");

        // add to the collection
        activeIngredients.add(activeIngredient);

        product.setActiveIngredients(activeIngredients);

        setAtcFields(product, AtcChoice.ONE_ATC_MODE);

        // national formulary name
        product.setNationalFormularyName("my new national formulary name");

        product = generateProductVo2(product, local, oi, oneDrugClass);
        product = generateProductVo3(product, local, oi, oneDrugClass);
        product = generateProductVo4(product, local, oi, oneDrugClass);

        return product;
    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {

    }

    /**
     * Create a Product View Object for the Product Generator class.
     * 
     * @param product The productVo with some default values
     * @param local Indicates if this is a local submission
     * @param oi Indicates whether an OI should be generated as well
     * @param oneDrugClass Indicates whether to add secondardy drug classes.
     * @return ProductVo
     */
    public ProductVo generateProductVo2(ProductVo product, boolean local, boolean oi, boolean oneDrugClass) {

        // orderable item
        if (oi) {
            OrderableItemVo orderableItem = new OrderableItemVo();
            orderableItem.setId(I9992);
            orderableItem.setOiName("DIGOXIN TAB");

            if (local) {
                DosageFormVo dosageForm = new DosageFormVo();
                dosageForm.setId("99963");
                dosageForm.setDosageFormName("TAB");
                orderableItem.setDosageForm(dosageForm);
            } else {
                DosageFormVo dosageForm = new DosageFormVo();
                dosageForm.setId(I9994);
                dosageForm.setDosageFormName("CAP,SA");
                orderableItem.setDosageForm(dosageForm);
            }

            product.setOrderableItem(orderableItem);
        }

        // single multi source product
        SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_SINGLE);
        product.setSingleMultiSourceProduct(singleMultiSourceProduct);

        // VA Print Name
        product.setVaPrintName("va print name");

        // VA Product Name
        product.setVaProductName("blue pill " + Long.valueOf(new Date().getTime()).toString());

        // set VA Dispense Unit
        DispenseUnitVo vaDispenseUnit = new DispenseUnitVo();
        vaDispenseUnit.setId(I9994);
        vaDispenseUnit.setRevisionNumber(Long.valueOf(1));
        vaDispenseUnit.setValue("TUBE");
        vaDispenseUnit.setRequestItemStatus(RequestItemStatus.APPROVED);
        product.setDispenseUnit(vaDispenseUnit);

        Collection<IfcapItemNumberVo> caps = new ArrayList<IfcapItemNumberVo>();
        IfcapItemNumberVo ifCapVo = new IfcapItemNumberVo();
        ifCapVo.setValue("APPLE-RED-FRESH-EATING-BAG");
        product.setIfcapItemNumber(caps);

        // get the vadf for the product
        DataFields<DataField> vadfs = product.getVaDataFields();

        // *********************mandatory VA Data Fields****************
        vadfs.getDataField(FieldKey.CATEGORIES).addStringSelection("MEDICATION");
        vadfs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT).addStringSelection("GM-GRAMS");

        // ****************local only fields*******************
        if (local) {

            product.setLocalUse((ItemStatus.INACTIVE.equals(product.getItemStatus()) ? false : true));

            // specimen type
            SpecimenTypeVo specimen = new SpecimenTypeVo();
            specimen.setValue("Kidney");
            product.setSpecimenType(specimen);

            DrugTextVo drugText = new DrugTextVo();
            drugText.setId(I9991);
            drugText.setValue("DT1");
            List<DrugTextVo> colDrugText = new ArrayList<DrugTextVo>();
            colDrugText.add(drugText);
            product.setLocalDrugTexts(colDrugText);

            LabTestMonitorVo ltm = new LabTestMonitorVo();
            ltm.setValue("Lab Test Monitor 1");
            product.setLabTestMonitor(ltm);

            vadfs.getDataField(FieldKey.ACTION_PROFILE_MESSAGE).selectStringValue("action profile message");
            vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE).addStringSelection("O-OUTPATIENT");
            vadfs.getDataField(FieldKey.CURRENT_INVENTORY).selectStringValue("87");
            vadfs.getDataField(FieldKey.LOCAL_NON_FORMULARY).selectValue(true);
            vadfs.getDataField(FieldKey.NORMAL_AMOUNT_TO_ORDER).selectValue(Long.valueOf(I_88));
            vadfs.getDataField(FieldKey.OP_EXTERNAL_DISPENSE).selectValue(false);
            vadfs.getDataField(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT).selectValue(D_10_2222);
            vadfs.getDataField(FieldKey.REORDER_LEVEL).selectValue(Long.valueOf(I_80));
            vadfs.getDataField(FieldKey.TRANSMIT_TO_CMOP).selectValue(true);
        } else {
            product.setOverrideDfDoseChkExclusn(true);
        }

        // Set the data fields
        product.setVaDataFields(vadfs);

        return product;
    }

    /**
     * Create a Product View Object
     * 
     * @param product The productVo with some default values
     * @param local Indicates if this is a local submission
     * @param oi Indicates whether an OI should be generated as well
     * @param oneDrugClass Indicates whether to add secondardy drug classes.
     * @return ProductVo
     */
    public ProductVo generateProductVo3(ProductVo product, boolean local, boolean oi, boolean oneDrugClass) {

        // get the vadf for the product
        DataFields<DataField> vadfs = product.getVaDataFields();

        // **************************optional non va data fields***************
        product.setGcnSequenceNumber("001111");

        product.setMaxDosePerDay(Long.valueOf(I_6));
        product.setNationalFormularyIndicator(true);

        // national formulary restriction
        product.setLocalSpecialHandling(" ahndling form restriction");

        DataField<Double> duou = vadfs.getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

        if (duou == null) {
            duou = DataField.newInstance(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);
            vadfs.setDataField(duou);
        }

        duou.selectValue(2.0);

        // synonyms
        SynonymVo synonym = new SynonymVo();
        synonym.setSynonymName("ABC");
        synonym.setNdcCode("22222-2222-22");

        // product intended use
        IntendedUseVo intendedUse = new IntendedUseVo();
        intendedUse.setId(I9991);
        intendedUse.setIntendedUse("0-TRADE NAME");
        synonym.setSynonymIntendedUse(intendedUse);

        synonym.setSynonymPricePerDispenseUnit(1.0);
        synonym.setSynonymPricePerOrderUnit(1.0);
        synonym.setSynonymDispenseUnitPerOrderUnit(new Double(1));
        synonym.setSynonymVendor("ABC company");
        synonym.setSynonymVsn("vendor stock number");

        Collection<SynonymVo> colSynonym = new ArrayList<SynonymVo>();
        colSynonym.add(synonym);

        product.setSynonyms(colSynonym);

        product.setLocalPossibleDosages(null);
        product.setNationalPossibleDosages(null);

        // Local Possible Dosages
        LocalPossibleDosagesVo localPossibleDosage = new LocalPossibleDosagesVo();
        localPossibleDosage.setBcmaUnitsPerDose(2.0);
        localPossibleDosage.setLocalPossibleDosage("local possible dosage");
        localPossibleDosage.setOtherLanguageDosageName("ptjer");
        localPossibleDosage.setNumericDose(2.0);

        PossibleDosagesPackageVo possibleDosagesPackage = new PossibleDosagesPackageVo();
        possibleDosagesPackage.setValue(I_INPATIENT);

        Collection<PossibleDosagesPackageVo> colPackage = new ArrayList<PossibleDosagesPackageVo>();
        colPackage.add(possibleDosagesPackage);

        localPossibleDosage.setPossibleDosagePackage(colPackage);

        DoseUnitVo doseUnit = new DoseUnitVo();
        doseUnit.setId("99922");
        doseUnit.setDoseUnitName("CAP");

        localPossibleDosage.setDoseUnit(doseUnit);

        Collection<LocalPossibleDosagesVo> colLocalPossibleDosages = new ArrayList<LocalPossibleDosagesVo>();
        colLocalPossibleDosages.add(localPossibleDosage);

        product.setLocalPossibleDosages(colLocalPossibleDosages);

        // Possible Dosages
        NationalPossibleDosagesVo possibleDosage = new NationalPossibleDosagesVo();
        possibleDosage.setBcmaUnitsPerDose(PPSConstants.F4POINT0);
        possibleDosage.setPossibleDosagesDispenseUnitsPerDose(PPSConstants.F4POINT0);
        possibleDosage.setDose(PPSConstants.F4POINT0);

        PossibleDosagesPackageVo natlPossibleDosagesPackage = new PossibleDosagesPackageVo();
        natlPossibleDosagesPackage.setValue(I_INPATIENT);

        Collection<PossibleDosagesPackageVo> colNatlPackage = new ArrayList<PossibleDosagesPackageVo>();
        colNatlPackage.add(natlPossibleDosagesPackage);
        possibleDosage.setPossibleDosagePackage(colNatlPackage);

        Collection<NationalPossibleDosagesVo> colNationalPossibleDosages = new ArrayList<NationalPossibleDosagesVo>();
        colNationalPossibleDosages.add(possibleDosage);
        product.setNationalPossibleDosages(colNationalPossibleDosages);

        product.setTallManLettering("tAlLmAn");

        // Set the data fields
        product.setVaDataFields(vadfs);

        return product;

    }

    /**
     * Create a Product View Object
     * 
     * @param product The productVo with some default values
     * @param local Indicates if this is a local submission
     * @param oi Indicates whether an OI should be generated as well
     * @param oneDrugClass Indicates whether to add secondardy drug classes.
     * @return ProductVo
     */
    public ProductVo generateProductVo4(ProductVo product, boolean local, boolean oi, boolean oneDrugClass) {

        // get the vadf for the product
        DataFields<DataField> vadfs = product.getVaDataFields();

        // *********optional va data fields*********
        vadfs.getDataField(FieldKey.APPROVED_FOR_SPLITTING).addStringSelection("YES");
        vadfs.getDataField(FieldKey.AR_WS_AMIS_CATEGORY).addStringSelection("3-BLOOD PRODUCT");
        vadfs.getDataField(FieldKey.AR_WS_CONVERSION_NUMBER).selectStringValue("22");
        vadfs.getDataField(FieldKey.DAW_CODE).addStringSelection("1-SUBSTITUTION NOT ALLOWED BY PRESCRIBER");
        vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT).selectStringValue("12D");
        vadfs.getDataField(FieldKey.DEA_SCHEDULE).addSelection("1-Schedule 1 Item");
        vadfs.getDataField(FieldKey.DEFAULT_MAIL_SERVICE).selectStringValue("snail mail");
        vadfs.getDataField(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT).selectStringValue("32");
        vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_ORDER).selectStringValue("dispense limit for order");
        vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE).selectStringValue("disp limit for schedule");
        vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE).selectStringValue("dispense override");
        vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE_REASON).selectStringValue("override reason");

        GroupDataField limit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
        limit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE).selectValue(PPSConstants.L30);
        limit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME).selectValue(PPSConstants.L35);
        vadfs.setDataField(limit);

        vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE).selectStringValue("dispense quantity override");
        vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON).selectStringValue("dispense quantity override reason");

        vadfs.getDataField(FieldKey.DO_NOT_HANDLE_IF_PREGNANT).selectValue(true);
        vadfs.getDataField(FieldKey.DO_NOT_MAIL).selectValue(true);

        vadfs.getDataField(FieldKey.FOLLOW_UP_TIME).selectValue(true);
        vadfs.getDataField(FieldKey.FORMULARY).addStringSelection(PPSConstants.FORMULARY);
        vadfs.getDataField(FieldKey.FSN).selectStringValue("fed stock num");

        vadfs.getDataField(FieldKey.HAZARDOUS_TO_DISPOSE).selectValue(true);
        vadfs.getDataField(FieldKey.HAZARDOUS_TO_HANDLE).selectValue(true);
        vadfs.getDataField(FieldKey.HAZARDOUS_TO_PATIENT).selectValue(true);
        vadfs.getDataField(FieldKey.HIGH_RISK).selectValue(true);
        vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP).selectValue(true);
        vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD).selectStringValue("100 days");

        vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME).selectStringValue("365 days");
        vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME).selectStringValue("90 days");

        // IFCAP Item number
        IfcapItemNumberVo ifcap = new IfcapItemNumberVo();
        ifcap.setValue("45");

        Collection<IfcapItemNumberVo> colIfcap = new ArrayList<IfcapItemNumberVo>();
        colIfcap.add(ifcap);

        product.setIfcapItemNumber(colIfcap);

        // vadfs.getDataField(FieldKey.LAB_MONITOR_MARK).selectValue(true);
        vadfs.getDataField(FieldKey.LONG_TERM_OUT_OF_STOCK).selectValue(true);
        vadfs.getDataField(FieldKey.LOW_SAFETY_MARGIN).selectValue(true);

        vadfs.getDataField(FieldKey.MAX_DISPENSE_LIMIT).selectStringValue("max dispense limit");
        vadfs.getDataField(FieldKey.MESSAGE).selectStringValue("message");
        vadfs.getDataField(FieldKey.MONITOR_MAX_DAYS).selectStringValue("30");

        
        vadfs.getDataField(FieldKey.NDCDP_QUANTITY_MULTIPLIER).selectStringValue("3");
        vadfs.getDataField(FieldKey.NON_RENEWABLE).selectValue(true);
        vadfs.getDataField(FieldKey.OVERRIDE_REASON_ENTERER).selectStringValue("Override reason enterer");

        vadfs.getDataField(FieldKey.PATIENT_SPECIFIC_LABEL).selectValue(true);

        vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(null);
        vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(true);

        vadfs.getDataField(FieldKey.QUANTITY_DISPENSE_MESSAGE).selectStringValue("Dispense 30 pills at a time.");

        vadfs.getDataField(FieldKey.RECALL_LEVEL).addStringSelection("0-NO RECALL");
        vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection("REFRIGERATE");

        // vadfs.getDataField(FieldKey.SERVICE_CODE).selectValue(Long.valueOf(600001));

        vadfs.getDataField(FieldKey.TOTAL_DISPENSE_QUANTITY).selectStringValue("150");

        vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE).selectStringValue("unit dose schedule");
        vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE_TYPE).addStringSelection("P - PRN");

        // vadfs.getDataField(FieldKey.WARNING_LABEL).selectStringValue("warning");
        vadfs.getDataField(FieldKey.WITNESS_TO_ADMINISTRATION).selectValue(true);

        product.setVaDataFields(vadfs);

        return product;
    }
}
