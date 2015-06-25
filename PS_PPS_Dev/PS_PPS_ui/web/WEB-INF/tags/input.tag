<%@tag import="org.springframework.util.StringUtils"%>
<%@tag import="java.text.ParseException"%>
<%@tag import="java.util.Date"%>
<%@tag import="gov.va.med.pharmacy.peps.common.utility.DateFormatUtility"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.lang.reflect.InvocationTargetException"%>
<%@tag import="java.lang.reflect.Method"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey.FieldSubType"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.spring.interceptor.PepsSpringInterceptor"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ProductVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ValueObject"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ManagedDataVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.Selectable"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.UserVo"%>
<%@tag import="org.springframework.util.NumberUtils"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ItemStatus"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField"%>
<%@tag import="java.util.List"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.DataField"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility"%>
<%@tag import="gov.va.med.pharmacy.peps.service.common.scheduler.EplNationalInfo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility"%>
<%@tag language="java" body-content="empty"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@include file="input.tagf"%>
<%
    boolean multiselect = (fieldKey.isMultiSelectPrimaryKeyDataField()
            || fieldKey.isMultiSelectListDataField() 
            || fieldKey.isMultiSelectField())
            && fieldKey.isGroupedDataField();
    jspContext.setAttribute("multiselect", multiselect);

    if (fieldKey.isStringSimpleDataField()
            || fieldKey.isNumberSimpleDataField()
            || fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.TEXT_LOCAL)
            || fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.TEXT_NATIONAL)
            || fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.CONSULT_TEXT)
            || fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.DESCRIPTION)) {
        if (cols != null && rows != null) {
            jspContext.setAttribute("cssClass", "textarea " + cssClass);
        } else {
            jspContext.setAttribute("cssClass", "textfield " + cssClass);
        }
    } else if (fieldKey.isBooleanSimpleDataField()
            || gov.va.med.pharmacy.peps.common.vo.FieldKey.SELECT.equals(fieldKey)
            || java.lang.Boolean.class.isAssignableFrom(fieldClass)) {
        jspContext.setAttribute("cssClass", "checkbox " + cssClass);
    } else if (fieldKey.isPrimaryKeyDataField()
            || fieldKey.isListDataField()
            || fieldKey.isSelectableField() || fieldClass.isEnum()) {
        jspContext.setAttribute("cssClass", "select " + cssClass);
    } else if (fieldKey.isDateSimpleDataField()
            || java.util.Date.class.isAssignableFrom(fieldClass)) {
        jspContext.setAttribute("cssClass", "date " + cssClass);
    } else if (gov.va.med.pharmacy.peps.common.vo.FieldKey.PASSWORD.equals(fieldKey)) {
        jspContext.setAttribute("cssClass", "password " + cssClass);
    } else {
        if (cols != null && rows != null) {
            jspContext.setAttribute("cssClass", "textarea " + cssClass);
        } else {
            jspContext.setAttribute("cssClass", "textfield " + cssClass);
        }
    }
    
%>
<peps:controlheader 
    id="${id}" 
    key="${key}"
    name="${name}"
    label="${label}"
    title="${tooltip}" 
    labelposition="${labelPosition}"
    required="${required}" 
    labelcssClass="${cssClass}"
    labelcssStyle="${cssStyle}" 
    fieldErrors="${fieldErrors}"
    fieldAuthorization="${fieldAuthorization}"
    dataField="${dataField}" />
