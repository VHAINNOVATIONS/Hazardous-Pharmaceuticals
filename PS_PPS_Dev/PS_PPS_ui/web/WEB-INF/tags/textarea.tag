<%@ tag
    language="java"
    body-content="empty"
    description="Dispalys a textarea"%>
<%@ taglib  
	prefix="s"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ attribute
    name="key"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"    
    description="Resource bundle key for displaying text in button."%>
    
<%@ attribute
    name="label"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="HTML label to provide for the field."%>
    
<%@ attribute
    name="id"
    required="false"
    type="java.lang.String"
    description="ID attribte"%>

<%@ attribute
    name="labelposition"
    required="false"
    type="java.lang.String"
    description="Position of the label.(top,left)"%>  
          
<%@ attribute
    name="required"
    required="false"
    type="java.lang.Boolean"
    description="True if the field is required to be entered."%>
    
<%@ attribute
    name="title"
    required="false"
    type="java.lang.String"
    rtexprvalue="true"
    description="HTML title to provide for the field."%>
    
<%@ attribute
    name="size"
    required="false"
    type="java.lang.String"
    description="Size for the displayed field."%> 
    
<%@ attribute
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="Css Class"%>     

<%@ attribute
    name="rows"
    required="false"
    type="java.lang.String"
    description="Rows"%>  
    
<%@ attribute
    name="cols"
    required="false"
    type="java.lang.String"
    description="Columns"%>      
       
<%@ attribute
    name="value"
    required="false"
    type="java.lang.String"
    description="value"%>       

<span class="controlLabel">
<label id="label_${label}" class="label ${labelposition} readonlynational">
<c:if test="${label !=null || !empty label}">
<s:message code="${label}"/>
</c:if>
<%
    if (required != null && required == true)
    {
%>
        <span class="required">*</span> 
<%  
    }
%>
:</label>
</span>
<span class="control">
<textarea
	id="${id}"
    name="${ognl}"    
    title="${title}"
    disabled="${disabled}"    
    cols="${cols}"
    rows="${rows}"
    class="${cssClass}"
    style="${cssStyle}"
    onchange="${jsOnChange}"
    onclick="${jsOnClick}"
    onkeyup="${jsOnKeyUp}">
    ${value}
</textarea>
</span>






