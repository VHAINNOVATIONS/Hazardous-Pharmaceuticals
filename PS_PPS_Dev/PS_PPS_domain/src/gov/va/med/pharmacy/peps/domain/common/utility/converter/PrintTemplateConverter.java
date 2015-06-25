/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintTemplateDo;


/**
 * Convert to/from {@link PrintTemplateVo} and {@link EplPrintTemplateDo}.
 */
public class PrintTemplateConverter extends Converter<PrintTemplateVo, EplPrintTemplateDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data PrintTemplateVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplPrintTemplateDo toDataObject(PrintTemplateVo data) {
        EplPrintTemplateDo savedPrintTemplate = new EplPrintTemplateDo();

        // set the epl Id to the correct value
        if (data.getId() != null) {
            savedPrintTemplate.setEplId(Long.parseLong(data.getId()));
        }

        // store the Template Location in the Do using the Vo Template enum.
        savedPrintTemplate.setTemplateLocation(data.getTemplateLocation().name());

        // set the user assigned template name into the VO
        savedPrintTemplate.setTemplateName(data.getTemplateName());

        Long currentSequenceNumber = 1L;

        for (int i = 0; i < data.getFields().size(); i++) {
            EplPrintFieldDo eplPrintFieldDo = new EplPrintFieldDo();
            EplPrintFieldDoKey key = new EplPrintFieldDoKey();

            // Populate the key
            key.setEplIdPrintTemplateFk(savedPrintTemplate.getEplId());
            key.setPrintFieldName(data.getFields().get(i).getFieldKey().getKey());

            // set the epl Id to the correct value
            eplPrintFieldDo.setKey(key);

            eplPrintFieldDo.setSequenceNumber(currentSequenceNumber++);
            eplPrintFieldDo.setTemplateFieldType(data.getFields().get(i).getFieldKey().getKey());
            savedPrintTemplate.getEplPrintFields().add(eplPrintFieldDo);
        }

        return savedPrintTemplate;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a PrintTemplateVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated PrintTemplateVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected PrintTemplateVo toValueObject(EplPrintTemplateDo data) {
        PrintTemplateVo printTemplateVo = new PrintTemplateVo();

        // set the id
        if (data.getEplId() != null) {
            printTemplateVo.setId(String.valueOf(data.getEplId()));
        }

        // now set the template location
        printTemplateVo.setTemplateLocation(TemplateLocation.valueOf(data.getTemplateLocation()));

        // set the template name
        printTemplateVo.setTemplateName(data.getTemplateName());

        String printFieldName = null;
        List<FieldKey> printFieldKeysList = new ArrayList<FieldKey>();

        // load the list of field keys
        for (EplPrintFieldDo printFieldDo : data.getEplPrintFields()) {
            printFieldName = printFieldDo.getKey().getPrintFieldName();
            FieldKey fieldKey = FieldKey.getKey(printFieldName);
            printFieldKeysList.add(fieldKey);
        }

        // populate the print field collection
        PrintTemplateVo.populatePrintFieldCollection(printTemplateVo, printFieldKeysList);

        return printTemplateVo;
    }
}
