/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;


/**
 * The ProductCsvFile class extends the CsvFile class that contains common csv file
 * code. This class contains code specific to the import and export of Product files.
 * Methods defined in this subclass and used by the Migration service are:
 * 1. openForImport - open file and validate column header line
 * 2. getNextProduct - get next Product from file, returns ProductVo object
 * 3. openForExport - open export and write header line
 * 4. putNextProduct - put next Product to file, input ProductVo object
 * 
 */
public class ProductCsvFile extends CsvFile {

    // column headings stored in first row of csv file
    // if a name is changed, index constant must be updated

    /** PRODUCT_NAME */
    public static final String PRODUCT_NAME = "VaProductName";

    /** ORDER_NAME */
    public static final String ORDER_NAME = "OiName";

    /** PROPOSED_INACT_DATE */
    public static final String PROPOSED_INACT_DATE = "PropInactivationDate";

    /** SYN_MULT */
    public static final String SYN_MULT = "SynonymMultiple";

    /** WIT_REQ */
    public static final String WIT_REQ = "WitnessRequired";

    /** PAT_SPEC */
    public static final String PAT_SPEC = "PatientSpecificLabel";

    /** PROT_LIGHT */
    public static final String PROT_LIGHT = "ProtectFromLight";

    /** PREG */
    public static final String PREG = "DoNotHandlePregnant";

    /** FOLLOWUPTIME */
    public static final String FOLLOWUPTIME = "FollowUpTime";

    /** HAZ_DOSP */
    public static final String HAZ_DOSP = "HazardToDispose";

    /** HAZ_HNDLE */
    public static final String HAZ_HNDLE = "HazardToHandle";

    /** HAZ_PAT */
    public static final String HAZ_PAT = "HazardToPatient";

    /** LTOOS */
    public static final String LTOOS = "LongTermOutOfStock";

    /** LSM */
    public static final String LSM = "LowSafetyMargin";

    /** NR */
    public static final String NR = "NonRenewable";

    /** LabMonitorMark */
    public static final String LMM = "LabMonitorMark";

    /** Formulary */
    public static final String FORM = "Formulary";

    /** NcpdpDispenseUnit */
    public static final String NCPDPDU = "NcpdpDispenseUnit";

    /** NcpdpQtyMultiplier */
    public static final String NCPDPM = "NcpdpQtyMultiplier";

    /** ApprovedForSplitting */
    public static final String AFS = "ApprovedForSplitting";

    /** DoNotMail */
    public static final String DNM = "DoNotMail";

    /** ArWsAmisCategory */
    public static final String AFWSCAT = "ArWsAmisCategory";

    /** ArWsAmisConvertNumber */
    public static final String AFWSNUM = "ArWsAmisConvertNumber";

    /** DawCode */
    public static final String DAW = "DawCode";

    /** DayOrDoseLimit */
    public static final String DDL = "DayOrDoseLimit";

    /** DispDaysSupplyLimit */
    public static final String DDSL = "DispDaysSupplyLimit";

    /** DispLimitForOrder */
    public static final String DLFO = "DispLimitForOrder";

    /** DispLimitForSchedule */
    public static final String DLFS = "DispLimitForSchedule";

    /** DispOverride */
    public static final String DISPOV = "DispOverride";

    /** DispOverrideReason */
    public static final String DISPOVR = "DispOverrideReason";

    /** DispQtyLimitDose */
    public static final String DISPQTYLD = "DispQtyLimitDose";

    /** DispQtyLimitTime */
    public static final String DISPQTYLT = "DispQtyLimitTime";

    /** DispQtyOverride */
    public static final String DISPQTY = "DispQtyOverride";

    /** DispQtyOverrideReason */
    public static final String DOSPQTYOR = "DispQtyOverrideReason";

    /** DispUnitPerOrderUnit */
    public static final String DISPUPOU = "DispUnitPerOrderUnit";

    /** FsnNsn */
    public static final String FSN = "FsnNsn";

    /** HighRiskMed */
    public static final String HIGHRISKMED = "HighRiskMed";

    /** HighRiskFollowup */
    public static final String HR_FOLLOWUP = "HighRiskFollowup";

    /** HighRiskFollowupTime */
    public static final String HR_FOLLOWUPTIME = "HighRiskFollowupTime";

    /** InpatMedExpOrdMaxTime */
    public static final String INPEXPMIN = "InpatMedExpOrdMaxTime";

    /** InpatMedExpOrdMinTime */
    public static final String INPEXPMAX = "InpatMedExpOrdMinTime";

    /** MaxDispenseLimit */
    public static final String MDL = "MaxDispenseLimit";

    /** MaxDosePerDay */
    public static final String MDPD = "MaxDosePerDay";

    /** RxMessage */
    public static final String RXM = "RxMessage";

    /** MonitorMaxDays */
    public static final String MMD = "MonitorMaxDays";

    /** OrderUnit */
    public static final String ORDERUNIT = "OrderUnit";

    /** DispOverrideReasonBy */
    public static final String DIPS_OVERRB = "DispOverrideReasonBy";

    /** QtyDispMessage */
    public static final String QTYDISPM = "QtyDispMessage";

    /** RequestRejectReason */
    public static final String RRS = "RequestRejectReason";

    /** RejectReasonText */
    public static final String RRT = "RejectReasonText";

    /** SecondaryVaDrugClass */
    public static final String SECONDVADC = "SecondaryVaDrugClass";

    /** TotalDispenseQty */
    public static final String TDQ = "TotalDispenseQty";

    /** UnitDoseSchedule */
    public static final String UDS = "UnitDoseSchedule";

    /** UnitDoseScheduleType */
    public static final String UDST = "UnitDoseScheduleType";

    /** Refrigeration */
    public static final String REFRIG = "Refrigeration";

    /** DefaultMailService */
    public static final String DFS = "DefaultMailService";

    /** RecallLevel */
    public static final String RECALL = "RecallLevel";

    /** TallmanLettering */
    public static final String TALL = "TallmanLettering";

    /** DeaSchedule */
    public static final String DEA = "DeaSchedule";

    /** MonitorRoutine */
    public static final String MONROUT = "MonitorRoutine";

    /** NatDrugText */
    public static final String NATDRUG = "NatDrugText";

    /** NumberOfDoses */
    public static final String NUMDOSE = "NumberOfDoses";

    /** PROD_FIELD_NAMES */
    public static final String[] PROD_FIELD_NAMES = {
        PRODUCT_NAME, ORDER_NAME, PROPOSED_INACT_DATE, SYN_MULT, WIT_REQ, PAT_SPEC, PROT_LIGHT, PREG, FOLLOWUPTIME,
        HAZ_DOSP, HAZ_HNDLE, HAZ_PAT, LTOOS, LSM, NR, LMM, FORM, NCPDPDU, NCPDPM, AFS, DNM, AFWSCAT, AFWSNUM, DAW,
        DDL, DDSL, DLFO, DLFS, DISPOV, DISPOVR, DISPQTYLD, DISPQTYLT, DISPQTY, DOSPQTYOR, DISPUPOU, FSN,
        HIGHRISKMED, HR_FOLLOWUP, HR_FOLLOWUPTIME, INPEXPMIN, INPEXPMAX, MDL, MDPD, RXM, MMD, ORDERUNIT,
        DIPS_OVERRB, QTYDISPM, RRS, RRT, SECONDVADC, TDQ, UDS, UDST, REFRIG, DFS, RECALL, TALL, DEA, MONROUT,
        NATDRUG, NUMDOSE };

    // use these constants to index field arrays

    /** PROD_NAME */
    public static final int PROD_NAME = ArrayUtils.indexOf(PROD_FIELD_NAMES, PRODUCT_NAME);

