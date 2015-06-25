<%@tag import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@tag import="java.util.Date"%>
<%@tag import="gov.va.med.pharmacy.peps.common.utility.DateFormatUtility"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.Selectable"%>
<%@tag import="java.util.Collection"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ValueObject"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.NdcVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ProductVo"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.ManagedDataVo"%>
<%@tag import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.UserVo"%>
<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="java.text.SimpleDateFormat"%>
<%@tag import="java.sql.Timestamp"%>
<%@tag import="java.util.Calendar"%>
<%@tag import="gov.va.med.pharmacy.peps.common.vo.datafield.DataField"%>
<%@ tag
    language="java"
    body-content="empty"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ attribute
    name="item"
    required="true"
    type="java.lang.Object"
    rtexprvalue="true"
    description="The object containing the data to display"%>
<%@ attribute
    name="id"
    required="false"
    type="java.lang.String"
    description="HTML id"%>    
<%@ attribute
    name="key"
    required="true"
    type="java.lang.String"
    description="String form of DataFieldKey the field to be displayed"%>
<%@ attribute
    name="label"
    required="false"
    type="java.lang.Boolean"
    description="Boolean true if the data field value should be displayed with a label"%>
<%@ attribute
    name="cssClass"
    required="false"
    type="java.lang.String"
    description="CSS class HTML attribute added to default PEPS classes passed on to Struts tags for the label."%>
<%@ attribute
    name="cssStyle"
    required="false"
    type="java.lang.String"
    description="CSS style HTML attribute passed on to Struts tags for the label."%>
<%@ attribute
    name="dataCssClass"
    required="false"
    type="java.lang.String"
    description="CSS class HTML attribute added to default PEPS classes passed on to Struts tags for the data."%>
<%@ attribute
    name="entityType"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.EntityType"
    description="Optional EntityType for which to localize"%>
<%@ attribute
    name="abbreviation"
    required="false"
    type="java.lang.Boolean"
    description="True/false if the label should display the localized abbreviation or the localized name. Default is false (to display the localized name)."%>
<%@ attribute
    name="inputType"
    required="false"
    type="java.lang.String"    
    description="The type of the input (e.g. textfield, label, checkbox)"%>
<%@ attribute
    name="value"
    required="false"
    type="java.lang.String"    
    description="The value to display in the textfield"%>
<%

            gov.va.med.pharmacy.peps.common.vo.FieldKey fieldKey = gov.va.med.pharmacy.peps.common.vo.FieldKey.getKey(key);

			Class fieldClass = fieldKey.getFieldClass();

            gov.va.med.pharmacy.peps.common.vo.EntityType itemType = gov.va.med.pharmacy.peps.common.vo.EntityType.toEntityType(fieldClass);
            
