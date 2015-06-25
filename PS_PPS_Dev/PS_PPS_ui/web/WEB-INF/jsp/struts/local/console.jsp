<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<!--  this is the LOCAL CONSOLE (System Information)   -->
<div class="panel"><peps:form id="console">
    <h1><s:text name="national.console.label" /></h1>
    <fieldset><peps:table
        tableId="consoleTable"
        dataOgnl="nationalStatus"
        fieldsOgnl="printTemplate"
        paged="false" /></fieldset>
    <h1><s:text name="local.console.label" /></h1>
    <fieldset><peps:table
        tableId="consoleTable"
        dataOgnl="localStatus"
        fieldsOgnl="printTemplate"
        paged="false" /> </fieldset>
</peps:form></div>