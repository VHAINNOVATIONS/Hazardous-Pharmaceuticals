<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
		<table width="100%" border="1">
				<tr>
					<td>
						<b>DrugUnits1:</b> <fmt:formatNumber value="${migrationState.drugUnitCnt}"  minFractionDigits="0" maxFractionDigits="1"/>%
					</td>
					<td>
					 	<script id="type="text/javascript">makeBar("progress0",'10%'); setProgressWidth('progress0',' <c:out value="${migrationState.drugUnitCnt}%"/>'); </script>
					</td>
				</tr>
				<tr>
					<td> 
						<b>VA DispenseUnit:</b> <fmt:formatNumber  value="${migrationState.vaDispenseUnitCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress1",'10%'); setProgressWidth('progress1','<c:out value="${migrationState.vaDispenseUnitCnt}%"/>'); </script>
					</td>
					
				</tr>
				<tr>
					<td>
						<b>VA Generic Name:</b> <fmt:formatNumber value="${migrationState.vaGenericNameCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress2",'10%'); setProgressWidth('progress2','<c:out value="${migrationState.vaGenericNameCnt}%"/>'); </script>
					</td>
					
				</tr>
				<tr>
					<td>
						<b>Dosage Form:</b> <fmt:formatNumber value="${migrationState.dosageFormCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress3",'10%'); setProgressWidth('progress3','<c:out value="${migrationState.dosageFormCnt}%"/>'); </script>
					</td>
					
				</tr>
				<tr>
					<td>
						<b>Drug Class:</b> <fmt:formatNumber value="${migrationState.drugClassCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress4",'10%'); setProgressWidth('progress4','<c:out value="${migrationState.drugClassCnt}%"/>'); </script>
					</td>
					
				</tr>
				<tr>
					<td>
						<b>Drug Ingredients:</b>  <fmt:formatNumber value="${migrationState.drugIngredientsCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress5",'10%'); setProgressWidth('progress5','<c:out value="${migrationState.drugIngredientsCnt}%"/>'); </script>
					</td>
					
				</tr>
				<tr>
					<td>
						<b>Orderable Items CSV File:</b>  <fmt:formatNumber value="${migrationState.orderableItemsCSVFileCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress6",'10%'); setProgressWidth('progress6','<c:out value="${migrationState.orderableItemsCSVFileCnt}%"/>'); </script>
					</td>
				</tr>
				<tr>
					<td>
						<b>ProductItems CSV File:</b>  <fmt:formatNumber value="${migrationState.productItemsCSVFileCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress7"); setProgressWidth('progress7','<c:out value="${migrationState.productItemsCSVFileCnt}%"/>'); </script>
					</td>						
				</tr>
				<tr>
					<td>
						<b>VA Product:</b>  <fmt:formatNumber value="${migrationState.vaProductCnt}" minFractionDigits="0" maxFractionDigits="1"/>% 
					</td>
					<td>
					 	<script type="text/javascript">makeBar("progress8",'10%'); setProgressWidth('progress8','<c:out value="${migrationState.vaProductCnt}%"/>'); </script>
					</td>
				</tr>
				<tr>
					<td>
						<hr>
						<b>Status:</b> <c:out value="${migrationState.status}"/>
					</td>
				</tr>
			</table>
 
