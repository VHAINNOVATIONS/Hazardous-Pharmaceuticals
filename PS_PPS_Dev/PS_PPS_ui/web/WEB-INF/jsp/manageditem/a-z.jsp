<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
    
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo"%>

<%
            ManagedItemVo managedItem = (ManagedItemVo)request.getAttribute("item");
            FieldKey[] ignoreKeys = new FieldKey[] {FieldKey.PRIMARY_DRUG_CLASS2, FieldKey.DISPLAYABLE_INGREDIENT_NAME, FieldKey.LOCAL_DISPENSE,
            		FieldKey.DISPLAYABLE_INGREDIENT_STRENGTH, FieldKey.DISPLAYABLE_INGREDIENT_UNIT, FieldKey.DISPLAYABLE_SYNONYM_NAME,
            		FieldKey.MIGRATED_DOSAGE_FORM_NAME,FieldKey.MEDICATION, FieldKey.INVESTIGATIONAL, FieldKey.COMPOUND, FieldKey.SUPPLY,
            		FieldKey.HERBAL, FieldKey.CHEMOTHERAPY, FieldKey.OTC, FieldKey.VETERINARY};
            List<FieldKey> fields = FieldKeyUtility.getSortedFieldKeys(managedItem.getEntityType(), request.getLocale(),
                ignoreKeys);
            int numberOfColumns = (managedItem.getEntityType().isDomainType()
                && !managedItem.getEntityType().isPharmacySystem() ? 1 : 3);

            int marker = fields.size() / numberOfColumns;
            int[] columnSizes = new int[numberOfColumns];

            Arrays.fill(columnSizes, marker);
            int remainder = fields.size() % numberOfColumns;

            for (int i = 0; i < remainder; i++) {
                columnSizes[i]++;
            }

            List<List<FieldKey>> columns = new ArrayList<List<FieldKey>>(numberOfColumns);
            int columnStart = 0;
            int columnEnd = columnSizes[0];

            for (int i = 0; i < numberOfColumns; i++) {
                columns.add(fields.subList(columnStart, columnStart + columnSizes[i]));
                columnStart += columnSizes[i];
            }
            
            FieldAuthorizationVo fieldAuthorization = (FieldAuthorizationVo)request.getAttribute("fieldAuthorization");
           