    /** OI_NAME */
    public static final int OI_NAME = ArrayUtils.indexOf(PROD_FIELD_NAMES, ORDER_NAME);

    /** PROP_INACT_DATE */
    public static final int PROP_INACT_DATE = ArrayUtils.indexOf(PROD_FIELD_NAMES, PROPOSED_INACT_DATE);

    /** SYNONYM_MULT */
    public static final int SYNONYM_MULT = ArrayUtils.indexOf(PROD_FIELD_NAMES, SYN_MULT);

    /** WITNESS_REQ */
    public static final int WITNESS_REQ = ArrayUtils.indexOf(PROD_FIELD_NAMES, WIT_REQ);

    /** PAT_SPEC_LABEL */
    public static final int PAT_SPEC_LABEL = ArrayUtils.indexOf(PROD_FIELD_NAMES, PAT_SPEC);

    /** IPROTECT */
    public static final int IPROTECT = ArrayUtils.indexOf(PROD_FIELD_NAMES, PROT_LIGHT);

    /** IPREGNANT */
    public static final int IPREGNANT = ArrayUtils.indexOf(PROD_FIELD_NAMES, PREG);

    /** IFOLLOWUP */
    public static final int IFOLLOWUP = ArrayUtils.indexOf(PROD_FIELD_NAMES, FOLLOWUPTIME);

    /** IHAZARDTODISP */
    public static final int IHAZARDTODISP = ArrayUtils.indexOf(PROD_FIELD_NAMES, HAZ_DOSP);

    /** IHAZARDTOHAND */
    public static final int IHAZARDTOHAND = ArrayUtils.indexOf(PROD_FIELD_NAMES, HAZ_HNDLE);

    /** IHAZARDTOPAT */
    public static final int IHAZARDTOPAT = ArrayUtils.indexOf(PROD_FIELD_NAMES, HAZ_PAT);

    /** ILONGTERM */
    public static final int ILONGTERM = ArrayUtils.indexOf(PROD_FIELD_NAMES, LTOOS);

    /** ILOWMARGIN */
    public static final int ILOWMARGIN = ArrayUtils.indexOf(PROD_FIELD_NAMES, LSM);

    /** INONRENEW */
    public static final int INONRENEW = ArrayUtils.indexOf(PROD_FIELD_NAMES, NR);

    /** ILABMONITOR */
    public static final int ILABMONITOR = ArrayUtils.indexOf(PROD_FIELD_NAMES, LMM);

    /** IFORMULARY */
    public static final int IFORMULARY = ArrayUtils.indexOf(PROD_FIELD_NAMES, FORM);

    /** INCPDPDISP */
    public static final int INCPDPDISP = ArrayUtils.indexOf(PROD_FIELD_NAMES, NCPDPDU);

    /** INCPDPQTYMULT */
    public static final int INCPDPQTYMULT = ArrayUtils.indexOf(PROD_FIELD_NAMES, NCPDPM);

    /** IAPPFORSPLIT */
    public static final int IAPPFORSPLIT = ArrayUtils.indexOf(PROD_FIELD_NAMES, AFS);

    /** IDONOTMAIL */
    public static final int IDONOTMAIL = ArrayUtils.indexOf(PROD_FIELD_NAMES, DNM);

    /** IARWSAMISCAT */
    public static final int IARWSAMISCAT = ArrayUtils.indexOf(PROD_FIELD_NAMES, AFWSCAT);

    /** IARWSAMISNUM */
    public static final int IARWSAMISNUM = ArrayUtils.indexOf(PROD_FIELD_NAMES, AFWSNUM);

    /** IDAWCODE */
    public static final int IDAWCODE = ArrayUtils.indexOf(PROD_FIELD_NAMES, DAW);

    /** IDODLIMIT */
    public static final int IDODLIMIT = ArrayUtils.indexOf(PROD_FIELD_NAMES, DDL);

    /** IDISPSUPPLIMIT */
    public static final int IDISPSUPPLIMIT = ArrayUtils.indexOf(PROD_FIELD_NAMES, DDSL);

    /** IDISPLIMITORD */
    public static final int IDISPLIMITORD = ArrayUtils.indexOf(PROD_FIELD_NAMES, DLFO);

    /** IDISPLIMITSCH */
    public static final int IDISPLIMITSCH = ArrayUtils.indexOf(PROD_FIELD_NAMES, DLFS);

    /** IDISPORIDE */
    public static final int IDISPORIDE = ArrayUtils.indexOf(PROD_FIELD_NAMES, DISPOV);

    /** IDISPORIDEREASON */
    public static final int IDISPORIDEREASON = ArrayUtils.indexOf(PROD_FIELD_NAMES, DISPOVR);

    /** IDISPQTYLIMITDOSE */
    public static final int IDISPQTYLIMITDOSE = ArrayUtils.indexOf(PROD_FIELD_NAMES, DISPQTYLD);

    /** IDISPQTYLIMITTIME */
    public static final int IDISPQTYLIMITTIME = ArrayUtils.indexOf(PROD_FIELD_NAMES, DISPQTYLT);

    /** IDISPQTYORIDE */
    public static final int IDISPQTYORIDE = ArrayUtils.indexOf(PROD_FIELD_NAMES, DISPQTY);

    /** IDISPQTYORIDEREASON */
    public static final int IDISPQTYORIDEREASON = ArrayUtils.indexOf(PROD_FIELD_NAMES, DOSPQTYOR);

    /** IDISPLUNITORDUNIT */
    public static final int IDISPLUNITORDUNIT = ArrayUtils.indexOf(PROD_FIELD_NAMES, DISPUPOU);

    /** IFSNNSN */
    public static final int IFSNNSN = ArrayUtils.indexOf(PROD_FIELD_NAMES, FSN);

    /** IHIRISK */
    public static final int IHIRISK = ArrayUtils.indexOf(PROD_FIELD_NAMES, HIGHRISKMED);

    /** IHIRISKFOLLOWUP */
    public static final int IHIRISKFOLLOWUP = ArrayUtils.indexOf(PROD_FIELD_NAMES, HR_FOLLOWUP);

    /** IHIRISKFUTIME */
    public static final int IHIRISKFUTIME = ArrayUtils.indexOf(PROD_FIELD_NAMES, HR_FOLLOWUPTIME);

    /** IINPMEDEXPMAXTIME */
    public static final int IINPMEDEXPMAXTIME = ArrayUtils.indexOf(PROD_FIELD_NAMES, INPEXPMIN);

    /** IINPMEDEXPMINTIME */
    public static final int IINPMEDEXPMINTIME = ArrayUtils.indexOf(PROD_FIELD_NAMES, INPEXPMAX);

    /** IMAXDISPLIMIT */
    public static final int IMAXDISPLIMIT = ArrayUtils.indexOf(PROD_FIELD_NAMES, MDL);

    /** IMAXDOSDAY */
    public static final int IMAXDOSDAY = ArrayUtils.indexOf(PROD_FIELD_NAMES, MDPD);

    /** IRXMESSAGE */
    public static final int IRXMESSAGE = ArrayUtils.indexOf(PROD_FIELD_NAMES, RXM);

    /** IMONMAXDAYS */
    public static final int IMONMAXDAYS = ArrayUtils.indexOf(PROD_FIELD_NAMES, MMD);

    /** IORDERUNIT */
    public static final int IORDERUNIT = ArrayUtils.indexOf(PROD_FIELD_NAMES, ORDERUNIT);

    /** IDISPORIDEREASONBY */
    public static final int IDISPORIDEREASONBY = ArrayUtils.indexOf(PROD_FIELD_NAMES, DIPS_OVERRB);

    /** IQTYDISPMSG */
    public static final int IQTYDISPMSG = ArrayUtils.indexOf(PROD_FIELD_NAMES, QTYDISPM);