<%
   if (fieldKey.isStringSimpleDataField()) {
        if (cols != null && rows != null) {
            jspContext.setAttribute("cssClass", "textarea " + cssClass);
%>
<textarea 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    cols="${cols}" 
    rows="${rows}"
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}" 
    onkeyup="${jsOnKeyUp}">${value}</textarea>

<%
        } else {
            jspContext.setAttribute("cssClass", "textfield " + cssClass);
%>
<input 
    type="text" 
    id="${id}" 
    name="${name}"
    value="${value == null ? '' : value}" 
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%> 
    <c:if test="${readonly}">readonly="readonly"</c:if>
    maxlength="${maxlength}"
    size="${size}"
    tabindex="${tabindex}" 
    class="${cssClass}" 
    style="${cssStyle}"
    onchange="${jsOnChange}" 
    onclick="${jsOnClick}" 
    onkeyup="${jsOnKeyUp}"    
    onkeypress="return checkEnter(event);" />

<%
        }
    } else if (fieldKey.isBooleanSimpleDataField()) {
        //                 if (environmentUtility.isLocal()) {
        //                     jspContext.setAttribute("cssClass", "checkbox2 " + cssClass);
        //                 }
        //                 else { // national has to account for lock or empty image space
        jspContext.setAttribute("cssClass", "checkbox " + cssClass);
        boolean checked = false;
        if (jspContext.getAttribute("value") != null || "".equals(jspContext.getAttribute("value"))) {
            checked = Boolean.valueOf(jspContext.getAttribute("value").toString());
        }
        jspContext.setAttribute("checked", checked);
        //                 }
%>
<input 
    type="checkbox" 
    id="${id}" 
    name="${name}"
    value="${value}"
    title="${tooltip}"
    tabindex="${tabindex}" 
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if>
    class="${cssClass}" 
    style="${cssStyle}" 
    onclick="${jsOnClick} changeCheckboxHiddenValue(this);"
    <%=checked ? "checked=\"checked\"" : ""%> />
<input type="hidden" value="${checked}" name="${name}" id="${name}.hidden"  />

<%
    } else if (fieldKey.isNumberSimpleDataField()) {
        //System.out.println("value: "+value);
        String numberValue;
        Number number = null;
        try {
            if (jspContext.getAttribute("value") != null) {
                number = NumberUtils.parseNumber((String)jspContext.getAttribute("value").toString(), fieldClass);
            }
        }
        catch (NumberFormatException e){
            number = null;
        }
        
        if (number == null) {
        	numberValue = "";
        }
        //can't apply JCAHO rules (trim trailing zeroes) to pricing fields
        else if (fieldKey.getKey().equalsIgnoreCase("ndc.price.per.dispense.unit")) {
        	numberValue = number.toString();
        }
        else { //
        	numberValue = gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility.format(number);
        }
%>
<input 
    type="text" 
    id="${id}" 
    name="${name}"
    value="<%=numberValue %>"
    title="${tooltip}" 
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if>
    maxlength="${maxlength}"
    tabindex="${tabindex}" 
    size="${size}" 
    class="${cssClass}"
    style="${cssStyle}" 
    onchange="${jsOnChange}" 
    onclick="${jsOnClick}"
    onkeyup="${jsOnKeyUp}"
    onkeypress="return checkEnter(event);" />

<%
    } else if (fieldKey.isPrimaryKeyDataField()) {
        jspContext.setAttribute("selectedValue", jspContext.getAttribute("value"));
%>
<select 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    size="${size}"
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}" 
    <%=multiselect ? "multiple=" : ""%>>
    <c:if test="${!multiselect}">
        <option value=""></option>
    </c:if>
    <c:forEach var="option"
        items="<%=((ListDataField<ValueObject>)domainUtility.getDomain(fieldKey)).getValue()%>"
        varStatus="optionStatus">
        
        <c:choose>
            <c:when test="${option.id == value}">
                <option value="${option.id}" selected="selected"><%=((ValueObject)jspContext.getAttribute("option")).toShortString()%></option>
            </c:when>
            <c:otherwise>
                <option value="${option.id}"><%=((ValueObject)jspContext.getAttribute("option")).toShortString()%></option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>
