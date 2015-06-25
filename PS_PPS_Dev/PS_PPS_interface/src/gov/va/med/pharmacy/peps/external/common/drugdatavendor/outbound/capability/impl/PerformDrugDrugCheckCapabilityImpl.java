/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugDrugCheckCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.MessageConversionUtility;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.StringUtility;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.ConsumerMonographVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckMessageVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugDrugCheckResultVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.ProfessionalMonographVo;

import firstdatabank.database.FDBException;
import firstdatabank.database.FDBSQLException;
import firstdatabank.dif.DDIMScreenResult;
import firstdatabank.dif.DDIMScreenResults;
import firstdatabank.dif.FDBCode;
import firstdatabank.dif.FDBDDIMSeverityFilter;
import firstdatabank.dif.FDBMonographSource;
import firstdatabank.dif.FDBMonographSourceType;
import firstdatabank.dif.FDBMonographType;
import firstdatabank.dif.FDBUnknownIDException;
import firstdatabank.dif.Monograph;
import firstdatabank.dif.MonographLine;
import firstdatabank.dif.MonographSection;
import firstdatabank.dif.MonographSections;
import firstdatabank.dif.ScreenDrugs;
import firstdatabank.dif.Screening;


/**
 * Perform drug-drug interaction check
 * 
 * This class is abstract so that Spring can provide lookup method injection to give new instances of ScreenDrugs and
 * ScreenDrug FDB objects.
 * 
 * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
 */