    /** IREQREJREASON */
    public static final int IREQREJREASON = ArrayUtils.indexOf(PROD_FIELD_NAMES, RRS);

    /** IREJREASONTEXT */
    public static final int IREJREASONTEXT = ArrayUtils.indexOf(PROD_FIELD_NAMES, RRT);

    /** ISECONDARYDRUGS */
    public static final int ISECONDARYDRUGS = ArrayUtils.indexOf(PROD_FIELD_NAMES, SECONDVADC);

    /** ITOTALDISPQTY */
    public static final int ITOTALDISPQTY = ArrayUtils.indexOf(PROD_FIELD_NAMES, TDQ);

    /** IUNITDOSESCH */
    public static final int IUNITDOSESCH = ArrayUtils.indexOf(PROD_FIELD_NAMES, UDS);

    /** IUNITDOSESCHTYPE */
    public static final int IUNITDOSESCHTYPE = ArrayUtils.indexOf(PROD_FIELD_NAMES, UDST);

    /** IREFRIG */
    public static final int IREFRIG = ArrayUtils.indexOf(PROD_FIELD_NAMES, REFRIG);

    /** IDEFMAILSERV */
    public static final int IDEFMAILSERV = ArrayUtils.indexOf(PROD_FIELD_NAMES, DFS);

    /** IRECALLLEVEL */
    public static final int IRECALLLEVEL = ArrayUtils.indexOf(PROD_FIELD_NAMES, RECALL);

    /** ITMANLETTER */
    public static final int ITMANLETTER = ArrayUtils.indexOf(PROD_FIELD_NAMES, TALL);

    /** IDEASCH */
    public static final int IDEASCH = ArrayUtils.indexOf(PROD_FIELD_NAMES, DEA);

    /** IMONROUTINE */
    public static final int IMONROUTINE = ArrayUtils.indexOf(PROD_FIELD_NAMES, MONROUT);

    /** IDRUGTEXT */
    public static final int IDRUGTEXT = ArrayUtils.indexOf(PROD_FIELD_NAMES, NATDRUG);

    /** INOOFDOSES */
    public static final int INOOFDOSES = ArrayUtils.indexOf(PROD_FIELD_NAMES, NUMDOSE);


    // -------------------------------------------------------------------------
    // subclass method to open Product file for import and validate the header row
    // -------------------------------------------------------------------------

    /**
     * Description
     *
     * @param filename 
     * @throws MigrationException 
     */
    public void openForImport(String filename) throws MigrationException {

        // open file and create string array with column headings
        String[] headerArray = this.openAndReadHeader(filename);

        // compare with a valid Product header
        if (!Arrays.equals(headerArray, PROD_FIELD_NAMES)) {
            throw new MigrationException("Column header on line 1 is invalid. ");
        }
    }

    /**
     * Description
     *
     * @param pFile 
     * @throws MigrationException 
     */
    public void openForImport(File pFile) throws MigrationException {

        // open file and create string array with column headings
        String[] headerArray = this.openAndReadHeader(pFile);

        // compare with a valid Product header
        if (!Arrays.equals(headerArray, PROD_FIELD_NAMES)) {
            throw new MigrationException("Column header on line 1 is invalid!");
        }

    }

    /**
     * Description
     *
     * @param pInputStream 
     * @throws MigrationException 
     */
    public void openForImport(InputStream pInputStream) throws MigrationException {

        // open file and create string array with column headings
        String[] headerArray = this.openAndReadHeader(pInputStream);

        // compare with a valid Product header
        if (!Arrays.equals(headerArray, PROD_FIELD_NAMES)) {
            throw new MigrationException("Column header on line 1 is invalid.");
        }

    }

    /**
     * Description
     *
     * @param productVo ProductVo
     * @return ProductVo  
     * @throws MigrationException exception
     */
    public ProductVo getNextProduct(ProductVo productVo) throws MigrationException {
        ProductVo prodVo = productVo;
        
        // call superclass member to read next row
        String[] prodFields = this.getNextRow(PROD_FIELD_NAMES.length);

        if (prodFields == null) {
            return null;
        }

        // put file fields into prodVo object and data fields
        // convert fields if required but do not validate
        // service will call validators on returned oiVo

        //get data fields from blank Vo
        DataFields<DataField> vadfs = prodVo.getVaDataFields();
        
        prodVo = getNextProduct1(prodVo, prodFields, vadfs);
        prodVo = getNextProduct2(prodVo, prodFields, vadfs);
        prodVo = getNextProduct3(prodVo, prodFields, vadfs);
        prodVo = getNextProduct4(prodVo, prodFields, vadfs);
        
        return prodVo;
        
    }
    
    /**
     * getNextProduct1
     * @param prodVo prodVo
     * @param prodFields prodFields
     * @param vadfs vadfs
     * @return ProductVo
     * @throws MigrationException MigrationException
     */
    private ProductVo getNextProduct1(ProductVo prodVo, String[] prodFields, DataFields<DataField> vadfs) 
        throws MigrationException {

        //initialize Vo data fields (not contained in file)
        prodVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        //VaProductName field (required)
        //store string in prodVo variable
        prodVo.setVaProductName(prodFields[PROD_NAME]);

        OrderableItemVo oiVo = new OrderableItemVo();
        oiVo.setOiName(prodFields[OI_NAME]);
        prodVo.setParent(oiVo);


        //PropInactivationDate field (optional)
        //store date in prodVo variable
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (prodFields[PROP_INACT_DATE].equals("")) {
            proposedInactivationDate.selectValue(null);
        } else {
            try {
                Date piDate = df.parse(prodFields[PROP_INACT_DATE]);
                proposedInactivationDate.selectValue(piDate);
            } catch (ParseException ex) {
                throw new MigrationException(DATE_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[PROP_INACT_DATE], prodFields[PROP_INACT_DATE]);
            }
        }

        prodVo.getVaDataFields().setDataField(proposedInactivationDate);

        //SynonymMultiple field 
        //store synonyms composed of (name,ndcode,vendor,vsn,use,orderunit,orderunit$,dispunit,dispunit$)
        if (prodFields[SYNONYM_MULT].length() > 0) {
            Collection<SynonymVo> synonyms = new ArrayList<SynonymVo>();

            // split multiple field into ingredient strings
            String[] synMultiple = prodFields[SYNONYM_MULT].split(FIELD_MULTIPLE_SEPARATOR_SPLIT);

            //iterate on each synonym string containing 9 values
            for (String oneSyn : synMultiple) {
                SynonymVo synonym = new SynonymVo();

                // split into synonym values, must be nine
                String[] synValues = oneSyn.split(MULTIPLE_VALUE_SEPARATOR_SPLIT);

                if (synValues.length == PPSConstants.I9) {

                    // set the name, unit, and strength
                    synonym.setSynonymName(synValues[0]);
                    synonym.setNdcCode(synValues[1]);
                    synonym.setSynonymVendor(synValues[2]);
                    synonym.setSynonymVsn(synValues[PPSConstants.I3]);
                    IntendedUseVo intendedUse = new IntendedUseVo();
                    intendedUse.setIntendedUse(synValues[PPSConstants.I4]);
                    synonym.setSynonymIntendedUse(intendedUse);
                    OrderUnitVo orderUnit = new OrderUnitVo();
                    orderUnit.setAbbrev(synValues[PPSConstants.I5]);
                    synonym.setSynonymOrderUnit(orderUnit);

                    try {
                        Double price = Double.parseDouble(synValues[PPSConstants.I6]);
                        synonym.setSynonymPricePerOrderUnit(price);
                        price = Double.parseDouble(synValues[PPSConstants.I7]);
                        synonym.setSynonymDispenseUnitPerOrderUnit(price);
                        price = Double.parseDouble(synValues[PPSConstants.I8]);
                        synonym.setSynonymPricePerDispenseUnit(price);
                        synonyms.add(synonym);
                    } catch (NumberFormatException ex) {
                        throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber),
                            prodFields[PROD_NAME], PROD_FIELD_NAMES[SYNONYM_MULT], oneSyn);
                    }
                } else {
                    throw new MigrationException(MULTIPLE_ERROR_MESSAGE, Integer.toString(fileRowNumber),
                        prodFields[PROD_NAME], PROD_FIELD_NAMES[SYNONYM_MULT], oneSyn);
                }
            }