//             if (fieldKey.getKey().contains("cmop")) {
//                 System.out.println(jspContext.getAttribute("value"));
//             }            

            // If the fieldClass didn't translate into an EntityType, try getting it from the action directly
            if (itemType == null && item != null && item instanceof ManagedItemVo) {
            	 itemType = ((ManagedItemVo)item).getEntityType();
            }

            jspContext.setAttribute("itemType", itemType);           
            
            Object dataField = item;
            
            String tooltip = gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedDescription(key, request.getLocale(),
                itemType);
            jspContext.setAttribute("tooltip", tooltip);

            if (label == null) {
                label = Boolean.FALSE;
            }

            if (label) {
                String labelText;
                if (abbreviation != null && abbreviation) {
                    labelText = gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedAbbreviation(key, request.getLocale(), itemType);
                }
                else {
                    labelText = gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedName(key, request.getLocale(),
                        itemType);
                }
                jspContext.setAttribute("labelText", labelText);

%>
<peps:controlheader 
    id="${key}"    
    label="${labelText}"
    title="${tooltip}"
    labelcssClass="${cssClass}" 
    labelcssStyle="${cssStyle}"/>
<%
                if (dataCssClass != null) {
%>
                    <span class="${dataCssClass}">
<%
                }
            }
			String emptyString = new String();
            if (dataField == null) {            	
%><%=emptyString%>
<%
           }
           else if (value != null) {                
%><%=value%>
<%
            }
            else {

                if (dataField instanceof gov.va.med.pharmacy.peps.common.vo.ValueObject) {
                    
                    Object fieldValue = gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getFieldValue(dataField, fieldKey);
					
					// If this object is an NDC a lot of the info displayed comes from its ProductVo, so search for it there
					if (dataField instanceof NdcVo && fieldValue == null)
						fieldValue = JspTagUtility.getFieldValue(((NdcVo)dataField).getProduct(), fieldKey);
					
					String value = fieldValue != null ? fieldValue.toString() : "";
				    
					
					if(dataField instanceof ProductVo && fieldKey.getKey().equals(FieldKey.OI_NAME.getKey())){                           
                        value = ((ProductVo)dataField).getOrderableItem() != null ? ((ProductVo)dataField).getOrderableItem().getOiName() : "";
                    }
					
					if(fieldValue instanceof gov.va.med.pharmacy.peps.common.vo.datafield.DataField){						
						if(fieldValue != null)
							
							if(((DataField)fieldValue).getValue() != null){
								value = ((DataField)fieldValue).toShortString();
							}else{
								value = "";
							}
						// If this object is an NDC a lot of the info displayed comes from its ProductVo, so search for it there
						else if(fieldValue == null && dataField instanceof NdcVo)
							value = ((NdcVo)dataField).getProduct().getVaDataFields().getDataField(fieldKey).getValue().toString();						
					}
					else if(fieldValue instanceof Selectable)
					     value = ((Selectable)fieldValue).getValue();
					
					else if(fieldValue instanceof Enum)
					    value = gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getText(request, (Enum)fieldValue);
					
					else if(fieldValue instanceof UserVo)
					{  
					    if(fieldKey.equals(FieldKey.REVIEWED_BY))
					        value = ((UserVo)fieldValue).getLocation()+":"+((UserVo)fieldValue).getUsername();
				        else
					        value = ((UserVo)fieldValue).getLastName()+", "+((UserVo)fieldValue).getFirstName();    
					}
					 
					if(value != null && value.startsWith("[") && value.endsWith("]"))
                        value = value.substring(1, value.length()-1);                    
                    
                    if (value != null && value.trim().length() > 0) {                    
                        if (fieldKey.getFieldClass().equals(java.util.Date.class)) {  
                        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");                        	
                        	
                        	long dv;
                        	java.util.Date dateValue = new Date();
                        	
                        	// The dates are coming back in two different formats, this is a hack to display them correctly
                        	try {
                        	    dv = Date.parse(value);
                        	    dateValue.setTime(dv);
                        	} catch (Exception e){
                        	    dateValue = sdf.parse(value);
                        	}
                        	
                            
                        
                        UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
                        String dateFormat = userContext.getUser().getDateTimeFormatPreference();
    %><%=DateFormatUtility.format(dateValue, dateFormat, request
                                .getLocale())%>
    <%           
                        }
                        else if(fieldValue instanceof java.util.Collection ){
                        	boolean first = true;
                        	
                        	// For grouped fields add list tag so that each item is displayed on its own line
                        	if (fieldKey == FieldKey.DRUG_CLASSES
                        			|| fieldKey == FieldKey.ACTIVE_INGREDIENT) {
                        		%><%= "<ul style='list-style: none; margin-left:0px; display: inline-block; padding: 0px;'>" %><%
                        	}
                        	
                            for(Object fV : (Collection)fieldValue) {
                        		if(fV instanceof ValueObject) {%>
                        			<%=((ValueObject)fV).toShortString()%>
                        		
                        	<%}
                        		else {if(!first){%>, <%}%><%=JspTagUtility.getText(request, fieldKey, fV.toString())%><%}
                        		first = false;
                        	}
                        	%><%= "</ul>" %><%
                            
                        	
                        }                        
                        else {                  
%><%=JspTagUtility.getText(request, value)%>
<%
                        }
                    }
                    else {
%><%=emptyString%>
<%
                    }
                } 
                else if (fieldClass.isEnum()) {
                    //System.out.println("fieldClass is Enum");
                    if (dataField instanceof Collection) {
                        Collection collection = (Collection) dataField;

                        if (collection.isEmpty()) {
%><%=emptyString%>
<%
                        }

                        for (int i = 0; i < collection.size(); i++) {

                            if (i != 0) {
%>,&nbsp;<%
                            }
                            //System.out.println("Getting enum value");
                            Enum enumeration = (Enum)collection.toArray()[i];
%><%=gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getText(request, enumeration)%>
<%
                    	}
                    }
                    else {
%><%=dataField.toString()%>
<%
                	}
                } 
                else if (dataField instanceof java.util.Collection) {

                    if (((java.util.Collection) dataField).isEmpty()) {
%><%=emptyString%>
<%
                    }

                    for (Object field : (java.util.Collection<Object>) dataField) {
                        if (field instanceof gov.va.med.pharmacy.peps.common.vo.ValueObject) {
                        	//System.out.println("Displaying VO String");
%><%=((gov.va.med.pharmacy.peps.common.vo.ValueObject) field).toShortString()%>
<%
                        }
                        else {
%><%=field.toString()%>
<%
                    	}
                    }
                }               
                else if (java.util.Date.class.isAssignableFrom(fieldClass)) {
                    java.util.Date dateValue = (java.util.Date) gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility
                        .getFieldValue(dataField, fieldKey);
                    
                    UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
                    String dateFormat = userContext.getUser().getDateTimeFormatPreference();
%><%=gov.va.med.pharmacy.peps.common.utility.DateFormatUtility.format(dateValue, dateFormat, request
                            .getLocale())%>
<%
                }
                else if (Boolean.class.isAssignableFrom(fieldClass)) {
%><%=gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility.getText(request, dataField
                            .toString())%>
<%				
                }
                else if (Double.class.isAssignableFrom(fieldClass) || Float.class.isAssignableFrom(fieldClass)) {
%><%=gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility.format((Number) dataField)%><%                    
			
                }
                else if(dataField instanceof String)
                {
                    %><%=dataField%><%                    
                }
                else { // just display its value

%><%=JspTagUtility.getText(request, dataField.toString())%>
<%
				
                }
            } 
            if (dataCssClass != null) {
%>
                </span>
<%
            }
            if (label) {
%>
<peps:controlfooter 
    id="${key}"    
    label="${labelText}"
    title="${tooltip}"
    labelcssClass="${cssClass}" 
    labelcssStyle="${cssStyle}"/>
<%
			}
            
%>
