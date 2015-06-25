/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcSourceType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * Generate NdcVo.
 */
public class NdcGenerator extends VoGenerator<NdcVo> {

    private static final String I9991 = "9991";
    private static final String LOTC = PPSConstants.OVER_THE_COUNTER.toLowerCase(Locale.US);
    private static final String BOTC = PPSConstants.OVER_THE_COUNTER;
    
    /**
     * Generate a list of NdcVo
     * 
     * @return List of NdcVo
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<NdcVo> generate() {
        List<NdcVo> ndcs = new ArrayList<NdcVo>();

        ndcs.add(pseudoRandom());
        ndcs.add(pseudoRandom());

        return ndcs;
    }

    /**
     * Generates the upc, upn and ndc number
     * 
     * @return NdcVo
     * @param ndc ndc that is assigned the information
     */
    private NdcVo generateNdcNumbers(NdcVo ndc) {
        String ndcNumber;
        String ndcRand;
        String upc;
        String upcRand;
        String upn;
        Long uniqueNumber1;
        Long uniqueNumber2;

        uniqueNumber1 = new Long(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        uniqueNumber2 = new Long(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        DecimalFormat tenDigits = new DecimalFormat("00000000000");

        ndcRand = tenDigits.format(uniqueNumber1);
        upcRand = tenDigits.format(uniqueNumber2);

        ndcNumber = ndcRand.substring(0, PPSConstants.I5) + "-" 
            + ndcRand.substring(PPSConstants.I5, PPSConstants.I9) 
            + "-" + ndcRand.substring(PPSConstants.I9, PPSConstants.I11);
        
        upc = "UPC-" + upcRand.substring(0, PPSConstants.I5);
        upn = "UPN-" + upcRand.substring(PPSConstants.I5, PPSConstants.I10);

        ndc.setNdc(ndcNumber);
        ndc.setUpcUpn(upc + upn);
        ndc.setNdcPart1(ndcRand.substring(0, PPSConstants.I5));
        ndc.setNdcPart2(ndcRand.substring(PPSConstants.I5, PPSConstants.I9));
        ndc.setNdcPart3(ndcRand.substring(PPSConstants.I9, PPSConstants.I11));

        return ndc;
    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Creates an ndcVo with minimal comment. Does not contain ndcnumber, ndc rand, and upc.
     * 
     * @return NdcVo
     */
    public NdcVo generateMinimalNdc() {
        NdcVo ndc = new NdcVo();
        ndc  = generateNdcNumbers(ndc);
        ndc.setRequestItemStatus(RequestItemStatus.APPROVED);
        ndc.setProduct(new ProductGenerator().getRandom());
        ndc.getProduct().setId(I9991);
        ndc.setSequenceNumber(RandomGenerator.nextLong(PPSConstants.I10));
        ndc.setOrderUnit(new OrderUnitGenerator().getRandom());
        ndc.setSource(NdcSourceType.COTS);
        ndc.setPackageSize(new Double(PPSConstants.I3));
        ndc.setTradeName("Aspirin");

        OtcRxVo otcRxVo = new OtcRxVo();
        otcRxVo.setId(LOTC);
        otcRxVo.setValue(BOTC);
        ndc.setOtcRxIndicator(otcRxVo);     
      
        List<Category> categories = new ArrayList<Category>();
        categories.add(Category.MEDICATION);
        ndc.setCategories(categories);
        
        SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_SINGLE);
        ndc.setSingleMultiSourceProduct(singleMultiSourceProduct);
        
        // UPC/UPN, Vendor, and VSN are all uniqueness fields that therefore are random.
        ndc.setUpcUpn(RandomGenerator.nextNumeric(PPSConstants.I20));
        ndc.setVendor(RandomGenerator.nextAlphabetic(PPSConstants.I30));
        ndc.setVendorStockNumber(RandomGenerator.nextAlphabetic(PPSConstants.I30));

        ManufacturerVo manu = new ManufacturerVo();
        manu.setId(I9991);
        manu.setValue("Test Manufacturer");
        ndc.setManufacturer(manu);
        ndc.setVaDataFields(new DataFields<DataField>());

        DataField<Double> unitPrice = DataField.newInstance(FieldKey.UNIT_PRICE);
        unitPrice.selectValue(PPSConstants.D1POINT1);
        unitPrice.setDataFieldId(new Long(PPSConstants.I42));

        DataField<Double> ndcPricePerDispense = DataField.newInstance(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        ndcPricePerDispense.selectValue(PPSConstants.D1POINT1);
        ndcPricePerDispense.setDataFieldId(new Long(PPSConstants.SIXTYFIVE));

        DataField<Boolean> chemoMed = DataField.newInstance(FieldKey.PATIENT_SPECIFIC_LABEL);
        chemoMed.selectValue(false);
        chemoMed.setEditable(true);
        chemoMed.setDataFieldId(new Long(PPSConstants.I12));

        // ************Optional VA Data Fields*******************


        ndc.getVaDataFields().setDataField(unitPrice);
        ndc.getVaDataFields().setDataField(ndcPricePerDispense);
        ndc.getVaDataFields().setDataField(chemoMed);
        
        DataField<String> prevNdc = DataField.newInstance(FieldKey.PREVIOUS_NDC);
        prevNdc.selectStringValue("12345-3333-22");
        prevNdc.setDataFieldId(new Long(PPSConstants.SIXTYSEVEN));
        ndc.getVaDataFields().setDataField(prevNdc);
        
        DataField<String> prevUPC = DataField.newInstance(FieldKey.PREVIOUS_UPC_UPN);
        prevUPC.selectStringValue("CDEFCDEFCDEFCDEFCDEFCDEFCDEFCDEFCDEFCDEF");
        prevUPC.setDataFieldId(new Long(PPSConstants.SIXTYEIGHT));
        ndc.getVaDataFields().setDataField(prevUPC);
        
        return ndc;
    }

    /**
     * Generate a pseudo-random (not all fields are actually random) NdcVo.
     * 
     * @return NdcVo
     */
    public NdcVo pseudoRandom() {

        NdcVo ndc = generateMinimalNdc();
        ndc = generateNdcNumbers(ndc);

        ndc.setColor(new ColorGenerator().getRandom());
        ndc.setImage(RandomGenerator.nextAlphabetic(PPSConstants.I25));
        ndc.setImprint("J88");
        ndc.setImprint2("BACKSIDE");
        ndc.setItemStatus(ItemStatus.values()[RandomGenerator.nextInt(ItemStatus.values().length)]);
        ndc.setManufacturer(new ManufacturerGenerator().getRandom());
        ndc.setShape(new ShapeGenerator().getRandom());
       
        ndc.setNdcDispUnitsPerOrdUnit(PPSConstants.D1POINT1); 

        ndc.setPackageSize(PPSConstants.D1POINT1); 
        
        ndc.setPackageType(new PackageTypeGenerator().getRandom());
        ndc.setSource(NdcSourceType.COTS);

        
        OtcRxVo otcRxVo = new OtcRxVo();
        otcRxVo.setId(LOTC);
        otcRxVo.setValue(BOTC); 
        
        SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_SINGLE);
        ndc.setSingleMultiSourceProduct(singleMultiSourceProduct);
        
        ndc.setLocalDispense(true);

        ndc.setOrderUnit(new OrderUnitGenerator().getRandom());

        List<Category> list = new ArrayList<Category>();
        list.add(Category.MEDICATION);
        
        ndc.setCategories(list);
        ndc.setNdcIen(String.valueOf(RandomGenerator.nextInt()));
        ndc.setRevisionNumber(RandomGenerator.nextLong());
        ndc.setTradeName("Trade Name");
        
        
        return ndc;
    }

    /**
     * This mehtod is used to generate an NDCVo
     * 
     * @param ndcVo1 An input NDCVo
     * @param local True if a local NDC
     * @param existingNdc A second input NdcVo
     * @return NdcVo
     */
    public NdcVo generateNdcVo(NdcVo ndcVo1, boolean local, NdcVo existingNdc) {
        
        // get the vadf for the ndc
        DataFields<DataField> vadfs = ndcVo1.getVaDataFields();

        // **********Mandatory Fields non VA Data Fields***************

        // ndc and upc
        String number1 = RandomGenerator.nextNumeric(PPSConstants.I5);
        String number2 = RandomGenerator.nextNumeric(PPSConstants.I4);
        String number3 = RandomGenerator.nextNumeric(PPSConstants.I2);
        String number = number1 + '-' + number2 + '-' + number3;
        ndcVo1.setNdc(number);
        ndcVo1.setUpcUpn(number1);

        if (existingNdc != null) {
       
            // product
            ndcVo1.setProduct(existingNdc.getProduct());
        }

        List<Category> list = new ArrayList<Category>();
        list.add(Category.MEDICATION);
        
        ndcVo1.setCategories(list);
        ndcVo1.setNdcIen(RandomGenerator.nextNumeric(PPSConstants.I4));
        ndcVo1.setRevisionNumber(RandomGenerator.nextLong());
        
        // order unit
        OrderUnitVo orderUnitVo1 = new OrderUnitVo();
        orderUnitVo1.setId(I9991);
        orderUnitVo1.setAbbrev("AM");
        ndcVo1.setOrderUnit(orderUnitVo1);

        OtcRxVo otcRxVo = new OtcRxVo();
        otcRxVo.setId(LOTC);
        otcRxVo.setValue(BOTC);
        ndcVo1.setOtcRxIndicator(otcRxVo); 
        
        SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_SINGLE);
        ndcVo1.setSingleMultiSourceProduct(singleMultiSourceProduct);
        
        // *********Mandatory VA Data Fields
        vadfs.getDataField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT).selectValue((double) PPSConstants.I12);

        if (local) {
            vadfs.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT).selectValue((
                    double) PPSConstants.I12); // this is a local only field
        }

      

