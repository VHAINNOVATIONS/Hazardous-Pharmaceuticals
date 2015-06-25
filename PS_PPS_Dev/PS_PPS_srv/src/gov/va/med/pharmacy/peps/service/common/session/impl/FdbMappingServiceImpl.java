/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.task.SimpleAsyncTaskExecutor;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FdbGenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugIngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbGenericNameDomainCapability;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingProcess;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingService;


/**
 * FDBMappingServiceImpl
 *
 */
public class FdbMappingServiceImpl implements FdbMappingService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(FdbMappingServiceImpl.class);
    private static final String SUFT8 = "UTF-8";

//    private static final String PATH = "./tmp/";
    private SimpleAsyncTaskExecutor domainMappingExecutor;
    private FdbMappingProcess fdbMappingProcess;
    private FdbDrugClassDomainCapability fdbDrugClassDomainCapability;
    private FdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability;
    private FdbDrugUnitDomainCapability fdbDrugUnitDomainCapability;
    private FdbDosageFormDomainCapability fdbDosageFormDomainCapability;
    private FdbGenericNameDomainCapability fdbGenericNameDomainCapability;
    private DomainService domainService;

    private String fileName = "";
    private Boolean processRunning = false;
    private UserVo user = null;

    /**
     * FDBMappingServiceImpl
     */
    public FdbMappingServiceImpl() {

    }

    /**
     * FdbMappingServiceImpl
     * @param eplDomainService DomainService
     */
    public FdbMappingServiceImpl(DomainService eplDomainService) {
        super();

        this.domainService = eplDomainService;
    }

    /**
     * getDomainType
     * 
     * @param domainType domainType
     * @param filterDate filter date
     * @return List<FdbVo>
     */
    @Override
    public List<FdbDomainVo> getFdbDomains(FdbDomainType domainType, Date filterDate) {
        List<FdbDomainVo> domains = new ArrayList<FdbDomainVo>();

        switch (domainType) {

            case DOSAGE_FORM:
                List<FdbDosageFormVo> fdbDosageForms = fdbDosageFormDomainCapability.retrieve(filterDate);

                for (FdbDosageFormVo fdbDosageForm : fdbDosageForms) {

                    FdbDomainVo fdbDomainVo = new FdbDomainVo();
                    fdbDomainVo.setDate(fdbDosageForm.getCreatedDate());
                    fdbDomainVo.setEplDomainId(fdbDosageForm.getEplId());
                    fdbDomainVo.setName(fdbDosageForm.getDrugDosageFormDesc());

                    domains.add(fdbDomainVo);
                }

                break;

            case DRUG_CLASS:
                List<FdbDrugClassVo> fdbDrugClasses = fdbDrugClassDomainCapability.retrieve(filterDate);

                for (FdbDrugClassVo fdbDrugClass : fdbDrugClasses) {

                    FdbDomainVo fdbDomainVo = new FdbDomainVo();
                    fdbDomainVo.setDate(fdbDrugClass.getCreatedDate());
                    fdbDomainVo.setEplDomainId(fdbDrugClass.getEplId());
                    fdbDomainVo.setName(fdbDrugClass.getFdbDrugClass());

                    domains.add(fdbDomainVo);
                }

                break;

            case DRUG_INGREDIENT:
                List<FdbDrugIngredientVo> fdbDrugIngredients = fdbDrugIngredientDomainCapability.retrieve(filterDate);

                for (FdbDrugIngredientVo fdbDrugIngredient : fdbDrugIngredients) {

                    FdbDomainVo fdbDomainVo = new FdbDomainVo();
                    fdbDomainVo.setDate(fdbDrugIngredient.getCreatedDate());
                    fdbDomainVo.setEplDomainId(fdbDrugIngredient.getEplId());
                    fdbDomainVo.setName(fdbDrugIngredient.getFdbDrugIngredient());

                    domains.add(fdbDomainVo);
                }

                break;

            case DRUG_UNIT:
                List<FdbDrugUnitVo> fdbDrugUnits = fdbDrugUnitDomainCapability.retrieve(filterDate);

                for (FdbDrugUnitVo fdbDrugUnit : fdbDrugUnits) {

                    FdbDomainVo fdbDomainVo = new FdbDomainVo();
                    fdbDomainVo.setDate(fdbDrugUnit.getCreatedDate());
                    fdbDomainVo.setEplDomainId(fdbDrugUnit.getEplId());
                    fdbDomainVo.setName(fdbDrugUnit.getFdbDrugStrengthunits());

                    domains.add(fdbDomainVo);
                }

                break;
            case GENERIC_NAME:
                List<FdbGenericNameVo> fdbGenericNames = fdbGenericNameDomainCapability.retrieve(filterDate);

                for (FdbGenericNameVo fdbGenericName : fdbGenericNames) {

                    FdbDomainVo fdbDomainVo = new FdbDomainVo();
                    fdbDomainVo.setDate(fdbGenericName.getCreatedDate());
                    fdbDomainVo.setEplDomainId(fdbGenericName.getEplId());
                    fdbDomainVo.setName(fdbGenericName.getFdbGenericDrugname());

                    domains.add(fdbDomainVo);
                }

                break;
            default:

        }

        for (FdbDomainVo fdbDomainVo : domains) {
            try {
                fdbDomainVo.setFdbDomainId(URLEncoder.encode(fdbDomainVo.getName(), SUFT8));
            } catch (UnsupportedEncodingException e) {
                LOG.debug(e);
            }
        }

        return domains;
    }

    /**
     * assignFdbDomainAssociation
     * 
     * @param domainType domainType
     * @param domains domains
     * @param usr user
     * @throws ValidationException ValidationException
     */
    @Override
    public void assignFdbDomainAssociation(FdbDomainType domainType, List<FdbDomainVo> domains, UserVo usr)
        throws ValidationException {

        for (FdbDomainVo fdbDomainVo : domains) {
            try {
                fdbDomainVo.setName(URLDecoder.decode(fdbDomainVo.getFdbDomainId(), SUFT8));
            } catch (UnsupportedEncodingException e) {
                LOG.debug(e);
            }
        }

        switch (domainType) {

            case DOSAGE_FORM:
                List<FdbDosageFormVo> fdbDosageForms = fdbDosageFormDomainCapability.retrieve(null);

                for (FdbDomainVo domain : domains) {

                    for (FdbDosageFormVo fdbDosageForm : fdbDosageForms) {

                        if (domain.getName().equals(fdbDosageForm.getDrugDosageFormDesc())
                            && domain.getEplDomainId() != fdbDosageForm.getEplId()) {

                            fdbDosageForm.setEplId(domain.getEplDomainId());
                            fdbDosageForm.setCreatedDate(domain.getDate());
                            fdbDosageFormDomainCapability.update(fdbDosageForm, usr);
                            break;
                        }
                    }
                }

                break;

            case DRUG_CLASS:
                List<FdbDrugClassVo> fdbDrugClasses = fdbDrugClassDomainCapability.retrieve(null);

                for (FdbDomainVo domain : domains) {

                    for (FdbDrugClassVo fdbDrugClass : fdbDrugClasses) {

                        if (domain.getName().equals(fdbDrugClass.getFdbDrugClass())
                            && domain.getEplDomainId() != fdbDrugClass.getEplId()) {

                            fdbDrugClass.setEplId(domain.getEplDomainId());
                            fdbDrugClass.setCreatedDate(domain.getDate());
                            fdbDrugClassDomainCapability.update(fdbDrugClass, usr);
                            break;
                        }
                    }
                }

                break;

            case DRUG_INGREDIENT:
                List<FdbDrugIngredientVo> fdbDrugIngredients = fdbDrugIngredientDomainCapability.retrieve(null);

                for (FdbDomainVo domain : domains) {

                    for (FdbDrugIngredientVo fdbDrugIngredient : fdbDrugIngredients) {

                        if (domain.getName().equals(fdbDrugIngredient.getFdbDrugIngredient())
                            && domain.getEplDomainId() != fdbDrugIngredient.getEplId()) {

                            fdbDrugIngredient.setEplId(domain.getEplDomainId());
                            fdbDrugIngredient.setCreatedDate(domain.getDate());
                            fdbDrugIngredientDomainCapability.update(fdbDrugIngredient, usr);
                            break;
                        }
                    }
                }

                break;

            case DRUG_UNIT:
                assignFdbDrugUnit(domains, usr);
                break;
            case GENERIC_NAME:
                assignFdbGenericName(domains, usr);
                break;
            default:

        }

    }

    /** 
     * getEplTerms
     * @param domain FdbDomainType
     * @return eplTerms
     */
    public Map<Long, String> getEplTerms(FdbDomainType domain) {

        Map<Long, String> eplTerms = new LinkedHashMap<Long, String>();
        switch (domain) {

            case DOSAGE_FORM:
                for (DosageFormVo dosageFormVo : domainService.getDosageForms()) {
                    eplTerms.put(Long.valueOf(dosageFormVo.getId()), dosageFormVo.getDosageFormName());
                }

                break;
            case DRUG_CLASS:
                for (DrugClassVo drugClassVo : domainService.getDrugClasses()) {
                    eplTerms.put(Long.valueOf(drugClassVo.getId()), drugClassVo.getValue());
                }

                break;
            case DRUG_INGREDIENT:
                for (IngredientVo ingredientVo : domainService.getIngredientName()) {
                    eplTerms.put(Long.valueOf(ingredientVo.getId()), ingredientVo.getValue());
                }

                break;
            case DRUG_UNIT:
                for (DrugUnitVo drugUnitVo : domainService.getUnits()) {
                    eplTerms.put(Long.valueOf(drugUnitVo.getId()), drugUnitVo.getValue());
                }

                break;
            case GENERIC_NAME:
                for (GenericNameVo genericNameVo : domainService.getGenericNames()) {
                    eplTerms.put(Long.valueOf(genericNameVo.getId()), genericNameVo.getValue());
                }

                break;
            default:
                
                // There are no default processes.

        }

        return eplTerms;
    }

    /**
     * getEplIds
     * @param domain FdbDomainType
     * @return Map<String, Long>
     */
    public Map<String, Long> getEplIds(FdbDomainType domain) {

        Map<String, Long> eplIds = new LinkedHashMap<String, Long>();
        switch (domain) {

            case DOSAGE_FORM:
                for (DosageFormVo dosageFormVo : domainService.getDosageForms()) {
                    eplIds.put(dosageFormVo.getDosageFormName(), Long.valueOf(dosageFormVo.getId()));
                }

                break;
            case DRUG_CLASS:
                for (DrugClassVo drugClassVo : domainService.getDrugClasses()) {
                    eplIds.put(drugClassVo.getValue(), Long.valueOf(drugClassVo.getId()));
                }

                break;
            case DRUG_INGREDIENT:
                for (IngredientVo ingredientVo : domainService.getIngredientName()) {
                    eplIds.put(ingredientVo.getValue(), Long.valueOf(ingredientVo.getId()));
                }

                break;
            case DRUG_UNIT:
                for (DrugUnitVo drugUnitVo : domainService.getUnits()) {
                    eplIds.put(drugUnitVo.getValue(), Long.valueOf(drugUnitVo.getId()));
                }

                break;
            case GENERIC_NAME:
                for (GenericNameVo genericNameVo : domainService.getGenericNames()) {
                    eplIds.put(genericNameVo.getValue(), Long.valueOf(genericNameVo.getId()));
                }

                break;
            default:

        }

        // return the correct set of EPL Ids
        return eplIds;
    }

    /**
     * assignFdbGenericName
     * @param domains domains
     * @param usr user
     */
    private void assignFdbDrugUnit(List<FdbDomainVo> domains, UserVo usr) {
        List<FdbDrugUnitVo> fdbDrugUnits = fdbDrugUnitDomainCapability.retrieve(null);

        for (FdbDomainVo domain : domains) {

            for (FdbDrugUnitVo fdbDrugUnit : fdbDrugUnits) {

                if (domain.getName().equals(fdbDrugUnit.getFdbDrugStrengthunits())
                    && domain.getEplDomainId() != fdbDrugUnit.getEplId()) {

                    fdbDrugUnit.setEplId(domain.getEplDomainId());
                    fdbDrugUnit.setCreatedDate(domain.getDate());
                    fdbDrugUnitDomainCapability.update(fdbDrugUnit, usr);
                    break;
                }
            }
        }

    }

    /**
     * assignFdbGenericName
     * @param domains domains
     * @param usr user
     */
    private void assignFdbGenericName(List<FdbDomainVo> domains, UserVo usr) {
        List<FdbGenericNameVo> fdbGenericNames = fdbGenericNameDomainCapability.retrieve(null);

        for (FdbDomainVo domain : domains) {

            for (FdbGenericNameVo fdbGenericName : fdbGenericNames) {

                if (domain.getName().equals(fdbGenericName.getFdbGenericDrugname())
                    && domain.getEplDomainId() != fdbGenericName.getEplId()) {

                    fdbGenericName.setEplId(domain.getEplDomainId());
                    fdbGenericName.setCreatedDate(domain.getDate());
                    fdbGenericNameDomainCapability.update(fdbGenericName, usr);
                    break;
                }
            }
        }
    }

    /**
     * importDomainMapping
     * @param fn String of file name
     * @param usr UserVo current user
     * @throws Exception exception
     */
    public void importDomainMapping(String fn, UserVo usr) throws Exception {

        processRunning = true;
        this.domainMappingExecutor.execute(fdbMappingProcess);
        this.setFileName(fn);
        this.setUser(usr);

    }

    /**
     * Drug Class
     * 
     * @param fdbDrugClassDomainCapability fdbDrugClassDomainCapability
     */
    public void setFdbDrugClassDomainCapability(FdbDrugClassDomainCapability fdbDrugClassDomainCapability) {
        this.fdbDrugClassDomainCapability = fdbDrugClassDomainCapability;
    }

    /**
     * Drug Ingredient
     * 
     * @param fdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability
     */
    public void setFdbDrugIngredientDomainCapability(FdbDrugIngredientDomainCapability fdbDrugIngredientDomainCapability) {
        this.fdbDrugIngredientDomainCapability = fdbDrugIngredientDomainCapability;
    }

    /**
     * Drug Unit
     * 
     * @param fdbDrugUnitDomainCapability fdbDrugUnitDomainCapability
     */
    public void setFdbDrugUnitDomainCapability(FdbDrugUnitDomainCapability fdbDrugUnitDomainCapability) {
        this.fdbDrugUnitDomainCapability = fdbDrugUnitDomainCapability;
    }

    /**
     * Dosage Form
     * 
     * @param fdbDosageFormDomainCapability fdbDosageFormDomainCapability
     */
    public void setFdbDosageFormDomainCapability(FdbDosageFormDomainCapability fdbDosageFormDomainCapability) {
        this.fdbDosageFormDomainCapability = fdbDosageFormDomainCapability;
    }

    /**
     * Generic Name
     * 
     * @param fdbGenericNameDomainCapability fdbGenericNameDomainCapability
     */
    public void setFdbGenericNameDomainCapability(FdbGenericNameDomainCapability fdbGenericNameDomainCapability) {
        this.fdbGenericNameDomainCapability = fdbGenericNameDomainCapability;
    }

    /**
     * getDomainService
     * @return DomainService domainService
     */
    public DomainService getDomainService() {
        return domainService;
    }

    /**
     * setDomainService
     * @param domainService DomainService
     */
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * setFdbMappingProcess
     * @param fdbMappingProcess the fdbMappingProcess to set
     */
    public void setFdbMappingProcess(FdbMappingProcess fdbMappingProcess) {
        this.fdbMappingProcess = fdbMappingProcess;
    }

    /**
     * getFdbMappingProcess
     * @return the fdbMappingProcess
     */
    public FdbMappingProcess getFdbMappingProcess() {
        return fdbMappingProcess;
    }

    /**
     * getDomainMappingExecutor
     * @return domainMappingExecutor SimpleAsyncTaskExecutor
     */
    public SimpleAsyncTaskExecutor getDomainMappingExecutor() {
        return domainMappingExecutor;
    }

    /**
     * setDomainMappingExecutor
     * @param domainMappingExecutor SimpleAsyncTaskExecutor
     */
    public void setDomainMappingExecutor(SimpleAsyncTaskExecutor domainMappingExecutor) {
        this.domainMappingExecutor = domainMappingExecutor;
    }

    /**
     * setFileName
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * getFileName
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * setUser
     * @param user the user to set
     */
    public void setUser(UserVo user) {
        this.user = user;
    }

    /**
     * getUser
     * @return the user
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * setProcessRunning
     * @param processRunning the processRunning to set
     */
    public void setProcessRunning(Boolean processRunning) {
        this.processRunning = processRunning;
    }

    /**
     * getProcessRunning
     * @return the processRunning
     */
    public Boolean getProcessRunning() {
        return processRunning;
    }

}