<%
    } else if (fieldKey.isListDataField() && !FieldSubType.LIST_CHECKBOX_DATA_FIELD.equals(fieldKey.getFieldSubType())) {

        //DEA Schedule has some very specific rules for populating its list, hence we do some unique work here
        if (gov.va.med.pharmacy.peps.common.vo.FieldKey.DEA_SCHEDULE.equals(fieldKey)) {
            
            gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo csFed = (gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo) gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility
                    .getFieldValue(item, "csFedSchedule");

            if (dataField instanceof CsFedScheduleVo) {
                jspContext.setAttribute("selectedValue", ((CsFedScheduleVo) dataField).getId());
            }

            if (csFed != null) {
                jspContext.setAttribute("deaList",
                        csFed.getDeaSchedule());
                List<String> deaListSelectedValues = ((ManagedItemVo)item).getVaDataFields().getDataField(FieldKey.DEA_SCHEDULE).getValue();
                String deaListSelectedValue = deaListSelectedValues.size() > 0 ? deaListSelectedValues.get(0) : "";
                jspContext.setAttribute("deaListSelectedValue", deaListSelectedValue);
            } else {
                jspContext.setAttribute("deaList",
                        new CsFedScheduleVo().getDeaSchedule());
                jspContext.setAttribute("deaListSelectedValue", value);
            }
%>
<select 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    size="${size}"
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}">
    <option value=""></option>
    <c:forEach var="option" items="${deaList}" varStatus="optionStatus">
    
        <c:choose>
            <c:when test="${option == deaListSelectedValue}">
                <option value="${option}" selected="selected">${option}</option>
            </c:when>
            <c:otherwise>
                <option value="${option}">${option}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>
<%
        } else {
        	
            ListDataField domainList = ((ListDataField) domainUtility.getDomain(fieldKey));
            if (dataField instanceof gov.va.med.pharmacy.peps.common.vo.datafield.DataField) {
                jspContext.setAttribute("value", ((DataField) dataField).getValue());
            } else if (postValue) {
                jspContext.setAttribute("value", jspContext.getAttribute("value"));
            } else {
            	jspContext.setAttribute("value", dataField);
            }
            if (jspContext.getAttribute("value") instanceof List) {
                List tmpList = (ArrayList) jspContext.getAttribute("value");
                if (tmpList.size() > 0) {
                    jspContext.setAttribute("selectedValue", tmpList.get(0));
                } else {
                    jspContext.setAttribute("selectedValue", domainList.getDefaultValue());
                }
            } else {
                jspContext.setAttribute("selectedValue", jspContext.getAttribute("value"));
            }
           
%>
<select 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    size="${size}"
    tabindex="${tabindex}"
    class="${cssClass}"
    style="${cssStyle}" 
    onchange="${jsOnChange}" 
    onclick="${jsOnClick}"
    <%=multiselect ? "multiple=" : ""%>>
    <c:if test="${!multiselect}">
        <option value=""></option>
    </c:if>
    <c:forEach var="option" items="<%=domainList.getValue()%>"
        varStatus="optionStatus">
        
        <c:choose>
            <c:when test="${option == selectedValue}">
                <option value="${option}" selected="selected">${option}</option>
            </c:when>
            <c:otherwise>
                <option value="${option}">${option}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>
<%
        }
    } else if (fieldKey.isDateSimpleDataField()) {
        jspContext.setAttribute("cssClass", "date " + cssClass);
        Object value = jspContext.getAttribute("value");
        
        if (value instanceof String) {
            try {
                value = DateFormatUtility.convertToDate(value.toString());
                
            } catch (ParseException e) {
                value = null;
            }
        }
        jspContext.setAttribute("value", value);
%>
<peps:dateInput 
    id="${id}" 
    name="${name}"
    value="${value}"
    label="<%= label %>" 
    title="<%= tooltip %>"
    labelPosition="${labelPosition}" 
    required="${required}"
    maxlength="${maxlength}" 
    size="${size}" 
    tabindex="${tabindex}"
    cssClass="${cssClass}"
    cssStyle="${cssStyle}" 
    disabled="${disabled}"
    readonly="${readonly}"
    headerFooterRequired="false" />

<%
    } else if (fieldKey.isSelectableField()) {
        
        if (postValue) {
%>    	
      <select 
            id="${id}" 
            name="${name}"
            title="${tooltip}"
            <%=disabled ? "disabled=\"\"" : ""%>
            <c:if test="${readonly}">readonly="readonly"</c:if> 
            size="${size}"
            tabindex="${tabindex}"
            class="${cssClass}" 
            style="${cssStyle}" 
            onchange="${jsOnChange}"
            onclick="${jsOnClick}">
            <option value=""></option>
            <c:forEach var="option" items="<%=domainUtility.getDomain(fieldKey)%>"
                varStatus="optionStatus">
                <c:choose>
                    <c:when test="${option.value == value}">
                        <option value="${option.value}" selected="selected">${option.value}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${option.value}">${option.value}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>


<%
        } else {
            ArrayList<String> selectedValues = new ArrayList<String>();
            if (jspContext.getAttribute("selectedValues") != null) {
                selectedValues.addAll((ArrayList)jspContext.getAttribute("selectedValues"));
            } else if (jspContext.getAttribute("value") instanceof Selectable) {
                selectedValues.add(((Selectable) jspContext.getAttribute("value")).getId());
            } else {
                selectedValues.add(jspContext.getAttribute("value") != null ? jspContext.getAttribute("value").toString() : "");            
            }
%>

<c:choose>
<c:when test="${parentFieldKey.groupListDataField && !multiselect &&!isHidden}">	
	<input 
	    type="text" 
	    id="text_${id}" 
	    name="text_${name}"
	    title="${tooltip}"
	    alt="${tooltip}"
	    readonly="readonly"
	    <%=disabled ? "disabled=\"\"" : ""%> 
	    maxlength="${fn:length(nameValue)}"
	    tabindex="${tabindex}"
	    class="textfield"    
	    style="width:90%; min-width=${fn:length(nameValue)};" 
	    onchange="${jsOnChange}" 
	    onclick="showHiddenDropdown('${id}', '${name}', '${value}', '${fieldKey.key}'); ${jsOnClick}" 
	    onkeyup="${jsOnKeyUp}"
	    onfocus="showHiddenDropdown('${id}', '${name}', '${value}', '${fieldKey.key}');"   
	    value="<c:if test="${value != null && not empty value}">${nameValue}</c:if>" />
    <select 
	    id="${id}" 
	    name="${name}"
	    title="${tooltip}"
	    <%=disabled ? "disabled=\"\"" : ""%>
	    <c:if test="${readonly}">readonly="readonly"</c:if> 
	    size="${size}"
	    tabindex="${tabindex}"
	    class="${cssClass}" 
	    style="display: none;${cssStyle}" 
	    onchange="${jsOnChange}"
	    onclick="${jsOnClick}" >       	
   		<option value="${value}" selected="selected"><c:if test="${value != null && not empty value}">${nameValue}</c:if></option>   		
    </select>    
</c:when>
<c:otherwise>
<c:set var="options" value="<%=domainUtility.getDomain(fieldKey)%>" />
<select 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    size="${size}"    
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}" 
    <%=multiselect ? "multiple=" : ""%>>
    <c:if test="${!multiselect}">
        <option value=""></option>
    </c:if>    
    <c:forEach var="option" items="${options}"
        varStatus="optionStatus">
        <c:set var="isSelected" value="false" scope="page"></c:set>
        <c:if test="${!isHidden}">
        <c:forEach var="selectedValue" items="<%=selectedValues%>"> 
            <c:if test="${option.id == selectedValue}">
                <c:set var="isSelected" value="true" scope="page"></c:set>                              
            </c:if>
        </c:forEach>
        </c:if>
            <c:choose>
                <c:when test="${isSelected}">
                    <option value="${option.id}" selected="selected">${option.value}</option>
                </c:when>
                <c:otherwise>
                    <option value="${option.id}">${option.value}</option>
                </c:otherwise>
            </c:choose>     
    </c:forEach>
</select>
</c:otherwise>
</c:choose>
<%
        }

        if (gov.va.med.pharmacy.peps.common.vo.ManagedDataVo.class.isAssignableFrom(fieldClass) && managedDataAddLink
        		&& !gov.va.med.pharmacy.peps.common.vo.FieldKey.STANDARD_MED_ROUTE.equals(fieldKey)) {
%>
<c:if test="${!disabled || isHidden}">
<peps:parameterizeSubmit 
    key="button.add"
    event="prepareAddManagedDomain"
    id="${key}.button" 
    itemType="<%=gov.va.med.pharmacy.peps.common.vo.EntityType.toEntityType(fieldClass).name().toLowerCase()%>"    
    formElementName="${name}"
    disabled="${disabled}"
    />
</c:if>
<%
        }
