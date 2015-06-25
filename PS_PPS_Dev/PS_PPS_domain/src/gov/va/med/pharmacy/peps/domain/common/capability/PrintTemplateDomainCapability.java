/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;


/**
 * Perform create, retrieve, update, and delete operations on PrintTemplateVo
 */
public interface PrintTemplateDomainCapability {

    /**
     * Create a new Print Template for the given user.
     * 
     * @param user UserVo
     * @param printTemplate PrintTemplateVo to persist
     * @return persisted Print Template
     */
    PrintTemplateVo create(UserVo user, PrintTemplateVo printTemplate);

    /**
     * Delete the given Print Template
     * 
     * @param printTemplate PrintTemplateVo to delete
     * @return deleted PrintTemplateVo
     */
    PrintTemplateVo delete(PrintTemplateVo printTemplate);

    /**
     * Retrieve the PrintTemplate for the user at the given location.
     * 
     * @param user UserVo
     * @param templateLocation Location where table/print template is to be displayed
     * @return PrintTemplateVo
     */
    PrintTemplateVo retrieve(UserVo user, TemplateLocation templateLocation);

    /**
     * update
     * 
     * @param templateOwner owner of the template
     * @param printTemplate instance to update
     * @return PrintTemplateVo
     */
    PrintTemplateVo update(UserVo templateOwner, PrintTemplateVo printTemplate);

    /**
     * retrieve
     * 
     * @param eplId criteria ID
     * @return SearchCriteriaVo
     */
    PrintTemplateVo retrieve(Long eplId);

    /**
     * delete
     * 
     * @param eplId id of search criteria to be deleted
     */
    void delete(Long eplId);
}
