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
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>  
	
<peps:form id="OIWizard3">
<peps:model ognlPrefix="item">
    <div class="clearLeft">
    <fieldset><legend>OI Additional Fields</legend>

     <div class="twoColumn">
        <peps:dataField key="lab.display.administration" />
        <peps:dataField key="lab.display.order.entry" />
        <peps:dataField key="lab.display.finishing.an.order" /> 
        <peps:dataField key="patient.instructions" />
        <peps:dataField key="other.language.instructions" />    
     </div>         
     <div class="twoColumn">
        <peps:dataField key="vitals.display.administration" />
        <peps:dataField key="vitals.display.order.entry" />
        <peps:dataField key="vitals.display.finish.an.order" />     
        <peps:dataField key="oi.iv.flag" cssClass="aligncheckbox5" />        
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
        </span>
    </div>

    </peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden">
	<s:property value="item.partialSave" />
	<%-- <c:out value="${item.partialSave}" /> --%>
</div>
