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

<peps:form id="ProductWizard7">
<peps:model ognlPrefix="item">
<div class="topspacer"></div>
<div class="clearLeft">
<fieldset><legend>Additional Fields (Page 8 of 10)</legend>
<div class="twoColumn">
    <peps:dataField key="ar.ws.amis.category" />
    <peps:dataField key="ar.ws.amis.conversion.number"  />
    <peps:dataField key="number.of.doses"  />
    <peps:dataField key="inpatient.medication.expired.order.max.time" />
    <peps:dataField key="inpatient.medication.expired.order.min.time" />
    <peps:dataField key="unit.dose.schedule" />
    <peps:dataField key="unit.dose.schedule.type" />
</div>
<div class="twoColumn">
    <peps:dataField key="do.not.mail"  />
    <peps:dataField key="follow.up.time" />
    <peps:dataField key="high.risk" />
    <peps:dataField key="high.risk.followup"  />
    <peps:dataField key="high.risk.followup.time.period" cssClass="highrisk" />
</div>

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
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /><%--<c:out value="item.partialSave" /> --%></div>
