/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.external.common.callback.ProductDomainCapabilityCallback;


/**
 * Perform CRUD operations on Products
 */
public interface ProductDomainCapability extends ManagedItemDomainCapability<ProductVo, EplProductDo>,
    ProductDomainCapabilityCallback {

    /**
     * Updates the product gcnseqNo
     * @param product The product
     * @param user The suer
     */
    void updateGcnSeqNo(ProductVo product, UserVo user);
        
    
    /**
     * Retrieve all formulary Products
     * 
     * @return list of String VA PRoduct Names
     */
    List<ProductVo> getAllFormularyProducts();

    /**
     * Retrieve all Products
     * 
     * @return list of VA PRoduct
     */
    List<ProductVo> getAllProducts();

    /**
     * Retrieve all Unit Dose and IV Products
     * 
     * @return list of Products
     */
    List<ProductVo> getAllUnitDoseAndIvProducts();

    /**
     * adds a single synonym
     * @param productId product
     * @return List of synonymVo
     */
    List<SynonymVo> retrieveSynonyms(String productId);
    
    /**
     * adds a single synonym
     * @param synonym synonym
     * @param product product
     * @param user user
     */
    void addSingleSynonym(SynonymVo synonym, ProductVo product,  UserVo user);
    
    /**
     * gets cmop id given print name
     * 
     * @param vaPrintName String
     * @param dispenseUnit Dispense Unit
     * @return generated cmop id
     */
    String getCmopIdForVaPrintName(String vaPrintName, String dispenseUnit);

    /**
     * Retrieve the ProductVo with the given vuId.
     * 
     * @param vuId vuId
     * @return ProductVo
     * @throws ItemNotFoundException exception
     */
    ProductVo retrieveByVuId(String vuId) throws ItemNotFoundException;

    /**
     * retrieve the active product count for the orderable item by orderable item id
     * 
     * @param orderableItemId String
     * @return int
     */
    int retrieveActiveChildrenCount(String orderableItemId);

    /**
     * this method will always create a cmop id given the va print name based on the generator algorithm and save into the
     * cmop_id_generator table
     * 
     * @param name String
     * @param dispenseUnit Dispense Unit
     * @param user {@link UserVo} performing the action
     * @param categories categories of the product
     * @return generated cmop id
     */
    String createCmopId(String name, String dispenseUnit, UserVo user, List<Category> categories);
    
    /**
     * this method will always create a cmop id given the va print name based on the generator algorithm and save into the
     * cmop_id_generator table
     * 
     * @param name String
     * @param user {@link UserVo} performing the action
     * @param printName pritnName
     * @param dispenseUnit dispense unit
     */
    void addCmopIdHistory(String name, UserVo user, String printName, String dispenseUnit);

    /**
     * this method is used after migraiton to set the initial state of the CMOP Id generator
     * @param user user
     */
    void setCmopIdGenerator(UserVo user);
    
    /**
     * Returns a count of products that have the specified ATC mnemonic value.
     * 
     * @param atcMnemonic The value to search for.
     * @param ignorProductId Optional ID of product to ignor in count (say, to eliminate self-counts).
     * @return int The number of products whose ATC mnemonic selection matches the specified one.
     */
    boolean hasDuplicateAtcMnemonic(String atcMnemonic, String ignorProductId);

    /**
     * Retrieves a list of all products with a specific gcnSeqNo
     * @param gcn GCNSeqNo
     * @return list of products
     */
    List<ProductVo> getAllProductswithGcn(Long gcn);
}
