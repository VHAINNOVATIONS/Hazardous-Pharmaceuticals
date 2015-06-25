<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@ taglib 
	prefix="fmt" 
	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
	prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions"%>
	
<form:form modelAttribute="domainMappingBean" id="domainMappingSearch" action="/PRE/domainMapping.go" 
		   method="GET" onsubmit="return disableSubmit(this.id);">
		   <input type="hidden" id="isFirstRun" name="isFirstRun" value="false" />
		    
	<div class="row">
		<span class="column">
		<spring:message code="domain.label" var="domainLabel"/>
		<peps:controlheader    
		     id="domainChoiceFromUserLabel" 
		     name="domainChoiceFromUserLabel" 
		     label="${domainLabel}" 
		     title="${domainLabel}" 
		     labelposition="top" />			
		   	<form:select id="domainChoiceFromUser" name="domain.choice" path="domainChoice" items="${domainChoices}" tabindex="2" title="${domainLabel}"/>
		   	<peps:controlfooter    
		     id="domainChoiceFromUserLabel" 
		     name="domainChoiceFromUserLabel" 
		     label="${domainLabel}" 
		     title="${domainLabel}" 
		     labelposition="top" />
	   	</span>
	</div>
	<div class="row">&nbsp;</div>
	<div class="row">
		<span class="column">
		   	<peps:dataField id="filter.date" item="${domainMappingBean}" key="filter.date" labelPosition="top" cssClass="iahdate" tabindex="3"/>			
		</span>	
		<br />
	</div>	
	<div class="row">&nbsp;</div>
	<div class="row">
		<span class="column" style="padding-left: 20px">
			<spring:message code="domain.dialog.text" var="dialogMessage" javaScriptEscape="true"/>
			<peps:submit onclick="scriptControlledExit('${dialogMessage}')" key="Search" tabindex="4"/>
		</span>				 
	</div>
	<div class="row">&nbsp;</div>
	
	<div class="bodyheaderspacer"></div>

<input name="doQuery" type="hidden" value="true"/>


	
</form:form>

<script src="/PRE/javascript/domainMapping.js" type="text/javascript"></script>

 
<!--  <script language = "Javascript">
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
 