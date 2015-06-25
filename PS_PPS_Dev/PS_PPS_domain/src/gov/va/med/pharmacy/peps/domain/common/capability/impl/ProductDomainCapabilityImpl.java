/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import gov.va.med.pharmacy.peps.common.exception.DomainValidationException;
import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReducedCopayVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidItemType;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplCmopIdHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdDrugClassAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdDrugTextNAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdIngredientAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdWarnLabelNAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplReducedCopayDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSynonymDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVuidStatusHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplAtcCanisterDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplCmopIdHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplCsFedScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIntendedUseDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMultiTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNationalPossibleDosageDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugClassAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdSpecHandlingAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplReducedCopayDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSpecialHandlingDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSynonymDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDispenseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDrugClassDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaGenNameDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVuidStatusHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductDoseFormComparator;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductDoseFormComparatorDesc;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductDrugClassComparator;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductDrugClassComparatorDesc;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductGenericComparator;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductGenericComparatorDesc;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductOIComparator;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductOIComparatorDesc;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductSynonymComparator;
import gov.va.med.pharmacy.peps.domain.common.utility.ProductSynonymComparatorDesc;
import gov.va.med.pharmacy.peps.domain.common.utility.SearchQueryUtility;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.ProductConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.ReducedCopayConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.SynonymConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.VuidStatusHistoryConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on ProductVo
 */
public class ProductDomainCapabilityImpl extends ManagedItemDomainCapabilityImpl<ProductVo, EplProductDo> implements
        ProductDomainCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ProductDomainCapabilityImpl.class);
    
    private static final int CHAR_A = 65;
    private static final int CHAR_Z = 90;
    
    private static final String DFNAME = "dfName";
    private static final String DFNAME_PREFIX = DFNAME + ".";
    private static final String ITEM = "item";
    private static final String ITEM_PREFIX = ITEM + ".";
    private static final String ITEM_WHERE_ITEM = "item where item.";
    private static final String JOIN = " JOIN";
    private static final String LIST = "list";
    private static final String LIST_JOIN = LIST + JOIN;
    private static final String LOVS = "lovs";
    private static final String LOVS_JOIN = LOVS + JOIN;
    
    private static final String COUNT_PRODUCT = "select count(*) from EplProductDo product where product.eplOrderableItem = ";

    private static String PRODUCT_EPL_ID = "eplProduct.eplId";
    private static String CMOPID_SUPPLY_LETTER = "X";
    
    private EplProductDao eplProductDao;
    private EplCmopIdHistoryDao eplCmopIdHistoryDao;
    private EplProdDrugClassAssocDao eplProdDrugClassAssocDao;
    private EplProdIngredientAssocDao eplProdIngredientAssocDao;

    private EplSynonymDao eplSynonymDao;

    private NdcDomainCapability ndcDomainCapability;
    private EplProdDrugTextNAssocDao eplProdDrugTextNAssocDao;
    private EplProdWarnLabelNAssocDao eplProdWarnLabelNAssocDao;
    private EplVuidStatusHistoryDao eplVuidStatusHistoryDao;
    private VuidStatusHistoryConverter vuidStatusHistoryConverter;
    private EplReducedCopayDao eplReducedCopayDao;
    private ReducedCopayConverter reducedCopayConverter;
    private ReportDomainCapability reportDomainCapability;

    // Local ONly attributes to be added back for PPSL
    //private DoseUnitDomainCapability doseUnitDomainCapability;
    //private EplAtcCanisterDao eplAtcCanisterDao;
    //private EplLocalPossibleDosageDao eplLocalPossibleDosageDao;
    //private EplNationalPossibleDosageDao eplNationalPossibleDosageDao;
    //private EplProductLabDao eplProductLabDao;
    //private EplProductVitalDao eplProductVitalDao;
    //private EplIfcapItemNumberDao eplIfcapItemNumberDao;
    //private EplProdDrugTextLAssocDao eplProdDrugTextLAssocDao;
    //private EplNdcByOutpatientSiteNdcDao eplNdcByOutpatientSiteNdcDao;
    //private EplProdWarnLabelLAssocDao eplProdWarnLabelLAssocDao;
    private ProductConverter productConverter;
    private SynonymConverter synonymConverter;

    /**
     * Insert the given {@link ManagedItemVo} with a duplicate check.
     * 
     * @param managedItem {@link ManagedItemVo}
     * @param user {@link UserVo} performing the action
     * @return {@link ManagedItemVo} inserted with new ID
     * @throws DuplicateItemException
     *             if item already exists by uniqueness fields
     */
    @Override
    public ProductVo create(ProductVo managedItem, UserVo user) throws DuplicateItemException {
        if (existsByUniquenessFields(managedItem)) {
            throw new DuplicateItemException(DuplicateItemException.PRODUCT_DUPLICATE_ITEM);
        }

        return createWithoutDuplicateCheck(managedItem, user);
    }

    /**
     * Insert the given ProductVo without duplicate check.
     * 
     * @param product ProductVo
     * @param user {@link UserVo} performing the action
     * @return ProductVo inserted ProductVo with new ID
     */
    @Override
    public ProductVo createWithoutDuplicateCheck(ProductVo product, UserVo user) {
        if (product.getId() == null) {
            product.setId(getSeqNumDomainCapability().generateId(product.getEntityType(), user));

            // only assign vaDfOwnerId if dataFields exists
            if (!(product.getVaDataFields().isEmpty())) {
                product.getVaDataFields().setVaDfOwnerId(getSeqNumDomainCapability().generatedOwnerId(user));
            }
        }

        EplProductDo productDo = productConverter.convert(product);

        EplProductDo insertedDo = eplProductDao.insert(productDo, user);
        product.setId(String.valueOf(insertedDo.getEplId()));

        eplProdDrugClassAssocDao.insert(productDo.getEplProdDrugClassAssocs(), user);
        eplProdIngredientAssocDao.insert(productDo.getEplProdIngredientAssocs(), user);

        // eplAtcCanisterDao.insert(productDo.getEplAtcCanisters(), user);
        //  eplNdcByOutpatientSiteNdcDao.insert(productDo.getEplNdcByOutpatientSiteNdcs(), user);

        for (EplSynonymDo synonym : productDo.getEplSynonyms()) {
            
            //entity type 8isn're really releavant right now
            synonym.setId(Long.valueOf(getSeqNumDomainCapability().generateId(EntityType.STANDARD_MED_ROUTE, user)));
        }
        
        eplSynonymDao.insert(productDo.getEplSynonyms(), user);
        
        
        //  eplLocalPossibleDosageDao.insert(productDo.getEplLocalPossibleDosages(), user);
        // eplNationalPossibleDosageDao.insert(productDo.getEplNationalPossibleDosages(), user);

        //  eplProdDrugTextLAssocDao.insert(productDo.getEplProdDrugTextLAssocs(), user);
        eplProdDrugTextNAssocDao.insert(productDo.getEplProdDrugTextNAssocs(), user);

        //  eplIfcapItemNumberDao.insert(productDo.getEplIfcapItemNumbers(), user);
        //  eplProductLabDao.insert(productDo.getEplProductLabs(), user);
        //  eplProductVitalDao.insert(productDo.getEplProductVitals(), user);
        //  eplProdWarnLabelLAssocDao.insert(productDo.getEplProdWarnLabelLAssocs(), user);
        eplProdWarnLabelNAssocDao.insert(productDo.getEplProdWarnLabelNAssocs(), user);

        if (product.getEffectiveDates() != null) {
            for (VuidStatusHistoryVo vuidStatusHistoryVo : product.getEffectiveDates()) {
                vuidStatusHistoryVo.setItemType(VuidItemType.PRODUCTS);
                vuidStatusHistoryVo.setId(getSeqNumDomainCapability().generateId(vuidStatusHistoryVo.getEntityType(), user));
                EplVuidStatusHistoryDo vuidStatusHistoryDo = vuidStatusHistoryConverter.convert(vuidStatusHistoryVo);
                eplVuidStatusHistoryDao.insert(vuidStatusHistoryDo, user);
            }
        }

        if (product.getReducedCopay() != null) {
            for (ReducedCopayVo reducedCopay : product.getReducedCopay()) {
                reducedCopay.setEplId(Long.valueOf(getSeqNumDomainCapability().generateId(EntityType.REDUCED_COPAY, user)));
                reducedCopay.setProductFk(insertedDo.getEplId());
                EplReducedCopayDo eplReducedCopayDo = reducedCopayConverter.convert(reducedCopay);
                eplReducedCopayDao.insert(eplReducedCopayDo, user);
            }
        }

        // only call save if datafields exists
        if (!(product.getVaDataFields().isEmpty())) {
            getDataFieldsDomainCapability().insertEplVaDfOwners(null, productDo, null, null, productDo.getEplVadfOwners(),
                user);
        }

        return product;
    }

    /**
     * this method will always create a cmop id given the va print name based on
     * the generator algorithm and save into the cmop_id_generator table
     * 
     * @param name String
     * @param dispenseUnit String
     * @param user {@link UserVo} performing the action
     * @param categories categories of the product
     * @return generated cmop id
     */
    @Override
    public synchronized String createCmopId(String name, String dispenseUnit, UserVo user, List<Category> categories) {

        // get the first alpha letter. if no letter found return null for now
        StringBuffer letter = null;
        String regx = "[A-Za-z]+"; // substring composed of English alphabet

        Pattern pat = Pattern.compile(regx);
        Matcher mat = pat.matcher(name);

        if (categories.contains(Category.SUPPLY)) {
            letter = new StringBuffer();
            letter.append(CMOPID_SUPPLY_LETTER);
        } else {
            while (mat.find()) {
                letter = new StringBuffer();
                letter.append(mat.group().substring(0, 1).toUpperCase());
                break;
            }
        }

        String cmopIdcreated = null;

        if (letter != null) {
            cmopIdcreated = getSeqNumDomainCapability().generateCmopId(letter, user);

            EplCmopIdHistoryDo hist = new EplCmopIdHistoryDo();
            hist.setCmopIdUsed(cmopIdcreated);
            hist.setVaPrintName(name);
            hist.setDispenseUnit(dispenseUnit);

            eplCmopIdHistoryDao.insert(hist, user);
        }

        return cmopIdcreated;
    }

    /**
     * this method will always create a cmop id given the va print name based on
     * the generator algorithm and save into the cmop_id_generator table
     * 
     * @param cmopId String
     * @param user {@link UserVo} performing the action
     * @param printName categories of the product
     * @param dispenseUnit The dispense Unit
     */
    @Override
    public synchronized void addCmopIdHistory(String cmopId, UserVo user, String printName,
        String dispenseUnit) {

        EplCmopIdHistoryDo hist = new EplCmopIdHistoryDo();
        hist.setCmopIdUsed(cmopId);
        hist.setVaPrintName(printName);
        hist.setDispenseUnit(dispenseUnit);

        eplCmopIdHistoryDao.insert(hist, user);
    }

    /**
     * this method will always create a cmop id given the va print name based on
     * the generator algorithm and save into the cmop_id_generator table
     * 
     * @param user user
     */
    @Override
    public void setCmopIdGenerator(UserVo user) {
        final String suffix = "0000";
        final int valMaxSize = 26;
        int idx = 0;
        String[] values = new String[valMaxSize];
        char letter;
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        // A=65, Z=90
        // make array of strings: A0000 -> Z0000
        for (int i = CHAR_A; i <= CHAR_Z; i++, idx++) {
            letter = (char) i;
            values[idx] = letter + suffix;
            map.put(Character.toString(letter), idx);
        }
        
        List<EplCmopIdHistoryDo> list = eplCmopIdHistoryDao.retrieve();

        for (EplCmopIdHistoryDo value : list) {
            String cmopIdUsed = value.getCmopIdUsed();
            String firstChar = cmopIdUsed.substring(0, 1);

            if (firstChar != null && !firstChar.isEmpty() && !firstChar.equals("X") 
                && map.containsKey(firstChar) && map.get(firstChar) != null) {
                
                // set value where cmopIdUsed begins with any letter, except X, as that is handled separately.
                idx = (map.get(firstChar)).intValue();
                values[idx] = setValue(cmopIdUsed, values[idx]);
            } else if (cmopIdUsed.startsWith("XH")) {
                
                // handles the separate case for X, which is XH.
                values[PPSConstants.I23] = setValue(cmopIdUsed, values[PPSConstants.I23]);
            }
        }

        idx = 0;
        
        // A=65, Z=90
        // set the generator value for each letter
        for (int i = CHAR_A; i <= CHAR_Z; i++, idx++) {
            letter = (char) i;
            getSeqNumDomainCapability().setCmopIdGeneratorValue(Character.toString(letter), values[idx], user);
        }
    }

    /**
     * setValue
     *
     * @param s1 String
     * @param s2 String
     * @return String
     */
    private String setValue(String s1, String s2) {

        if (s1.compareToIgnoreCase(s2) < 1) {
            return s2;
        } else {
            return s1;
        }
    }

    /**
     * gets cmop id given print name
     * 
     * @param vaPrintName String
     * @param dispenseUnit String
     * @return cmop id
     */
    @Override
    public String getCmopIdForVaPrintName(String vaPrintName, String dispenseUnit) {
        String cmopId = null;

        // check to see if we need to add the VuidStatusHistoryRecord
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(
            EplCmopIdHistoryDo.class).append(ITEM_WHERE_ITEM).toString());

        queryBuffer.append(EplCmopIdHistoryDo.VA_PRINT_NAME).append(PPSConstants.LIKE).append(vaPrintName)
            .append(PPSConstants.TICKAND);
        queryBuffer.append(EplCmopIdHistoryDo.DISPENSE_UNIT).append(PPSConstants.LIKE).append(dispenseUnit).append("'");

        List<EplCmopIdHistoryDo> cmopIdList = eplCmopIdHistoryDao.executeHqlQuery(queryBuffer.toString());

        if (cmopIdList == null || cmopIdList.size() == 0) {
            LOG.warn("CMOP ID did not exist for Va Print Name '" + vaPrintName + "', returning null!");
        } else if (cmopIdList.size() == 1) {
            cmopId = cmopIdList.get(0).getCmopIdUsed();
        } else {
            LOG.warn("Multiple cmop ids exist for the same printName and dispense Unit:" + vaPrintName + ":" + dispenseUnit);
        }

        return cmopId;
    }

    /**
     * adds a single synonym
     * @param synonym synonym
     * @param product product
     * @param user user
     */
    @Override
    public void addSingleSynonym(SynonymVo synonym, ProductVo product, UserVo user) {

        EplSynonymDo dataObject = synonymConverter.convert(synonym);
        EplProductDo productObject = new EplProductDo();
        productObject.setEplId(Long.valueOf(product.getId()));
        dataObject.setEplProduct(productObject);
        dataObject.setId(Long.valueOf(getSeqNumDomainCapability().generateId(EntityType.STANDARD_MED_ROUTE, user)));
        synonym.setId(dataObject.getId());
        eplSynonymDao.insert(dataObject, user);
    }

    /**
     * Retrieves the synonyms
     * @param productId productId
     * @return List of synonyms
     */
    @Override
    public List<SynonymVo> retrieveSynonyms(String productId) {
        Long value = new Long(productId);
        List<EplSynonymDo> listDo = eplSynonymDao.retrieve(PRODUCT_EPL_ID, value);

        return synonymConverter.convert(listDo);
    }

    /**
    * Update the gcnseqNo of the given ProductVo. 
    * 
    * @param product ProductVo
    * @param user {@link UserVo} performing the action
    */
    @Override
    public void updateGcnSeqNo(ProductVo product, UserVo user) {
        eplProductDao.executeQuery("update EplProductDo set GCN_SEQNO = " + product.getGcnSequenceNumber()
            + " where epl_id = " + product.getId());
    }

    /**
     * Update the given ProductVo. This method will update the product and all
     * its data fields It does not update any of the ndc's as per rules
     * 
     * @param product ProductVo
     * @param user {@link UserVo} performing the action
     * @return ProductVo
     * @throws DuplicateItemException DuplicateItemException
     */
    @Override
    public ProductVo update(ProductVo product, UserVo user) throws DuplicateItemException {
        EplProductDo updatedProduct = productConverter.convert(product);

        if (!(product.getVaDataFields().isEmpty())) {
            getDataFieldsDomainCapability().update(updatedProduct.getEplVadfOwners(), user);
        }

        // check to see if we need to add the VuidStatusHistoryRecord
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(
            EplVuidStatusHistoryDo.class).append(ITEM_WHERE_ITEM).toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append(PPSConstants.EQUALTICK).append(product.getVuid())
            .append(PPSConstants.TICKAND);
        queryBuffer.append(EplVuidStatusHistoryDo.ITEM_TYPE).append(PPSConstants.EQUAL).append(PPSConstants.VUID_PRODUCT);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());

        if (StringUtils.isNotBlank(updatedProduct.getVuid()) && (vuidStatusHistroyList.size() == 0)) {
            if (!CollectionUtils.isEmpty(product.getEffectiveDates())) {
                product.getEffectiveDates().get(0).setId(getSeqNumDomainCapability().generateId(
                    EntityType.VUID_STATUS_HISTORY, user));
                EplVuidStatusHistoryDo data =
                    vuidStatusHistoryConverter.convert(product.getEffectiveDates().get(0));
                eplVuidStatusHistoryDao.insert(data, user);
            }
        }

        // delete all existing assocs and add the ones from the new Vo
        eplProdDrugClassAssocDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
        updatedProduct.getEplProdDrugClassAssocs().addAll(
            eplProdDrugClassAssocDao.insert(updatedProduct.getEplProdDrugClassAssocs(), user));

