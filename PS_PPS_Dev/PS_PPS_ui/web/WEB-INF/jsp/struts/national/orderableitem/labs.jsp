<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
 <div class="panel">
    <div class="twoColumn">
    <fieldset style="width: 450px;" ><legend class="legend">Labs</legend>
        <peps:dataField key="lab.display.order.entry" />
        <peps:dataField key="lab.display.finishing.an.order" />
        <peps:dataField key="lab.display.administration" />
    </fieldset>  
    </div>
    <div class="twoColumn">
    <fieldset style="width: 450px;" ><legend class="legend">Vitals</legend>
        <peps:dataField key="vitals.display.order.entry" />
        <peps:dataField key="vitals.display.finish.an.order" />
        <peps:dataField key="vitals.display.administration" />        
    </fieldset>  
    </div>
    <div class="horizontalspacer"></div>
 </div>