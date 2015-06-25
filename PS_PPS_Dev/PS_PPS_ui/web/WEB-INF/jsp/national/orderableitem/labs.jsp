<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
 <div class="panel" style="height: 380px;" >
    <div class="twoColumn">
    <fieldset style="width: 450px;" ><legend class="legend">Labs</legend>
        <peps:text item="${item}" key="lab.display.order.entry" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="lab.display.finishing.an.order" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="lab.display.administration" label="true" cssClass="readonlynational" />
    </fieldset>  
    </div>
    <div class="twoColumn">
    <fieldset style="width: 450px;" ><legend class="legend">Vitals</legend>
        <peps:text item="${item}" key="vitals.display.order.entry" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="vitals.display.finish.an.order" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="vitals.display.administration" label="true" cssClass="readonlynational" />        
    </fieldset>  
    </div>
    <div class="horizontalspacer"></div>
 </div>