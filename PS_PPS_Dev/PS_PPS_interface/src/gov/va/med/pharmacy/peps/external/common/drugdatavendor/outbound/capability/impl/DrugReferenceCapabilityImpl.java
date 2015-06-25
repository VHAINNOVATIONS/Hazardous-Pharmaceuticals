/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionResultVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbNdcVo;
import gov.va.med.pharmacy.peps.common.vo.FdbProductVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportWarningLabelVo;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.external.common.callback.ProductDomainCapabilityCallback;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.document.PmiRequestDocument;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.document.PmiResponseDocument;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.JdbcConnectionFactory;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.PatientMedicationInformationConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DrugMonograph;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.pmi.request.PmiRequest;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.pmi.response.PmiData;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.Language;

import firstdatabank.database.FDBDataManager;
import firstdatabank.database.FDBException;
import firstdatabank.database.FDBSQLException;
import firstdatabank.dif.AHFSClassification;
import firstdatabank.dif.AHFSClassifications;
import firstdatabank.dif.Classification;
import firstdatabank.dif.Classifications;
import firstdatabank.dif.DTClass;
import firstdatabank.dif.DTClasses;
import firstdatabank.dif.DispensableDrug;
import firstdatabank.dif.DispensableGeneric;
import firstdatabank.dif.DispensableGenerics;
import firstdatabank.dif.DrugName;
import firstdatabank.dif.DrugSearchFilter;
import firstdatabank.dif.FDBClassificationsType;
import firstdatabank.dif.FDBCode;
import firstdatabank.dif.FDBCodes;
import firstdatabank.dif.FDBDispensableDrugLoadType;
import firstdatabank.dif.FDBDrugNameLoadType;
import firstdatabank.dif.FDBDrugNameTypeFilter;
import firstdatabank.dif.FDBMonographSource;
import firstdatabank.dif.FDBNotAvailableException;
import firstdatabank.dif.FDBPackagedDrugFilter;
import firstdatabank.dif.FDBPhoneticSearch;
import firstdatabank.dif.FDBRxOTCFilter;
import firstdatabank.dif.FDBSearchMethod;
import firstdatabank.dif.FDBUnknownIDException;
import firstdatabank.dif.FilterCodes;
import firstdatabank.dif.FilterDrugSubset;
import firstdatabank.dif.Imprint;
import firstdatabank.dif.ImprintColors;
import firstdatabank.dif.ImprintShapes;
import firstdatabank.dif.Imprints;
import firstdatabank.dif.Ingredient;
import firstdatabank.dif.Ingredients;
import firstdatabank.dif.Monograph;
import firstdatabank.dif.MonographLines;
import firstdatabank.dif.MonographSection;
import firstdatabank.dif.Navigation;
import firstdatabank.dif.PLWLookupResult;
import firstdatabank.dif.PLWLookupResults;
import firstdatabank.dif.PackagedDrug;
import firstdatabank.dif.PackagedDrugs;
import firstdatabank.dif.SearchFilter;


/**
 * Provides drug reference information.
 */
