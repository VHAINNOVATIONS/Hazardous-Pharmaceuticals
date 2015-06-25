<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
    <fieldset style="width: 916px;"><legend class="legend">Dispensing Data</legend>
    <div class="twoColumn">
         <peps:resumeFlowLink
                eventId="enterEditDosagesPopup"
                text="Enter / Edit Dosages"
                onclick="return popupField(this.href, this.id);" 
                cssClass="linkpadding" />
        <peps:dataField key="day.nd.or.dose.nl.limit" />
        <peps:dataField key="max.dose.per.day" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" />
        <peps:dataField key="quantity.dispense.message" />   
        <peps:dataField key="dispense.quantity.limit" />
        <peps:dataField key="dispense.quantity.override" />
    </div> 
    <div class="twoColumn">
        <peps:dataField key="dispense.days.supply.limit" />    
        <peps:dataField key="dispense.limit.for.order" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" />
        <peps:dataField key="dispense.limit.for.schedule" />        
        <peps:dataField key="max.dispense.limit" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" />
        <peps:dataField key="total.dispense.quantity" jsOnChange="stripLeadingTrailingZerosForLong(this.id);" />        
        <peps:dataField key="dispense.override" /> 
        <peps:dataField key="dispense.override.reason" />
        <peps:dataField key="override.reason.enterer" />   
    </div>                        
    </fieldset>
    <div class="horizontalspacer"></div>
    <fieldset style="width: 446px;"><legend class="legend">Specialty Mail Settings</legend>
        <peps:dataField key="do.not.mail" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="default.mail.service" />
    </fieldset>
</div>