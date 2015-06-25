/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.check;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.bind.JAXBException;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.vo.DrugDataVendorVersionVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.Body;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.ConsumerMonograph;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DoseStatus;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.Drug;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugCheck;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugDoseCheck;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugDoseChecks;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugDrugCheck;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugDrugChecks;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugNotChecked;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugTherapyCheck;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugTherapyChecks;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.DrugsNotChecked;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.Header;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.InteractedDrugList;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.MServer;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.MUser;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.Message;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.MessageSeverity;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.MessageTypeType;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.MonographSourceType;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.NotCheckedStatus;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.PEPSResponse;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.PEPSVersion;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.ProfessionalMonograph;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.ReferencesType;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.Time;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckMessageVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugDoseCheckResultVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugDrugCheckResultVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugTherapyCheckResultVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckHeaderVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckResultsVo;


/**
 * Converts a VO into an XML response
 */
public class ResponseConverter {

    /**
     * The private constructor to prevent instantiation of the class.
     */
    private ResponseConverter() {

    }

    /**
     * Marshal the given OrderCheckResultsVo into an XML message.
     * 
     * @param orderCheckResults OrderCheckResultsVo to convert into XML
     * @return String XML message
     */
    public static PEPSResponse toPepsResponse(OrderCheckResultsVo orderCheckResults) {
        try {
            ObjectFactory objectFactory = new ObjectFactory();
            PEPSResponse response = objectFactory.createPEPSResponse();
            response.setHeader(toHeader(orderCheckResults.getHeader(), orderCheckResults.getDrugDataVendorVersion(),
                objectFactory));
            response.setBody(toBody(orderCheckResults, objectFactory));

            return response;
        } catch (JAXBException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }
    }

    /**
     * Convert the OrderCheckResultsVo into the XML Body.
     * 
     * @param orderCheckResults OrderCheckresultsVo to convert
     * @param objectFactory ObjectFactory to use to instantiate XML objects
     * @return Body from OrderCheckResultsVo
     * @throws JAXBException if error instantiating XML objects
     */
    private static Body toBody(OrderCheckResultsVo orderCheckResults, ObjectFactory objectFactory) throws JAXBException {
        Body body = objectFactory.createBody();

        DrugCheck drugCheck = objectFactory.createDrugCheck();
        body.setDrugCheck(drugCheck);

        if (orderCheckResults.getDrugsNotChecked() != null && !orderCheckResults.getDrugsNotChecked().isEmpty()) {
            drugCheck.setDrugsNotChecked(toDrugsNotChecked(orderCheckResults.getDrugsNotChecked(), objectFactory));
        }

        if (orderCheckResults.getDrugDrugCheckResults() != null) {
            drugCheck.setDrugDrugChecks(toDrugDrugChecks(orderCheckResults.getDrugDrugCheckResults(), objectFactory));
        }

        if (orderCheckResults.getDrugTherapyCheckResults() != null) {
            drugCheck
                .setDrugTherapyChecks(toDrugTherapyChecks(orderCheckResults.getDrugTherapyCheckResults(), objectFactory));
        }

        if (orderCheckResults.getDrugDoseCheckResults() != null) {
            drugCheck.setDrugDoseChecks(toDrugDoseChecks(orderCheckResults.getDrugDoseCheckResults(), objectFactory));
        }

        return body;
    }

