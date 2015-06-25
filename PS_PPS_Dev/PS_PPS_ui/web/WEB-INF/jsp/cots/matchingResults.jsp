<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo"%>
    
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
    
 <%@ taglib
    prefix="fmt" 
    uri="http://java.sun.com/jsp/jstl/fmt"  %>
    
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>     

<script src="/PRE/javascript/fdbMatchingResults.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/fdbupdate.css" />

 <form id="matchResultForm" name="matchResultForm" action="addNdcToProducts.go" method="post"
 	onsubmit="return disableSubmit(this.id);">
      
   <div class="body-content">
   
        <div class="horizontalspacer"></div>
        <table border="0" width="100%"  cellpadding="0" cellspacing="0">
                               
            <tr>
	            <td style="vertical-align: top">
	               <div style="overflow:auto; height:400px; width:680px; ">
				        <table border="1" style="width:670px;"  cellpadding="0" cellspacing="0"  class="pendingTable" >
    			             <thead>
    			                 <tr>
                                    <th colspan="7" align="center">
                                        <h1 style="font-size: x-large" align="center">FDB NDCs</h1>
                                    </th>
                                 </tr> 
	                             <tr>
	                                 <th align="center" width="60px">Select All<br/><input type="checkbox"  name="checkboxAll" onclick="selectAllCheckBoxes(this);" /></th>
	                                 <th align="center" width="100px">NDC</th>
	                                 <th align="center" width="100px">Label Name</th>
	                                 <th align="center" width="80px">Package Size</th>
	                                 <th align="center" width="30px">Package Type</th>
	                                 <th align="center" width="20px">GcnSeq No</th>
	                                 <th align="center" width="80px">Fdb Generic Name</th>
	                             </tr>
	                         </thead>
				             <c:choose>
	                             <c:when test="${empty flowScope.fdbAddStateBean.matchNDCList}">
	                                 <tr>
	                                     <td colspan ="5">
	                                        <font size="4" color="red"><b>No Results Found</b></font>
	                                    </td>
	                                </tr>
	                             </c:when>
	                             <c:otherwise>
	                                <c:forEach var="result" items="${flowScope.fdbAddStateBean.matchNDCList}" begin="0" varStatus="status">
	                                     <tr class="${status.index % 2 == 0 ? 'even' : 'odd'}"> 
	                                        <td align="center" valign="middle">
	                                                <c:choose>
		                                               <c:when test="${result.ndcExist}">
		                                                    <input disabled id="fdbCheckBox${status.count}" type="checkbox" align="right" name="fdbItem" value="${status.index}" onclick="selectCheckBox('<c:out value="${status.index}"/>');" />
		                                               </c:when>
		                                               <c:otherwise>
		                                                   <input checked id="fdbCheckBox${status.count}" type="checkbox" align="right" name="fdbItem" value="${status.index}" onclick="selectCheckBox('<c:out value="${status.index}"/>');" />
		                                               </c:otherwise>
	                                                </c:choose> 
	                                         </td>
	                                         
	                                      <!--  <td align="center"><a href="#" name="${result.ndc}" onclick="displayFdbDetails('<c:out value="${result.ndc}" />');" ><c:out value="${result.ndc}" /></a></td> -->
	                                        <td align="center"><a href="/PRE/fdbDetails.go?ndc=${result.ndc}" onclick="return popupWithOptions(this.href, 'print_details', 'resizable=yes,scrollbars=yes,toolbar=no, width=1000,height=500');"><c:out value="${result.ndc}" /></a></td>                                           
	                                        <td align="center" valign="middle"><c:out value="${result.labelName}" /></td>
	                                        <td align="center" valign="middle"><c:out value="${result.packageSize}" /></td>
	                                        <td align="center" valign="middle"><c:out value="${result.packageType}" /></td>
	                                        <td align="center" valign="middle"><fmt:formatNumber minIntegerDigits="6" pattern="######" value="${result.gcnSeqno}" /></td>
	                                        <td align="center" valign="middle"><c:out value="${result.fdbProductName}" /></td>
	                                     </tr>
	                                     <tr>
		                                     <td>
		                                        <input type="hidden" value="${result.ndc}" name="ndcId"/>
		                                        <input type="hidden" value="${result.gcnSeqno}" name="gcnSeqno"/>
		                                     </td>
	                                     </tr>
	                                     
	                               </c:forEach>
	                             </c:otherwise>
	                         </c:choose>
				        </table>
			        </div>
		        </td>
		        <td style="vertical-align: top; align:top;">

				         <table border="1" style="width:565px;" cellpadding="0" cellspacing="0"  class="pendingTable">
				          <thead>
				                 <tr>
				                     <th colspan="4" align="center">
                                        <h1 style="font-size: x-large" align="center">VA PRODUCTS</h1>
                                     </th>
                                 </tr>
                                 <tr>
                                     <th align="center" width="25%">Product</th>
                                     <th width="10%"  align="center">Item Status</th>
                                     <th width="10%"  align="center">GCN Sequence No</th>
                                     <th width="10%"  align="center">Use</th>
                                 </tr>
                             </thead>
				             <c:choose>
				                 <c:when test="${empty flowScope.fdbAddStateBean.closeProductsMatches}">
				                     <tr>
	                                     <td colspan="4">
	                                        <font size="4" color="red"><b>No Results Found</b></font>
	                                    </td>
	                                </tr>
				                 </c:when>
				                 <c:otherwise>
				                    <c:forEach var="productMatches" items="${flowScope.fdbAddStateBean.closeProductsMatches}" begin="0" varStatus="prod">
				                          <tr class="${prod.index % 2 == 0 ? 'even' : 'odd'}">
				                                <td width="25%" align="center"><a href="#" name="${productMatches.vaProductName}" onclick="displayProductDetails('<c:out value="${productMatches.id}" />');" ><c:out value="${productMatches.vaProductName}" /></a></td>
				                                <td width="10%" align="center"><c:out value="${productMatches.itemStatus}" /></td>
				                                <td width="10%" align="center"><fmt:formatNumber minIntegerDigits="6" pattern="######" value="${productMatches.gcnSequenceNumber}" /></td>
				                                <td width="10%" align="center"><input type="radio" name="cbProductItem" value="${productMatches.id}" onclick=""></td>
				                         </tr>
			                         </c:forEach>
			                          <input id="productId" type="hidden" value="0" name="productId"/>
				                 </c:otherwise>
				             </c:choose>
				         </table>
			     </td>
	        </tr>
        </table>
        <table width="1000px" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td><br/><br/></td>  
            </tr>
            <tr>
                  <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;            
                  </td>
                  <c:if test="${nationalMgrOrSuperRole == true}">
	                  <td colspan="2" align="center">
		                  <c:url value="/blankTemplate.go" var="blankTemplateUrl"/>
	                      <peps:submit key="button.blank.template" action="${blankTemplateUrl}"/>
	                  </td>
	                  <c:choose>
	                    <c:when test="${empty flowScope.fdbAddStateBean.closeProductsMatches}">
	                       
	                        <td colspan="2" align="center">
	                        	<c:url value="/addNdcToProducts.go" var="addNdcToProductUrl"/>
	                      		<peps:submit key="button.add.to.product" action="${addNdcToProductUrl}" onclick="return addNdcToProduct('true');"/>
	                        </td>
	                        <td colspan="2" align="center">
	                        	<c:url value="/existingTemplate.go" var="existingTemplateUrl"/>
	                      		<peps:submit key="button.use.existing" action="${existingTemplateUrl}" onclick="return useExistingProduct('true');"/>
	                        </td>
	                     
	                    </c:when>
	                    <c:otherwise>
	                        <td colspan="2" align="center">	                        
	                        	<c:url value="/addNdcToProducts.go" var="addNdcToProductUrl"/>
	                      		<peps:submit key="button.add.to.product" action="${addNdcToProductUrl}" onclick="return addNdcToProduct('false');"/>
	                        </td>
	                        <td colspan="2" align="center">
	                        	<c:url value="/existingTemplate.go" var="existingTemplateUrl"/>
	                      		<peps:submit key="button.use.existing" action="${existingTemplateUrl}" onclick="return useExistingProduct('false');"/>
	                        </td>
	                    </c:otherwise>
	                  </c:choose>
	                  <td colspan="2" align="center">
	                  		<c:url value="/searchForExistingProduct.go" var="associateProductUrl"/>
	                      	<peps:submit key="button.product.search" action="${associateProductUrl}" />
	                  </td>
                  </c:if>                  
                  <td colspan="2" align="center">
                  	<c:url value="/cancelFdbMatch.go" var="cancelUrl"/>
             		<peps:submit key="button.cancel" action="${cancelUrl}"/>
                  </td>
                  <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  </td>
            </tr>
        </table>

    </div>
</form>
<%--
  <table border="0" width="100%">
        <tr> 
           <td>
                 <fieldset>
                     <legend><b>    </b></legend>
                     <peps:table 
                         tableId="matchingResultsTable" 
                         dataModel="${pendingList}"
                         printTemplate="${printTemplate}"
                         disableMultiselectButton="true"
                         multiselectButtonAction="fdbAddDelete.go"
                      />
                 </fieldset>
                 
         </td>
       </tr>      
 </table>
  --%>