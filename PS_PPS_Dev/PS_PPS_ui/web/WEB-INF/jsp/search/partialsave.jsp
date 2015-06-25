<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>    

<fieldset><legend>Work in Progress</legend>

<peps:table
    tableId="partialSaveTable"
    dataModel="${partialItems}"
    printTemplate="${printTemplate}"
    multiselectButtonKey="button.delete"
    multiselectButtonAction="managePartialItem.go"
    paged="false" />

</fieldset>