    /**
     * Convert the DrugCheckResultsVo into a DrugDoseChecks XML object.
     * 
     * @param drugDoseCheckResults DrugCheckResultsVo from the OrderCheckResultsVo
     * @param objectFactory ObjectFactory to use to create XML objects
     * @return DrugDoseChecks from DrugCheckResultsVo
     * @throws JAXBException if cannot instantiate XML objects
     */
    private static DrugDoseChecks toDrugDoseChecks(DrugCheckResultsVo<DrugDoseCheckResultVo> drugDoseCheckResults,
                                                   ObjectFactory objectFactory) throws JAXBException {

        DrugDoseChecks drugChecks = objectFactory.createDrugDoseChecks();

        if (!drugDoseCheckResults.getChecks().isEmpty()) {
            for (DrugDoseCheckResultVo resultVO : drugDoseCheckResults.getChecks()) {
                DrugDoseCheck drugDoseCheck = objectFactory.createDrugDoseCheck();
                drugDoseCheck.setDrug(toDrug(resultVO.getDrug(), objectFactory));

                if (!drugDoseCheckResults.getMessages().isEmpty()) {
                    Iterator<DrugCheckMessageVo> iter2 = drugDoseCheckResults.getMessages().iterator();

                    // look for messages with same GCNSeqNo as doseCheckResult
                    while (iter2.hasNext()) {
                        DrugCheckMessageVo drugCheckMessage = iter2.next();

                        if (drugCheckMessage.getDrug().getGcnSeqNo() == resultVO.getDrug().getGcnSeqNo()) {
                            drugDoseCheck.getMessage().add(toMessage(drugCheckMessage, objectFactory));
                            iter2.remove();
                        }
                    }
                }

                if (resultVO.getDrug().isSpecificDoseCheck()) {
                    drugDoseCheck.setSingleDoseStatus(DoseStatus.fromValue(resultVO.getSingleDoseStatus()));
                    drugDoseCheck.setSingleDoseStatusCode(resultVO.getSingleDoseStatusCode());
                    drugDoseCheck.setSingleDoseMessage(resultVO.getSingleDoseMessage());
                    drugDoseCheck.setSingleDoseMax(resultVO.getSingleDoseMax());
                    drugDoseCheck.setRangeDoseStatus(DoseStatus.fromValue(resultVO.getRangeDoseStatus()));
                    drugDoseCheck.setRangeDoseStatusCode(resultVO.getRangeDoseStatusCode());
                    drugDoseCheck.setRangeDoseMessage(resultVO.getRangeDoseMessage());
                    drugDoseCheck.setRangeDoseLow(resultVO.getRangeDoseLow());
                    drugDoseCheck.setRangeDoseHigh(resultVO.getRangeDoseHigh());
                    drugDoseCheck.setDurationStatus(DoseStatus.fromValue(resultVO.getDurationStatus()));
                    drugDoseCheck.setDurationStatusCode(resultVO.getDurationStatusCode());
                    drugDoseCheck.setDurationMessage(resultVO.getDurationMessage());
                    drugDoseCheck.setFrequencyStatus(DoseStatus.fromValue(resultVO.getFrequencyStatus()));
                    drugDoseCheck.setFrequencyStatusCode(resultVO.getFrequencyStatusCode());
                    drugDoseCheck.setFrequencyMessage(resultVO.getFrequencyMessage());
                    drugDoseCheck.setDailyDoseStatus(DoseStatus.fromValue(resultVO.getDailyDoseStatus()));
                    drugDoseCheck.setDailyDoseStatusCode(resultVO.getDailyDoseStatusCode());
                    drugDoseCheck.setDailyDoseMessage(resultVO.getDailyDoseMessage());
                    drugDoseCheck.setMaxDailyDoseStatus(DoseStatus.fromValue(resultVO.getMaxDailyDoseStatus()));
                    drugDoseCheck.setMaxDailyDoseStatusCode(resultVO.getMaxDailyDoseStatusCode());
                    drugDoseCheck.setMaxDailyDoseMessage(resultVO.getMaxDailyDoseMessage());
                    drugDoseCheck.setMaxLifetimeDose(resultVO.getMaxLifetimeDose());
                }

                // general dose check also gets these fields
                drugDoseCheck.setDoseHigh(resultVO.getDoseHigh());
                drugDoseCheck.setDoseHighUnit(resultVO.getDoseHighUnit());
                drugDoseCheck.setDoseLow(resultVO.getDoseLow());
                drugDoseCheck.setDoseLowUnit(resultVO.getDoseLowUnit());
                drugDoseCheck.setDoseFormHigh(resultVO.getDoseFormHigh());
                drugDoseCheck.setDoseFormHighUnit(resultVO.getDoseFormHighUnit());
                drugDoseCheck.setDoseFormLow(resultVO.getDoseFormLow());
                drugDoseCheck.setDoseFormLowUnit(resultVO.getDoseFormLowUnit());
                drugDoseCheck.setDoseRouteDescription(resultVO.getDoseRouteDescription());

                drugChecks.getDrugDoseCheck().add(drugDoseCheck);
            }
        }

        if (!drugDoseCheckResults.getMessages().isEmpty()) {
            drugChecks.getMessage().addAll(toMessages(drugDoseCheckResults.getMessages(), objectFactory));
        }

        return drugChecks;
    }

