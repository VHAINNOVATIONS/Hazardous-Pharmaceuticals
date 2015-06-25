/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;


/**
 * Generate a list of search templates
 */
public class SearchTemplateGenerator extends VoGenerator<SearchTemplateVo> {

    /**
     * Generate a list of search templates
     * 
     * @return List of search templates
     */
    protected List<SearchTemplateVo> generate() {
        SearchTermGenerator termGenerator = new SearchTermGenerator();
        List<SearchTemplateVo> templates = new ArrayList<SearchTemplateVo>();

        SearchTemplateVo firstTemplate = new SearchTemplateVo();
        firstTemplate.getSearchCriteria().setSearchTerms(termGenerator.generate());
        firstTemplate.setDefault(false);
        firstTemplate.setTemplateName("New Template 1");
        firstTemplate.setTemplateType(TemplateType.PERSONAL);
        firstTemplate.setNotes("Personal new template");

        templates.add(firstTemplate);

        return templates;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
