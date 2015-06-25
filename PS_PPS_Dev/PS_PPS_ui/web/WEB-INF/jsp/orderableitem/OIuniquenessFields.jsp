<%@ taglib 
	prefix="peps" 
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	uri="http://tiles.apache.org/tags-tiles" 
	prefix="tiles"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>      
    
<peps:form id="OIWizard1">
	<div id="loiParentDiv" class="twoColumn floatRight hidden">
	   <peps:submit key="button.select.loi.parent" />
	</div>
		
<script language="javascript" type="text/javascript">
	function toggleOIParentSelect()
	{
	    var buttonDiv = document.getElementById('loiParentDiv');
	    var labelDiv = document.getElementById('parentNameDiv');
	    var oiType = document.getElementById('item.orderableItemType');
	    if (oiType.value == "NATIONAL") {
	     	buttonDiv.className = 'twoColumn floatRight hidden';
	        labelDiv.className = 'controlGroup hidden';
	    } else {
	        buttonDiv.className = 'twoColumn floatRight';
	        labelDiv.className = 'controlGroup';
	    }
	}
</script>
		<div class="topspacer"><label style="left: 20px;">Fields marked with <font color="red">*</font> are required</label>
		</div>

		<div class="topspacer"></div>
		<div class="clearLeft">
			<fieldset class="background1">
				<legend>OI Uniqueness Fields</legend>
				<div class="panel">
				
<%-- 				<c:choose> --%>
<%-- 					<c:when test="${environemnt.national}"> --%>
						<peps:text item="${item}" key="orderable.item.type" label="true"
							cssClass="readonlynational" />
<%-- 					</c:when> --%>
<%-- 					<c:otherwise>				 --%>
<%-- 						<peps:dataField item="${item}" key="orderable.item.type" --%>
<!-- 							jsOnChange="toggleOIParentSelect()" /> -->
<%-- 					</c:otherwise> --%>
<%-- 				</c:choose> --%>
					<peps:dataField item="${item}" key="generic.name" />
					<peps:dataField item="${item}" key="dosage.form" />
					<c:url value="/${fn:toLowerCase(item.entityType)}/generateOINames.go" var="generateNamesUrl"/>
					<peps:submit key="button.generate.oi.names"	onclick="checkGenericName();" action="${generateNamesUrl}"/>
					<peps:dataField item="${item}" key="vista.oi.name" cssClass="maxlen40" maxlength="40" />
					<peps:dataField item="${item}" key="oi.name" cssClass="maxlen71" maxlength="71" />
					<peps:dataField item="${item}" key="national.formulary.indicator" />					
					<peps:dataField item="${item}" key="standard.med.route" />
					<br />

                <div class="column" > 
					<fieldset class="subcat" style="width: 250px;">
						<legend>Category <span class="required">*</span> </legend>
				   			<peps:dataField item="${item}"  key="categories" labelPosition="none" cssClass="aligncheckbox1" />
                </div>  
                <div class="column"> 
					<fieldset class="subcat" style="width: 250px;">
						<legend>Sub-Category</legend>
				   			<peps:dataField item="${item}"  key="sub.categories" labelPosition="none" cssClass="aligncheckbox11" />
                </div>



				</div>
			</fieldset>
		</div>

		<div class="buttonspacer"></div>
		<div id="buttons" class="panel">
			<span class="floatRight">			
				<span class="column">
	                <c:url value="/${fn:toLowerCase(item.entityType)}/addCancel.go" var="cancelUrl"/>
	                <peps:submit key="button.cancel" action="${cancelUrl}"/> 
                </span>
				<span class="column">
					<peps:submit key="button.submit" />
				</span>
			</span>
		</div>

</peps:form>
<div id="partial.save.flag" class="hidden">
	<c:out value="${item.partialSave}" />
</div>