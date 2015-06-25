<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.ApplicationContextUtility"%>
<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:url value="/product/${item.id}/changeWarningLabel.go" var="changeWarningLabelLanguageUrl" />
<script type="text/javascript">
	function languageChange() {
		setCookie('warningLabels', 3);
		checkForChanges = null;
		document.forms[0].action = '${changeWarningLabelLanguageUrl}';
		document.forms[0].method = 'POST';
		document.forms[0].submit();
	}
</script>

<spring:message var="warningLabelLanguageChoiceTitle" code="spanish.text.name" />

<div class="row">
	<div class=" controlGroup" id="spanish.text.controlGroup">
		<span class=controlLabel>
		    <label accessKey="" id=label_spanish.text class="label left select " title="Language Version" for=warningLableLanguageChoice>${warningLabelLanguageChoiceTitle} :&nbsp;&nbsp;&nbsp;&nbsp; </label>
		</span>
		<span class="control">
			<select name="warningLableLanguageChoice" title="${warningLabelLanguageChoiceTitle}" id="warning.label.language.choice" onchange="languageChange()">
			    <c:forEach var="languageChoice" items="${languageChoices}">
			    <c:choose>
			    <c:when test="${languageChoice eq flowScope.languageChoice}">
			        <c:set var="selected"> selected="selected"</c:set>
			    </c:when>
			    <c:otherwise>
			        <c:set var="selected"></c:set>
			    </c:otherwise>
			    </c:choose>
			        <option value="${languageChoice}"${selected}>
			            <spring:message code="LanguageChoice.${languageChoice}" />
			        </option>
			    </c:forEach>
			</select>
		</span>
	</div>
</div>

<table class="pepsTable warningLabels"  rules="cols" frame="box">
    <thead>
        <tr>
            <th scope="col"><spring:message code="warning.label.code"/></th>
            <th scope="col"><spring:message code="warning.label.text"/></th>
        </tr>
    </thead>
    <tbody>
		<c:forEach var="warningLabel" items="${warningLabels}" varStatus="status">
            <c:choose>
                <c:when test="${status.index mod 2 eq 0}">
                    <c:set var="oddEven" value="even" />
                </c:when>
                <c:otherwise>
                    <c:set var="oddEven" value="odd" />
                </c:otherwise>
            </c:choose>
			<tr class="warningLabel ${oddEven}">
				<td class="warningLabelCode">${warningLabel.code}</td>
				<td class="warningLabelText">${warningLabel.text}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>