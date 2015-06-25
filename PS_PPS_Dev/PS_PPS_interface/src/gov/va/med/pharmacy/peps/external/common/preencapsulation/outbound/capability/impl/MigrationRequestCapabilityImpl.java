/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl;


import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ExcludeDosageCheck;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationResponseVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesAutoCreate;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductPackage;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.MigrationRequestCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.MigrationRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.ActiveIngredients;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.DispenseUnitsPerDose;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.DosageFormFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.DrugIngredientsFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.DrugMigrationResponse;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.DrugUnitsFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.EffectiveDateTime;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.Units;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.VaDispenseUnitFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.VaDrugClassFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.VaGenericNameFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.migration.data.response.VaProductFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.data.request.DrugMigrationRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.migration.data.request.Request;
import gov.va.med.vistalink.rpc.RpcResponse;





/**
 * Sends updates to VistaLink
 */
public class MigrationRequestCapabilityImpl implements
        MigrationRequestCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(MigrationRequestCapabilityImpl.class);

    private static String RPC_NAME = "PPS NDFMS MIGR";
    private static String RPC_CONTEXT = "XOBV VISTALINK TESTER";
    private static String PARSE_DATE = "Error parsing date";
    private static String INACT_DATE = "Inactivation Date";
    private static String NO0OR1 = "Field does not contain a '0' or a '1'";
    private static String MASTER_VUID = "Master Entry For VUID";
    private static String MIGRATION = "Migration";
    private static String INPAT = "I-Inpatient";
    private static String OUTPAT = "O-Outpatient";
    private static String EFF_DATE = "Effective Date";
    private static String PACKAGE = "Package";
    
    private VistaLinkConnectionUtility vistaLinkConnectionUtility;
    private EnvironmentUtility environmentUtility;
    private DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);

    /**
     * Constructor
     */
    public MigrationRequestCapabilityImpl() {
        super();
        
        if (environmentUtility != null) {
            environmentUtility.isLocal();
        }
    }

    /**
     * Handle the request
     * 
     * @param file Number of VA File
     * @param ienStart Start number for the IEN
     * @param recordCount number of records to return
     * @param activeState 0 -3
     * @return MigrationResponseVo
     */
    public MigrationResponseVo migrationRequest(String file, String ienStart,
            int recordCount, int activeState) {

        // create the request object

        // The IEN passed in is = to the highest IEN from before so we need to
        // increment it one here.
        int it = Integer.parseInt(ienStart);
        it++;
        String realIENStart = String.valueOf(it);

        DrugMigrationRequest drugMigrationRequest = new DrugMigrationRequest();
        Request request = new Request();
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
   
        try {
            BigInteger ienStartBigInt = new BigInteger(realIENStart);
            BigInteger recordCountBigInt = new BigInteger(
                    String.valueOf(recordCount));
            BigInteger activeStateBigInt = new BigInteger(
                    String.valueOf(activeState));

            // Load the object
            request.setNdfmsFile(file);
            request.setStartingIen(ienStartBigInt);
            request.setNumElements(recordCountBigInt);
            request.setType(activeStateBigInt);

            // Now set the Request in the drug Migration Request
            drugMigrationRequest.setRequest(request);

            // Marshal the migration request into an XML
            String xmlRequest = MigrationRequestDocument.instance().marshal(
                    drugMigrationRequest);

            // Debugging log send the XML string to the log
            LOG.debug("Request sent to VistA for  " + file + " IEN: "
                    + realIENStart + " record count: " + recordCount
                    + " activeState: " + activeState);

            // now send the request XML via VistALink
            List<String> params = new LinkedList<String>();
            params.add(xmlRequest);
            RpcResponse response = null;
     
            try {
            
                // Send the request and get the response
                response = vistaLinkConnectionUtility.sendRequest(RPC_CONTEXT,
                        RPC_NAME, params, null);
            } catch (Exception e) {
                LOG.error("Error sending vista request: " + e.getMessage());
        
                return null;
            }

            String xmlResponse = response.getResults();
      
            LOG.debug("Response received: " + xmlResponse);
     
            DrugMigrationResponse drugMigrationResponse = null;
 
            try {
            
                // Send the request and get the response
                // Unmarshal the VistA XML response to a drugMigration object
                drugMigrationResponse = gov.va.med.pharmacy.peps.external.common.preencapsulation.
                    inbound.document.MigrationResponseDocument.instance().unmarshal(xmlResponse);
            } catch (Exception e) {
                LOG.error("Error parsing vista response: " + e.getMessage());
                
                return null;
            }

            // Select the correct migration response process
            if (file.equals(PPSConstants.VA_DRUG_UNIT_FILE)) {
                migrationResponseVo = processDrugUnits(drugMigrationResponse);
            } else if (file.equals(PPSConstants.VA_DOSAGE_FORM_FILE)) {
                migrationResponseVo = processDosageForms(drugMigrationResponse);
            } else if (file.equals(PPSConstants.VA_DRUG_CLASS_FILE)) {
                migrationResponseVo = processClasses(drugMigrationResponse);
            } else if (file.equals(PPSConstants.VA_DISPENSE_UNIT_FILE)) {
                migrationResponseVo = processDispenseUnits(drugMigrationResponse);
            } else if (file.equals(PPSConstants.VA_DRUG_INGREDIENT_FILE)) {
                migrationResponseVo = processIngredients(drugMigrationResponse);
            } else if (file.equals(PPSConstants.VA_PRODUCT_FILE)) {
                migrationResponseVo = processProducts(drugMigrationResponse);
            } else if (file.equals(PPSConstants.VA_GENERIC_NAME_FILE)) { 
                migrationResponseVo = processGenericNames(drugMigrationResponse);
            }
        } catch (NumberFormatException e) {
            LOG.error("Error converting IEN: " + ienStart
                    + " to a number.  Error is " + e.getMessage());
     
            return null;
        }

        // Return the drugMigrationResponse object
        return migrationResponseVo;
    }

    /**
     * Process Drug Unit 50.607
     * 
     * @param drugMigrationResponse drugMigrationResponse
     * @return MigrationResponseVo
     */
    private MigrationResponseVo processDrugUnits(
            DrugMigrationResponse drugMigrationResponse) {
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
        List<ManagedItemVo> managedItemsVos = new ArrayList<ManagedItemVo>();
        List<MigrationException> migrationExceptions = new ArrayList<MigrationException>();
        boolean addRecord;

        // set the End Of File flag when the re migration response has an end of file.
        if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("0")) {
            migrationResponseVo.setEof(false);
        } else if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("1")) {
            migrationResponseVo.setEof(true);
        }

        for (DrugUnitsFieldType type : drugMigrationResponse.getDrugUnits()) {
        
            // set the add record flag to true
            addRecord = true;

            // Create the VO
            DrugUnitVo vo = new DrugUnitVo();

            vo.setDrugUnitIen(type.getDrugUnitsIen());

            // Map the name
            vo.setValue(type.getName());

            // Get the date and format it
            if (StringUtils.isBlank(type.getInactivationDate())) {
                vo.setInactivationDate(null);
                vo.setItemStatus(ItemStatus.ACTIVE);
            } else {
                try {
                    
                    // set the item status to inactive
                    vo.setItemStatus(ItemStatus.INACTIVE);
                    Date inactiveDate = df.parse(type.getInactivationDate());
                    vo.setInactivationDate(inactiveDate);
                } catch (ParseException ex) {
                    MigrationException migrationException = new MigrationException(
                            PARSE_DATE, type.getDrugUnitsIen(),
                            type.getName(), INACT_DATE,
                            type.getInactivationDate());
                    migrationExceptions.add(migrationException);

                    // Don't add this record
                    addRecord = false;
                }
            }

            // Add the file to the list of MigrationResponseVo's if there are no errors
            if (addRecord) {
                managedItemsVos.add(vo);
            }

            // Now put the two lists into the migrationResponseVo
            migrationResponseVo.setManagedItemsVos(managedItemsVos);
            migrationResponseVo.setMigrationExceptions(migrationExceptions);
        }

        // Log the object after the conversion to a VO for debugging
        LOG.debug("Drug Unit Response to VistA: " + drugMigrationResponse);

        return (migrationResponseVo);
    }

    /**
     * Process Dispense Unit 50.64
     * 
     * @param drugMigrationResponse drugMigrationResponse
     * @return MigrationResponseVo
     */
    private MigrationResponseVo processDispenseUnits(
            DrugMigrationResponse drugMigrationResponse) {
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
        List<ManagedItemVo> managedItemsVos = new ArrayList<ManagedItemVo>();
        List<MigrationException> migrationExceptions = new ArrayList<MigrationException>();
        boolean addRecord;

        // set the End Of File flag
        if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("0")) {
            migrationResponseVo.setEof(false);
        } else if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("1")) {
            migrationResponseVo.setEof(true);
        }

        for (VaDispenseUnitFieldType type : drugMigrationResponse
                .getVaDispenseUnit()) {
      
            // set the add record flag to true
            addRecord = true;

            // Create the VO
            DispenseUnitVo vo = new DispenseUnitVo();

            // set the IEN
            vo.setDispenseUnitIen(type.getDispenseUnitsIen());

            // Map the name
            vo.setValue(type.getName());

            // Get the date and format it
            if (StringUtils.isBlank(type.getInactivationDate())) {
                vo.setInactivationDate(null);
                vo.setItemStatus(ItemStatus.ACTIVE);
            } else {
                try {
                    vo.setItemStatus(ItemStatus.INACTIVE);
                    Date inactiveDate = df.parse(type.getInactivationDate());
                    vo.setInactivationDate(inactiveDate);
                } catch (ParseException ex) {
                    MigrationException migrationException = new MigrationException(
                            PARSE_DATE, type.getDispenseUnitsIen(),
                            type.getName(), INACT_DATE,
                            type.getInactivationDate());
                    migrationExceptions.add(migrationException);

                    // Don't add this record
                    addRecord = false;
                }
            }

            // Add the file to the list of MigrationResponseVos if there are no errors with this request.
            if (addRecord) {
                managedItemsVos.add(vo);
            }

            // Now put the two lists into this MigrationResponseVo to return to the user.
            migrationResponseVo.setManagedItemsVos(managedItemsVos);
            migrationResponseVo.setMigrationExceptions(migrationExceptions);
        }

        // Log the object after the conversion to a VO for debugging
        LOG.debug("Dipsense Unit Response to VistA: " + drugMigrationResponse);

        return (migrationResponseVo);
    }

    /**
     * Process Ingredients 50.416
     * 
     * @param drugMigrationResponse drugMigrationResponse
     * @return MigrationResponseVo
     */
    private MigrationResponseVo processIngredients(
            DrugMigrationResponse drugMigrationResponse) {
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
        List<ManagedItemVo> managedItemsVos = new ArrayList<ManagedItemVo>();
        List<MigrationException> migrationExceptions = new ArrayList<MigrationException>();
        List<VuidStatusHistoryVo> effectiveDateTimes;
        boolean addRecord;

        // set the End Of File flag when appropriate
        if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("0")) {
            migrationResponseVo.setEof(false);
        } else if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("1")) {
            migrationResponseVo.setEof(true);
        }

        for (DrugIngredientsFieldsType type : drugMigrationResponse
                .getDrugIngredients()) {

            // set the add record flag to true
            addRecord = true;
            IngredientVo vo = new IngredientVo();

            if (type.getMasterEntryForVuid().equals("1")) {
                vo.setMasterEntryForVuid(true);
            } else if (type.getMasterEntryForVuid().equals("0")) {
                vo.setMasterEntryForVuid(false);
            } else {
                MigrationException migrationException = new MigrationException(
                        NO0OR1,
                        type.getDrugIngredientsIen(), type.getName(),
                        MASTER_VUID, type.getMasterEntryForVuid());

                migrationExceptions.add(migrationException);

                addRecord = false;
            }

            if (addRecord) {

                vo.setIngredientIen(type.getDrugIngredientsIen());
                vo.setValue(type.getName());

                if (type.getInactivationDate() == null
                        || type.getInactivationDate().equals("")) {
                    vo.setInactivationDate(null);
                    vo.setItemStatus(ItemStatus.ACTIVE);
                } else {
                    try {
                        vo.setItemStatus(ItemStatus.INACTIVE);
                        Date inactiveDate = df
                                .parse(type.getInactivationDate());
                        vo.setInactivationDate(inactiveDate);
                    } catch (ParseException ex) {
                        MigrationException migrationException = new MigrationException(
                                PARSE_DATE,
                                type.getDrugIngredientsIen(), type.getName(),
                                INACT_DATE, type.getInactivationDate());

                        migrationExceptions.add(migrationException);

                        // Don't add this record
                        addRecord = false;
                    }
                }
          
                if (addRecord) {

                    if (type.getPrimaryIngredient() == null
                            || type.getPrimaryIngredient().equals("")) {
                        vo.setPrimaryIngredient(null);
                    } else {
          
                        IngredientVo pvo = new IngredientVo();
                        pvo.setValue(type.getPrimaryIngredient());
                        vo.setPrimaryIngredient(pvo);
                    }

                    vo.setVuid(type.getVuid());
                    effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();

                    // process the effective date multiples for processIngredients.
                    for (EffectiveDateTime effectiveDate : type
                            .getEffectiveDateTime()) {
                        try {
                            VuidStatusHistoryVo effectiveDateTime = new VuidStatusHistoryVo();
         
                            // Format the date
                            Date multiEffectiveDate = df.parse(effectiveDate
                                    .getEffectiveDateTime());

                            // Set the date in the effetiveDate object for processIngredients.
                            effectiveDateTime
                                    .setEffectiveDateTime(multiEffectiveDate);
                            effectiveDateTime.setVuid(Long.valueOf(type
                                    .getVuid()));
                            effectiveDateTime.setCreatedBy(MIGRATION);
                            effectiveDateTime.setCreatedDate(new Date());
       
                            // Set active state in the Effective Date object for processingredeints.
                            if ("1".equals(effectiveDate.getStatus())) {
                                effectiveDateTime.setEffectiveDtmStatus(ItemStatus.ACTIVE);
                            } else {
                                effectiveDateTime.setEffectiveDtmStatus(ItemStatus.INACTIVE);
                            }

                            // add the effective date object to the Effective
                            // Data List Object in the Ingredients VO
                            effectiveDateTimes.add(effectiveDateTime);
                        } catch (ParseException ex) {
                            if (addRecord) {
                                MigrationException migrationException = new MigrationException(
                                        PARSE_DATE,
                                        type.getDrugIngredientsIen(),
                                        type.getName(), INACT_DATE,
                                        type.getInactivationDate());

                                migrationExceptions.add(migrationException);

                                // Don't add this record for processIngredients
                                addRecord = false;
                                break;
                            }
                        }
                    }

                    // add the effective date time list to the VO for processIngredients
                    vo.setEffectiveDates(effectiveDateTimes);
                }

                // Add the file to the list of VO's if there are no errors
                if (addRecord) {
                    managedItemsVos.add(vo);
                }
            }

            // Now put the two lists into the response VO for processIngredients
            migrationResponseVo.setManagedItemsVos(managedItemsVos);
            migrationResponseVo.setMigrationExceptions(migrationExceptions);
        }

        // Log the object after the conversion to a VO for debugging for processIngredients
        LOG.debug("Ingredient Response to VistA: " + drugMigrationResponse);

        return (migrationResponseVo);
    }

    /**
     * Process Generic Name 50.6
     * 
     * @param drugMigrationResponse drugMigrationResponse
     * @return MigrationResponseVo
     */
    private MigrationResponseVo processGenericNames(
            DrugMigrationResponse drugMigrationResponse) {
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
        List<ManagedItemVo> managedItemsVos = new ArrayList<ManagedItemVo>();
        List<MigrationException> migrationExceptions = new ArrayList<MigrationException>();
        List<VuidStatusHistoryVo> effectiveDateTimes;
        boolean addRecord;

        // set the End Of File flag
        if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("0")) {
            migrationResponseVo.setEof(false);
        } else if (drugMigrationResponse.getResponseHeader().getEndOfFile()
                .equals("1")) {
            migrationResponseVo.setEof(true);
        }

        for (VaGenericNameFieldsType type : drugMigrationResponse
                .getVaGenericName()) {
     
            // set the add record flag to true
            addRecord = true;

            // Create the VO
            GenericNameVo vo = new GenericNameVo();

            // Set the Master Entry For Vuid
            if (type.getMasterEntryForVuid().equals("1")) {
                vo.setMasterEntryForVuid(true);
            } else if (type.getMasterEntryForVuid().equals("0")) {
                vo.setMasterEntryForVuid(false);
            } else {
                MigrationException migrationException = new MigrationException(
                        NO0OR1,
                        type.getVaGenericIen(), type.getName(),
                        MASTER_VUID, type.getMasterEntryForVuid());

                migrationExceptions.add(migrationException);

                // Don't add this record
                addRecord = false;
            }

            if (addRecord) {

                // set the IEN
                vo.setGenericIen(type.getVaGenericIen());

                // Map the name
                vo.setValue(type.getName());

                // Get the date and format it
                if (StringUtils.isBlank(type.getInactivationDate())) {
                    vo.setInactivationDate(null);
                    vo.setItemStatus(ItemStatus.ACTIVE);
                } else {
                    try {
                        vo.setItemStatus(ItemStatus.INACTIVE);
                        Date inactiveDate = df
                                .parse(type.getInactivationDate());
                        vo.setInactivationDate(inactiveDate);
                    } catch (ParseException ex) {
                        MigrationException migrationException = new MigrationException(
                                PARSE_DATE,
                                type.getVaGenericIen(), type.getName(),
                                INACT_DATE, type.getInactivationDate());

                        migrationExceptions.add(migrationException);

                        // Don't add this record
                        addRecord = false;
                    }// end catch for inactive date
                }

                // Set the VUID
                vo.setVuid(type.getVuid());

                // set the effectiveDateTimes list
                effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();

                // process the effective date multiples
                for (EffectiveDateTime effectiveDate : type
                        .getEffectiveDateTime()) {
                    try {
                        VuidStatusHistoryVo effectiveDateTime = new VuidStatusHistoryVo();
         
                        // Format the date
                        Date multiEffectiveDate = df.parse(effectiveDate
                                .getEffectiveDateTime());

                        // Set the date in the effetiveDate object
                        effectiveDateTime
                                .setEffectiveDateTime(multiEffectiveDate);
                        effectiveDateTime.setVuid(Long.valueOf(type.getVuid()));
                        effectiveDateTime.setCreatedBy(MIGRATION);
                        effectiveDateTime.setCreatedDate(new Date());
                 
                        // Set active state in the Effective Date object
                        if ("1".equals(effectiveDate.getStatus())) {
                            effectiveDateTime.setEffectiveDtmStatus(ItemStatus.ACTIVE);
                        } else {
                            effectiveDateTime.setEffectiveDtmStatus(ItemStatus.INACTIVE);
                        }

                        // add the effective date object to the Effective Data
                        // List Object in the Generic Name VO
                        effectiveDateTimes.add(effectiveDateTime);
                    } catch (ParseException ex) {
                        if (addRecord) {
                            MigrationException migrationException = new MigrationException(
                                    PARSE_DATE,
                                    type.getVaGenericIen(), type.getName(),
                                    EFF_DATE, "", EFF_DATE,
                                    effectiveDate.getEffectiveDateTime());

                            migrationExceptions.add(migrationException);

                            // Don't add this record
                            addRecord = false;
                            break;
                        }
                    }// end catch for effective date time
                }
          
                // add the effective date time list to the VO for Generic Name
                vo.setEffectiveDates(effectiveDateTimes);

                // Add the file to the list of VO's if there are no errors for Generic Name
                if (addRecord) {
                    managedItemsVos.add(vo);
                }
            }

            // Now put the two lists into the response GenericNameVo.
            migrationResponseVo.setManagedItemsVos(managedItemsVos);
            migrationResponseVo.setMigrationExceptions(migrationExceptions);
        }

        // Log the object after the conversion to a VO for debugging
        LOG.debug("Generic Name Response to VistA: " + drugMigrationResponse);

        return (migrationResponseVo);
    }

    /**
     * Process Drug Class 50.605
     * 
     * @param drugMigrationResponse drugMigrationResponse
     * @return MigrationResponseVo
     */
    private MigrationResponseVo processClasses(
            DrugMigrationResponse drugMigrationResponse) {
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
        List<ManagedItemVo> managedItemsVos = new ArrayList<ManagedItemVo>();
        List<MigrationException> migrationExceptions = new ArrayList<MigrationException>();
        List<VuidStatusHistoryVo> effectiveDateTimes;
        boolean addRecord;

        // set the End Of File flag for the drugClass process
        if (drugMigrationResponse.getResponseHeader().getEndOfFile().equals("0")) {
            migrationResponseVo.setEof(false);
        } else if (drugMigrationResponse.getResponseHeader().getEndOfFile().equals("1")) {
            migrationResponseVo.setEof(true);
        }

        for (VaDrugClassFieldsType type : drugMigrationResponse.getVaDrugClass()) {
       
            // set the add record flag to true
            addRecord = true;

            // Create the VO
            DrugClassVo vo = new DrugClassVo();

            // Set the Master Entry For Vuid
            if (type.getMasterEntryForVuid().equals("1")) {
                vo.setMasterEntryForVuid(true);
            } else if (type.getMasterEntryForVuid().equals("0")) {
                vo.setMasterEntryForVuid(false);
            } else {
                MigrationException migrationException =
                    new MigrationException(NO0OR1, type.getVaDrugClassIen(), (type.getCode() + type.getClassification()),
                        MASTER_VUID, type.getMasterEntryForVuid());
                migrationExceptions.add(migrationException);

                // Don't add this record
                addRecord = false;
            }

            if (addRecord) {

                // Map the IEN if it is a numeric value
                vo.setDrugClassIen(type.getVaDrugClassIen());

                // Map the IEN if it is a numeric value
                DrugClassificationTypeVo dcctVo = new DrugClassificationTypeVo();

                if ("0".equals(type.getType())) {
                    dcctVo.setValue("0-Major");
                    dcctVo.setId("1");
                    vo.setClassificationType(dcctVo);
                } else if ("1".equals(type.getType())) {
                    dcctVo.setValue("1-Minor");
                    dcctVo.setId("2");
                    vo.setClassificationType(dcctVo);
                } else if ("2".equals(type.getType())) {
                    dcctVo.setValue("2-Sub-Class");
                    dcctVo.setId("3");
                    vo.setClassificationType(dcctVo);
                } else {
                    MigrationException migrationException =
                        new MigrationException("Error on Type Field", "", (type.getCode() + type.getClassification()),
                            "DrugClass Type", type.getType());
                    migrationExceptions.add(migrationException);
                    addRecord = false;
                }

                vo.setCode(type.getCode());
                vo.setClassification(type.getClassification());
                vo.setVuid(type.getVuid());
                vo.setDrugClassIen(type.getVaDrugClassIen());
                List<String> strList = type.getDescription();
                StringBuilder sb = new StringBuilder();
         
                for (String str : strList) {
                    sb.append(str);
                }
 
                vo.setDescription(sb.toString().replaceAll("\\s+", " "));

                if (type.getParentClass() != null) {

                    // Setup the parent class;
                    DrugClassVo pvo = new DrugClassVo();
                    pvo.setCode(type.getParentClass().getCode());
                    pvo.setClassification(type.getParentClass().getClassification());
                    vo.setParentDrugClass(pvo);
                }

                // processClasses set the effectiveDateTimes list
                effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();

                // processClasses process the effective date multiples
                for (EffectiveDateTime effectiveDate : type.getEffectiveDateTime()) {
                    try {
                        VuidStatusHistoryVo effectiveDateTime = new VuidStatusHistoryVo();
          
                        // Format the date
                        Date multiEffectiveDate = df.parse(effectiveDate.getEffectiveDateTime());

                        // Set the date in the effetiveDate object for the Drug Classes
                        effectiveDateTime.setEffectiveDateTime(multiEffectiveDate);
                        effectiveDateTime.setVuid(Long.valueOf(type.getVuid()));
                        effectiveDateTime.setCreatedBy(MIGRATION);
                        effectiveDateTime.setCreatedDate(new Date());
               
                        // Set active state in the Effective Date object for the DrugClasses
                        if ("1".equals(effectiveDate.getStatus())) {
                            effectiveDateTime.setEffectiveDtmStatus(ItemStatus.ACTIVE);
                        } else {
                            effectiveDateTime.setEffectiveDtmStatus(ItemStatus.INACTIVE);
                        }

                        // add the effective date object to the Effective Data
                        // List Object in the Generic Name VO
                        effectiveDateTimes.add(effectiveDateTime);
                    } catch (ParseException ex) {
                        if (addRecord) {
                            MigrationException migrationException =
                                new MigrationException("Error parsing Effective Date", type.getVaDrugClassIen(),
                                    (type.getCode() + type.getClassification()), EFF_DATE, "", EFF_DATE,
                                    effectiveDate.getEffectiveDateTime());
                            migrationExceptions.add(migrationException);

                            // Don't add this record
                            addRecord = false;
                            break;
                        }
                    }
                }
           
                // add the effective date time list to the VO
                vo.setEffectiveDates(effectiveDateTimes);

                // Add the file to the list of VO's if there are no errors
                if (addRecord) {
                    managedItemsVos.add(vo);
                }
            }

            // Now put the two lists into the response VO
            migrationResponseVo.setManagedItemsVos(managedItemsVos);
            migrationResponseVo.setMigrationExceptions(migrationExceptions);
        }

        // Log the object after the conversion to a VO for debugging
        LOG.debug("Drug Class Response to VistA: " + drugMigrationResponse);

        return (migrationResponseVo);
    }

    /**
     * Process Dosage Form 50.606
     * 
     * @param drugMigrationResponse drugMigrationResponse
     * @return MigrationResponseVo
     */
    private MigrationResponseVo processDosageForms(DrugMigrationResponse drugMigrationResponse) {
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
        List<ManagedItemVo> managedItemsVos = new ArrayList<ManagedItemVo>();
        List<MigrationException> migrationExceptions = new ArrayList<MigrationException>();
        boolean addRecord;

        // set the End Of File flag
        if (drugMigrationResponse.getResponseHeader().getEndOfFile().equals("0")) {
            migrationResponseVo.setEof(false);
        } else if (drugMigrationResponse.getResponseHeader().getEndOfFile().equals("1")) {
            migrationResponseVo.setEof(true);
        }

        for (DosageFormFieldType type : drugMigrationResponse.getDosageForm()) {
       
            addRecord = true;
            DosageFormVo vo = new DosageFormVo();

            // Get the inactivation date and format it
            if (type.getInactivationDate() == null) {
                vo.setInactivationDate(null);
                vo.setItemStatus(ItemStatus.ACTIVE);
            } else {
                try {
                    vo.setItemStatus(ItemStatus.INACTIVE);
                    Date inactiveDate = df.parse(type.getInactivationDate());
                    vo.setInactivationDate(inactiveDate);
                } catch (ParseException ex) {
                    MigrationException migrationException =
                        new MigrationException(PARSE_DATE, type.getDosageFormIen(), type.getName(), INACT_DATE,
                            type.getInactivationDate());
                    migrationExceptions.add(migrationException);
                    addRecord = false;
                }// end catch for inactive date
            }

            vo.setDosageFormIen(type.getDosageFormIen());
            vo.setDosageFormName(type.getName());
            
            if (type.getExcludeFromDosageChecks().equals("1")) {
                vo.setExcludeFromDosageChks(ExcludeDosageCheck.YES);
            } else if (type.getExcludeFromDosageChecks().equals("0")) {
                vo.setExcludeFromDosageChks(ExcludeDosageCheck.NO);
            } else {
                MigrationException migrationException =
                    new MigrationException("Error Exclude From Dosage Checks is not a Boolean", type.getDosageFormIen(),
                        type.getName(), "Dosage Form Exclude Dosage Check", type.getExcludeFromDosageChecks());
                migrationExceptions.add(migrationException);
                addRecord = false;
            }

            // set the effectiveDateTimes list
            ArrayList<DosageFormUnitVo> dosageFormUnitVos = new ArrayList<DosageFormUnitVo>();

            // Map dosage form units from units
            for (Units units : type.getUnits()) {
                DrugUnitVo duvo = new DrugUnitVo();
                duvo.setValue(units.getUnits());
                duvo.setDrugUnitIen(units.getUnitsIen());
                List<PossibleDosagesPackageVo> lpdpvo = new ArrayList<PossibleDosagesPackageVo>();

                // See if the data is inpatient
                if (units.getPackage() == null || "".equals(units.getPackage())) {
                    PossibleDosagesPackageVo pdpvo = new PossibleDosagesPackageVo();
                    pdpvo.setValue("");
                    lpdpvo.add(pdpvo);
                } else if (units.getPackage().equals("I")) {
                    PossibleDosagesPackageVo pdpvo = new PossibleDosagesPackageVo();
                    pdpvo.setValue(INPAT);
                    lpdpvo.add(pdpvo);
                } else if (units.getPackage().equals("O")) {
                    PossibleDosagesPackageVo pdpvo = new PossibleDosagesPackageVo();
                    pdpvo.setValue(OUTPAT);
                    lpdpvo.add(pdpvo);
                } else if (units.getPackage().equals("IO") || units.getPackage().equals("OI")) {
                    PossibleDosagesPackageVo pdpvo1 = new PossibleDosagesPackageVo();
                    pdpvo1.setValue(INPAT);
                    lpdpvo.add(pdpvo1);
                    PossibleDosagesPackageVo pdpvo2 = new PossibleDosagesPackageVo();
                    pdpvo2.setValue(OUTPAT);
                    lpdpvo.add(pdpvo2);
                } else {
                    MigrationException migrationException =
                        new MigrationException("Error parsing package", type.getDosageFormIen(), type.getName(), PACKAGE, "",
                            PACKAGE, units.getPackage());
                    migrationExceptions.add(migrationException);
                    addRecord = false;
                    break;
                }

                // Create the Dosage Form Unit VO
                DosageFormUnitVo dfvo = new DosageFormUnitVo();

                // now add the package list to the possible dosages
                dfvo.setPackages(lpdpvo);

                // now add the drug unit to possible dosages
                dfvo.setDrugUnit(duvo);

                // now add the dosage form unit to the dosage form
                dosageFormUnitVos.add(dfvo);
            }

            // now set the dosage in the vo
            vo.setDfUnits(dosageFormUnitVos);

         
            addRecord = processDosageForm1(type, vo, addRecord, migrationExceptions);
            
            // Add the file to the list of VO's if there are no errors
            if (addRecord) {
                managedItemsVos.add(vo);
            }

            // Now put the two lists into the response VO
            migrationResponseVo.setManagedItemsVos(managedItemsVos);
            migrationResponseVo.setMigrationExceptions(migrationExceptions);
            
            
        }


        return migrationResponseVo;
    }

    /**
     * processDosageForm1
     * @param type type
     * @param vo vo
     * @param addRecord addRecord
     * @param migrationExceptions migrationExceptions
     * @return true if a successful parsing
     */
    private boolean processDosageForm1(DosageFormFieldType type,  DosageFormVo vo, boolean addRecord, 
        List<MigrationException> migrationExceptions) {
        
        boolean addThisRecord = addRecord;
        
        // create a list array of dispnse units per dose list
        ArrayList<DispenseUnitPerDoseVo> dispenseUnitPerDoseVos = new ArrayList<DispenseUnitPerDoseVo>();

        // Map dispense units per dose from Dispense Units Per Dose
        for (DispenseUnitsPerDose dispenseUnitsPerDose : type.getDispenseUnitsPerDose()) {

            // Create the list Possible Dosages Package VO
            List<PossibleDosagesPackageVo> lpdpvo = new ArrayList<PossibleDosagesPackageVo>();

            // Create the Possible Dosages Package VO

            // See if the data is inpatient
            if (dispenseUnitsPerDose.getPackage() == null || "".equals(dispenseUnitsPerDose.getPackage())) {
                PossibleDosagesPackageVo pdpvo = new PossibleDosagesPackageVo();
                pdpvo.setValue("");
                lpdpvo.add(pdpvo);
            } else if (dispenseUnitsPerDose.getPackage().equals("I")) {
                PossibleDosagesPackageVo pdpvo = new PossibleDosagesPackageVo();
                pdpvo.setValue(INPAT);
                lpdpvo.add(pdpvo);
            } else if (dispenseUnitsPerDose.getPackage().equals("O")) {
                PossibleDosagesPackageVo pdpvo = new PossibleDosagesPackageVo();
                pdpvo.setValue(OUTPAT);
                lpdpvo.add(pdpvo);
            } else if (dispenseUnitsPerDose.getPackage().equals("IO") || dispenseUnitsPerDose.getPackage().equals("OI")) {
                PossibleDosagesPackageVo pdpvo1 = new PossibleDosagesPackageVo();
                pdpvo1.setValue(INPAT);
                lpdpvo.add(pdpvo1);
                PossibleDosagesPackageVo pdpvo2 = new PossibleDosagesPackageVo();
                pdpvo2.setValue(OUTPAT);
                lpdpvo.add(pdpvo2);
            } else {
                MigrationException migrationException =
                    new MigrationException("Error Parsing Dispense Package", type.getDosageFormIen(), type.getName(),
                        PACKAGE, "", PACKAGE, dispenseUnitsPerDose.getPackage());
                migrationExceptions.add(migrationException);
                addThisRecord = false;
                break;
            }

            // Create the Dispense Unit Per Dose VO
            DispenseUnitPerDoseVo dupdvo = new DispenseUnitPerDoseVo();
            dupdvo.setStrDispenseUnitPerDose(dispenseUnitsPerDose.getDispenseUnitsPerDose());
            dupdvo.setDispenseUnitPerDosIen(dispenseUnitsPerDose.getDispenseUnitsPerDoseIen());
            dupdvo.setPackages(lpdpvo);

            // now add the dispense unit per dose to the VO
            dispenseUnitPerDoseVos.add(dupdvo);
        }

        // now set the dispense in the vo
        vo.setDfDispenseUnitsPerDose(dispenseUnitPerDoseVos);

        return addThisRecord;
    }
    
    /**
     * Process VA Product 50.68
     * 
     * @param drugMigrationResponse drugMigrationResponse
     * @return MigrationResponseVo
     */
    private MigrationResponseVo processProducts(DrugMigrationResponse drugMigrationResponse) {
        MigrationResponseVo migrationResponseVo = new MigrationResponseVo();
        List<ManagedItemVo> managedItemsVos = new ArrayList<ManagedItemVo>();
        List<MigrationException> migrationExceptions = new ArrayList<MigrationException>();
       
        boolean addRecord;

        // set the End Of File flag
        if (drugMigrationResponse.getResponseHeader().getEndOfFile().equals("0")) {
            migrationResponseVo.setEof(false);
        } else if (drugMigrationResponse.getResponseHeader().getEndOfFile().equals("1")) {
            migrationResponseVo.setEof(true);
        }

        for (VaProductFieldsType type : drugMigrationResponse.getVaProduct()) {
            try {
                addRecord = true;
                ProductVo vo = new ProductVo();

                addRecord = processProducts1(addRecord, type, vo, migrationExceptions);

                if (addRecord) {

                    if ("1".equals(type.getNationalFormularyIndicator())) {
                        vo.setNationalFormularyIndicator(true);
                    } else {
                        vo.setNationalFormularyIndicator(false);
                    }

                    // Map the Federal Schedule VO
                    CsFedScheduleVo csFedScheduleVo = new CsFedScheduleVo();
                    csFedScheduleVo.setValue(type.getCsFederalSchedule());
                    vo.setCsFedSchedule(csFedScheduleVo);

                    // singleMultiSource
                    SingleMultiSourceProductVo singleMultiSourceProductVo = new SingleMultiSourceProductVo();

                    if (StringUtils.isEmpty(type.getSingleMultiSourceProduct())) {
                        vo.setSingleMultiSourceProduct(null);
                    } else if (type.getSingleMultiSourceProduct().equals("M")) {
                        singleMultiSourceProductVo.setValue(PPSConstants.MULTISOURCE_MULTI);
                        vo.setSingleMultiSourceProduct(singleMultiSourceProductVo);
                    } else {
                        singleMultiSourceProductVo.setValue(PPSConstants.MULTISOURCE_SINGLE);
                        vo.setSingleMultiSourceProduct(singleMultiSourceProductVo);
                    }

                    

                    // Get the inactivation date and format it
                    if (StringUtils.isBlank(type.getInactivationDate())) {
                        vo.setInactivationDate(null);
                        vo.setItemStatus(ItemStatus.ACTIVE);
                    } else {

                        try {
                            vo.setItemStatus(ItemStatus.INACTIVE);
                            Date inactiveDate = df.parse(type.getInactivationDate());
                            vo.setInactivationDate(inactiveDate);
                        } catch (ParseException ex) {
                            MigrationException migrationException =
                                new MigrationException(PARSE_DATE, type.getName(), type.getName(), INACT_DATE,
                                    type.getInactivationDate());
                            migrationExceptions.add(migrationException);
                            addRecord = false;
                        }
                    }
                }

                addRecord = processProducts2(addRecord, type, vo, migrationExceptions);

                if (addRecord) {

                    vo.setFdaMedGuide(type.getFdaMedGuide());
        
                    if (StringUtils.isNotBlank(type.getServiceCode())) {
                        try {
                            vo.setServiceCode(type.getServiceCode());
                        } catch (Exception e) {
                            MigrationException migrationException =
                                new MigrationException("Product ServiceCode is not numeric", type.getNdfProductIen(),
                                    type.getName(), "NDF Product IEN", type.getNdfProductIen());
                            migrationExceptions.add(migrationException);
                            addRecord = false;
                        }
                    }
                }

                if (addRecord) {

                    managedItemsVos.add(vo);
                }
            } catch (Exception e) {
                LOG.error("Exception in MigrationRequestCapability not caught by someting else for "
                    + type.getVaProductIdentifier() + ":" + e.getMessage());
            }

            migrationResponseVo.setManagedItemsVos(managedItemsVos);
            migrationResponseVo.setMigrationExceptions(migrationExceptions);
        }

        return (migrationResponseVo);
    }

    /**
     * processProducts1
     * @param addRecord addRecord
     * @param type type
     * @param vo vo
     * @param migrationExceptions migrationExceptions
     * @return boolean
     */
    private boolean processProducts1(boolean addRecord, VaProductFieldsType type, ProductVo vo, 
        List<MigrationException> migrationExceptions) {
        
        boolean addThisRecord = addRecord;
        
        try {
            vo.setNdfProductIen(Long.parseLong(type.getNdfProductIen()));
            Long serviceCode = vo.getNdfProductIen() + PPSConstants.L600000;
            vo.setServiceCode(serviceCode.toString());
        } catch (Exception e) {
            MigrationException migrationException =
                new MigrationException("ProductIEN is not numeric", type.getNdfProductIen(), type.getName(),
                    "NDF PRODUCT IEN", type.getNdfProductIen());
            migrationExceptions.add(migrationException);
            addThisRecord = false;
        }

        if (addRecord) {
            vo.setVaProductName(type.getName());
            GenericNameVo genericVo = new GenericNameVo();
            genericVo.setValue(type.getVaGenericName());
            vo.setGenericName(genericVo);
            vo.setMigratedDosageFormName(type.getDosageForm());
            vo.setNationalFormularyName(type.getNationalFormularyName());
            
            if (type.getVaPrintName() == null) {
                vo.setVaPrintName("");
            } else {
                vo.setVaPrintName(type.getVaPrintName());
            }
            
            vo.setProductStrength(type.getStrength());
            
            if (StringUtils.isNotBlank(type.getUnits())) {
                DrugUnitVo drugUnitVo = new DrugUnitVo();
                drugUnitVo.setValue(type.getUnits().toUpperCase(Locale.US));
                vo.setProductUnit(drugUnitVo);
            }
            
            vo.setCmopId(type.getVaProductIdentifier());

            if (StringUtils.isBlank(type.getTransmitToCmop())) {
                vo.setCmopDispense(false);
            } else if (type.getTransmitToCmop().equals("1")) {
                vo.setCmopDispense(true);
            } else {
                vo.setCmopDispense(false);
            }

            DispenseUnitVo dispenseVo = new DispenseUnitVo();

            if (StringUtils.isNotBlank(type.getVaDispenseUnit())) {
                dispenseVo.setValue(type.getVaDispenseUnit());
            }

            vo.setDispenseUnit(dispenseVo);
            vo.setGcnSequenceNumber(type.getGcnSeqNo());

            List<ActiveIngredients> aiList = type.getActiveIngredients();
            Collection<ActiveIngredientVo> aiColl = new ArrayList<ActiveIngredientVo>();

            for (ActiveIngredients ai : aiList) {
                ActiveIngredientVo aiVo = new ActiveIngredientVo();
                aiVo.setStrength(ai.getStrength());
                DrugUnitVo duVo = new DrugUnitVo();
                duVo.setValue(ai.getUnits());
                aiVo.setDrugUnit(duVo);
                IngredientVo iVo = new IngredientVo();
                iVo.setValue(ai.getIngredientName());
                aiVo.setIngredient(iVo);
                aiColl.add(aiVo);
            }

            vo.setActiveIngredients(aiColl);

            if (type.getPrimaryVaDrugClass() == null
                || StringUtils.isBlank(type.getPrimaryVaDrugClass().getClassification())
                || StringUtils.isBlank(type.getPrimaryVaDrugClass().getCode())) {
                MigrationException migrationException =
                    new MigrationException("The Primary Drug Class is mandatory", type.getName(), type.getName(),
                        "NDF Product Primary Drug Class", type.getName());
                migrationExceptions.add(migrationException);
                addThisRecord = false;
            } else {
                DrugClassVo dcVo = new DrugClassVo();
                dcVo.setCode(type.getPrimaryVaDrugClass().getCode());
                dcVo.setClassification(type.getPrimaryVaDrugClass().getClassification());
                DrugClassGroupVo groupVo = new DrugClassGroupVo();
                groupVo.setPrimary(true);
                groupVo.setDrugClass(dcVo);
                Collection<DrugClassGroupVo> dgvo = new ArrayList<DrugClassGroupVo>(0);
                dgvo.add(groupVo);
                vo.setDrugClasses(dgvo);
            }
        }
        
        return addThisRecord;
    }
    
    /**
     * processProducts2
     * @param addRecord addRecord
     * @param type type
     * @param vo vo
     * @param migrationExceptions migrationExceptions
     * @return boolean
     */
    private boolean processProducts2(boolean addRecord, VaProductFieldsType type, ProductVo vo, 
        List<MigrationException> migrationExceptions) {
        
        List<VuidStatusHistoryVo> effectiveDateTimes;
        boolean addThisRecord = addRecord;
        
        if (addRecord) {

            if ("1".equals(type.getOverrideDfDoseChkExclusion())) {
                vo.setOverrideDfDoseChkExclusn(true);
            } else {
                vo.setOverrideDfDoseChkExclusn(false);
            }

            if ("1".equals(type.getExcludeDrugDrugInteraction())) {
                vo.setExcludeDrgDrgInteractionCheck(true);
            } else {
                vo.setExcludeDrgDrgInteractionCheck(false);
            }

            if (type.getCreatePossibleDosage() == null || StringUtils.isBlank(type.getCreatePossibleDosage())) {
                vo.setCreatePossibleDosage(false);
            } else if (type.getCreatePossibleDosage().startsWith("Y")) {
                vo.setCreatePossibleDosage(true);
            } else {
                vo.setCreatePossibleDosage(false);
            }


            if ("N".equals(type.getPossibleDosagesToCreate())) {
                vo.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.NO_POSSIBLE_DOSAGES);
            } else if ("O".equals(type.getPossibleDosagesToCreate())) {
                vo.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES);
            } else if ("B".equals(type.getPossibleDosagesToCreate())) {
                vo.setPossibleDosagesAutoCreate(PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES);
            }

            if ("I".equals(type.getProductPackage())) {
                vo.setProductPackage(ProductPackage.INPATIENT);
            } else if ("O".equals(type.getProductPackage())) {
                vo.setProductPackage(ProductPackage.OUTPATIENT);
            } else if ("IO".equals(type.getProductPackage())) {
                vo.setProductPackage(ProductPackage.BOTH);
            } else if ("OI".equals(type.getProductPackage())) {
                vo.setProductPackage(ProductPackage.BOTH);
            }

            // Map the VUID
            vo.setVuid(type.getVuid());
        
            if ("1".equals(type.getMasterEntryForVuid())) {
                vo.setMasterEntryForVuid(true);
            } else if ("0".equals(type.getMasterEntryForVuid())) {
                vo.setMasterEntryForVuid(false);
            } else {
                MigrationException migrationException =
                    new MigrationException(NO0OR1, vo.getNdfProductIen().toString(), vo.getVaProductName(),
                        "MasterEntryForVUID", type.getMasterEntryForVuid());
                migrationExceptions.add(migrationException);
                addThisRecord = false;
            }
        }

        if (addRecord) {

            // set the effectiveDateTimes list
            effectiveDateTimes = new ArrayList<VuidStatusHistoryVo>();

            // process the effective date multiples
            for (EffectiveDateTime effectiveDate : type.getEffectiveDateTime()) {
                try {
                    VuidStatusHistoryVo effectiveDateTime = new VuidStatusHistoryVo();
       
                    // Format the date
                    Date multiEffectiveDate = df.parse(effectiveDate.getEffectiveDateTime());

                    // Set the date in the effetiveDate object
                    effectiveDateTime.setEffectiveDateTime(multiEffectiveDate);
                    effectiveDateTime.setVuid(Long.valueOf(type.getVuid()));
                    effectiveDateTime.setCreatedBy(MIGRATION);
                    effectiveDateTime.setCreatedDate(new Date());
       
                    // Set active state in the Effective Date object
                    if ("1".equals(effectiveDate.getStatus())) {
                        effectiveDateTime.setEffectiveDtmStatus(ItemStatus.ACTIVE);
                    } else {
                        effectiveDateTime.setEffectiveDtmStatus(ItemStatus.INACTIVE);
                    }

                    // add the effective date object to the Effective
                    // Data List Object in the Ingredients VO
                    effectiveDateTimes.add(effectiveDateTime);
                } catch (ParseException ex) {
                    if (addRecord) {
                        MigrationException migrationException =
                            new MigrationException(PARSE_DATE, type.getName(), type.getName(), INACT_DATE,
                                type.getInactivationDate());
                        migrationExceptions.add(migrationException);

                        // Don't add this record
                        addThisRecord = false;
                        break;
                    }
                }
            }

            // add the effective date time list to the VO
            vo.setEffectiveDates(effectiveDateTimes);
        }
        
        return addThisRecord;
    }
    
    /**
     * Sets the VistaLinkConnectionUtility.
     * 
     * @param connection Class that will be used to communicate with VistA.
     */
    public void setVistaLinkConnectionUtility(
            VistaLinkConnectionUtility connection) {

        this.vistaLinkConnectionUtility = connection;
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }
  
}
