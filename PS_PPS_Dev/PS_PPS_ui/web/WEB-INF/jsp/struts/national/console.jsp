<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--  this is the NATIONAL CONSOLE (System Information)  -->
<div class="panel">
    <peps:form id="console">
        <h1>
            <spring:message code="national.console.label" />
        </h1>

        <fieldset>
                <peps:table 
                tableId="consoleTable" 
                dataModel="${nationalStatus}"
                printTemplate="${printTemplate}" 
                paged="false" />
        </fieldset>

<%-- LOCAL COMMENT  
        <h1><s:text name="local.console.label" /></h1> --%>
        <%-- <h1>
            <spring:message code="local.console.label" />
        </h1>

        <fieldset>
            <peps:table tableId="consoleTable" dataOgnl="localStatus"
        <!--    fieldsOgnl="printTemplate" paged="false" /> -->
                <peps:table tableId="consoleTable" dataModel="${localStatus}"
                    printTemplate="${printTemplate}" paged="false" />
        </fieldset>

        <peps:submit key="button.refresh" event="refresh" />

        <peps:submit key="button.request.information"
            event="requestLocalSystemInformation" />

        <peps:submit key="button.refresh.all"
            event="refreshAllSystemInformation" />

                <p><s:text name="console.note.1" /></p>
        <p>
            <spring:message code="console.note.1" />
        </p>

                <p><s:text name="console.note.2" /></p>
        <p>
            <spring:message code="console.note.2" />
        </p>

                <p><s:text name="console.note.3" /></p>
        <p>
            <spring:message code="console.note.3" />
        </p>

                <p><s:text name="console.note.4" /></p>
        <p>
            <spring:message code="console.note.4" />
        </p> 
--%>

    </peps:form>
</div>