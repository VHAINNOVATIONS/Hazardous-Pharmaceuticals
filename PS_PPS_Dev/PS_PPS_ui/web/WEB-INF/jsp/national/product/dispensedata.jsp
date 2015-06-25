<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%> 
        
<div class="panel">
    <fieldset><legend class="legend">Dispensing Data</legend>
    <div class="twoColumn">
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<peps:link
                event="enterEditDosagesPopup"
                text="Enter / Edit Dosages"
                onclick="return popupField(this.href, this.id);" 
                cssClass="linkpadding" />
        <peps:dataField key="day.nd.or.dose.nl.limit" item="${item}"/>
        <peps:dataField key="max.dose.per.day" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" item="${item}"/>
        <peps:dataField key="quantity.dispense.message" item="${item}"/>   
        <peps:dataField key="dispense.quantity.override" item="${item}"/><br />
        <peps:dataField key="dispense.quantity.limit" item="${item}"/>
    </div> 
    <div class="twoColumn">
        <peps:dataField key="dispense.days.supply.limit" item="${item}"/>    
        <peps:dataField key="dispense.limit.for.order" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" item="${item}"/>
        <peps:dataField key="dispense.limit.for.schedule" item="${item}"/>        
        <peps:dataField key="max.dispense.limit" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" item="${item}"/>
        <peps:dataField key="total.dispense.quantity" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" item="${item}"/>        
        <peps:dataField key="dispense.override" item="${item}"/> 
        <peps:dataField key="dispense.override.reason" item="${item}"/>
        <peps:dataField key="override.reason.enterer" item="${item}"/>   
    </div>                        
    </fieldset>
    <div class="horizontalspacer"></div>
    <fieldset style="width: 446px;"><legend class="legend">Specialty Mail Settings</legend>
        <peps:dataField key="do.not.mail" cssClass="aligncheckbox225 labelreadonly" item="${item}"/>
        <peps:dataField key="default.mail.service" item="${item}"/>
    </fieldset>
</div>