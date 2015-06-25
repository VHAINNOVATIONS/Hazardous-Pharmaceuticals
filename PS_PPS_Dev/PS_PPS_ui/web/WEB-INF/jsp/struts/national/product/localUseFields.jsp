<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 

<peps:form id="ProductWizard3">
    <peps:model ognlPrefix="item">
<div class="topspacer"></div>
        <div class="clearLeft">
        <fieldset><legend>Local Use Fields (Page 4 of 10)</legend>
            <peps:dataField key="local.use" cssClass="aligncheckbox1" />
            <peps:dataField key="product.ndc" />
            <peps:dataField key="application.package.use" cssClass="apppackageuse" />
            <peps:dataField key="monitor.routine" />
            <peps:dataField key="lab.test.monitor" />
            <peps:dataField key="lab.monitor.mark" cssClass="aligncheckbox1"/>
            <peps:dataField key="service.code" />
            <peps:dataField key="override.df.dose.chk.exclusn" />
        </fieldset>
        </div>

        <div class="buttonspacer"></div>

    <div id="buttons" class="panel">
        <span class="floatRight">
        <s:if test="%{getConversationScopeAttribute('templateSubFlow')}">
            <span class="column"><peps:submit
                key="button.cancel"
                event="cancel" /></span>
        </s:if>
        <s:else>
            <span class="column"><peps:cancel /></span>
        </s:else>
        <%--
        <c:choose>
	        <c:when test="%{getConversationScopeAttribute('templateSubFlow')}">
	            <span class="column"><peps:submit
	                key="button.cancel"
	                event="cancel" /></span>
	        </c:when>
	        <c:otherwise>
	            <span class="column"><peps:cancel /></span>
	        </c:otherwise>
	     </c:choose>
         --%>
        <span class="column">
            <peps:submit
                key="button.bookmark"
                event="savePartial" />
        </span>        
        <span class="column">
            <peps:submit
                key="button.previous" 
                event="previous" />
        </span>       
        <span class="column">
            <peps:submit
                key="button.submit" 
                event="submit" />
        </span>
        <span class="column">
            <peps:submit
                key="button.next"
                event="next" />
        </span>
        </span>
    </div>

    </peps:model>
</peps:form>