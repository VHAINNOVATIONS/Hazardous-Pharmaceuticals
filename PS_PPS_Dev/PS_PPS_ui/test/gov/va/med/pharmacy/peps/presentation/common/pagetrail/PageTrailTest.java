/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;




import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMockBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * PageTrail Test
 */
public class PageTrailTest {

    private static final String THE_URL_SHOULD_BE_NULL = "The url should be null";
    private static final String THE_URL_SHOULD_BE = "The url should be ";
    private static final String GET_CURRENT_URL = "getCurrentUrl";
    private static final String THE_PAGE_STACK_SHOULD_BE_THE_SAME = "The page stack should be the same";
    private static final String THE_PAGE_SHOULD_HAVE_EXISTED = "The page should have existed";
    private static final String CLEAR_PAGE_FROM_PAGES = "clearPageFromPages";
    private static final String THE_PAGE_SHOULD_NOT_HAVE_EXISTED = "The page should not have existed";
    private static final String GET_FLOWS = "getFlows";
    private static final String THERE_SHOULD_BE_TWO_FLOWS_ON_THE_PAGE_TRAIL = "There should be two flows on the page trail";
    private static final String ADD_NEW_FLOW = "addNewFlow";
    private static final String CREATE_PAGE = "createPage";
    private static final String CLEAR_PAGES_AFTER_AND_INCLUDING_PAGE = "clearPagesAfterAndIncludingPage";
    private static final String THE_PAGE_LABEL_SHOULD_BE = "The page label should be ";
    private static final String THE_PAGE_ID_SHOULD_BE = "The page id should be ";
    private static final String THERE_SHOULD_BE_ONE_PAGE_IN_THE_PAGE_STACK_IN_THE_FLOW_OF_THE_FLOW_STACK =
        "There should be one page in the page stack in the flow of the flow stack.";
    private static final String THERE_SHOULD_BE_ONE_FLOW_IN_THE_FLOW_STACK = "There should be one flow in the flow stack.";
    private static final String FOOBAR = "foobar";
    private static final String FOO3 = "foo3";
    private static final String FOO2 = "foo2";
    private static final String BAR1 = "bar1";
    private static final String FOO1 = "foo1";
    private static final String ID1 = "1";
    private static final String LABEL1 = "one";
    private static final String URL1 = "http://someurl";

    private IMockBuilder<PageTrail> pageTrailMockBuilder;
    private MockHttpSession session;
    private FlowScope flowScope;
    private FlowInputScope flowInputScope;

    /**
     * setUp
     */
    @Before
    public void setUp() {
        pageTrailMockBuilder = EasyMock.createMockBuilder(PageTrail.class).withConstructor();
        session = new MockHttpSession();
        flowScope = EasyMock.createMock(FlowScope.class);
        flowInputScope = EasyMock.createMock(FlowInputScope.class);
    }

    /**
     * tearDown
     */
    @After
    public void tearDown() {
        pageTrailMockBuilder = null;
        session.clearAttributes();
        session = null;
    }

    /**
     * testPageTrail
     */
    @Test
    public void testPageTrail() {

        PageTrail pageTrail = pageTrailMockBuilder.createMock();

        assertEquals("The flows stack should be empty after creation.", 0, pageTrail.getFlows().size());
    }

    /**
     * testAddPageStringString
     */
    @Test
    public void testAddPageStringString() {

        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod("addPage", String.class, String.class, Boolean.class).createMock();

        pageTrail.addPage(ID1, LABEL1, true);

        replay(pageTrail);

        pageTrail.addPage(ID1, LABEL1);

        verify(pageTrail);

        assertEquals("The flows stack should be empty after createion.", 0, pageTrail.getFlows().size());
    }

