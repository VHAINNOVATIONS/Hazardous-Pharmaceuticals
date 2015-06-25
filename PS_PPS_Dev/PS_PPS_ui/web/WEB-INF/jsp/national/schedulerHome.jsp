<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib
    prefix="fmt" 
    uri="http://java.sun.com/jsp/jstl/fmt" %> 

<script src="/PRE/javascript/fdbScheduler.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/fdbupdate.css" />

<script type="text/javascript">

    var messagingRunning = "<c:out value="${schedulerState.messagingRunning}"/>";
    
</script>
   
<div class="horizontalspacer"></div>
   <table border="1" frame="box" rules="none" style="width:1100px; margin:0px 0px 0px 3px; border:1px solid black;background-color:lightgrey;"  >
	  <tr>
          <td colspan="1">
                <c:if test="${schedAccess == true}">
                    <input type="button" value="Start" onclick="schedulerCommand('START');"/>
                    <input type="button" value="Stop" onclick="schedulerCommand('STOP');"/>
                    <br> 
                </c:if> 
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
           <td colspan="1" align="center">
             <h2>Fdb Control Process</h2>
          </td>
          <td colspan="1" align="right">
              <input id="refresh" type="button" value="Refresh" name="refresh" onclick="refreshScreen();"/>
          </td>
     </tr>
     <tr>
		<td colspan="3">
		  <strong>  System Time:</strong> <label style="color:Navy"> <fmt:formatDate type="both" dateStyle="short" pattern="MMM-dd-yyyy HH:mm:ss" value="${schedulerState.serverCurrentTime}" /></label> 
		</td>              
     </tr>
     <tr>
        <td colspan="3">
            <strong>Scheduler Status: </strong><label style="color:Navy"><c:out value="${schedulerState.schedulerStatus}"/> </label>
        </td>
     </tr>
	  <tr>
           <td colspan="5"> 
                 <c:out value="${schedulerState.stateResponseMessage.responseMessage}"/>
           </td>
      </tr>
  </table>
 
  <table frame="box" rules="cols" style="width:1100px;margin:3px 0px 0px 3px;   border-collapse: collapse; table-layout: auto; ">
    <tr>
	    <td width="100%">
	       <table  rules="cols" >
	        <thead>
               <tr>
                   <th width="6%" align="center" style="background-color:#B0B0B0;"><strong>Jobs</strong></th>
                   <th width="10%" align="center" style="background-color:#B0B0B0;"><strong>Controls</strong></th>
                   <th width="3%" align="center" style="background-color:#B0B0B0;"><strong>Hrs</strong></th>
                   <th width="3%" align="center" style="background-color:#B0B0B0;"><strong>Mins</strong></th>
                   <th width="4%" align="center" style="background-color:#B0B0B0;"><strong>Job Status</strong></th>
                   <th width="8%" align="center" style="background-color:#B0B0B0;"><strong>Next Fire Time</strong></th>
                   <th width="8%" align="center" style="background-color:#B0B0B0;"><strong>Last Success Run</strong></th>
                   <th width="8%" align="center" style="background-color:#B0B0B0;"><strong>Process Status</strong></th>
               </tr>
        </thead>
	            <tr>
	                <td>  <!--  need to sort job names -->
				          <c:forEach var="jobMap" items="${schedulerState.jobStatusInfoMap}"  varStatus="status" >
				                <c:choose>
				                    <c:when test="${jobMap.value.fdbJobNames.jobName == ''}">
				                    </c:when>
				                    <c:otherwise>
				                        <c:choose>
				                            <c:when test="${(jobMap.value.fdbJobNames.jobDisplayName == 'FSS MIGRATION' ||  jobMap.value.fdbJobNames.jobDisplayName == 'FDB MIGRATION') && role == false}">
                                            </c:when>
                                            <c:otherwise>
									            <tr class="${status.index % 2 == 1 ? 'even' : 'odd'}">
									              <!-- Jobs names -->
									              <td width="6%">
									                    <label style="color:Navy"><c:out value="${jobMap.value.fdbJobNames.jobDisplayName}"/> </label>
									              </td>
									              <!-- controls -->
									              <td width="10%">
									                  <c:choose>
		                                                    <c:when test="${jobMap.value.status == 'RUNNING'}">
		                                                        <input class="ignoreEnable" disabled="disabled" id="startButton${status.count}" type="button" value="Resume" onclick="scheduleJob('<c:out value="${status.count}"/>', 'RESUME', '<c:out value="${jobMap.value.fdbJobNames}"/>');"/>
		                                                    </c:when>
		                                                    <c:otherwise>
		                                                        <input id="startButton${status.count}" type="button" value="Resume" onclick="scheduleJob('<c:out value="${status.count}"/>', 'RESUME', '<c:out value="${jobMap.value.fdbJobNames}"/>');"/>
		                                                    </c:otherwise>
		                                              </c:choose>
		                                              <c:choose>
		                                                    <c:when test="${jobMap.value.status == 'PAUSED'}">
		                                                        <input class="ignoreEnable" disabled="disabled" id="stopButton${status.count}" type="button" value="Pause" onclick="scheduleJob('<c:out value="${status.count}"/>', 'PAUSE', '<c:out value="${jobMap.value.fdbJobNames}"/>');"/>
		                                                    </c:when>
		                                                    <c:otherwise>
		                                                         <input id="stopButton${status.count}" type="button" value="Pause" onclick="scheduleJob('<c:out value="${status.count}"/>', 'PAUSE', '<c:out value="${jobMap.value.fdbJobNames}"/>');"/>
		                                                    </c:otherwise>
		                                              </c:choose>
		                                               <c:choose>
		                                                   <c:when test="${jobMap.value.status == 'PAUSED'}">
		                                                       <input id="scheduleButton${status.count}" type="button" value="Schedule" onclick="scheduleJob('<c:out value="${status.count}"/>', 'SCHEDULE', '<c:out value="${jobMap.value.fdbJobNames}"/>');"/>
		                                                   </c:when>
		                                                   <c:otherwise>
		                                                         <input class="ignoreEnable" disabled="disabled" id="scheduleButton${status.count}" type="button" value="Schedule" onclick="scheduleJob('<c:out value="${status.count}"/>', 'SCHEDULE', '<c:out value="${jobMap.value.fdbJobNames}"/>');"/>
		                                                   </c:otherwise>
		                                              </c:choose>
									              </td>
									              <!--  hour -->
									              <td width="3%">
									                <c:choose>
		                                                   <c:when test="${jobMap.value.status == 'PAUSED'}">
		                                                         <select  id="hour${status.count}">
		                                                            <c:forEach var="hrs" items="${schedulerState.jobStartTimeData.hours}">
		                                                                <c:choose>
		                                                                    <c:when test="${hrs == jobMap.value.hour}">
		                                                                        <option selected value="${hrs}">${hrs}</option>                                                                
		                                                                    </c:when>
		                                                                    <c:otherwise>
		                                                                        <option value="${hrs}">${hrs}</option>
		                                                                    </c:otherwise>
		                                                                 </c:choose>
		                                                            </c:forEach>
		                                                        </select>
		                                                   </c:when>
		                                                   <c:otherwise>
		                                                         <select disabled id="hour${status.count}">
		                                                            <c:forEach var="hrs" items="${schedulerState.jobStartTimeData.hours}">
			                                                            <c:choose>
		                                                                    <c:when test="${hrs == jobMap.value.hour}">
		                                                                        <option selected value="${hrs}">${hrs}</option>                                                                
		                                                                    </c:when>
		                                                                    <c:otherwise>
		                                                                        <option value="${hrs}">${hrs}</option>
		                                                                    </c:otherwise>
		                                                                 </c:choose>
			                                                        </c:forEach>
		                                                        </select>
		                                                   </c:otherwise>
		                                              </c:choose>
									              </td>
									               <!-- mins -->
								                  <td  width="3%">
								                    <c:choose>
		                                                   <c:when test="${jobMap.value.status == 'PAUSED'}">
		                                                       <select id="mins${status.count}">
		                                                           <c:forEach var="mins" items="${schedulerState.jobStartTimeData.mins}">
			                                                             <c:choose>
			                                                                <c:when test="${mins == jobMap.value.mins}">
			                                                                    <option selected value="${mins}">${mins}</option>                                                                
			                                                                </c:when>
			                                                                <c:otherwise>
			                                                                    <option value="${mins}">${mins}</option>
			                                                                </c:otherwise>
			                                                             </c:choose>
		                                                           </c:forEach>
		                                                       </select>
		                                                   </c:when>
		                                                   <c:otherwise>
		                                                         <select disabled id="mins${status.count}">
		                                                            <c:forEach var="mins" items="${schedulerState.jobStartTimeData.mins}">
				                                                           <c:choose>
				                                                                <c:when test="${mins == jobMap.value.mins}">
				                                                                    <option selected value="${mins}">${mins}</option>                                                                
				                                                                </c:when>
				                                                                <c:otherwise>
				                                                                    <option value="${mins}">${mins}</option>
				                                                                </c:otherwise>
				                                                           </c:choose>
		                                                            </c:forEach>
		                                                       </select>
		                                                   </c:otherwise>
		                                              </c:choose>
									               </td>
									              <!-- Job status -->
									              <td width="4%" align="center">
									                 <c:choose>
		                                                    <c:when test="${jobMap.value.status == 'PAUSED'}">
		                                                        <label style="color:Red; font:bold;"> <c:out  value="${jobMap.value.status}"/> </label>
		                                                    </c:when>
		                                                    <c:otherwise>
		                                                         <label style="color:Green; font:bold;"> <c:out  value="${jobMap.value.status}"/> </label>
		                                                    </c:otherwise>
		                                              
		                                              </c:choose>
									              </td>
									              <!-- next fire time-->
									               <td width="8%" align="center">
		                                              <label style="color:Black" > <fmt:formatDate type="both" dateStyle="short" pattern="MMM-dd-yyyy HH:mm:ss" value="${jobMap.value.nextFireTime}" /> </label>
		                                          </td>
		                                          <!-- last success run date-->
		                                          <td width="8%" align="center">
		                                              <label style="color:Black" ><fmt:formatDate type="both" dateStyle="short" pattern="MMM-dd-yyyy HH:mm:ss" value="${jobMap.value.lastSuccessRunDate}" /> </label>
		                                          </td>
		                                          <!-- Process Run Status -->
		                                          <td width="8%" align="center">
		                                              <c:choose>
		                                                    <c:when test="${jobMap.value.processStatus == 'COMPLETED'}">
		                                                        <label style="color:Red; font:bold;" > <c:out  value="${jobMap.value.processStatus}"/> </label>
		                                                    </c:when>
		                                                    <c:otherwise>
		                                                        <label style="color:Green; font:bold;"> <c:out  value="${jobMap.value.processStatus}"/> </label>
		                                                    </c:otherwise>
		                                              
		                                              </c:choose>
		                                             
		                                          </td>
									          </tr>
							               </c:otherwise>
                                       </c:choose>
                                    </c:otherwise>
                                </c:choose>
				         </c:forEach>
	                </td>
	            </tr>
	        </table>
	     </td>
     </tr>
   </table> 
   <table width="100%" frame="box" rules="all" style="width:1100px; margin:0px 0px 0px 3px;">
            
            <thead>
                <tr>
                    <th  width="25%" align="center" style="background-color:#B0B0B0;">
                        Messaging Status                        
                    </th>
                    <th width="25%" align="center" style="background-color:#B0B0B0;">
                        FDA Host Name                       
                    </th>
                    <th width="15%" align="center" style="background-color:#B0B0B0;">
                        Messages on Queue                        
                    </th>
                    <th  width="15%" align="center" style="background-color:#B0B0B0;">
                        Messaging Running                        
                    </th>
                    <th width="45%" align="center" style="background-color:#B0B0B0;">
                        Error Messages                        
                    </th>
                     
                   
                    
                </tr>
            </thead>
            
            <tr>
             <td align="center">
                   <c:choose>
                        <c:when test="${schedulerState.messagingRunning == true}">
                            <input class="ignoreEnable" disabled="disabled" id="onButton" type="button" value="On" />
                            <input id="offButton" type="button" value="Off" onclick="toggleMessaging('OFF');"/>
                        </c:when>
                        <c:otherwise>
                            <input  id="onButton" type="button" value="On" onclick="toggleMessaging('ON');"/>
                            <input class="ignoreEnable" disabled="disabled" id="offButton" type="button" value="Off" onclick="toggleMessaging('OFF');"/>
                        </c:otherwise>
                   </c:choose>
               </td>
                <td align="center">
                       <input id="fdaHostName" type="text" " name="fdaHostName" value="${schedulerState.fdaHostName}"/>
                       <input id="hostNameButton" type="button" value="Update" onclick="updateHostName();"/> 
                 </td>
               <td align="center" >
                   <strong><label style="color:Black;"><c:out  value="${schedulerState.messagesOnQueueCount}"/> </label></strong>
               </td>
               <td  align="center">
                   <strong><label style="color:Black;"><c:out  value="${schedulerState.messagingRunning}"/> </label></strong>
               </td>
               <td align="center">
                   <strong><label style="color:Black;"><c:out  value="${schedulerState.errorMessage}"/> </label></strong>
               </td>
               
              
            </tr>
            <hr>
          
</table>
