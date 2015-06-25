<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%> 	
<%@ taglib  
	prefix="form"
	uri="http://www.springframework.org/tags/form"%> 	
	    
<peps:form id="userPreferences">
<%--    
    <s:if test="%{environment.local}">
        <!-- Notifications are really a form of a Struts checkbox list, but since this is a custom view, we have to make the list manually -->
        <input type="hidden" id="__multiselect_user.notifications" name="__multiselect_user.notifications" value="" />
        <fieldset><LEGEND>Notification Preferences</LEGEND>
        <table
            align="left"
            valign="center"
            cellpadding="0"
            cellspacing="0">
            <thead>
                <tr>
                    <th width="210px">Item Type</th>
                    <th width="110px">Approved</th>
                    <th width="110px">Rejected</th>
                    <th width="110px">Modified</th>
                    <th width="110px">Inactivated</th>
                    <th width="110px">Local Use</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td align="left">Orderable Item</td>
                    <td align="center"><input
                        type="checkbox"
                        id="APPROVED_ORDERABLE_ITEMS"
                        name="user.notifications"
                        value="APPROVED_ORDERABLE_ITEMS"
                        <s:if test="user.isNotificationSet('APPROVED_ORDERABLE_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="REJECTED_ORDERABLE_ITEMS"
                        name="user.notifications"
                        value="REJECTED_ORDERABLE_ITEMS" 
                        <s:if test="user.isNotificationSet('REJECTED_ORDERABLE_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="MODIFIED_ORDERABLE_ITEMS"
                        name="user.notifications"
                        value="MODIFIED_ORDERABLE_ITEMS" 
                        <s:if test="user.isNotificationSet('MODIFIED_ORDERABLE_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="INACTIVATED_ORDERABLE_ITEMS"
                        name="user.notifications"
                        value="INACTIVATED_ORDERABLE_ITEMS" 
                        <s:if test="user.isNotificationSet('INACTIVATED_ORDERABLE_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="ORDERABLE_ITEM_MARKED_FOR_LOCAL_USE"
                        name="user.notifications"
                        value="ORDERABLE_ITEM_MARKED_FOR_LOCAL_USE" 
                        <s:if test="user.isNotificationSet('ORDERABLE_ITEM_MARKED_FOR_LOCAL_USE')">checked="checked"</s:if> /></td>
                </tr>
                <tr>
                    <td align="left">Product</td>
                    <td align="center"><input
                        type="checkbox"
                        id="APPROVED_PRODUCT_ITEMS"
                        name="user.notifications"
                        value="APPROVED_PRODUCT_ITEMS" 
                        <s:if test="user.isNotificationSet('APPROVED_PRODUCT_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="REJECTED_PRODUCT_ITEMS"
                        name="user.notifications"
                        value="REJECTED_PRODUCT_ITEMS" 
                        <s:if test="user.isNotificationSet('REJECTED_PRODUCT_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="MODIFIED_PRODUCT_ITEMS"
                        name="user.notifications"
                        value="MODIFIED_PRODUCT_ITEMS" 
                        <s:if test="user.isNotificationSet('MODIFIED_PRODUCT_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="INACTIVATED_PRODUCT_ITEMS"
                        name="user.notifications"
                        value="INACTIVATED_PRODUCT_ITEMS" 
                        <s:if test="user.isNotificationSet('INACTIVATED_PRODUCT_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="PRODUCT_ITEM_MARKED_FOR_LOCAL_USE"
                        name="user.notifications"
                        value="PRODUCT_ITEM_MARKED_FOR_LOCAL_USE" 
                        <s:if test="user.isNotificationSet('PRODUCT_ITEM_MARKED_FOR_LOCAL_USE')">checked="checked"</s:if> /></td>
                </tr>
                <tr>
                    <td align="left">NDC</td>
                    <td align="center"><input
                        type="checkbox"
                        id="APPROVED_NDC_ITEMS"
                        name="user.notifications"
                        value="APPROVED_NDC_ITEMS" 
                        <s:if test="user.isNotificationSet('APPROVED_NDC_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="REJECTED_NDC_ITEMS"
                        name="user.notifications"
                        value="REJECTED_NDC_ITEMS" 
                        <s:if test="user.isNotificationSet('REJECTED_NDC_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="MODIFIED_NDC_ITEMS"
                        name="user.notifications"
                        value="MODIFIED_NDC_ITEMS" 
                        <s:if test="user.isNotificationSet('MODIFIED_NDC_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center"><input
                        type="checkbox"
                        id="INACTIVATED_NDC_ITEMS"
                        name="user.notifications"
                        value="INACTIVATED_NDC_ITEMS" 
                        <s:if test="user.isNotificationSet('INACTIVATED_NDC_ITEMS')">checked="checked"</s:if> /></td>
                    <td align="center">&nbsp;</td>
                </tr>
            </tbody>
        </table>
        <p>
        <div
            id="SYSTEM_EVENT.controlGroup"
            class="controlGroup"><span class="controlLabel"><label
            class="label left checkbox2"
            id="_label_SYSTEM_EVENT"
            title="System Events"
            for="SYSTEM_EVENT">System Events:</label></span><span class="control"><input
            type="checkbox"
            name="user.notifications"
            value="SYSTEM_EVENT"
            id="SYSTEM_EVENT"
            class="checkbox2 aligncheckbox225"
            title="System Events"
            <s:if test="user.isNotificationSet('SYSTEM_EVENT')">checked="checked"</s:if> /></span></div>
        <div
            id="COTS_UPDATE.controlGroup"
            class="controlGroup"><span class="controlLabel"><label
            class="label left checkbox2"
            id="_label_COTS_UPDATE"
            title="COTS Updates"
            for="COTS_UPDATE">COTS Updates:</label></span><span class="control"><input
            type="checkbox"
            name="user.notifications"
            value="COTS_UPDATE"
            id="COTS_UPDATE"
            class="checkbox2 aligncheckbox225"
            title="COTS Updates" 
            <s:if test="user.isNotificationSet('COTS_UPDATE')">checked="checked"</s:if> /></span></div>
        </p>
        
        <peps:model ognlPrefix="user">
        <div
            id="preferenceGroup"
            class="controlGroup">
        <peps:dataField key="preference.group" />
        </div >
        </peps:model >
        
        </fieldset >
    </s:if > 
