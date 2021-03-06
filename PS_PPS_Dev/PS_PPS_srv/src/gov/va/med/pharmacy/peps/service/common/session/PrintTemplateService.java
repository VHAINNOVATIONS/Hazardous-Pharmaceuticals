/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;


/**
 * Perform create, retrieve, update, and delete operations on PrintTemplateVo
 */
public interface PrintTemplateService {

    /**
     * Create a new Print Template for the given user for PrintTemplateService.
     * 
     * @param user UserVo
     * @param printTemplate PrintTemplateVo to persist
     * @return persisted Print Template
     */
    PrintTemplateVo create(UserVo user, PrintTemplateVo printTemplate);

    /**
     * Delete the given Print Template  for PrintTemplateService.
     * 
     * @param printTemplate PrintTemplateVo to delete
     * @return deleted PrintTemplateVo
     */
    PrintTemplateVo delete(PrintTemplateVo printTemplate);

    /**
     * Retrieve the PrintTemplate for the user at the given location  for PrintTemplateService.
     * 
     * @param user UserVo
     * @param templateLocation Location where table/print template is to be displayed
     * @return PrintTemplateVo
     */
    PrintTemplateVo retrieve(UserVo user, TemplateLocation templateLocation);


}