    /**
     * Create a new {@link Drug} from a {@link DrugCheckVo}.
     * 
     * @param drugCheck {@link DrugCheckVo}
     * @param objectFactory {@link ObjectFactory} to create {@link Drug}
     * @return {@link Drug} with values set from given {@link DrugCheckVo}
     */
    private static Drug toDrug(DrugCheckVo drugCheck, ObjectFactory objectFactory) {
        Drug drug = objectFactory.createDrug();

        // required attributes
        drug.setGcnSeqNo(new BigInteger(drugCheck.getGcnSeqNo()));
        drug.setOrderNumber(drugCheck.getOrderNumber());

        // optional attributes
        if (drugCheck.getIen() != null && drugCheck.getIen().trim().length() > 0) {
            drug.setIen(new BigInteger(drugCheck.getIen()));
        }

        if (drugCheck.getVuid() != null && drugCheck.getVuid().trim().length() > 0) {
            drug.setVuid(new BigInteger(drugCheck.getVuid()));
        }

        if (drugCheck.getDrugName() != null && drugCheck.getDrugName().trim().length() > 0) {
            drug.setDrugName(drugCheck.getDrugName());
        }

        return drug;
    }

    /**
     * Convert the DrugCheckResultsVo into a DrugTherapyChecks XML object.
     * 
     * @param drugTherapyCheckResults DrugCheckResultsVo from OrderCheckResultsVo
     * @param objectFactory ObjectFactory to use to create XML objects
     * @return DrugTherapyChecks from DrugCheckResultsVo
     * @throws JAXBException if cannot instantiate XML objects
     */
    private static DrugTherapyChecks toDrugTherapyChecks(
                                                         DrugCheckResultsVo<DrugTherapyCheckResultVo> drugTherapyCheckResults,
                                                         ObjectFactory objectFactory) throws JAXBException {

        DrugTherapyChecks drugChecks = objectFactory.createDrugTherapyChecks();

        if (!drugTherapyCheckResults.getChecks().isEmpty()) {
            for (DrugTherapyCheckResultVo resultVO : drugTherapyCheckResults.getChecks()) {
                DrugTherapyCheck drugTherapyCheck = objectFactory.createDrugTherapyCheck();
                drugTherapyCheck.setClassification(resultVO.getDuplicateClass());
                drugTherapyCheck.setShortText(resultVO.getShortText());
                drugTherapyCheck.setDuplicateAllowance(resultVO.getAllowance());

                drugTherapyCheck.setInteractedDrugList(toInteractedDrugList(resultVO.getDrugs(), objectFactory));

                drugChecks.getDrugTherapyCheck().add(drugTherapyCheck);
            }
        }

        if (!drugTherapyCheckResults.getMessages().isEmpty()) {
            drugChecks.getMessage().addAll(toMessages(drugTherapyCheckResults.getMessages(), objectFactory));
        }

        return drugChecks;
    }

