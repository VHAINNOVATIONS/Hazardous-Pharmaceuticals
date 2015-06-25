/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.service.common.capability.PrintTemplateCapability;
import gov.va.med.pharmacy.peps.service.common.session.PrintTemplateService;


/**
 * Perform create, retrieve, update, and delete operations on PrintTemplateVo
 */
public class PrintTemplateServiceImpl implements PrintTemplateService {
    private PrintTemplateCapability printTemplateCapability;

    /**
     * Create a new Print Template for the given user.
     * 
     * @param user UserVo
     * @param printTemplate PrintTemplateVo to persist
     * @return persisted Print Template
     */
    public PrintTemplateVo create(UserVo user, PrintTemplateVo printTemplate) {
        return printTemplateCapability.create(user, printTemplate);
    }

    /**
     * Delete the given Print Template
     * 
     * @param printTemplate PrintTemplateVo to delete
     * @return deleted PrintTemplateVo
     */
    public PrintTemplateVo delete(PrintTemplateVo printTemplate) {
        return printTemplateCapability.delete(printTemplate);
    }

    /**
     * Retrieve the PrintTemplate for the user at the given location.
     * 
     * @param user UserVo
     * @param templateLocation Location where table/print template is to be displayed
     * @return PrintTemplateVo
     */
    public PrintTemplateVo retrieve(UserVo user, TemplateLocation templateLocation) {
        return printTemplateCapability.retrieve(user, templateLocation);
    }

    /**
     * setPrintTemplateCapability
     * @param printTemplateCapability printTemplateCapability property
     */
    public void setPrintTemplateCapability(PrintTemplateCapability printTemplateCapability) {
        this.printTemplateCapability = printTemplateCapability;
    }
}
