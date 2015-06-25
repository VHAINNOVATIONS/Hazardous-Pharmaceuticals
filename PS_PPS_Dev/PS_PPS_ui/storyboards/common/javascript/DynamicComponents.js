var criteria = 1;
var bGroup = 0;


function createCriteria() {

    criteria++;
    var x = criteria;

    var criteriaPanel = document.getElementById("searchPanelContent");
    var buttonPanel = document.getElementById("endOfCriteria");
    var criteriaDiv = document.createElement("div");
    var criteriaCheckbox = document.createElement("div");
    var criteriaC1 = document.createElement("div"); 
    var criteriaC2 = document.createElement("div");
    var criteriaC3 = document.createElement("div");
    var criteriaC4 = document.createElement("div");

    criteriaDiv.className = "searchCriteria";
    criteriaDiv.id = "searchCriteriaFields"+x;
    criteriaCheckbox.className = "criteriaSpacer";
    criteriaC1.className = "criteriaColumn1";
    criteriaC1.id = "c"+x+"c1";
    criteriaC2.className = "criteriaColumn2";
    criteriaC2.id = "c"+x+"c2";
    criteriaC3.className = "criteriaColumn3";
    criteriaC4.className = "criteriaColumn4";

    criteriaCheckbox.innerHTML = "<input type='checkbox'/>";

    criteriaC1.innerHTML = "<label>Select field:<select id='criteria"+x+"_field' onChange='setComponent(document.getElementById('criteria"+x+"_field').value, 'c"+x+"c2')'><option selected='yes'>enter field</option><option>All Text Fields</option><option>Generic Name</option><option>Local Use</option><option>Dosage Form</option><option>Product Status</option><option>Do Not Handle if Pregnant</option><option>Do Not Mail</option><option>Witness to administration</option></select></label>";
    
    criteriaC2.innerHTML = criteriaC2.id;
    criteriaC3.innerHTML = "";
    criteriaC4.innerHTML = "";

    criteriaDiv.appendChild(criteriaCheckbox);
    criteriaDiv.appendChild(criteriaC1);
    criteriaDiv.appendChild(criteriaC2);
    criteriaDiv.appendChild(criteriaC3);
    criteriaDiv.appendChild(criteriaC4);

    criteriaPanel.insertBefore(criteriaDiv, buttonPanel);


}


function whatever() {

    var label = document.createElement("label");
    var labelText = document.createTextNode("Select field:");
    var select = document.createElement("select");
    select.id = "criteria" + x +"_field";
    select.onClick = "";
    
    var opt1 = document.createElement("option");
    opt1.text = 'enter field';
    select.add(opt1, null);
    var opt2 = document.createElement("option");
    opt2.text = 'Generic Name';
    select.add(opt2, opt1);
 
    label.append(labelText);
    label.append(select);

    return label;   

}


function nextComponent(element) {
    var div = document.getElementById(element);

    switch(div.value) {
        case "Drug Class":
		case "Manufacturer":		div.appendChild(createConstraintComponent());
									div.appendChild(createTextField());
									break;									
		case "Generic Name":			putTextComponents(div);
						break;
		case "Local Use":			putBooleanComponents(div);
						break;
		case "Product Status":			div.innerHTML = putProductStatusComponent();
						break;
		case "Do Not Handle if Pregnant":	div.innerHTML = putBooleanComponent();
						break;
		case "Do Not Mail":			div.innerHTML = putBooleanComponent();
						break;
		case "Dosage Form":			div.innerHTML = putDosageFormComponent();
						break;
		default:				div.innerHTML = "";

    }        
}


function setComponent(fieldType, element) {


    var div = document.getElementById(element);

    switch(fieldType) {
        case "Drug Class":
	case "Manufacturer":
	case "Generic Name":			div.innerHTML = putTextComponents();
						break;
	case "Local Use":			div.innerHTML = putBooleanComponent();
						break;
	case "Product Status":			div.innerHTML = putProductStatusComponent();
						break;
	case "Do Not Handle if Pregnant":	div.innerHTML = putBooleanComponent();
						break;
	case "Do Not Mail":			div.innerHTML = putBooleanComponent();
						break;
	case "Dosage Form":			div.innerHTML = putDosageFormComponent();
						break;
	default:				div.innerHTML = "";

    }
    
    
}


function createConstraintComponent() {
/*    var constraint = document.createElement("select");
    constraint.options(0) = "begins with";
    constraint.options(1) = "contains";
    constraint.options(2) = "is exactly";*/
	var constraint = document.createTextNode("&nbsp;Hello");
	return constraint;
}

function createTextField() {
	var text = document.createElement("input");
	text.type = "text";
	return text;
}

function getTextComponent(div) {

    var space = document.createTextNode("&nbsp;");
    div.appendChild(space);
    var constraint = document.createElement("select");
    constraint.options(0) = "begins with";
    constraint.options(1) = "contains";
    constraint.options(2) = "is exactly";
    div.appendChild(constraint);
    div.appendChild(space);
    var text = createElement("input");
    text.type = "text";
    div.appendChild(text);

}


function putTextComponents(div) {
	var space = document.createTextNode("&nbsp;");
    var constraints = document.createElement("select");
    constraints.options(0) = "begins with";
    constraints.options(1) = "contains";
    constraints.options(2) = "is exactly";
    var text = createElement("input");
    text.type = "text";	    
	div.appendChild(space);
    div.appendChild(constraints);
    div.appendChild(space);
    div.appendChild(text);	
}


function putBooleanComponent() {
    bGroup++;
    return "<input type='radio' name='boolGrp"+bGroup+"' value='true'/>Yes <input type='radio' name='boolGrp"+bGroup+"' value='false'/>No";

}


function putProductStatusComponent() {

    return "<select multiple='yes'><option>Active</option><Option>Inactive</option></select>";

}


function putDosageFormComponent() {

    return "<select><option>Tablet</option><Option>Capsule</option></select>";

}


function comment() {

    window.open('comment.html','', 'width=363,height=170');

}


function saveQuery() {

    document.execCommand('SaveAs','1',null);

    //window.open('query.html','', 'width=363,height=100');

}


function reportDiv(root) {

    var divHead = document.getElementByTagName("div");
    
    alert("element: " + root);

}

function aboutDialog() {

    window.open('about.html', '', 'width=400, height=400'); 

}

function note() {

    window.open('notes.html','','width=363, height=200');
	
}


function enableSearch() {
	activate("searchBy-Select", false);
/*	activate("searchFor", false);
	activate("strength", false);
	activate("form", false);
	activate("searchButton", false):
	activate("statusApproved", false);
	activate("statusPending", false);
	activate("statusRejected", false);
	activate("statusActive", false);
	activate("statusInactive", false);
	activate("localUse", false);
	activate("allItems", false);*/	
}


function disableSearch() {
		activate("searchBy-Select", true);
/*		activate("searchFor", true);
		activate("strength", true);
		activate("form", true);
		activate("searchButton", true);
		activate("statusApproved", true);
		activate("statusPending", true);
		activate("statusRejected", true);
		activate("statusActive", true);
		activate("statusInactive", true);
		activate("localUse", true);
		activate("allItems", true);	*/
}


function activate(id, active) {
	var element = document.getElementById(id);
	element.disabled = active;	
	
}

