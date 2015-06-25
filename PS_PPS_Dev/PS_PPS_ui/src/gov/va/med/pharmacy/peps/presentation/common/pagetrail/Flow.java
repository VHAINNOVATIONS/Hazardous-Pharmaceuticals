/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/**
 * Flow's brief summary
 * 
 * Details of Flow's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class Flow {

    private Stack<Page> pages;

    private Map<String, Object> flowMap;

    /**
     * Constructor
     *
     */
    public Flow() {

        super();

        pages = new Stack<Page>();
    }

    /**
     * getPages
     * @return the pages
     */
    public Stack<Page> getPages() {
        return pages;
    }

    /**
     * setPages
     * @param pages the pages to set
     */
    public void setPages(Stack<Page> pages) {
        this.pages = pages;
    }

    /**
     * getFlowMap
     * @return the flowMap
     */
    public Map<String, Object> getFlowMap() {
        if (flowMap == null) {
            flowMap = new HashMap();
        }

        return flowMap;
    }

    /**
     * setFlowMap
     * @param flowMap the flowMap to set
     */
    public void setFlowMap(Map<String, Object> flowMap) {
        this.flowMap = flowMap;
    }

}
