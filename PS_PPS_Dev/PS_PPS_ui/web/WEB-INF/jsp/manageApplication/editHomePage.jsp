<%@ taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@ taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib 
    prefix="pps" 
    tagdir="/WEB-INF/tags/pps"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
    
<script type="text/javascript">
function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    } 
}

function toggleImageDisplay(imageSelection) {
    var displayImageBlock = document.getElementById('imageDisplay');
    var displayImageUpload = document.getElementById('displayImageUpload');
    var defaultImage = document.getElementById('defaultImage');
    var currentImage = document.getElementById('currentImage');
    
    if(imageSelection == "CURRENT") {
        defaultImage.style.display="none";
        defaultImage.style.visibility="hidden";
        displayImageBlock.style.display = "block";
        displayImageUpload.style.display = "none";
        displayImageBlock.style.visibility = "visible";
        displayImageUpload.style.visibility = "hidden";
        defaultImage.style.display="none";
        defaultImage.style.visibility="hidden";
        currentImage.style.display="inline";
        currentImage.style.visibility="visible";
    } else if(imageSelection == "UPLOAD_NEW") {
        displayImageBlock.style.display = "none";
        displayImageUpload.style.display = "block";
        displayImageBlock.style.visibility = "hidden";
        displayImageUpload.style.visibility = "visible";
    } else {
        displayImageBlock.style.display = "block";
        displayImageUpload.style.display = "none";
        displayImageBlock.style.visibility = "visible";
        displayImageUpload.style.visibility = "hidden";
        currentImage.style.display="none";
        currentImage.style.visibility="hidden";
        defaultImage.style.display="inline";
        defaultImage.style.visibility="visible";
    }
}
</script>

<style type="text/css">

label {
    float: left;
    width: 8em;
    margin-right: 1em;
    font-weight: bold;
}

#imageSelect {
    float: left; 
    margin-right: 5px;
    margin-left: 5px;
    margin-top: 5px;
    min-height: 350px;
    /* background-color: #cccccc; */
}

#imageDisplay {
    min-height: 150px; 
    min-width: 150px;
    max-height: 500px;
    max-width: 500px;
    overflow: hidden;
    margin-top: 10px;
    visibility: hidden;
    display: none;
    /* background-color: #ffcccc; */
}

#displayImageUpload {
    visibility: hidden;
    display: none;
    /* background-color: #ccffff; */
}

#announceInfo {
    margin-left: 5px; 
    margin-top: 5px;
    min-height: 350px;
    /* background-color: #cccccc; */
}

#formButtons {
    clear: both;
    padding-top: 1em;
    margin-top: 50px;
    margin-left: 1em;
    /* background-color: #ccccff; */
}

#defaultImage {
    border-style: solid;
    border-color: black;
    border-width: 0px; /* zero, as the default image doesn't need a border to show the user */
}

#currentImage {
    border-style: solid;
    border-color: black;
    border-width: 1px; /* a small border to show the user the edge of the custom current image */
}
</style>

<div class="body-content">
    <div class="horizontalspacer"></div>
    
    <div>        
        <form:form id="editHomePage" action="/PRE/editHomePage.go" modelAttribute="preferences" method="post" 
            enctype="multipart/form-data" onsubmit="return disableSubmit(this.id);">
            <div>
                <fieldset style="float: left; margin-right: 1.5em; margin-left: 1em;">
                    <legend>Home Page Image</legend>
                    <div id="imageSelect">
                        <div style="margin-bottom: 1em; padding-right: 1em;">
                            <label for="imageFormSelect" class="left comboBox">Select image:</label>
                            <form:select id="imageFormSelect" path="imageSelected" cssClass="comboBox" items="${imageSelectChoices}"
                                itemLabel="displayValue" itemValue="value" onchange="toggleImageDisplay(this.value);"/>
                        </div>
                        <div id="imageDisplay">
                            <img id="defaultImage" class="news-img" src="images/VeteransPoster.jpg" alt="Honoring Those Who Served" width="180" />
                            <img id="currentImage" class="news-img" src="uploaded/image/HomePage-current.go" alt="Current Custom Homepage image"/>
                        </div>
                        <div id="displayImageUpload">
                            <label for="image.upload" class="left comboBox">Upload image:</label>
                            <input id="image.upload" type="file" name="file" />
                        </div>
                    </div>
                </fieldset>
                <fieldset style="float: left;">
                    <legend>Announcement Information</legend>
                    <div id="announceInfo">
                        <div style="margin-bottom: 1em; padding-right: 1em;">                        
                            <label for="announcement.title" class="left">Title:</label>
                            <form:input id="announcement.title" path="title" maxlength="100" size="50"/>
                        </div>
                        <div style="margin-bottom: 1em; padding-right: 1em;">                        
                            <label for="announcement.body" class="left">Body:<br/>
                                <span style="font-size: 80%; font-weight: normal;">(2000 character limit)</span>
                            </label>
                            <form:textarea id="announcement.body" path="body" cols="50" rows="10" onkeydown="limitText(this, 2000);" 
                                onkeyup="limitText(this, 2000);"/>
                        </div>
                        <div style="margin-bottom: 1em; padding-right: 1em;">                        
                            <label for="announcement.link.title" class="left">Link Title:</label>
                            <form:input id="announcement.link.title" path="linkTitle" maxlength="100" size="50"/>
                        </div>
                        <div style="padding-right: 1em;">                        
                            <label for="announcement.link" class="left">Link:</label>
                            <form:input id="announcement.link" path="link" maxlength="1000" size="50"/>
                        </div>
                    </div>
                </fieldset>
            </div>
            <div id="formButtons">
                <span style="margin-right: 1em;">
                    <peps:cancelMvc key="button.undo" name="cancel" />
                </span>
                <span>
                    <peps:submit key="button.saveChanges" />
                </span>
            </div>
        </form:form>      
        <script type="text/javascript">
        <!--
        toggleImageDisplay(document.getElementById('imageFormSelect').value);
        // -->
        </script>
          
    </div>
</div>