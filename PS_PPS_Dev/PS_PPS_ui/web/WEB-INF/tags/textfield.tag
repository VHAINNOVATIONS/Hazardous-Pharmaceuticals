<%@ tag
    language="java"
    body-content="empty"
    description="Dispalys a textfield."%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	

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
       


<spring:message text="${label}" var="labelTxt"  />
<spring:message text="${title}" var="titleTxt" />

<%
if(required == null)
	required = false;

if(labelposition == "top")
{
%>
<div>
	<label title="${titleTxt}">${titleTxt}</label>
	<%
		if (required == true)
		{
	%>
			<span class="required">*</span>	:
	<%	
		}else if(required == null || required == false)
	%>:
</div> 
<%
}
else if(labelposition == "left")
{
%>
<span>
	<label title="${titleTxt}">${titleTxt}</label>
	<%
		if (required == true)
		{
	%>
			<span class="required">*</span>	:
	<%	
		}else if(required == null || required == false)
	%>:
</span> 
<%
}
else
{
%>
<span>
	<label title="${titleTxt}">${titleTxt}</label>
	<%
		if (required == true)
		{
	%>
			<span class="required">*</span>	
	<%	
		}else if(required == null || required == false)
	%>:
</span> 
<%
}
%> 
 

<input
    type=text
    title="${titleTxt}" 
    alt="${titleTxt}" 
/>
