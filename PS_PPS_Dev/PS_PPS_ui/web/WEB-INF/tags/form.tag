<%@ tag
    language="java"
    body-content="scriptless"%>
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
    name="onsubmit"
    required="false"
    type="java.lang.String"
    description="onsubmit event."%>
<%@ attribute
    name="mimetype"
    required="false"
    type="java.lang.String"
    description="encrypt type"%>

<%
    if (onsubmit != null) {
        onsubmit = "disableSubmit(this.id); " + onsubmit;
        
        if (!onsubmit.endsWith(";")) {
            onsubmit += ";";
        }
        
        onsubmit += " return true;";
    }
    else {
        onsubmit = "return disableSubmit(this.id);";
    }

    jspContext.setAttribute("onsubmit", onsubmit);
    
   
    
    String action = 
    	gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility.createMvcExecutionUrl(request);

%>
<form
    id="${id}"
    name="${name}"
    onsubmit="${onsubmit}"
    enctype="${mimetype}"
    method="post"
    action="<%=action%>">
<jsp:doBody />
</form>