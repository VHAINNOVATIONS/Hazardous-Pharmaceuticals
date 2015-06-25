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

<peps:form id="ProductWizard2b">
<peps:model ognlPrefix="item">
<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>
    <div class="clearLeft">
    <fieldset><legend>Additional Required Fields (Page 3 of 10)</legend>

    <div class="twocolumn">
        <peps:dataField key="national.formulary.name"  cssClass="formulary" />
        <peps:dataField key="local.non.formulary"  cssClass="aligncheckbox1" />
        <peps:dataField key="national.formulary.indicator" cssClass="aligncheckbox1" /> 
        <peps:dataField key="formulary"  /> 
        <peps:dataField key="formulary.alternative" cssClass="formularyalt" />
    </div>

    <div class="panel">
       <peps:dataField key="drug.classes" jsOnClick="selectPrimaryDrugClass(this);" />
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
