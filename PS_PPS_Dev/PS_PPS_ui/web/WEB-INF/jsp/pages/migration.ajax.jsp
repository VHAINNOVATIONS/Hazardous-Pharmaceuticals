<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
	<table width="100%" border="1">
		<tr>
			<td>
				<b>DrugUnits1:</b> <c:out value="${migrationState.drugUnitCnt}%" /> 
			</td>
			<td>
			 	<script type="text/javascript">makeBar("progress0",'10%'); setProgressWidth('progress0','<c:out value="${migrationState.drugUnitCnt}%"/>'); </script>
			</td>
		</tr>
		<tr>
			<td> 
				<b>VA DispenseUnit:</b><c:out value="${migrationState.vaDispenseUnitCnt}%"/> </li>
			</td>
			</tr>
		<tr>
			<td>
				<b>VA Generic Name:</b><c:out value="${migrationState.vaGenericNameCnt}%"/></li>
			</td>
		</tr>
		<tr>
			<td>
				<b>Dosage Form:</b> <c:out value="${migrationState.dosageFormCnt}%" /></li>
			</td>
			</tr>
		<tr>
		<td>
			<b>Drug Class:</b> <c:out value="${migrationState.drugClassCnt}%"/></li>
		</td>
		</tr>
		<tr>
			<td>
				<b>Drug Ingredients:</b> <c:out value="${migrationState.drugIngredientsCnt}%" /></li>
			</td>
		</tr>
		<tr>
			<td>
				<b>Orderable Items CSV File:</b> <c:out value="${migrationState.orderableItemsCSVFileCnt}%"/></li>
			</td>
			</tr>
		<tr>
			<td>
				<b>ProductItems CSV File:</b> <c:out value="${migrationState.productItemsCSVFileCnt}%"/></li>
			</td>
			</tr>
		<tr>
			<td>
				<b>VA Product:</b> <c:out value="${migrationState.vaProductCnt}%"/></li>
			</td>
		</tr>
		<tr>
			<td>
				<hr>
				<b>Status:</b> <c:out value="${migrationState.status}"/></li>
			</td>
		</tr>
</table>
 
