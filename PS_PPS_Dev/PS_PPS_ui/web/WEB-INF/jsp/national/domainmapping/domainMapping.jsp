<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			
<c:if test="${showResults}">
	<form:form modelAttribute="domainMappingBean" id="domainMappingDisplay"
		action="/PRE/submitDomainMappingPage.go" method="POST"
		onsubmit="return disableSubmit(this.id);">

		<form:hidden path="domainChoice" name="domainChoiceForSubmit"
			id="domainChoiceForSubmit" />		
			
		<form:hidden path="searchAfterSave" id="searchAfterSave" name="searchAfterSave"/>		

		<div class="panel" style="position: relative;">
			<div class="twoColumn" style="vertical-align: top;">
				<div style="height: 660px; width: 100%; overflow: auto">
					<table id="fdb.term" class="scrollableTable" border="1" width="585"
						style='table-layout: fixed' tabindex="5">
						<thead>
							<tr>
								<th id="FDBTermList" width="200" scope="col">FDB Term List</th>
								<th id="EntryDate" width="100" scope="col">Entry Date</th>
								<th id="PPSNTerm" width="285" scope="col">Associated PPS-N Term</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="term" items="${fdbTerms}" varStatus="loopStatus">
								<c:if test="${loopStatus.index == 0}">
									<c:set var="selectedEplId"
										value="fdbEpl${domainMappingBean.associationMap[term.fdbDomainId]}" />
								</c:if>
											<tr id="fdbEpl${domainMappingBean.associationMap[term.fdbDomainId]}" 
								        	    class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}"
								        	    onclick="selectEpl(this, ${fn:length(eplTerms)})">
								        	
								        		<td>
								        			<input id='${index}' type="radio" name="fdbRadioGroup" value="${term.name}" class="hidden">
								        			<label for='${index}'>${term.name}</label>
								        		</td>
												<td> <center><fmt:formatDate
														type="date" dateStyle="short" pattern="MM/dd/yyyy"
														value="${term.date}" />
														</center>
												</td>
	
								        		<td class="eplLeft" title='${eplTerms[domainMappingBean.associationMap[term.fdbDomainId]]}'> 
								        			<span class="eplLabel" >${eplTerms[domainMappingBean.associationMap[term.fdbDomainId]]}</span>
								        			<form:hidden path="associationMap[${term.fdbDomainId}]"/>
								        		</td>
	 							        	</tr>

							</c:forEach>
						</tbody>
					</table>
				</div> 
			</div> 
			<div class="twoColumn" style="vertical-align: top;">
				<div style="height: 660px; width: 100%; overflow: auto">
					<table id="epl.term" class="scrollableTable" border="1" width="585"
						style='table-layout: fixed' tabindex="6">
						<thead>
							<tr>
								<th id="TermList" width="400" scope="col">PPS-N Term List</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="term" items="${eplTerms}" varStatus="loopStatus">
								<tr id="epl.term${term.key}"
									class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}"
									onclick="associateEpl(this)">
									<td headers="TermList"><input id='${term.key}' type="radio"
										name="eplRadioGroup" value="${term.key}"
										style="display: none;"> <label for='${term.value}'>${term.value}</label>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div> 
			</div>
		</div>

		<div class="clearButton">
			<button type="button" onclick='clearEpl()'>Clear</button>
		</div>

		<div class="submitButton">
			<peps:submit key="Submit" />
		</div>

	</form:form>

	<script type="text/javascript">
	 selectEpl(document.getElementById('${selectedEplId}'), ${fn:length(eplTerms)});
	</script>

</c:if>
<!--  
 <script language = "Javascript">
var separator= "/";
 var minYear=1900;
 var maxYear=2500;

 function isInteger(s){
 var i;
 for (i = 0; i < s.length; i++){ 
//  Check that current character is a number or not.
 var c = s.charAt(i);
 if (((c < "0") || (c > "9"))) return false;
 }
//  All characters are numbers.
 return true;
 }

 function stripCharsInBag(s, bag){
 var i;
 var returnString = "";
//  Search through string's characters one by one.
//  If character is not in bag, append to returnString.
 for (i = 0; i < s.length; i++){ 
 var c = s.charAt(i);
 if (bag.indexOf(c) == -1) returnString += c;
 }
 return returnString;
 }

 function daysInFebruary (year){
//  February has 29 days in any year evenly divisible by four,
//  EXCEPT for centurial years which are not also divisible by 400.
 return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
 }
 function DaysArray(n) {
 for (var i = 1; i <= n; i++) {
 this[i] = 31
 if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
 if (i==2) {this[i] = 29}
 } 
 return this
 }

 function isDate(dtStr){
 var daysInMonth = DaysArray(12)
 var pos1=dtStr.indexOf(separator)
 var pos2=dtStr.indexOf(separator,pos1+1)
 var strMonth=dtStr.substring(0,pos1)
 var strDay=dtStr.substring(pos1+1,pos2)
 var strYear=dtStr.substring(pos2+1)
 strYr=strYear
 if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
 if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
 for (var i = 1; i <= 3; i++) {
 if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
 }
 month=parseInt(strMonth)
 day=parseInt(strDay)
 year=parseInt(strYr)
 if (pos1==-1 || pos2==-1){
 alert("The date format should be : MM/DD/YYYY")
 return false
 }
 if (strMonth.length<1 || month<1 || month>12){
 alert("Please enter a valid month")
 return false
 }
 if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
 alert("Please enter a valid day")
 return false
 }
 if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
 alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
 return false
 }
 if (dtStr.indexOf(separator,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, separator))==false){
 alert("Please enter a valid date")
 return false
 }
 alert("Entered date is valid")
 return true
 }

 function ValidateDate(){
 var dt=document.frm.txtDate
 if (isDate(dt.value)==false){
 dt.focus()
 return false
 }
 return true
 }
 </script>
  -->