%>
<%
    } else if (gov.va.med.pharmacy.peps.common.vo.FieldKey.IMAGE.equals(fieldKey)) {
%>

<c:set var="imagePath" value="${value}" scope="page" />
<a href="#"
    onclick="popup('${imagePath}', 'FDB_Image', 255, 325, 100, 100); return false;">
    <img 
    src="${imagePath}"
    alt="<%=gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility
                        .getText(request, "click.to.view.larger.image")%>"
    height="62" class="ndcImage" /> </a>

<%
    } else if (gov.va.med.pharmacy.peps.common.vo.FieldKey.PASSWORD.equals(fieldKey)) {
        jspContext.setAttribute("cssClass", "password " + cssClass);
%>


<input 
    type="password" 
    id="${id}"
    name="<%="user." + fieldKey.toAttributeName()%>" 
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    maxlength="${maxlength}"
    size="${size}"
    tabindex="${tabindex}" 
    class="${cssClass}" 
    style="${cssStyle}"
    onclick="${jsOnClick}" />


<%
    } else if (gov.va.med.pharmacy.peps.common.vo.FieldKey.SELECT.equals(fieldKey)) {
        //                 if (environmentUtility.isLocal()) {
        //                     jspContext.setAttribute("cssClass", "checkbox2 " + cssClass);
        //                 }
        //                 else {
        //jspContext.setAttribute("cssClass", "checkbox " + cssClass);
        //                 }

      String itemId = (String)gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "id");   
      jspContext.setAttribute("itemId", itemId);
       
       jspContext.setAttribute("requestId", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "requestId"));       
       jspContext.setAttribute("gcnSeqno", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "gcnSeqno"));
       jspContext.setAttribute("ndcId", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "ndc"));
       jspContext.setAttribute("itemEntityType", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "entityType"));
       
       
       // If the itemId is null, this might be a request - get the itemId out of the requestSummaryVo
       if(org.apache.commons.lang.StringUtils.isEmpty(itemId)) {
    	   jspContext.setAttribute("itemId", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "requestItemId"));
       }
       
       // If the itemId is still null, this is FDBAdd/Update so use the ndcId
       if(org.apache.commons.lang.StringUtils.isEmpty(itemId)) {
    	   jspContext.setAttribute("itemId", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "ndc"));
       }
       
       Long eplId = (Long) gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "eplId");
       if(org.apache.commons.lang.StringUtils.isEmpty(itemId) && eplId != null) {
           jspContext.setAttribute("itemId", gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(item, "eplId"));
       }

