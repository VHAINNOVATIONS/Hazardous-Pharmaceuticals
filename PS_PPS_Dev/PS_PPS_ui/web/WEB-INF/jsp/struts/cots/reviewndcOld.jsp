<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

	    
<div class="horizontalspacer"></div>


<peps:form id="performFDBSearchOption">

    <div class="panel" style="width: 600px"> 

    <s:select
            name="fdbSearchOption.fdbSearchOptionType"
            id="fdb.search.option.type"
            list="@gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType@values()"
            listValue="getText('FDBSearchOptionType.' + name())"
            labelposition="left"
            label="%{getLocalizedFieldDescription('fdb.search.option.type')}"
            title="%{getLocalizedFieldDescription('fdb.search.option.type')}"
            cssClass="comboBox"
            emptyOption="true"
            disabled="false" />
            
    <s:textfield
        key="fdbSearchOption.fdbSearchString"
        label="%{getLocalizedFieldName('fdb.search.string')}"
        labelposition="left"
        required="false"
        title="%{getLocalizedFieldDescription('fdb.search.string')}"
        size="19" />
    
    </div>

    <div class="horizontalspacer"></div>
    <div class="panel">
    <span class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <peps:submit
            key="button.performFDBSearchOption"
            event="performFDBSearchOption" />
    </span>
    </div> 

<br />
<s:if test="fdbSearchOptionResults.size()">
<br> <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<font size="4" color="red"><b>No Matching Results Found</b></font>
</s:if>
<s:if test="fdbSearchOptionResults.size() > 1">
        <table
            id="ndcReviewTable"
            frame="box"
            rules="cols"
            class="pepsTable">
<s:iterator value="fdbSearchOptionResults" status="details">
	<s:set
	   scope="page"
	   name="index"
	   value="#details.index" />

    <s:if test="#attr.index == 0">
        <thead>
        <tr>
            <th style="width: 72px">NDC</th>
            <th style="width: 40px;">Pkg Size</th>
            <th style="width: 150px">Pkg Type</th>
            <th style="width: 150px">Manufacture</th>
            <th style="width: 150px">Label Name 25</th>
            <th style="width: 150px">Add'l Description</th>
        </tr>
        </thead>
    </s:if>
    
    <s:if test="#attr.index > -1">  
    <tr>
        <td><a href="#" name="<s:property value='getNDC()' />" onclick="printNDCInfo(this.name); return <s:set name='testField' value='linkSearch' /> ;" ><s:property value="getNDC()" /></a></td>
        <td style="text-align: right;"><s:property value="getPackageSize()" /></td>    
        <td><s:property value="getPackageDescription()" /></td>
        <td><s:property value="getManufacturerDistributorName()" /></td>    
        <td><s:property value="getLabelName25()" /></td>    
        <td><s:property value="getAdditionalDescriptor()" /></td>  
    </tr>  
    </s:if>
	
</s:iterator>
    </table>
</s:if>

<s:if test="fdbSearchOptionResults.size() == 1">
    <s:iterator value="fdbSearchOptionResults" status="details">
        <s:set
            scope="page"
            name="index"
            value="#details.index" />

    <s:if test="#attr.index > -1">
        <table>
        <tr><td width="160">NDC: </td><td width="500"><s:property value="getNDC()" />&nbsp;&nbsp;<s:property value="getNDCFormatCode()" /></td><td width="160">Package Size: </td><td><s:property value="getPackageSize()" /></td></tr>
        <tr><td></td><td></td><td>Package Description: </td><td><s:property value="getPackageDescription()" />  </td></tr>
        <tr><td>Brand Name: </td><td><s:property value="getBrandName()" /></td><td>Additional Description: </td><td><s:property value="getAdditionalDescriptor()" /></td></tr>
        <tr><td>ManDistrib: </td><td><s:property value="getManufacturerDistributorName()" />  </td></tr>
        <tr><td>Label Name: </td><td><s:property value="getLabelName()" />  </td></tr>
        <tr><td>Label Name25: </td><td><s:property value="getLabelName25()" /> </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Generic Name: </td><td><s:property value="getGenericName()" />  </td></tr>
        <tr><td>Drug Strength Description: </td><td><s:property value="getDrugStrengthDescription()" /> </td></tr>
        <tr><td>Dose Form Description: </td><td><s:property value="getDosageFormDescription()" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Unit Dose Indicator: </td><td><s:property value="getUnitDoseIndicator()" /> </td><td>DEA Code: </td><td><s:property value="getDEACode()" /> </td></tr>
        <tr><td>GCN Seq No: </td><td><s:property value="getGCNSeqNo()" />  </td><td>OTC/RX Indicator: </td><td><s:property value="getOTCIndicator()" /> </td></tr>
        <tr><td>Drug Form Code: </td><td><s:property value="getDrugFormCode()" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Drug Class: </td><td width="500"><s:property value="getDrugClassification()" /> </td></tr>
        <tr><td>Duplicate Therapy Classes: </td><td><s:property value="getDuplicateTherapies()" /> </td></tr>
        <tr><td>Color: </td><td><s:property value="getColor()" /> </td></tr>
        <tr><td>Shape: </td><td><s:property value="getShape()" />  </td></tr>
        <tr><td>Flavor: </td><td><s:property value="getFlavor()" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Top 200 Rank: </td><td><s:property value="getTop200Rank()" />  </td></tr>
        <tr><td>Top 50 Gen Rank: </td><td><s:property value="getTop50GenRank()" />  </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
        <tr><td>Replacement NDC: </td><td><s:property value="getReplacementNDC()" />  </td></tr>
        <tr><td>Previous NDC: </td><td><s:property value="getPreviousNDC()" /> </td></tr>
        <tr><td>Obsolete Date: </td><td><s:property value="getObsoleteDate()" />  </td></tr>
        </table>
        <br />
        <button type="button" onclick="history.back()">Return to Search Results</button> 

    </s:if>
    </s:iterator>
</s:if>
</peps:form>
