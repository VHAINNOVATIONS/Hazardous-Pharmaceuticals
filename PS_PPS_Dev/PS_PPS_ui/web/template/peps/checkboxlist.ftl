<#include "/${parameters.templateDir}/peps/controlheader.ftl" />
<#assign itemCount = 0/>
<#if parameters.list?exists>
    <@s.iterator value="parameters.list" status="checkboxListStatus">
        <#assign itemCount = itemCount + 1/>
        <#if parameters.listKey?exists>
            <#assign itemKey = stack.findValue(parameters.listKey)/>
        <#else>
            <#assign itemKey = stack.findValue('top')/>
        </#if>
        <#if parameters.listValue?exists>
            <#assign itemValue = stack.findString(parameters.listValue)/>
        <#else>
            <#assign itemValue = stack.findString('top')/>
        </#if>
<#assign itemKeyStr=itemKey.toString() />
<#if itemCount == 6>
</td><td width="100px" height="50px">
</#if>
<input type="checkbox" name="${parameters.name?html}" value="${itemKeyStr?html}" id="${parameters.name?html}-${itemCount}"<#rt/>
        <#if tag.contains(parameters.nameValue, itemKey)>
 checked="checked"<#rt/>
        </#if>
        <#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
        </#if>
        <#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
        </#if>
        <#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
        <#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
/>
<#if itemValue == "IV" || itemValue == "OTC">
<label for="${parameters.name?html}-${itemCount}" class="label">${itemValue?html}</label>
<#else>
<label for="${parameters.name?html}-${itemCount}" class="label">${itemValue?capitalize?html}</label>
</#if>
<@s.if test="!#checkboxListStatus.last"><br/></@s.if><#rt/>
    </@s.iterator>
</#if>
<#include "/${parameters.templateDir}/peps/controlfooter.ftl" />
<#-- Correct https://issues.apache.org/struts/browse/WW-2802 -->
<input type="hidden" id="__multiselect_${parameters.id?html}" name="__multiselect_${parameters.name?html}" value=""<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
 /> 