//        // delete all existing assocs and add the ones from the new Vo
//        eplProdDrugTextLAssocDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        updatedProduct.getEplProdDrugTextLAssocs().addAll(
//            eplProdDrugTextLAssocDao.insert(updatedProduct.getEplProdDrugTextLAssocs(), user));

        // delete all existing assocs and add the ones from the new Vo
        eplProdDrugTextNAssocDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
        eplProdDrugTextNAssocDao.insert(updatedProduct.getEplProdDrugTextNAssocs(), user);

//        // delete all existing assocs and add the ones from the new Vo
//        eplProdWarnLabelLAssocDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplProdWarnLabelLAssocDao.insert(updatedProduct.getEplProdWarnLabelLAssocs(), user);
//
//        eplProdWarnLabelNAssocDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplProdWarnLabelNAssocDao.insert(updatedProduct.getEplProdWarnLabelNAssocs(), user);

        // update ingredients
        eplProdIngredientAssocDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
        updatedProduct.getEplProdIngredientAssocs().addAll(
            eplProdIngredientAssocDao.insert(updatedProduct.getEplProdIngredientAssocs(), user));

//        // update canisters
//        eplAtcCanisterDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplAtcCanisterDao.insert(updatedProduct.getEplAtcCanisters(), user);
//
//        // labs
//        eplProductLabDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplProductLabDao.insert(updatedProduct.getEplProductLabs(), user);
//
//        // vitals
//        eplProductVitalDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplProductVitalDao.insert(updatedProduct.getEplProductVitals(), user);

        // update inpatient pharmacy locations
//        eplNdcByOutpatientSiteNdcDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplNdcByOutpatientSiteNdcDao.insert(updatedProduct.getEplNdcByOutpatientSiteNdcs(), user);
//
//        // update local dosages
//        eplLocalPossibleDosageDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplLocalPossibleDosageDao.insert(updatedProduct.getEplLocalPossibleDosages(), user);
//
//        // update national dosages
//        eplNationalPossibleDosageDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplNationalPossibleDosageDao.insert(updatedProduct.getEplNationalPossibleDosages(), user);

        // update synonyms
        eplSynonymDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
        
        for (EplSynonymDo synonym : updatedProduct.getEplSynonyms()) {
            
            //entity type 8isn're really releavant right now
            synonym.setId(Long.valueOf(getSeqNumDomainCapability().generateId(EntityType.STANDARD_MED_ROUTE, user)));

            //            eplSynonymDao.insert(synonym, user);
        }
        
        eplSynonymDao.insert(updatedProduct.getEplSynonyms(), user);

//        // update ifcapNumbers
//        eplIfcapItemNumberDao.delete(PRODUCT_EPL_ID, updatedProduct.getEplId());
//        eplIfcapItemNumberDao.insert(updatedProduct.getEplIfcapItemNumbers(), user);

        // update ReducedCopay
        eplReducedCopayDao.executeQuery("delete EplReducedCopayDo where product_fk = " + product.getId());

        for (ReducedCopayVo reducedCopay : product.getReducedCopay()) {
            reducedCopay.setEplId(Long.valueOf(getSeqNumDomainCapability().generateId(EntityType.REDUCED_COPAY, user)));
            reducedCopay.setProductFk(updatedProduct.getEplId());
            EplReducedCopayDo eplReducedCopayDo = reducedCopayConverter.convert(reducedCopay);
            eplReducedCopayDao.insert(eplReducedCopayDo, user);
        }

        // update attributes on product table
        eplProductDao.update(updatedProduct, user);

        return product;

    }

    /**
     * search by simple data field
     * 
     * @param searchType String
     * @param defName String
     * @param value String
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @param localUse boolean
     * @return List of EplProductDo
     * 
     */
    @Override
    protected List<EplProductDo> searchBySimpleDataField(SearchType searchType, String defName, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplProductDo.class)
            .append(PPSConstants.ITEM_JOIN)
                .append(PPSConstants.ITEM, EplProductDo.EPL_VA_DF_OWNERS).append(PPSConstants.OWNERS_JOIN)
                .append(PPSConstants.OWNERS, EplVadfOwnerDo.EPL_VA_DF_NON_LIST_VALUES).append("nonlist JOIN")
                .append("nonlist", EplVadfNonlistValueDo.EPL_VA_DF).append("def"));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append(PPSConstants.AND).append("def.vadfName = '");
        query.append(defName);
        query.append("' AND nonlist.vaDfValue ");

        if (SearchType.CONTAINS.equals(searchType)) {
            query.append(PPSConstants.LIKE_PERCENT).append(value).append("%'");
        } else if (SearchType.BEGINS_WITH.equals(searchType)) {
            query.append(PPSConstants.LIKE).append(value).append("%'");
        } else {
            query.append(PPSConstants.EQUALTICK).append(value).append("'");
        }

        List<EplProductDo> searchBySimpleDataFieldReturnDos = eplProductDao.executeHqlQuery(query.toString());

        return searchBySimpleDataFieldReturnDos;
    }

    /**
     * search by list data field
     * 
     * @param searchType String
     * @param defName String
     * @param value String
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @param localUse boolean
     * @return List EplProductDo
     * 
     */
    @Override
    protected List<EplProductDo> searchByListDataField(SearchType searchType, String defName, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplProductDo.class)
            .append(PPSConstants.ITEM_JOIN)
                .append(PPSConstants.ITEM, EplProductDo.EPL_VA_DF_OWNERS).append(PPSConstants.OWNERS_JOIN)
                .append(PPSConstants.OWNERS, EplVadfOwnerDo.EPL_VA_DF_ASSOC_VALUES).append(LIST_JOIN)
            .append(LIST, EplVadfAssocValueDo.EPL_VA_DF_LOV).append(LOVS_JOIN)
            .append(LOVS, EplVadfLovDo.EPL_VA_DF).append(DFNAME));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses)).append(PPSConstants.AND)
            .append(DFNAME_PREFIX).append(EplVaDfDo.NAME).append(PPSConstants.EQUALTICK).append(defName)
            .append(PPSConstants.TICKAND).append("list.key.listValue ");

        appendSearchTypeConditional(query, searchType, value);

        List<EplProductDo> returnedDos = eplProductDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * search by multi text data field
     * 
     * @param searchType String
     * @param defName defName 2
     * @param value va
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @param localUse boolean
     * @return List of multitext EplProductDo
     * 
     */
    @Override
    protected List<EplProductDo> searchByMultiTextDataField(SearchType searchType, String defName, String value,
        List<ItemStatus> iStatuses, List<RequestItemStatus> rStatuses, LocalUseSearchType localUse) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplProductDo.class)
            .append(PPSConstants.ITEM_JOIN)
                .append(PPSConstants.ITEM, EplProductDo.EPL_VA_DF_OWNERS).append(PPSConstants.OWNERS_JOIN)
                .append(PPSConstants.OWNERS, EplVadfOwnerDo.EPL_VA_DF_MULTI_TEXTS).append(LIST_JOIN)
                .append(LIST, EplMultiTextDo.EPL_VA_DF).append(DFNAME));

        query.append(SearchQueryUtility.createStatusLocaUseQuery(localUse, iStatuses, rStatuses));

        query.append(PPSConstants.AND).append(DFNAME_PREFIX);
        query.append(EplVaDfDo.NAME);
        query.append(PPSConstants.EQUALTICK);
        query.append(defName);
        query.append("' AND  list.key.text ");

        if (SearchType.CONTAINS.equals(searchType)) {
            query.append(PPSConstants.LIKE_PERCENT);
            query.append(value);
            query.append("%'");
        } else if (SearchType.BEGINS_WITH.equals(searchType)) {
            query.append(PPSConstants.LIKE);
            query.append(value);
            query.append("%'");
        } else {
            query.append(PPSConstants.EQUALTICK);
            query.append(value);
            query.append("'");
        }

        List<EplProductDo> returnedDos = eplProductDao.executeHqlQuery(query.toString());

        return returnedDos;
    }

    /**
     * Retrieves a list of all products from database where vadf list value =
     * FORMULARY.
     * 
     * @return list of ProductVo's
     */
    @Override
    public List<ProductVo> getAllFormularyProducts() {
        StringBuffer query = new StringBuffer();
        query.append(
            HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplProductDo.class).append(PPSConstants.ITEM_JOIN)
                .append(PPSConstants.ITEM, EplProductDo.EPL_VA_DF_OWNERS).append(PPSConstants.OWNERS_JOIN)
                .append(PPSConstants.OWNERS, EplVadfOwnerDo.EPL_VA_DF_ASSOC_VALUES).append(LIST_JOIN)
                .append(LIST, EplVadfAssocValueDo.EPL_VA_DF_LOV).append(LOVS_JOIN)
                .append(LOVS, EplVadfLovDo.EPL_VA_DF)
                .append(DFNAME).append(PPSConstants.WHERE).append(DFNAME_PREFIX).appendNoSpace(EplVaDfDo.NAME)
                .append(" = 'formulary'").append(PPSConstants.AND).append("list.key.listValue = 'FORMULARY'")
                .append(PPSConstants.ORDERBY).append(ITEM_PREFIX).appendNoSpace(EplProductDo.VA_PRODUCT_NAME).toString());

        List<EplProductDo> returnedDos = eplProductDao.executeHqlQuery(query.toString());
        List<ProductVo> returnedVos = new ArrayList<ProductVo>();

        for (EplProductDo data : returnedDos) {
            ProductVo prod = productConverter.convertMinimal(data);
            returnedVos.add(prod);
        }

        // return the product value objects for all Formulary products.
        return returnedVos;
    }

    /**
     * Retrieves a list of all products
     * 
     * @return list of String VA PRoduct Names
     */
    @Override
    public List<ProductVo> getAllProducts() {
        List<EplProductDo> returnedDos = eplProductDao.retrieveAscending(EplProductDo.EPL_ID);
        List<ProductVo> returnedVos = new ArrayList<ProductVo>();

        for (EplProductDo data : returnedDos) {
            ProductVo prod = productConverter.convertMinimal(data);
            returnedVos.add(prod);
        }

        return returnedVos;
    }

    /**
     * Retrieves a list of all products marked for Unit Dose and IV in the
     * Application Package Use List data field
     * 
     * @return list of products
     */
    @Override
    public List<ProductVo> getAllUnitDoseAndIvProducts() {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT distinct item FROM").append(EplProductDo.class)
            .append(PPSConstants.ITEM_JOIN).append(PPSConstants.ITEM, EplProductDo.EPL_VA_DF_OWNERS)
            .append(PPSConstants.OWNERS_JOIN).append(PPSConstants.OWNERS, EplVadfOwnerDo.EPL_VA_DF_ASSOC_VALUES)
            .append(LIST_JOIN).append(LIST, EplVadfAssocValueDo.EPL_VA_DF_LOV).append(LOVS_JOIN)
            .append(LOVS, EplVadfLovDo.EPL_VA_DF).append(DFNAME).append(PPSConstants.WHERE).append(DFNAME_PREFIX)
            .appendNoSpace(EplVaDfDo.NAME).append(" = 'application.package.use'")
            .append(PPSConstants.AND).append("(list.key.listValue = 'U-UNIT DOSE' OR list.key.listValue = 'I-IV')")
            .append(PPSConstants.ORDERBY).append(ITEM_PREFIX).appendNoSpace(EplProductDo.VA_PRODUCT_NAME).toString());

        List<EplProductDo> returnedDos = eplProductDao.executeHqlQuery(query.toString());
        List<ProductVo> returnedVos = new ArrayList<ProductVo>();

        for (EplProductDo data : returnedDos) {
            ProductVo prod = productConverter.convertMinimal(data);
            returnedVos.add(prod);
        }

        // return the value objects for all unit does and iv products.
        return returnedVos;
    }

    /**
     * Retrieves a list of all products with a specific gcnSeqNo
     * @param gcn GCNSeqNo
     * @return list of products
     */
    @Override
    public List<ProductVo> getAllProductswithGcn(Long gcn) {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplProductDo.class)
            .append("item WHERE GCN_SEQNO = ").append(gcn.toString()));

        List<EplProductDo> returnedDos = eplProductDao.executeHqlQuery(query.toString());
        List<ProductVo> returnedVos = new ArrayList<ProductVo>();

        for (EplProductDo data : returnedDos) {
            ProductVo prod = productConverter.convertMinimal(data);
            returnedVos.add(prod);
        }

        return returnedVos;
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its
     * uniqueness fields for the ProductDomainCapabilityImpl.
     * 
     * @param item
     *            {@link ManagedItemVo} for which to create uniqueness
     *            {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(ProductVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();

        // Create a criteria that looks for active products with the same VA
        // product name as the specified one,
        // OR ELSE (if the specified item has a ATC mnemonic specified) looks
        // for products that have the same
        // ATC mnemonic as the specified item has.
        String atcMnemonic = item.getAtcMnemonic();

        if (atcMnemonic != null && atcMnemonic.trim().length() > 0) {

            // Note: A Hibernate Disjunction uses "OR" instead of "AND" when
            // Restrictions are added.
            Disjunction disjunction = Restrictions.disjunction();

            disjunction.add(Restrictions.ilike(EplProductDo.VA_PRODUCT_NAME, item.getVaProductName()));
            disjunction.add(Restrictions.ilike(EplProductDo.ATC_MNEMONIC, atcMnemonic.trim()));

            criteria.add(disjunction);
        } else {
            criteria.add(Restrictions.ilike(EplProductDo.VA_PRODUCT_NAME, item.getVaProductName()));
        }

        return criteria;
    }

    /**
     * retrieve
     * @param eplId eplId
     * @return ProductVo
     * @throws ItemNotFoundException 
     */
    @Override
    public ProductVo retrieve(String eplId) throws ItemNotFoundException {
        ProductVo productVo = super.retrieve(eplId);

        StringBuffer queryBuffer =
            new StringBuffer(HqlBuilder.create(PPSConstants.SELECT_ITEM_FROM).append(EplVuidStatusHistoryDo.class)
                .append(" item WHERE ").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append(PPSConstants.EQUALTICK).append(productVo.getVuid())
            .append(PPSConstants.TICKAND).append(EplVuidStatusHistoryDo.ITEM_TYPE).append(PPSConstants.EQUAL)
            .append(PPSConstants.VUID_PRODUCT);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());

        List<VuidStatusHistoryVo> vuidStatusHistroyVoList = vuidStatusHistoryConverter.convert(vuidStatusHistroyList);
        productVo.setEffectiveDates(vuidStatusHistroyVoList);

        return productVo;

    }

    /**
     * Retrieve the ProductVo with the given vuId.
     * 
     * @param vuId
     *            vuId
     * @return ProductVo
     * @throws ItemNotFoundException 
     */
    @Override
    public ProductVo retrieveByVuId(String vuId) throws ItemNotFoundException {
        List<EplProductDo> productList;
        EplProductDo productDo = null;
        List<EplVuidStatusHistoryDo> eplVuidStatusHistoryList;
        List<VuidStatusHistoryVo> vuidStatusHistroyList;

        final String fieldVuid = "vuId ";

        try {
            productList = eplProductDao.retrieve(EplProductDo.VUID, vuId);

            if (productList.size() == 0) {
                throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, new Object[] { fieldVuid + vuId });
            }

            productDo = productList.get(0);
            eplProductDao.refresh(productDo);

            productDo.setEplVadfOwners(getDataFieldsDomainCapability().retrieveEplVaDfOwners(productDo.getEplId(),
                EntityType.PRODUCT.name()));

            eplVuidStatusHistoryList = eplVuidStatusHistoryDao.retrieve("VUID", vuId);

            vuidStatusHistroyList = vuidStatusHistoryConverter.convert(eplVuidStatusHistoryList);

        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(e, ItemNotFoundException.ITEM_NOT_FOUND, new Object[] { fieldVuid + vuId });
        }

        ProductVo retrievedVo = productConverter.convert(productDo);

        retrievedVo.setEffectiveDates(vuidStatusHistroyList);

        return retrievedVo;
    }

    /**
     * Search for All Products
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<ProductVo>
     */
    protected List<ProductVo> simpleAllSearch(SearchCriteriaVo searchCriteria) {
        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);

        Criteria criteria = getDataAccessObject().getCriteria("ITEM");
        criteria = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids, true);

        // get one page of data
        criteria.setFirstResult(searchCriteria.getStartRow());
        criteria.setMaxResults(searchCriteria.getPageSize());

        List<EplProductDo> data = criteria.list();

        // get row count for all results
        // simpleAllSearch
        //Criteria count = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids,  false);
        //int fullSize = getDataAccessObject().getCount(count);
        int fullSize = ids.size();

        List<ProductVo> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<ProductVo>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());

    }

    /**
     * Search for All Products
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<ProductVo>
     */
    protected List<ProductVo> simpleSynonymSearch(SearchCriteriaVo searchCriteria) {
        long start = System.currentTimeMillis();
        List<Long> ids = reportDomainCapability.getSimpleSynonymSearchIds(searchCriteria);

        if (ids == null || ids.size() < 1) {
            return new ArrayList();
        }

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("Select item from ")
            .append(EplProductDo.class).append("item where item.")
            .append(EplProductDo.EPL_ID).append(" IN ( "));

        for (int i = 0; i < ids.size(); i++) {
            query.append(ids.get(i));

            if ((i + 1) < ids.size()) {
                query.append(", ");
            } else {
                query.append(")");
            }
        }

        if (FieldKey.VA_PRODUCT_NAME.equals(searchCriteria.getSortedFieldKey())) {
            query.append(PPSConstants.ORDERBY).append(ITEM_PREFIX).append(EplProductDo.VA_PRODUCT_NAME);
            query.append(addDescending(searchCriteria.getSortOrder()));
        } else if (FieldKey.PRODUCT_STRENGTH.equals(searchCriteria.getSortedFieldKey())) {
            query.append(PPSConstants.ORDERBY).append(ITEM_PREFIX).append(EplProductDo.STRENGTH);
            query.append(addDescending(searchCriteria.getSortOrder()));
        } else if (FieldKey.CMOP_DISPENSE.equals(searchCriteria.getSortedFieldKey())) {
            query.append(PPSConstants.ORDERBY).append(ITEM_PREFIX).append(EplProductDo.CMOP_DISPENSE);
            query.append(addDescending(searchCriteria.getSortOrder()));
        } else if (FieldKey.REQUEST_ITEM_STATUS.equals(searchCriteria.getSortedFieldKey())) {
            query.append(PPSConstants.ORDERBY).append(ITEM_PREFIX).append(EplProductDo.REQUEST_STATUS);
            query.append(addDescending(searchCriteria.getSortOrder()));
        } else if (FieldKey.ITEM_STATUS.equals(searchCriteria.getSortedFieldKey())) {
            query.append(PPSConstants.ORDERBY).append(ITEM_PREFIX).append(EplProductDo.ITEM_STATUS);
            query.append(addDescending(searchCriteria.getSortOrder()));
        } else if (FieldKey.FORMULARY.equals(searchCriteria.getSortedFieldKey())) {
            query.append(PPSConstants.ORDERBY).append(ITEM_PREFIX).append(EplProductDo.NATIONAL_FORMULARY_INDICATORY);
            query.append(addDescending(searchCriteria.getSortOrder()));
        } else if (FieldKey.PRODUCT_UNIT.equals(searchCriteria.getSortedFieldKey())) {
            query.append(PPSConstants.ORDERBY).append(ITEM_PREFIX).append(EplProductDo.DRUG_UNIT);
            query.append(addDescending(searchCriteria.getSortOrder()));
        }

        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        filterMultiValueDisplayableFields(searchCriteria.getSearchTerms().get(0).getFieldKey(), searchCriteria
            .getSearchTerms().get(0).getValue(), searchCriteria.getSearchTerms().get(0).getSearchType(), products);

        LOG.debug("filtering took: " + (System.currentTimeMillis() - start));

        if (FieldKey.SYNONYM_NAME.equals(searchCriteria.getSortedFieldKey())) {
            if (SortOrder.ASCENDING.equals(searchCriteria.getSortOrder())) {
                Collections.sort(products, new ProductSynonymComparator());
            } else {
                Collections.sort(products, new ProductSynonymComparatorDesc());
            }
        } else if (FieldKey.OI_NAME.equals(searchCriteria.getSortedFieldKey())) {
            if (SortOrder.ASCENDING.equals(searchCriteria.getSortOrder())) {
                Collections.sort(products, new ProductOIComparator());
            } else {
                Collections.sort(products, new ProductOIComparatorDesc());
            }
        } else if (FieldKey.DOSAGE_FORM.equals(searchCriteria.getSortedFieldKey())) {
            if (SortOrder.ASCENDING.equals(searchCriteria.getSortOrder())) {
                Collections.sort(products, new ProductDoseFormComparator());
            } else {
                Collections.sort(products, new ProductDoseFormComparatorDesc());
            }
        } else if (FieldKey.PRIMARY_DRUG_CLASS2.equals(searchCriteria.getSortedFieldKey())) {
            if (SortOrder.ASCENDING.equals(searchCriteria.getSortOrder())) {
                Collections.sort(products, new ProductDrugClassComparator());
            } else {
                Collections.sort(products, new ProductDrugClassComparatorDesc());
            }
        } else if (FieldKey.GENERIC_NAME.equals(searchCriteria.getSortedFieldKey())) {
            if (SortOrder.ASCENDING.equals(searchCriteria.getSortOrder())) {
                Collections.sort(products, new ProductGenericComparator());
            } else {
                Collections.sort(products, new ProductGenericComparatorDesc());
            }
        }

        List<EplProductDo> pagedList = new ArrayList<EplProductDo>();
        int startNum = searchCriteria.getStartRow();

        for (int i = startNum; i < products.size(); i++) {
            pagedList.add(products.get(i));

            int stopNow = i - startNum + 1;

            if (stopNow >= searchCriteria.getPageSize()) {
                break;
            }
        }

        List<ProductVo> vos = productConverter.convertSearch(pagedList);
        PaginatedList<ProductVo> paginatedList = new PaginatedList<ProductVo>(vos, products.size(),
            searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());

        return paginatedList;

    }

    /**
     * sortOrder
     * @param sortOrder sortOrder
     * @return "DESC" or nothing.
     */
    private String addDescending(SortOrder sortOrder) {
        if (sortOrder.equals(SortOrder.DESCENDING)) {
            return " DESC ";
        } else {
            return " ";
        }
    }

    /**
     * Search for All Products
     * 
     * @param searchCriteria {@link SearchCriteriaVo}
     * @return List<ProductVo>
     */
    protected List<ProductVo> simpleAllSearchFullList(SearchCriteriaVo searchCriteria) {
        List<Long> ids = reportDomainCapability.getSimpleSearchIds(searchCriteria);

        Criteria criteria = getDataAccessObject().getCriteria(ITEM);
        criteria = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids, true);

        List<EplProductDo> data = criteria.list();

        // get row count for all results
        //Criteria count = createSimpleAllSearchTermCriteria(searchCriteria, criteria, ids,  false);
        //int fullSize = getDataAccessObject().getCount(count);
        int fullSize = ids.size();

        List<ProductVo> valueObjects = getConverter().convertSearch(data);

        return new PaginatedList<ProductVo>(valueObjects, fullSize, searchCriteria.getSortedFieldKey(),
            searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());
    }

    /**
     * Return search results
     * 
     * @param searchCriteria
     *            SearchCriteriaVo
     * @return search results
     */
    @Override
    public List<ProductVo> search(SearchCriteriaVo searchCriteria) {

        if (searchCriteria.getPageSize() == 0) {
            searchCriteria.setPageSize(PPSConstants.I100);
        }

        List<ProductVo> resultCollection = new ArrayList<ProductVo>();
        List<EplProductDo> products = new ArrayList<EplProductDo>();
        List<EplProductDo> allProducts = new ArrayList<EplProductDo>();

        if (searchCriteria.isAdvanced()) {

            SearchCriteriaVo currCriteria = searchCriteria;
            List<SearchTermVo> terms = new ArrayList<SearchTermVo>();

            for (SearchTermVo term : searchCriteria.expandSearchTerms()) {
                boolean isAndSearch = term.getAdvancedAndSearch();

                terms.add(term);

                if (isAndSearch) {
                    continue;
                } else {
                    currCriteria.setSearchTerms(terms);
                    products = searchAdvanced(currCriteria);
                    terms.clear();

                }

                allProducts.addAll(products);
            }

            List<EplProductDo> products2 = new ArrayList(searchCriteria.getPageSize());

            for (int i = searchCriteria.getStartRow(); i < searchCriteria.getStartRow() + searchCriteria.getPageSize()
                && i < allProducts.size(); i++) {
                products2.add(allProducts.get(i));
            }

            resultCollection = productConverter.convertSearch(products2);

            return new PaginatedList<ProductVo>(resultCollection, allProducts.size(), searchCriteria.getSortedFieldKey(),
                searchCriteria.getSortOrder(), searchCriteria.getStartRow(), searchCriteria.getPageSize());

        } else {

            SearchTermVo searchTermVo = searchCriteria.getSearchTerms().get(0);

            if (searchTermVo.getSearchField().getFieldKey().equals(FieldKey.SEARCH_ALL_FIELDS)) {
                resultCollection = simpleAllSearch(searchCriteria);
            } else if (searchTermVo.getSearchField().getFieldKey().equals(FieldKey.SYNONYM_NAME)) {
                resultCollection = simpleSynonymSearch(searchCriteria);
            } else {
                resultCollection = simpleSearch(searchCriteria);
            }
        }

        return resultCollection;
    }

    /**
     * Return search results
     * 
     * @param searchCriteria
     *            SearchCriteriaVo
     * @return search results
     */
    @Override
    public List<ProductVo> searchFullList(SearchCriteriaVo searchCriteria) {

        List<ProductVo> resultCollection = new ArrayList<ProductVo>();
        List<EplProductDo> products = new ArrayList<EplProductDo>();

        if (searchCriteria.isAdvanced()) {
            products = searchAdvanced(searchCriteria);

            for (EplProductDo data : products) {
                ProductVo vo = productConverter.convert(data);
                int tempCount = ndcDomainCapability.retrieveChildrenCount(vo.getId());
                vo.setNdcCount(tempCount);
                resultCollection.add(vo);
            }
        } else {
            SearchTermVo searchTermVo = searchCriteria.getSearchTerms().get(0);

            if (searchTermVo.getSearchField().getFieldKey().equals(FieldKey.SEARCH_ALL_FIELDS)) {
                resultCollection = simpleAllSearchFullList(searchCriteria);
            } else {
                resultCollection = simpleSearchFullList(searchCriteria);
            }
        }

        return resultCollection;
    }

    /**
     * Search for Products with the given search criteria.
     *  
     * @param searchCriteria criteria for matching Products
     * @return List of Products
     */
    public List<EplProductDo> searchAdvanced(SearchCriteriaVo searchCriteria) {
        boolean isAndSearch = searchCriteria.isAdvancedAndSearch();
        AbstractAdvancedSearchHelper searchHelper;
        searchHelper = new AdvancedSearchHelper(eplProductDao, searchCriteria);

        int nonListCount = 0;
        List<SearchTermVo> nonListSearchTerms = new ArrayList<SearchTermVo>();
        List<SearchTermVo> listSearchTerms = new ArrayList<SearchTermVo>();
        List<SearchTermVo> listMultiTextSearchTerms = new ArrayList<SearchTermVo>();
        List<SearchTermVo> atcCanisterSearchTerms = new ArrayList<SearchTermVo>();

        Criteria strength = null;
        Criteria nationalPossibleDosage = null;
        Criteria synonym = null;
        Criteria intendedUseCriteria = null;

        for (SearchTermVo term : searchCriteria.expandSearchTerms()) {
            
            // Skip no-op terms.
            if (term.getFieldKey().equals(FieldKey.SEARCH_ALL_FIELDS)
                || term.getFieldKey().equals(FieldKey.SEARCH_ALL_DESIGNATED_FIELDS)
                || term.getFieldKey().equals(FieldKey.SEARCH_NO_FIELDS)) {
                continue;
            }

            isAndSearch = term.getAdvancedAndSearch();

            if (isAndSearch) {
                searchHelper = new AndAdvancedSearchHelper(eplProductDao, searchCriteria);
            }

            MatchMode mode = MatchMode.EXACT;

            if (SearchType.CONTAINS.equals(term.getSearchType())) {
                mode = MatchMode.ANYWHERE;
            } else if (SearchType.BEGINS_WITH.equals(term.getSearchType())) {
                mode = MatchMode.START;
            }

            if (doExtraCriteria(term, mode, searchHelper)) {
                LOG.trace("doing extra criteria for advanced search.");
            } else if (doSearchHelperSetup(term, mode, searchHelper, isAndSearch)) {
                LOG.trace("doing search helper setup for advanced search.");
            } else if (doSynonyms(term, mode, searchHelper)) {
                LOG.trace("doing synonyms for advanced search.");
            } else if (term.getFieldKey().equals(FieldKey.DISPLAYABLE_INGREDIENT_UNIT)) {
                strength = searchHelper.getSubCriteria(EplProductDo.PROD_INGREDIENT_ASSOCS);
                Criteria drugUNit = searchHelper.getSubCriteria(strength, EplProdIngredientAssocDo.EPL_DRUG_UNIT);
                drugUNit.add(Restrictions.ilike(EplDrugUnitDo.NAME, term.formatValue(), mode));
            } else if (term.getFieldKey().equals(FieldKey.INACTIVATION_DATE)) {
                try {
                    searchHelper.add(Restrictions.eq(EplProductDo.INACTIVATION_DATE, 
                        DateFormatUtility.convertToDate(term.formatValue())), isAndSearch);
                } catch (ParseException pe) {
                    throw new DomainValidationException(pe, DomainValidationException.FIELD_FORMAT_ERROR,
                        term.getFieldKey().getLocalizedName(Locale.getDefault()), term.formatValue());
                }
            } else if (term.getFieldKey().equals(FieldKey.SYNONYM_INTENDED_USE)) {
                synonym = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);
                intendedUseCriteria = searchHelper.getSubCriteria(synonym, EplSynonymDo.INTENDED_USE);
                intendedUseCriteria.add(Restrictions.ilike(EplIntendedUseDo.INTENDED_USE, term.formatValue(), mode));
            } else if (term.getFieldKey().equals(FieldKey.DOSE)) {
                nationalPossibleDosage = searchHelper.getSubCriteria(EplProductDo.NATIONAL_POSSIBLE_DOSAGES);
                nationalPossibleDosage
                    .add(Restrictions.eq(EplNationalPossibleDosageDo.DOSE, new Double(term.formatValue())));
            } else if (term.getFieldKey().equals(FieldKey.POSSIBLE_DOSAGES_DISPENSE_UNITS_PER_DOSE)) {
                nationalPossibleDosage = searchHelper.getSubCriteria(EplProductDo.NATIONAL_POSSIBLE_DOSAGES);
                nationalPossibleDosage.add(Restrictions.eq(EplNationalPossibleDosageDo.DISPENSE_UNITS_PER_DOSE, new Double(
                    term.formatValue())));
            } else if (term.getFieldKey().equals(FieldKey.POSSIBLE_DOSAGE_PACKAGE)) {
                nationalPossibleDosage = searchHelper.getSubCriteria(EplProductDo.NATIONAL_POSSIBLE_DOSAGES);
                nationalPossibleDosage.add(Restrictions.ilike(EplNationalPossibleDosageDo.PACKAGE_NAME, term.formatValue(),
                    mode));
            } else if (term.getFieldKey().isSimpleDataField()) {
                if (nonListCount == 0) {

                    // Can only do one Hibernate query of this type, then after that must do manually (see below).
                    Criteria ownerCriteria = searchHelper.getSubCriteria(EplProductDo.EPL_VA_DF_OWNERS);
                    Criteria vaDfValueCriteria = ownerCriteria.createCriteria(EplVadfOwnerDo.EPL_VA_DF_NON_LIST_VALUES);
                    vaDfValueCriteria.add(Restrictions.ilike(EplVadfNonlistValueDo.VA_DF_VALUE, term.formatValue(), mode));
                    Criteria vaDfCriteria = vaDfValueCriteria.createCriteria(EplVadfNonlistValueDo.EPL_VA_DF);
                    LOG.debug(term.getFieldKey().getKey());
                    vaDfCriteria.add(Restrictions.eq(EplVaDfDo.NAME, term.getFieldKey().getKey()));
                } else {
                    nonListSearchTerms.add(term);
                }

                nonListCount++;
            } else if (term.getFieldKey().isListDataField()) {
                listSearchTerms.add(term);
            } else if (term.getFieldKey().isPrimaryKeyDataField() || term.getFieldKey().isMultitextDataField()) {
                listMultiTextSearchTerms.add(term);
            } else {
                throw new UnsupportedOperationException("No advanced search support for field '" + term.getFieldKey() + "'");
            }
        } // end for all search terms.

        // Perform the advanced search:
        List<AndExpressionRunnable<EplProductDo>> listManuals = new ArrayList<AndExpressionRunnable<EplProductDo>>();

        if (atcCanisterSearchTerms.size() > 0) {
            for (SearchTermVo term : atcCanisterSearchTerms) {
                listManuals.add(new AtcCanisterAndExpression(term));
            }
        }

        Set<EplProductDo> resultCollection =
            performAdvancedSearchWithCriteria(searchCriteria, eplProductDao, searchHelper, new ProductDoComparator(),
                nonListSearchTerms, listSearchTerms, listMultiTextSearchTerms, listManuals);

        return new ArrayList<EplProductDo>(resultCollection);
    }
    
    /**
     * doExtraCriteria
     *
     * @param term SearchTermVo
     * @param mode MatchMode
     * @param searchHelper AbstractAdvancedSearchHelper
     * @return boolean
     */
    private boolean doExtraCriteria(SearchTermVo term, MatchMode mode, AbstractAdvancedSearchHelper searchHelper) {
        
     // Ingredient Keys
        if (term.getFieldKey().equals(FieldKey.INGREDIENT)) {
            Criteria ingredAssocs = searchHelper.getSubCriteria(EplProductDo.PROD_INGREDIENT_ASSOCS);
            Criteria ingName = searchHelper.getSubCriteria(ingredAssocs, EplProdIngredientAssocDo.EPL_INGREDIENT);
            ingName.add(Restrictions.ilike(EplIngredientDo.NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.ACTIVE_INGREDIENT)) {
            Criteria ingredAssocs = searchHelper.getSubCriteria(EplProductDo.PROD_INGREDIENT_ASSOCS);
            Criteria ingName = searchHelper.getSubCriteria(ingredAssocs, EplProdIngredientAssocDo.EPL_INGREDIENT);
            ingName.add(Restrictions.ilike(EplIngredientDo.NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.INGREDIENT_NAME)) {
            Criteria ingredAssocs = searchHelper.getSubCriteria(EplProductDo.PROD_INGREDIENT_ASSOCS);
            Criteria ingName = searchHelper.getSubCriteria(ingredAssocs, EplProdIngredientAssocDo.EPL_INGREDIENT);
            ingName.add(Restrictions.ilike(EplIngredientDo.NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.DISPLAYABLE_INGREDIENT_NAME)) {
            Criteria ingredAssocs = searchHelper.getSubCriteria(EplProductDo.PROD_INGREDIENT_ASSOCS);
            Criteria ingName = searchHelper.getSubCriteria(ingredAssocs, EplProdIngredientAssocDo.EPL_INGREDIENT);
            ingName.add(Restrictions.ilike(EplIngredientDo.NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.CS_FED_SCHEDULE)) {
            Criteria fed = searchHelper.getSubCriteria(EplProductDo.CS_FED_SCHEDULES);
            fed.add(Restrictions.ilike(EplCsFedScheduleDo.SCHEDULE_NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.GENERIC_NAME)) {
            Criteria genName = searchHelper.getSubCriteria(EplProductDo.VA_GEN_NAME);
            genName.add(Restrictions.ilike(EplVaGenNameDo.NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.NATIONAL_DRUG_TEXTS)) {
            Criteria nationalDrugTexts = searchHelper.getSubCriteria(EplProductDo.PROD_DRUG_TEXT_NATIONAL_ASSOCS);
            Criteria drugText = nationalDrugTexts.createCriteria(EplProdDrugTextNAssocDo.EPL_DRUG_TEXT);
            drugText.add(Restrictions.ilike(EplDrugTextDo.DRUG_TEXT_NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.ORDER_UNIT)) {
            Criteria orderUnit = searchHelper.getSubCriteria(EplProductDo.EPL_ORDER_UNIT);
            orderUnit.add(Restrictions.ilike(EplOrderUnitDo.ABBREV, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.SPECIAL_HANDLING)) {
            Criteria specialHandlings = searchHelper.getSubCriteria(EplProductDo.EPL_PROD_SPEC_HANDLING_ASSOCS);
            Criteria specialHandling =
                searchHelper.getSubCriteria(specialHandlings, EplProdSpecHandlingAssocDo.EPL_SPECIAL_HANDLING);
            specialHandling.add(Restrictions.ilike(EplSpecialHandlingDo.CODE, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.SYNONYM_ORDER_UNIT)) {
            Criteria drugAssocs = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);
            Criteria drugs = searchHelper.getSubCriteria(drugAssocs, EplSynonymDo.EPL_ORDER_UNIT);
            drugs.add(Restrictions.ilike(EplOrderUnitDo.ABBREV, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.DISPENSE_UNIT)) {
            Criteria disp = searchHelper.getSubCriteria(EplProductDo.VA_DISPENSE_UNIT);
            disp.add(Restrictions.ilike(EplVaDispenseUnitDo.DISPENSE_UNIT_NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.VA_DRUG_CLASS)) { // Uses a substring of the term (see SearchTermVo)
            Criteria drugAssocs = searchHelper.getSubCriteria(EplProductDo.PROD_DRUG_CLASS_ASSOCS);
            drugAssocs.add(Restrictions.ilike(EplProdDrugClassAssocDo.PRIMARY_YN, "Y"));
            Criteria drugs = searchHelper.getSubCriteria(drugAssocs, EplProdDrugClassAssocDo.EPL_VA_DRUG_CLASS);
            drugs.add(Restrictions.ilike(EplVaDrugClassDo.CODE, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.DRUG_CLASS)) {
            Criteria drugAssocs = searchHelper.getSubCriteria(EplProductDo.PROD_DRUG_CLASS_ASSOCS);
            drugAssocs.add(Restrictions.ilike(EplProdDrugClassAssocDo.PRIMARY_YN, "Y"));
            Criteria drugs = searchHelper.getSubCriteria(drugAssocs, EplProdDrugClassAssocDo.EPL_VA_DRUG_CLASS);
            drugs.add(Restrictions.ilike(EplVaDrugClassDo.DESCRIPTION, term.formatValue(), mode));
        } else {
            return false;
        }
        
        return true;
    }

    
    /**
     * doSearchHelperSetup
     *
     * @param term SearchTermVo
     * @param mode MatchMode
     * @param searchHelper AbstractAdvancedSearchHelper
     * @param isAndSearch boolean
     * @return boolean
     */
    private boolean doSearchHelperSetup(SearchTermVo term, MatchMode mode, AbstractAdvancedSearchHelper searchHelper,
        boolean isAndSearch) {
        
        if (term.getFieldKey().equals(FieldKey.CREATE_POSSIBLE_DOSAGE)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.CREATE_POSSIBLE_DOSAGE, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.CMOP_DISPENSE)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.CMOP_DISPENSE, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.EXCLUDE_DRG_DRG_INTERACTION_CHECK)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.EXCLUDE_INTERACTION_CHECK, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.FDA_MED_GUIDE)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.FDA_MED_GUIDE, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.GCN_SEQUENCE_NUMBER)) {

            // The GUI's GCN seq number is a string, but it is stored in the database as a long.
            String fmtValue = term.formatValue();
            Long gcnToSearch = (fmtValue == null ? null : Long.parseLong(fmtValue));
            searchHelper.add(Restrictions.eq(EplProductDo.GCN_SEQ_NUMBER, gcnToSearch), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.DISPLAYABLE_INGREDIENT_STRENGTH)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.STRENGTH, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.LAB_TEST_MONITOR)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.LAB_TEST_MONITOR, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.MAX_DOSE_PER_DAY)) {
            searchHelper.add(Restrictions.eq(EplProductDo.MAX_DOSE_PER_DAY, new Short(term.formatValue())), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.NATIONAL_FORMULARY_INDICATOR)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.NATIONAL_FORMULARY_INDICATORY, term.formatValue(), mode), 
                isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.NATIONAL_FORMULARY_NAME)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.NATIONAL_FORMULARY_NAME, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.NDF_PRODUCT_IEN)) {
            searchHelper.add(Restrictions.eq(EplProductDo.NDF_PRODUCT_IEN, new Long(term.formatValue())), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.REQUEST_ITEM_STATUS)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.REQUEST_STATUS, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.OVERRIDE_DF_DOSE_CHK_EXCLUSN)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.OVERRIDE_DF_DOSE_CHK_EXCLUSN, term.formatValue(), mode), 
                isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.POSSIBLE_DOSAGES_AUTO_CREATE)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.POSSIBLE_DOSAGES_TO_CREATE, term.formatValue(), mode), 
                isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.PRODUCT_PACKAGE)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.PRODUCT_PACKAGE, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.PRODUCT_STRENGTH)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.STRENGTH, term.formatValue(), mode), isAndSearch);
        } else if (doSearchHelperSetup2(term, mode, searchHelper, isAndSearch)) {
            LOG.trace("doing second nested block for search helper.");
        } else {
            return false;
        }
        
        return true;
    }
    
    
    /**
     * doSearchHelperSetup2
     *
     * @param term SearchTermVo
     * @param mode MatchMode
     * @param searchHelper AbstractAdvancedSearchHelper
     * @param isAndSearch boolean
     * @return boolean
     */
    private boolean doSearchHelperSetup2(SearchTermVo term, MatchMode mode, AbstractAdvancedSearchHelper searchHelper,
        boolean isAndSearch) {
        
        if (term.getFieldKey().equals(FieldKey.REJECTION_REASON_TEXT)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.REJECT_REASON_TEXT, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.REQUEST_REJECTION_REASON)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.REQUEST_REJECT_REASON, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.SERVICE_CODE)) {
            searchHelper.add(Restrictions.eq(EplProductDo.SERVICE_CODE, new Long(term.formatValue())), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.SINGLE_MULTISOURCE_PRODUCT)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.SINGLE_MULTI_SOURCE_PRODUCT, term.formatValue(), mode), 
                isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.TALL_MAN_LETTERING)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.TALLMAN_LETTERING, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.VA_PRINT_NAME)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.VA_PRINT_NAME, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.CMOP_ID)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.CMOP_ID, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.VA_PRODUCT_NAME)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.VA_PRODUCT_NAME, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.VUID)) {
            searchHelper.add(Restrictions.ilike(EplProductDo.VUID, term.formatValue(), mode), isAndSearch);
        } else if (term.getFieldKey().equals(FieldKey.IEN)) {
            searchHelper.add(Restrictions.eq(EplProductDo.IEN, new Long(term.formatValue())), isAndSearch);
        } else {
            return false;
        }
        
        return true;
    }

    /**
     * doSynonyms
     *
     * @param term SearchTermVo
     * @param mode MatchMode
     * @param searchHelper AbstractAdvancedSearchHelper
     * @return boolean
     * 
     * 
     */
    private boolean doSynonyms(SearchTermVo term, MatchMode mode, AbstractAdvancedSearchHelper searchHelper) {
        Criteria synonym = null;
        
        if (term.getFieldKey().equals(FieldKey.SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT)) {
            synonym = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);

            try {
                synonym.add(Restrictions.eq(EplSynonymDo.DISPENSE_UNITS_PER_ORDER_UNIT, new Double(term.formatValue())));
            } catch (NumberFormatException e) {
                throw new DomainValidationException(e, DomainValidationException.NUMERIC_ERROR,
                    term.getFieldKey().getLocalizedName(Locale.getDefault()), term.formatValue());
            }
        } else if (term.getFieldKey().equals(FieldKey.NDC_CODE)) {
            synonym = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);
            synonym.add(Restrictions.ilike(EplSynonymDo.NDC_CODE, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT)
            || term.getFieldKey().equals(FieldKey.SYNONYM_PRICE_PER_DISPENSE_UNIT)) {
            synonym = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);

            try {
                synonym.add(Restrictions.eq(EplSynonymDo.PRICE_PER_DISPENSE_UNIT, new Double(term.formatValue())));
            } catch (NumberFormatException e) {
                throw new DomainValidationException(e, DomainValidationException.NUMERIC_ERROR,
                    term.getFieldKey().getLocalizedName(Locale.getDefault()), new Double(term.formatValue()));
            }
        } else if (term.getFieldKey().equals(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT)
            || term.getFieldKey().equals(FieldKey.SYNONYM_PRICE_PER_ORDER_UNIT)) {
            synonym = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);

            try {
                synonym.add(Restrictions.eq(EplSynonymDo.PRICE_PER_ORDER_UNIT, new Double(term.formatValue())));
            } catch (NumberFormatException e) {
                throw new DomainValidationException(e, DomainValidationException.NUMERIC_ERROR,
                    term.getFieldKey().getLocalizedName(Locale.getDefault()), new Double(term.formatValue()));
            }
        } else if (term.getFieldKey().equals(FieldKey.PRODUCT_UNIT)) {
            synonym = searchHelper.getSubCriteria(EplProductDo.DRUG_UNIT);
            synonym.add(Restrictions.ilike(EplDrugUnitDo.NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.DISPLAYABLE_SYNONYM_NAME)) {
            synonym = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);
            synonym.add(Restrictions.ilike(EplSynonymDo.SYNONYM_NAME, term.formatValue(), mode));
        } else if (term.getFieldKey().equals(FieldKey.SYNONYM_VENDOR)) {
            synonym = searchHelper.getSubCriteria(EplProductDo.SYNONYM_NAMES);
            synonym.add(Restrictions.ilike(EplSynonymDo.VENDOR, term.formatValue(), mode));
        } else {
            return false;
        }
        
        return true;
    }
    
    /**
     * Adds to the specified Criteria, additional 'AND'-ed criteria that is
     * specified on the top-level panel on the advanced search page. These
     * addtional criteria are always 'AND'-ed into the overall advanced search
     * query, regardless of if the mode is OR or AND.
     * 
     * @param searchCriteria
     *            {@link SearchCriteriaVo}
     * @param criteria
     *            The Criteria to add additional queries to
     */
    @Override
    protected void addTopPanelCriteria(SearchCriteriaVo searchCriteria, Criteria criteria) {

        if (LocalUseSearchType.LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            criteria.add(Restrictions.eq(EplProductDo.LOCAL_USE, "Y"));
        } else if (LocalUseSearchType.NOT_LOCAL_USE.equals(searchCriteria.getLocalUse())) {
            criteria.add(Restrictions.eq(EplProductDo.LOCAL_USE, "N"));
        }

        SearchQueryUtility.createItemStatusCriteria(searchCriteria.getItemStatus(), criteria, EplProductDo.ITEM_STATUS);

        SearchQueryUtility.createRequestItemStatusCriteria(searchCriteria.getRequestStatus(), criteria,
            EplProductDo.REQUEST_STATUS);
    }

    /**
     * Retrieve the ProductVos with the given Orderable Item ID.
     * 
     * @param orderableItemId String OrderableItem ID
     * @return List of ProductVo
     * 
     */
    @Override
    public List<ProductVo> retrieveChildren(String orderableItemId) {
        List<EplProductDo> products =
                eplProductDao.retrieve(EplProductDo.EPL_ORDERABLE_ITEM_EPL_ID, Long.valueOf(orderableItemId));
        List<ProductVo> returnedProducts = productConverter.convert(products);

        return returnedProducts;
    }

    /**
     * Retrieve the minimally populated ProductVos with the given Orderable Item
     * ID.
     * 
     * @param orderableItemId String OrderableItem ID
     * @return List of ProductVo
     */
    @Override
    public List<ProductVo> retrieveMinimalChildren(String orderableItemId) {
        List<EplProductDo> products =
                eplProductDao.retrieve(EplProductDo.EPL_ORDERABLE_ITEM_EPL_ID, Long.valueOf(orderableItemId));
        List<ProductVo> returnedProducts = productConverter.convertChild(products);

        return returnedProducts;
    }

    /**
     * Retrieve a partial list of Products for the given Orderable Item ID. The
     * list will begin at index startRow and will have pageSize number of rows
     * in it. The list will be sorted by sortedFieldKey in the given sortOrder.
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    @Override
    public PaginatedList<ProductVo> retrieveChildren(String parentItemId, FieldKey sortedFieldKey, SortOrder sortOrder,
        int startRow, int pageSize) {
        List<EplProductDo> data =
            eplProductDao.retrievePage(EplProductDo.EPL_ORDERABLE_ITEM_EPL_ID, Long.valueOf(parentItemId), startRow,
                pageSize, sortedFieldKey, sortOrder);
        List<ProductVo> products = productConverter.convertChild(data);

        return new PaginatedList<ProductVo>(products, retrieveChildrenCount(parentItemId), sortedFieldKey, sortOrder,
            startRow, pageSize);
    }

    /**
     * Retrieve a partial list of ACTIVE Products for the given Orderable Item
     * ID. The list will begin at index startRow and will have pageSize number
     * of rows in it. The list will be sorted by sortedFieldKey in the given
     * sortOrder.
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    @Override
    public PaginatedList<ProductVo> retrieveActiveChildren(String parentItemId, FieldKey sortedFieldKey,
        SortOrder sortOrder, int startRow, int pageSize) {

        List<EplProductDo> data =
            eplProductDao.retrievePage(EplProductDo.EPL_ORDERABLE_ITEM_EPL_ID, Long.valueOf(parentItemId), startRow,
                pageSize, sortedFieldKey, sortOrder);
        List<ProductVo> products = productConverter.convert(data);

        return new PaginatedList<ProductVo>(products, retrieveChildrenCount(parentItemId), sortedFieldKey, sortOrder,
            startRow, pageSize);
    }

    /**
     * retrieve the ndc count for the product by product id
     * 
     * @param orderableItemId String
     * @return int
     */
    @Override
    public int retrieveChildrenCount(String orderableItemId) {
        if (orderableItemId == null) {
            return 0;
        }

        StringBuffer query = new StringBuffer(COUNT_PRODUCT)
            .append(orderableItemId);
        Long rowsReturned = (Long) eplProductDao.executeHqlQuery(query.toString()).iterator().next();

        return rowsReturned.intValue();
    }

    /**
     * retrieve the product count for the orderable item by orderable id
     * 
     * @param orderableItemId String
     * @return int
     */
    @Override
    public int retrieveActiveChildrenCount(String orderableItemId) {
        if (orderableItemId == null) {
            return 0;
        }

        String query =
            COUNT_PRODUCT + orderableItemId
                + " AND product.itemStatus = 'ACTIVE' ";
        Long rowsReturned = (Long) eplProductDao.executeHqlQuery(query).iterator().next();

        return rowsReturned.intValue();

    }

    /**
     * Returns a count of products that have the specified ATC mnemonic value.
     * 
     * @param atcMnemonic The value to search for.
     * @param ignoredProductId Optional ID of product to ignore in count (say, to eliminate self-counts).
     * @return int The number of products whose ATC mnemonic selection matches the specified one.
     */
    @Override
    public boolean hasDuplicateAtcMnemonic(String atcMnemonic, String ignoredProductId) {

        if (atcMnemonic == null || atcMnemonic.trim().length() <= 0) {
            throw new UnsupportedOperationException("No support for ATC mnemonic searches on null/empty search strings");
        }

        Criteria productCriteria = eplProductDao.getCriteria();

        productCriteria.add(Restrictions.ilike(EplProductDo.ATC_MNEMONIC, atcMnemonic.trim(), MatchMode.EXACT));

        if (ignoredProductId != null && ignoredProductId.trim().length() > 0) {
            productCriteria.add(Restrictions.ne(EplProductDo.EPL_ID, Long.valueOf(ignoredProductId.trim())));
        }

        return eplProductDao.getCount(productCriteria) > 0;
    }

    /**
     * setEplProdDrugClassAssocDao
     * @param eplProdDrugClassAssocDao eplProdDrugClassAssocDao property
     */
    public void setEplProdDrugClassAssocDao(EplProdDrugClassAssocDao eplProdDrugClassAssocDao) {
        this.eplProdDrugClassAssocDao = eplProdDrugClassAssocDao;
    }

    /**
     * setEplProdIngredientAssocDao
     * @param eplProdIngredientAssocDao eplProdIngredientAssocDao property
     */
    public void setEplProdIngredientAssocDao(EplProdIngredientAssocDao eplProdIngredientAssocDao) {
        this.eplProdIngredientAssocDao = eplProdIngredientAssocDao;
    }

    /**
     * setEplProductDao
     * @param eplProductDao productDao property
     */
    public void setEplProductDao(EplProductDao eplProductDao) {
        this.eplProductDao = eplProductDao;
    }

    /**
     * setNdcDomainCapability
     * 
     * @param ndcDomainCapability ndcDomainCapability property
     */
    public void setNdcDomainCapability(NdcDomainCapability ndcDomainCapability) {
        this.ndcDomainCapability = ndcDomainCapability;
    }

    /**
     * setReportDomainCapability
     * 
     * @param reportDomainCapability reportDomainCapability property
     */
    public void setReportDomainCapability(ReportDomainCapability reportDomainCapability) {
        this.reportDomainCapability = reportDomainCapability;
    }

    /**
     * setEplSynonymDao
     * @param eplSynonymDao eplSynonymDao property
     */
    public void setEplSynonymDao(EplSynonymDao eplSynonymDao) {
        this.eplSynonymDao = eplSynonymDao;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    @Override
    public DataAccessObject getDataAccessObject() {
        return eplProductDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    @Override
    public ProductConverter getConverter() {
        return productConverter;
    }

    /**
     * setEplCmopIdHistoryDao
     * @param eplCmopIdHistoryDao eplCmopIdHistoryDao property
     */
    public void setEplCmopIdHistoryDao(EplCmopIdHistoryDao eplCmopIdHistoryDao) {
        this.eplCmopIdHistoryDao = eplCmopIdHistoryDao;
    }

    /**
     * setEplProdWarnLabelNAssocDao
     * @param eplProdWarnLabelNAssocDao eplProdWarnLabelNAssocDao property
     */
    public void setEplProdWarnLabelNAssocDao(EplProdWarnLabelNAssocDao eplProdWarnLabelNAssocDao) {
        this.eplProdWarnLabelNAssocDao = eplProdWarnLabelNAssocDao;
    }

    /**
     * setEplProdDrugTextNAssocDao
     * @param eplProdDrugTextNAssocDao eplProdDrugTextNAssocDao property
     */
    public void setEplProdDrugTextNAssocDao(EplProdDrugTextNAssocDao eplProdDrugTextNAssocDao) {
        this.eplProdDrugTextNAssocDao = eplProdDrugTextNAssocDao;
    }

    /**
     * setProductConverter
     * @param productConverter
     *            productConverter property
     */
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    /**
     * setSynonymConverter
     * @param synonymConverter
     *            synonymConverter property
     */
    public void setSynonymConverter(SynonymConverter synonymConverter) {
        this.synonymConverter = synonymConverter;
    }

    /**
     * setEplVuidStatusHistoryDao
     * @param eplVuidStatusHistoryDao eplVuidStatusHistoryDao property
     */
    public void setEplVuidStatusHistoryDao(EplVuidStatusHistoryDao eplVuidStatusHistoryDao) {
        this.eplVuidStatusHistoryDao = eplVuidStatusHistoryDao;
    }

    /**
     * setVuidStatusHistoryConverter
     * @param vuidStatusHistoryConverter vuidStatusHistoryConverter property
     */
    public void setVuidStatusHistoryConverter(VuidStatusHistoryConverter vuidStatusHistoryConverter) {
        this.vuidStatusHistoryConverter = vuidStatusHistoryConverter;
    }

    /**
     * setEplReducedCopayDao
     * @param eplReducedCopayDao eplReducedCopayDao property
     */
    public void setEplReducedCopayDao(EplReducedCopayDao eplReducedCopayDao) {
        this.eplReducedCopayDao = eplReducedCopayDao;
    }

    /**
     * setReducedCopayConverter
     * @param reducedCopayConverter reducedCopayConverter property
     */
    public void setReducedCopayConverter(ReducedCopayConverter reducedCopayConverter) {
        this.reducedCopayConverter = reducedCopayConverter;
    }

    /**
     * Comparator class intended for use in sorted sets of non-new ManagedItemVo
     * instances, such as via the TreeSet structure.
     */
    private static class ProductDoComparator implements Comparator<EplProductDo> {

        /**
         * Compares the two productDos using compareTo
         * 
         * @param o1 First Do
         * @param o2 Second Do
         * @return o1.compare(o2)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(EplProductDo o1, EplProductDo o2) {
            return o1.getEplId().compareTo(o2.getEplId());
        }

    }

    /**
     * Manual AND expression for ATC Canister searches.
     */
    private static class AtcCanisterAndExpression implements AndExpressionRunnable<EplProductDo> {

        private SearchTermVo atcCanisterSearchTerm = null;

        /**
         * Create an instance.
         * 
         * @param atcCanisterSearchTerm
         *            of type SearchTermVo
         */
        public AtcCanisterAndExpression(SearchTermVo atcCanisterSearchTerm) {
            this.atcCanisterSearchTerm = atcCanisterSearchTerm;
        }

        /**
         * Perform an AND expression based on the input data object.
         * 
         * @param data
         *            The data to perform AND expression on.
         * @return boolean True if this data passes the AND expression, false
         *         otherwise.
         */
        @Override
        public boolean performAndExpression(EplProductDo data) {

            // Manually query for ATC Canister number information.
            if (atcCanisterSearchTerm != null) {
                Long atcCanisterValue = new Long(atcCanisterSearchTerm.formatValue());

                boolean isOneAtcMatch =
                        (data.getOneAtcCanister() == null ? false : data.getOneAtcCanister().equals(atcCanisterValue));

                if (!isOneAtcMatch) {
                    boolean isMdfAtcMatch = false;

                    for (EplAtcCanisterDo next : data.getEplAtcCanisters()) {
                        if (next.getAtcCanister() != null && next.getAtcCanister().equals(atcCanisterValue)) {
                            isMdfAtcMatch = true;
                            break;
                        }
                    }

                    if (!isMdfAtcMatch) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
