/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugDoseCheckCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.DoseStatusUtility;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.MessageConversionUtility;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.StringUtility;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugDoseCheckResultVo;

import firstdatabank.database.FDBSQLException;
import firstdatabank.dif.DOSEScreenResult;
import firstdatabank.dif.DOSEScreenResults;
import firstdatabank.dif.FDBDOSESource;
import firstdatabank.dif.ScreenDrugs;
import firstdatabank.dif.Screening;


/**
 * Perform a drug dosage check
 */
public class PerformDrugDoseCheckCapabilityImpl implements PerformDrugDoseCheckCapability {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.#####"); // format numbers similar to FDB
    private static final int MAX_DECIMALS = 5;
    private Screening screening;

    /**
     * Default constructor
     */
    public PerformDrugDoseCheckCapabilityImpl() {
        super();

        DECIMAL_FORMAT.setMaximumFractionDigits(MAX_DECIMALS);
    }

    /**
     * Perform a drug dosage check
     * 
     * @param screenDrugs FDB ScreenDrugs used as input to dose check call
     * @param drugMap Map of the combined String of IEN and order number to DrugCheckVo. The combined String of IEN and order
     *            number is stored in the FDB ScreenDrug's custom ID attribute.
     * @param prospectiveOnly boolean true if check should only be done on prospective drugs
     * @param ageInDays long age of patient in days
     * @param weightInKg double weight of patient in kilograms
     * @param bodySurfaceAreaInSqM double body surface area of patient in square meters
     * @param customTables boolean true if customized tables to be used in the check
     * @return DrugDoseCheckResultsVo containing results from FDB
     */
    public DrugCheckResultsVo<DrugDoseCheckResultVo> performDrugDoseCheck(ScreenDrugs screenDrugs,
                                                                          Map<String, DrugCheckVo> drugMap,
                                                                          boolean prospectiveOnly, long ageInDays,
                                                                          double weightInKg, double bodySurfaceAreaInSqM,
                                                                          boolean customTables) {

        FDBDOSESource source = FDBDOSESource.fdbDSDRCAndMinMax;
        String category = "";

        if (customTables) {
            source = FDBDOSESource.fdbDSCustomDRCAndMinMax;
            category = DrugCheckVo.VA_CUSTOM_CATEGORY;
        }

        try {
            DOSEScreenResults fdbResults = screening.DOSEScreen(screenDrugs, prospectiveOnly, ageInDays, weightInKg,
                bodySurfaceAreaInSqM, source, category);

            return convertResults(fdbResults, screenDrugs, drugMap);
        } catch (FDBSQLException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        } catch (IOException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        }
    }

