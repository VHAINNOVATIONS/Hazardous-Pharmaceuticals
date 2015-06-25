<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<s:if test="%{items != null}">
    <h1>Search Results</h1>
    <peps:table
        tableId="<%= gov.va.med.pharmacy.peps.presentation.common.action.SearchAction.SEARCH_RESULTS_TABLE %>"
        dataOgnl="items"
        fieldsOgnl="searchTemplate.printTemplate"  />
</s:if>