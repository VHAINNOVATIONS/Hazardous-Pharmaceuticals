<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
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
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%
            ManagedItemVo managedItem = OgnlUtility.findValue(request, "item");
            FieldKey[] ignoreKeys = new FieldKey[] {FieldKey.PRIMARY_DRUG_CLASS2, FieldKey.DISPLAYABLE_INGREDIENT_NAME};
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
%>
<div class="panel">
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
                                    pageContext.setAttribute("fieldKey", fieldKey.getKey());

                                    pageContext.setAttribute("text", fieldKey.getLocalizedName(request.getLocale(), managedItem
                                        .getEntityType()));

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
                style="${bordersCss}"><peps:resumeFlowLink
                id="${fieldKey}"
                eventId="editField"
                text="${text}"
                onclick="return popupField(this.href, this.id);"
                tooltip="${fieldLinkAltText} ${text}"
                fieldKey="${fieldKey}" /></td>
            <td
                width="<%=numberOfColumns==1 ? "294px" : "200px"%>"
                style="${bordersCss}"><span id="${fieldKey}.value"> 
                <s:if test="%{itemType.isEntityType()}">
<%--                 <c:if test="%{itemType.isEntityType()}"> --%>
                <%
                                            if (fieldKey.isGroupListDataField() || fieldKey.isMultitextDataField()
                                            || fieldKey.isMultiSelectField() || fieldKey.isMultiSelectListDataField()
                                            || fieldKey.isMultiSelectPrimaryKeyDataField()) {
                %><i><%-- <s:text name="click.link.for.details" /> --%><spring:message code="click.link.for.details" /></i>
                <%
                                        }
                                        else {
                %>
                <peps:text
                    key="${fieldKey}"
                    ognlPrefix="item" />
                <%
                }
                %>
            </s:if> <s:if test="%{itemType.isDomainType()}">    
<%--             </c:if> <c:if test="%{itemType.isDomainType()}"> --%>
                <%
                if (fieldKey.isGroupListDataField() || fieldKey.isMultitextDataField()) {
                %><i><%-- <s:text name="click.link.for.details" /> --%><spring:message code="click.link.for.details" /></i>
                <%
                                        }
                                        else {
                %>
                <peps:text
                    key="${fieldKey}"
                    ognlPrefix="item" />
                <%
                }
                %>
            </s:if>
<%--             </c:if> --%>
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