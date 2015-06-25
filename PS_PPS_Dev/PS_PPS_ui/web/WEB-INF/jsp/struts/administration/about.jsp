<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<peps:text
    key="<%= FieldKey.BUILD_VERSION.getKey() %>"
    label="true" />
<peps:text
    key="<%= FieldKey.BUILD_DATE.getKey() %>"
    label="true" />
<div class="controlGroup" id="build.version.controlGroup"><peps:label
    key="<%= FieldKey.BUILD_BASELINE.getKey() %>" />:
<pre><%=OgnlUtility.findValue(request, "buildBaseline").toString()%></pre>
</div>