            prodVo.setSynonyms(synonyms);
        }

        //WitnessRequired field - store Y/N value in boolean data field
        DataField<Boolean> witness = vadfs.getDataField(FieldKey.WITNESS_TO_ADMINISTRATION);
        this.fromFieldArrayBoolean(witness, prodFields, WITNESS_REQ, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(witness);

        //PatientSpecificLabel field - store Y/N value in boolean data field
        DataField<Boolean> pslabel = vadfs.getDataField(FieldKey.PATIENT_SPECIFIC_LABEL);
        this.fromFieldArrayBoolean(pslabel, prodFields, PAT_SPEC_LABEL, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(pslabel);

        //ProtectFromLight field - use Y/N string to set boolean data field
        DataField<Boolean> protect = vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT);
        this.fromFieldArrayBoolean(protect, prodFields, IPROTECT, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(protect);

        //DoNotHandleIfPregnant field - use Y/N string to set boolean data field
        DataField<Boolean> pregnant = vadfs.getDataField(FieldKey.DO_NOT_HANDLE_IF_PREGNANT);
        this.fromFieldArrayBoolean(pregnant, prodFields, IPREGNANT, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(pregnant);

        //FollowUpTime field - use Y/N string to set boolean data field
        DataField<Boolean> followup = vadfs.getDataField(FieldKey.FOLLOW_UP_TIME);
        this.fromFieldArrayBoolean(followup, prodFields, IFOLLOWUP, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(followup);

        //HazardousToDispose field - use Y/N string to set boolean data field
        DataField<Boolean> hazardDisp = vadfs.getDataField(FieldKey.HAZARDOUS_TO_DISPOSE);
        this.fromFieldArrayBoolean(hazardDisp, prodFields, IHAZARDTODISP, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(hazardDisp);

        //HazardousToHandle field - use Y/N string to set boolean data field
        DataField<Boolean> hazardHand = vadfs.getDataField(FieldKey.HAZARDOUS_TO_HANDLE);
        this.fromFieldArrayBoolean(hazardHand, prodFields, IHAZARDTOHAND, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(hazardHand);

        //HazardousToPatient field - use Y/N string to set boolean data field
        DataField<Boolean> hazardPat = vadfs.getDataField(FieldKey.HAZARDOUS_TO_PATIENT);
        this.fromFieldArrayBoolean(hazardPat, prodFields, IHAZARDTOPAT, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(hazardPat);

        //LongTermOutOfStock field - use Y/N string to set boolean data field
        DataField<Boolean> longterm = vadfs.getDataField(FieldKey.LONG_TERM_OUT_OF_STOCK);
        this.fromFieldArrayBoolean(longterm, prodFields, ILONGTERM, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(longterm);

        //LowSafetyMargin field - use Y/N string to set boolean data field
        DataField<Boolean> lowmargin = vadfs.getDataField(FieldKey.LOW_SAFETY_MARGIN);
        this.fromFieldArrayBoolean(lowmargin, prodFields, ILOWMARGIN, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(lowmargin);

        //NonRenewable field - use Y/N string to set boolean data field
        DataField<Boolean> nonrenew = vadfs.getDataField(FieldKey.NON_RENEWABLE);
        this.fromFieldArrayBoolean(nonrenew, prodFields, INONRENEW, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(nonrenew);

        //LabMonitorMark field - use Y/N string to set boolean data field
        DataField<Boolean> labmonitor = vadfs.getDataField(FieldKey.LAB_MONITOR_MARK);
        this.fromFieldArrayBoolean(labmonitor, prodFields, ILABMONITOR, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(labmonitor);

        return prodVo;
    }
    
    /**
     * getNextProduct2
     * @param prodVo prodVo
     * @param prodFields prodFields
     * @param vadfs vadfs
     * @return ProductVo
     * @throws MigrationException MigrationException
     */
    private ProductVo getNextProduct2(ProductVo prodVo, String[] prodFields, DataFields<DataField> vadfs) 
        throws MigrationException {
        
        //Formulary field - store string in data field, single selection list
        if (!prodFields[IFORMULARY].isEmpty()) {
            ListDataField<String> formulary = vadfs.getDataField(FieldKey.FORMULARY);
            formulary.addStringSelection(prodFields[IFORMULARY]);
            prodVo.getVaDataFields().setDataField(formulary);
        }

        //NcpcpDispenseUnit field - store string in list data field, not a multiple
        if (!prodFields[INCPDPDISP].isEmpty()) {
            ListDataField<String> ndu = vadfs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT);
            ndu.removeSelection("EA-EACH"); // remove default
            ndu.addStringSelection(prodFields[INCPDPDISP]);
            prodVo.getVaDataFields().setDataField(ndu);
        }

        //NcpcpQtyMultiplier field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.NDCDP_QUANTITY_MULTIPLIER), prodVo, prodFields, INCPDPDISP,
            INCPDPQTYMULT);

        //ApprovedForSplitting field - store string in list data field, not a multiple
        if (!prodFields[IAPPFORSPLIT].isEmpty()) {
            ListDataField<String> split = vadfs.getDataField(FieldKey.APPROVED_FOR_SPLITTING);

            if (prodFields[IAPPFORSPLIT].equalsIgnoreCase("Y")) {
                split.addStringSelection(PPSConstants.YES);
            } else if (prodFields[IAPPFORSPLIT].equalsIgnoreCase("N")) {
                split.addStringSelection(PPSConstants.NO);
            } else {
                throw new MigrationException("Approved For Splitting needs to by 'Y' or 'N'", Integer.toString(fileRowNumber),
                    prodFields[PROD_NAME], PROD_FIELD_NAMES[IAPPFORSPLIT], prodFields[IAPPFORSPLIT]);
            }

            prodVo.getVaDataFields().setDataField(split);
        }

        //DoNotMail field - use Y/N string to set boolean data field
        DataField<Boolean> dontmail = vadfs.getDataField(FieldKey.DO_NOT_MAIL);
        this.fromFieldArrayBoolean(dontmail, prodFields, IDONOTMAIL, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(dontmail);

        //ArWsAmisCategory field - store string in list data field, not a multiple
        if (!prodFields[IARWSAMISCAT].isEmpty()) {
            ListDataField<String> category = vadfs.getDataField(FieldKey.AR_WS_AMIS_CATEGORY);
            category.addStringSelection(prodFields[IARWSAMISCAT]);
            prodVo.getVaDataFields().setDataField(category);
        }

        //ArWsAmisConvertNumber field - store string in long data field
        if (!prodFields[IARWSAMISNUM].isEmpty()) {
            DataField<Long> cnum = vadfs.getDataField(FieldKey.AR_WS_CONVERSION_NUMBER);

            try {
                Long.parseLong(prodFields[IARWSAMISNUM]);
                cnum.selectStringValue(prodFields[IARWSAMISNUM]);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[IARWSAMISNUM], prodFields[IARWSAMISNUM]);
            }

            prodVo.getVaDataFields().setDataField(cnum);
        }

        //DawCode field - store string in list data field, not a multiple
        if (!prodFields[IDAWCODE].isEmpty()) {
            ListDataField<String> dcode = vadfs.getDataField(FieldKey.DAW_CODE);
            dcode.removeSelection("0-NO PRODUCT SELECTION INDICATED"); // remove default
            dcode.addStringSelection(prodFields[IDAWCODE]);
            prodVo.getVaDataFields().setDataField(dcode);
        }

        //DaysOrDoseLimit field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT), prodVo, prodFields, IDODLIMIT, IDODLIMIT);

        //DispDaysSupplyLimit field - store string in long data field
        if (!prodFields[IDISPSUPPLIMIT].isEmpty()) {
            DataField<Long> slimit = vadfs.getDataField(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);

            try {
                Long.parseLong(prodFields[IDISPSUPPLIMIT]);
                slimit.selectStringValue(prodFields[IDISPSUPPLIMIT]);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[IDISPSUPPLIMIT], prodFields[IDISPSUPPLIMIT]);
            }

            prodVo.getVaDataFields().setDataField(slimit);
        }

        //DispLimitForOrder - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_ORDER), prodVo, prodFields, IDISPLIMITORD,
            IDISPLIMITORD);

        //DispLimitForSchedule - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE), prodVo, prodFields, IDISPLIMITSCH,
            IDISPLIMITSCH);

        //DispOverride - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE), prodVo, prodFields, IDISPORIDE, IDISPORIDE);

        //DispOverrideReason field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE_REASON), prodVo, prodFields, IDISPORIDEREASON,
            IDISPORIDEREASON);

        //DispenseQtyLimitDose field - store string in long grouped data field
        if (!prodFields[IDISPQTYLIMITDOSE].isEmpty()) {
            GroupDataField dqlimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
            DataField<Long> dqlimitdose = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE);

            try {
                Long.parseLong(prodFields[IDISPQTYLIMITDOSE]);
                dqlimitdose.selectStringValue(prodFields[IDISPQTYLIMITDOSE]);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[IDISPQTYLIMITDOSE], prodFields[IDISPQTYLIMITDOSE]);
            }

            prodVo.getVaDataFields().setDataField(dqlimit);
        }

        //DispenseQtyLimitTime field - store string in long grouped data field
        if (!prodFields[IDISPQTYLIMITTIME].isEmpty()) {
            GroupDataField dqlimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
            DataField<Long> dqlimittime = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME);

            try {
                Long.parseLong(prodFields[IDISPQTYLIMITTIME]);
                dqlimittime.selectStringValue(prodFields[IDISPQTYLIMITTIME]);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[IDISPQTYLIMITTIME], prodFields[IDISPQTYLIMITTIME]);
            }

            prodVo.getVaDataFields().setDataField(dqlimit);
        }

        //DispQtyOverride - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE), prodVo, prodFields, IDISPQTYORIDE,
            IDISPQTYORIDE);

        //DispQtyOverrideReason field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON), prodVo, prodFields,
            IDISPQTYORIDEREASON, IDISPQTYORIDEREASON);
        
        return prodVo;
    }

    /**
     * getNextProduct3
     * @param prodVo prodVo
     * @param prodFields prodFields
     * @param vadfs vadfs
     * @return ProductVo
     * @throws MigrationException MigrationException
     */
    private ProductVo getNextProduct3(ProductVo prodVo, String[] prodFields, DataFields<DataField> vadfs) 
        throws MigrationException {
        
        //DispenseUnitsPerOrderUnit field - store string in double data field
        if (!prodFields[IDISPLUNITORDUNIT].isEmpty()) {
            DataField<Double> dunits = vadfs.getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

            try {
                Double.parseDouble(prodFields[IDISPLUNITORDUNIT]);
                dunits.selectStringValue(prodFields[IDISPLUNITORDUNIT]);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[IDISPLUNITORDUNIT], prodFields[IDISPLUNITORDUNIT]);
            }

            prodVo.getVaDataFields().setDataField(dunits);
        }

        //FsnNsn field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.FSN), prodVo, prodFields, IFSNNSN, IFSNNSN);

        //HighRiskMed field - use Y/N string to set boolean data field
        DataField<Boolean> hirisk = vadfs.getDataField(FieldKey.HIGH_RISK);
        this.fromFieldArrayBoolean(hirisk, prodFields, IHIRISK, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(hirisk);

        //HighRiskFollowup field - use Y/N string to set boolean data field
        DataField<Boolean> hiriskfu = vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP);
        this.fromFieldArrayBoolean(hiriskfu, prodFields, IHIRISKFOLLOWUP, PROD_NAME, PROD_FIELD_NAMES);
        prodVo.getVaDataFields().setDataField(hiriskfu);

        //HighRiskFollowupTime - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD), prodVo, prodFields, IHIRISKFUTIME,
            IHIRISKFUTIME);

        //InpatMedExpOrdMaxTime field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME), prodVo, prodFields,
            IINPMEDEXPMAXTIME, IINPMEDEXPMAXTIME);

        //InpatMedExpOrdMinTime field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME), prodVo, prodFields,
            IINPMEDEXPMINTIME, IINPMEDEXPMINTIME);

        //MaxDispenseLimit field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.MAX_DISPENSE_LIMIT), prodVo, prodFields, IMAXDISPLIMIT, IMAXDISPLIMIT);

        //MaxDosePerDay field - store string in long Vo variable
        if (!prodFields[IMAXDOSDAY].isEmpty()) {
            try {
                Long maxdose = Long.parseLong(prodFields[IMAXDOSDAY]);
                prodVo.setMaxDosePerDay(maxdose);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[IMAXDOSDAY], prodFields[IMAXDOSDAY]);
            }
        }

        //RxMessage field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.MESSAGE), prodVo, prodFields, IRXMESSAGE, IRXMESSAGE);

        //MonitorMaxDays field - store string in long data field
        if (!prodFields[IMONMAXDAYS].isEmpty()) {
            DataField<Long> mmdays = vadfs.getDataField(FieldKey.MONITOR_MAX_DAYS);

            try {
                Long.parseLong(prodFields[IMONMAXDAYS]);
                mmdays.selectStringValue(prodFields[IMONMAXDAYS]);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[IMONMAXDAYS], prodFields[IMONMAXDAYS]);
            }

            prodVo.getVaDataFields().setDataField(mmdays);
        }

        //OrderUnit field - store string in orderUnitVo abbrev string variable
        if (!prodFields[IORDERUNIT].isEmpty()) {
            OrderUnitVo orderUnit = new OrderUnitVo();

            //    only abbrev is set, service completes vo as needed 
            orderUnit.setAbbrev(prodFields[IORDERUNIT]);
            prodVo.setOrderUnit(orderUnit);
        }

        //DispOverrideReasonBy field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.OVERRIDE_REASON_ENTERER), prodVo, prodFields, IDISPORIDEREASONBY,
            IDISPORIDEREASONBY);

        //QtyDispMesssage field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.QUANTITY_DISPENSE_MESSAGE), prodVo, prodFields, IQTYDISPMSG,
            IQTYDISPMSG);

        //RequestRejectReason field - compare string to enum values; if found set Vo enum variable
        if (!prodFields[IREQREJREASON].isEmpty()) {
            Boolean found = false;

            for (RequestRejectionReason reason : RequestRejectionReason.values()) {
                if (prodFields[IREQREJREASON].equals(reason.toString())) {
                    prodVo.setRequestRejectionReason(reason);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new MigrationException("For Product " + prodVo.getVaProductName() + ", " + LIST_ERROR_MESSAGE,
                    Integer.toString(fileRowNumber), prodFields[PROD_NAME], PROD_FIELD_NAMES[IREQREJREASON],
                    prodFields[IREQREJREASON]);
            }
        }
        
        return prodVo;
    }

    /**
     * getNextProduct4
     * @param prodVo prodVo
     * @param prodFields prodFields
     * @param vadfs vadfs
     * @return ProductVo
     * @throws MigrationException MigrationException
     */
    private ProductVo getNextProduct4(ProductVo prodVo, String[] prodFields, DataFields<DataField> vadfs) 
        throws MigrationException {
        
        //RejectReasonText field - use string to set Vo string variable
        if (!prodFields[IREJREASONTEXT].isEmpty()) {
            prodVo.setRejectionReasonText(prodFields[IREJREASONTEXT]);
        }

        //SecondaryVaDrugClass - store drugs composed of (name,code)in prodVo list variable
        Collection<DrugClassGroupVo> drugs = new ArrayList<DrugClassGroupVo>();

        // split multiple field into drugs strings
        String[] drugMultiple = prodFields[ISECONDARYDRUGS].split(FIELD_MULTIPLE_SEPARATOR_SPLIT);

        //iterate on each drug string containing name,code
        for (String oneDrug : drugMultiple) {
            if (StringUtils.isBlank(oneDrug)) {
                break;
            }

            DrugClassGroupVo drug = new DrugClassGroupVo();
            DrugClassVo drugClass = new DrugClassVo();

            // split into drug values, must be two
            String[] drugValues = oneDrug.split(MULTIPLE_VALUE_SEPARATOR_SPLIT);

            if (drugValues.length == 2) {

                // set the class and code and primary flag
                drugClass.setClassification(drugValues[0]);
                drugClass.setCode(drugValues[1]);
                drug.setDrugClass(drugClass);
                drug.setPrimary(false);
                drugs.add(drug);
            } else {
                throw new MigrationException(MULTIPLE_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[ISECONDARYDRUGS], oneDrug);
            }
        }

        prodVo.setDrugClasses(drugs);

        //TotalDispenseQty field
        fromFieldArrayString(vadfs.getDataField(FieldKey.TOTAL_DISPENSE_QUANTITY), prodVo, prodFields, ITOTALDISPQTY,
            ITOTALDISPQTY);

        //UnitDoseSchedule field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE), prodVo, prodFields, IUNITDOSESCH, IUNITDOSESCH);

        //UnitDoseScheduleType field - store string in list data field
        if (!prodFields[IUNITDOSESCHTYPE].isEmpty()) {
            ListDataField<String> udstype = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE_TYPE);
            udstype.addStringSelection(prodFields[IUNITDOSESCHTYPE]);
            prodVo.getVaDataFields().setDataField(udstype);
        }

        // Refrigeration field
        if (!prodFields[IREFRIG].isEmpty()) {
            if (prodFields[IREFRIG].equals("Y")) {
                vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection(PPSConstants.REFRIGERATE);
            } else if (prodFields[IREFRIG].equals("N")) {
                vadfs.getDataField(FieldKey.REFRIGERATION).addStringSelection(PPSConstants.DO_NOT_REFRIGERATE);
            }
        }

        //DefaultMailService field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.DEFAULT_MAIL_SERVICE), prodVo, prodFields, IDEFMAILSERV, IDEFMAILSERV);

        //RecallLevel field - store string in list data field, single selection
        if (!prodFields[IRECALLLEVEL].isEmpty()) {
            ListDataField<String> rlevel = vadfs.getDataField(FieldKey.RECALL_LEVEL);
            rlevel.addStringSelection(prodFields[IRECALLLEVEL]);
            prodVo.getVaDataFields().setDataField(rlevel);
        }

        //TallmanLettering field - use string to set Vo string variable
        if (!prodFields[ITMANLETTER].isEmpty()) {
            prodVo.setTallManLettering(prodFields[ITMANLETTER]);
        }

        //DeaSchedule field - store string in list data field, single selection
        if (!prodFields[IDEASCH].isEmpty()) {
            ListDataField<String> dsch = vadfs.getDataField(FieldKey.DEA_SCHEDULE);
            dsch.addStringSelection(prodFields[IDEASCH]);
            prodVo.getVaDataFields().setDataField(dsch);
        }

        //MonitorRoutine field - store string in string data field
        fromFieldArrayString(vadfs.getDataField(FieldKey.MONITOR_ROUTINE), prodVo, prodFields, IMONROUTINE, IMONROUTINE);

        //Drug Text field (optional) - use string list to create a DrugTextVo array
        if (!prodFields[IDRUGTEXT].isEmpty()) {
            String[] dTextArray = prodFields[IDRUGTEXT].split(FIELD_MULTIPLE_SEPARATOR_SPLIT);
            List<DrugTextVo> drugTexts = new ArrayList<DrugTextVo>();

            for (String selection : dTextArray) {
                DrugTextVo drugText = new DrugTextVo();
                drugText.setValue(selection);
                drugTexts.add(drugText);
            }

            prodVo.setNationalDrugTexts(drugTexts);
        }

        //NumberOfDoses field - store string in long data field
        if (!prodFields[INOOFDOSES].isEmpty()) {
            DataField<Long> nodoses = vadfs.getDataField(FieldKey.NUMBER_OF_DOSES);

            try {
                Long.parseLong(prodFields[INOOFDOSES]);
                nodoses.selectStringValue(prodFields[INOOFDOSES]);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), prodFields[PROD_NAME],
                    PROD_FIELD_NAMES[INOOFDOSES], prodFields[INOOFDOSES]);
            }

            prodVo.getVaDataFields().setDataField(nodoses);
        }

        return prodVo;

    } //end getNextProduct    

    /**
     * Description
     *
     * @param filename 
     * @throws MigrationException 
     */
    public void openForExport(String filename) throws MigrationException {

        // subclass method to open a Product file for export and write header row
        this.openAndWriteHeader(filename, PROD_FIELD_NAMES);

    }

    /**
     * Description
     *
     * @param prodVo ProductVo
     * @throws MigrationException exception 
     */
    public void putNextProduct(ProductVo prodVo) throws MigrationException {

        // subclass method to add next Product row to file from data stored in Vo
        String[] prodFieldArray = new String[PROD_FIELD_NAMES.length];
        Arrays.fill(prodFieldArray, "");

        //get the vo data fields
        DataFields<DataField> vadfs = prodVo.getVaDataFields();

        //pull data from vo and data fields to fill the prodFieldArray
        //VaProductName field
        prodFieldArray[PROD_NAME] = prodVo.getVaProductName();
        prodFieldArray[OI_NAME] = prodVo.getOrderableItem().getOiName();

        //ProposedInactivationDate field
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (proposedInactivationDate.getValue() != null) {
            prodFieldArray[PROP_INACT_DATE] = df.format(proposedInactivationDate.getValue());
        }

        //SynonymMultiple field 
        //each synonym composed of (name,ndcode,vendor,vsn,use,orderunit,orderunit$,dispunit,dispunit$)
        String drugStr = "";
        Collection<SynonymVo> synonyms = prodVo.getSynonyms();

        if (synonyms != null) {

            // iterate for each synonym in list
            for (SynonymVo synonym : synonyms) {
                if (!drugStr.isEmpty()) {
                    drugStr = drugStr.concat(FIELD_MULTIPLE_SEPARATOR);
                }

                drugStr = drugStr.concat(synonym.getSynonymName());
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr = drugStr.concat(synonym.getNdcCode());
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr = conStr(synonym.getSynonymVendor(), drugStr, null);
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr = conStr(synonym.getSynonymVendor(), drugStr, synonym.getSynonymVsn());
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr = drugStr.concat(synonym.getSynonymIntendedUse().getValue());
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr = conStr(synonym.getSynonymOrderUnit(), drugStr, synonym.getSynonymOrderUnit().getAbbrev());
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr =
                    conStr(synonym.getSynonymPricePerOrderUnit(), drugStr, synonym.getSynonymPricePerOrderUnit().toString());
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr =
                    conStr(synonym.getSynonymDispenseUnitPerOrderUnit(), drugStr, synonym.getSynonymDispenseUnitPerOrderUnit()
                        .toString());
                drugStr = drugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                drugStr =
                    conStr(synonym.getSynonymPricePerDispenseUnit(), drugStr, synonym.getSynonymPricePerDispenseUnit()
                        .toString());
            }

            prodFieldArray[SYNONYM_MULT] = drugStr;
        }

        //WitnessRequired field
        DataField<Boolean> witness = vadfs.getDataField(FieldKey.WITNESS_TO_ADMINISTRATION);
        this.toFieldArrayBoolean(witness, prodFieldArray, WITNESS_REQ);

        //PatientSpecificLabel field
        DataField<Boolean> pslabel = vadfs.getDataField(FieldKey.PATIENT_SPECIFIC_LABEL);
        this.toFieldArrayBoolean(pslabel, prodFieldArray, PAT_SPEC_LABEL);

        //ProtectFromLight field
        DataField<Boolean> protect = vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT);
        this.toFieldArrayBoolean(protect, prodFieldArray, IPROTECT);

        //DoNotHandleIfPregnant field
        DataField<Boolean> pregnant = vadfs.getDataField(FieldKey.DO_NOT_HANDLE_IF_PREGNANT);
        this.toFieldArrayBoolean(pregnant, prodFieldArray, IPREGNANT);

        //FollowUpTime field
        DataField<Boolean> followup = vadfs.getDataField(FieldKey.FOLLOW_UP_TIME);
        this.toFieldArrayBoolean(followup, prodFieldArray, IFOLLOWUP);

        //HazardousToDispose field
        DataField<Boolean> hazardDisp = vadfs.getDataField(FieldKey.HAZARDOUS_TO_DISPOSE);
        this.toFieldArrayBoolean(hazardDisp, prodFieldArray, IHAZARDTODISP);

        //HazardousToHandle field
        DataField<Boolean> hazardHand = vadfs.getDataField(FieldKey.HAZARDOUS_TO_HANDLE);
        this.toFieldArrayBoolean(hazardHand, prodFieldArray, IHAZARDTOHAND);

        //HazardousToPatient field
        DataField<Boolean> hazardPat = vadfs.getDataField(FieldKey.HAZARDOUS_TO_PATIENT);
        this.toFieldArrayBoolean(hazardPat, prodFieldArray, IHAZARDTOPAT);

        //LongTermOutOfStock field
        DataField<Boolean> longterm = vadfs.getDataField(FieldKey.LONG_TERM_OUT_OF_STOCK);
        this.toFieldArrayBoolean(longterm, prodFieldArray, ILONGTERM);

        //LowSafetyMargin field
        DataField<Boolean> lowmargin = vadfs.getDataField(FieldKey.LOW_SAFETY_MARGIN);
        this.toFieldArrayBoolean(lowmargin, prodFieldArray, ILOWMARGIN);

        //NonRenewable field
        DataField<Boolean> nonrenew = vadfs.getDataField(FieldKey.NON_RENEWABLE);
        this.toFieldArrayBoolean(nonrenew, prodFieldArray, INONRENEW);

        //LabMonitorMark field
        DataField<Boolean> labmonitor = vadfs.getDataField(FieldKey.LAB_MONITOR_MARK);
        this.toFieldArrayBoolean(labmonitor, prodFieldArray, ILABMONITOR);

        //Formulary field - assumes a single selection
        ListDataField<String> formulary = vadfs.getDataField(FieldKey.FORMULARY);

        if (formulary != null && formulary.getValue() != null && formulary.getValue().size() > 0) {
            prodFieldArray[IFORMULARY] = formulary.getValue().get(0);
        }

        //NcpcpDispenseUnit field - assumes a single selection
        ListDataField<String> ndu = vadfs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT);

        if (ndu != null && ndu.getValue() != null && ndu.getValue().size() > 0) {
            prodFieldArray[INCPDPDISP] = ndu.getValue().get(0);
        }

        //NcpcpQtyMultiplier field
        DataField<String> nqm = vadfs.getDataField(FieldKey.NDCDP_QUANTITY_MULTIPLIER);
        this.toFieldArrayString(nqm, prodFieldArray, INCPDPQTYMULT);

        //ApprovedForSplitting field - assumes a single selection
        ListDataField<String> approvedForSplit = vadfs.getDataField(FieldKey.APPROVED_FOR_SPLITTING);

        if (approvedForSplit != null && !approvedForSplit.getValue().isEmpty()) {
            if ("YES".equalsIgnoreCase(approvedForSplit.getValue().get(0))) {
                prodFieldArray[IAPPFORSPLIT] = "Y";
            } else {
                prodFieldArray[IAPPFORSPLIT] = "N";
            }
        }

        //DoNotMail field
        DataField<Boolean> dontmail = vadfs.getDataField(FieldKey.DO_NOT_MAIL);
        this.toFieldArrayBoolean(dontmail, prodFieldArray, IDONOTMAIL);

        //ArWsAmisCategory field - assumes a single selection
        ListDataField<String> category = vadfs.getDataField(FieldKey.AR_WS_AMIS_CATEGORY);

        if (category != null && category.getValue() != null && category.getValue().size() > 0) {
            prodFieldArray[IARWSAMISCAT] = category.getValue().get(0);
        }

        //ArWsAmisConvertNumber field
        DataField<Long> cnum = vadfs.getDataField(FieldKey.AR_WS_CONVERSION_NUMBER);
        toFieldArrayNumber(cnum, prodFieldArray, IARWSAMISNUM);

        //DawCode field - assumes a single selection
        ListDataField<String> dcode = vadfs.getDataField(FieldKey.DAW_CODE);

        if (dcode != null && dcode.getValue() != null && dcode.getValue().size() > 0) {
            prodFieldArray[IDAWCODE] = dcode.getValue().get(0);
        }

        //DaysOrDoseLimit field
        DataField<String> dlimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
        this.toFieldArrayString(dlimit, prodFieldArray, IDODLIMIT);

        //DispDaysSupplyLimit field
        DataField<Long> slimit = vadfs.getDataField(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);
        toFieldArrayNumber(slimit, prodFieldArray, IDISPSUPPLIMIT);

        //DispLimitForOrder - store string in string data field
        DataField<String> dlord = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_ORDER);
        this.toFieldArrayString(dlord, prodFieldArray, IDISPLIMITORD);

        //DispLimitForSchedule
        DataField<String> dlsch = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE);
        this.toFieldArrayString(dlsch, prodFieldArray, IDISPLIMITSCH);

        //DispOverride
        DataField<String> doride = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE);
        this.toFieldArrayString(doride, prodFieldArray, IDISPORIDE);

        //DispOverrideReason
        DataField<String> doreason = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE_REASON);
        this.toFieldArrayString(doreason, prodFieldArray, IDISPORIDEREASON);

        //DispenseQtyLimitDose field
        GroupDataField dqlimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);
        DataField<Long> dqlimitdose = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE);
        toFieldArrayNumber(dqlimitdose, prodFieldArray, IDISPQTYLIMITDOSE);

        //DispenseQtyLimitTime field
        DataField<Long> dqlimittime = dqlimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME);
        toFieldArrayNumber(dqlimittime, prodFieldArray, IDISPQTYLIMITTIME);

        //DispQtyOverride
        DataField<String> dqoride = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE);
        this.toFieldArrayString(dqoride, prodFieldArray, IDISPQTYORIDE);

        //DispQtyOverrideReason
        DataField<String> dqoreason = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON);
        this.toFieldArrayString(dqoreason, prodFieldArray, IDISPQTYORIDEREASON);

        //DispenseUnitsPerOrderUnit field
        DataField<Double> dunits = vadfs.getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);
        toFieldArrayNumber(dunits, prodFieldArray, IDISPLUNITORDUNIT);

        //FsnNsn field
        DataField<String> fsn = vadfs.getDataField(FieldKey.FSN);
        this.toFieldArrayString(fsn, prodFieldArray, IFSNNSN);

        //HighRiskMed field
        DataField<Boolean> hirisk = vadfs.getDataField(FieldKey.HIGH_RISK);
        this.toFieldArrayBoolean(hirisk, prodFieldArray, IHIRISK);

        //HighRiskFollowup field
        DataField<Boolean> hiriskfu = vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP);
        this.toFieldArrayBoolean(hiriskfu, prodFieldArray, IHIRISKFOLLOWUP);

        //HighRiskFollowupTime field
        DataField<String> hirisktime = vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD);
        this.toFieldArrayString(hirisktime, prodFieldArray, IHIRISKFUTIME);

        //InpatMedExpOrdMaxTime field
        DataField<String> maxtime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME);
        this.toFieldArrayString(maxtime, prodFieldArray, IINPMEDEXPMAXTIME);

        //InpatMedExpOrdMinTime field
        DataField<String> mintime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME);
        this.toFieldArrayString(mintime, prodFieldArray, IINPMEDEXPMINTIME);

        //MaxDispenseLimit field
        DataField<String> mdlimit = vadfs.getDataField(FieldKey.MAX_DISPENSE_LIMIT);
        this.toFieldArrayString(mdlimit, prodFieldArray, IMAXDISPLIMIT);

        //MaxDosePerDay field
        if (prodVo.getMaxDosePerDay() != null) {
            prodFieldArray[IMAXDOSDAY] = prodVo.getMaxDosePerDay().toString();
        }

        //RxMessage field
        DataField<String> rxmsg = vadfs.getDataField(FieldKey.MESSAGE);
        this.toFieldArrayString(rxmsg, prodFieldArray, IRXMESSAGE);

        //MonitorMaxDays field
        DataField<Long> mmdays = vadfs.getDataField(FieldKey.MONITOR_MAX_DAYS);
        toFieldArrayNumber(mmdays, prodFieldArray, IMONMAXDAYS);

        //OrderUnit field
        if (prodVo.getOrderUnit() != null && prodVo.getOrderUnit().getAbbrev() != null) {
            prodFieldArray[IORDERUNIT] = prodVo.getOrderUnit().getAbbrev();
        }

        //DispOverrideReasonBy field
        DataField<String> dorby = vadfs.getDataField(FieldKey.OVERRIDE_REASON_ENTERER);
        this.toFieldArrayString(dorby, prodFieldArray, IDISPORIDEREASONBY);

        //QtyDispMesssage field
        DataField<String> qdmsg = vadfs.getDataField(FieldKey.QUANTITY_DISPENSE_MESSAGE);
        this.toFieldArrayString(qdmsg, prodFieldArray, IQTYDISPMSG);

        //RequestRejectReason field
        if (prodVo.getRequestRejectionReason() != null && !prodVo.getRequestRejectionReason().toString().isEmpty()) {
            prodFieldArray[IREQREJREASON] = prodVo.getRequestRejectionReason().toString();
        }

        //RejectReasonText field
        if (prodVo.getRejectionReasonText() != null && !prodVo.getRejectionReasonText().isEmpty()) {
            prodFieldArray[IREJREASONTEXT] = prodVo.getRejectionReasonText();
        }

        //SecondaryVaDrugClass multiple field
        String sdrugStr = "";
        Collection<DrugClassGroupVo> drugs = prodVo.getDrugClasses();

        // iterate for each drug in list        
        for (DrugClassGroupVo drug : drugs) {

            //ignore the primary drug
            if (!drug.getPrimary()) {
                if (!sdrugStr.isEmpty()) {
                    sdrugStr = sdrugStr.concat(FIELD_MULTIPLE_SEPARATOR);
                }

                sdrugStr = sdrugStr.concat(drug.getDrugClass().getClassification());
                sdrugStr = sdrugStr.concat(MULTIPLE_VALUE_SEPARATOR);
                sdrugStr = sdrugStr.concat(drug.getDrugClass().getCode());
            }
        }

        prodFieldArray[ISECONDARYDRUGS] = sdrugStr;

        //TotalDispenseQty field
        DataField<String> tdqty = vadfs.getDataField(FieldKey.TOTAL_DISPENSE_QUANTITY);
        this.toFieldArrayString(tdqty, prodFieldArray, ITOTALDISPQTY);

        //UnitDoseSchedule field
        DataField<String> udsch = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE);
        this.toFieldArrayString(udsch, prodFieldArray, IUNITDOSESCH);

        //UnitDoseScheduleType field - assumes a single selection
        ListDataField<String> udstype = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE_TYPE);

        if (udstype != null && udstype.getValue() != null && !udstype.getValue().isEmpty()) {
            prodFieldArray[IUNITDOSESCHTYPE] = udstype.getValue().get(0);
        }

        //Refrigeration field
        ListDataField<String> refrigerate = vadfs.getDataField(FieldKey.REFRIGERATION);

        if (refrigerate.getValue() != null) {
            if (refrigerate.contains("REFRIGERATE")) {
                prodFieldArray[IREFRIG] = "Y";
            }

            if (refrigerate.contains("DO NOT REFRIGERATE")) {
                prodFieldArray[IREFRIG] = "N";
            }
        }

        //DefaultMailService field
        DataField<String> dmserv = vadfs.getDataField(FieldKey.DEFAULT_MAIL_SERVICE);
        this.toFieldArrayString(dmserv, prodFieldArray, IDEFMAILSERV);

        //RecallLevel field
        ListDataField<String> rlevel = vadfs.getDataField(FieldKey.RECALL_LEVEL);

        if (rlevel != null && rlevel.getValue() != null && !rlevel.getValue().isEmpty()) {
            prodFieldArray[IRECALLLEVEL] = rlevel.getValue().get(0);
        }

        //TallmanLettering field
        if (prodVo.getTallManLettering() != null) {
            prodFieldArray[ITMANLETTER] = prodVo.getTallManLettering();
        }

        //DeaSchedule field
        ListDataField<String> dsch = vadfs.getDataField(FieldKey.DEA_SCHEDULE);

        if (dsch != null && dsch.getValue() != null && !dsch.getValue().isEmpty()) {
            prodFieldArray[IDEASCH] = dsch.getValue().get(0);
        }

        //MonitorRoutine field
        DataField<String> mroutine = vadfs.getDataField(FieldKey.MONITOR_ROUTINE);
        this.toFieldArrayString(mroutine, prodFieldArray, IMONROUTINE);

        //Drug Text multiple field (optional)
        if (prodVo.getNationalDrugTexts() != null && !prodVo.getNationalDrugTexts().isEmpty()) {
            prodFieldArray[IDRUGTEXT] = "";
            List<DrugTextVo> dTextArray = prodVo.getNationalDrugTexts();

            for (DrugTextVo drugText : dTextArray) {
                if (!prodFieldArray[IDRUGTEXT].isEmpty()) {
                    prodFieldArray[IDRUGTEXT] = prodFieldArray[IDRUGTEXT].concat(FIELD_MULTIPLE_SEPARATOR);
                }

                prodFieldArray[IDRUGTEXT] = prodFieldArray[IDRUGTEXT].concat(drugText.getValue());
            }
        }

        //NumberOfDoses field
        DataField<Long> nodoses = vadfs.getDataField(FieldKey.NUMBER_OF_DOSES);
        toFieldArrayNumber(nodoses, prodFieldArray, INOOFDOSES);

        // write row to product file
        this.putNextRow(prodFieldArray);

    } //end putNextProduct
} //end ProductCsvFile
