/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleIntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.PreferenceType;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * Retrieve possible values for various domain attributes.
 */
public class DomainUtilityImpl implements DomainUtility {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DomainUtilityImpl.class);
    private static Map FIELD_KEY_TO_DOMAIN;

    private DomainService domainService;

    private ManagedItemService managedItemService;

    /**
     * Get the possible values for a particular FieldKey.
     * 
     * @param <T>
     *            type of field to return
     * @param fieldKey
     *            FieldKey for field to return
     * @return instance of field with all possible domain
     */
    @Override
    public <T> T getDomain(FieldKey fieldKey) {
        T domain;

        if (fieldKey.isPrimaryKeyDataField()) {
            domain = (T) getPrimaryKeyDomain(fieldKey);
        } else if (fieldKey.isVistaLinkedDataField()) {
            domain = (T) domainService.getVistaDomain(fieldKey);

//        } else if (FieldKey.LOCAL_WARNING_LABELS.equals(fieldKey)) {
//            domain = (T) domainService.getLocalRxConstult();
//        } else if (FieldKey.NATIONAL_WARNING_LABELS.equals(fieldKey)) {
//            domain = (T) domainService.getNationalRxConstult();
        } else if (FieldKey.LOCAL_DRUG_TEXTS.equals(fieldKey)) {
            domain = (T) managedItemService.retrieve(EntityType.DRUG_TEXT);
        } else if (FieldKey.NATIONAL_DRUG_TEXTS.equals(fieldKey)) {
            domain = (T) domainService.getNationalDrugText();
        } else if (FieldKey.WARNING_MAPPING.equals(fieldKey)) {
            domain = (T) domainService.getCotsRxConsult();
        } else if (FieldKey.PREFERENCE_TYPE.equals(fieldKey)) {
            domain = (T) PreferenceType.getAll();
        } else if (FieldKey.CATEGORIES.equals(fieldKey)) {
            domain = (T) Category.getAll();
        } else if (FieldKey.SUB_CATEGORIES.equals(fieldKey)) {
            domain = (T) SubCategory.getAll();
        } else if (ManagedDataVo.class.isAssignableFrom(fieldKey.getFieldClass())) {
            domain = (T) managedItemService.retrieve(EntityType.toEntityType(fieldKey.getFieldClass()));
        } else {
            domain = (T) FIELD_KEY_TO_DOMAIN.get(fieldKey);
        }

        if (domain == null) {
            LOG.warn("Possible domain values for key '" + fieldKey.getKey()
                + "' could not be found. Please verify the DomainUtility and DomainService are populating these values.");
        }

        return domain;
    }

    /**
     * FieldKeys which represent "primary key" fields must be reloaded every
     * time the Presentation wants to display them, as
     * their lists are dynamic. Currently there is no common way to retrieve
     * these from the DomainService, so each one is
     * placed within an if block. If the FieldKey does not equal one of the
     * known primary key FieldKeys, then null is
     * returned.
     * 
     * @param <T>
     *            type of field to return
     * @param fieldKey
     *            FieldKey for primary key field to return
     * @return instance of field with all possible domain
     */
    private <T> T getPrimaryKeyDomain(FieldKey fieldKey) {
        ListDataField domain = (ListDataField) FIELD_KEY_TO_DOMAIN.get(fieldKey);

        if (domain == null) {
            domain = (ListDataField) DataField.newInstance(fieldKey);
        }

        if (FieldKey.CORRESPONDING_INPATIENT_DRUG.equals(fieldKey)) {
            domain.selectValue(domainService.getCorrespondingInpatientDrugs());
        } else if (FieldKey.CORRESPONDING_OUTPATIENT_DRUG.equals(fieldKey)) {
            domain.selectValue(domainService.getCorrespondingOutpatientDrugs());
        } else if (FieldKey.FORMULARY_ALTERNATIVE.equals(fieldKey)) {
            domain.selectValue(domainService.getFormularyAlternatives());
        } else if (FieldKey.SCHEDULE.equals(fieldKey)) {
            domain.selectValue(domainService.getAdminSchedules());
        } else if (FieldKey.SPECIAL_HANDLINGS.equals(fieldKey)) {
            domain.selectValue(domainService.getSpecialHandlings());
        }

        return (T) domain;
    }

    /**
     * Called by Spring via the init-method attribute set in the
     * xml/spring/PresentationContextTemplate.xml configuration
     * file. Spring calls the method name configured in the init-method
     * attribute after all necessary properties on the bean
     * are set by the Spring container. Therefore, once this method is called
     * the DomainService will be set and ready for use
     * to populate the VA DataFields and Map of Selectables.
     * 
     * Using the init-method avoids having conditions where multiple threads try
     * and instantiate the static variables at the
     * same time via lazy loading, which would be a thread safety issue.
     * 
     * @see http
     *      ://static.springframework.org/spring/docs/2.0.x/reference/beans.html
     *      #beans-factory-lifecycle-initializingbean
     */
    public void init() {
        FIELD_KEY_TO_DOMAIN = new HashMap();

        // add Selectable values
        FIELD_KEY_TO_DOMAIN.put(FieldKey.CLASSIFICATION_TYPE, domainService.getDrugClassTypes());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.COLOR, domainService.getColors());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.CS_FED_SCHEDULE, domainService.getCsFedSchedule());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.OTC_RX_INDICATOR, domainService.getOtcRx());

        // FIELD_KEY_TO_DOMAIN.put(FieldKey.PACKAGE_SIZE, domainService.getPackageSizes());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.SYNONYM_INTENDED_USE, domainService.getIntendedUse());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.SHAPE, domainService.getShapes());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.EXCLUDE_FROM_DOSAGE_CHKS, domainService.getShapes());

        FIELD_KEY_TO_DOMAIN.put(FieldKey.ADMIN_SCHEDULE_TYPE, domainService.getAdminScheduleTypes());

        // FIELD_KEY_TO_DOMAIN.put(FieldKey.WARD_GROUP_FOR_ATC, domainService.getWardGroupForAtc());

        FIELD_KEY_TO_DOMAIN.put(FieldKey.PACKAGE_USAGES, domainService.getPackageUsages());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.LOCAL_MED_ROUTES, domainService.getLocalRoutes());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.OI_SCHEDULE_TYPE, domainService.getOiScheduleTypes());

        // add DataField values
        DataFields dataFields = domainService.getDataFields();

        for (Object key : dataFields.getDataFields().keySet()) {
            FIELD_KEY_TO_DOMAIN.put(key, dataFields.getDataField((FieldKey) key));
        }

        // Add 'alias' field keys, used to support advanced searches on multi-select data fields:

        Map<FieldKey, FieldKey> searchableToMainKeysMap = FieldKey.getMapSearchableToMainKey();

        for (Map.Entry<FieldKey, FieldKey> meNext : searchableToMainKeysMap.entrySet()) {
            FIELD_KEY_TO_DOMAIN.put(meNext.getKey(), FIELD_KEY_TO_DOMAIN.get(meNext.getValue()));
        }

        // The deprecated method requires FieldKey.APPLICATION_PACKAGE_USE be present in the FIELD_KEY_TO_DOMAIN
        // so we have to put the value after putting the valuesing for VA data fields
        FIELD_KEY_TO_DOMAIN.put(FieldKey.POSSIBLE_DOSAGE_PACKAGE, getPossibleDosagesPackage());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.PACKAGES, getPossibleDosagesPackage());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.INTENDED_USE, getPossibleIntendedUse());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.SINGLE_MULTISOURCE_PRODUCT, getSingleMultiSourceProduct());
        FIELD_KEY_TO_DOMAIN.put(FieldKey.SINGLE_MULTISOURCE_NDC, getSingleMultiSourceProduct());
    }

    /**
     * setDomainService
     * 
     * @param domain
     *            domain property
     */
    public void setDomainService(DomainService domain) {
        this.domainService = domain;

    }

    /**
     * gives the list for PossibleDosagesPackage
     * 
     * @return List<PossibleDosagesPackageVo>
     * @deprecated The possible dosages should be retrieved from the
     *             DomainService!
     */
    @Deprecated
    private List<PossibleDosagesPackageVo> getPossibleDosagesPackage() {

        // // get the list values from application package use
        // gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField<String> applicationPkgUse =
        // (gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField<String>) FIELD_KEY_TO_DOMAIN
        // .get(FieldKey.APPLICATION_PACKAGE_USE);
        // List<String> lstAppPkgUseValues = applicationPkgUse.getValue();
        //
        List<PossibleDosagesPackageVo> lstPossibleDosagesPackage = new ArrayList<PossibleDosagesPackageVo>();

        //
        // // itereate over the values from application package use list and add to possible dosages package list value
        // for (String lstVal : lstAppPkgUseValues) {
        PossibleDosagesPackageVo possibleDosagesPackage = new PossibleDosagesPackageVo();
        possibleDosagesPackage.setValue("I-Inpatient");
        lstPossibleDosagesPackage.add(possibleDosagesPackage);

        PossibleDosagesPackageVo possibleDosagesPackage1 = new PossibleDosagesPackageVo();
        possibleDosagesPackage1.setValue("O-Outpatient");
        lstPossibleDosagesPackage.add(possibleDosagesPackage1);

        // }

        return lstPossibleDosagesPackage;
    }

    /**
     * gives the list for PossibleDosagesPackage
     * 
     * @return List<PossibleDosagesPackageVo>
     * @deprecated The possible dosages should be retrieved from the
     *             DomainService!
     */
    @Deprecated
    private List<PossibleIntendedUseVo> getPossibleIntendedUse() {

        // // get the list values from application package use
        // gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField<String> applicationPkgUse =
        // (gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField<String>) FIELD_KEY_TO_DOMAIN
        // .get(FieldKey.APPLICATION_PACKAGE_USE);
        // List<String> lstAppPkgUseValues = applicationPkgUse.getValue();
        //
        List<PossibleIntendedUseVo> lstPossibleDosagesPackage = new ArrayList<PossibleIntendedUseVo>();

        //
        // // itereate over the values from application package use list and add to possible dosages package list value
        // for (String lstVal : lstAppPkgUseValues) {
        PossibleIntendedUseVo possibleDosagesPackage = new PossibleIntendedUseVo();
        possibleDosagesPackage.setValue("Inpatient");
        lstPossibleDosagesPackage.add(possibleDosagesPackage);

        PossibleIntendedUseVo possibleDosagesPackage1 = new PossibleIntendedUseVo();
        possibleDosagesPackage1.setValue("Outpatient");
        lstPossibleDosagesPackage.add(possibleDosagesPackage1);

        // }

        return lstPossibleDosagesPackage;
    }

    /**
     * gets the hard coded list for SingleMultiSource Product
     * 
     * @return List<SingleMultiSourceProductVo>
     */
    private List<SingleMultiSourceProductVo> getSingleMultiSourceProduct() {
        List<SingleMultiSourceProductVo> lstSingleMultiSourceProduct = new ArrayList<SingleMultiSourceProductVo>();

        SingleMultiSourceProductVo singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_MULTI);
        lstSingleMultiSourceProduct.add(singleMultiSourceProduct);

        singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_SINGLE);
        lstSingleMultiSourceProduct.add(singleMultiSourceProduct);

        singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_BOTH);
        lstSingleMultiSourceProduct.add(singleMultiSourceProduct);

        singleMultiSourceProduct = new SingleMultiSourceProductVo();
        singleMultiSourceProduct.setValue(PPSConstants.MULTISOURCE_NEITHER);
        lstSingleMultiSourceProduct.add(singleMultiSourceProduct);

        return lstSingleMultiSourceProduct;
    }

    /**
     * setManagedItemService
     * 
     * @param managedItemService
     *            managedItemService property
     */
    public void setManagedItemService(ManagedItemService managedItemService) {
        this.managedItemService = managedItemService;
    }
}
