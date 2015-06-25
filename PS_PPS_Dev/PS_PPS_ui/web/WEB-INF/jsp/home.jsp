<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="gov.va.med.pharmacy.peps.presentation.common.controller.SystemSettingsController.ImageSelector" %>
<div class="panel">
	<peps:form id="home.go">
		<table class="background3" width="1250" border="0" cellspacing="10"
			cellpadding="10">
			<tr valign="top">
				<!--PBM OFFICE MESSAGE-->
				<td valign="top" width="180">
                    <c:choose>
                    
                        <c:when test="${ (not empty announcement.imagePath) and ('images/VeteransPoster.jpg' ne announcement.imagePath )}"><img class="news-img" src="${ announcement.imagePath }.go" alt="Current Custom Homepage Image" /></c:when>
                        <c:otherwise><img class="news-img" src="images/VeteransPoster.jpg" alt="Honoring Those Who Served" width="180" /></c:otherwise>
                    </c:choose>                            
				</td>
				<td valign="top" width="320">
					<div class="office-title">
						<spring:message code="home.pbmoffice.label" />
					</div>
					<p>
                        <strong>
                            <c:choose>
                                <c:when test="${ not empty announcement.title }"> ${ announcement.title }</c:when>
                                <c:otherwise>Don't Return Controlled Substance</c:otherwise>
                            </c:choose> 
                        </strong> <br />
                        <c:choose>
                            <c:when test="${ not empty announcement.body }"> ${ announcement.body }</c:when>
                            <c:otherwise>
                                No provisions in Controlled Substances Act or Code of Federal Regulations for pharmacy take 
                                back of controlled substances from patients. Pharmacists should be aware that no provisions
                                exist in the Controlled Substances Act or Code of Federal Regulations (CFR) for a DEA 
                                registrant, such as a community pharmacy, to take back controlled substances from a 
                                non-registrant (i.e., individual patient). However, patients may return an unused controlled
        						substance medication to the pharmacy in the event that the controlled substance is recalled
                                or a dispensing error has occurred, according to... See more below.</c:otherwise>
                        </c:choose>
                        <br />
                    </p>
                    <p>
                        <c:set scope="page" value="Full Story..." var="linkTitle" />
                        <c:if test="${ not empty (announcement.linkTitle) }">
                            <c:set scope="page" value="${ announcement.linkTitle }" var="linkTitle" />
                        </c:if>
                        <c:choose>
                            <c:when test="${ not empty (announcement.link) }"><a href="${ announcement.link }" title="More">${ linkTitle }</a></c:when>
                            <c:otherwise><a href="#" title="More">${ linkTitle }</a></c:otherwise>
                        </c:choose>
                    </p>
                    </td>

				<td valign="top" align="center" width="500">
					<table style="height: 100%">
						<tr>
							<!--SEARCH-->
							<td>
								<!-- <input type="hidden" name="_flowExecutionId" value="${flowExecutionKey}" />-->
								<a href="searchItems.go" title="Perform Simple Search">
									Perform Simple Search</a>
							</td>
						</tr>
						<!--QUEUE STATUS-->
						<tr height="100" valign="middle">
							<td><c:if test="${not (messagingStatus) }">
									<a style="color: red; font-size: medium;"> Messaging is
										currently off because a user turned it off.<br></a>
										<a> There are ${ inQueue } items in
											the queue.</a>
								</c:if>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
		<!--HORIZONTAL DIVIDING LINE-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr bgcolor="#000066">
				<td>&nbsp;</td>
			</tr>
		</table>
		<table class="background3" width="1250" border="1">
			<tr valign="top">
				<!--    PARTIAL SAVES     -->
				<td>
					<table width="94%" border="0">
						<tr valign="top">
							<td><spring:message code="partial.save.table.title"
									var="title" /> <a href="managePartialItem.go" title="${title}">${title}:</a>
								<c:set value="${partialItemsCount}" var="itemCount" scope="page"></c:set>
								<c:choose>
									<c:when test="${partialItemsCount == 1}">
										<spring:message code="partial.save.one.item" />
									</c:when>
									<c:when
										test="${partialItemsCount  > 1 && partialItemsCount <= 5}">
										<spring:message code="partial.save.count.${partialItemsCount}" />
										<spring:message code="partial.save.items.found" />
									</c:when>
									<c:when
										test="${partialItemsCount > 5 && partialItemsCount <= 20}">
										<spring:message code="partial.save.count.${partialItemsCount}" />
										<spring:message code="partial.save.items.found" />
										<spring:message code="partial.save.recent.five" />
									</c:when>
									<c:when test="${partialItemsCount > 20}">
										<spring:message code="partial.save.more.than.twenty" />
										<spring:message code="partial.save.recent.five" />
									</c:when>
									<c:otherwise>
										<spring:message code="partial.save.no.items" />
									</c:otherwise>
								</c:choose></td>
						</tr>

						<tr>
							<td><peps:table tableId="partialSaveTable"
									dataModel="${partialItems}" printTemplate="${printTemplate}"
									multiselectButtonKey="button.delete"
									multiselectButtonAction="delete" paged="false" />
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			summary="Standard VA Web Links">
			<tbody>
				<tr>
					<td id="nav-footer" align="center"><br /> <a
						href="http://www.va.gov/" title="VA Home">VA Home</a>&nbsp;| <a
						href="http://www.va.gov/privacy/" title="Privacy">Privacy</a>&nbsp;|
						<a href="http://www.foia.va.gov/" title="FOIA">FOIA</a>&nbsp;| <a
						href="http://www.va.gov/orpm/" title="Regulations">Regulations</a>&nbsp;|
						<a href="http://www.va.gov/webpolicylinks.asp"
						title="Web Policies">Web Policies</a>&nbsp;| <a
						href="http://www.va.gov/about_va/va_notices.asp" title="Notices">Notices</a>&nbsp;|
						<a href="http://www.va.gov/orm/NOFEAR_Select.asp"
						title="No FEAR Act">No FEAR Act</a>&nbsp;| <a
						href="http://www.va.gov/general/site_map.htm" title="Site Index">Site
							Index</a>&nbsp;| <a href="ext_redirect.asp?url=http://www.usa.gov/"
						title="USA.gov">USA.gov</a>&nbsp;| <a
						href="ext_redirect.asp?url=http://www.whitehouse.gov/"
						title="White House">White House</a>&nbsp;| <a
						href="ext_redirect.asp?url=http://www.nationalresourcedirectory.gov/"
						title="National Resource Directory">National Resource
							Directory</a>&nbsp;| <a href="http://www.va.gov/oig/"
						title="Inspector General">Inspector General</a></td>
				</tr>
				<tr>
					<td class="style1" id="nav-review" align="center"><br />
						<!-- Reviewed/Updated Date: December 13, 2011<br> -->
						<label style="text-align: center; font-weight: bold;">Version:</label> ${buildInfo.buildVersion}<br>
                        <label style="text-align: center; font-weight: bold;">Build Date:</label> ${buildInfo.buildDate} <br>
				    </td>
				</tr>
			</tbody>
		</table>
	</peps:form>
</div>
<!--end <div class="body-content"> -->
