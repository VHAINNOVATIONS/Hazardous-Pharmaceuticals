/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * ServiceTestSampleMigrationXML
 * 
 */
public class ServiceTestSampleMigrationXML {

    private static String STARTTAG = "<ndfmsFile>";
    private static String STOPTAG = "</ndfmsFile>";
    private static String ACTIVETAG = "<type>1</type>";
    private static String MANUFACTURERSTARTTAG = "<manufacturerMigrationSyncRequest";
    private static String MANUFACTURERSTOPTAG = "</manufacturerMigrationSyncRequest>";
    private static String PACKAGETYPESTARTTAG = "<packageTypeMigrationSyncRequest";
    private static String PACKAGETYPESTOPTAG = "</packageTypeMigrationSyncRequest>";
    private static String DRUGUNITSYNC = "<drugUnitSyncRequest";
    private static String VADISPENSEUNITSYNC = "<vaDispenseUnitSyncRequest";
    private static String VAGENERICNAMESYNC = "<vaGenericNameSyncRequest";
    private static String DOSAGEFORMSYNC = "<dosageFormSyncRequest";
    private static String DRUGCLASSSYNC = "<drugClassSyncRequest";
    private static String DRUGINGREDIENTSSYNC = "<drugIngredientSyncRequest";
    private static String PRODUCTSYNC = "<vaProductSyncRequest";
    private static String NDCSYNC = "<ndcSyncRequest";
    private static String MANUFACTURERSYNC = "<manufacturerSyncRequest";
    private static String PACKAGETYPESYNC = "<packageTypeSyncRequest";
    private static String XML1 = "xmlns=\"gov/va/med/pharmacy/peps/external/common/vo/inbound/migration/data/response\" ";
    private static String XML2 = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\">";

    /**
     * default Constructor
     */
    public ServiceTestSampleMigrationXML() {
    }

    /**
     * getXML
     * 
     * @param request
     *            request
     * @return String
     */
    public String getXML(String request) {

        if (request.contains(STARTTAG + PPSConstants.VA_DRUG_UNIT_FILE + STOPTAG)) {
            if (request.contains(ACTIVETAG)) {
                return createDrugUnitActiveXML();
            } else {
                return createDrugUnitInActiveXML();
            }
        }

        if (request.contains(STARTTAG + PPSConstants.VA_DISPENSE_UNIT_FILE + STOPTAG)) {
            if (request.contains(ACTIVETAG)) {
                return createDispenseUnitActiveXML();
            } else {
                return createDispenseUnitInActiveXML();
            }
        }

        if (request.contains(STARTTAG + PPSConstants.VA_GENERIC_NAME_FILE + STOPTAG)) {
            if (request.contains(ACTIVETAG)) {
                return createGenericNameActiveXML();
            } else {
                return createGenericNameInActiveXML();
            }
        }

        if (request.contains(STARTTAG + PPSConstants.VA_DRUG_INGREDIENT_FILE + STOPTAG)) {
            if (request.contains(ACTIVETAG)) {
                return createDrugIngredientActiveXML();
            } else {
                return createDrugIngredientInActiveXML();
            }
        }

        if (request.contains(STARTTAG + PPSConstants.VA_DRUG_CLASS_FILE + STOPTAG)) {
            if (request.contains("<type>0</type>")) {
                return createDrugClass0XML();
            } else {
                if (request.contains(ACTIVETAG)) {
                    return createDrugClass1XML();
                } else {
                    return createDrugClass2XML();
                }
            }
        }

        if (request.contains(STARTTAG + PPSConstants.VA_DOSAGE_FORM_FILE + STOPTAG)) {
            if (request.contains(ACTIVETAG)) {
                return this.createDosageFormActive();
            } else {
                return this.createDosageFormInActive();
            }
        }

        if (request.contains(STARTTAG + PPSConstants.VA_PRODUCT_FILE + STOPTAG)) {
            if (request.contains(ACTIVETAG)) {
                return this.createProductActive();
            } else {
                return this.createProductInActive();
            }
        }

        return new String();
    }