    /**
     * testAddPageStringStringBooleanNewFlow
     */
    @Test
    public void testAddPageStringStringBooleanNewFlow() {

        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(CLEAR_PAGES_AFTER_AND_INCLUDING_PAGE, Page.class)
                .addMockedMethod(CREATE_PAGE, String.class, String.class).addMockedMethod(ADD_NEW_FLOW).createMock();

        pageTrail.setFlowScope(flowScope);
        pageTrail.getFlows().add(new Flow());

        Page page = new Page(ID1, LABEL1, URL1);

        expect(pageTrail.createPage(ID1, LABEL1)).andReturn(page);
        expect(pageTrail.clearPagesAfterAndIncludingPage(page)).andReturn(false);
        pageTrail.addNewFlow();

        replay(pageTrail);

        pageTrail.addPage(ID1, LABEL1, true);

        verify(pageTrail);

        // Ensure the flows are what they are supposed to be for the testAddPageStringStringBooleanNewFlow test.
        assertEquals(THERE_SHOULD_BE_ONE_FLOW_IN_THE_FLOW_STACK, 1, pageTrail.getFlows().size());
        assertEquals(THERE_SHOULD_BE_ONE_PAGE_IN_THE_PAGE_STACK_IN_THE_FLOW_OF_THE_FLOW_STACK, 1,
                pageTrail.getFlows().get(0).getPages().size());
        assertEquals(THE_PAGE_ID_SHOULD_BE + ID1, ID1, pageTrail.getFlows().get(0).getPages().get(0).getId());
        assertEquals(THE_PAGE_LABEL_SHOULD_BE + LABEL1, LABEL1, pageTrail.getFlows().get(0).getPages().get(0).getLabel());

    }

    /**
     * testAddPageStringStringBooleanExistingFlowPageNotAlreadyExist
     */
    @Test
    public void testAddPageStringStringBooleanExistingFlowPageNotAlreadyExist() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(CLEAR_PAGES_AFTER_AND_INCLUDING_PAGE, Page.class)
                .addMockedMethod(CREATE_PAGE, String.class, String.class).addMockedMethod(ADD_NEW_FLOW).createMock();

        pageTrail.setFlowScope(flowScope);
        pageTrail.getFlows().add(new Flow());

        Page page = new Page(ID1, LABEL1, URL1);

        expect(pageTrail.createPage(ID1, LABEL1)).andReturn(page);
        expect(pageTrail.clearPagesAfterAndIncludingPage(page)).andReturn(false);

        replay(pageTrail);

        pageTrail.addPage(ID1, LABEL1, false);

        verify(pageTrail);