--%>

     <s:set
        scope="page"
        name="d1"
        value="%{getText('date.format.seven')}" />
    <s:set
        scope="page"
        name="d2"
        value="%{getText('date.format.eight')}" />
    <s:set
        scope="page"
        name="d3"
        value="%{getText('date.format.dmy')}" />
    <s:set
        scope="page"
        name="d4"
        value="%{getText('date.format.wmdy')}" />
    <s:set
        scope="page"
        name="t1"
        value="%{getText('time.format.std')}" />
    <s:set
        scope="page"
        name="t2"
        value="%{getText('time.format.mil')}" />
    <s:set
        scope="page"
        name="ds1"
        value="%{getText('drug.class.code')}" />
    <s:set
        scope="page"
        name="ds2"
        value="%{getText('drug.class.classification')}" /> 

<%-- <spring:message
        scope="page"
        var="d1"
        code="date.format.seven" />
    <spring:message
        scope="page"
        var="d2"
        code="date.format.eight" />
    <spring:message
        scope="page"
        var="d3"
        code="date.format.dmy" />
    <spring:message
        scope="page"
        var="d4"
        code="date.format.wmdy" />
    <spring:message
        scope="page"
        var="t1"
        code="time.format.std" />
    <spring:message
        scope="page"
        var="t2"
        code="time.format.mil" />
    <spring:message
        scope="page"
        var="ds1"
        code="drug.class.code" />
    <spring:message
        scope="page"
        var="ds2"
        code="drug.class.classification" /> --%>
        
    <fieldset><legend>Date/Time Format</legend>
    <div class="row">
    <div class="fourColumn">

    <s:radio
        key="date.format"
        list="#@java.util.HashMap@{'MDDYYYY':#attr.d1, 'MMDDYYYY':#attr.d2, 
                        'DAY_MONTH_YEAR':#attr.d3, 'WEEKDAY_MONTH_DAY_YEAR':#attr.d4}"
        name="user.sessionPreferences['LONG_DATE_FORMAT']"
        labelposition="top"
    />    
    </div>
    	<div class="fourColumn">
    <s:radio
        key="time.format"
        list="#@java.util.HashMap@{'STANDARD':#attr.t1, 'MILITARY':#attr.t2}"
        name="user.sessionPreferences['TIME_FORMAT']"
        labelposition="top" />
       </div>
    </div>
    </fieldset>
    <br>
    <fieldset><legend>Table Display Format</legend>
    <div class="column"><s:textfield
        key="user.sessionPreferences['TABLE_ROW_COUNT']"
        label="%{getLocalizedFieldDescription('table.row.count')}"
        labelposition="top"
        title="%{getLocalizedFieldDescription('table.row.count')}"
        size="15" />&nbsp;(10-100)</div>
     </fieldset>
     <!--  Please inspect the clear case history for the drug class sort preference area -->
    <br>
    <span class="column"><peps:submit
        key="button.undo"
        event="cancel"
        onclick="confirmUndo()" /></span>
    <span class="column"><peps:submit
        key="button.setPreferences"
        event="updatePreferences" /></span>
</peps:form>