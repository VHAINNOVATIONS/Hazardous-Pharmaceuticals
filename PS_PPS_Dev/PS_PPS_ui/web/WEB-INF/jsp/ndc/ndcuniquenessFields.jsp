<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<peps:form id="NDCWizard1">

	<div class="twoColumn floatRight">
		<c:url value="/changeParent.go" var="actionUrl" />
		<input type="hidden" id="entityType" name="entityType"
			value="${fn:toLowerCase(item.entityType.parent)}" />
			<c:choose>
			<c:when test="${item.product.id != null}">
				<peps:submit key="button.change.ndc.parent" action="${actionUrl}" disabled="true"/>
			</c:when>
			<c:otherwise>
				<peps:submit key="button.select.ndc.parent" action="${actionUrl}" disabled="true"/>
			</c:otherwise>
			</c:choose>

	</div>
	<div class="topspacer">
		&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required
	</div>
	<div class="clearLeft">
		<fieldset class="background1">
			<legend>NDC Information</legend>
			<div class="twoColumn">
				<peps:dataField item="${item}" key="ndc" cssClass="uniqueness" maxlength="13" />
				<peps:dataField item="${item}" key="upc.upn" cssClass="uniqueness" maxlength="20" />
				<peps:dataField item="${item}" key="trade.name"
					cssClass="uniqueness maxlen40" maxlength="40"/>
				<peps:dataField item="${item}" key="order.unit"
					cssClass="uniqueness matchwidth" />
				<peps:dataField item="${item}" key="package.type"
					cssClass="uniqueness matchwidth" />
				<peps:dataField item="${item}" key="package.size" cssClass="uniqueness matchwidth" maxlength="11" />
				<peps:dataField item="${item}" key="manufacturer"
					cssClass="uniqueness matchwidth" />
				<peps:dataField item="${item}" key="otc.rx.indicator"
					cssClass="uniqueness matchwidth" />
				<peps:dataField item="${item}" key="single.multi.source.ndc" 
					abbreviation="true" cssClass="uniqueness matchwidth" />
			</div>
			<div class="twoColumn">
				<fieldset class="subcat">
					<legend>Sub-Category</legend>
						<peps:dataField item="${item}"  key="sub.categories" labelPosition="none" cssClass="aligncheckbox5" />
				</fieldset>
			</div>
		</fieldset>
		<div class="horizontalspacer"></div>

		<fieldset class="background2">
			<legend>Pill Information</legend>
			<div class="twoColumn">
				<peps:dataField item="${item}" key="product.number" cssClass="uniqueness maxlen40" maxlength="40" />
				<peps:dataField item="${item}" key="scored" cssClass="uniqueness matchwidth" />
				<peps:dataField item="${item}" key="color" cssClass="uniqueness matchwidth" />
				<peps:dataField item="${item}" key="shape" cssClass="uniqueness matchwidth" />
				<peps:dataField item="${item}" key="imprint" cssClass="uniqueness matchwidth maxlen25" maxlength="25" />
				<peps:dataField item="${item}" key="imprint2" cssClass="uniqueness matchwidth maxlen25" maxlength="25" />
			</div>
			<div class="twoColumn">
				<peps:dataField item="${item}" key="inactivation.date" />
				<peps:dataField item="${item}" key="protect.from.light"
					cssClass="aligncheckbox5" />
				<peps:dataField item="${item}" key="refrigeration" />
				<peps:dataField item="${item}" key="image" />
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