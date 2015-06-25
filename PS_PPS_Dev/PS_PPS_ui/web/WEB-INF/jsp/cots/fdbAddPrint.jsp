<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
    
<%@ taglib 
    prefix="fn" 
    uri="http://java.sun.com/jsp/jstl/functions" %>
    
 <%@ taglib
    prefix="fmt" 
    uri="http://java.sun.com/jsp/jstl/fmt"  %> 


	<table  width="100%" style="border-style:solid; border-width:1px;">
	    <tr>
	        <td align="center" colspan="2">
	           <h1 style="font-size: x-large">
	                <strong>FDB ADD</strong>
	           </h1></td>
	    </tr>
	     <tr>
            <td align="left">
                <button type="button" onclick="window.close();">Close</button>
                <button type="button" onclick="window.print();">Print</button>
            </td>
              <td align="right"  colspan="8">
                Record Count: <c:out value="${fn:length(pendingList)}"/>
          </td>
        </tr>
	</table>
	<table style="border-style:solid; border-width:1px;" id=""  width="100%">
	       <tr>
	       <td>
	        <table id="fdbPendingTable"  frame="box" rules="cols" style="width:1255px;">
	        <thead>
		         <tr>
		             <td width="60px" align="center"><strong>Select All<input disabled     type="checkbox"  name="checkboxAll" onclick="selectAllCheckBoxes(this,'ADD');" /></strong></td>
		             <td width="90px" align="center"><strong>NDC</strong></td>
		             <td width="80px" align="center"><strong>GCNSeqNo</strong></td>
		             <td width="80px" align="center"><strong>Package Size</strong></td>
		             <td width="100px" align="center"><strong>Package Type</strong></td>
		             <td width="140px" align="center"><strong>Manufacturer</strong></td>
		             <td width="180px" align="center"><strong>FDB Product Name</strong></td>
		             <td width="210px" align="center"><strong>Label Name</strong></td>
		             <td width="155px" align="center"><strong>Additional Description</strong></td>
		             <c:choose>
		               <c:when test="${fdbSearchMode == true}">
		                      <td width="90px" align="center"><strong>Obsolete Date</strong></td>
		               </c:when>
		               <c:otherwise>
		                      <td width="90px"align="center"><strong>Creation Date</strong></td>
		               </c:otherwise>
		             </c:choose>
		             
		         </tr>
	     </thead>
	     </table>
	
	
	    <table id="fdbPendingTable"  frame="box" rules="cols" style="width:1255px;">
	       <c:forEach var="result" items="${pendingList}" begin="0" varStatus="status">
	           <tr>
	                <td width="60px" align="center"><input disabled id="fdbCheckBox${status.count}" type="checkbox" align="right" name="fdbItem" value="<c:out value="${status.index}"/>" onclick="selectCheckBox('<c:out value="${status.index}"/>');" /></td>
	                <td width="90px" align="center"><c:out value="${result.ndc}" /></td>
	                <td width="80px" align="center"><fmt:formatNumber minIntegerDigits="6" pattern="######" value="${result.gcnSeqno}" /></td>
	                <td width="80px" align="center"><c:out value="${result.packageSize}" /></td>
	                <td width="100px" align="center"><c:out value="${result.packageType}" /></td>
	                <td width="140px" align="center"><c:out value="${result.manufacturer}" /></td>
	                <td width="180px" align="center"><c:out value="${result.fdbProductName}" /></td>
	                <td width="210px" align="center"><c:out value="${result.labelName}" /></td>
	                <td width="155px" align="center"><c:out value="${result.addDesc}" /></td>
	                <c:choose>
	                   <c:when test="${fdbSearchMode == true}">
	                        <td width="90px"align="center"><fmt:formatDate type="both" dateStyle="short" pattern="MM-dd-yyyy" value="${result.obsoleteDateStr}" /></td>
	                   </c:when>
	                   <c:otherwise>
	                        <td width="90px"  align="center"><fmt:formatDate type="both" dateStyle="short" pattern="MM-dd-yyyy" value="${result.fdbCreationDate}" /></td>   
	                   </c:otherwise>
	                </c:choose>                                   
	           </tr>
	           <input type="hidden" value="${result.ndc}" name="ndcId"/>
	           <input type="hidden" value="${result.gcnSeqno}" name="seqNo"/>
	        </c:forEach>
	    </table>
	
	</td>
	</tr>
	</table>