/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudopeps;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.utility.ClassUtility;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.utility.test.DataAccessManager;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.NdcItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.OrderableItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.ProductItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.NdcItemConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.OrderableItemConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.ProductItemConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemStatus;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.ndcitem.NdcItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.orderableitem.OrderableItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.productitem.ProductItem;


/**
 * Dump interface XML.
 */
public class DumpXml { 

    private static final Map<FieldKey, Difference> EMPTY_DIFF = new HashMap<FieldKey, Difference>();


    
    

    /**
     * DumpXml
     * @throws PharmacyException  PharmacyException
     * @throws IOException 
     * @see junit.framework.TestCase#setUp()
     */
    private DumpXml() throws PharmacyException, IOException {
        ApplicationContext context = new DataAccessManager().getLocalOneApplicationContext();

        ProductDomainCapability productCapability = (ProductDomainCapability) context.getBean(ClassUtility
            .getSpringBeanId(ProductDomainCapability.class));

        ProductVo product = productCapability.retrieve("9991");
        NdcVo ndc = product.getNdcs().get(0);
        ndc.setProduct(product);

        ManufacturerDomainCapability manufacturerCapability = (ManufacturerDomainCapability) context.getBean(ClassUtility
            .getSpringBeanId(ManufacturerDomainCapability.class));

        List<ManufacturerVo> manufacturers = manufacturerCapability.retrieve();
        ndc.setManufacturer(manufacturers.get(0));

//        IngredientDomainCapability ingredientCapability = (IngredientDomainCapability) context.getBean(ClassUtility
//            .getSpringBeanId(IngredientDomainCapability.class));
//
        DrugTextDomainCapability drugTextCapability = (DrugTextDomainCapability) context.getBean(ClassUtility
            .getSpringBeanId(DrugTextDomainCapability.class));

    //    List<IngredientVo> ingredients = ingredientCapability.retrieve();

        OrderableItemVo orderable = product.getOrderableItem();

        List<DrugTextVo> drugTexts = drugTextCapability.retrieve();
        orderable.setLocalDrugTexts(Arrays.asList(new DrugTextVo[] {drugTexts.get(0)}));

        File addPath = new File("tmp/PseudoPeps/etc/add");
        addPath.mkdirs();

        File modifyPath = new File("tmp/PseudoPeps/etc/modify");
        modifyPath.mkdirs();

        // Add

//        NdfDomain ndfDomain = NdfDomainConverter.toNdfDomain(// checkstyle!
//            new DomainItem[] {new DomainItem(manufacturers.get(1), ItemAction.ADD, EMPTY_DIFF),
//                              new DomainItem(ingredients.get(1), ItemAction.ADD, EMPTY_DIFF)});
//        ndfDomain.setPepsIdNumber(new BigInteger("1"));
//        writeToFile(new File(addPath, "ndfDomain.xml"), NdfDomainDocument.instance().marshal(ndfDomain));

//        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(// checkstyle!
//            new DomainItem[] {new DomainItem(drugTexts.get(1), ItemAction.ADD, EMPTY_DIFF)});
//        pdmDomain.setPepsIdNumber(new BigInteger("2"));
//        writeToFile(new File(addPath, "pdmDomain.xml"), PdmDomainDocument.instance().marshal(pdmDomain));

        OrderableItem orderableItem = OrderableItemConverter.toOrderableItem(orderable, EMPTY_DIFF, ItemAction.ADD,
            new DomainItem[] {new DomainItem(product.getGenericName(), ItemAction.ADD, EMPTY_DIFF),
                              new DomainItem(product.getDosageForm(), ItemAction.ADD, EMPTY_DIFF),
                              new DomainItem(orderable.getLocalDrugTexts().iterator().next(), ItemAction.ADD, EMPTY_DIFF)},
            true);
        orderableItem.setPepsIdNumber(new BigInteger("3"));
        orderableItem.setStatus(ItemStatus.APPROVED);
        writeToFile(new File(addPath, "orderableItem.xml"), OrderableItemDocument.instance().marshal(orderableItem));

        ProductItem productItem = ProductItemConverter.toProductItem(product, EMPTY_DIFF, ItemAction.ADD,
            new DomainItem[] {new DomainItem(product.getStandardMedicationRoute(), ItemAction.ADD, EMPTY_DIFF),
                              new DomainItem(product.getDispenseUnit(), ItemAction.ADD, EMPTY_DIFF),
                              new DomainItem(product.getPrimaryDrugClass(), ItemAction.ADD, EMPTY_DIFF)}, true);
        productItem.setPepsIdNumber(new BigInteger("4"));
        productItem.setStatus(ItemStatus.APPROVED);
        writeToFile(new File(addPath, "productItem.xml"), ProductItemDocument.instance().marshal(productItem));

        NdcItem ndcItem = NdcItemConverter.toNdcItem(ndc, EMPTY_DIFF, ItemAction.ADD, new DomainItem[] {new DomainItem(ndc
            .getManufacturer(), ItemAction.ADD, EMPTY_DIFF)});
        ndcItem.setPepsIdNumber(new BigInteger("5"));
        ndcItem.setStatus(ItemStatus.APPROVED);
        writeToFile(new File(addPath, "ndcItem.xml"), NdcItemDocument.instance().marshal(ndcItem));

    }

    /**
     * Write to file.
     * 
     * @param file filename
     * @param xml xml content
     * @throws IOException IOException
     */
    private void writeToFile(File file, String xml) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        try {
            writer.write(xml);
        } finally {
            writer.close();
        }
    }

    /**
     * Launch utility.
     * 
     * @param args arguments
     * @throws IOException IOException
     * @throws PharmacyException  PharmacyException
     */
    public static void main(String[] args) throws PharmacyException, IOException  {
        new DumpXml();
    }
    
}