        if (local) {
            vadfs.getDataField(FieldKey.UNIT_PRICE).selectValue((double) PPSConstants.I12); // this is a local only field
        }

        ndcVo1.setVendor("ABC Company");
        ndcVo1.setVendorStockNumber(RandomGenerator.nextNumeric(PPSConstants.I4));

        if (local) {

            // ***************Local Only Fields*****************
            ndcVo1.setLocalDispense(true);

            vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE).addStringSelection("O-OUTPATIENT");
        }

        // ************Optional NON VA Data Fields*****************
        // color
        ColorVo color = new ColorVo();
        color.setId("31");
        color.setValue("Yellow & Gray");

        ndcVo1.setColor(color);

        ndcVo1.setImprint("LILIES");

        // Manufacturer
        ManufacturerVo manufacturer = new ManufacturerVo();
        manufacturer.setId("999928");
        manufacturer.setValue("3M COMPANY");
        ndcVo1.setManufacturer(manufacturer);

        // NDC Dispense Units per Order Unit
        ndcVo1.setNdcDispUnitsPerOrdUnit(PPSConstants.D1POINT1);

        // OTX/RX indicator
        OtcRxVo rx = new OtcRxVo();
        rx.setId(PPSConstants.PRESCRIPTION.toLowerCase(Locale.US));
        rx.setValue(PPSConstants.PRESCRIPTION);
        ndcVo1.setOtcRxIndicator(rx);

        // Package size
        ndcVo1.setPackageSize(PPSConstants.D1POINT1);

        // Package Type
        PackageTypeVo packageType = new PackageTypeVo();
        packageType.setId("99948");
        packageType.setValue("BAG");

        ndcVo1.setPackageType(packageType);

        // Shape
        ShapeVo shape = new ShapeVo();
        shape.setId("33");
        shape.setValue("U-shape");

        ndcVo1.setShape(shape);

        // Trade Name
        ndcVo1.setTradeName("ZESTRIL");
        
        //NDC Price per Order Unit
        vadfs.getDataField(FieldKey.NDC_PRICE_PER_ORDER_UNIT).selectValue(2.0d);

        // ************Optional VA Data Fields*******************
        vadfs.getDataField(FieldKey.PREVIOUS_NDC).selectStringValue("121212111111");
        vadfs.getDataField(FieldKey.PREVIOUS_UPC_UPN).selectStringValue("PREVIOUSUPC");
        vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(null);
        vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(false);
        vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection("REFRIGERATE");
        vadfs.getDataField(FieldKey.SCORED).addStringSelection("0.25");

        return ndcVo1;
    }
}
