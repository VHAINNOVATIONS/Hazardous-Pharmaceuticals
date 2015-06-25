/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * Generate a list of Products
 */
public class OrderableItemGenerator extends VoGenerator<OrderableItemVo> {

    /**
     * Generate a list of Products
     * 
     * @return List of Products
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<OrderableItemVo> generate() {

        List<OrderableItemVo> orderableItems = new ArrayList<OrderableItemVo>();

        orderableItems.add(buildOrderableItemVo());

        return orderableItems;
    }

    /**
     * Create a OrderableItemVo with dummy values.
     * 
     * @return OrderableItemVo
     */
    public OrderableItemVo buildOrderableItemVo() {

        OrderableItemVo vo = new OrderableItemVo();
        vo.setLocalUse(true);

        vo.setLocal();
        vo.setOiName("OI" + UUID.randomUUID().getLeastSignificantBits());
        vo.setVistaOiName("OIVISTANAME" + UUID.randomUUID().getLeastSignificantBits());

        vo.setItemStatus(ItemStatus.ACTIVE);
        vo.setRequestItemStatus(RequestItemStatus.APPROVED);
        vo.setDosageForm(new DosageFormGenerator().getRandom());

        OrderableItemVo national = new OrderableItemVo();
        national.setId("9992");
        vo.setOrderableItemParent(national);

        vo.setItemStatus(ItemStatus.values()[RandomGenerator.nextInt(ItemStatus.values().length)]);
        vo.setRequestItemStatus(RequestItemStatus.values()[RandomGenerator.nextInt(RequestItemStatus.values().length)]);
        vo.setNationalFormularyIndicator(false);

        vo.setNonVaMed(false);

        DataField<Double> ndcPrice = DataField.newInstance(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        ndcPrice.setDataFieldId(PPSConstants.L65);
        ndcPrice.selectValue(PPSConstants.F5POINT5);
        vo.getVaDataFields().setDataField(ndcPrice);

        return vo;

    }

    /**
     * Generate a random OrderableItemVo.
     * 
     * @return OrderableItemVo
     */
    @Override
    public OrderableItemVo getRandom() {

        OrderableItemVo orderableItem = buildOrderableItemVo();

        return orderableItem;
    }

    /**
     * Create an OrderableItemVo
     * 
     * @param orderableItemVo The input vo with some default fields
     * @param local Is this a local orderable item
     * @param noi Does it have an NOI parent
     * @return OrderableItemVo
     */
    public OrderableItemVo generateOrderableItemVo(OrderableItemVo orderableItemVo, boolean local, boolean noi) {

        // get the va data fields
        DataFields<DataField> vadfs = orderableItemVo.getVaDataFields();

        // Set mandatory fields
        if (local) {
            orderableItemVo.setLocal();
        } else {
            orderableItemVo.setNational();
        }

        orderableItemVo.setItemStatus(ItemStatus.ACTIVE);

        // dosage form
        DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setId("9995");
        dosageForm.setDosageFormName("CREAM");
        orderableItemVo.setDosageForm(dosageForm);

        if (noi) {
            orderableItemVo.setOrderableItemParent(null);
        } else {
            OrderableItemVo parent = new OrderableItemVo();
            parent.setId("9993");
            orderableItemVo.setOrderableItemParent(parent);
        }

        // Vista OI Name
        orderableItemVo.setVistaOiName("my VistA OI Name" + new Long(new Date().getTime()).toString());

        // OI Name is a concatenation of VistA OI Name and Dosage Form, so let the OI VO set the value itself
        orderableItemVo.updateOiName();

        // ***************Optional Non VA Data Fields******************
        orderableItemVo.setNationalFormularyIndicator(false);

        // ******************Optional VA Data Fields******************
        vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT).selectStringValue("50L");

        DataField<Boolean> lifetime = DataField.newInstance(FieldKey.LIFETIME_CUMULATIVE_DOSAGE);
        lifetime.setDataFieldId(PPSConstants.L45);
        lifetime.selectValue(true);
        vadfs.setDataField(lifetime);

        orderableItemVo.setNonVaMed(false);

        // OI-Drug Text Entry
        DrugTextVo drugText = new DrugTextVo();
        drugText.setId("9991");

        List<DrugTextVo> colDrugText = new ArrayList<DrugTextVo>();
        colDrugText.add(drugText);
        orderableItemVo.setNationalDrugTexts(colDrugText);

        vadfs.getDataField(FieldKey.OI_IV_FLAG).selectValue(false);

        vadfs.getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM).addStringSelection("synonym");
        vadfs.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS).selectStringValue("other language instructions");
        vadfs.getDataField(FieldKey.PATIENT_INSTRUCTIONS).selectStringValue("patient instructions");
        vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(null);

        return orderableItemVo;

    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {

    }
}
