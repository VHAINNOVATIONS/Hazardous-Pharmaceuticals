<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
    prefix="spring"
    uri="http://www.springframework.org/tags"%>   

<peps:form id="ProductWizard3">    
<div class="topspacer"></div>
        <div class="clearLeft">
        <fieldset><legend>Local Use Fields (Page 4 of 10)</legend>
            <peps:dataField item="${item}" key="local.use" cssClass="aligncheckbox1" />
            <peps:dataField item="${item}" key="product.ndc" />
            <peps:dataField item="${item}" key="application.package.use" cssClass="apppackageuse" />
            <peps:dataField item="${item}" key="monitor.routine" />
            <peps:dataField item="${item}" key="lab.test.monitor" />
            <peps:dataField item="${item}" key="lab.monitor.mark" cssClass="aligncheckbox1"/>
            <peps:dataField item="${item}" key="service.code" />
            <peps:dataField item="${item}" key="override.df.dose.chk.exclusn" />
        </fieldset>
        </div>

        <div class="buttonspacer"></div>

    <div id="buttons" class="panel">
        <span class="floatRight">

	    <span class="column">
	       <peps:cancel />
	    </span>
         
        <span class="column">
            <peps:submit
                key="button.bookmark" />
        </span>        
        <span class="column">
            <peps:submit
                key="button.previous" />
        </span>       
        <span class="column">
            <peps:submit
                key="button.submit" />
        </span>
        <span class="column">
            <peps:submit
                key="button.next" />
        </span>
        </span>
    </div>
</peps:form>