    /**
     * Convert the DrugCheckResultsVo for the drug interaction check into the DrugDrugChecks XML object.
     * 
     * @param drugDrugCheckResults DrugCheckResultsVo from the OrderCheckResultsVo
     * @param objectFactory ObjectFactory to use to create XML objects
     * @return DrugDrugChecks XML object from DrugCheckResultsVo
     * @throws JAXBException if cannot instantiate XML object
     */
    private static DrugDrugChecks toDrugDrugChecks(DrugCheckResultsVo<DrugDrugCheckResultVo> drugDrugCheckResults,
                                                   ObjectFactory objectFactory) throws JAXBException {
        DrugDrugChecks drugChecks = objectFactory.createDrugDrugChecks();

        if (!drugDrugCheckResults.getChecks().isEmpty()) {
            for (DrugDrugCheckResultVo resultVo : drugDrugCheckResults.getChecks()) {
                DrugDrugCheck drugDrugCheck = objectFactory.createDrugDrugCheck();
                drugDrugCheck.setSeverity(resultVo.getSeverity());
                drugDrugCheck.setShortText(resultVo.getShortText());
                drugDrugCheck.setInteraction(resultVo.getInteractionDescription());

                // Copy the Professional Monograph, only if the data came in from FDB
                if (resultVo.getProfessionalMonograph() != null) {
                    drugDrugCheck.setProfessionalMonograph(objectFactory.createProfessionalMonograph());
                    ProfessionalMonograph professionalMonograph = drugDrugCheck.getProfessionalMonograph();

                    if (resultVo.getProfessionalMonograph().isFdbMonographSourceType()) {
                        professionalMonograph.setMonographSource(MonographSourceType.FDB);
                    } else {
                        professionalMonograph.setMonographSource(MonographSourceType.CUSTOM);
                    }

                    professionalMonograph.setDisclaimer(resultVo.getProfessionalMonograph().getDisclaimer());
                    professionalMonograph.setMonographTitle(resultVo.getProfessionalMonograph().getMonographTitle());
                    professionalMonograph.setSeverityLevel(resultVo.getProfessionalMonograph().getSeverityLevel());
                    professionalMonograph.setMechanismOfAction(resultVo.getProfessionalMonograph().getMechanismOfAction());
                    professionalMonograph.setClinicalEffects(resultVo.getProfessionalMonograph().getClinicalEffects());
                    professionalMonograph.setPredisposingFactors(resultVo.getProfessionalMonograph()
                        .getPredisposingFactors());
                    professionalMonograph.setPatientManagement(resultVo.getProfessionalMonograph().getPatientManagement());
                    professionalMonograph.setDiscussion(resultVo.getProfessionalMonograph().getDiscussion());

                    ReferencesType referencesType = objectFactory.createReferencesType();
                    referencesType.getReference().addAll(resultVo.getProfessionalMonograph().getReferences());
                    professionalMonograph.setReferences(referencesType);
                }

                // Copy the Consumer Monograph, only if the data came in from FDB
                if (resultVo.getConsumerMonograph() != null) {
                    drugDrugCheck.setConsumerMonograph(objectFactory.createConsumerMonograph());
                    ConsumerMonograph consumerMonograph = drugDrugCheck.getConsumerMonograph();

                    if (resultVo.getConsumerMonograph().isFdbMonographSourceType()) {
                        consumerMonograph.setMonographSource(MonographSourceType.FDB);
                    } else {
                        consumerMonograph.setMonographSource(MonographSourceType.CUSTOM);
                    }

                    consumerMonograph.setDisclaimer(resultVo.getConsumerMonograph().getDisclaimer());
                    consumerMonograph.setHowOccurs(resultVo.getConsumerMonograph().getHowOccurs());
                    consumerMonograph.setMedicalWarning(resultVo.getConsumerMonograph().getMedicalWarning());
                    consumerMonograph.setMonographTitle(resultVo.getConsumerMonograph().getMonographTitle());

                    ReferencesType referencesType = objectFactory.createReferencesType();
                    referencesType.getReference().addAll(resultVo.getConsumerMonograph().getReferences());
                    consumerMonograph.setReferences(referencesType);

                    consumerMonograph.setWhatMightHappen(resultVo.getConsumerMonograph().getWhatMightHappen());
                    consumerMonograph.setWhatToDo(resultVo.getConsumerMonograph().getWhatToDo());
                }

                drugDrugCheck.setInteractedDrugList(toInteractedDrugList(resultVo.getDrugs(), objectFactory));

                drugChecks.getDrugDrugCheck().add(drugDrugCheck);
            }
        }

        if (!drugDrugCheckResults.getMessages().isEmpty()) {
            drugChecks.getMessage().addAll(toMessages(drugDrugCheckResults.getMessages(), objectFactory));
        }

        return drugChecks;
    }

