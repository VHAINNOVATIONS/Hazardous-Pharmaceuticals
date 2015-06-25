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
                   <label id="mainRequest.lastReviewer.firstName">${mainRequest.lastReviewer.firstName}</label>&nbsp;
                   <label id="mainRequest.lastReviewer.lastName">${mainRequest.lastReviewer.lastName}</label> 
                </td> 
                <td align="center"> 
                   <peps:textarea id="mainRequest.note"  value="${mainRequest.note}" rows="10" cols="40" />                   
                </td>
            </tr>
        </tbody>
    </table>
