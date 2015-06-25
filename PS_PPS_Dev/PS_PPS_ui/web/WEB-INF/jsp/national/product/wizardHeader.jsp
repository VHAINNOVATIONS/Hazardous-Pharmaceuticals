<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>


<div align="center" class="addproducttitle">Add
    New National Product</div>
    
<!-- Add some header items for creating products from FDB only -->
<c:set var="fdbAddVo" value="${flowScope.fdbAddVo}" />

<c:if test="${fdbAddVo != null}">
	<div class="twoColumn">
		<table>
			<tr><td width="146">NDC: </td><td width="300"><c:out value="${fdbAddVo.ndc}" /></td></tr>
			<tr><td>Label Name25: </td><td><c:out value="${fdbAddVo.labelName}" /> </td></tr>
		</table>
	</div>
	<div class="twoColumn" >
		<table>
			<tr><td width="146">GCN Seq No: </td><td width="300"><c:out value="${fdbAddVo.gcnSeqno}" /></td></tr>
			<tr><td>Generic Name: </td><td><c:out value="${fdbAddVo.fdbProductName}" /></td></tr>
			<tr><td>Brand Name: </td><td><c:out value="${fdbAddVo.tradeName}"/></td></tr>
		</table>
	</div>
</c:if>
<c:set var="fdbPageTrail" value="" />

    <div class="twoColumn">
        <peps:text key="<%= FieldKey.PRODUCT_LONG_NAME.getKey() %>" label="true"
            item="${item}" cssClass="editheader" />
        &nbsp;<peps:text key="<%= FieldKey.CATEGORIES.getKey() %>" label="true"
            item="${item}" cssClass="editheader" />

    </div>
    <div class="twoColumn">
        <peps:link event="edit"
                 id="productParentLink"
                 key="oi.name"
                 label="true"
                 itemId="${item.parent.id}"
                 itemType="${item.parent.entityType}" 
                 cssClass="editheader" />

<!-- The following will only be necessary when PPS-L is available -->
<%--         <c:if test="${item.parent != null}">
            <span class="column">(<peps:text key="<%= FieldKey.ORDERABLE_ITEM_TYPE.getKey() %>"
                    label="false" item="${item.parent}" />)
            </span>
        </c:if> --%>

    </div>