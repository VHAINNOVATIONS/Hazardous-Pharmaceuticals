<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
    
	<%--
	<s:if test="%{wizard.prev}">
	    <span class="column"><peps:submit
	        key="button.previous"
	        event="prev" />
	   	</span>
	</s:if>
	--%>
<c:if test="%{wizard.prev}">
    <span class="column">
    	<peps:submit
        	key="button.previous"
        	event="prev" />
    </span>
</c:if>
	<%--  
	<s:if test="%{getConversationScopeAttribute('templateSubFlow')}">
	    <span class="column"><peps:submit
	        key="button.cancel"
	        event="cancel" /></span>
	</s:if>
	<s:else>
	    <span class="column"><peps:cancel /></span>
	</s:else>
	--%>
<c:choose>
	<c:when test="%{getConversationScopeAttribute('templateSubFlow')}">
	    <span class="column"><peps:submit
	        key="button.cancel"
	        event="cancel" /></span>
	</c:when>
	<c:otherwise>
	    <span class="column"><peps:cancel /></span>
	</c:otherwise>
</c:choose>
<span class="column">
	<peps:submit
    	key="button.submit"
    	event="submit" /></span>
	<%--
	<s:if test="%{wizard.next}">
	    <span class="column"><peps:submit
	        key="button.next"
	        event="next" /></span>
	</s:if>
	--%>
<c:if test="%{wizard.next}">
    <span class="column"><peps:submit
        key="button.next"
        event="next" /></span>
</c:if>