%>
<div class="panel">
Legend: <font color="red">*</font> is required, L is local-only, R is read-only
<table
    width="<%=numberOfColumns==1 ? "567px" : "1220px"%>"
    id="fieldTable"
    class="pepsTable"
    style="empty-cells: show; border-style: none;">
    <thead>
        <tr>
            <%
                            for (int i = 0; i < numberOfColumns; i++) {
                            pageContext.setAttribute("columnNumber", i);
            %>
            <th style="border-left: 1px solid black; border-right: 1px solid black; border-top: 1px solid black;"><peps:label
                id="field.name.${columnNumber}"
                key="field.name"
                abbreviation="true" /></th>
            <th style="border-left: 1px solid black; border-right: 1px solid black; border-top: 1px solid black;"><peps:label
                id="current.value.${columnNumber}"
                key="current.value"
                abbreviation="true" /></th>
            <%
            if (i != numberOfColumns - 1) {
            %>
            <th style="background-color: #FFFFFF; border-style: none;" />
            <%
                        }
                        }
            %>
            
        </tr>
    </thead>
    <tbody>
        <%
                        for (int row = 0; row < columns.get(0).size(); row++) {
                        if (row % 2 == 0) {
        %>
        <tr class="odd">
            <%
                            }
                            else {
            %>
        
        <tr class="even">
            <%
                            }

                            for (int i = 0; i < numberOfColumns; i++) {
                                List<FieldKey> column = columns.get(i);

                                if (row < column.size()) {
                                    FieldKey fieldKey = column.get(row);
                                    pageContext.setAttribute("fieldKey", fieldKey);
                                    pageContext.setAttribute("key", fieldKey.getKey());

                                    if (managedItem.getEntityType().isDrugClassCode()){
                                    	pageContext.setAttribute("text", fieldKey.getLocalizedAbbreviation(request.getLocale(), managedItem
                                                .getEntityType()));
                                    }
                                    else {   
                                    	pageContext.setAttribute("text", fieldKey.getLocalizedName(request.getLocale(), managedItem
                                        .getEntityType()));
                                    }

                                    if (row == columns.get(0).size() - 1) {
                                        pageContext
                                            .setAttribute("bordersCss",
                                                "border-left: 1px solid black; border-right: 1px solid black; border-bottom: 1px solid black;");
                                    }
                                    else {
                                        pageContext.setAttribute("bordersCss",
                                            "border-left: 1px solid black; border-right: 1px solid black;");
                                    }
            %>
            <td
                width="<%=numberOfColumns==1 ? "294px" : "200px"%>"
                style="${bordersCss}">
                <peps:link
                id="${key}"
                event="editField"
                text="${text}"
                itemType="${item.entityType}"
                itemId="${item.id}"
                fieldKey="${key}"
                onclick="return popupField(this.href, this.id);"
                tooltip="${fieldLinkAltText} ${text}"
                key="${key}" />
                
                <!-- add data field identifiers for local only or disabled, and required  -->
                <c:if test="<%=fieldAuthorization.isLocalOnly(fieldKey) || fieldAuthorization.isDisabled(fieldKey) || fieldAuthorization.isRequired(fieldKey)%>">
                <font color="0066CC">[</font>
                </c:if>
	                <c:if test="<%=fieldAuthorization.isRequired(fieldKey) %>">
	                    <label title="<spring:message code="field.restrictions.required"/>"><font color="red">*</font></label>
	                </c:if> 
	                <c:if test="<%=fieldAuthorization.isLocalOnly(fieldKey) %>">
	                    <label title="<spring:message code="field.restrictions.local.only"/>"><font color="0066CC">L</font></label>
	                </c:if>  
	                <c:if test="<%=fieldAuthorization.isDisabled(fieldKey) && !fieldAuthorization.isLocalOnly(fieldKey) %>">
	                    <label title="<spring:message code="field.restrictions.read.only"/>"><font color="0066CC">R</font></label>
	                </c:if> 
                <c:if test="<%=fieldAuthorization.isLocalOnly(fieldKey) || fieldAuthorization.isDisabled(fieldKey) || fieldAuthorization.isRequired(fieldKey)%>">
                <font color="0066CC">]</font>
                </c:if>
 
            </td>
            <td
                width="<%=numberOfColumns==1 ? "294px" : "200px"%>"
                style="${bordersCss}"><span id="${key}.value"> 

                <c:if test="${item.entityType.entityType}">
                <%
                                            if (fieldKey.isGroupListDataField() || fieldKey.isMultitextDataField()
                                            || fieldKey.isMultiSelectField() || fieldKey.isMultiSelectListDataField()
                                            || fieldKey.isMultiSelectPrimaryKeyDataField()) {
                %><i><spring:message code="click.link.for.details" /></i>
                <%
                                        }
                                        else {
                
                %>
                <peps:text key="${key}" item="${item}" />
                <%
                }
                %>
            </c:if> <c:if test="${item.entityType.domainType}">
                <%
                if (fieldKey.isGroupListDataField() || fieldKey.isMultitextDataField()) {
                %><i><spring:message code="click.link.for.details" /></i>
                <%
                                        }
                                        else {
                %>
                <peps:text
                    key="${key}"
                    item="${item}" />
                <%
                }
                %>
            </c:if>
            </span></td>
            <%
                                }
                                else {
            %><td style="${bordersCss}" />
            <td style="${bordersCss}" />
            <%
                                }
                                if (i != numberOfColumns - 1) {
            %>
            
            <td
                width="10px"
                style="background-color: #FFFFFF;" />
            <%
                            }
                            }
            %>
            
        </tr>
        <%
        }
        %>
    </tbody>
</table>
</div>