<br/>
<br/>
<h1><big><strong>Migration Summary</strong></big></h1>
<br/>
<table width="700" border="1" align="center" cellspacing="0" cellpadding="5">
	<tr>
	    <td align="left"><strong>File</strong></td>
    	<td align="center"><strong>Total Records Processed</strong></td>
    	<td align="center"><strong>Total Errors</strong></td>
    	<td align="center"><strong>Duplicates Not Migrated</strong></td>
    	<td align="center"><strong>Total Migrated</strong></td>
    </tr>
    <tr>
    	<td>Drug Units - 50.607</td>
    	<td align="center">${migrationState.migratedDrugUnit.count}</td>
    	<td align="center">${migrationState.migratedDrugUnit.totalErrors}</td><!-- how many errors were encountered -->
    	<td align="center">${migrationState.migratedDrugUnit.duplicatesNoMigrated}</td><!-- duplicates not  migrated-->
    	<td align="center">${migrationState.migratedDrugUnit.totalMigrated}</td><!-- and successfully migrated for the particular file type (i.e. NDC) -->
    </tr>
    <tr>
    	<td>VA Dispense Unit - 50.64</td>
   		<td align="center">${migrationState.migratedVaDispenseUnit.count}</td>
       	<td align="center">${migrationState.migratedVaDispenseUnit.totalErrors}</td>
       	<td align="center">${migrationState.migratedVaDispenseUnit.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedVaDispenseUnit.totalMigrated}</td>
    </tr>
    <tr>
    	<td>VA Generic Name - 50.6</td>
 		<td align="center">${migrationState.migratedVaGenericName.count}</td>
		<td align="center">${migrationState.migratedVaGenericName.totalErrors}</td>
       	<td align="center">${migrationState.migratedVaGenericName.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedVaGenericName.totalMigrated}</td>
   </tr>
    <tr>
    	<td>Dosage Form - 50.606</td>
    	<td align="center">${migrationState.migratedDosageForm.count}</td>
       	<td align="center">${migrationState.migratedDosageForm.totalErrors}</td>
       	<td align="center">${migrationState.migratedDosageForm.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedDosageForm.totalMigrated}</td>
    </tr>
    <tr>
    	<td>Drug Class - 50.605</td>
    	<td align="center">${migrationState.migratedDrugClass.count}</td>
       	<td align="center">${migrationState.migratedDrugClass.totalErrors}</td>
       	<td align="center">${migrationState.migratedDrugClass.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedDrugClass.totalMigrated}</td>
    </tr>
    <tr>
    	<td>Drug Ingredients - 50.416</td>
    	<td align="center">${migrationState.migratedIngredients.count}</td>
       	<td align="center">${migrationState.migratedIngredients.totalErrors}</td>
       	<td align="center">${migrationState.migratedIngredients.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedIngredients.totalMigrated}</td>
    </tr>
    <tr>
    	<td>Orderable Items CSV File</td>
      	<td align="center">${migrationState.migratedOrderableItemsCsv.count}</td>
       	<td align="center">${migrationState.migratedOrderableItemsCsv.totalErrors}</td>
       	<td align="center">${migrationState.migratedOrderableItemsCsv.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedOrderableItemsCsv.totalMigrated}</td>
    </tr>
 
    <tr>
    	<td>VA Product - 50.68</td>
  		<td align="center">${migrationState.migratedVaProduct.count}</td>
       	<td align="center">${migrationState.migratedVaProduct.totalErrors}</td>
       	<td align="center">${migrationState.migratedVaProduct.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedVaProduct.totalMigrated}</td>
    </tr>
    <tr>
    	<td>NDC CSV File</td>
     	<td align="center">${migrationState.migratedNDCCsvFile.count}</td>
       	<td align="center">${migrationState.migratedNDCCsvFile.totalErrors}</td>
       	<td align="center">${migrationState.migratedNDCCsvFile.duplicatesNoMigrated}</td>
       	<td align="center">${migrationState.migratedNDCCsvFile.totalMigrated}</td>
    </tr>
 </table>
    <!-- TODO: ensure the view all details button is disabled while a migration is in progress -->
<p>&nbsp;</p>
<table
   	width="700"
  	border="1"
  	align="center"
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
<br/>
<br/>