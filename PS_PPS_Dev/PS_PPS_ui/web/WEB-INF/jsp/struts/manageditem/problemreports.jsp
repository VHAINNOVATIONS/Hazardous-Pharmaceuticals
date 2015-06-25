<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@ taglib 
	prefix="form" 
	uri="http://www.springframework.org/tags/form" %> 
     <table
        id="requests"
        frame="box"
        rules="cols"
        class="pepsTable">
        <tbody>
            <tr>
                <th><peps:label key="requested.by" abbreviation="true" /></th>
                <th><peps:label key="comment" abbreviation="true" /></th>
            </tr>
            <tr>
                <td align="center">
                   <%--<s:text name="mainRequest.lastReviewer.firstName" />&nbsp;
                   <s:text name="mainRequest.lastReviewer.lastName" /> --%>
                   <spring:message code="mainRequest.lastReviewer.firstName" />&nbsp;
                   <spring:message code="mainRequest.lastReviewer.lastName" />
                </td> 
                <td align="center"> 
                   <s:textarea name="mainRequest.note" rows="10" cols="40" />
                   <%--<form:textarea id="mainRequest.note" rows="10" cols="40" /> 
                   		<textArea name="mainRequest.note" cols="40" rows="10" ></textArea>--%>
                </td>
            </tr>
        </tbody>
    </table>