    /**
     * Convert a Collection of DrugCheckVo into an InteractedDrugList XML object.
     * 
     * @param drugs Collection of DrugCheckVo
     * @param objectFactory ObjectFactory to use to create XML objects
     * @return InteractedDrugList
     * @throws JAXBException if cannot instantiate XML objects
     */
    private static InteractedDrugList toInteractedDrugList(Collection<DrugCheckVo> drugs, ObjectFactory objectFactory)
        throws JAXBException {

        InteractedDrugList interactedDrugList = objectFactory.createInteractedDrugList();

        for (DrugCheckVo drug : drugs) {
            Drug xmlDrug = objectFactory.createDrug();

            // required attributes
            xmlDrug.setGcnSeqNo(new BigInteger(drug.getGcnSeqNo()));
            xmlDrug.setOrderNumber(drug.getOrderNumber());

            // optional attributes
            if (drug.getIen() != null && drug.getIen().trim().length() > 0) {
                xmlDrug.setIen(new BigInteger(drug.getIen()));
            }

            if (drug.getVuid() != null && drug.getVuid().trim().length() > 0) {
                xmlDrug.setVuid(new BigInteger(drug.getVuid()));
            }

            if (drug.getDrugName() != null && drug.getDrugName().trim().length() > 0) {
                xmlDrug.setDrugName(drug.getDrugName());
            }

            interactedDrugList.getDrug().add(xmlDrug);
        }

        return interactedDrugList;
    }

    /**
     * Convert a Collection of DrugCheckMessageVo into a Collection of MessageType XML objects.
     * 
     * @param drugMessages Collection of DrugCheckMessageVo
     * @param objectFactory ObjectFactory to use to create XML objects
     * @return Collection of MessageType XML objects
     * @throws JAXBException if cannot instantiate XML objects
     */
    private static Collection<Message> toMessages(Collection<DrugCheckMessageVo> drugMessages, ObjectFactory objectFactory)
        throws JAXBException {

        Collection<Message> messages = new ArrayList<Message>();

        for (DrugCheckMessageVo drugCheckMessage : drugMessages) {
            messages.add(toMessage(drugCheckMessage, objectFactory));
        }

        return messages;
    }

    /**
     * Create a {@link Message} from the data in a {@link DrugCheckMessageVo}.
     * 
     * @param drugCheckMessage {@link DrugCheckMessageVo} to convert
     * @param objectFactory {@link ObjectFactory} to use to create {@link Message}
     * @return {@link Message} with data from given {@link DrugCheckMessageVo}
     */
    private static Message toMessage(DrugCheckMessageVo drugCheckMessage, ObjectFactory objectFactory) {
        Message message = objectFactory.createMessage();
        message.setDrug(toDrug(drugCheckMessage.getDrug(), objectFactory));
        message.setSeverity(MessageSeverity.fromValue(drugCheckMessage.getSeverity()));
        message.setType(MessageTypeType.fromValue(drugCheckMessage.getType()));
        message.setDrugName(drugCheckMessage.getDrugName());
        message.setText(drugCheckMessage.getText());

        return message;
    }

