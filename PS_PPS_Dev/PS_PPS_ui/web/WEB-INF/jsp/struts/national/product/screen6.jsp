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
	
<peps:form id="ProductWizard6">
<peps:model ognlPrefix="item">
<div class="topspacer"></div>
<div class="clearLeft">
<fieldset><legend>Additional Fields (Page 7 of 10)</legend>
<div class="twoColumn">
    <peps:dataField key="patient.specific.label" cssClass="aligncheckbox1" />
    <peps:dataField key="witness.to.administration" cssClass="aligncheckbox1" />
    <peps:dataField key="corresponding.inpatient.drug" />
    <peps:dataField key="corresponding.outpatient.drug" />
    <peps:dataField key="action.profile.message" />    
    <peps:dataField key="fsn" />
    <peps:dataField key="transmit.to.cmop" cssClass="aligncheckbox1"  /> 
    <peps:dataField key="quantity.dispense.message" />
    <peps:dataField key="ifcap.item.number" cssClass="itemnumber" />  
</div>
<div class="twoColumn">
    
    <peps:dataField key="max.dose.per.day" />
    <peps:dataField key="national.drug.texts" />
    <peps:dataField key="specimen.type" />
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
