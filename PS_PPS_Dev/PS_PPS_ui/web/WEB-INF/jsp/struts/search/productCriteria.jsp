<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
    
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Collections"%>

<peps:form id="search">
<fieldset>
    <LEGEND>Search Criteria for Product Search</LEGEND>
    <div class="row">
        <div class="column"><s:select
            key="searchTemplate.searchCriteria.searchTerms[0].searchField.key"
            list="searchTemplate.searchCriteria.searchFields"
            listKey="key"
            listValue="fieldKey.getLocalizedName(locale)"
            label="%{getLocalizedFieldName('search.fields')}"
            title="%{getLocalizedFieldDescription('search.fields')}"
            value="searchTemplate.searchCriteria.searchTerms[0].searchField.key"
            labelposition="top" /></div>
        <div class="column">
      
	        <s:textfield
	            key="searchTemplate.searchCriteria.searchTerms[0].value"
	            label="%{getLocalizedFieldName('search.for')}"
	            labelposition="top"
	            required="true"
	            title="%{getLocalizedFieldDescription('search.for')}"
	            size="15" 
	        />

 	      
<%-- 	      <peps:textfield --%>
<!-- 	            key="searchTemplate.searchCriteria.searchTerms[0].value" -->
<!-- 	            label="search.for.name" -->
<!-- 	            labelposition="top" -->
<!-- 	            required="true" -->
<!-- 	            title="search.for.description" -->
<!-- 	            size="15" -->
<!-- 	      /> -->
      
	     &nbsp;<div></div>(use % as wildcard)
        </div>
        <div class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
        <div class="column2">
        
        <s:textfield
            key="searchTemplate.searchCriteria.strength"
            label="%{getLocalizedFieldName('strength')}"
            labelposition="top"
            title="%{getLocalizedFieldDescription('strength')}"
            size="5" 
        />
    
<%--         <peps:textfield --%>
<!--             key="searchTemplate.searchCriteria.strength" -->
<!--             label="strength.name" -->
<!--             labelposition="top" -->
<!--             title="strength.description" -->
<!--             required="false" -->
<!--             size="5"  -->
<!--         />      -->
        	&nbsp;(number only)
        	
        </div> 
        <div class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
    <s:if test="%{environment.local}">
        <div class="column"><s:radio
                key="searchTemplate.searchCriteria.localUse"
                list="@gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType@values()"
                listKey="name()"
                listValue="getText('LocalUseSearchType.' + name())"
                label="%{getLocalizedFieldName('local.use')}"
                labelposition="top"
                title="%{getLocalizedFieldDescription('local.use')}" /></div>

        <div class="column">&nbsp;&nbsp;</div>
    </s:if>
        <div class="column"><s:checkboxlist
            key="searchTemplate.searchCriteria.itemStatus"
            list="@gov.va.med.pharmacy.peps.common.vo.ItemStatus@getValues()"
            listValue="getText('ItemStatus.' + name())"
            label="%{getLocalizedFieldName('item.status')}"
            labelposition="top"
            title="%{getLocalizedFieldDescription('item.status')}"
            cssClass="checkbox" /></div>
        <div class="column">&nbsp;&nbsp;</div>
        <div class="column"><s:checkboxlist
            key="searchTemplate.searchCriteria.requestStatus"
            list="@gov.va.med.pharmacy.peps.common.vo.RequestItemStatus@displayedValues()"
            listValue="getText('RequestItemStatus.' + name())"
            label="%{getLocalizedFieldName('request.item.status')}"
            labelposition="top"
            title="%{getLocalizedFieldDescription('request.item.status')}"
            cssClass="checkbox" /></div>
    </div>
    </fieldset>
    
    <div class="row"></div>
    <div class="eightColumn"></div>
    <div class="eightColumn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<peps:submit
        key="button.search"
        event="search" /></div>
    <div class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
    
    <div class="horizontalspacer"></div>
</peps:form>