        // assertion statements for the testAddPageStringStringBooleanExistingFlowPageNotAlreadyExist test.
        assertEquals(THERE_SHOULD_BE_ONE_FLOW_IN_THE_FLOW_STACK, 1, pageTrail.getFlows().size());
        assertEquals(THERE_SHOULD_BE_ONE_PAGE_IN_THE_PAGE_STACK_IN_THE_FLOW_OF_THE_FLOW_STACK, 1,
                pageTrail.getFlows().get(0).getPages().size());
        assertEquals(THE_PAGE_ID_SHOULD_BE + ID1, ID1, pageTrail.getFlows().get(0).getPages().get(0).getId());
        assertEquals(THE_PAGE_LABEL_SHOULD_BE + LABEL1, LABEL1, pageTrail.getFlows().get(0).getPages().get(0).getLabel());

    }

    /**
     * testAddPageStringStringBooleanExistingFlowPageAlreadyExist
     */
    @Test
    public void testAddPageStringStringBooleanExistingFlowPageAlreadyExist() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(CLEAR_PAGES_AFTER_AND_INCLUDING_PAGE, Page.class)
                .addMockedMethod(CREATE_PAGE, String.class, String.class).addMockedMethod(ADD_NEW_FLOW).createMock();

        pageTrail.setFlowScope(flowScope);
        pageTrail.getFlows().add(new Flow());

        Page page = new Page(ID1, LABEL1, URL1);

        expect(pageTrail.createPage(ID1, LABEL1)).andReturn(page);
        expect(pageTrail.clearPagesAfterAndIncludingPage(page)).andReturn(true);

        replay(pageTrail);

        pageTrail.addPage(ID1, LABEL1, false);

        verify(pageTrail);

        assertEquals(THERE_SHOULD_BE_ONE_FLOW_IN_THE_FLOW_STACK, 1, pageTrail.getFlows().size());
        assertEquals(THERE_SHOULD_BE_ONE_PAGE_IN_THE_PAGE_STACK_IN_THE_FLOW_OF_THE_FLOW_STACK, 1,
                pageTrail.getFlows().get(0).getPages().size());
        assertEquals(THE_PAGE_ID_SHOULD_BE + ID1, ID1, pageTrail.getFlows().get(0).getPages().get(0).getId());
        assertEquals(THE_PAGE_LABEL_SHOULD_BE + LABEL1, LABEL1, pageTrail.getFlows().get(0).getPages().get(0).getLabel());

    }

    /**
     * testAddNewFlowFlowsEmpty
     */
    @Test
    public void testAddNewFlowFlowsEmpty() {

        PageTrail pageTrail = new PageTrail();
        pageTrail.setFlowInputScope(flowInputScope);
        pageTrail.setFlowScope(flowScope);

        flowScope.clear();
        flowScope.putAll(flowInputScope);
        flowInputScope.setBackingMap(null);

        replay(flowScope, flowInputScope);

        pageTrail.addNewFlow();

        verify(flowScope, flowInputScope);

        assertEquals("There should be one flow on the page trail", 1, pageTrail.getFlows().size());
    }

    /**
     * testAddNewFlow1ExistingFlow
     */
    @Test
    public void testAddNewFlow1ExistingFlow() {

        PageTrail pageTrail = new PageTrail();
        pageTrail.setFlowInputScope(flowInputScope);
        pageTrail.setFlowScope(flowScope);
        pageTrail.getFlows().add(new Flow());

        // testAddNewFlow1ExistingFlow
        expect(flowScope.size()).andReturn(1);
        Map<String, Object> flowInputs = new HashMap<String, Object>();
        flowInputs.put(ID1, LABEL1);
        expect(flowScope.entrySet()).andReturn(flowInputs.entrySet());

        // testAddNewFlow1ExistingFlow
        flowScope.clear();
        flowScope.putAll(flowInputScope);
        flowInputScope.setBackingMap(null);
        replay(flowScope, flowInputScope);
        pageTrail.addNewFlow();
        verify(flowScope, flowInputScope);
        assertEquals(THERE_SHOULD_BE_TWO_FLOWS_ON_THE_PAGE_TRAIL, 2, pageTrail.getFlows().size());
    }

    /**
     * testAddNewFlow2ExistingFlow
     */
    @Test
    public void testAddNewFlow2ExistingFlow() {

        PageTrail pageTrail = new PageTrail();
        pageTrail.setFlowInputScope(flowInputScope);
        pageTrail.setFlowScope(flowScope);
        pageTrail.getFlows().add(new Flow());
        pageTrail.getFlows().add(new Flow());

        expect(flowScope.size()).andReturn(1);
        Map<String, Object> flowInputs = new HashMap<String, Object>();
        flowInputs.put(ID1, LABEL1);
        expect(flowScope.entrySet()).andReturn(flowInputs.entrySet());

        flowScope.clear();
        flowScope.putAll(flowInputScope);
        flowInputScope.setBackingMap(null);

        replay(flowScope, flowInputScope);

        pageTrail.addNewFlow();

        verify(flowScope, flowInputScope);

        assertEquals("There should be three flows on the page trail", PPSConstants.I3, pageTrail.getFlows().size());
    }

    /**
     * testClearPagesAfterAndIncludingPageNoFlows
     */
    @Test
    public void testClearPagesAfterAndIncludingPageNoFlows() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();

        Stack<Flow> flows = new Stack<Flow>();

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        boolean pageOnTrail = pageTrail.clearPagesAfterAndIncludingPage(new Page(ID1, LABEL1, URL1));

        verify(pageTrail);

        assertFalse(THE_PAGE_SHOULD_NOT_HAVE_EXISTED, pageOnTrail);

    }

    /**
     * 
     * testClearPagesAfterAndIncludingPageOneFlow
     *
     */
    @Test
    public void testClearPagesAfterAndIncludingPageOneFlow() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).addMockedMethod(CLEAR_PAGE_FROM_PAGES, Page.class, Stack.class)
                .createMock();

        Stack<Flow> flows = new Stack<Flow>();
        Flow flow = new Flow();
        flows.add(flow);
        Page page = new Page(ID1, LABEL1, URL1);

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        boolean pageOnTrail = pageTrail.clearPagesAfterAndIncludingPage(page);

        verify(pageTrail);

        assertFalse(THE_PAGE_SHOULD_NOT_HAVE_EXISTED, pageOnTrail);
    }

    /**
     * 
     * testClearPagesAfterAndIncludingPageTwoFlowsPageInSecondFlow
     *
     */
    @Test
    public void testClearPagesAfterAndIncludingPageTwoFlowsPageInSecondFlow() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).addMockedMethod(CLEAR_PAGE_FROM_PAGES, Page.class, Stack.class)
                .createMock();
        pageTrail.setFlowScope(flowScope);

        Stack<Flow> flows = new Stack<Flow>();
        Flow flow1 = new Flow();
        flows.add(flow1);
        Flow flow2 = new Flow();
        flows.add(flow2);
        Page page = new Page(ID1, LABEL1, URL1);
        flow2.getPages().add(page);

        expect(pageTrail.getFlows()).andReturn(flows);
        expect(pageTrail.clearPageFromPages(page, flow2.getPages())).andReturn(true);
        flowScope.putAll(flow1.getFlowMap());

        replay(pageTrail, flowScope);

        boolean pageOnTrail = pageTrail.clearPagesAfterAndIncludingPage(page);

        verify(pageTrail, flowScope);

        assertTrue(THE_PAGE_SHOULD_HAVE_EXISTED, pageOnTrail);
    }

    /**
     * 
     * testClearPagesAfterAndIncludingPageTwoFlowsPageInFirstFlow
     *
     */
    @Test
    public void testClearPagesAfterAndIncludingPageTwoFlowsPageInFirstFlow() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).addMockedMethod(CLEAR_PAGE_FROM_PAGES, Page.class, Stack.class)
                .createMock();
        pageTrail.setFlowScope(flowScope);

        Stack<Flow> flows = new Stack<Flow>();
        Flow flow1 = new Flow();
        flows.add(flow1);
        Flow flow2 = new Flow();
        flows.add(flow2);
        Page page = new Page(ID1, LABEL1, URL1);
        flow1.getPages().add(page);

        expect(pageTrail.getFlows()).andReturn(flows);
        expect(pageTrail.clearPageFromPages(page, flow1.getPages())).andReturn(true);
        flowScope.putAll(flow1.getFlowMap());
        replay(pageTrail, flowScope);

        boolean pageOnTrail = pageTrail.clearPagesAfterAndIncludingPage(page);

        verify(pageTrail, flowScope);

        assertTrue(THE_PAGE_SHOULD_HAVE_EXISTED, pageOnTrail);
        assertEquals("There should be one flows on the page trail", 1, flows.size());
    }

    /**
     * 
     * testClearPagesAfterAndIncludingPageThreeFlowsPageInSecondFlow
     *
     */
    @Test
    public void testClearPagesAfterAndIncludingPageThreeFlowsPageInSecondFlow() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).addMockedMethod(CLEAR_PAGE_FROM_PAGES, Page.class, Stack.class)
                .createMock();
        pageTrail.setFlowScope(flowScope);

        Stack<Flow> flows = new Stack<Flow>();
        Flow flow1 = new Flow();
        flows.add(flow1);
        Flow flow2 = new Flow();
        flows.add(flow2);
        Flow flow3 = new Flow();
        flows.add(flow3);
        Page page = new Page(ID1, LABEL1, URL1);
        flow2.getPages().add(page);

        expect(pageTrail.getFlows()).andReturn(flows);
        expect(pageTrail.clearPageFromPages(page, flow2.getPages())).andReturn(true);
        flowScope.putAll(flow1.getFlowMap());
        replay(pageTrail, flowScope);

        boolean pageOnTrail = pageTrail.clearPagesAfterAndIncludingPage(page);

        verify(pageTrail, flowScope);

        assertTrue(THE_PAGE_SHOULD_HAVE_EXISTED, pageOnTrail);
        assertEquals(THERE_SHOULD_BE_TWO_FLOWS_ON_THE_PAGE_TRAIL, 2, flows.size());
    }

    /**
     * 
     * testClearPageFromPagesPageNotExist
     *
     */
    @Test
    public void testClearPageFromPagesPageNotExist() {
        PageTrail pageTrail = new PageTrail();

        Page page = new Page(ID1, LABEL1, URL1);
        Stack<Page> pages = new Stack<Page>();
        pages.add(new Page(FOO1, LABEL1, URL1));
        pages.add(new Page(FOO2, LABEL1, URL1));
        pages.add(new Page(FOO3, LABEL1, URL1));
        pages.add(new Page("foo4", LABEL1, URL1));

        boolean pageExists = pageTrail.clearPageFromPages(page, pages);

        assertFalse(THE_PAGE_SHOULD_NOT_HAVE_EXISTED, pageExists);
        assertEquals(THE_PAGE_STACK_SHOULD_BE_THE_SAME, PPSConstants.I4, pages.size());
    }

    /**
     * 
     * testClearPageFromPagesPageExistInOnePage
     *
     */
    @Test
    public void testClearPageFromPagesPageExistInOnePage() {
        PageTrail pageTrail = new PageTrail();

        Page page = new Page(ID1, LABEL1, URL1);
        Stack<Page> pages = new Stack<Page>();
        pages.add(page);

        boolean pageExists = pageTrail.clearPageFromPages(page, pages);

        assertTrue(THE_PAGE_SHOULD_HAVE_EXISTED, pageExists);
        assertEquals(THE_PAGE_STACK_SHOULD_BE_THE_SAME, 0, pages.size());
    }

    /**
     * 
     * testClearPageFromPagesPageExistInManyPagesFirstPage
     *
     */
    @Test
    public void testClearPageFromPagesPageExistInManyPagesFirstPage() {

        PageTrail pageTrail = new PageTrail();

        Page page = new Page(ID1, LABEL1, URL1);
        Stack<Page> pages = new Stack<Page>();
        pages.push(page);
        pages.push(new Page(FOO1, LABEL1, URL1));
        pages.push(new Page(FOO2, LABEL1, URL1));
        pages.push(new Page(FOO3, LABEL1, URL1));

        boolean pageExists = pageTrail.clearPageFromPages(page, pages);

        assertTrue(THE_PAGE_SHOULD_HAVE_EXISTED, pageExists);
        assertEquals("The page stack should be empty", 0, pages.size());
    }

    /**
     * 
     * testClearPageFromPagesPageExistInManyPagesMiddlePage
     *
     */
    @Test
    public void testClearPageFromPagesPageExistInManyPagesMiddlePage() {

        PageTrail pageTrail = new PageTrail();

        Page page = new Page(ID1, LABEL1, URL1);
        Stack<Page> pages = new Stack<Page>();
        pages.push(new Page(FOO1, LABEL1, URL1));
        pages.push(page);
        pages.push(new Page(FOO2, LABEL1, URL1));

        boolean pageExists = pageTrail.clearPageFromPages(page, pages);

        assertTrue(THE_PAGE_SHOULD_HAVE_EXISTED, pageExists);
        assertEquals("The page stack should contain one page", 1, pages.size());
    }

    /**
     * 
     * testClearPageFromPagesPageExistInManyPagesLastPage
     *
     */
    @Test
    public void testClearPageFromPagesPageExistInManyPagesLastPage() {

        PageTrail pageTrail = new PageTrail();

        Page page = new Page(ID1, LABEL1, URL1);
        Stack<Page> pages = new Stack<Page>();
        pages.push(new Page(FOO1, LABEL1, URL1));
        pages.push(new Page(FOO2, LABEL1, URL1));
        pages.push(new Page(FOO2, LABEL1, URL1));
        pages.push(page);

        boolean pageExists = pageTrail.clearPageFromPages(page, pages);

        assertTrue(THE_PAGE_SHOULD_HAVE_EXISTED, pageExists);
        assertEquals("The page stack should contain three pages", PPSConstants.I3, pages.size());
    }

    /**
     * 
     * testGoToPreviousUrl
     *
     */
    @Test
    public void testGoToPreviousUrl() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod("goToPreviousUrl", String.class).createMock();
        expect(pageTrail.goToPreviousUrl(null)).andReturn(null);
        replay(pageTrail);
        String url = pageTrail.goToPreviousUrl();
        verify(pageTrail);
        assertEquals(THE_URL_SHOULD_BE_NULL, null, url);
    }

    /**
     * 
     * testGoToPreviousUrlStringNoFlows
     *
     */
    @Test
    public void testGoToPreviousUrlStringNoFlows() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();

        Stack<Flow> flows = new Stack<Flow>();

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        String url = pageTrail.goToPreviousUrl(FOO1);

        verify(pageTrail);

        assertEquals(THE_URL_SHOULD_BE + FOO1, FOO1, url);

    }

    /**
     * 
     * testGoToPreviousUrlStringOneFlowWithMutiplePages
     *
     */
    @Test
    public void testGoToPreviousUrlStringOneFlowWithMutiplePages() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).addMockedMethod(GET_CURRENT_URL).createMock();

        Stack<Flow> flows = new Stack<Flow>();
        Flow flow = new Flow();

        Stack<Page> pages = new Stack<Page>();
        pages.push(new Page(FOO1, FOO1, FOO1));
        pages.push(new Page(FOO2, FOO2, FOO2));
        pages.push(new Page(FOO3, FOO3, FOO3));
        flow.setPages(pages);
        flows.add(flow);

        expect(pageTrail.getFlows()).andReturn(flows);
        expect(pageTrail.getCurrentUrl()).andReturn(FOOBAR);

        replay(pageTrail);

        String url = pageTrail.goToPreviousUrl(BAR1);

        verify(pageTrail);

        assertEquals("The url should be foobar", FOOBAR, url);
        assertEquals("There should be 2 pages in the page stack", 2, pages.size());

    }

    /**
     * 
     * testGoToPreviousUrlStringOneFlowWithOnePage
     *
     */
    @Test
    public void testGoToPreviousUrlStringOneFlowWithOnePage() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).addMockedMethod(GET_CURRENT_URL).createMock();
        pageTrail.setFlowScope(flowScope);

        Stack<Flow> flows = new Stack<Flow>();
        Flow flow = new Flow();

        Stack<Page> pages = new Stack<Page>();
        pages.push(new Page(FOO1, FOO1, FOO1));
        flow.setPages(pages);
        flows.add(flow);

        expect(pageTrail.getFlows()).andReturn(flows);
        flowScope.clear();

        replay(pageTrail, flowScope);

        String url = pageTrail.goToPreviousUrl(BAR1);

        verify(pageTrail, flowScope);

        assertEquals("The url should be bar1", BAR1, url);
        assertEquals("There should be 0 flows in the flow stack", 0, flows.size());
    }

    /**
     * 
     * testGoToPreviousUrlStringMultipleFlowsLastFlowHasOnePage
     *
     */
    @Test
    public void testGoToPreviousUrlStringMultipleFlowsLastFlowHasOnePage() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).addMockedMethod(GET_CURRENT_URL).createMock();
        pageTrail.setFlowScope(flowScope);

        Stack<Flow> flows = new Stack<Flow>();

        // first flow for testGoToPreviousUrlStringMultipleFlowsLastFlowHasOnePage.
        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, FOO1));
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);

        // second flow for testGoToPreviousUrlStringMultipleFlowsLastFlowHasOnePage.
        Flow secondFlow = new Flow();
        Stack<Page> secondPages = new Stack<Page>();
        secondPages.push(new Page(FOO1, FOO1, FOO1));
        secondFlow.setPages(secondPages);
        flows.add(secondFlow);

        expect(pageTrail.getFlows()).andReturn(flows);
        expect(pageTrail.getCurrentUrl()).andReturn(FOOBAR);
        flowScope.clear();
        flowScope.putAll(firstFlow.getFlowMap());

        replay(pageTrail, flowScope);

        String url = pageTrail.goToPreviousUrl(BAR1);

        verify(pageTrail, flowScope);

        assertEquals(THE_URL_SHOULD_BE + FOOBAR, FOOBAR, url);
        assertEquals("There should be 1 flows in the flow stack", 1, flows.size());
    }

    /**
     * 
     * testGetPreviousUrlEmptyFlows
     *
     */
    @Test
    public void testGetPreviousUrlEmptyFlows() {
        PageTrail pageTrail = new PageTrail();
        String url = pageTrail.getPreviousUrl();
        assertNull(THE_URL_SHOULD_BE_NULL, url);
    }

    /**
     * 
     * testGetPreviousUrlOneFlowOnePage
     *
     */
    @Test
    public void testGetPreviousUrlOneFlowOnePage() {
        PageTrail pageTrail =  pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();

        Stack<Flow> flows = new Stack<Flow>();
        
        // create the flow for testGetPreviousUrlOneFlowOnePage.
        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, FOO1));
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);
        expect(pageTrail.getFlows()).andReturn(flows);
        replay(pageTrail);
        String url = pageTrail.getPreviousUrl();
        verify(pageTrail);
        assertNull(THE_URL_SHOULD_BE_NULL, url);
    }

    /**
     * 
     * testGetPreviousUrlTwoFlowsOnePageEachFlow
     *
     */
    @Test
    public void testGetPreviousUrlTwoFlowsOnePageEachFlow() {
        PageTrail pageTrail = pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();
        Stack<Flow> flows = new Stack<Flow>();
        
        // first flow for testGetPreviousUrlTwoFlowsOnePageEachFlow
        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, FOO1));
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);

        // second flow for testGetPreviousUrlTwoFlowsOnePageEachFlow
        Flow secondFlow = new Flow();
        Stack<Page> secondPages = new Stack<Page>();
        secondPages.push(new Page(FOO1, FOO1, FOO1));
        secondFlow.setPages(secondPages);
        flows.add(secondFlow);

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        String url = pageTrail.getPreviousUrl();

        verify(pageTrail);

        assertEquals(THE_URL_SHOULD_BE + FOO1, FOO1, url);
    }

    /**
     * 
     * testGetPreviousUrlOneFlowMultiplePages
     *
     */
    @Test
    public void testGetPreviousUrlOneFlowMultiplePages() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();

        Stack<Flow> flows = new Stack<Flow>();

        Flow flow = new Flow();
        Stack<Page> pages = new Stack<Page>();
        pages.push(new Page(FOO1, FOO1, FOO1));
        pages.push(new Page(FOO2, FOO2, FOO2));
        pages.push(new Page(FOO3, FOO3, FOO3));
        flow.setPages(pages);
        flows.add(flow);

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        String url = pageTrail.getPreviousUrl();

        verify(pageTrail);

        assertEquals(THE_URL_SHOULD_BE + FOO2, FOO2, url);
    }

    /**
     * 
     * testGoToPreviousFlowUrl
     *
     */
    @Test
    public void testGoToPreviousFlowUrl() {

        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod("goToPreviousFlowUrl", String.class).createMock();
        expect(pageTrail.goToPreviousFlowUrl(null)).andReturn(null);
        replay(pageTrail);
        String url = pageTrail.goToPreviousFlowUrl();
        verify(pageTrail);
        assertNull(THE_URL_SHOULD_BE_NULL, url);
    }

    /**
     * 
     * testGoToPreviousFlowUrlStringFlowsEmpty
     *
     */
    @Test
    public void testGoToPreviousFlowUrlStringFlowsEmpty() {
        PageTrail pageTrail = new PageTrail();
        String url = pageTrail.goToPreviousFlowUrl(null);
        assertNull(THE_URL_SHOULD_BE_NULL, url);
    }

    /**
     * 
     * testGoToPreviousFlowUrlStringOneFlow
     *
     */
    @Test
    public void testGoToPreviousFlowUrlStringOneFlow() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();

        Stack<Flow> flows = new Stack<Flow>();
        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, FOO1));
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);

        expect(pageTrail.getFlows()).andReturn(flows);
        replay(pageTrail);
        String url = pageTrail.goToPreviousFlowUrl(null);
        verify(pageTrail);

        // assert if the URL is not null.
        assertNull(THE_URL_SHOULD_BE_NULL, url);
    }

    /**
     * 
     * testGoToPreviousFlowUrlTwoFlowsOnePageEachFlow
     *
     */
    @Test
    public void testGoToPreviousFlowUrlTwoFlowsOnePageEachFlow() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();
        pageTrail.setFlowScope(flowScope);

        // testGoToPreviousFlowUrlTwoFlowsOnePageEachFlow
        Stack<Flow> flows = new Stack<Flow>();

     // first flow for testGoToPreviousFlowUrlTwoFlowsOnePageEachFlow
        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, FOO1));
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);

        // second flow for testGoToPreviousFlowUrlTwoFlowsOnePageEachFlow
        Flow secondFlow = new Flow();
        Stack<Page> secondPages = new Stack<Page>();
        secondPages.push(new Page(FOO1, FOO1, FOO1));
        secondFlow.setPages(secondPages);
        flows.add(secondFlow);

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        //      // first flow for testGoToPreviousFlowUrlTwoFlowsOnePageEachFlow
        String url = pageTrail.goToPreviousFlowUrl(null);

        verify(pageTrail);

        assertEquals(THE_URL_SHOULD_BE + FOO1, FOO1, url);
    }

    /**
     * 
     * testGoToPreviousFlowUrlTwoFlowsOnePageEachFlowNoPagesFirstFlow
     *
     */
    @Test
    public void testGoToPreviousFlowUrlTwoFlowsOnePageEachFlowNoPagesFirstFlow() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();
        pageTrail.setFlowScope(flowScope);

        Stack<Flow> flows = new Stack<Flow>();

        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);

        // set the secondFlow for the testGoToPreviousFlowUrlTwoFlowsOnePageEachFlowNoPagesFirstFlow
        Flow secondFlow = new Flow();
        Stack<Page> secondPages = new Stack<Page>();
        secondPages.push(new Page(FOO1, FOO1, FOO1));
        secondFlow.setPages(secondPages);
        flows.add(secondFlow);

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        String url = pageTrail.goToPreviousFlowUrl(null);

        verify(pageTrail);

        assertNull(THE_URL_SHOULD_BE_NULL, url);
    }

    /**
     * 
     * testGetAllPages
     *
     */
    @Test
    public void testGetAllPages() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();

        Stack<Flow> flows = new Stack<Flow>();

        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, FOO1));
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);

        // set the second flow for the get all pages test
        Flow secondFlow = new Flow();
        Stack<Page> secondPages = new Stack<Page>();
        secondPages.push(new Page(FOO1, FOO1, FOO1));
        secondFlow.setPages(secondPages);
        flows.add(secondFlow);

        expect(pageTrail.getFlows()).andReturn(flows);
        replay(pageTrail);
        Stack<Page> pages = pageTrail.getAllPages();
        verify(pageTrail);

        assertEquals("There should be two (2) pages returned", 2, pages.size());
    }

    /**
     * 
     * testClearTrail
     *
     */
    @Test
    public void testClearTrail() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();
        pageTrail.setFlowScope(flowScope);
        Stack<Flow> flows = new Stack<Flow>();

        Flow firstFlow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, FOO1));
        firstFlow.setPages(firstPages);
        flows.add(firstFlow);

        Flow secondFlow = new Flow();
        Stack<Page> secondPages = new Stack<Page>();
        secondPages.push(new Page(FOO1, FOO1, FOO1));
        secondFlow.setPages(secondPages);
        flows.add(secondFlow);

        expect(pageTrail.getFlows()).andReturn(flows);
        flowScope.clear();

        replay(pageTrail, flowScope);

        pageTrail.clearTrail();

        verify(pageTrail, flowScope);

        assertEquals("There flows stack should be empty", 0, flows.size());
    }

    /**
     * 
     * testGetCurrentUrl
     *
     */
    @Test
    public void testGetCurrentUrl() {
        PageTrail pageTrail = pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();
        Stack<Flow> flows = new Stack<Flow>();

        // create the flow for testGetCurrentUrl
        Flow flow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, URL1));
        flow.setPages(firstPages);
        flows.add(flow);
        expect(pageTrail.getFlows()).andReturn(flows);
        replay(pageTrail);
        String url = pageTrail.getCurrentUrl();
        verify(pageTrail);
        assertEquals(THE_URL_SHOULD_BE + URL1, URL1, url);
    }

    /**
     * 
     * testGetCurrentPageTitle
     *
     */
    @Test
    public void testGetCurrentPageTitle() {
        PageTrail pageTrail =
            pageTrailMockBuilder.addMockedMethod(GET_FLOWS).createMock();
        Stack<Flow> flows = new Stack<Flow>();

        Flow flow = new Flow();
        Stack<Page> firstPages = new Stack<Page>();
        firstPages.push(new Page(FOO1, FOO1, URL1));
        flow.setPages(firstPages);
        flows.add(flow);

        expect(pageTrail.getFlows()).andReturn(flows);

        replay(pageTrail);

        String title = pageTrail.getCurrentPageTitle();

        verify(pageTrail);

        assertEquals("The title should be " + FOO1, FOO1, title);
    }

}
