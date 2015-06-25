<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	function migrationDetails()
	{
		window.location = '/PRE/migrationDetails.go?viewReport=DOSAGE_FORM_ACTIVE&singleReport=false&printReport=false';	
	}
</script>

<div class="panel">
	<table
        width="1250"
        border="0">
        <tr>
        	<td width="50">
        	<a href="/PRE/summaryPrint.go" onclick="return popupWithOptions(this.href, 'print_summary', 'resizable=yes,scrollbars=yes,toolbar=yes, width=1050,height=650');" >Print</a></td>
        	<td style="font-size:x-large" align="center" ><strong>Migration Summary</strong></td></tr>
        <tr><td colspan="2"></td></tr>
    </table>
    <TABLE width="1250">
		<TR><TD width="230">&nbsp;</TD>
		<TD>
			<table
		        width="800"
		        border="1"
		        cellspacing="0"
		        cellpadding="5">
		        <tr>
		        	<td align="left"><strong>File</strong></td>
		        	<td align="center"><strong>Total Records Processed</strong></td>
		        	<td align="center"><strong>Total Errors</strong></td>
		        	<td align="center"><strong>Duplicates Not Migrated</strong></td>
		        	<td align="center"><strong>Total Migrated</strong></td>
		        </tr>
		        <tr><!--TODO: replace migrationState calls with the correct VO calls and eliminate trailing 0's
		        				Also, add appropriate VO calls in conjunction with the TODO below.  -->
		        	<td>
		        		<a href="/PRE/migrationDetails.go?viewReport=DRUG_UNITS_ACTIVE&singleReport=true&printReport=false" >Drug Units - 50.607</a>
	        		</td>
		        	<td align="center">${migrationState.migratedDrugUnit.count}</td>
		        	<td align="center">${migrationState.migratedDrugUnit.totalErrors}</td><!-- how many errors were encountered -->
		        	<td align="center">${migrationState.migratedDrugUnit.duplicatesNoMigrated}</td><!-- duplicates not  migrated-->
		        	<td align="center">${migrationState.migratedDrugUnit.totalMigrated}</td><!-- and successfully migrated for the particular file type (i.e. NDC) -->
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=VA_DISPENSE_UNIT_ACTIVE&singleReport=true&printReport=false">VA Dispense Unit - 50.64</a></td>
		        	<td align="center">${migrationState.migratedVaDispenseUnit.count}</td>
		        	<td align="center">${migrationState.migratedVaDispenseUnit.totalErrors}</td>
		        	<td align="center">${migrationState.migratedVaDispenseUnit.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedVaDispenseUnit.totalMigrated}</td>
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=VA_GENERIC_NAME_ACTIVE&singleReport=true&printReport=false">VA Generic Name - 50.6</a></td>
		        	<td align="center">${migrationState.migratedVaGenericName.count}</td>
		        	<td align="center">${migrationState.migratedVaGenericName.totalErrors}</td>
		        	<td align="center">${migrationState.migratedVaGenericName.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedVaGenericName.totalMigrated}</td>
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=DOSAGE_FORM_ACTIVE&singleReport=true&printReport=false">Dosage Form - 50.606</a></td>
		        	<td align="center">${migrationState.migratedDosageForm.count}</td>
		        	<td align="center">${migrationState.migratedDosageForm.totalErrors}</td>
		        	<td align="center">${migrationState.migratedDosageForm.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedDosageForm.totalMigrated}</td>
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=DRUG_CLASS_0&singleReport=true&printReport=false">Drug Class - 50.605</a></td>
		        	<td align="center">${migrationState.migratedDrugClass.count}</td>
		        	<td align="center">${migrationState.migratedDrugClass.totalErrors}</td>
		        	<td align="center">${migrationState.migratedDrugClass.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedDrugClass.totalMigrated}</td>
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=DRUG_INGREDIENTS_ACTIVE&singleReport=true&printReport=false">Drug Ingredients - 50.416</a></td>
		        	<td align="center">${migrationState.migratedIngredients.count}</td>
		        	<td align="center">${migrationState.migratedIngredients.totalErrors}</td>
		        	<td align="center">${migrationState.migratedIngredients.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedIngredients.totalMigrated}</td>
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=ORDERABLE_ITEMS_CSV_FILE_ACTIVE&singleReport=true&printReport=false">Orderable Items CSV File - 50.7</a></td>
		        	<td align="center">${migrationState.migratedOrderableItemsCsv.count}</td>
		        	<td align="center">${migrationState.migratedOrderableItemsCsv.totalErrors}</td>
		        	<td align="center">${migrationState.migratedOrderableItemsCsv.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedOrderableItemsCsv.totalMigrated}</td>
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=VA_PRODUCT_ACTIVE&singleReport=true&printReport=false">VA Product - 50.68</a></td>
		        	<td align="center">${migrationState.migratedVaProduct.count}</td>
		        	<td align="center">${migrationState.migratedVaProduct.totalErrors}</td>
		        	<td align="center">${migrationState.migratedVaProduct.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedVaProduct.totalMigrated}</td>
		        </tr>
		        <tr>
		        	<td><a href="/PRE/migrationDetails.go?viewReport=NDC_CSV_FILE_ACTIVE&singleReport=true&printReport=false">NDC CSV File - 50.67</a></td>
		        	<td align="center">${migrationState.migratedNDCCsvFile.count}</td>
		        	<td align="center">${migrationState.migratedNDCCsvFile.totalErrors}</td>
		        	<td align="center">${migrationState.migratedNDCCsvFile.duplicatesNoMigrated}</td>
		        	<td align="center">${migrationState.migratedNDCCsvFile.totalMigrated}</td>
		        </tr>
		     </table>
		 </TD><TD>&nbsp;</TD>
		</TR>
		<TR><TD colspan="3">&nbsp;</TD></TR>
		<TR><TD>&nbsp;</TD><TD colspan="2"><input type="button" name="View All" value="View All Details" onclick="migrationDetails();" /></TD></TR>
		        <!-- TODO: ensure the view all details button is disabled while a migration is in progress -->
		<TR><TD colspan="3">&nbsp;</TD></TR>
		<TR><TD>&nbsp;</TD>
			<TD>
				<table
		        	width="800"
		       		border="1"
		        	cellspacing="0"
		        	cellpadding="5">
		        	<tr>
		        		<td colspan="2" align="center"><big><strong>Additional Migrated Data</strong></big></td>
		        	</tr>
		        	<tr>
			        	<td align="center"><strong>Data</strong></td>
			        	<td align="center"><strong>Total Number Added</strong></td>
			        </tr>
			        <tr>
			        	<td align="center">Manufacturers</td>
			        	<td align="center">${migrationState.migratedNDCCsvFile.manufacturersMigrated}</td>
			        </tr>
			        <tr>
			        	<td align="center">Package Types</td>
			        	<td align="center">${migrationState.migratedNDCCsvFile.packageTypesMigrated}</td>
			        </tr>
		        </table>
		  </TD>
		  <TD>&nbsp;<input type="hidden" name="file" /></TD>
		</TR>
	</TABLE>
</div>