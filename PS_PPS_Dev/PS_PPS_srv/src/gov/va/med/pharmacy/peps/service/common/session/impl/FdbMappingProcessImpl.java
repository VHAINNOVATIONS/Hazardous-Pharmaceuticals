/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingProcess;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingService;
import gov.va.med.pharmacy.peps.service.common.utility.DomainMappingCsvFile;


/**
 * FdbMappingService
 *
 */
public class FdbMappingProcessImpl implements FdbMappingProcess, Runnable {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FdbMappingProcessImpl.class);

    private FdbMappingService fdbMappingService;
    private DomainService domainService;
    private PlatformTransactionManager transactionManager;

    @Override
    public void run() {
        try {
            runTask();
        } catch (Exception e) {
            LOG.error("Error running Domain Mapping Task: " + e);
        }

    }

    /**
     * importDomainMapping
     * @throws MigrationException  MigrationException
     * @throws ValidationException ValidationException
     */
    private void importDomainMapping() throws ValidationException, MigrationException {

        List<FdbDomainVo> drugClassList = new ArrayList();
        List<FdbDomainVo> ingredientList = new ArrayList();
        List<FdbDomainVo> dosageFormList = new ArrayList();
        List<FdbDomainVo> drugUnitList = new ArrayList();
        List<FdbDomainVo> genericNameList = new ArrayList();
        FdbDomainVo domain = new FdbDomainVo();

        String fileName = fdbMappingService.getFileName();
        UserVo user = fdbMappingService.getUser();

        int count = 0;

        DomainMappingCsvFile domainMappingCsvFile = new DomainMappingCsvFile();

        domainMappingCsvFile.openForImport(fileName);

        Map<String, Long> drugClassIds = getEplIds(FdbDomainType.DRUG_CLASS);
        Map<String, Long> ingredientIds = getEplIds(FdbDomainType.DRUG_INGREDIENT);
        Map<String, Long> dosageFormIds = getEplIds(FdbDomainType.DOSAGE_FORM);
        Map<String, Long> drugUnitIds = getEplIds(FdbDomainType.DRUG_UNIT);
        Map<String, Long> genericNameIds = getEplIds(FdbDomainType.GENERIC_NAME);

        do {
            domain = new FdbDomainVo();

            try {

                domainMappingCsvFile.getNextDomain(domain);

            } catch (Exception e) {
                LOG.error("Could not get next domain mapping row: " + e);
            }

            if (domain.getDomainType() != null) {
                switch (domain.getDomainType()) {
                    
                    // case of Drug_CLASS
                    case DRUG_CLASS:
                        domain.setEplDomainId(drugClassIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        drugClassList.add(domain);
                        LOG.debug(count + "-Drug Class row returned: " + domain.getName() + "-" + domain.getEplDomainId());
                        break;

                     // case of DRUG_INGREDIENT
                    case DRUG_INGREDIENT:
                        domain.setEplDomainId(ingredientIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        ingredientList.add(domain);
                        LOG.debug(count + "-Drug Ingredient row returned: " + domain.getName());
                        break;

                     // case of DOSAGE_FORM
                    case DOSAGE_FORM:
                        domain.setEplDomainId(dosageFormIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        dosageFormList.add(domain);
                        LOG.debug(count + "-Dosage Form row returned: " + domain.getName());
                        break;

                     // case of DRUG_UNIT
                    case DRUG_UNIT:
                        domain.setEplDomainId(drugUnitIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        drugUnitList.add(domain);
                        LOG.debug(count + "-Drug Unit row returned: " + domain.getName());
                        break;

                     // case of GENERIC_NAME
                    case GENERIC_NAME:
                        domain.setEplDomainId(genericNameIds.get(domain.getEplTerm()));
                        domain.setEplTerm(null);
                        genericNameList.add(domain);
                        LOG.debug(count + "-Generic Name row returned: " + domain.getName());
                        break;

                    default:
                        LOG.debug("We should not land in the Default: " + domain.toString());
                        break;
                }
            }

            count++;

        } while (domain.getDomainType() != null);

        try {
            DefaultTransactionDefinition def =
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setTimeout(PPSConstants.I60);
            TransactionStatus status = transactionManager.getTransaction(def);

            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DRUG_CLASS, drugClassList, user);
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DRUG_INGREDIENT, ingredientList, user);
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DOSAGE_FORM, dosageFormList, user);
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.DRUG_UNIT, drugUnitList, user);
            fdbMappingService.assignFdbDomainAssociation(FdbDomainType.GENERIC_NAME, genericNameList, user);

            transactionManager.commit(status);
            
//            fdbMappingService.setProcessRunning(false);

        } catch (ValidationException e) {
            LOG.error("Error: Validation Exception while importing domain mapping:" + e);
        }

    }

    /**
     * Returns Epl Ids for each domain
     * @param domain FdbDomainType
     * @return Map<String, Long> String: Eplterm - Long: eplId
     */
    private Map<String, Long> getEplIds(FdbDomainType domain) {

        Map<String, Long> eplIds = new LinkedHashMap<String, Long>();
        DefaultTransactionDefinition def =
            new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        def.setTimeout(PPSConstants.I60);
        TransactionStatus status = transactionManager.getTransaction(def);

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
                
                // FDBMappingProcessImpl.getIds DRUG_INGREDIENTS.
            case DRUG_INGREDIENT:
                for (IngredientVo ingredientVo : domainService.getIngredientName()) {
                    eplIds.put(ingredientVo.getValue(), Long.valueOf(ingredientVo.getId()));
                }

                break;
                
             // FDBMappingProcessImpl.getIds DRUG_UNIT.
            case DRUG_UNIT:
                for (DrugUnitVo drugUnitVo : domainService.getUnits()) {
                    eplIds.put(drugUnitVo.getValue(), Long.valueOf(drugUnitVo.getId()));
                }

                break;
                
             // FDBMappingProcessImpl.getIds GENERIC_NAME.
            case GENERIC_NAME:
                for (GenericNameVo genericNameVo : domainService.getGenericNames()) {
                    eplIds.put(genericNameVo.getValue(), Long.valueOf(genericNameVo.getId()));
                }

                break;
            default:

        }
        transactionManager.commit(status);

        return eplIds;
    }

    /**
     * runTask
     * @throws Exception Exception
     */
    private void runTask() throws Exception {
        LOG.debug("Starting Domain Mapping Import");
        importDomainMapping();

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

    }

    /**
     * setFdbMappingService
     * @param fdbMappingService the fdbMappingService to set
     */
    public void setFdbMappingService(FdbMappingService fdbMappingService) {
        this.fdbMappingService = fdbMappingService;
    }

    /**
     * FdbMappingService
     * @return the fdbMappingService FdbMappingService
     */
    public FdbMappingService getFdbMappingService() {
        return fdbMappingService;
    }

    /**
     * getDomainService
     * @return domainService DomainService
     */
    public DomainService getDomainService() {
        return domainService;
    }

    /**
     * setDomainService for FdbMappingPorcessImpl.
     * @param domainService DomainService
     */
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * setTransactionManager
     * 
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * getTransactionManager
     * 
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

}
