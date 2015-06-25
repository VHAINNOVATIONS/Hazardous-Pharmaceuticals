<#include "/${parameters.templateDir}/peps/controlheader.ftl" />
<#if parameters.flat?if_exists == 'true'>
<div style="float: right; margin-left: 1em; margin-bottom: 1em;" id="calendar-container"></div>
<script type="text/javascript">
  Calendar.setup(
    {
      showsTime      :    ${parameters.showstime?default("false")},
      <#if parameters.format?exists>
      ifFormat       :    "${parameters.format}",
      </#if>
      flat         : "calendar-container",  // ID of the parent element
      flatCallback : ${parameters.flatCallback?default("dateChanged")},  // our callback function
      timeFormat  : "12"
    }
  );
</script>
<#else>
    <#include "/${parameters.templateDir}/simple/text.ftl" />
    <#if !parameters.readonly?exists><a href="#" id="${parameters.id}_button"></#if>
        <img src="<@s.url value="/struts/jscalendar/img.gif" encode='false' includeParams='none'/>" width="16" height="16" border="0" alt="Click Here to Pick up the date"
        <#if parameters.cssStyle?exists>
            style="${parameters.cssStyle?html}"
        </#if>
        <#if parameters.cssClass?exists>
            class="${parameters.cssClass?html}"
        </#if>
        >
    <#if !parameters.readonly?exists></a></#if>
    <#if !parameters.readonly?exists>
    <script type="text/javascript">
        Calendar.setup({
            inputField     :    "${parameters.id}",
            button         :    "${parameters.id}_button",
        <#if parameters.singleclick?exists>
            singleClick    :    ${parameters.singleclick},
        </#if>
    </#if>
            showsTime      :    ${parameters.showstime?default("false")},
    <#if parameters.format?exists>
            ifFormat       :    "${parameters.format}",
    </#if>
            step           :    1,
            timeFormat  : "12"
        });
    </script>
</#if>
<#include "/${parameters.templateDir}/peps/controlfooter.ftl" />