<%@page
    import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@page 
    import="gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType"%>
<%@ taglib 
    prefix="peps" 
    tagdir="/WEB-INF/tags"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@ taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@ taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
    prefix="fn" 
    uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="body-content">

<div class="horizontalspacer"></div>

<form:form id="performFDBSearchOption" 
           modelAttribute="fdbSearchOption" 
           action="/PRE/reviewNdcInformation.go" 
           method="GET"
           onsubmit="return disableSubmit(this.id);">

    <div class="panel" style="width: 600px">
        <div class="controlGroup">
            <spring:message code="fdb.search.option.type.name" var="fdbTypeText" />
            <label for="fdb.search.option.type" class="label left comboBox">${fdbTypeText}:</label>
            <form:select                
                id="fdb.search.option.type" 
                path="fdbSearchOptionType"
                title="${fdbTypeText}" 
                cssClass="comboBox"
                
                items="${fdbSearchOptionType}" />
        </div>

        <div class="controlGroup">
            <spring:message code="fdb.search.string.name" var="fdbSearchText" />

            <label for="fdb.search.option.string" class="label left">${fdbSearchText}:</label>

            <form:input 
                id="fdb.search.option.string"
                path="fdbSearchString" 
                size="19" 
                title="${fdbSearchText}" />

        </div>
    </div>

    <div class="horizontalspacer"></div>
    
    <div class="panel">
        <span class="column"> 
            <peps:submit key="button.performFDBSearchOption" /> 
        </span>
    </div>

    <br />

<c:if test="${fdbSearchOption.searchOptionResults != null}"> 
<!-- Search Results////////////////////////////////////////////////////////////////////////////// -->
    <c:if test="${empty fdbSearchOption.searchOptionResults}">
        <br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <font size="4" color="red"><b>No Matching Results Found</b></font>
    </c:if>

<c:if test="${fn:length(fdbSearchOption.searchOptionResults) > 1}">
        <table
            id="ndcReviewTable"
            frame="box"
            rules="cols"
            class="pepsTable">
           
 <c:forEach var="result" items="${fdbSearchOption.searchOptionResults}" varStatus="details">
    <c:set
       scope="page"
       var="index"
       value="${details.index}" />

    <c:if test="${index == 0}">
        <thead>
        <tr>
            <th style="width: 72px">NDC</th>
            <th style="width: 40px;">Pkg Size</th>
            <th style="width: 150px">Pkg Type</th>
            <th style="width: 150px">Manufacture</th>
            <th style="width: 150px">Label Name 25</th>
            <th style="width: 150px">Add'l Description</th>
        </tr>
        </thead>
    </c:if>
    
    <c:if test="${index > -1}">  
    <tr>
        <td><a href="#" name="${result.NDC}" onclick="printNDCInfo(this.name); return<c:set var='testField' value='linkSearch' />;" >
        <c:out value="${result.NDC}" /></a></td>
        <td style="text-align: right;">
        <c:out value="${result.packageSize}" /></td>    
        <td><c:out value="${result.packageDescription}" /></td>
        <td><c:out value="${result.manufacturerDistributorName}" /></td>    
        <td><c:out value="${result.labelName25}" /></td>    
        <td><c:out value="${result.additionalDescriptor}" /></td>  
    </tr>  
    </c:if>
    
</c:forEach>
    </table>
</c:if>

    <c:if test="${fn:length(fdbSearchOption.searchOptionResults) == 1}">
    <c:forEach var="result" items="${fdbSearchOption.searchOptionResults}" varStatus="details">
        <c:set
            scope="page"
            var="index"
            value="${details.index}" />

    <c:if test="${index > -1}">
        <table>
        <tr><td width="160">NDC: </td><td width="500"><c:out value="${result.NDC}" />&nbsp;&nbsp;<c:out value="${result.NDCFormatCode}" /></td><td width="160">Package Size: </td><td><c:out value="${result.packageSize}" /></td></tr>
        <tr><td></td><td></td><td>Package Description: </td><td><c:out value="${result.packageDescription}" />  </td></tr>
        <tr><td>Brand Name: </td><td><c:out value="${result.brandName}" /></td><td>Additional Description: </td><td><c:out value="${result.additionalDescriptor}" /></td></tr>
        <tr><td>ManDistrib: </td><td><c:out value="${result.manufacturerDistributorName}" />  </td></tr>
        <tr><td>Label Name: </td><td><c:out value="${result.labelName}" />  </td></tr>
        <tr><td>Label Name25: </td><td><c:out value="${result.labelName25}" /> </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Generic Name: </td><td><c:out value="${result.genericName}" />  </td></tr>
        <tr><td>Drug Strength Description: </td><td><c:out value="${result.drugStrengthDescription}" /> </td></tr>
        <tr><td>Dose Form Description: </td><td><c:out value="${result.dosageFormDescription}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Unit Dose Indicator: </td><td><c:out value="${result.unitDoseIndicator}" /> </td><td>DEA Code: </td><td><c:out value="${result.DEACode}" /> </td></tr>
        <tr><td>GCN Seq No: </td><td><c:out value="${result.GCNSeqNo}" />  </td><td>OTC/RX Indicator: </td><td><c:out value="${result.OTCIndicator}" /> </td></tr>
        <tr><td>Drug Form Code: </td><td><c:out value="${result.drugFormCode}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Drug Class: </td><td width="500"><c:out value="${result.drugClassification}" /> </td></tr>
        <tr><td>Duplicate Therapy Classes: </td><td><c:out value="${result.duplicateTherapies}" /> </td></tr>
        <tr><td>Color: </td><td><c:out value="${result.color}" /> </td></tr>
        <tr><td>Shape: </td><td><c:out value="${result.shape}" />  </td></tr>
        <tr><td>Flavor: </td><td><c:out value="${result.flavor}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Top 200 Rank: </td><td><c:out value="${result.top200Rank}" />  </td></tr>
        <tr><td>Top 50 Gen Rank: </td><td><c:out value="${result.top50GenRank}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Replacement NDC: </td><td><c:out value="${result.replacementNDC}" />  </td></tr>
        <tr><td>Previous NDC: </td><td><c:out value="${result.previousNDC}" /> </td></tr>
        <tr><td>Obsolete Date: </td><td><c:out value="${result.obsoleteDate}" />  </td></tr>
        </table>
        <br />
        <button type="button" onclick="history.back()">Return to Search Results</button> 

    </c:if>
    </c:forEach> 
</c:if>
</c:if>
</form:form>
</div>