<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table  width="900px" style="border-style:solid; border-width:1px;">
    <tr>
        <td align="center" colspan="4"><h1 style="font-size: x-large">
                <strong>Details</strong>
            </h1></td>
    </tr>
</table>

<table  width="900px"  style="border-style:solid; border-width:1px;">

    <tr>
        <td align="center" colspan="4">
                &nbsp;&nbsp;</td>
    </tr>
    
    <tr>
        <td width="160"><strong>NDC:</strong>
        </td>
        <td width="400"><c:out value="${resultDetails.NDC}" />&nbsp;&nbsp;<c:out
                value="${resultDetails.NDCFormatCode}" /></td>
        <td width="160"><strong>Package Size:</strong></td>
        <td width="160"><c:out value="${resultDetails.packageSize}" /></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td><strong>Package Description:</strong></td>
        <td><c:out value="${resultDetails.packageDescription}" /></td>
    </tr>
    <tr>
        <td><strong>Brand Name:</strong></td>
        <td><c:out value="${resultDetails.brandName}" />
        </td>
        <td><strong>Additional Description:</strong></td>
        <td><c:out value="${resultDetails.additionalDescriptor}" />
        </td>
    </tr>
    <tr>
        <td><strong>ManDistrib:</strong></td>
        <td><c:out value="${resultDetails.manufacturerDistributorName}" />
        </td>
    </tr>
    <tr>
        <td><strong>Label Name:</strong></td>
        <td><c:out value="${resultDetails.labelName}" /></td>
    </tr>
    <tr>
        <td><strong>Label Name25:</strong></td>
        <td><c:out value="${resultDetails.labelName25}" /></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><strong>Generic Name:</strong></td>
        <td><c:out value="${resultDetails.genericName}" /></td>
    </tr>
    <tr>
        <td><strong>Drug Strength Description:</strong></td>
        <td><c:out value="${resultDetails.drugStrengthDescription}" /></td>
    </tr>
    <tr>
        <td><strong>Dose Form Description:</strong></td>
        <td><c:out value="${resultDetails.dosageFormDescription}" /></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><strong>Unit Dose Indicator:</strong></td>
        <td><c:out value="${resultDetails.unitDoseIndicator}" /></td>
        <td><strong>DEA Code:</strong></td>
        <td><c:out value="${resultDetails.DEACode}" /></td>
    </tr>
    <tr>
        <td><strong>GCN Sequence Number:</strong></td>
        <td><c:out value="${resultDetails.GCNSeqNo}" /></td>
        <td><strong>OTC/RX Indicator:</strong></td>
        <td><c:out value="${resultDetails.OTCIndicator}" /></td>
    </tr>
    <tr>
        <td><strong>Drug Form Code:</strong></td>
        <td><c:out value="${resultDetails.drugFormCode}" /></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><strong>Drug Class:</strong></td>
        <td width="500"><c:out
                value="${resultDetails.drugClassification}" /></td>
    </tr>
    <tr>
        <td><strong>Duplicate Therapy Classes:</strong></td>
        <td><c:out value="${resultDetails.duplicateTherapies}" /></td>
    </tr>
    <tr>
        <td><strong>Color:</strong></td>
        <td><c:out value="${resultDetails.color}" /></td>
    </tr>
    <tr>
        <td><strong>Shape:</strong></td>
        <td><c:out value="${resultDetails.shape}" /></td>
    </tr>
    <tr>
        <td><strong>Flavor:</strong></td>
        <td><c:out value="${resultDetails.flavor}" /></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><strong>Top 200 Rank:</strong></td>
        <td><c:out value="${resultDetails.top200Rank}" /></td>
    </tr>
    <tr>
        <td><strong>Top 50 Gen Rank:</strong></td>
        <td><c:out value="${resultDetails.top50GenRank}" /></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><strong>Replacement NDC:</strong></td>
        <td><c:out value="${resultDetails.replacementNDC}" /></td>
    </tr>
    <tr>
        <td><strong>Previous NDC:</strong></td>
        <td><c:out value="${resultDetails.previousNDC}" /></td>
    </tr>
    <tr>
        <td><strong>Obsolete Date:</strong></td>
        <td><c:out value="${resultDetails.obsoleteDate}" /></td>
    </tr>
</table>
<br />
<table  width="900px" >
    <tr>
        <td align="center" colspan="4">
           <button type="button" onclick="window.close();">Close</button>
           <button type="button" onclick="window.print();">Print</button>
         </td>
    </tr>
</table>