%>
<input 
    type="checkbox" 
    id="${id}" 
    name="itemIds" 
    value="${itemId}"
    title="${tooltip}" 
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if>
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" />
    
<input type="hidden" name="itemEntityTypes" value="${itemEntityType}" />
<input type="hidden" name="gcnSeqno" value="${gcnSeqno}" />
<input type="hidden" name="ndcId" value="${ndcId}" />
<input type="hidden" name="requestIds" value="${requestId}" />

<%
    } else if (fieldClass.isEnum()) {
        jspContext.setAttribute("enumValues", JspTagUtility.getEnumValues(request.getLocale(), fieldClass));
        jspContext.setAttribute("enumClass", StringUtils.unqualify(fieldClass.getName()));
        
        if (FieldSubType.LIST_CHECKBOX_DATA_FIELD.equals(fieldKey.getFieldSubType())) {
        	jspContext.setAttribute("cssClass", "checkbox " + cssClass);
    %>
    <input type="hidden" name="${name}" />
    <c:forEach var="checkbox" items="${enumValues}">
        <c:set var="checked" value="" />
        <c:forEach var="daValue" items="${value}">
            <c:if test="${checkbox.value eq daValue}">
                <c:set var="checked" value=" checked=\"checked\"" />
            </c:if>
        </c:forEach>
        <div class="controlGroup">
            <input type="checkbox"
               <%=disabled ? "disabled=\"\"" : ""%>  
               name="${name}_${checkbox.value}" 
               ${checked} 
               class="${cssClass}" 
               id="${id}.${checkbox.value}"/>           
            <label for="${name}_${checkbox.value}" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${checkbox.key}</label>
        </div>

    </c:forEach>
    <%
        } else {
%>
<select 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    size="${size}"
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}" 
    <%=multiselect ? "multiple=" : ""%>>
    <c:if test="${!multiselect}">
        <option value=""></option>
    </c:if>
    <c:forEach var="option" items="${enumValues}" varStatus="optionStatus">
        <c:choose>
            <c:when test="${option.value == value}">
                <option value="${option.value}" selected="selected">${option.key}</option>
            </c:when>
            <c:otherwise>
                <option value="${option.value}">${option.key}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>

<%
        }
    } else if (java.lang.Boolean.class.isAssignableFrom(fieldClass)) {
        //                 if (environmentUtility.isLocal()) {
        //                     jspContext.setAttribute("cssClass", "checkbox2 " + cssClass);
        //                 }
        //next line centers checkbox for a specific field in a dynamictable
        //                 else if (environmentUtility.isLocal()
        //                     && fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.DEFAULT_SCHEDULE)) {
        //                     jspContext.setAttribute("cssClass", "columnCheckbox " + cssClass);
        //                 }
        //                 else {
        jspContext.setAttribute("cssClass", "checkbox " + cssClass);
        boolean checked = false;
        
        if (jspContext.getAttribute("value") != null) {
        	String[] values = StringUtils.split(jspContext.getAttribute("value").toString(), ",");
        	if(values != null) {
            	checked = Boolean.valueOf(values[0]);
        	} else {
        		checked = Boolean.valueOf(jspContext.getAttribute("value").toString());
        	}
            jspContext.setAttribute("checked", checked);
        } else {
        	jspContext.setAttribute("checked", checked);
        }
        //                 }
%>
<input 
    type="checkbox" 
    id="${id}" 
    name="${name}"
    value="${value}"
    labelPosition="${labelPosition}" 
    label="${label}" 
    title="${tooltip}"
    tabindex="${tabindex}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    <c:if test="${required}">required="${required}"</c:if>
    cssClass="${cssClass}" 
    cssStyle="${cssStyle}" 
    onclick="${jsOnClick} changeCheckboxHiddenValue(this);"
    <%=checked ? "checked=\"checked\"" : ""%> />
<input type="hidden" value="${checked}" name="${name}" id="${name}.hidden"  />

<%
    } else if (java.util.Date.class.isAssignableFrom(fieldClass)) {
        jspContext.setAttribute("cssClass", "date " + cssClass);
        Object value = jspContext.getAttribute("value");
        
        if (value instanceof String) {
            try {
                value = DateFormatUtility.convertToDate(value.toString());
                
            } catch (ParseException e) {
                value = null;
            }
        }
        jspContext.setAttribute("value", value);
            
%>

<peps:dateInput 
    id="${id}" 
    name="${name}"
    value="${value}"
    label="<%= label %>" 
    title="<%= tooltip %>"
    labelPosition="${labelPosition}" 
    required="${required}"
    maxlength="${maxlength}" 
    size="${size}" 
    tabindex="${tabindex}"
    cssClass="${cssClass}"
    cssStyle="${cssStyle}" 
    disabled="${disabled}"
    readonly="${readonly}"
    headerFooterRequired="false" />

<%
    } else { // assume it's a textfield with the name pointing directly at the property and value
        if (cols != null && rows != null) {
            jspContext.setAttribute("cssClass", "textarea " + cssClass);
%>

<textarea 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    cols="${cols}" 
    rows="${rows}"
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}">${value}</textarea>

<%
        // make these textareas for use in a-z tab
        } else if (fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.TEXT_LOCAL)
                || fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.TEXT_NATIONAL)
                || fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.CONSULT_TEXT)
                || fieldKey.equals(gov.va.med.pharmacy.peps.common.vo.FieldKey.DESCRIPTION)) {
            jspContext.setAttribute("cols", "25");
            jspContext.setAttribute("rows", "4");
            jspContext.setAttribute("cssClass", "textarea " + cssClass);
%>

<textarea 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    cols="${cols}" 
    rows="${rows}"
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}">${value}</textarea>

