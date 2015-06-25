/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;


/**
 *  The CsvFile class contains code common for processing caret separated value
 *  (CSV)files. Note that the separator may be changed using constants below.
 *  The subclasses that extend this superclass are:
 *  1. NdcCsvFile - import and export the NDC text file
 *  2. OiCsvFile -  import and export the Orderable Item text file
 *  3. ProductCsvFile - import and export the Product text file
 *  
 *  Methods defined in this superclass for use by the Migration service are:
 *  1. closeImport - close import file
 *  2. closeExport - close export file
 *  
 *  Methods defined in this superclass for use by the subclasses are:
 *  1. openAndReadHeader - open import file and return column headers
 *  2. getNextRow - get next row from file, returns fields in a string array
 *  3. openAndWriteHeader - open export and write header line
 *  4. putNextRow - put next row to file, input is field string array
 */
public class CsvFile {

    // separator between row fields
    // second constant required because string split method would not work "^" string

    /** ROW_FIELD_SEPARATOR */
    public static final String ROW_FIELD_SEPARATOR = "^";

    /** ROW_FIELD_SEPARATOR_SPLIT */
    public static final String ROW_FIELD_SEPARATOR_SPLIT = "\\^";

    // separator between multiples in a field

    /** FIELD_MULTIPLE_SEPARATOR */
    public static final String FIELD_MULTIPLE_SEPARATOR = "||";

    /** FIELD_MULTIPLE_SEPARATOR_SPLIT */
    public static final String FIELD_MULTIPLE_SEPARATOR_SPLIT = "\\|\\|";

    // separator between values in a multiple

    /** MULTIPLE_VALUE_SEPARATOR */
    public static final String MULTIPLE_VALUE_SEPARATOR = "::";

    /** MULTIPLE_VALUE_SEPARATOR_SPLIT */
    public static final String MULTIPLE_VALUE_SEPARATOR_SPLIT = "\\:\\:";

    // common messages for exceptions

    /** DATE_ERROR_MESSAGE */
    public static final String DATE_ERROR_MESSAGE = "Date parsing error, expected YYYYMMDD";

    /** BOOLEAN_ERROR_MESSAGE */
    public static final String BOOLEAN_ERROR_MESSAGE = "Not a valid boolean value, expected Y,N";

    /** NUMBER_ERROR_MESSAGE */
    public static final String NUMBER_ERROR_MESSAGE = "Number parsing error";

    /** LIST_ERROR_MESSAGE */
    public static final String LIST_ERROR_MESSAGE = "Not a valid list item";

    /** MULTIPLE_ERROR_MESSAGE */
    public static final String MULTIPLE_ERROR_MESSAGE = "Invalid number of multiple values";

    /** ROW_ERROR_MESSAGE */
    public static final String ROW_ERROR_MESSAGE = "Number of row field values is less than minimum";

    // file handles

    /** importFile */
    protected BufferedReader importFile = null;

    /** exportFile */
    protected BufferedWriter exportFile = null;

    // keeps track of the row number for exceptions

    /** fileRowNumber */
    protected int fileRowNumber = 0;

    // format for all dates