public abstract class PerformDrugDrugCheckCapabilityImpl implements PerformDrugDrugCheckCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(PerformDrugDrugCheckCapabilityImpl.class);

    private static final String CONTINUATION_FORMAT_CODE = "0";
    private static final int SEVERITY_CODE_TYPE = 14;
    private static final String FDB_ID_CATEGORY = PropertyUtility.getProperty(PerformDrugDrugCheckCapabilityImpl.class,
        "fdb.id.category");

    private Screening screening;

    /**
     * Empty constructor
     */
    public PerformDrugDrugCheckCapabilityImpl() {
        super();
    }

    /**
     * Perform drug-drug interaction check
     * 
     * @param screenDrugs FDB ScreenDrugs used as input to dose check call
     * @param drugMap Map of the combined String of IEN and order number to DrugCheckVo. The combined String of IEN and order
     *            number is stored in the FDB ScreenDrug's custom ID attribute.
     * @param prospectiveOnly boolean true if check should only be done on prospective drugs
     * @param customTables boolean true if customized tables to be used in the check
     * @return DrugDrugCheckResultsVo contains response from FDB
     */
    public DrugCheckResultsVo<DrugDrugCheckResultVo> performDrugDrugCheck(ScreenDrugs screenDrugs,
                                                                          Map<String, DrugCheckVo> drugMap,
                                                                          boolean prospectiveOnly, boolean customTables) {

        try {
            DDIMScreenResults fdbResults = screening.DDIMScreen(screenDrugs, prospectiveOnly,
                FDBDDIMSeverityFilter.fdbDDIMSFModerate, customTables, customTables, customTables, FDB_ID_CATEGORY, false,
                "");

            return convertResults(fdbResults, screenDrugs, drugMap, customTables);
        } catch (FDBException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        } catch (SQLException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        }
    }

    /**
     * Convert from the results received into Collection of value objects
     * 
     * @param screenResults results from FDB
     * @param screenDrugs FDB ScreenDrugs used as input to dose check call
     * @param drugMap Map of the combined String of IEN and order number to DrugCheckVo. The combined String of IEN and order
     *            number is stored in the FDB ScreenDrug's custom ID attribute.
     * @param customTables boolean true if customized tables to be used in the check
     * @return DrugCheckResultsVO results converted from Drug Data Vendor return containing DrugDrugCheckResultVO for checks
     * @throws FDBException if error within FDB API
     * @throws SQLException if error within FDB API
     */
    private DrugCheckResultsVo<DrugDrugCheckResultVo> convertResults(DDIMScreenResults screenResults,
                                                                     ScreenDrugs screenDrugs,
                                                                     Map<String, DrugCheckVo> drugMap, boolean customTables)
        throws FDBException, SQLException {

        DrugCheckResultsVo<DrugDrugCheckResultVo> vo = new DrugCheckResultsVo<DrugDrugCheckResultVo>();

        Collection<DrugCheckMessageVo> messages = MessageConversionUtility.toDrugCheckMessages(screenResults.getMessages(),
            drugMap, screenDrugs);
        vo.setMessages(messages);

        List<DDIMScreenResult> filtered = filterResults(screenResults);

        for (DDIMScreenResult result : filtered) {
            if (result.getUserInteraction()) {
                LOG.debug("Converting Custom DDIMScreenResult with ID " + result.getInteractionID());
            } else {
                LOG.debug("Converting FDB DDIMScreenResult with ID " + result.getInteractionID());
            }

            DrugDrugCheckResultVo check = new DrugDrugCheckResultVo();

            check.setDrugs(new ArrayList<DrugCheckVo>());
            DrugCheckVo drug1 = drugMap.get(screenDrugs.get(result.getDrug1Index()).getCustomID());
            check.getDrugs().add(drug1);
            DrugCheckVo drug2 = drugMap.get(screenDrugs.get(result.getDrug2Index()).getCustomID());
            check.getDrugs().add(drug2);

            check.setInteractionDescription(StringUtility.nullToEmptyString(result.getInteractionDescription()));
            check.setShortText(StringUtility.nullToEmptyString(result.getScreenMessage()));

            FDBCode severity = newFdbCodeInstance();
            severity.load(SEVERITY_CODE_TYPE, result.getSeverityLevelCode());
            check.setSeverity(StringUtility.nullToEmptyString(severity.getDescription()));

            try {
                check.setProfessionalMonograph(loadProfessionalMonograph(result.getMonographID(), customTables));
            } catch (FDBUnknownIDException e) {
                LOG.warn("DDIM Professional Monograph not found with ID " + result.getMonographID());
            }

            try {
                check.setConsumerMonograph(loadConsumerMonograph(result.getMonographID(), customTables));
            } catch (FDBUnknownIDException e) {
                LOG.warn("DDIM Consumer Monograph not found with ID " + result.getMonographID());
            }

            vo.getChecks().add(check);
        }

        return vo;
    }

    /**
     * Filter out the FDB interactions replaced by custom interactions.
     * <p>
     * First accept all interactions. Then for each FDB interaction, find if there are mapped custom interactions. If there
     * are, find the FDB interactions with the same two drugs as the custom interaction and filter it out.
     * 
     * @param screenResults DDIMScreenResults from FDB with results to filter
     * @return filtered results
     */
    private List<DDIMScreenResult> filterResults(DDIMScreenResults screenResults) {
        Map<Long, List<DDIMScreenResult>> fdbResults = new HashMap<Long, List<DDIMScreenResult>>();
        Map<Long, List<DDIMScreenResult>> userResults = new HashMap<Long, List<DDIMScreenResult>>();

        for (int i = 0; i < screenResults.count(); i++) {
            DDIMScreenResult screenResult = screenResults.item(i);

            if (screenResult.getUserInteraction()) {
                addResult(userResults, screenResult);
            } else {
                addResult(fdbResults, screenResult);
            }
        }

        removeMappedInteractions(fdbResults, userResults);

        List<DDIMScreenResult> filtered = new ArrayList<DDIMScreenResult>();

        for (List<DDIMScreenResult> results : fdbResults.values()) {
            filtered.addAll(results);
        }

        for (List<DDIMScreenResult> results : userResults.values()) {
            filtered.addAll(results);
        }

        return filtered;
    }

    /**
     * Iterator over the fdbResults entry set, finding DDIMScreenResult with mapped custom interactions.
     * <p>
     * If custom interactions are found, iterate over these interactions finding the FDB interaction that uses the same
     * custom drug IDs assigned when creating the ScreenDrugs.
     * 
     * @param fdbResults Map of interaction ID to DDIMScreenResult for FDB interactions
     * @param userResults Map of interaction ID to DDIMScreenResult for custom interactions
     */
    private void removeMappedInteractions(Map<Long, List<DDIMScreenResult>> fdbResults,
                                          Map<Long, List<DDIMScreenResult>> userResults) {

        for (Entry<Long, List<DDIMScreenResult>> entry : fdbResults.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                Long customId = getMappedId(entry.getValue().get(0));
                List<DDIMScreenResult> mappedResults = userResults.get(customId);

                if (mappedResults != null) {
                    LOG.debug("Found FDB interaction " + entry.getKey() + " with mapped custom interactions");

                    for (DDIMScreenResult mappedResult : mappedResults) {
                        String mappedDrugOneId = mappedResult.getDrug1CustomID();
                        String mappedDrugTwoId = mappedResult.getDrug2CustomID();

                        List<DDIMScreenResult> filtered = new ArrayList<DDIMScreenResult>();

                        for (DDIMScreenResult fdbResult : entry.getValue()) {
                            String fdbDrugOneId = fdbResult.getDrug1CustomID();
                            String fdbDrugTwoId = fdbResult.getDrug2CustomID();

                            if ((mappedDrugOneId.equals(fdbDrugOneId) && mappedDrugTwoId.equals(fdbDrugTwoId))
                                || (mappedDrugOneId.equals(fdbDrugTwoId) && mappedDrugTwoId.equals(fdbDrugOneId))) {

                                LOG.debug("Filtering out FDB interaction with ID " + fdbResult.getInteractionID());
                            } else {
                                filtered.add(fdbResult);
                            }
                        }

                        entry.setValue(filtered);
                    }
                }
            }
        }
    }

    /**
     * Add the given DDIMScreenResult to the given Map.
     * <p>
     * If the ID for the DDIMScreenResult does not yet have an entry in the Map, put one in.
     * 
     * @param results Map of interaction ID to DDIMScreenResult
     * @param screenResult DDIMScreenResult
     */
    private void addResult(Map<Long, List<DDIMScreenResult>> results, DDIMScreenResult screenResult) {
        List<DDIMScreenResult> idResults = results.get(screenResult.getInteractionID());

        if (idResults == null) {
            idResults = new ArrayList<DDIMScreenResult>();
            results.put(screenResult.getInteractionID(), idResults);
        }

        idResults.add(screenResult);
    }

    /**
     * Find the custom interaction ID which maps to the given FDB interaction.
     * <p>
     * Note that the FDB DIF API loads custom strings only for FDB interactions so the mapping is actually from the FDB
     * interaction to the custom interaction, not the other way around!
     * 
     * @param screenResult FDB DDIMScreenResult
     * @return Long ID of mapped custom interaction, null if no mapped custom interaction
     */
    private Long getMappedId(DDIMScreenResult screenResult) {
        Long interactionId = null;

        if (!screenResult.getUserInteraction() && screenResult.getCustomStrings() != null
            && screenResult.getCustomStrings().itemByCategory(FDB_ID_CATEGORY) != null) {

            String customInteractionId = screenResult.getCustomStrings().itemByCategory(FDB_ID_CATEGORY).getCustomString();

            if (NumberUtils.isNumber(customInteractionId)) {
                interactionId = Long.valueOf(customInteractionId);
            } else {
                LOG.error("FDB ID Custom String '" + customInteractionId + "' is not a number."
                    + "Defaulting to the FDB ID!");
            }
        }

        return interactionId;
    }

    /**
     * Load the professional monograph
     * 
     * @param monographId ID for the monograph to load
     * @param customTables boolean true if customized tables to be used in the check
     * @return ProfessionalMonographVo
     * @throws FDBUnknownIDException if FDB API cannot find the given monograph ID
     * @throws FDBSQLException if the FDB API cannot load the monograph
     */
    private ProfessionalMonographVo loadProfessionalMonograph(long monographId, boolean customTables)
        throws FDBSQLException, FDBUnknownIDException {

        FDBMonographSource source = FDBMonographSource.fdbMSFDBOnly;

        if (customTables) {
            source = FDBMonographSource.fdbMSCustomWithFDBDefault;
        }

        Monograph monograph = newMonographInstance();
        monograph.load(FDBMonographType.fdbMTDDIM, ProfessionalMonographVo.MONOGRAPH_TYPE, monographId, source);
        MonographSections sections = monograph.getSections();

        ProfessionalMonographVo professional = new ProfessionalMonographVo();

        if (FDBMonographSourceType.fdbMSTFDB.equals(monograph.getMonographSourceType())) {
            professional.setFdbMonographSourceType(true);
        } else {
            professional.setFdbMonographSourceType(false);
        }

        professional.setDisclaimer(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.DISCLAIMER_SECTION)));
        professional.setMonographTitle(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.TITLE_SECTION)));
        professional.setSeverityLevel(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.SEVERITY_SECTION)));
        professional.setMechanismOfAction(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.MECHANISM_SECTION)));
        professional.setClinicalEffects(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.CLINICAL_SECTION)));
        professional.setPredisposingFactors(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.PREDISPOSING_SECTION)));
        professional.setPatientManagement(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.PATIENT_SECTION)));
        professional.setDiscussion(getMonographSectionText(sections
            .getItemBySectionID(ProfessionalMonographVo.DISCUSSION_SECTION)));
        professional.setReferences(getReferences(sections.getItemBySectionID(ProfessionalMonographVo.REFERENCES_SECTION)));

        return professional;
    }

    /**
     * Load the consumer monograph
     * 
     * @param monographId ID for the monograph to load
     * @param customTables boolean true if customized tables to be used in the check
     * @return ConsumerMonographVo
     * @throws FDBUnknownIDException if FDB API cannot find the given monograph ID
     * @throws FDBSQLException if the FDB API cannot load the monograph
     */
    private ConsumerMonographVo loadConsumerMonograph(long monographId, boolean customTables) throws FDBSQLException,
        FDBUnknownIDException {
        Monograph monograph = newMonographInstance();
        boolean found = false;

        // The FDB DIF API has a bug where if you use FDBMonographSource.fdbMSCustomWithFDBDefault and no custom
        // monograph exists, it will always return the professional FDB monograph, even if a consumer was requested.
        // The workaround is to try and load the custom monograph and manually default to the FDB monograph.
        if (customTables) {
            try {
                monograph.load(FDBMonographType.fdbMTDDIM, ConsumerMonographVo.MONOGRAPH_TYPE, monographId,
                    FDBMonographSource.fdbMSCustomOnly);
                found = true;
            } catch (FDBUnknownIDException e) {
                LOG.warn("DDIM Custom Consumer Monograph not found with ID " + monographId);
            }
        }

        if (!found) {
            monograph.load(FDBMonographType.fdbMTDDIM, ConsumerMonographVo.MONOGRAPH_TYPE, monographId,
                FDBMonographSource.fdbMSFDBOnly);
            found = true;
        }

        MonographSections sections = monograph.getSections();

        ConsumerMonographVo consumer = new ConsumerMonographVo();

        if (FDBMonographSourceType.fdbMSTFDB.equals(monograph.getMonographSourceType())) {
            consumer.setFdbMonographSourceType(true);
        } else {
            consumer.setFdbMonographSourceType(false);
        }

        consumer.setDisclaimer(getMonographSectionText(sections.getItemBySectionID(ConsumerMonographVo.DISCLAIMER_SECTION)));
        consumer.setHowOccurs(getMonographSectionText(sections.getItemBySectionID(ConsumerMonographVo.HOW_OCCURS_SECTION)));
        consumer.setMedicalWarning(getMonographSectionText(sections
            .getItemBySectionID(ConsumerMonographVo.MEDICAL_WARNING_SECTION)));
        consumer.setMonographTitle(getMonographSectionText(sections.getItemBySectionID(ConsumerMonographVo.TITLE_SECTION)));
        consumer.setReferences(getReferences(sections.getItemBySectionID(ConsumerMonographVo.REFERENCE_SECTION)));
        consumer.setWhatMightHappen(getMonographSectionText(sections
            .getItemBySectionID(ConsumerMonographVo.WHAT_MIGHT_HAPPEN_SECTION)));
        consumer.setWhatToDo(getMonographSectionText(sections.getItemBySectionID(ConsumerMonographVo.WHAT_TO_DO_SECTION)));

        return consumer;
    }

    /**
     * Spring overridden method through lookup method injection. Spring will provide a new instance of FDBCode each time this
     * is called, because the FdbCode Spring bean is defined with a scope of "prototype".
     * 
     * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
     * 
     * @return new instance of FDBCode
     */
    protected abstract FDBCode newFdbCodeInstance();

    /**
     * Spring overridden method through lookup method injection. Spring will provide a new instance of Monograph each time
     * this is called, because the FdbMonograph Spring bean is defined with a scope of "prototype".
     * 
     * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
     * 
     * @return new instance of Monograph
     */
    protected abstract Monograph newMonographInstance();

    /**
     * Returns the text for a monograph section
     * 
     * @param section monograph section
     * @return String section text
     */
    private String getMonographSectionText(MonographSection section) {
        StringBuffer text = new StringBuffer();

        for (int i = 0; i < section.getLines().count(); i++) {
            text.append(section.getLines().item(i).getLineText());
        }

        return text.toString();
    }

    /**
     * Convert the given references MonographSection into a List of String, with each reference as a single String.
     * 
     * @param section references monograph section
     * @return List of references as Strings
     */
    private List<String> getReferences(MonographSection section) {
        List<String> references = new ArrayList<String>();
        StringBuffer text = null;

        for (int i = 0; i < section.getLines().count(); i++) {
            MonographLine line = section.getLines().item(i);

            if (!CONTINUATION_FORMAT_CODE.equals(line.getFormatCode())) {
                if (text != null) {
                    references.add(text.toString());
                }

                text = new StringBuffer();
            }

            text.append(line.getLineText());
        }

        return references;
    }

    /**
     * setScreening
     * @param screening screening property
     */
    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}