    /**
     * createDrugUnitActiveXML
     * 
     * @return drugUnitXML
     */
    private String createDrugUnitActiveXML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");
        a.append("<drugUnits><drugUnitsIen>33</drugUnitsIen><name>%/0.5MLL</name>");
        a.append("<inactivationDate></inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>36</drugUnitsIen><name>%MINL</name>");
        a.append("<inactivationDate></inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>37</drugUnitsIen><name>%%</name><inactivationDate></inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>123</drugUnitsIen><name>NEWC30.5ML</name>" 
                 + "<inactivationDate></inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>223</drugUnitsIen><name>NEW13GM/1CC</name>" 
                 + "<inactivationDate></inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>225</drugUnitsIen><name>NEW13GM/1CC</name>" 
                 + "<inactivationDate></inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>344</drugUnitsIen><name></name><inactivationDate></inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>677</drugUnitsIen>" 
                 + "<name>Over75Characters1234567890123456789012345678901234567890123456789012345678901234567890</name>" 
                 + "<inactivationDate></inactivationDate></drugUnits>");
        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDrugUnitInactiveXML
     * 
     * @return xml
     */
    private String createDrugUnitInActiveXML() {
        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");
        a.append("<drugUnits><drugUnitsIen>16</drugUnitsIen><name>NEW/ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>18</drugUnitsIen><name>NEW/10ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>28</drugUnitsIen><name>NEW1MG/25ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>40</drugUnitsIen><name>NEWUNT/10M</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>41</drugUnitsIen><name>NEWUNT/2ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>45</drugUnitsIen><name>NEW1%/0.5ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>46</drugUnitsIen><name>NEW1%MIN</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>50</drugUnitsIen><name></name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>51</drugUnitsIen><name>" 
                 + "InActOver75Characters123456789012345678901234567890123456789012345678901234567890123456789</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>52</drugUnitsIen><name>MEQ/50ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("<drugUnits><drugUnitsIen>54</drugUnitsIen><name>MILLION UNT</name>" 
                 + "<inactivationDate>20080219</inactivationDate></drugUnits>");
        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDispenseUnitActiveXML
     * 
     * @return xml
     */
    private String createDispenseUnitActiveXML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>1</dispenseUnitsIen><name>NEW5ML</name>" 
                 + "<inactivationDate></inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>5</dispenseUnitsIen><name>NEW10ML</name>" 
                 + "<inactivationDate></inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>8</dispenseUnitsIen><name>ONLYTENCHARACTERSALLOWED</name>" 
                 + "<inactivationDate></inactivationDate></vaDispenseUnit>");
        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDispenseUnitInActiveXML
     * 
     * @return xml
     */
    private String createDispenseUnitInActiveXML() {
        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>16</dispenseUnitsIen><name>NN72GR/ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>18</dispenseUnitsIen><name>OVER10CHARSTA7MEQ/10ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>28</dispenseUnitsIen><name>NN7MG/25ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>40</dispenseUnitsIen><name>NN7UNT/10M</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>41</dispenseUnitsIen><name>NN7UNT/2ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>45</dispenseUnitsIen><name>NN7%/0.5ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>46</dispenseUnitsIen><name>NN7%MIN</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>50</dispenseUnitsIen><name>NN70.3ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>51</dispenseUnitsIen><name>NN70.6ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>52</dispenseUnitsIen><name>NN710ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>54</dispenseUnitsIen><name>NN720ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>54</dispenseUnitsIen><name>NN720ML</name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("<vaDispenseUnit><dispenseUnitsIen>54</dispenseUnitsIen><name></name>" 
                 + "<inactivationDate>20080219</inactivationDate></vaDispenseUnit>");
        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createGenericNameActiveXML
     * 
     * @return xml
     */
    private String createGenericNameActiveXML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");
        a.append("<vaGenericName><vaGenericIen>1</vaGenericIen><name><![CDATA[NEWG&ENERIC1]]></name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>411111</vuid><effectiveDateTime><effectiveDateTime>20110415</effectiveDateTime>" 
                 + "<status>1</status></effectiveDateTime></vaGenericName>");
        a.append("<vaGenericName><vaGenericIen>11</vaGenericIen><name>NEWGENERIC2</name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>411112</vuid><effectiveDateTime><effectiveDateTime>20110416</effectiveDateTime>" 
                 + "<status>1</status></effectiveDateTime></vaGenericName>");
        a.append("<vaGenericName><vaGenericIen>21</vaGenericIen><name>NEWGENERIC3</name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>411113</vuid><effectiveDateTime><effectiveDateTime>20110417</effectiveDateTime>" 
                 + "<status>1</status></effectiveDateTime></vaGenericName>");
        a.append("<vaGenericName><vaGenericIen>31</vaGenericIen><name>NEWGENERIC3</name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>411114</vuid><effectiveDateTime><effectiveDateTime>20110418</effectiveDateTime>" 
                 + "<status>1</status></effectiveDateTime></vaGenericName>");
        a.append("<vaGenericName><vaGenericIen>41</vaGenericIen><name>NEWGENERICFAILNEWGENERICFAILNEWGENE" 
                 + "RICFAILNEWGENERICFAILNEWGENERICFAILNEWGENERICFAILNEWGENERICFAILNEWGENERICFAILNEWGENERICF" 
                 + "AILNEWGENERICFAILNEWGENERICFAILNEWGENERICFAIL</name><inactivationDate></inactivationDate>" 
                 + "<masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>34534</vuid><effectiveDateTime><effectiveDateTime>20110418</effectiveDateTime>" 
                 + "<status>1</status></effectiveDateTime></vaGenericName>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createGenericNameInActiveXML
     * 
     * @return xml
     */
    private String createGenericNameInActiveXML() {
        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");
        a.append("<vaGenericName><vaGenericIen>2</vaGenericIen><name>NEWGENERIC4</name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>411115</vuid><effectiveDateTime><effectiveDateTime>20110415</effectiveDateTime>" 
                 + "<status>0</status></effectiveDateTime></vaGenericName>");
        a.append("<vaGenericName><vaGenericIen>12</vaGenericIen><name>NEWGENERIC5</name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>411116</vuid><effectiveDateTime><effectiveDateTime>20110416</effectiveDateTime>" 
                 + "<status>0</status></effectiveDateTime></vaGenericName>");
        a.append("<vaGenericName><vaGenericIen>22</vaGenericIen><name>NEWGENERIC6</name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>1</masterEntryForVuid>");
        a.append("<vuid>411117</vuid><effectiveDateTime><effectiveDateTime>20110417</effectiveDateTime>" 
                 + "<status>0</status></effectiveDateTime></vaGenericName>");
        a.append("<vaGenericName><vaGenericIen>32</vaGenericIen><name>NEWGENERIC3</name>" 
                 + "<inactivationDate></inactivationDate><masterEntryForVuid>0</masterEntryForVuid>");
        a.append("<vuid>411118</vuid><effectiveDateTime><effectiveDateTime>20110418</effectiveDateTime>" 
                 + "<status>0</status></effectiveDateTime></vaGenericName>");
        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDrugIngredientActiveXML
     * 
     * @return xml
     */
    private String createDrugIngredientActiveXML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<drugIngredients><drugIngredientsIen>12</drugIngredientsIen><name>CHILD3</name>" 
                 + "<primaryIngredient>PARENT3</primaryIngredient>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4444</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>13</drugIngredientsIen><name>PARENT2</name>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4445</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>14</drugIngredientsIen><name>CHILD1</name>" 
                 + "<primaryIngredient>PARENT1</primaryIngredient>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4446</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>15</drugIngredientsIen><name>PARENT1</name>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>44477</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110312</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>16</drugIngredientsIen><name>CHILD2</name>" 
                 + "<primaryIngredient>PARENT2</primaryIngredient>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4448</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>4759</drugIngredientsIen><name>NEWAALEVOMEFOLATE</name>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>40308541</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110312</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>4761</drugIngredientsIen>" 
                 + "<name>AMPICLLIN CARBONATE</name><primaryIngredient>AMPICILLIN</primaryIngredient>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>403450</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>4761</drugIngredientsIen>" 
                 + "<name>AMPICLLIN CARBONATION</name><primaryIngredient>AMPICILLIN</primaryIngredient>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>40450</vuid><effectiveDateTime>" 
                 + "<effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>4761</drugIngredientsIen>" 
                 + "<name>NEWAAPEFOLATE CALCIUM</name><primaryIngredient>PATLEVOMEFOLATE</primaryIngredient>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4030850</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>4764</drugIngredientsIen><name>NEWAAPATGADOBUTROL</name>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>403085231</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20110712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>5467</drugIngredientsIen><name>NEWINGREDIENT1</name>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4033085231</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20090712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>4</drugIngredientsIen><name></name>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4033085231</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20090712</effectiveDateTime>");
        a.append("<status>1</status></effectiveDateTime></drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>162</drugIngredientsIen><name>" 
                 + "<![CDATA[INSULIN,REGULAR,HUMAN/SEMISYNTHETIC]]></name><primaryIngredient><![CDATA[INSULIN]]>" 
                 + "</primaryIngredient><masterEntryForVuid>1</masterEntryForVuid><vuid>4017569</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></drugIngredients>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDrugIngredientInActiveXML
     * 
     * @return xml
     */
    private String createDrugIngredientInActiveXML() {
        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<drugIngredients><drugIngredientsIen>146</drugIngredientsIen><name>INSULIN,PROTAMINE ZINC,BEEF/PORK</name>" 
                 + "<primaryIngredient></primaryIngredient>");
        a.append("<inactivationDate>20051014</inactivationDate><masterEntryForVuid>1</masterEntryForVuid><vuid>4017553</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20051014</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("</drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>1211</drugIngredientsIen><name>PARENT3</name>");
        a.append("<inactivationDate>20091014</inactivationDate><masterEntryForVuid>1</masterEntryForVuid><vuid>401233</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20091014</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("</drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>1464</drugIngredientsIen>" 
                 + "<name>INSULIN,PROTAMINE ZINC,BEEF/CHICKEN</name><primaryIngredient>INSULIN</primaryIngredient>");
        a.append("<inactivationDate>20091014</inactivationDate><masterEntryForVuid>1</masterEntryForVuid><vuid>401233</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20091014</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("</drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>1555</drugIngredientsIen>" 
                 + "<name>NEWINGREDIENT1</name><primaryIngredient></primaryIngredient>");
        a.append("<inactivationDate>20091014</inactivationDate><masterEntryForVuid>1</masterEntryForVuid><vuid>401233</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20091014</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("</drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>1666</drugIngredientsIen>" 
                 + "<name>NEWINGREDIENT2</name><primaryIngredient>NEWINGREDIENT1</primaryIngredient>");
        a.append("<inactivationDate>20091014</inactivationDate><masterEntryForVuid>1</masterEntryForVuid><vuid>401233</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20091014</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("</drugIngredients>");

        a.append("<drugIngredients><drugIngredientsIen>2213</drugIngredientsIen>" 
                 + "<name><![CDATA[INSULIN,REGULAR,HUMAN/SEMISYNTHETIC]]></name>");
        a.append("<primaryIngredient><![CDATA[INSULIN]]></primaryIngredient><inactivationDate>20041122</inactivationDate>" 
                 + "<masterEntryForVuid>0</masterEntryForVuid><vuid>4017569</vuid>" 
                 + "<effectiveDateTime><effectiveDateTime>20041122</effectiveDateTime><status>0</status>" 
                 + "</effectiveDateTime>");
        a.append("</drugIngredients>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDrugClass0XML
     * 
     * @return xml
     */
    private String createDrugClass0XML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<vaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>CHRISANTIDOTES,DETERRENTS AND POISON CONTROL</classification>");
        a.append("<type>0</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021513</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<description>NOTE: Includes nicotine polacrilex and other deterrents (AD900).</description>");
        a.append("<description>Excludes anticoagulant antagonists (BL200,VT700);</description> ");
        a.append("<description>antifolate antagonists (VT102); antivenins (IM300);</description>");
        a.append("<description>dialysis solutions (IR200); emetics (GA600); opioid</description>");
        a.append("<description>antagonists (CN102).</description>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>6</vaDrugClassIen><code>AH000</code>" 
                 + "<classification>CHRISANTIHISTAMINES</classification>");
        a.append("<type>0</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021518</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<description>NOTE: Excludes H2-antagonists (GA301); combination cold</description>");
        a.append("<description>products (RE500).</description>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>9</vaDrugClassIen><code>AM000</code>" 
                 + "<classification>CHRISANTIMICROBIALS</classification>");
        a.append("<type>0</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021521</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<description>NOTE: Combination products containing two or more active</description>");
        a.append("<description>ingredients from the same subclassification are</description>");
        a.append("<description>classified in that subclassification (e.g.,triple sulfas</description>");
        a.append("<description>in AM650). Products containing two or more active</description>");
        a.append("<description>ingredients from different subclassifications are</description>");
        a.append("<description>classified under \"anti-infectives, other\" (e.g.,</description>");
        a.append("<description>tetracycline and amphotericin B (AM900). Products</description>");
        a.append("<description>containing probenecid or clavulanic acid are classified</description>");
        a.append("<description>under the subclassification of the antimicrobial agent.</description>");
        a.append("<description>Beta-lactam antibiotics not classified under penicillins</description>");
        a.append("<description>or cephalosporins are classified (AM130).</description>");
        a.append("<description>Excludes topical anti-infectives (DE100), topical anti-</description>");
        a.append("<description>infective/anti-inflammatory combinations (DE250),</description>");
        a.append("<description>ophthalmic anti-infectives (OP200), ophthalmic anti-</description>");
        a.append("<description>infective/anti-inflammatory combinations (OP350), otic</description>");
        a.append("<description>anti-infectives (OT100), otic anti-infective/anti-</description>");
        a.append("<description>inflammatory combinations (OT250); vaginal anti-</description>");
        a.append("<description>infectives (GU300).</description>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>27</vaDrugClassIen><code>AN000</code>" 
                 + "<classification>CHRISANTINEOPLASTICS</classification>");
        a.append("<type>0</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021538</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<description>NOTE: Includes hormones (AN500) which are exclusively</description>");
        a.append("<description>used as antineoplastics (e.g.,tamoxifen).</description>");
        a.append("<description>Excludes other hormones (HS000).</description>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>39</vaDrugClassIen><code>AU000</code>" 
                 + "<classification>CHRISAUTONOMIC MEDICATIONS</classification>");
        a.append("<type>0</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021550</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<description>NOTE: Includes single ingredient anticholinergic products used</description>");
        a.append("<description>as antiparkinson agents (e.g.,benztropine,trihexyphenidyl</description>");
        a.append("<description>(AU350)) and single ingredient anticholinergic products used as</description>");
        a.append("<description>antispasmodics in the gastrointestinal tract</description>");
        a.append("<description>(e.g.,glycopyrrolate).</description>");
        a.append("<description>Excludes those products classified under selected</description>");
        a.append("<description>cardiovascular (beta-blockers (CV100), alpha-blockers</description>");
        a.append("<description>(CV150), antihypertensives (CV400,CV490)), respiratory</description>");
        a.append("<description>(sympathomimetic bronchodilators (RE103), anticholinergic</description>");
        a.append("<description>bronchodilators (RE105)), or ophthalmic (beta-blockers</description>");
        a.append("<description>(OP101,OP107)) subclassifications; gastrointestinal tract</description>");
        a.append("<description>antispasmodic combinations (GA802); and urinary tract</description>");
        a.append("<description>antispasmodics (GU200).</description>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>45</vaDrugClassIen><code>AU0003</code>" 
                 + "<classification>CHRISAUTONOMIC MEDICATIONS</classification>");
        a.append("<type>0</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021550</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<description>NOTE: Includes single ingredient anticholinergic products used</description>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>47</vaDrugClassIen><code>AU0003</code>"
                 + "<classification>CHRISAUTONOMIC MEDICATIONSCHRISAUTONOMIC MEDICATIONSCHRISAUTONOMIC " 
                 + "MEDICATIONSCHRISAUTONOMIC MEDICATIONSCHRISAUTONOMIC MEDICATIONSCHRISAUTONOMIC MEDICA" 
                 + "TIONSCHRISAUTONOMIC MEDICATIONS</classification>");
        a.append("<type>0</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021550</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<description>NOTE: Includes single ingredient anticholinergic products used</description>");
        a.append("</vaDrugClass>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDrugClass1XML
     * 
     * @return xml
     */
    private String createDrugClass1XML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<vaDrugClass><vaDrugClassIen>2</vaDrugClassIen><code>AD100</code>"
                 + "<classification>CHRISALCOHOL DETERRENTS</classification>");
        a.append("<parentClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>"
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021514</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>3</vaDrugClassIen><code>AD200</code>"
                 + "<classification>CHRISCYANIDE ANTIDOTES</classification>");
        a.append("<parentClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021515</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>4</vaDrugClassIen><code>AD400</code>" 
                 + "<classification>CHRISANTIDOTES,DETERRENTS,AND POISON CONTROL EXCHANGE RESINS</classification>");
        a.append("<parentClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021516</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>5</vaDrugClassIen><code>AD900</code>" 
                 + "<classification>CHRISANTIDOTES/DETERRENTS,OTHER</classification>");
        a.append("<parentClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021517</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>7</vaDrugClassIen><code>AH100</code>" 
                 + "<classification>CHRISANTIHISTAMINES,PHENOTHIAZINE</classification>");
        a.append("<parentClass><vaDrugClassIen>6</vaDrugClassIen><code>AH000</code>" 
                 + "<classification>ANTIHISTAMINES</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021519</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>8</vaDrugClassIen><code>AH109</code>" 
                 + "<classification>CHRISANTIHISTAMINES,OTHER</classification>");
        a.append("<parentClass><vaDrugClassIen>6</vaDrugClassIen><code>AH000</code>" 
                 + "<classification>ANTIHISTAMINES</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021520</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>10</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>CHRIS(INACTIVE) PENICILLINS</classification>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021522</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("<description>INACTIVE NO LONGER IN USE.</description>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>CHRISPENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification>");
        a.append("<parentClass><vaDrugClassIen>9</vaDrugClassIen><code>AM000</code>" 
                 + "<classification>CHRISANTIMICROBIALS</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021523</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>15</vaDrugClassIen><code>AM150</code>" 
                 + "<classification>CHRISCHLORAMPHENICOL</classification>");
        a.append("<parentClass><vaDrugClassIen>9</vaDrugClassIen><code>AM000</code>" 
                 + "<classification>CHRISANTIMICROBIALS</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021527</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>16</vaDrugClassIen><code>AM200</code>" 
                 + "<classification>CHRISERYTHROMYCINS/MACROLIDES</classification>");
        a.append("<parentClass><vaDrugClassIen>9</vaDrugClassIen><code>AM000</code>" 
                 + "<classification>CHRISANTIMICROBIALS</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021528</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>17</vaDrugClassIen><code>AM250</code>" 
                 + "<classification>CHRISTETRACYCLINES</classification>");
        a.append("<parentClass><vaDrugClassIen>9</vaDrugClassIen><code>AM000</code>" 
                 + "<classification>CHRISANTIMICROBIALS</classification></parentClass>");
        a.append("<type>1</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021529</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("</vaDrugClass>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDrugClass2XML
     * 
     * @return xml
     */
    private String createDrugClass2XML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<vaDrugClass><vaDrugClassIen>12</vaDrugClassIen><code>AM115</code>" 
                 + "<classification>CHRISCEPHALOSPORIN 1ST GENERATION</classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021524</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>13</vaDrugClassIen><code>AM116</code>" 
                 + "<classification>CHRISCEPHALOSPORIN 2ND GENERATION</classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021525</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>14</vaDrugClassIen><code>AM117</code>" 
                 + "<classification>CHRISCEPHALOSPORIN 3RD GENERATION</classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4021526</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>524</vaDrugClassIen><code>AM118</code>" 
                 + "<classification>CHRISCEPHALOSPORIN 4TH GENERATION</classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4022034</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>525</vaDrugClassIen><code>AM118</code>" 
                 + "<classification>CHRISCEPHALOSPORIN 5TH GENERATION</classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4022035</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>526</vaDrugClassIen><code>AM118</code>" 
                 + "<classification>CHRISCEPHALOSPORIN 6TH GENERATION</classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM1774</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4022036</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>5241</vaDrugClassIen><code></code>" 
                 + "<classification>CHRISCEPHALOSPORIN 4TH GENERATION</classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4022001</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("<vaDrugClass><vaDrugClassIen>5242</vaDrugClassIen><code>AM118</code><classification></classification>");
        a.append("<parentClass><vaDrugClassIen>11</vaDrugClassIen><code>AM114</code>" 
                 + "<classification>PENICILLINS AND BETA-LACTAM ANTIMICROBIALS</classification></parentClass>");
        a.append("<type>2</type><masterEntryForVuid>1</masterEntryForVuid><vuid>4022001</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status>" 
                 + "</effectiveDateTime></vaDrugClass>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDosageFormActive
     * 
     * @return xml
     */
    private String createDosageFormActive() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<dosageForm><dosageFormIen>14</dosageFormIen><name>MCG/0.4ML</name>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>15</dosageFormIen><name>TAB,SA,24HR (EXTENDED RELEASE)</name>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>1</dosageFormIen><name>NOUNITPACKAGE</name>");
        a.append("<units><unitsIen>1</unitsIen><units>GM</units><package></package></units>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>2</dosageFormIen><name>CHRISCAP,EC</name> ");
        a.append("<units><unitsIen>1</unitsIen><units>MG</units><package>IO</package></units>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>3</dosageFormIen><name>CHRISCAP,ORAL</name>");
        a.append("<units><unitsIen>1</unitsIen><units>GM</units><package>IO</package></units>");
        a.append("<units><unitsIen>2</unitsIen><units>MCG</units><package>IO</package></units>");
        a.append("<units><unitsIen>3</unitsIen><units>MG</units><package>IO</package></units>");
        a.append("<units><unitsIen>4</unitsIen><units>MG/PKG</units><package>I</package></units>");
        a.append("<units><unitsIen>5</unitsIen><units>MIC</units><package>IO</package></units>");
        a.append("<units><unitsIen>6</unitsIen><units>MIL</units><package>IO</package></units>");
        a.append("<units><unitsIen>7</unitsIen><units>MIN</units><package>IO</package></units>");
        a.append("<units><unitsIen>8</unitsIen><units>ML</units><package>IO</package></units>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>4</dosageFormIen><name>CHRISCAP,SA</name>");
        a.append("<units><unitsIen>1</unitsIen><units>MEQ</units><package>IO</package></units>");
        a.append("<units><unitsIen>2</unitsIen><units>MG</units><package>IO</package></units>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>5</dosageFormIen><name>CHRISCREAM</name>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>6</dosageFormIen><name>CHRISCRYSTAL</name>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>7</dosageFormIen><name>CHRISDENTAL CONE</name>");
        a.append("<units><unitsIen>1</unitsIen><units>MG</units><package>IO</package></units>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>8</dosageFormIen><name>CHRISDIAPHRAGM</name>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>9</dosageFormIen><name>CHRISDOUCHE</name>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>10</dosageFormIen><name>CHRISDRESSING</name>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>11</dosageFormIen><name>CHRISDRESSING</name>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>12</dosageFormIen><name>CHRISDRESSINGCHRISDRESSINGCHRIS3DRESSINGCH" 
                 + "RIS3DRESSINGCHRIS3DRESSINGCHRIS3DRESSINGCHRIS3DRESSINGCHRIS3DRESSINGCHRIS3DRESSINGCHRIS3DRESSINGC" 
                 + "HRIS3DRESSING</name>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>13</dosageFormIen><name>InvalidDrugUnit</name>");
        a.append("<units><unitsIen>1</unitsIen><units>NOTADRUGUNIT</units><package>IO</package></units>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createDosageFormInActive
     * 
     * @return xml
     */
    private String createDosageFormInActive() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<dosageForm><dosageFormIen>1</dosageFormIen><name>CAP,EC12</name>"
                + "<units><unitsIen>1</unitsIen><units>MG</units><package>I</package></units>"
                + "<units><unitsIen>1</unitsIen><units>EA</units><package>O</package></units>"
                + "<units><unitsIen>1</unitsIen><units>GAL</units><package>IO</package></units>"
                + "<units><unitsIen>1</unitsIen><units>LB</units><package>OI</package></units>"
                + "<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose><package>I</package></dispenseUnitsPerDose>"
                + "<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose><package>O</package></dispenseUnitsPerDose>"
                + "<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>3</dispenseUnitsPerDoseIen>" 
                + "<dispenseUnitsPerDose>3</dispenseUnitsPerDose><package>IO</package></dispenseUnitsPerDose>"
                + "<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>4</dispenseUnitsPerDoseIen>" 
                + "<dispenseUnitsPerDose>4</dispenseUnitsPerDose><package>OI</package></dispenseUnitsPerDose>"
                + "<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>9</dosageFormIen><name>CCDISK</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>12</dosageFormIen><name>CCELIXER</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>15</dosageFormIen><name>CCEXTRACT</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>23</dosageFormIen><name>CCINHALATIONS</name" 
                 + "><inactivationDate>20051014</inactivationDate>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>32</dosageFormIen><name>CCLENS,HARD</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>33</dosageFormIen><name>CCLENS,SOFT</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>38</dosageFormIen><name>CCMAGMA</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>39</dosageFormIen><name>CCMILK</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>43</dosageFormIen><name>CCOINTMENT</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>47</dosageFormIen><name>CCPILL</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>48</dosageFormIen><name>CCPOULTICE</name>" 
                 + "<inactivationDate>20000630</inactivationDate>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>60</dosageFormIen><name>CCSUTURE</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<excludeFromDosageChecks>1</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>62</dosageFormIen><name>CCSYRUP,SR</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("<dosageForm><dosageFormIen>62</dosageFormIen><name>CCSYRUP,SR</name>" 
                 + "<inactivationDate>20051014</inactivationDate>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>1</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>1</dispenseUnitsPerDose>");
        a.append("<package>I</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>2</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose>");
        a.append("<package>O</package></dispenseUnitsPerDose>");
        a.append("<dispenseUnitsPerDose><dispenseUnitsPerDoseIen>3</dispenseUnitsPerDoseIen>" 
                 + "<dispenseUnitsPerDose>2</dispenseUnitsPerDose>");
        a.append("<package>IO</package></dispenseUnitsPerDose>");
        a.append("<excludeFromDosageChecks>0</excludeFromDosageChecks></dosageForm>");

        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createProductActive
     * 
     * @return String
     */
    private String createProductActive() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>1342</ndfProductIen><name>LISINOPRIL 50MG TAB</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>10</strength><units>MMO</units>" 
                 + "<nationalFormularyName>LISINOPRIL 50MG TAB</nationalFormularyName>");
        a.append("<vaPrintName>LISINOPRIL 50MG TAB</vaPrintName><vaProductIdentifier>L0077</vaProductIdentifier>");
        a.append("<transmitToCmop>0</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>14</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>M</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120003</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT3.pdf</fdaMedGuide><serviceCode>12</serviceCode>");
        a.append("</vaProduct>");
        
        
        a.append("<vaProduct><ndfProductIen>3</ndfProductIen><name><![CDATA[ATROPINE SO4 1% XOINT,OPH]]></name>" 
                 + "<vaGenericName><![CDATA[ATROPINE]]></vaGenericName><dosageForm>OINT,OPH</dosageForm>" 
                 + "<nationalFormularyName><![CDATA[ATROPINE OINT,OPH]]></nationalFormularyName>" 
                 + "<vaPrintName><![CDATA[ATROPINE SULFATE 1% OPH OINT]]></vaPrintName>" 
                 + "<transmitToCmop>1</transmitToCmop>" 
                 + "<vaDispenseUnit>TUBE</vaDispenseUnit><gcnSeqNo>007864</gcnSeqNo><activeIngredients>" 
                 + "<activeIngredientIen>1</activeIngredientIen>" 
                 + "<ingredientName><![CDATA[ATROPINE SULFATE]]></ingredientName>" 
                 + "<strength><![CDATA[1]]></strength><units>%</units></activeIngredients><primaryVaDrugClass>" 
                 + "<vaDrugClassIen>169</vaDrugClassIen><code>OP600</code>" 
                 + "<classification>MYDRIATICS/CYCLOPLEGICS,TOPICAL OPHTHALMIC</classification></primaryVaDrugClass>" 
                 + "<nationalFormularyIndicator>1</nationalFormularyIndicator>" 
                 + "<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<createPossibleDosage>Y</createPossibleDosage>" 
                 + "<masterEntryForVuid>1</masterEntryForVuid><vuid>4000626</vuid><effectiveDateTime>" 
                 + "<effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>" 
                 + "<serviceCode>600003</serviceCode></vaProduct>");
        
        a.append("<vaProduct><ndfProductIen>23423</ndfProductIen><name><![CDATA[CHRISATROPINE SO4 1% XOINT,OPH]]></name>" 
            + "<vaGenericName><![CDATA[ATROPINE]]></vaGenericName><dosageForm>OINT,OPH</dosageForm>" 
            + "<nationalFormularyName><![CDATA[CHRISATROPINE OINT,OPH]]></nationalFormularyName>" 
            + "<vaPrintName><![CDATA[CHRISATROPINE SULFATE 1% OPH OINT]]></vaPrintName>" 
            + "<transmitToCmop>1</transmitToCmop>" 
            + "<vaDispenseUnit>TUBE</vaDispenseUnit><gcnSeqNo>18</gcnSeqNo><activeIngredients>" 
            + "<activeIngredientIen>1</activeIngredientIen>" 
            + "<ingredientName><![CDATA[ATROPINE SULFATE]]></ingredientName>" 
            + "<strength><![CDATA[1]]></strength><units>%</units></activeIngredients><primaryVaDrugClass>" 
            + "<vaDrugClassIen>169</vaDrugClassIen><code>OP600</code>" 
            + "<classification>MYDRIATICS/CYCLOPLEGICS,TOPICAL OPHTHALMIC</classification></primaryVaDrugClass>" 
            + "<nationalFormularyIndicator>1</nationalFormularyIndicator>" 
            + "<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
            + "<createPossibleDosage>Y</createPossibleDosage>" 
            + "<masterEntryForVuid>0</masterEntryForVuid><vuid>40006226</vuid><effectiveDateTime>" 
            + "<effectiveDateTime>20240310</effectiveDateTime><status>1</status></effectiveDateTime>" 
            + "<serviceCode>600003</serviceCode></vaProduct>");

        a.append("<vaProduct><ndfProductIen>282</ndfProductIen><name><![CDATA[SECOBARBITAL NA 100MG CAP]]></name>" 
                 + "<vaGenericName><![CDATA[SECOBARBITAL]]></vaGenericName><dosageForm>CAP,ORAL</dosageForm>" 
                 + "<strength><![CDATA[100]]></strength><units>MG</units><nationalFormularyName>" 
                 + "<![CDATA[SECOBARBITAL CAP,ORAL]]></nationalFormularyName>" 
                 + "<gcnSeqNo>003630</gcnSeqNo><activeIngredients>" 
                 + "<activeIngredientIen>44</activeIngredientIen>" 
                 + "<ingredientName><![CDATA[SECOBARBITAL SODIUM]]></ingredientName>" 
                 + "<strength><![CDATA[100]]></strength><units>MG</units></activeIngredients><primaryVaDrugClass>" 
                 + "<vaDrugClassIen>77</vaDrugClassIen><code>CN301</code>" 
                 + "<classification>BARBITURIC ACID DERIVATIVE SEDATIVES/HYPNOTICS</classification></primaryVaDrugClass>" 
                 + "<nationalFormularyIndicator>0</nationalFormularyIndicator><csFederalSchedule>2n</csFederalSchedule>" 
                 + "<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion><createPossibleDosage>Y</createPossibleDosage>" 
                 + "<masterEntryForVuid>1</masterEntryForVuid><vuid>4000904</vuid><effectiveDateTime>" 
                 + "<effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>" 
                 + "<fdaMedGuide>Secobarbital_(Seconal)_(2008).pdf</fdaMedGuide><serviceCode>600282</serviceCode></vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>2</ndfProductIen><name>TESTVAPRODUCT743</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>25</strength><units>mg</units>" 
                 + "<nationalFormularyName>TESTVAPRODUCT74</nationalFormularyName>");
        a.append("<vaPrintName>TESTVAPRODUCT1</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>19</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen><ingredientName>ALCOHOL</ingredientName>" 
                 + "<strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>2</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4106</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT1.pdf</fdaMedGuide><serviceCode>9</serviceCode>");
        a.append("</vaProduct>");
        
        a.append("<vaProduct>");
        a.append("<ndfProductIen>2</ndfProductIen><name>TESTVAPRODUCT1</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>25</strength><units>mg</units>" 
                 + "<nationalFormularyName>TESTVAPRODUCT1</nationalFormularyName>");
        a.append("<vaPrintName>TESTVAPRODUCT1</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>4199</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>2</csFederalSchedule>");
        a.append("<inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4106</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT1.pdf</fdaMedGuide><serviceCode>9</serviceCode>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>6</ndfProductIen><name>NODISPENSEUNIT</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>25</strength><units>mg</units>" 
                 + "<nationalFormularyName>NODISPENSEUNIT</nationalFormularyName>");
        a.append("<vaPrintName>NODISPENSEUNIT</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><gcnSeqNo>1711</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>2</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120001</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/NODISPENSEUNIT.pdf</fdaMedGuide><serviceCode>9</serviceCode>"); 
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>17422</ndfProductIen>" 
                 + "<name>ASA 325MG/CITRIC ACID 1000MG/SODIUM BICARBONATE 1916MG TAB,EFFERVSC</name>" 
                 + "<vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>25</strength><units>mg</units>" 
                 + "<nationalFormularyName>ASPIRIN/CITRIC ACID/SODIUM BICARBONATE TAB,EFFERVSC</nationalFormularyName>");
        a.append("<vaPrintName>ASA 325/CITRIC ACID/NA BICARB TAB EFF</vaPrintName><" 
                 + "vaProductIdentifier>A1267</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>8159</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>2</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>N</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120001</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT1.pdf</fdaMedGuide><serviceCode>617422</serviceCode>" 
                 + "<possibleDosagesToCreate>N</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        
        a.append("<vaProduct>");
        a.append("<ndfProductIen>9</ndfProductIen><name>TESTVAPRODUCT1A</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>ENEMA</dosageForm><strength>25</strength><units>GM</units>" 
                 + "<nationalFormularyName>TESTVAPRODUCT1A</nationalFormularyName>");
        a.append("<vaPrintName>TESTVAPRODUCT1A</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>3699</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>XA200</code>" 
                 + "<classification>TAPE</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>0</nationalFormularyIndicator><csFederalSchedule>4</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>N</createPossibleDosage><productPackage>O</productPackage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120001</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT1A.pdf</fdaMedGuide><serviceCode>9</serviceCode>" 
                 + "<possibleDosagesToCreate>N</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>9</ndfProductIen><name>TESTVAPRODUCT1B</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>25</strength><units>15ML</units>" 
                 + "<nationalFormularyName>TESTVAPRODUCT1B</nationalFormularyName>");
        a.append("<vaPrintName>TESTVAPRODUCT1B</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1192</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>5</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>N</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120001</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT1B.pdf</fdaMedGuide><serviceCode>9</serviceCode>" 
                 + "<possibleDosagesToCreate>N</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>11</ndfProductIen><name>ATP PRODUCT TEST</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>456</strength><units>ml</units>" 
                 + "<nationalFormularyName>TESTVAPRODUCT2</nationalFormularyName>");
        a.append("<vaPrintName>TESTVAPRODUCT2</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>0</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1193</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>11</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>2N</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>0</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120002</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT2.pdf</fdaMedGuide><serviceCode>11</serviceCode>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>12</ndfProductIen><name>TESTVAPRODUCT3</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName>TESTVAPRODUCT3</nationalFormularyName>");
        a.append("<vaPrintName>TESTVAPRODUCT3</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>0</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1773</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>M</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>N</createPossibleDosage><productPackage>I</productPackage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120003</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT3.pdf</fdaMedGuide><serviceCode>12</serviceCode>" 
                 + "<possibleDosagesToCreate>O</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>12</ndfProductIen><name>LISINOPRIL 10MG TAB</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName>DUPLICATE</nationalFormularyName>");
        a.append("<vaPrintName>DUPLICATE</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>0</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1774</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>M</singleMultiSourceProduct><inactivationDate></inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120003</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/DUPLICATE.pdf</fdaMedGuide><serviceCode>12</serviceCode>");
        a.append("</vaProduct>");

      

        a.append("<vaProduct>");
        a.append("<ndfProductIen>1342</ndfProductIen><name>PSEUDOEPHEDRINE HCL 70MG TAB</name>" 
                 + "<vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>60</strength><units>GM/TAB</units>" 
                 + "<nationalFormularyName>PSEUDOEPHEDRINE HCL 70MG TAB</nationalFormularyName>");
        a.append("<vaPrintName>PSEUDOEPHEDRINE HCL 70MG TAB</vaPrintName>");
        a.append("<transmitToCmop>0</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code" 
                 + "><classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>M</singleMultiSourceProduct>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>N</excludeDrugDrugInteraction>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>120003</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/TESTVAPRODUCT3.pdf</fdaMedGuide><serviceCode>12</serviceCode>" 
                 + "<possibleDosagesToCreate>N</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>4323</ndfProductIen><name>ACETAMINOPHEN 523MG TAB</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><strength>523</strength>" 
                 + "<nationalFormularyName>ACETAMINOPHEN 523MG</nationalFormularyName>");
        a.append("<vaPrintName>ACETAMINOPHEN 523MG</vaPrintName>");
        a.append("<transmitToCmop>0</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen><ingredientName>ALCOHOL</ingredientName>" 
                 + "<strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>M</singleMultiSourceProduct>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>Y</excludeDrugDrugInteraction>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>4323</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/ACETAMINOPHEN 523MG.pdf</fdaMedGuide><serviceCode>60023</serviceCode>" 
                 + "<possibleDosagesToCreate>N</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>45</ndfProductIen><name>INTESTVAPRODUCT1</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName>INTESTVAPRODUCT1</nationalFormularyName>");
        a.append("<vaPrintName>INTESTVAPRODUCT1</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1234</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
            
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct><inactivationDate>20110831</inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>N</createPossibleDosage><productPackage>I</productPackage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>1234</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20070310</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/INTESTVAPRODUCT1.pdf</fdaMedGuide><serviceCode>1234</serviceCode>" 
                 + "<possibleDosagesToCreate>I</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>46</ndfProductIen><name>INTESTVAPRODUCT2</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName>asdfsa</nationalFormularyName>");
        a.append("<vaPrintName>INTESTVAPRODUCT2</vaPrintName><vaProductIdentifier></vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1234</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>0</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct>");
        a.append("<inactivationDate>20110901</inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        
        a.append("<createPossibleDosage>N</createPossibleDosage><productPackage>I</productPackage>");
        
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>1234</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20070310</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/INTESTVAPRODUCT2.pdf</fdaMedGuide><serviceCode>1234</serviceCode>" 
                 + "<possibleDosagesToCreate>O</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>46</ndfProductIen><name>INTESTVAPRODUCT3</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName>asdf</nationalFormularyName>");
        a.append("<vaPrintName>INTESTVAPRODUCT3</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1234</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen><ingredientName>ALCOHOL</ingredientName>" 
                 + "<strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>0</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct>");
        a.append("<inactivationDate>20110902</inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>");
        a.append("<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>123234</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20070310</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/INTESTVAPRODUCT3.pdf</fdaMedGuide>");
        a.append("<serviceCode>1234</serviceCode>");
        a.append("</vaProduct>");
        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createProductInActive
     * 
     * @return xml
     */
    private String createProductInActive() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<drugMigrationResponse ");
        a.append(XML1);
        a.append(XML2);
        a.append("<responseHeader><endOfFile>1</endOfFile></responseHeader>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>45</ndfProductIen><name>INTESTVAPRODUCT1</name><vaGenericName></vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName>INTESTVAPRODUCT1</nationalFormularyName>");
        a.append("<vaPrintName>INTESTVAPRODUCT1</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1234</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>1</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct>");
        a.append("<inactivationDate>20110831</inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>N</createPossibleDosage><productPackage>I</productPackage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>1234</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20070310</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/INTESTVAPRODUCT1.pdf</fdaMedGuide><serviceCode>1234</serviceCode>" 
                 + "<possibleDosagesToCreate>I</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>46</ndfProductIen><name>INTESTVAPRODUCT2</name><vaGenericName></vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName></nationalFormularyName>");
        a.append("<vaPrintName>INTESTVAPRODUCT2</vaPrintName><vaProductIdentifier></vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1234</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen>" 
                 + "<ingredientName>ALCOHOL</ingredientName><strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>0</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct>");
        a.append("<inactivationDate>20110831</inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>" 
                 + "<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>N</createPossibleDosage><productPackage>I</productPackage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>1234</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20070310</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/INTESTVAPRODUCT2.pdf</fdaMedGuide><serviceCode>1234</serviceCode>" 
                 + "<possibleDosagesToCreate>O</possibleDosagesToCreate>");
        a.append("</vaProduct>");

        a.append("<vaProduct>");
        a.append("<ndfProductIen>46</ndfProductIen><name>INTESTVAPRODUCT3</name><vaGenericName>OPIUM</vaGenericName>");
        a.append("<dosageForm>TAB</dosageForm><nationalFormularyName></nationalFormularyName>");
        a.append("<vaPrintName>INTESTVAPRODUCT3</vaPrintName><vaProductIdentifier>T0032</vaProductIdentifier>");
        a.append("<transmitToCmop>1</transmitToCmop><vaDispenseUnit>EA</vaDispenseUnit><gcnSeqNo>1234</gcnSeqNo>");
        a.append("<activeIngredients><activeIngredientIen>12</activeIngredientIen><ingredientName>ALCOHOL</ingredientName>" 
                 + "<strength>12</strength><units>MG</units></activeIngredients>");
        a.append("<primaryVaDrugClass><vaDrugClassIen>1</vaDrugClassIen><code>AD000</code>" 
                 + "<classification>ANTIDOTES,DETERRENTS AND POISON CONTROL</classification></primaryVaDrugClass>");
        a.append("<nationalFormularyIndicator>0</nationalFormularyIndicator><csFederalSchedule>1</csFederalSchedule>");
        a.append("<singleMultiSourceProduct>S</singleMultiSourceProduct>");
        a.append("<inactivationDate>20110831</inactivationDate>");
        a.append("<overrideDfDoseChkExclusion>1</overrideDfDoseChkExclusion>");
        a.append("<excludeDrugDrugInteraction>0</excludeDrugDrugInteraction>");
        a.append("<createPossibleDosage>Y</createPossibleDosage>");
        a.append("<masterEntryForVuid>1</masterEntryForVuid><vuid>1234</vuid>");
        a.append("<effectiveDateTime><effectiveDateTime>20050310</effectiveDateTime><status>1</status></effectiveDateTime>");
        a.append("<effectiveDateTime><effectiveDateTime>20070310</effectiveDateTime><status>0</status></effectiveDateTime>");
        a.append("<fdaMedGuide>medguide/INTESTVAPRODUCT3.pdf</fdaMedGuide>");
        a.append("<serviceCode>1234</serviceCode>");
        a.append("</vaProduct>");
        a.append("</drugMigrationResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createManufacturerSyncResponse
     * 
     * @return xml
     */
    public String createManufacturerSyncResponse() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<manufacturerMigrationSynchResponse ");
        a.append("xmlns=\"gov/va/med/pharmacy/peps/external/common/vo/inbound/migration/manufacturer/response\" ");
        a.append(XML2);
        a.append("<responseType><status>Success</status></responseType><manufacturerIen>90123</manufacturerIen>");

        a.append("</manufacturerMigrationSynchResponse>]]></Response>");

        return a.toString();
    }

    /**
     * createPackageTypeSyncResponse
     * 
     * @return xml
     */
    public String createPackageTypeSyncResponse() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<packageTypeMigrationSynchResponse ");
        a.append("xmlns=\"gov/va/med/pharmacy/peps/external/common/vo/inbound/migration/packageType/response\" ");
        a.append(XML2);
        a.append("<responseType><status>Success</status></responseType><packageTypeIen>90123</packageTypeIen>");

        a.append("</packageTypeMigrationSynchResponse>]]></Response>");

        return a.toString();
    }

    /**
     * getMigrSyncResponse
     * 
     * @param request request string
     * 
     * @return xml
     */
    public String getMigrSyncResponse(String request) {

        if (request.contains(MANUFACTURERSTARTTAG) && request.contains(MANUFACTURERSTOPTAG)) {
            return this.createManufacturerSyncResponse();
        }

        if (request.contains(PACKAGETYPESTARTTAG) && request.contains(PACKAGETYPESTOPTAG)) {
            return this.createPackageTypeSyncResponse();
        } else {

            StringBuilder a = new StringBuilder();
            a.append("<Response type=\"array\"><![CDATA[");
            a.append("<ndcMigrationSynchResponse ");
            a.append("xmlns=\"gov/va/med/pharmacy/peps/external/common/vo/inbound/migration/ndc/response\" ");
            a.append(XML2);
            a.append("<responseType><status>Success</status></responseType><ndcIen>90123</ndcIen>");

            a.append("</ndcMigrationSynchResponse>]]></Response>");

            return a.toString();
        }
    }
        
    /**
     * getSyncXML
     * 
     * @param request
     *            request
     * @return String
     */
    public String getSyncXML(String request) {

        if (request.contains(DRUGUNITSYNC)) {
            return createSyncXML();
        } else if (request.contains(VADISPENSEUNITSYNC)) {
            return createSyncXML();
        } else if (request.contains(VAGENERICNAMESYNC)) {
            return createSyncXML();
        } else if (request.contains(DOSAGEFORMSYNC)) {
            return createSyncXML();
        } else if (request.contains(DRUGCLASSSYNC)) {
            return createSyncXML();
        } else if (request.contains(DRUGINGREDIENTSSYNC)) {
            return createSyncXML();
        } else if (request.contains(PRODUCTSYNC)) {
            return createSyncXML();
        } else if (request.contains(NDCSYNC)) {
            return createSyncXML();
        } else if (request.contains(MANUFACTURERSYNC)) {
            return createSyncXML();
        } else if (request.contains(PACKAGETYPESYNC)) {
            return createSyncXML();
        } else {
            
            return "";
        }
    }

    /**
     * createSyncXML
     * 
     * @return xml
     */
    public String createSyncXML() {

        StringBuilder a = new StringBuilder();
        a.append("<Response type=\"array\"><![CDATA[");
        a.append("<syncResponse ");
        a.append("xmlns=\"gov/va/med/pharmacy/peps/external/common/vo/inbound/sync/syncResponse\" ");
        a.append(XML2);
        a.append("<syncResponseType><status>Success</status></syncResponseType><ien>90123</ien>");
        a.append("</syncResponse>]]></Response>");

        return a.toString();
    }


    
}
