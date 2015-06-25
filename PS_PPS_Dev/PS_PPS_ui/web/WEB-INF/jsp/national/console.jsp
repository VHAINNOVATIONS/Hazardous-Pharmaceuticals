<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--  this is the NATIONAL CONSOLE (System Information)  -->
<div class="horizontalspacer"></div>
<div class="panel">
    <peps:form id="console">
        <h2 align="center">
            <spring:message code="national.console.label" />
        </h2>

        <fieldset>
                <peps:table 
                tableId="consoleTable" 
                dataModel="${nationalStatus}"
                printTemplate="${printTemplate}" 
                paged="false" />
        </fieldset>

    </peps:form>
</div>