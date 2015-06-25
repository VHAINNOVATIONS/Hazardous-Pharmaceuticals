/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.domain.common.capability.PrintTemplateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPrintFieldDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPrintTemplateDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintTemplateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PrintTemplateConverter;


/**
 * Perform create, retrieve, update, and delete operations on PrintTemplateVo. This class doesn't actually interact with the
 * database at this time. If, at a future point, there is a need to customize tables other than the search results table,
 * this domain capability may be used then.
 */
public class PrintTemplateDomainCapabilityImpl implements PrintTemplateDomainCapability {
    private EplPrintTemplateDao eplPrintTemplateDao;
    private EplPrintFieldDao eplPrintFieldDao;
    private SeqNumDomainCapability seqNumDomainCapability;
    private PrintTemplateConverter printTemplateConverter;

    /**
     * Delete the given Print Template
     * 
     * @param printTemplate PrintTemplateVo to delete
     * @return deleted PrintTemplateVo
     */
    public PrintTemplateVo delete(PrintTemplateVo printTemplate) {
        return printTemplate;
    }

    /**
     * Retrieve the PrintTemplate for the user at the given location.
     * 
     * @param user UserVo
     * @param templateLocation Location where table/print template is to be displayed
     * @return PrintTemplateVo
     */
    public PrintTemplateVo retrieve(UserVo user, TemplateLocation templateLocation) {
        List<Criterion> allCriterias = new ArrayList<Criterion>();
        Criterion crit = null;

        crit = Restrictions.eq("templateLocation", templateLocation.toString());
        allCriterias.add(crit);

        List<EplPrintTemplateDo> items = eplPrintTemplateDao.retrieve(allCriterias);

        // there is always only one
        if (items != null && items.size() > 0) {
            return printTemplateConverter.convert(items.get(0));
        }

        return null;
    }

    /**
     * Retrieves the default print template using the associated template location.
     * 
     * @param templateLocation associated template location
     * @return PrintTemplateVo
     */
    public PrintTemplateVo retrieveDefaultTemplate(TemplateLocation templateLocation) {
        return DefaultPrintTemplateFactory.getDefaultPrintTemplate(templateLocation);
    }

    /**
     * setSeqNumDomainCapability
     * @param seqNumDomainCapability seqNumDomainCapability property
     */
    public void setSeqNumDomainCapability(SeqNumDomainCapability seqNumDomainCapability) {
        this.seqNumDomainCapability = seqNumDomainCapability;
    }

    /**
     * create a print template
     * 
     * @param templateOwner property
     * @param printField property
     * @return printtTemplateVo PrintTemplateVo
     */
    public PrintTemplateVo create(UserVo templateOwner, PrintTemplateVo printField) {
        Long printFieldId = null;

        if (printField.getId() == null) {
            printFieldId = seqNumDomainCapability.generatePrintTemplateId(templateOwner);

            printField.setId(printFieldId.toString());
        }

        EplPrintTemplateDo printTemplateDo = printTemplateConverter.convert(printField);
        eplPrintTemplateDao.insert(printTemplateDo, templateOwner);

        for (EplPrintFieldDo tempPrintField : printTemplateDo.getEplPrintFields()) {
            eplPrintFieldDao.insert(tempPrintField, templateOwner);
        }

        return printField;
    }

    /**
     * delete print template
     * 
     * @param eplId id of search criteria to be deleted
     */
    public void delete(Long eplId) {
        EplPrintTemplateDo printTemplateDo = eplPrintTemplateDao.retrieve(eplId);

        for (EplPrintFieldDo tempPrintField : printTemplateDo.getEplPrintFields()) {
            eplPrintFieldDao.delete(tempPrintField);
        }

        eplPrintTemplateDao.delete(printTemplateDo);
    }

    /**
     * retrieve print template by id
     * 
     * @param eplId criteria ID
     * @return SearchCriteriaVo
     */
    public PrintTemplateVo retrieve(Long eplId) {
        return printTemplateConverter.convert(eplPrintTemplateDao.retrieve(eplId));
    }

    /**
     * update print template
     * 
     * @param templateOwner owner of the template
     * @param printTemplate instance to update
     * @return PrintTemplateVo
     */
    public PrintTemplateVo update(UserVo templateOwner, PrintTemplateVo printTemplate) {
        EplPrintTemplateDo printTemplateDo = printTemplateConverter.convert(printTemplate);

        // delete print fields
        eplPrintFieldDao.delete("eplPrintTemplate.eplId", printTemplateDo.getEplId());
        eplPrintFieldDao.insert(printTemplateDo.getEplPrintFields(), templateOwner);
        
        eplPrintTemplateDao.update(printTemplateDo, templateOwner);

        return printTemplate;
    }

    /**
     * setEplPrintFieldDao
     * @param eplPrintFieldDao property
     */
    public void setEplPrintFieldDao(EplPrintFieldDao eplPrintFieldDao) {
        this.eplPrintFieldDao = eplPrintFieldDao;
    }

    /**
     * setEplPrintTemplateDao
     * @param eplPrintTemplateDao property
     */
    public void setEplPrintTemplateDao(EplPrintTemplateDao eplPrintTemplateDao) {
        this.eplPrintTemplateDao = eplPrintTemplateDao;
    }

    /**
     * setPrintTemplateConverter
     * @param printTemplateConverter printTemplateConverter property
     */
    public void setPrintTemplateConverter(PrintTemplateConverter printTemplateConverter) {
        this.printTemplateConverter = printTemplateConverter;
    }
}