<%
        } else {
            jspContext.setAttribute("cssClass", "textfield " + cssClass);
            
            //JCAHO Compliance Formatting
            if (Double.class.isAssignableFrom(fieldClass)
                    || Float.class.isAssignableFrom(fieldClass)) {  
                Number number = null;
                try {
                    if(jspContext.getAttribute("value")!=null)
                        number = NumberUtils.parseNumber(jspContext.getAttribute("value").toString(), fieldClass);
                }
                catch (NumberFormatException e){
                    number = null;
                }
                
%>

<input 
    type="text" 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    maxlength="${maxlength}"
    cols="${cols}"
    rows="${rows}"
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}"
    onclick="${jsOnClick}" 
    onkeyup="${jsOnKeyUp}"
    onkeypress="return checkEnter(event);"
    value="<%= number == null ? "" : gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility.format(number)%>" />
                                
<%
            } else {
            	
            	//adding this to assign column width for textfields in multiples
            	if(jspContext.getAttribute("cssStyle") == null) {
                	 jspContext.setAttribute("cssStyle", "vertical-align: top");
          		} 
%>

<input 
    type="text" 
    id="${id}" 
    name="${name}"
    title="${tooltip}"
    <%=disabled ? "disabled=\"\"" : ""%>
    <c:if test="${readonly}">readonly="readonly"</c:if> 
    maxlength="${maxlength}"
    size="${size}" 
    tabindex="${tabindex}"
    class="${cssClass}" 
    style="${cssStyle}" 
    onchange="${jsOnChange}" 
    onclick="${jsOnClick}" 
    onkeyup="${jsOnKeyUp}"
    onkeypress="return checkEnter(event);"
    value="${value == null ? '' : value}" />

<% 
	if (fieldKey.getKey().equals(FieldKey.FDA_MED_GUIDE.getKey()) && dataField != null) {
	//String fdaHostName = "http://vaww.fda.gov"; /* use this value for now until it can be retrieved from the database */
	gov.va.med.pharmacy.peps.common.vo.UserVo user = PepsSpringInterceptor.getUserContext(request.getSession()).getUser();
    String fdaHostName = user.getHostName();
    jspContext.setAttribute("fdaHostName", fdaHostName);
	String fdaMedGuideName = JspTagUtility.getText(request, dataField.toString());
	String fdaDocumentLink = fdaHostName + "/" + fdaMedGuideName;
	jspContext.setAttribute("fdaDocumentLink", fdaDocumentLink);
%>
    <a href="${fdaDocumentLink}" target="_blank"><img src="<c:url value="/images/pdf.png" />" height="16px;" width="16px;" 
    alt="Click to view the FDA MED GUIDE" />
    </a>

<% 
	} 
%>

<%
            }
        }
    }
%>
<peps:controlfooter 
    id="${id}" 
    key="${key}"
    name="${name}"
    label="${label}"
    title="${tooltip}" 
    labelposition="${labelPosition}"
    required="${required}" 
    labelcssClass="${cssClass}"
    labelcssStyle="${cssStyle}" 
    fieldErrors="${fieldErrors}"
    fieldAuthorization="${fieldAuthorization}"
    dataField="${dataField}" />