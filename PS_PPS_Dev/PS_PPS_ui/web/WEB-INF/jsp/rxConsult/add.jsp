<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>


<h1>Add {item.rxConsultType}<peps:localizeName key="rx.consult" /></h1>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<peps:form id="rxConsultForm">    
        <peps:dataField item="${item}" key="value" />
        <peps:dataField
            item="${item}" 
            key="consult.text"
            rows="4"
            cols="10" />
        <peps:dataField
            item="${item}" 
            key="spanish.translation"
            rows="4"
            cols="10" />
        <peps:dataField
            item="${item}" 
            key="warning.mapping"
            managedDataAddLink="false" />    

    <br />
    <span class="column"><peps:submit
        key="button.cancel" /></span>
    <span class="column"> <peps:submit
        key="button.submit" /> </span>


</peps:form>