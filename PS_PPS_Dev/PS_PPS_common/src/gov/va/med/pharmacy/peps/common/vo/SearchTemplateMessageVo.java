/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * A class representing the message sent from National to Local when a national seach template is updated
 */
public class SearchTemplateMessageVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private UserVo user;
    private SearchTemplateVo searchTemplate;
    private boolean warned;
    

    /**
     * Constructor used to build the item all at once
     * 
     * @param user userVo
     * @param searchTemplate searchTemplateVo
     * @param warned warned
     */
    public SearchTemplateMessageVo(UserVo user, SearchTemplateVo searchTemplate, boolean warned) {
        this.user = user;
        this.searchTemplate = searchTemplate;
        this.warned = warned;
    }

    /**
     * Description
     * 
     * @return user property
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * Description
     * 
     * @param user user property
     */
    public void setUser(UserVo user) {
        this.user = user;
    }

    /**
     * Description
     * 
     * @return searchTemplate property
     */
    public SearchTemplateVo getSearchTemplate() {
        return searchTemplate;
    }

    /**
     * Description
     * 
     * @param searchTemplate searchTemplate property
     */
    public void setSearchTemplate(SearchTemplateVo searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    /**
     * Description
     * 
     * @return warned property
     */
    public boolean isWarned() {
        return warned;
    }

    /**
     * Description
     * 
     * @param warned warned property
     */
    public void setWarned(boolean warned) {
        this.warned = warned;
    }

}