    /**
     * Convert a Collection of drugs not checked from the OrderCheckResultsVo into the DrugsNotChecked XML object.
     * 
     * @param drugsNotChecked Collection of drugs not checked from OrderCheckResultsVo
     * @param objectFactory ObjectFactory to create XML objects with
     * @return DrugsNotChecked
     * @throws JAXBException if cannot instantiate XML objects
     */
    private static DrugsNotChecked toDrugsNotChecked(Collection<DrugCheckVo> drugsNotChecked, ObjectFactory objectFactory)
        throws JAXBException {

        DrugsNotChecked drugs = objectFactory.createDrugsNotChecked();

        for (DrugCheckVo drugCheckVo : drugsNotChecked) {
            DrugNotChecked drugNotChecked = objectFactory.createDrugNotChecked();

            drugNotChecked.setStatus(NotCheckedStatus.UNABLE_TO_LOAD_DRUG_FOR_GCN_SEQ_NO);
            drugNotChecked.setDrug(objectFactory.createDrug());
            drugNotChecked.getDrug().setGcnSeqNo(new BigInteger(drugCheckVo.getGcnSeqNo()));
            drugNotChecked.getDrug().setIen(new BigInteger(drugCheckVo.getIen()));

            if (drugCheckVo.getOrderNumber().trim().length() > 0) {
                drugNotChecked.getDrug().setOrderNumber(drugCheckVo.getOrderNumber());
            }

            if (drugCheckVo.getVuid().trim().length() > 0) {
                drugNotChecked.getDrug().setVuid(new BigInteger(drugCheckVo.getVuid()));
            }

            if (drugCheckVo.getDrugName().trim().length() > 0) {
                drugNotChecked.getDrug().setDrugName(drugCheckVo.getDrugName());
            }

            drugs.getDrugNotChecked().add(drugNotChecked);
        }

        return drugs;
    }

    /**
     * Convert the OrderCheckHeaderVo into the PEPSResponse Header.
     * 
     * @param headerVo OrderCheckHeaderVo
     * @param drugDataVendorVersion DrugDataVendorVersionVo
     * @param objectFactory ObjectFactory to use to create XML objects
     * @return PEPSResponse Header
     * @throws JAXBException if error instantiating objects for Header
     */
    private static Header toHeader(OrderCheckHeaderVo headerVo, DrugDataVendorVersionVo drugDataVendorVersion,
                                   ObjectFactory objectFactory) throws JAXBException {

        Header header = objectFactory.createHeader();

        MServer server = objectFactory.createMServer();
        server.setIp(headerVo.getIp());
        server.setNamespace(headerVo.getNamespace());
        server.setServerName(headerVo.getServerName());
        server.setStationNumber(headerVo.getStationNumber());
        server.setUci(headerVo.getUci());
        header.setMServer(server);

        MUser user = objectFactory.createMUser();
        user.setDuz(headerVo.getDuz());
        user.setJobNumber(headerVo.getJobNumber());
        user.setUserName(headerVo.getUserName());
        header.setMUser(user);

        Time time = objectFactory.createTime();
        time.setValue(headerVo.getTime());
        header.setTime(time);

        PEPSVersion version = objectFactory.createPEPSVersion();
        version.setDifBuildVersion(drugDataVendorVersion.getBuildVersion());
        version.setDifDbVersion(drugDataVendorVersion.getDataVersion());
        version.setDifIssueDate(drugDataVendorVersion.getIssueDate());
        header.setPEPSVersion(version);

        return header;
    }

    /**
     * Convert the given OrderCheckHeaderVo and DrugDataVendorVersionVo into a ping response.
     * 
     * @param header OrderCheckHeaderVo to use for header data
     * @param drugDataVendorVersion DrugDataVendorVersionVo to use for DDV version data
     * @return ping PEPSResposne
     */
    public static PEPSResponse createPingResponse(OrderCheckHeaderVo header, DrugDataVendorVersionVo drugDataVendorVersion) {
        try {
            ObjectFactory objectFactory = new ObjectFactory();
            PEPSResponse response = objectFactory.createPEPSResponse();
            response.setHeader(toHeader(header, drugDataVendorVersion, objectFactory));

            return response;
        } catch (JAXBException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }
    }
}