public class DrugReferenceCapabilityImpl implements DrugReferenceCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(DrugReferenceCapabilityImpl.class);

    private static final String FDB_CE = "FDB-CE";
    private static final String FDB_CS = "FDB-CS";
    private static final String I0002 = "0002";
    private static final String I0_5 = "0.5";
    private static final int SEARCH_SIZE = 500;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);

    private FDBDataManager fdbDataManager;
    private ProductDomainCapabilityCallback productDomainCapability;
    private DrugSearchFilter openFilter = new DrugSearchFilter();

    /**
     * DrugReferenceCapabilityImpl
     */
    public DrugReferenceCapabilityImpl() {
        super();

        openFilter.setIncludeDevices(true);
        openFilter.setIncludeRepackagers(true);
        openFilter.setIncludeObsoleteDrugs(true);
        openFilter.setIncludeSynonyms(true);
        openFilter.setSingleIngredientOnly(false);
        openFilter.setPackagedDrugFilter(FDBPackagedDrugFilter.fdbPDFAll);
        openFilter.setRxOTC(FDBRxOTCFilter.fdbROFAll);
        openFilter.setNameType(FDBDrugNameTypeFilter.fdbDNTFBoth);
        openFilter.setIncludePrivateLabelers(true);
    }

    /**
     * Performs an FDB Search based on Search Criteria.
     * 
     * @param gcnSeqNo
     *            The GCNSeqNo
     * @return ProductVo
     */
    @Override
    public ProductVo retrieveProductByGcn(String gcnSeqNo) {
        ProductVo productVo = new ProductVo();

        return productVo;
    }

    /**
     * retrieveWarningLabels
     * 
     * @param vo
     *            ReportProductVo
     * @return ReportProductVo
     */
    @Override
    public ReportProductVo retrieveWarningLabels(ReportProductVo vo) {

        try {
            DispensableGeneric generic = new DispensableGeneric(getManager());
            generic.load(Long.parseLong(vo.getGcnSeqNo()), "", "");
            PLWLookupResults warningLabels = generic.getLabelWarnings(vo.isSpanish() ? FDB_CS : FDB_CE);
            List<ReportWarningLabelVo> list = new ArrayList<ReportWarningLabelVo>();

            for (int i = 0; i < warningLabels.count(); i++) {
                ReportWarningLabelVo warnVo = new ReportWarningLabelVo();
                PLWLookupResult result = warningLabels.item(i);
                warnVo.setCode(result.getLabelWarningID());
                warnVo.setText(result.getWarningText());
                list.add(warnVo);
            }

            vo.setWarningLabels(list);
        } catch (NumberFormatException e) {
            return vo;
        } catch (FDBUnknownIDException e) {
            return vo;
        } catch (FDBSQLException e) {
            return vo;
        }

        return vo;
    }

    /**
     * Load a monograph.
     * 
     * @param gcnSequenceNumber
     *            GCN Sequence Number
     * @param spanish
     *            true if spanish, false if english
     * @return PatientMedicationInstructionVo
     * 
     * @throws ValidationException
     *             if error loading FDB monograph
     */
    @Override
    public PatientMedicationInstructionVo retrievePatientMedicationInformation(
            long gcnSequenceNumber, boolean spanish) throws ValidationException {
        DrugMonograph drugMonograph;

        try {
            drugMonograph = loadMonograph(gcnSequenceNumber, spanish);
        } catch (Exception e) {

            // Throwing ValiationException instead of
            // InterfaceValidationException so that Presentation layer
            // properly displays the error message
            throw new ValidationException(e,
                    ValidationException.INVALID_MONOGRAPH,
                    e.getLocalizedMessage());
        }

        PatientMedicationInstructionVo pmiVo = new PatientMedicationInstructionVo();

        // set all the PMI fields to return to the GUI
        pmiVo.setBrandName(drugMonograph.getBrandName());
        pmiVo.setTitle(drugMonograph.getTitle());
        pmiVo.setMissedDose(drugMonograph.getMissedDose());
        pmiVo.setPhonetics(drugMonograph.getPhonetics());
        pmiVo.setHowToTake(drugMonograph.getHowToTake());
        pmiVo.setDrugInteractions(drugMonograph.getDrugInteractions());
        pmiVo.setMedicalAlerts(drugMonograph.getMedicalAlerts());
        pmiVo.setNotes(drugMonograph.getNotes());
        pmiVo.setOverdose(drugMonograph.getOverdose());
        pmiVo.setPrecautions(drugMonograph.getPrecautions());
        pmiVo.setStorage(drugMonograph.getStorage());
        pmiVo.setSideEffects(drugMonograph.getSideEffects());
        pmiVo.setUses(drugMonograph.getUses());
        pmiVo.setWarning(drugMonograph.getWarnings());
        pmiVo.setDisclaimer(drugMonograph.getDisclaimer());

        return pmiVo;
    }

    /**
     * Load a monograph.
     * 
     * @param gcnSequenceNumber
     *            GCN Sequence Number
     * @param spanish
     *            true if spanish, false if english
     * @return drug monograph if GCN Sequence # found, null otherwise
     * 
     * @throws FDBSQLException
     *             if error reading from FDB DIF database
     * @throws FDBUnknownIDException
     *             if ID provided is unknown
     */
    private DrugMonograph loadMonograph(long gcnSequenceNumber, boolean spanish)
        throws FDBSQLException, FDBUnknownIDException {

        DispensableGeneric generic = new DispensableGeneric(getManager());
        generic.load(gcnSequenceNumber, "", "");

        Monograph fdbMonograph = generic.getPEMMonograph(spanish ? FDB_CS
                                                                : FDB_CE, FDBMonographSource.fdbMSFDBOnly);

        return new DrugMonograph(getMonographSection(fdbMonograph, "T"),
                getMonographSection(fdbMonograph, "C"),
                getMonographSection(fdbMonograph, "D"),
                getMonographSection(fdbMonograph, "F"),
                getMonographSection(fdbMonograph, "H"),
                getMonographSection(fdbMonograph, "I"),
                getMonographSection(fdbMonograph, "M"),
                getMonographSection(fdbMonograph, "N"),
                getMonographSection(fdbMonograph, "O"),
                getMonographSection(fdbMonograph, "P"),
                getMonographSection(fdbMonograph, "R"),
                getMonographSection(fdbMonograph, "S"),
                getMonographSection(fdbMonograph, "U"),
                getMonographSection(fdbMonograph, "W"),
                getMonographSection(fdbMonograph, "Z"));
    }

    /**
     * Retrieve the PMI data.
     * 
     * @param requestXML request
     * @return XML response
     */
    @Override
    public String retrievePatientMedicationInformation(String requestXML) {
        PmiRequest request = PmiRequestDocument.instance()
                .unmarshal(requestXML);

        long gcnSequenceNumber = locateGcnSequenceNumber(request);

        DrugMonograph monograph = null;

        try {
            monograph = loadMonograph(gcnSequenceNumber,
                    Language.SPANISH.equals(request.getLanguage()));
        } catch (FDBSQLException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR,
                    InterfaceException.DRUG_DATA_VENDOR);
        } catch (FDBUnknownIDException e) {

            // normal flow since FDB exceptions
            // are not bubbled
            LOG.debug(
                    "Returning null as the ID was not found in FDB DIF: "
                            + e.getLocalizedMessage(), e);
        }

        PmiData pmiData = PatientMedicationInformationConverter
                .toPmiData(monograph);

        pmiData.setLanguage(request.getLanguage());
        pmiData.setGcnSequenceNumber(BigInteger.valueOf(gcnSequenceNumber));
        pmiData.setVuid(request.getVuid());

        return PmiResponseDocument.instance().marshal(pmiData);
    }

    /**
     * Locate the GCN Sequence Number for a product if not provided in the
     * request.
     * 
     * @param request PmiRequest
     * @return GCN Sequence #
     */
    private Long locateGcnSequenceNumber(PmiRequest request) {
        if (request.getGcnSequenceNumber() != null) {
            return request.getGcnSequenceNumber().longValue();
        }

        try {
            ProductVo product = productDomainCapability.retrieveByVuId(request
                    .getVuid().toString());

            return Long.valueOf(product.getGcnSequenceNumber());
        } catch (ItemNotFoundException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR,
                    InterfaceException.DRUG_DATA_VENDOR);
        }
    }

    /**
     * Performs an FDB Search based on Search Criteria.
     * 
     * @param fdbSearchOptionVo The search options
     * @param user UserVo
     * @return FDBSearchOptionVo response
     */
    @Override
    public FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo fdbSearchOptionVo,
                                                    UserVo user) {

        SearchFilter searchFilter = setSearchFilter(user);
        DrugSearchFilter drugSearchFilter = setDrugSearchFilter(user);

        FDBSearchOptionVo result = fdbSearchOptionVo;

        LOG.debug("performFDBSeachOption for "
                + fdbSearchOptionVo.getFdbSearchOptionType());

        if (fdbSearchOptionVo.getFdbSearchOptionType() == FDBSearchOptionType.NDC_SEARCH) {
            result = performNDCSearch(fdbSearchOptionVo, searchFilter, drugSearchFilter);
        }

        if (fdbSearchOptionVo.getFdbSearchOptionType() == FDBSearchOptionType.GENERIC_SEARCH) {
            result = performGenericSearch(fdbSearchOptionVo, searchFilter, drugSearchFilter);
        }

        if (fdbSearchOptionVo.getFdbSearchOptionType() == FDBSearchOptionType.LABEL_SEARCH) {
            result = performLabelSearch(fdbSearchOptionVo, searchFilter, drugSearchFilter);
        }

        if (fdbSearchOptionVo.getFdbSearchOptionType() == FDBSearchOptionType.GCNSEQNO_SEARCH) {
            result = performGCNSeqNoSearch(fdbSearchOptionVo);
        }

        if (fdbSearchOptionVo.getFdbSearchOptionType() == FDBSearchOptionType.LABEL_GENERIC_SEARCH) {
            result = performLabelGenericSearch(fdbSearchOptionVo, searchFilter, drugSearchFilter);
        }

        return result;
    }

    /**
     * performs FDB SearchOption (overloaded version)
     *
     * @param pFdbSearchOption pFdbSearchOption
     * @param fdbSearchOptionType fdbSearchOptionType
     * @param user UserVo
     * @return FDBSearchOptionVo
     */
    @Override
    public FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo pFdbSearchOption,
                                                        FDBSearchOptionType fdbSearchOptionType,
                                                        UserVo user) {

        SearchFilter searchFilter = setSearchFilter(user);
        DrugSearchFilter drugSearchFilter = setDrugSearchFilter(user);

        pFdbSearchOption.setFdbSearchString(pFdbSearchOption.getFdbSearchString().trim());

        FDBSearchOptionVo result = null;
        switch (fdbSearchOptionType) {
            case ALL:
                result = performAllSearch(pFdbSearchOption, searchFilter, drugSearchFilter);
                break;

            case NDC_SEARCH:
                result = performNDCSearch(pFdbSearchOption, searchFilter, drugSearchFilter);
                break;

            case GENERIC_SEARCH:
                result = performGenericSearch(pFdbSearchOption, searchFilter, drugSearchFilter);
                break;

            case LABEL_SEARCH:
                result = performLabelSearch(pFdbSearchOption, searchFilter, drugSearchFilter);
                break;

            case GCNSEQNO_SEARCH:
                result = performGCNSeqNoSearch(pFdbSearchOption);
                break;

            case LABEL_GENERIC_SEARCH:
                result = performLabelGenericSearch(pFdbSearchOption, searchFilter, drugSearchFilter);
                break;

            case DRUG_CLASS:
                result = performTherapeuticDrugClassSearch(pFdbSearchOption, searchFilter, drugSearchFilter);
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * removeDuplicates - removes duplicate records, comparing the NDC number
     *
     * @param searchList searchList
     * @return Collection FDBSearchOptionResultVo
     */
    public Collection<FDBSearchOptionResultVo> removeDuplicates(List<FDBSearchOptionResultVo> searchList) {
        List<FDBSearchOptionResultVo> results = new ArrayList<FDBSearchOptionResultVo>();
        Set<FDBSearchOptionResultVo> s = new TreeSet<FDBSearchOptionResultVo>(new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                FDBSearchOptionResultVo p1 = (FDBSearchOptionResultVo) o1;
                FDBSearchOptionResultVo p2 = (FDBSearchOptionResultVo) o2;

                return p1.getNDC().compareTo(p2.getNDC());
            }
        });

        s.addAll(searchList);
        results.addAll(s);

        return results;
    }

    /**
     * perform Search on ALL
     *
     * @param pFdbSearchOption pFdbSearchOption
     * @param searchFilter SearchFilter
     * @param drugSearchFilter drugSearchFilter
     * @return FDBSearchOptionVo 
     */
    private FDBSearchOptionVo performAllSearch(FDBSearchOptionVo pFdbSearchOption, SearchFilter searchFilter,
            DrugSearchFilter drugSearchFilter) {

        List<FDBSearchOptionResultVo> searchResultList = new ArrayList<FDBSearchOptionResultVo>();

        FDBSearchOptionVo vo1 = performNDCSearch(pFdbSearchOption, searchFilter, drugSearchFilter);

        if (vo1.getSearchOptionResults().size() > 0) {
            searchResultList.addAll(vo1.getSearchOptionResults());
        }

        FDBSearchOptionVo vo2 = performGenericSearch(pFdbSearchOption, searchFilter, drugSearchFilter);

        if (vo2.getSearchOptionResults().size() > 0) {
            searchResultList.addAll(vo2.getSearchOptionResults());
        }

        FDBSearchOptionVo vo3 = performLabelSearch(pFdbSearchOption, searchFilter, drugSearchFilter);

        if (vo3.getSearchOptionResults().size() > 0) {
            searchResultList.addAll(vo3.getSearchOptionResults());
        }

        FDBSearchOptionVo vo4 = performGCNSeqNoSearch(pFdbSearchOption);

        if (vo4.getSearchOptionResults().size() > 0) {
            searchResultList.addAll(vo4.getSearchOptionResults());
        }

        FDBSearchOptionVo vo6 = performTherapeuticDrugClassSearch(pFdbSearchOption, searchFilter, drugSearchFilter);

        if (vo6.getSearchOptionResults().size() > 0) {
            searchResultList.addAll(vo6.getSearchOptionResults());
        }

        pFdbSearchOption.setSearchOptionResults(removeDuplicates(searchResultList));

        return pFdbSearchOption;
    }

    /**
     * pFdbSearchOption 
     * @param pFdbSearchOption pFdbSearchOption
     * @param searchFilter SearchFilter
     * @param drugSearchFilter DrugSearchFilter
     * @return FDBSearchOptionVo
     */
    private FDBSearchOptionVo performTherapeuticDrugClassSearch(FDBSearchOptionVo pFdbSearchOption,
        SearchFilter searchFilter, DrugSearchFilter drugSearchFilter) {

        Navigation navigation = new Navigation(getManager());

        FDBSearchOptionVo resultsFDBSearchOptionVo = new FDBSearchOptionVo(pFdbSearchOption.getFdbSearchOptionType(),
            pFdbSearchOption.getFdbSearchString());

        resultsFDBSearchOptionVo.setSearchOptionResults(new ArrayList<FDBSearchOptionResultVo>());

        try {
            AHFSClassifications classif = navigation.AHFSClassificationSearch(pFdbSearchOption.getFdbSearchString(),
                searchFilter);

            for (int i = 0; i < classif.count(); i++) {
                AHFSClassification ahfs = classif.item(i);
                DispensableGenerics gDrugs = ahfs.getDispensableGenerics(drugSearchFilter);

                for (int index = 0; index < gDrugs.count(); index++) {
                    DispensableGeneric gDrug = gDrugs.item(index);
                    PackagedDrugs packagedDrugs = gDrug.getPackagedDrugs(new FilterDrugSubset());

                    for (int index1 = 0; index1 < packagedDrugs.count(); index1++) {
                        PackagedDrug packagedDrug = packagedDrugs.item(index1);

                        FDBSearchOptionResultVo result = this.createVoFromPackagedDrug(packagedDrug);

                        resultsFDBSearchOptionVo.getSearchOptionResults().add(result);

                        if (resultsFDBSearchOptionVo.getSearchOptionResults().size() > SEARCH_SIZE) {
                            return resultsFDBSearchOptionVo;
                        }

                    }
                }
            }
        } catch (FDBSQLException e1) {
            LOG.error(e1);
        } catch (FDBNotAvailableException e) {
            LOG.error(e);
        }

        return resultsFDBSearchOptionVo;
    }

    /**
     * Performs an Label Name Search and a generic name search of FDB. This
     * search does both a generic name search and a label name search and then
     * merges the results.
     * 
     * @param fdbSearchOptionVo The search options
     * @param searchFilter SearchFilter
     * @param drugSearchFilter DrugSearchFilter
     * @return FDBSearchOptionVo response
     */
    private FDBSearchOptionVo performLabelGenericSearch(
            FDBSearchOptionVo fdbSearchOptionVo, SearchFilter searchFilter, DrugSearchFilter drugSearchFilter) {

        // First do the label and the generic name search
        FDBSearchOptionVo labelResults = performLabelSearch(fdbSearchOptionVo, searchFilter, drugSearchFilter);
        FDBSearchOptionVo genericResults = performGenericSearch(fdbSearchOptionVo, searchFilter, drugSearchFilter);

        // Now get the resulting data sets and merge the results
        Collection<FDBSearchOptionResultVo> labelCollection = labelResults
                .getSearchOptionResults();
        Collection<FDBSearchOptionResultVo> genericCollection = genericResults
                .getSearchOptionResults();

        // Now iterate through the generic collection and if the object is not
        // also in the label
        // collection then add it to the label collection.
        Iterator<FDBSearchOptionResultVo> l2Iterator = genericCollection
                .iterator();

        while (l2Iterator.hasNext()) {
            FDBSearchOptionResultVo obj = l2Iterator.next();

            if (!labelCollection.contains(obj)) {
                labelCollection.add(obj);
            }
        }

        labelResults.setSearchOptionResults(labelCollection);

        return labelResults;
    }

    /**
     * Performs an Label Name Search of FDB
     * 
     * @param fdbSearchOptionVo
     *            The search options
     * @param searchFilter searchFilter
     * @param drugSearchFilter drugSearchFilter
     * @return FDBSearchOptionVo response
     */
    private FDBSearchOptionVo performLabelSearch(
            FDBSearchOptionVo fdbSearchOptionVo, SearchFilter searchFilter, DrugSearchFilter drugSearchFilter) {

        Navigation navigation = null;

        FDBSearchOptionVo resultsFDBSearchOptionVo = new FDBSearchOptionVo(
                fdbSearchOptionVo.getFdbSearchOptionType(),
                fdbSearchOptionVo.getFdbSearchString());
        resultsFDBSearchOptionVo
                .setSearchOptionResults(new ArrayList<FDBSearchOptionResultVo>());

        try {
            navigation = new Navigation(getManager());

            PackagedDrugs drugs = navigation.packagedDrugSearch(
                    fdbSearchOptionVo.getFdbSearchString(), searchFilter,
                    drugSearchFilter);

            for (int drugsIndex = 0; drugsIndex < drugs.count(); drugsIndex++) {
                PackagedDrug drug = drugs.item(drugsIndex);
                FDBSearchOptionResultVo result = this.createVoFromPackagedDrug(drug);
                resultsFDBSearchOptionVo.getSearchOptionResults().add(result);
                LOG.debug(drug.getID() + ":" + resultsFDBSearchOptionVo.getSearchOptionResults().size());

                if (resultsFDBSearchOptionVo.getSearchOptionResults().size() > SEARCH_SIZE) {
                    return resultsFDBSearchOptionVo;
                }

            }
        } catch (Exception e) {
            resultsFDBSearchOptionVo.setErrorMessage(e.toString());
        } catch (Throwable t) {
            resultsFDBSearchOptionVo.setErrorMessage(t.toString());
        }

        return resultsFDBSearchOptionVo;

    }

    /**
     * setSearchFilter
     * @param user user
     * @return SearchFilter
     */
    private SearchFilter setSearchFilter(UserVo user) {
        SearchFilter searchFilter = new SearchFilter();

        for (Iterator iter = user.getSessionPreferences().entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            SessionPreferenceType key = (SessionPreferenceType) entry.getKey();
            String value = (String) entry.getValue();
            LOG.debug(key + ":" + value);

            if (SessionPreferenceType.FDB_PHONETIC_SEARCH.equals(key)) {
                if ("LITERAL".equalsIgnoreCase(value)) {
                    searchFilter.setPhoneticSearch(FDBPhoneticSearch.fdbPSNoPhonetic);
                } else if ("PHONETIC".equalsIgnoreCase(value)) {
                    searchFilter.setPhoneticSearch(FDBPhoneticSearch.fdbPSOnly);
                } else {
                    searchFilter.setPhoneticSearch(FDBPhoneticSearch.fdbPSAfterEmpty);
                }
            }

            if (SessionPreferenceType.FDB_SEARCH_METHODS.equals(key)) {
                if ("BEGINS_WITH_LITERAL".equalsIgnoreCase(value)) {
                    searchFilter.setSearchMethod(FDBSearchMethod.fdbSMSimple);
                } else if ("BEGINS_WITH_EACH_STRING".equalsIgnoreCase(value)) {
                    searchFilter.setSearchMethod(FDBSearchMethod.fdbSMExhaustive);
                } else if ("CONTAINS_LITERAL".equalsIgnoreCase(value)) {
                    searchFilter.setSearchMethod(FDBSearchMethod.fdbSMContainsSimple);
                } else {
                    searchFilter.setSearchMethod(FDBSearchMethod.fdbSMContainsExhaustive);
                }
            }

        }
        

        return searchFilter;
    }

    /**
     * setDrugSearchFilter
     * @param user user
     * @return DrugSearchFilter
     */
    private DrugSearchFilter setDrugSearchFilter(UserVo user) {

        DrugSearchFilter drugSearchFilter = new DrugSearchFilter();

        // set the defaults
        drugSearchFilter.setIncludeObsoleteDrugs(true);
        drugSearchFilter.setIncludeRepackagers(true);
        drugSearchFilter.setIncludePrivateLabelers(true);
        drugSearchFilter.setSingleIngredientOnly(false);
        drugSearchFilter.setIncludeDevices(true);
        drugSearchFilter.setPackagedDrugFilter(FDBPackagedDrugFilter.fdbPDFAll);
        drugSearchFilter.setNameType(FDBDrugNameTypeFilter.fdbDNTFBoth);
        drugSearchFilter.setRxOTC(FDBRxOTCFilter.fdbROFAll);
        FilterCodes codes = new FilterCodes();
        
        for (Iterator iter = user.getSessionPreferences().entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            SessionPreferenceType key = (SessionPreferenceType) entry.getKey();
            String value = (String) entry.getValue();
            LOG.debug(key + ":" + value);

            if (SessionPreferenceType.FDB_OBSOLETE_DRUGS.equals(key)) {
                if (PPSConstants.FALSE.equalsIgnoreCase(value)) {
                    drugSearchFilter.setIncludeObsoleteDrugs(false);
                }
            }

            if (SessionPreferenceType.FDB_REPACKAGERS.equals(key)) {
                if (PPSConstants.FALSE.equalsIgnoreCase(value)) {
                    drugSearchFilter.setIncludeRepackagers(false);
                }
            }

            if (SessionPreferenceType.FDB_PRIVATE_LABELERS.equals(key)) {
                if (PPSConstants.FALSE.equalsIgnoreCase(value)) {
                    drugSearchFilter.setIncludePrivateLabelers(false);
                }
            }

            if (SessionPreferenceType.FDB_SINGLE_INGREDIENT.equals(key)) {
                if (PPSConstants.TRUE.equalsIgnoreCase(value)) {
                    drugSearchFilter.setSingleIngredientOnly(true);
                }
            }

            if (SessionPreferenceType.FDB_DEVICES.equals(key)) {
                if (PPSConstants.FALSE.equalsIgnoreCase(value)) {
                    drugSearchFilter.setIncludeDevices(false);
                }
            }

            if (SessionPreferenceType.FDB_PACKAGED_DRUG.equals(key)) {
                if ("PACKAGED DRUG".equalsIgnoreCase(value)) {
                    drugSearchFilter.setPackagedDrugFilter(FDBPackagedDrugFilter.fdbPDFHasPackagedDrugs);
                } else if ("EQUIVALENT PACKAGE DRUG".equalsIgnoreCase(value)) {
                    drugSearchFilter.setPackagedDrugFilter(FDBPackagedDrugFilter.fdbPDFHasEquivPackagedDrugs);
                }
            }

            if (SessionPreferenceType.FDB_PACKAGED_DRUG.equals(key)) {
                if ("GENERIC".equalsIgnoreCase(value)) {
                    drugSearchFilter.setNameType(FDBDrugNameTypeFilter.fdbDNTFGeneric);
                } else if ("BRAND".equalsIgnoreCase(value)) {
                    drugSearchFilter.setNameType(FDBDrugNameTypeFilter.fdbDNTFBrandName);
                }
            }

            if (SessionPreferenceType.FDB_RX_OTC.equals(key)) {
                if ("RX".equalsIgnoreCase(value)) {
                    drugSearchFilter.setRxOTC(FDBRxOTCFilter.fdbROFRxOnly);
                } else if ("OTC".equalsIgnoreCase(value)) {
                    drugSearchFilter.setRxOTC(FDBRxOTCFilter.fdbROFOTCOnly);
                }
            }
            
            if (SessionPreferenceType.FDB_STATUS_CODE_ACTIVE.equals(key)) {
                if (PPSConstants.TRUE.equalsIgnoreCase(value)) {
                    codes.addItem("0");
                } else {
                    codes.removeItem("0");
                }
            }
            
            if (SessionPreferenceType.FDB_STATUS_CODE_REPLACED.equals(key)) {
                if (PPSConstants.TRUE.equalsIgnoreCase(value)) {
                    codes.addItem("1");
                } else {
                    codes.removeItem("1");
                }
            }
            
            if (SessionPreferenceType.FDB_STATUS_CODE_RETIRED.equals(key)) {
                if (PPSConstants.TRUE.equalsIgnoreCase(value)) {
                    codes.addItem("2");
                } else {
                    codes.removeItem("2");
                }
            }
            
            if (SessionPreferenceType.FDB_STATUS_CODE_INACTIVE.equals(key)) {
                if (PPSConstants.TRUE.equalsIgnoreCase(value)) {
                    codes.addItem("3");
                } else {
                    codes.removeItem("3");
                }
            }
            
            if (SessionPreferenceType.FDB_STATUS_CODE_UNASSOCIATED.equals(key)) {
                if (PPSConstants.TRUE.equalsIgnoreCase(value)) {
                    codes.addItem("9");
                } else {
                    codes.removeItem("9");
                }
            }
        }
        
        drugSearchFilter.setIncludeStatusCodes(codes);

        return drugSearchFilter;
    }

    /**
     * Performs an NDC Search based on Search Criteria.
     * 
     * @param fdbSearchOptionVo
     *            The search options
     * @param searchFilter searchFilter
     * @param drugSearchFilter drugSearchFilter
     * @return FDBSearchOptionVo response
     */
    private FDBSearchOptionVo performNDCSearch(
            FDBSearchOptionVo fdbSearchOptionVo, SearchFilter searchFilter,
            DrugSearchFilter drugSearchFilter) {

        Navigation navigation = new Navigation(getManager());
        PackagedDrugs drugs = null;

        FDBSearchOptionVo resultsFDBSearchOptionVo = new FDBSearchOptionVo(
                fdbSearchOptionVo.getFdbSearchOptionType(),
                fdbSearchOptionVo.getFdbSearchString());

        resultsFDBSearchOptionVo
                .setSearchOptionResults(new ArrayList<FDBSearchOptionResultVo>());

        String searchValue = fdbSearchOptionVo.getFdbSearchString(); //.replaceAll("-", "");

        try {
            drugs = navigation.NDCSearch(searchValue, drugSearchFilter);

            for (int index = 0; index < drugs.count(); index++) {

                if (resultsFDBSearchOptionVo.getSearchOptionResults().size() > SEARCH_SIZE) {
                    return resultsFDBSearchOptionVo;
                }

                PackagedDrug drug = drugs.item(index);
                FDBSearchOptionResultVo result = this
                        .createVoFromPackagedDrug(drug);

                // DF166 - added
                if ("1".equals(drug.getHCFADrugTypeCode())) {
                    result.setStrFederalLegendCode(PPSConstants.PRESCRIPTION);
                } else {
                    result.setStrFederalLegendCode(PPSConstants.OVER_THE_COUNTER);
                }

                resultsFDBSearchOptionVo.getSearchOptionResults().add(result);
            }
        } catch (Exception e) {
            resultsFDBSearchOptionVo.setErrorMessage(e.toString());
        }

        return resultsFDBSearchOptionVo;
    }

    /**
     * Performs an NDC Search based on Search Criteria.
     * 
     * @param fdbSearchOptionVo  The search options
     * @return FDBSearchOptionVo response
     */
    public FDBSearchOptionVo getOneNdc(
            FDBSearchOptionVo fdbSearchOptionVo) {

        Navigation navigation = new Navigation(getManager());
        PackagedDrugs drugs = null;

        FDBSearchOptionVo resultsFDBSearchOptionVo = new FDBSearchOptionVo(
                fdbSearchOptionVo.getFdbSearchOptionType(),
                fdbSearchOptionVo.getFdbSearchString());

        resultsFDBSearchOptionVo
                .setSearchOptionResults(new ArrayList<FDBSearchOptionResultVo>());

        String searchValue = fdbSearchOptionVo.getFdbSearchString().replaceAll(
                "-", "");

        try {
            drugs = navigation.NDCSearch(searchValue, openFilter);

            for (int index = 0; index < drugs.count(); index++) {

                if (resultsFDBSearchOptionVo.getSearchOptionResults().size() > SEARCH_SIZE) {
                    return resultsFDBSearchOptionVo;
                }

                PackagedDrug drug = drugs.item(index);
                FDBSearchOptionResultVo result = createVoFromPackagedDrug(drug);
                resultsFDBSearchOptionVo.getSearchOptionResults().add(result);
            }
        } catch (Exception e) {
            resultsFDBSearchOptionVo.setErrorMessage(e.toString());
        }

        return resultsFDBSearchOptionVo;
    }

    /**
     * SingleMultiSourceProductVo
     * @param code code
     * @return SingleMultiSourceProductVo
     */
    private SingleMultiSourceProductVo getMultiSourceCode(String code) {

        SingleMultiSourceProductVo vo = new SingleMultiSourceProductVo();

        if ("1".equals(code)) {
            vo.setValue(PPSConstants.MULTISOURCE_MULTI);
        } else if ("2".equals(code)) {
            vo.setValue(PPSConstants.MULTISOURCE_SINGLE);
        } else if ("3".equals(code)) {
            vo.setValue(PPSConstants.MULTISOURCE_BOTH);
        } else {
            vo.setValue(PPSConstants.MULTISOURCE_NEITHER);
        }

        return vo;
    }

    /**
     * Retrieve the Multi-Source Code
     * 
     * @param vo ProductVo
     * @return ProductVo
     */
    @Override
    public ProductVo retrieveMultiSource(ProductVo vo) {

        if (StringUtils.isEmpty(vo.getGcnSequenceNumber())) {
            return vo;
        }

        DispensableDrug drug = new DispensableDrug(getManager());

        long drugGcn = new Long(vo.getGcnSequenceNumber()).longValue();

        try {
            drug.load(drugGcn, FDBDispensableDrugLoadType.fdbDDLTGCNSeqNo, "",
                    "", "");

            vo.setSingleMultiSourceProduct(getMultiSourceCode(drug.getMultiSourceCode()));

        } catch (Exception e) {
            LOG.debug("Swallow Exception : " + e.getMessage());
        }

        return vo;
    }

    /**
     * Performs an GCNSeqNo Search of FDB
     * 
     * @param fdbSearchOptionVo
     *            The search options
     * @return FDBSearchOptionVo response
     */
    private FDBSearchOptionVo performGCNSeqNoSearch(
            FDBSearchOptionVo fdbSearchOptionVo) {

        FDBSearchOptionVo resultsFDBSearchOptionVo = new FDBSearchOptionVo(
                fdbSearchOptionVo.getFdbSearchOptionType(),
                fdbSearchOptionVo.getFdbSearchString());
        resultsFDBSearchOptionVo
                .setSearchOptionResults(new ArrayList<FDBSearchOptionResultVo>());

        try {

            // navigation.packagedDrugSearch(searchText, filter, drugFilter,
            // bQuickLoad)

            DispensableGeneric dispDrug = new DispensableGeneric(getManager());
            dispDrug.load(Long.parseLong(fdbSearchOptionVo.getFdbSearchString()),
                "", "");

            PackagedDrugs drugs = dispDrug
                    .getPackagedDrugs(new FilterDrugSubset());

            for (int index = 0; index < drugs.count(); index++) {
                PackagedDrug drug = drugs.item(index);
                FDBSearchOptionResultVo vo = createVoFromPackagedDrug(drug);
                resultsFDBSearchOptionVo.getSearchOptionResults().add(vo);
            }
        } catch (Exception e) {
            resultsFDBSearchOptionVo.setErrorMessage(e.toString());
        }

        return resultsFDBSearchOptionVo;
    }

    /**
     * Performs an Generic Name Search of FDB
     * 
     * @param fdbSearchOptionVo The search options
     * @param searchFilter SearchFilter
     * @param drugSearchFilter drugSearchFilter
     * @return FDBSearchOptionVo response
     */
    private FDBSearchOptionVo performGenericSearch(FDBSearchOptionVo fdbSearchOptionVo,
        SearchFilter searchFilter, DrugSearchFilter drugSearchFilter) {

        Navigation navigation = null;
        DispensableGenerics genericDrugs = null;
        FDBSearchOptionVo resultsFDBSearchOptionVo = new FDBSearchOptionVo(fdbSearchOptionVo.getFdbSearchOptionType(),
            fdbSearchOptionVo.getFdbSearchString());

        resultsFDBSearchOptionVo.setSearchOptionResults(new ArrayList<FDBSearchOptionResultVo>());

        try {
          
           
            
            navigation = new Navigation(getManager());
            genericDrugs = navigation.dispensableGenericDrugSearch(fdbSearchOptionVo.getFdbSearchString(),
                searchFilter, new DrugSearchFilter());

            for (int index = 0; index < genericDrugs.count(); index++) {

                if (resultsFDBSearchOptionVo.getSearchOptionResults().size() > SEARCH_SIZE) {
                    return resultsFDBSearchOptionVo;
                }

                DispensableGeneric genericDrug = genericDrugs.item(index);
                PackagedDrugs packagedDrugs = genericDrug.getPackagedDrugs(new FilterDrugSubset());

                for (int index1 = 0; index1 < packagedDrugs.count(); index1++) {
                    PackagedDrug packagedDrug = packagedDrugs.item(index1);

                    FDBSearchOptionResultVo result = this.createVoFromPackagedDrug(packagedDrug);
                    resultsFDBSearchOptionVo.getSearchOptionResults().add(result);
                    LOG.debug(genericDrug.getGenericDrugID() + ":" + packagedDrug.getID() + ":"
                        + resultsFDBSearchOptionVo.getSearchOptionResults().size());
                }
            }
        } catch (Exception e) {
            resultsFDBSearchOptionVo.setErrorMessage(e.toString());
        }

        return resultsFDBSearchOptionVo;
    }

    /**
     * Populate the elements for the results vo from the packaged drug
     * 
     * @param drug
     *            drug
     * @return FDBSEarchOptionResultVo
     */
    private FDBSearchOptionResultVo createVoFromPackagedDrug(PackagedDrug drug) {

        FDBSearchOptionResultVo vo = new FDBSearchOptionResultVo();

        // set Brandname and tradeName to same variable.
        vo.setBrandName(retrieveTradeName(drug.getDrugNameID()));
        vo.setStrTradeName(vo.getBrandName());
        
        
        vo.setDrugFormCode(drug.getFormatCode());
        vo.setSingleMultiSourceProductVo(getMultiSourceCode(drug.getMultiSourceCode()));

        try {
            Classifications classifications = drug
                    .getClassifications(FDBClassificationsType.fdbCTAHFS);

            for (int i = 0; i < classifications.count(); i++) {
                Classification classification = classifications.item(i);

                if (vo.getDrugClassification() == null) {
                    vo.setDrugClassification(classification.getDescription());
                } else {
                    vo.setDrugClassification(vo.getDrugClassification() + ", "
                            + classification.getDescription());
                }
            }
        } catch (Exception e) {
            vo.setDrugClassification("");
        }

        vo.setOTCIndicator(retrieveOtcRx(drug));

        try {
            DTClasses dtClasses = drug.getDuplicateTherapyClasses();
            String strClasses = "";

            if (dtClasses != null) {

                for (int i = 0; i < dtClasses.count(); i++) {
                    DTClass dtClass = dtClasses.item(i);
                    strClasses = dtClass.getDescription() + " ";
                }
            }

            vo.setDuplicateTherapies(strClasses);
        } catch (Exception e) {
            vo.setDuplicateTherapies("");
        }

        drug.getMultiSourceCode();
        

        vo.setAdditionalDescriptor(drug.getAddDescriptor());
        vo.setDEACode(drug.getFederalDEAClassCode());
        vo.setDosageFormDescription(drug.getDoseForm());
        vo.setDrugStrengthDescription(drug.getStrength()
                + drug.getStrengthUnit());
        vo.setGCNSeqNo(Long.toString(drug.getGCNSeqNo()));
        vo.setGenericName(drug.getGenericDrugName().toUpperCase(Locale.US));
        vo.setLabelName(drug.getDescription());
        vo.setLabelName25(drug.getLabelName25());
        vo.setManufacturerDistributorName(drug.getLabelerName());

        // vo.setNDC(drug.getNDC());
        vo.setNDCFormatCode(this.setNDCFormatCode(drug.getFormatCode()));
        vo.setObsoleteDate(drug.getObsoleteDate());
        vo.setPackageDescription(drug.getPackageDescription());
        vo.setPackageSize(NumberFormatUtility.format(drug.getPackageSize()));
        vo.setPreviousNDC(drug.getPreviousNDC());
        vo.setReplacementNDC(drug.getReplacementNDC());
        vo.setTop200Rank(drug.getTop200Rank());
        vo.setTop50GenRank(drug.getTop50GenRank());
        vo.setUnitDoseIndicator(Boolean.toString(drug.getUnitDosePackaging()));
        vo.setNDC(drug.getID());
        
        try {
            Imprints imprints = drug.getImprints();
            vo.setStrImprint1(drug.getCurrentImprint().getImprint1());
            vo.setStrImprint2(drug.getCurrentImprint().getImprint2());

            if (drug.getCurrentImprint().getScoringCode().equals(I0002)) {
                vo.setStrScored(I0_5);
            }

            for (int imprintIndex = 0; imprintIndex < imprints.count(); imprintIndex++) {
                for (int colorIndex = 0; colorIndex < imprints
                        .item(imprintIndex).getColors().count(); colorIndex++) {
                    if (vo.getColor() == null) {
                        vo.setColor(imprints.item(imprintIndex).getColors()
                                .item(colorIndex).getColorDescription().toUpperCase(Locale.US));
                    } else {
                        vo.setColor(vo.getColor()
                                + " "
                                + imprints.item(imprintIndex).getColors()
                                        .item(colorIndex).getColorDescription().toUpperCase(Locale.US));
                    }
                }

                if (vo.getShape() == null) {
                    vo.setShape(imprints.item(0).getShapeDescription().toUpperCase(Locale.US));
                } else {
                    vo.setShape(vo.getShape() + " "
                            + imprints.item(0).getShapeDescription().toUpperCase(Locale.US));
                }

                if (vo.getFlavor() == null) {
                    vo.setFlavor(imprints.item(0).getFlavorDescription());
                } else {
                    vo.setFlavor(vo.getFlavor() + " "
                            + imprints.item(0).getFlavorDescription());
                }
            }

            if (vo.getColor() == null) {
                vo.setColor("");
            }

            if (vo.getFlavor() == null) {
                vo.setFlavor("");
            }

            if (vo.getShape() == null) {
                vo.setShape("");
            }
            
            
        } catch (Exception e) {
            vo.setColor("");
            vo.setFlavor("");
            vo.setShape("");
        }

        String strFC = drug.getFormatCode();
        
        vo.setNDCFormatCode(setNDCFormatCode(drug.getFormatCode()));

        // don't set it if it is the 11 digit HRI.
        if ("0".equals(strFC)) {
            vo.setStrTenDigitNdc("");
        } else {
            vo.setStrTenDigitNdc(drug.getNDC());
        }
        
        return vo;
    }

    /**
     * retrieveOtcRx
     * @param drug The packaged Drug
     * @return The string represention of the OTC value
     */
    private String retrieveOtcRx(PackagedDrug drug) {
        String value = "";

        try {
            if (drug.getFederalLegendCode() == null
                || drug.getFederalLegendCode().length() < 1) {
                value = "";
            } else {
                Navigation navigation = new Navigation(getManager());
                FDBCodes fdbCodes = navigation.FDBCodesLoad(PPSConstants.I58);

                for (int i = 0; i < fdbCodes.count(); i++) {
                    FDBCode fdbCode = fdbCodes.item(i);

                    if (fdbCode.getCodeValue().equalsIgnoreCase(
                        drug.getFederalLegendCode())) {
                        value = fdbCode.getDescription();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            value = "";
        }

        return value;
    }

    /**
     * This method is used to convert the NDC Formatted code into a human
     * readable string
     * 
     * @param strCode
     *            The FDB Format code
     * @return The human readable string
     */
    public String setNDCFormatCode(String strCode) {
        if (strCode == null) {
            return "";
        } else if ("0".equals(strCode)) {
            return "5-4-2 PIN";
        } else if ("1".equals(strCode)) {
            return "04-4-2 NDC";
        } else if ("2".equals(strCode)) {
            return "5-03-2 NDC";
        } else if ("3".equals(strCode)) {
            return "5-4-01 NDC";
        } else if ("4".equals(strCode)) {
            return "5-03-2 UPC";
        } else if ("5".equals(strCode)) {
            return "5-4-01 UPC";
        } else if ("6".equals(strCode)) {
            return "5-4-10 UPC";
        } else if ("7".equals(strCode)) {
            return "04-4-2 HRI";
        } else {
            return "";
        }
    }

    /**
     * Spring runtime reflection.
     * 
     * @param fdbDataManager
     *            data manager
     */
    public void setFdbDataManager(FDBDataManager fdbDataManager) {
        this.fdbDataManager = fdbDataManager;
    }

    /**
     * Spring runtime reflection.
     * 
     * @param productDomainCapability
     *            productDomainCapability property
     */
    public void setProductDomainCapability(
            ProductDomainCapabilityCallback productDomainCapability) {
        this.productDomainCapability = productDomainCapability;
    }

    /**
     * Get the monograph section. Not all sections are available for all
     * language translations.
     * 
     * @param monograph
     *            monograph
     * @param sectionName
     *            name of section
     * @return section text if present, empty string otherwise
     */
    private String getMonographSection(Monograph monograph, String sectionName) {
        StringBuilder builder = new StringBuilder("");
        MonographSection section = monograph.getSections().getItemBySectionID(
                sectionName);

        if (section != null) {
            MonographLines lines = section.getLines();

            for (int i = 0; i < lines.count(); i++) {
                builder.append(lines.item(i).getLineText());
            }
        }

        return builder.toString();
    }

    /**
     * This method is used to poplulate the FdbAdVo with details for the fdb add queue
     * 
     * @param ndc ndcVO
     * @return NdcVo 
     * @throws FDBException 
     */
    @Override
    public FdbAddVo populateFdbAddVoFields(String ndc) throws FDBException {
        Navigation navigation = new Navigation(getManager());
        PackagedDrugs drugs = null;

        DrugSearchFilter drugSearchFilter = new DrugSearchFilter();
        drugSearchFilter.setIncludeDevices(true);
        drugSearchFilter.setIncludeObsoleteDrugs(true);
        drugSearchFilter.setIncludePrivateLabelers(true);
        drugSearchFilter.setIncludeRepackagers(true);
        drugSearchFilter.setIncludeSynonyms(true);
        drugSearchFilter.setLoadExternalProperties(true);

        String searchValue = ndc.replaceAll("-", "");

        drugs = navigation.NDCSearch(searchValue, drugSearchFilter);

        FdbAddVo fdbAddVo = new FdbAddVo();

        if (drugs.count() == 0) {
            return fdbAddVo;
        }

        PackagedDrug drug = drugs.item(0);
        fdbAddVo.setNdc(drug.getNDCFormatted());
        fdbAddVo.setPackageType(drug.getPackageDescription());
        fdbAddVo.setPackageSize(String.valueOf(drug.getPackageSize()));
        fdbAddVo.setManufacturer(drug.getLabelerName());
        fdbAddVo.setLabelName(drug.getDescription());
        fdbAddVo.setAddDesc(drug.getAddDescriptor());

        // Set tradename field, if it exists
        fdbAddVo.setTradeName(retrieveTradeName(drug.getDrugNameID()));
        fdbAddVo.setFdbProductName(drug.getGenericDrugName().toUpperCase(Locale.US));
        
        //fdbAddVo.setFdbProductName(drug.getGenericDrugName());
        fdbAddVo.setGcnSeqno(Long.valueOf(drug.getGCNSeqNo()));

        try {
            Date date = dateFormat.parse(drug.getAddDate());
            fdbAddVo.setFdbCreationDate(date);

        } catch (ParseException e) {
            fdbAddVo.setFdbCreationDate(new Date());
        }

        return fdbAddVo;
    }

    /**
     * retrieveTradeName
     * @param drugNameId drugNameId
     * @return TradeName
     */
    private String retrieveTradeName(long drugNameId) {

        try {
            DrugName drugName = new DrugName(getManager());
            drugName.load(drugNameId,
                    FDBDrugNameLoadType.fdbDNLTMNID, "", "", "");

            return drugName.getDescription().toUpperCase(Locale.US);
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * This method is used to poplulate the NDCVo with additional data fields
     * from FDB before migration efforts
     * 
     * @param ndcvo ndcVO
     * @return NdcVo 
     * @throws FDBException 
     */
    @Override
    public NdcVo populateFdbNdcFields(NdcVo ndcvo) throws FDBException {
        try {
            Navigation navigation = new Navigation(getManager());
            PackagedDrugs drugs = null;
            String searchValue = ndcvo.getNdc().replaceAll("-", "");
            drugs = navigation.NDCSearch(searchValue, openFilter);
            FdbNdcVo fdbNdcVo = new FdbNdcVo();

            if (drugs.count() == 0) {
                ndcvo.setFdbNdcVo(null);

                return ndcvo;
            }

            PackagedDrug drug = drugs.item(0);
            fdbNdcVo.setDosageForm(drug.getDoseForm());
            fdbNdcVo.setFederalDeaClassCode(drug.getFederalDEAClassCode());
            fdbNdcVo.setFederalLegendCode(retrieveOtcRx(drug));
            fdbNdcVo.setTradeName(retrieveTradeName(drug.getDrugNameID()));
            ndcvo.setFdbProductName(drug.getGenericDrugName().toUpperCase(Locale.US));

            fdbNdcVo.setFormatCode(setNDCFormatCode(drug.getFormatCode()));
            fdbNdcVo.setGcnSeqno(new Long(drug.getGCNSeqNo()));
            fdbNdcVo.setLabelerName(drug.getLabelerName());
            fdbNdcVo.setNdc(drug.getNDC());
            fdbNdcVo.setMultiSourceCode(drug.getMultiSourceCode());
            ndcvo.setSingleMultiSourceProduct(getMultiSourceCode(drug.getMultiSourceCode()));
            fdbNdcVo.setNdcFormatted(drug.getNDCFormatted());
            fdbNdcVo.setObsoleteDate(drug.getObsoleteDate());
            fdbNdcVo.setPackageDescription(drug.getPackageDescription());
            fdbNdcVo.setPackageSize(new Double(drug.getPackageSize()));
            fdbNdcVo.setPreviousNdc(drug.getPreviousNDC());
            fdbNdcVo.setReplacementNdc(drug.getReplacementNDC());
            fdbNdcVo.setStrengthNumeric(new Double(drug.getStrengthNumeric()));
            fdbNdcVo.setStrengthUnit(drug.getStrengthUnit());

            ndcvo.setFdbNdcVo(fdbNdcVo);

            try {
                Imprint imprint = drug.getCurrentImprint();

                if (imprint != null) {
                    ndcvo.setImprint(drug.getCurrentImprint().getImprint1());
                    ndcvo.setImprint2(drug.getCurrentImprint().getImprint2());

                    try {
                        if (drug.getCurrentImprint().getShapeDescription() != null) {
                            if (ndcvo.getShape() == null) {
                                ShapeVo shapeVo = new ShapeVo();
                                ndcvo.setShape(shapeVo);
                            }

                            ndcvo.getShape().setValue(
                                    drug.getCurrentImprint()
                                            .getShapeDescription().toUpperCase(Locale.US));
                        }
                    } catch (Exception e) {

                        // log nothing, this is thrown from
                        // drug.getCurrentImprint when no imprint exists.
                        // Instead of returning null, it throws an exception.
                        e.toString();
                    }

                    // If getImprints().getScored() is a value of 0002
                    // (“Scored”), put in a value of “1/2”, otherwise put in a
                    // value of Empty.
                    if (imprint.getScoringCode()
                            .equals(I0002)) {
                        DataFields<DataField> vadfs = ndcvo.getVaDataFields();
                        vadfs.getDataField(FieldKey.SCORED).addSelection(I0_5);
                    }

                    ImprintColors ic = imprint.getColors();

                    if (ic != null) {
                        if (ic.count() > 0) {
                            ndcvo.getColor().setValue(
                                    drug.getCurrentImprint().getColors()
                                            .item(0).getColorDescription().toUpperCase(Locale.US));
                        }
                    }
                }
            } catch (Exception e) {

                // log nothing, this is thrown from drug.getCurrentImprint when
                // no imprint exists. Instead of returning
                // null, it throws an exception.
                e.toString();
            }

            String strFC = drug.getFormatCode();
            ndcvo.getFdbNdcVo().setFormatCode(setNDCFormatCode(drug.getFormatCode()));

            if ("1".equals(strFC) || "2".equals(strFC) || "3".equals(strFC)) {
                ndcvo.setTenDigitFormatIndication(setNDCFormatCode(drug
                        .getFormatCode()));
                ndcvo.setTenDigitNdc(drug.getNDC());

            }

            if ("4".equals(strFC) || "5".equals(strFC) || "6".equals(strFC)) {
                ndcvo.setUpcUpn(drug.getNDC());
                ndcvo.setTenDigitNdc(drug.getNDC());
            }
        } catch (Exception e) {

            // log nothing, this is thrown from drug.getCurrentImprint when
            // no imprint exists. Instead of returning
            // null, it throws an exception.
            LOG.debug("PopulateNDCFDBFields: " + e.toString());
        }

        return ndcvo;
    }

    /**
     * This method is used to poplulate the NDCVo with additional data fields
     * from FDB before migration efforts
     * 
     * @param gcnSeqNo gcnSeqNo
     * @return FdbProductVo 
     * @throws FDBException 
     */
    @Override
    public FdbProductVo populateFdbProductFields(String gcnSeqNo) throws FDBException {

        FdbProductVo vo = new FdbProductVo();

        try {

            DispensableDrug dispDrug = new DispensableDrug(getManager());
            dispDrug.load(Long.valueOf(gcnSeqNo),
                    FDBDispensableDrugLoadType.fdbDDLTGCNSeqNo, "", "", "");

            vo.setDdConceptType(dispDrug.getConceptType().toString());
            vo.setDdDosageForm(dispDrug.getDoseForm());
            vo.setDdGcnSeqno(dispDrug.getGCNSeqNo());

            if (dispDrug.getHasPackagedDrugs()) {
                vo.setDdHasPackagedDrugs(PPSConstants.T);
            } else {
                vo.setDdHasPackagedDrugs(PPSConstants.F);
            }

            vo.setDdMultisource(dispDrug.getMultiSourceCode());
            vo.setDdDispenseDrugName(dispDrug.getName());
            vo.setDdObsoleteDate(dispDrug.getObsoleteDate());

            if (dispDrug.getReplaced()) {
                vo.setDdReplaced(PPSConstants.T);
            } else {
                vo.setDdReplaced(PPSConstants.F);
            }

            vo.setDdRoute(dispDrug.getRoute());
            vo.setDdStatusCode(dispDrug.getStatusCode());
            vo.setDdStrength(dispDrug.getStrength());
            vo.setDdStrengthUnit(dispDrug.getStrengthUnit());

            DispensableGeneric generic = new DispensableGeneric(getManager());
            generic.load(Long.valueOf(gcnSeqNo), "", "");
            vo.setDgGenericDosageForm(String.valueOf(generic.getGenericDoseFormID()));
            vo.setDgGenericDrugName(generic.getGenericDrugName());

            if (dispDrug.getHasPackagedDrugs()) {
                vo.setDgHasPackagedDrugs(PPSConstants.T);
            } else {
                vo.setDgHasPackagedDrugs(PPSConstants.F);
            }

            vo.setDgGenericDrugId(generic.getGenericDrugID());
            vo.setDgRoute(generic.getRoute());

            if (dispDrug.getSingleIngredient()) {
                vo.setDgSingleIngredient(PPSConstants.T);
            } else {
                vo.setDgSingleIngredient(PPSConstants.F);
            }

            vo.setDgStrength(generic.getStrength());

            // set the mapping fields
            vo.setMappedDosageForm(dispDrug.getDoseForm());
            vo.setMappedGeneric(dispDrug.getGenericDrugName());
            vo.setMappedDrugUnit(dispDrug.getStrengthUnit());

            Ingredients ingredients = dispDrug.getIngredients();
            List<String> list = new ArrayList<String>();

            for (int i = 0; i < ingredients.count(); i++) {
                Ingredient ingredient = ingredients.item(i);
                list.add(ingredient.getDescription());
            }

            vo.setMappedIngredients(list);

            Classifications classifications = dispDrug.getClassifications(FDBClassificationsType.fdbCTAHFS);
            list = new ArrayList<String>();

            for (int i = 0; i < classifications.count(); i++) {
                Classification classification = classifications.item(i);
                list.add(classification.getDescription());
            }

            vo.setMappedDrugClasses(list);

        } catch (Exception e) {
            LOG.debug("Gcn is " + gcnSeqNo + " Error is " + e.toString());
            vo = null;
        }

        return vo;

    }

    /**
     * getColors.
     * 
     * @return List<ColorVo>
     */
    @Override
    public List<ColorVo> getColors() {
        ArrayList<ColorVo> list = new ArrayList<ColorVo>();
        Navigation navigation = new Navigation(getManager());
        ImprintColors colors;

        try {
            colors = navigation.imprintColorsLoad();
        } catch (FDBSQLException e) {
            return list;
        }

        for (int i = 0; i < colors.count(); i++) {
            ColorVo vo = new ColorVo();
            String color = colors.item(i).getColorDescription();
            vo.setValue(color.toUpperCase(Locale.US));
            vo.setId(color);
            list.add(vo);
        }

        return list;
    }

    /**
     * getWarningLabels
     * 
     * @param gcnSeqNo gcnSeqNo
     * @param isEnglish true if english
     * @return List<String>
     */
    public List<String> getWarningLabels(long gcnSeqNo, boolean isEnglish) {

        List<String> list = new ArrayList<String>();

        try {

            DispensableGeneric generic = new DispensableGeneric(getManager());
            generic.load(gcnSeqNo, "", "");

            PLWLookupResults warningLabels;
            warningLabels = generic.getLabelWarnings(isEnglish ? FDB_CE : FDB_CS);

            for (int i = 0; i < warningLabels.count(); i++) {
                PLWLookupResult result = warningLabels.item(i);
                String warningLabel = result.getLabelWarningID() + ":"
                    + result.getWarningText();
                list.add(warningLabel);

            }
        } catch (FDBSQLException e) {
            LOG.debug(e.getMessage());
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }

        return list;
    }

    /**
     * getShapes
     * 
     * @return List<ShapeVo>
     */
    @Override
    public List<ShapeVo> getShapes() {
        ArrayList<ShapeVo> list = new ArrayList<ShapeVo>();
        Navigation navigation = new Navigation(getManager());
        ImprintShapes shapes;

        try {
            shapes = navigation.imprintShapesLoad();
        } catch (FDBSQLException e) {
            return list;
        }

        for (int i = 0; i < shapes.count(); i++) {

            ShapeVo vo = new ShapeVo();
            String shape = shapes.item(i).getDescription();
            vo.setValue(shape.toUpperCase(Locale.US));
            vo.setId(shape);
            list.add(vo);
        }

        return list;
    }

  
    /**
     * getManager returns the JNDI lookup for the manger (threadsafe version) if on the weblogic server
     * and uses the spring injected manager (ie. on the dev box) if now JNDI lookup is available.
     * 
     * @return FDBDataManager
     */
    public FDBDataManager getManager() {
        FDBDataManager manager = null;
    
        try {
            JdbcConnectionFactory factory = new JdbcConnectionFactory();
            factory.setDataSource(getDataSource());
            manager = FDBDataManager.getInstanceUsingFactory(factory);
        } catch (Exception e) {
            manager = this.fdbDataManager;
        }
        
        return manager;
    }
    
    
    /**
     * Uses JNDI lookup to get the datasource. 
     * 
     * @return DataSource
     * @throws NamingException NamingException
     */
    private DataSource getDataSource() throws NamingException {

        // Obtain our environment naming context
        Context initCtx;
        DataSource datasource = null;


        ConfigFileUtility cfu = new ConfigFileUtility();
        initCtx = new InitialContext();
        Hashtable<String, String> ht = new Hashtable<String, String>();

        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

        ht.put(Context.PROVIDER_URL, "t3://localhost:" + cfu.getPort());

        initCtx = new InitialContext(ht);

        // Lookup this DataSouce at the top level of the WebLogic JNDI tree
        datasource = (DataSource) initCtx
            .lookup("datasource.FDB-DIF");

        return datasource;
    }
    
   

}
