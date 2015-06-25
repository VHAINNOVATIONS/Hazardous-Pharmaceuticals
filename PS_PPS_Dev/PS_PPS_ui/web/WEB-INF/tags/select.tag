<%@ tag
    language="java"
    body-content="empty"
    description="Dispalys a textarea"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	

<%@ attribute
    name="key"
    required="true"
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
    name="list"
    required="false"
    type="java.lang.String"
    description="Columns"%>
      
<%@ attribute
    name="listValue"
    required="false"
    type="java.lang.String"
    description="Columns"%>  
    
<spring:message text="${key}" var="keyVar" />  
<spring:message text="${label}" var="labelTxt" />
<spring:message text="${title}" var="titleTxt" />
<spring:message text="${labelposition}" var="labelPos" />


<%
%>




