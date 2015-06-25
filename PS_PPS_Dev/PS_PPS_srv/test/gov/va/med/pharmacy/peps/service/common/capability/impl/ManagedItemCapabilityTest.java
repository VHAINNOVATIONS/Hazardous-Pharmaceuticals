/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;

import junit.framework.TestCase;




/**
 * ManagedItemCapabilityTest's brief summary
 * 
 * Details of ManagedItemCapabilityTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ManagedItemCapabilityImpl.class)
public class ManagedItemCapabilityTest extends TestCase {
    
    private ManagedItemCapabilityImpl managedItemCapability;
    private ManagedItemCapabilityFactory managedItemCapabilityFactory;
    private ManagedItemDomainCapability productCapability;
    private ManagedItemDomainCapability ndcCapability;

    /**
     * Creates the mock managedItemCapability for the category cascade test
     *
     * @throws Exception Exception
     */
    @Override
    @Before
    public void setUp() throws Exception {
        Method updateMethod = ManagedItemCapabilityImpl.class.getMethod("update", ManagedItemVo.class, UserVo.class);
        Method toggleProductSupplySpecialHandlingMethod =
            ManagedItemCapabilityImpl.class.getDeclaredMethod("toggleProductSupplySpecialHandling", ProductVo.class);
        Method saveItemAuditHistoryCascadeMethod =
            ManagedItemCapabilityImpl.class.getDeclaredMethod("saveItemAuditHistoryCascade", String.class, ManagedItemVo.class,
                Difference.class, Difference.class, UserVo.class);
        managedItemCapability =
            PowerMock.createMock(ManagedItemCapabilityImpl.class, updateMethod, toggleProductSupplySpecialHandlingMethod,
                saveItemAuditHistoryCascadeMethod);
                
        productCapability = EasyMock.createMock(ProductDomainCapability.class);
        ndcCapability = EasyMock.createMock(NdcDomainCapability.class);

        managedItemCapabilityFactory = EasyMock.createMock(ManagedItemCapabilityFactory.class);
        managedItemCapability.setManagedItemCapabilityFactory(managedItemCapabilityFactory);

    }

    /**
     * 
     * Cleans up the mock managedItemCapability
     *
     */
    @After
    public void cleanUp() {
        managedItemCapability = null;
    }

    /**
     * testCommitAllModifications
     */
    @Test
    public void testCommitAllModifications() {
        boolean isTrue = true;
        assertTrue("Need to write a test here.", isTrue);
    }

    /**
     * 
     * Tests the cascadeCategoriesToChildren method
     * @throws Exception 
     *
     */
    @Test
    public void testCategoryCascade() throws Exception {
        setUp();

        UserVo user = new UserVo();

        OrderableItemVo orderableItem = (OrderableItemVo) OrderableItemVo.newInstance(EntityType.ORDERABLE_ITEM);
        List<Category> categories = new ArrayList<Category>();
        categories.add(Category.COMPOUND);
        categories.add(Category.INVESTIGATIONAL);

        orderableItem.setCategories(categories);

        assertFalse("OrderableItem categories empty", orderableItem.getCategories().isEmpty());

        EasyMock.expect(managedItemCapabilityFactory.getDomainCapability(EntityType.PRODUCT)).andReturn(productCapability)
            .anyTimes();
        EasyMock.expect(managedItemCapabilityFactory.getDomainCapability(EntityType.NDC)).andReturn(ndcCapability).anyTimes();


        for (int i = 0; i < PPSConstants.I10; i++) {
            ProductVo product = (ProductVo) ManagedItemVo.newInstance(EntityType.PRODUCT);
            product.setCategories(categories);
            EasyMock.expect(productCapability.retrieve(product.getId())).andReturn(product);
            EasyMock.expect(managedItemCapability.toggleProductSupplySpecialHandling(product)).andReturn(
                new Difference(FieldKey.CATEGORIES, Collections.EMPTY_LIST, categories));
            EasyMock.expect(managedItemCapability.update(product, user)).andReturn(null);
            Map<FieldKey, String> reasons = new HashMap<FieldKey, String>();
            reasons.put(FieldKey.CATEGORIES, "test");
            Map<FieldKey, Difference> diffs = new HashMap<FieldKey, Difference>();
            diffs.put(FieldKey.CATEGORIES, new Difference(FieldKey.CATEGORIES, Collections.EMPTY_LIST, categories));
            managedItemCapability.saveItemAuditHistoryCascade(reasons, product, diffs, user);
            EasyMock.expectLastCall().anyTimes();


            for (int j = PPSConstants.I10; j < PPSConstants.I10; j++) {
                NdcVo ndc = (NdcVo) ManagedItemVo.newInstance(EntityType.NDC);
                ndc.setCategories(categories);
                EasyMock.expect(ndcCapability.retrieve(ndc.getId())).andReturn(ndc);
                product.getChildren().add(ndc);
                EasyMock.expect(managedItemCapability.update(ndc, user)).andReturn(null);
                managedItemCapability.saveItemAuditHistoryCascade(reasons, ndc, diffs, user);
                EasyMock.expectLastCall().anyTimes();

            }

            orderableItem.getChildren().add(product);
        }

        OrderableItemVo originalOI = orderableItem.copy();
        
        categories = new ArrayList<Category>();
        categories.add(Category.MEDICATION);
        categories.add(Category.SUPPLY);

        orderableItem.setCategories(categories);

//        ModDifferenceVo mod = new ModDifferenceVo();
//        Collection<ModDifferenceVo> collection = new ArrayList<ModDifferenceVo>();
//        Difference diff = new Difference(FieldKey.CATEGORIES, categories, categories);
//        mod.setModificationReason("reason");
//        mod.setSiteName("TEST SITE");
//        mod.setReviewer(user);
//        collection.add(mod);
        
        EasyMock.replay(managedItemCapabilityFactory, productCapability, ndcCapability);
        PowerMock.replay(managedItemCapability, ManagedItemCapabilityImpl.class);
        
        managedItemCapability.cascadeChangesToChildren(orderableItem, user, originalOI,
            Collections.EMPTY_LIST);

        EasyMock.verify(managedItemCapabilityFactory, productCapability, ndcCapability);
        PowerMock.verify(managedItemCapability, ManagedItemCapabilityImpl.class);

        for (ManagedItemVo product : orderableItem.getChildren()) {
            assertTrue("The Product Categories are not equal to the OrderableItem Categories", ((ProductVo) product)
                .getCategories().equals(orderableItem.getCategories()));

            for (ManagedItemVo ndc : product.getChildren()) {
                assertTrue("The NDC Categories are not equal to the OrderableItem Categories", ((NdcVo) ndc).getCategories()
                    .equals(orderableItem.getCategories()));

            }
        }

        cleanUp();

    }

}
