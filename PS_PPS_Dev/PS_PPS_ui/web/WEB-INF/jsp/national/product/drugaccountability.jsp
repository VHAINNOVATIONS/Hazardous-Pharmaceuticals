<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<div class="panel">
	<center>
		<b><peps:text key="product.long.name" label="false" item="${item}" /> Synonym Multiple </b>
	</center>

	<peps:text key="ifcap.item.number" label="true" item="${item}" />
	<div class="horizontalspacer"></div>
	<peps:dataField key="synonyms" item="${item}" />
</div>