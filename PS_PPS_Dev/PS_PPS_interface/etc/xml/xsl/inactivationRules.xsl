<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform
    version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="text" />
    <xsl:strip-space elements="*" />
    <xsl:template match="text()" />

    <xsl:template match="*">
        <!-- NDF Files -->

        <xsl:for-each select="*[local-name(.)='drugIngredientsFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>1.) To inactivate a Drug Ingredient File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='drugManufacturerFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>2.) To inactivate a Drug Manufacturer File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='drugUnitsFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>3.) To inactivate a Drug Unit File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='ndcUpnFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>4.) To inactivate a NDC/UPN File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='packageSizeFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>5.) To inactivate a Package Size File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='packageTypeFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>6.) To inactivate a Package Type File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='vaDispenseUnitFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>7.) To inactivate a VA Dispense Unit File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='vaDrugClass']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>8.) A Va Drug Class entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='vaGenericFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>9.) To inactivate a VA Generic File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='vaProductFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>10.) To inactivate a VA Product File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <!-- PDM Files -->

        <xsl:for-each select="*[local-name(.)='administrationScheduleFile']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>11.) An Administration Schedule File entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='dosageFormFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>12.) To inactivate a Dosage Form File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='dosageUnitFile']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>13.) A Dosage Unit File entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>


        <xsl:for-each select="*[local-name(.)='drugFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactiveDate']) = 0)">
                <xsl:text>14.) To inactivate a Drug File entry, an 'inactiveDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='drugTextFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>15.) To inactivate a Drug Text File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='medicationInstructionFile']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>16.) A Medication Instruction File entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='medicationRoutesFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactivationDate']) = 0)">
                <xsl:text>17.) To inactivate a Medication Routes File entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='orderUnitFile']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>18.) An Order Unit File entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='pharmacyOrderableItemFile']">
            <xsl:if test="(@action = 'Inactivate') and (count(*[local-name(.)='inactiveDate']) = 0)">
                <xsl:text>19.) To inactivate a Pharmacy Orderable Item entry, an 'inactivationDate' must be specified.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='pharmacySystemFile']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>20.) A Pharmacy System File entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='rxConsultFile']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>21.) A Rx Consult File entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>

        <xsl:for-each select="*[local-name(.)='standardMedicationRoutesFile']">
            <xsl:if test="@action = 'Inactivate'">
                <xsl:text>22.) A Standard Medication Routes File entry can not be inactivated.&#10;</xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:transform>