    /**
     * Convert from the results received into Collection of value objects
     * 
     * @param screenResults return from FDB call to dose check
     * @param screenDrugs FDB ScreenDrugs used as input to dose check call
     * @param drugMap Map of the combined String of IEN and order number to DrugCheckVo. The combined String of IEN and order
     *            number is stored in the FDB ScreenDrug's custom ID attribute.
     * @return DrugCheckResultsVo results converted from Drug Data Vendor return containing DrugDoseCheckResultVO for checks
     * @throws IOException if cannot load properties file
     */
    private DrugCheckResultsVo<DrugDoseCheckResultVo> convertResults(DOSEScreenResults screenResults,
                                                                     ScreenDrugs screenDrugs,
                                                                     Map<String, DrugCheckVo> drugMap) throws IOException {

        DrugCheckResultsVo<DrugDoseCheckResultVo> vo = new DrugCheckResultsVo<DrugDoseCheckResultVo>();

        // Set messages
        vo.setMessages(MessageConversionUtility.toDrugCheckMessages(screenResults.getMessages(), drugMap,
            screenDrugs));

        vo.setChecks(new ArrayList<DrugDoseCheckResultVo>());

        for (int i = 0; i < screenResults.count(); i++) {
            DOSEScreenResult result = screenResults.item(i);
            DrugDoseCheckResultVo check = new DrugDoseCheckResultVo();
            check.setDrug(drugMap.get(screenDrugs.get(result.getDrugIndex()).getCustomID()));

            // Set range dose
            check.setRangeDoseHigh(DECIMAL_FORMAT.format(result.doseRecord().getDoseHigh()));

            if (result.getDoseRecord().getDoseHighUnit() != null
                && result.getDoseRecord().getDoseHighUnit().trim().length() > 0) {
                check.setRangeDoseHigh(check.getRangeDoseHigh() + " " + result.doseRecord().getDoseHighUnit().trim());
            }

            check.setRangeDoseLow(DECIMAL_FORMAT.format(result.doseRecord().getDoseLow()));

            if (result.getDoseRecord().getDoseLowUnit() != null
                && result.getDoseRecord().getDoseLowUnit().trim().length() > 0) {
                check.setRangeDoseLow(check.getRangeDoseLow() + " " + result.doseRecord().getDoseLowUnit().trim());
            }

            check.setRangeDoseMessage(StringUtility.nullToEmptyString(result.getRangeDoseMessage()));
            check.setRangeDoseStatus(DoseStatusUtility.convert(result.getRangeDoseStatus()));
            check.setRangeDoseStatusCode(String.valueOf(result.getRangeDoseStatus().toInt()));

            // Set single dose
            check.setSingleDoseMax(DECIMAL_FORMAT.format(result.doseRecord().getMaxSingleDose()));

            if (result.getDoseRecord().getMaxSingleDoseUnit() != null
                && result.getDoseRecord().getMaxSingleDoseUnit().trim().length() > 0) {
                check.setSingleDoseMax(check.getSingleDoseMax() + " " + result.doseRecord().getMaxSingleDoseUnit().trim());
            }

            check.setSingleDoseMessage(StringUtility.nullToEmptyString(result.getSingleDoseMessage()));
            check.setSingleDoseStatus(DoseStatusUtility.convert(result.getSingleDoseStatus()));
            check.setSingleDoseStatusCode(String.valueOf(result.getSingleDoseStatus().toInt()));

            // Set duration
            check.setDurationMessage(StringUtility.nullToEmptyString(result.getDurationMessage()));
            check.setDurationStatus(DoseStatusUtility.convert(result.getDurationStatus()));
            check.setDurationStatusCode(String.valueOf(result.getDurationStatus().toInt()));

            // Set frequency
            check.setFrequencyMessage(StringUtility.nullToEmptyString(result.getFrequencyMessage()));
            check.setFrequencyStatus(DoseStatusUtility.convert(result.getFrequencyStatus()));
            check.setFrequencyStatusCode(String.valueOf(result.getFrequencyStatus().toInt()));

            // Set daily dose
            check.setDailyDoseMessage(StringUtility.nullToEmptyString(result.getDailyDoseMessage()));
            check.setDailyDoseStatus(DoseStatusUtility.convert(result.getDailyDoseStatus()));
            check.setDailyDoseStatusCode(String.valueOf(result.getDailyDoseStatus().toInt()));

            // Set max daily dose
            check.setMaxDailyDoseMessage(StringUtility.nullToEmptyString(result.getMaxDailyDoseMessage()));
            check.setMaxDailyDoseStatus(DoseStatusUtility.convert(result.getMaxDailyDoseStatus()));
            check.setMaxDailyDoseStatusCode(String.valueOf(result.getMaxDailyDoseStatus().toInt()));

            // Set max lifetime dose
            check.setMaxLifetimeDose(DECIMAL_FORMAT.format(result.doseRecord().getMaxLifetimeDose()));

            if (result.getDoseRecord().getMaxLifetimeDoseUnit() != null
                && result.getDoseRecord().getMaxLifetimeDoseUnit().trim().length() > 0) {
                check.setMaxLifetimeDose(check.getMaxLifetimeDose() + " "
                    + result.doseRecord().getMaxLifetimeDoseUnit().trim());
            }

            // Set dose high
            check.setDoseHigh(DECIMAL_FORMAT.format(result.getDoseRecord().getDoseHigh()));
            check.setDoseHighUnit(StringUtility.nullToEmptyString(result.getDoseRecord().getDoseHighUnit()));

            // Set dose low
            check.setDoseLow(DECIMAL_FORMAT.format(result.getDoseRecord().getDoseLow()));
            check.setDoseLowUnit(StringUtility.nullToEmptyString(result.getDoseRecord().getDoseLowUnit()));

            // Set dose form high
            check.setDoseFormHigh(DECIMAL_FORMAT.format(result.getDoseRecord().getDoseFormHigh()));
            check.setDoseFormHighUnit(StringUtility.nullToEmptyString(result.getDoseRecord().getDoseFormHighUnit()));

            // Set dose form low
            check.setDoseFormLow(DECIMAL_FORMAT.format(result.getDoseRecord().getDoseFormLow()));
            check.setDoseFormLowUnit(StringUtility.nullToEmptyString(result.getDoseRecord().getDoseFormLowUnit()));

            // Set route description
            check.setDoseRouteDescription(StringUtility.nullToEmptyString(result.getDoseRecord().getDoseRouteDescription()));

            // Add the current order check to the Collection of all order checks
            vo.getChecks().add(check);
        }

        return vo;
    }

    /**
     * setScreening
     * @param screening property
     */
    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}
