/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility;


/**
 * 
 * Represents the trail of pages the user has visited.  This is a hierarchy PageTrail.  
 * It is meant to show where the user is in the application hierarchy.
 *
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PageTrail {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FlowScope flowScope;

    @Autowired
    private FlowInputScope flowInputScope;

    @Autowired
    private MessageSource messageSource;

    private final Stack<Flow> flows;

    /**
     * The constructor.
     */
    public PageTrail() {
        super();

        flows = new Stack<Flow>();
    }

    /**
     * Add a new page on the PageTrail.
     *
     * @param id the String id for the page being pushed on the PageTrail
     * @param label the label to display on the PageTrail.  Can be a message property or the actual String.
     */
    public void addPage(String id, String label) {
        addPage(id, label, getFlows().isEmpty());
    }

    /**
     * Add a new page on the PageTrail.
     *
     * @param id the String id for the page being pushed on the PageTrail
     * @param label the label to display on the PageTrail.  Can be a message property or the actual String.
     * @param newFlow if true, indicates this is a new flow or set of pages
     */
    public void addPage(String id, String label, Boolean newFlow) {

        Page page = createPage(id, label);

        boolean pageAlreadyOnTrail = clearPagesAfterAndIncludingPage(page);

        if (getFlows().empty() || newFlow && !pageAlreadyOnTrail) {
            addNewFlow();
        }

        getFlows().peek().getPages().push(page);

    }

    /**
     * Adds a new flow to the flow Stack.  Brings values in from flowInputScope if they exist.
     */
    void addNewFlow() {

        // store the current flowscope into the current flow
        if (!getFlows().empty()) {
            getFlows().peek().getFlowMap().putAll(flowScope);
        }

        flowScope.clear();
        flowScope.putAll(flowInputScope);
        flowInputScope.setBackingMap(null);
        getFlows().add(new Flow());

    }

    /**
     * Takes an id and label, and returns a new Page
     *
     * @param id a unique id for this Page
     * @param label the label to display.  Can be a message key or a the actual label.
     * @return the new Page
     */
    Page createPage(String id, String label) {

        // build the label
        String localizedLabel = messageSource.getMessage(label, null, label, request.getLocale());

        // build the url
        String url = UrlUtility.createMvcExecutionUrl(request);

        // remove the context
        url = url.substring(url.indexOf("/", 1));

        Page page = new Page(id, localizedLabel, url);

        return page;
    }

    /**
     * Clears the pages out of the flows up through and including the passed in page
     *
     * @param page the page to clear to
     * @return if the page was found
     */
    boolean clearPagesAfterAndIncludingPage(Page page) {
        boolean pageAlreadyOnTrail = false;
        int deleteFlowIndex;
        int pageIndex;

        // loop through flows and pages to search for page
        // if found, clear flows and pages after and including that page

        Stack<Flow> localFlows = getFlows();

        for (deleteFlowIndex = localFlows.size() - 1; deleteFlowIndex > -1; deleteFlowIndex--) {

            Flow flow = localFlows.elementAt(deleteFlowIndex);

            pageIndex = flow.getPages().search(page) - 1;

            if (pageIndex >= 0) {
                break;
            }
        }

        if (deleteFlowIndex >= 0) {
            for (int flowIndex = localFlows.size() - 1; flowIndex > deleteFlowIndex; flowIndex--) {
                localFlows.pop();
            }

            Flow flow = localFlows.peek();

            pageAlreadyOnTrail = clearPageFromPages(page, flow.getPages());
            flowScope.putAll(flow.getFlowMap());
        }

        return pageAlreadyOnTrail;
    }

    /**
     * Clears all the pages in the stack of pages up to and including the page passed in.
     *
     * @param page the page to clear through
     * @param pages the stack of pages to clear
     * @return if the page was found
     */
    boolean clearPageFromPages(Page page, Stack<Page> pages) {
        int index = pages.size() - pages.search(page);

        if (index >= 0 && index < pages.size()) {

            while (index < pages.size()) {
                pages.remove(index);
            }

            return true;
        } else {

            return false;
        }
    }

    /**
     * Pops off the current page in the PageTrail and returns the previous URL, or null if it doesn't exist.
     *
     * @return the String URL of the last page on the PageTrail
     */
    public String goToPreviousUrl() {
        return goToPreviousUrl(null);
    }

    /**
     * Pops off the current page in the PageTrail and returns the previous URL, or the default URL if it doesn't exist.
     *
     * @param defaultUrl the String URL to default to if a page does not exist on the PageTrail.
     * @return the String URL of the last page on the PageTrail or the default URL passed in if a page does 
     *         not exist on the PageTrail.
     */
    public String goToPreviousUrl(String defaultUrl) {
        String url = defaultUrl;

        Stack<Flow> localFlows = getFlows();

        if (!localFlows.isEmpty()) {
            Stack<Page> pages = localFlows.peek().getPages();

            if (pages.size() > 1) {
                pages.pop();
                url = getCurrentUrl();
            } else {
                localFlows.pop();
                flowScope.clear();

                if (!localFlows.isEmpty()) {
                    url = getCurrentUrl();
                    flowScope.putAll(localFlows.peek().getFlowMap());
                }
            }
        }

        return url;
    }

    /**
     * Returns the previous URL, or null if it doesn't exist.
     *
     * @return the String URL of the last page on the PageTrail or null if a page does not exist on the PageTrail.
     */
    public String getPreviousUrl() {
        String url = null;

        Stack<Flow> localFlows = getFlows();

        if (!localFlows.isEmpty()) {
            Stack<Page> pages = localFlows.peek().getPages();

            if (pages.size() > 1) {
                url = pages.get(pages.size() - 2).getUrl();
            } else {
                if (localFlows.size() > 1) {
                    url = localFlows.get(localFlows.size() - 2).getPages().peek().getUrl();
                }
            }
        }

        return url;
    }

    /**
     * Pops off pages from the PageTrail until a page that indicates it is a new flow is found.  The last page 
     * before the new flow is popped off and the URL is returned.  If a new flow page with a page before 
     * that is not found, null is returned.
     *
     * @return the String URL of the last page before the current flow on the PageTrail or null if 
     *         a page does not exist on the PageTrail.
     */
    public String goToPreviousFlowUrl() {
        return goToPreviousFlowUrl(null);
    }

    /**
     * Pops off pages from the PageTrail until a page that indicates it is a new flow is found.  The last page 
     * before the new flow is popped off and the URL is returned.  If a new flow page with a page before 
     * that is not found, the default page is returned.
     *
     * @param defaultUrl the String URL to default to if a page does not exist on the PageTrail before the current flow.
     * @return the String URL of the last page before the current flow on the PageTrail or the default URL passed in if 
     *         a page does not exist on the PageTrail.
     */
    public String goToPreviousFlowUrl(String defaultUrl) {
        String url = defaultUrl;

        Stack<Flow> localFlows = getFlows();

        if (!localFlows.isEmpty()) {
            localFlows.pop();

            if (!localFlows.isEmpty()) {
                Stack<Page> pages = localFlows.peek().getPages();

                if (pages.size() > 0) {
                    url = pages.peek().getUrl();
                }

                flowScope.clear();
                flowScope.putAll(localFlows.peek().getFlowMap());
            }
        }

        return url;
    }

    /**
     * Returns the Stack of Pages in the PageTrail.
     *
     * @return the Stack of Pages in the PageTrail
     */
    public Stack<Page> getAllPages() {
        Stack<Page> pages = new Stack<Page>();

        for (Flow flow : getFlows()) {
            pages.addAll(flow.getPages());
        }

        return pages;
    }

    /**
     * 
     * Clears all of the pages out of the crumb trail
     *
     */
    public void clearTrail() {
        getFlows().clear();
        flowScope.clear();
    }

    /**
     * 
     * Returns the current URL without popping the from the stack
     *
     * @return the current url
     */
    public String getCurrentUrl() {
        return getFlows().peek().getPages().peek().getUrl();
    }

    /**
     * 
     * Gets the current page title
     *
     * @return the page title
     */
    public String getCurrentPageTitle() {
        String title = null;
        Stack<Flow> localFlows = getFlows();

        if (localFlows != null && localFlows.size() > 0) {
            Stack<Page> pages = localFlows.peek().getPages();

            if (pages != null && pages.size() > 0) {
                Page page = pages.peek();
                title = page.getLabel();
            }
        }

        return title;
    }

    /**
     * Returns the flows Stack
     *
     * @return the flows Stack
     */
    Stack<Flow> getFlows() {
        return flows;
    }

    /**
     * 
     * getRequest
     *
     * @return the request
     */
    HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 
     * setRequest
     *
     * @param request the request
     */
    void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 
     * getFlowScope
     *
     * @return the flowScope
     */
    FlowScope getFlowScope() {
        return flowScope;
    }

    /**
     * 
     * setFlowScope
     *
     * @param flowScope the flowScope
     */
    void setFlowScope(FlowScope flowScope) {
        this.flowScope = flowScope;
    }

    /**
     * 
     * getFlowInputScope
     *
     * @return the flowInputScope
     */
    FlowInputScope getFlowInputScope() {
        return flowInputScope;
    }

    /**
     * 
     * setFlowInputScope
     *
     * @param flowInputScope the flowInputScope
     */
    void setFlowInputScope(FlowInputScope flowInputScope) {
        this.flowInputScope = flowInputScope;
    }

    /**
     * 
     * getMessageSource
     *
     * @return the messageSource
     */
    MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * 
     * setMessageSource
     *
     * @param messageSource the messageSouce
     */
    void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
