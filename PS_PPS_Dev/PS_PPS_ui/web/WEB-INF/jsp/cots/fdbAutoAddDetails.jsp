<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
    
<%@ taglib 
    prefix="fn" 
    uri="http://java.sun.com/jsp/jstl/functions" %>
    
<script type="text/javascript">

function displayFdb()
{
       window.location = '/PRE//fdbAdd.go';
    
}

</script>


<table>
        <tr><td width="160">NDC: </td><td width="500"><c:out value="${resultDetails.NDC}" />&nbsp;&nbsp;<c:out value="${resultDetails.NDCFormatCode}" /></td><td width="160">Package Size: </td><td><c:out value="${resultDetails.packageSize}" /></td></tr>
        <tr><td></td><td></td><td>Package Description: </td><td><c:out value="${resultDetails.packageDescription}" />  </td></tr>
        <tr><td>Brand Name: </td><td><c:out value="${resultDetails.brandName}" /></td><td>Additional Description: </td><td><c:out value="${resultDetails.additionalDescriptor}" /></td></tr>
        <tr><td>ManDistrib: </td><td><c:out value="${resultDetails.manufacturerDistributorName}" />  </td></tr>
        <tr><td>Label Name: </td><td><c:out value="${resultDetails.labelName}" />  </td></tr>
        <tr><td>Label Name25: </td><td><c:out value="${resultDetails.labelName25}" /> </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Generic Name: </td><td><c:out value="${resultDetails.genericName}" />  </td></tr>
        <tr><td>Drug Strength Description: </td><td><c:out value="${resultDetails.drugStrengthDescription}" /> </td></tr>
        <tr><td>Dose Form Description: </td><td><c:out value="${resultDetails.dosageFormDescription}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Unit Dose Indicator: </td><td><c:out value="${resultDetails.unitDoseIndicator}" /> </td><td>DEA Code: </td><td><c:out value="${resultDetails.DEACode}" /> </td></tr>
        <tr><td>GCN Sequence Number: </td><td><c:out value="${resultDetails.GCNSeqNo}" />  </td><td>OTC/RX Indicator: </td><td><c:out value="${resultDetails.OTCIndicator}" /> </td></tr>
        <tr><td>Drug Form Code: </td><td><c:out value="${resultDetails.drugFormCode}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Drug Class: </td><td width="500"><c:out value="${resultDetails.drugClassification}" /> </td></tr>
        <tr><td>Duplicate Therapy Classes: </td><td><c:out value="${resultDetails.duplicateTherapies}" /> </td></tr>
        <tr><td>Color: </td><td><c:out value="${resultDetails.color}" /> </td></tr>
        <tr><td>Shape: </td><td><c:out value="${resultDetails.shape}" />  </td></tr>
        <tr><td>Flavor: </td><td><c:out value="${resultDetails.flavor}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Top 200 Rank: </td><td><c:out value="${resultDetails.top200Rank}" />  </td></tr>
        <tr><td>Top 50 Gen Rank: </td><td><c:out value="${resultDetails.top50GenRank}" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Replacement NDC: </td><td><c:out value="${resultDetails.replacementNDC}" />  </td></tr>
        <tr><td>Previous NDC: </td><td><c:out value="${resultDetails.previousNDC}" /> </td></tr>
        <tr><td>Obsolete Date: </td><td><c:out value="${resultDetails.obsoleteDate}" />  </td></tr>
        </table>
        <br />
        <button type="button" onclick="history.back()">Return to Search Results</button> 
