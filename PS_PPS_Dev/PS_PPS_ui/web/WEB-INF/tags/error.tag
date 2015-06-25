<%@ tag
    language="java"
    body-content="scriptless"%>
<%@taglib 
	prefix="s" 
	uri="http://www.springframework.org/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib 
	prefix="fn" 
	uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute
    name="id"
    required="true"
    type="java.lang.String"
    description="HTML ID attribute for form."%>
<%@ attribute
    name="name"
    required="false"
    type="java.lang.String"
    description="HTML Name attribute for form."%>
<%@ attribute
    name="errors"
    required="true"
    type="java.util.List"
    description="The list of errors on the page"
    rtexprvalue="true"%>
<c:if test="${errors != null || !empty errors}">
<div class="validation" id="${id}">
<div class="errorMessage">

	<h3><s:message code="field.level.error3"/></h3>
	<!-- Hidden anchor used for 508 compliance, snaps window focus to error list -->
	<a id="errorAnchor" href="#errorAnchor" style="display: hidden;" title="This is the Error Messages block
	which contains a bulleted list. You can reach the block with the H key. To read the bulleted items, you 
	can use the i key."></a>
	<ul id="errorsList">
		<c:forEach var="error" items="${errors}" varStatus="status">
			<li id="error_${status.index}" class="errorMessage3">${error}</li>
		</c:forEach>		
	</ul>

</div>
</div>
</c:if>