<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>

<fieldset><legend>Work in Progress</legend><peps:table
    tableId="partialSaveTable"
    dataOgnl="partialItems"
    fieldsOgnl="searchTemplate.printTemplate"
    multiselectButtonKey="button.delete"
    multiselectButtonEvent="delete"
    paged="false" /></fieldset>