    /** df */
    protected DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);

    /**
     * superclass constructor method
     */
    public CsvFile() {

        // disable lenient parsing of dates
        df.setLenient(false);

    }

    /**
     * superclass method to open an existing import file and read header row
     * designed for use by subclass only
     * @param filename filename
     * @return String Array
     * @throws MigrationException exception
     */
    public String[] openAndReadHeader(String filename) throws MigrationException {

        try {

            //open file, it must exist
            importFile = new BufferedReader(new FileReader(filename));

            // read the header line (should be first row in file)
            String ndcRow = importFile.readLine();

            // create string array with column headings
            String[] fieldArray = ndcRow.split(ROW_FIELD_SEPARATOR_SPLIT);
            fileRowNumber = 0;

            return fieldArray;
        } catch (FileNotFoundException e) {
            throw new MigrationException("File  " + filename + " not found. ");
        } catch (IOException e) {
            throw new MigrationException("IOExeption  reading header line. ");
        }
    }

    /**
     * This method returns the total total amount of rows in a file
     * @param pInputStream pInputStream
     * @return long
     * @throws MigrationException 
     */
    public long getTotalRowsInfile(InputStream pInputStream) throws MigrationException {
        LineNumberReader lnr;

        try {
            lnr = new LineNumberReader(new InputStreamReader(pInputStream));
            lnr.skip(Long.MAX_VALUE);
            long lineCount = lnr.getLineNumber();
            lnr.close();

            return lineCount;

        } catch (FileNotFoundException e) {
            throw new MigrationException("getTotalRowsInfile==>>File not found.");
        } catch (IOException e) {
            throw new MigrationException("IOExeption: getTotalRowsInfile==>> reading rows in file.");
        }

    }

    /**
     * openAndReadHeader
     * @param pInputStream pInputStream
     * @return String Array
     * @throws MigrationException 
     */
    public String[] openAndReadHeader(InputStream pInputStream) throws MigrationException {

        try {

            //DataInputStream in = new DataInputStream(pInputStream);
            importFile = new BufferedReader(new InputStreamReader(pInputStream));

            // read the header line (should be first row in file)
            String ndcRow = importFile.readLine();

            // create string array with column headings
            String[] fieldArray = ndcRow.split(ROW_FIELD_SEPARATOR_SPLIT);

            fileRowNumber = 0;

            return fieldArray;
        } catch (FileNotFoundException e) {
            throw new MigrationException("File  + filename +  not found.");
        } catch (IOException e) {
            throw new MigrationException("IOExeption reading header line. ");
        }
    }

    /**
     * openAndReadHeader
     * @param pFile pFile
     * @return String[]
     * @throws MigrationException 
     */
    public String[] openAndReadHeader(File pFile) throws MigrationException {
        try {

            //open file, it must exist
            importFile = new BufferedReader(new FileReader(pFile));

            // read the header line (should be first row in file)
            String ndcRow = importFile.readLine();

            // create string array with column headings
            String[] fieldArray = ndcRow.split(ROW_FIELD_SEPARATOR_SPLIT);

            fileRowNumber = 0;

            return fieldArray;
        } catch (FileNotFoundException e) {
            throw new MigrationException("File " + pFile.getName() + " not found.");
        } catch (IOException e) {
            throw new MigrationException("IOExeption reading header line.");
        }
    }

    /**
     * superclass method to read the next data row/line from the import file
     * designed for use by subclass only
     * @param numberOfFields number of fields
     * @return String array
     * @throws MigrationException 
     */
    public String[] getNextRow(int numberOfFields) throws MigrationException {
        String row;

        try {

            // Read the next line
            row = importFile.readLine();

            if (row == null) {
                return null;
            }

            // create string array with field data
            // if the last value in the string is null split does not return a null string (bug)
            // so add to null strings to array to complete
            String[] rowFields = StringUtils.splitPreserveAllTokens(row, ROW_FIELD_SEPARATOR_SPLIT);

            // increment row number
            fileRowNumber = fileRowNumber + 1;

            return rowFields;
        } catch (IOException e) {

            // assume end of file if any error occurs duringreadLine
            return null;
        }
    }

    /**
     * superclass method to close the import file
     * designed for use by Migration service
     * @throws MigrationException 
     */
    public void closeImport() throws MigrationException {
        try {
            importFile.close();
        } catch (IOException e) {
            throw new MigrationException("IOException " + e.getMessage());
        }
    }

    /**
     * superclass method to open a file for export and the write header row
     * designed for use by subclass only
     * @param filename filename
     * @param columnNames columnNames
     * @throws MigrationException 
     */
    public void openAndWriteHeader(String filename, String[] columnNames) throws MigrationException {

        String row = "";

        try {

            //open file for write (will create a new file or overwrite existing file)
            exportFile = new BufferedWriter(new FileWriter(filename));

            // write the header line (should be first row in file)
            for (int i = 0; i < columnNames.length; i++) {
                row = row + columnNames[i];

                //don't add separator after last field
                if (i < columnNames.length - 1) {
                    row = row + ROW_FIELD_SEPARATOR;
                }
            }

            exportFile.write(row);
            exportFile.newLine();
        } catch (IOException e) {
            throw new MigrationException("IO Exception writing column header line.");
        }
    }

    /**
     * superclass method to write the next data row
     * designed for use by subclass only
     * @param dataFields This is a data field.
     * @throws MigrationException MigrationException
     */
    public void putNextRow(String[] dataFields) throws MigrationException {

        String row = "";

        // add line to export file from item data fields
        for (int i = 0; i < dataFields.length; i++) {
            row = row + dataFields[i];

            //don't add separator after last field
            if (i < dataFields.length - 1) {
                row = row + ROW_FIELD_SEPARATOR;
            }
        }

        try {
            exportFile.write(row);
            exportFile.newLine();
        } catch (IOException e) {
            throw new MigrationException("IO exception writing row.");
        }

    }

    /**
     * superclass method to close the export file
     * designed for use by Migration service
     */
    public void closeExport() {
        try {
            exportFile.flush();
            exportFile.close();
        } catch (IOException e) {
            exportFile = null;
        }
    }

    /**
     * toFieldArray
     *
     * @param x DataField<Boolean>
     * @param fieldArray String[]
     * @param key int
     */
    protected void toFieldArrayBoolean(DataField<Boolean> x, String[] fieldArray, int key) {
        if (x.getValue() == Boolean.TRUE) {
            fieldArray[key] = "Y";
        } else {
            fieldArray[key] = "N";
        }
    }

    /**
     * fromFieldArray
     *
     * @param x DataField<Boolean>
     * @param fieldArray String[]
     * @param key int
     * @param name int
     * @param fieldNames String[]
     * @throws MigrationException exception
     */
    protected void fromFieldArrayBoolean(DataField<Boolean> x, String[] fieldArray, int key, int name, String[] fieldNames)
        throws MigrationException {
        if ("Y".equals(fieldArray[key])) {
            x.selectValue(true);
        } else if ("N".equals(fieldArray[key])) {
            x.selectValue(false);
        } else {
            throw new MigrationException(BOOLEAN_ERROR_MESSAGE, Integer.toString(fileRowNumber), fieldArray[name],
                fieldNames[key], fieldArray[key]);
        }
    }

    /**
     * fromFieldArrayString
     *
     * @param x DataField<String>
     * @param vo ManagedItemVo
     * @param fieldArray String[]
     * @param key int
     * @param select int
     */
    protected void fromFieldArrayString(DataField<String> x, ManagedItemVo vo, String[] fieldArray, int key, int select) {
        if (!fieldArray[key].isEmpty()) {
            x.selectValue(fieldArray[select]);
            vo.getVaDataFields().setDataField(x);
        }
    }

    /**
     * toFieldArrayNumber
     *
     * @param x DataField<? extends Number>
     * @param fieldArray String[]
     * @param key int
     */
    protected void toFieldArrayNumber(DataField<? extends Number> x, String[] fieldArray, int key) {
        if (x != null && x.getValue() != null) {
            fieldArray[key] = x.getValue().toString();
        }
    }

    /**
     * toFieldArrayString
     *
     * @param x DataField<String>
     * @param fieldArray  String[]
     * @param key int
     */
    protected void toFieldArrayString(DataField<String> x, String[] fieldArray, int key) {
        if (x != null && x.getValue() != null) {
            fieldArray[key] = x.getValue();
        }
    }

    /**
     * concatString
     *
     * @param src Object
     * @param dest String
     * @param altSrc String
     * @return String
     */
    protected String conStr(Object src, String dest, String altSrc) {
        String rv = null;

        if (src == null) {
            rv = dest; //dest.concat("");
        } else {
            if (src instanceof String && altSrc == null) {
                rv = dest.concat(src.toString());
            } else {
                rv = dest.concat(altSrc);
            }
        }

        return rv;
    }
}
