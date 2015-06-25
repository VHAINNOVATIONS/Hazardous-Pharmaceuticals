/**
 * Source file created in 2011 by Southwest Research Institute
 *
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://displaytag.sourceforge.net/license.html
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */


package gov.va.med.pharmacy.peps.presentation.common.displaytag;


import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.displaytag.Messages;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.export.BinaryExportView;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;


/**
 * Excel exporter using POI.
 */
public class DefaultHssfExportView implements BinaryExportView {
    private static final Logger LOG = Logger.getLogger(DefaultHssfExportView.class);
    
    private TableModel model;

    private HSSFSheet sheet;

    private boolean exportFull;

    private boolean decorated;

    /**
     * doExport creates excel file for download
     * @param out OutputStream
     * @see org.displaytag.export.BinaryExportView#doExport(java.io.OutputStream)
     * @throws IOException IOException
     * @throws JspException JspException
     */
    public void doExport(OutputStream out) throws IOException, JspException {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            sheet = wb.createSheet("Table_Export");

            int rowNum = 0;
            int colNum = 0;

            //Create a header row
            HSSFRow xlsRow = sheet.createRow(rowNum++);

            HSSFCellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
            headerStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
            HSSFFont bold = wb.createFont();
            bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            bold.setColor(HSSFColor.WHITE.index);
            headerStyle.setFont(bold);

            Iterator iterator = this.model.getHeaderCellList().iterator();

            while (iterator.hasNext()) {
                HeaderCell headerCell = (HeaderCell) iterator.next();

                String columnHeader = headerCell.getTitle();

                if (columnHeader == null) {
                    columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
                }

                HSSFCell cell = xlsRow.createCell(colNum++);
                cell.setCellValue(columnHeader);
                cell.setCellStyle(headerStyle);
            }

            RowIterator rowIterator = this.model.getRowIterator(this.exportFull);

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                xlsRow = sheet.createRow(rowNum++);
                colNum = 0;

                // iterate on columns
                ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

                while (columnIterator.hasNext()) {
                    Column column = columnIterator.nextColumn();

                    Object value = column.getValue(this.decorated);

                    HSSFCell cell = xlsRow.createCell(colNum++);

                    if (value instanceof Number) {
                        Number num = (Number) value;
                        cell.setCellValue(num.doubleValue());
                    } else if (value instanceof Date) {
                        cell.setCellValue((Date) value);
                    } else if (value instanceof Calendar) {
                        cell.setCellValue((Calendar) value);
                    } else {
                        cell.setCellValue(escapeColumnValue(value));
                    }

                }
            }

            wb.write(out);

//            new HssfTableWriter(wb).writeTable(this.model, "-1");            
//            wb.write(out);
        } catch (Exception e) {
            throw new HssfGenerationException(e);
        }
    }

    /**
     * escapeColumnValue
     * @param rawValue unmodified text
     * @return String with html/xml removed
     */
    protected String escapeColumnValue(Object rawValue) {

        if (rawValue == null) {
            return null;
        }

        String returnString = ObjectUtils.toString(rawValue);

        // Extract text
        try {
            returnString = extractText(returnString);
        } catch (IOException e) {
            LOG.warn(e.getLocalizedMessage());
        }

        // escape the String to get the tabs, returns, newline explicit as \t \r \n
        returnString = StringEscapeUtils.escapeJava(StringUtils.trimToEmpty(returnString));

        // remove tabs, insert four whitespaces instead
        returnString = StringUtils.replace(StringUtils.trim(returnString), "\\t", "    ");

        // remove the return, only newline valid in excel
        returnString = StringUtils.replace(StringUtils.trim(returnString), "\\r", " ");

        // unescape so that \n gets back to newline
        returnString = StringEscapeUtils.unescapeJava(returnString);

        return returnString;
    }

    /** 
     * setParameters
     * @param pModel TableModel
     * @param exportFullList boolean true if full list is exported
     * @param includeHeader This view will always export headers
     * @param decorateValues boolean if decorator is used
     * @see org.displaytag.export.ExportView#setParameters(org.displaytag.model.TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel pModel, boolean exportFullList, boolean includeHeader, boolean decorateValues) {
        this.model = pModel;
        this.exportFull = exportFullList;
        this.decorated = decorateValues;
    }

    /**
     * getMimeType sets excel file type
     * @see org.displaytag.export.BaseExportView#getMimeType()
     * @return "application/vnd.ms-excel"
     */
    public String getMimeType() {
        return "application/vnd.ms-excel"; //$NON-NLS-1$
    }

    /**
     * Wraps POI-generated exceptions.
     */
    static class HssfGenerationException extends BaseNestableJspTagException {

        /**
         * D1597A17A6.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Instantiate a new PdfGenerationException with a fixed message and the given cause.
         * @param cause Previous exception
         */
        public HssfGenerationException(Throwable cause) {
            super(DefaultHssfExportView.class, Messages.getString("DefaultHssfExportView.errorexporting"), cause); //$NON-NLS-1$
        }

        /**
         * SeverityEnum
         * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
         * @return SeverityEnum of error
         */
        public SeverityEnum getSeverity() {
            return SeverityEnum.ERROR;
        }
    }

    /**
     * Parse HTML to String
     * @param html Input String to convert
     * @return text String value that is returned
     * @throws IOException IOException
     */
    String extractText(String html) throws IOException {
        final ArrayList<String> list = new ArrayList<String>();

        // instantiate the instanced of the delegator and callback classes for parsing
        ParserDelegator parserDelegator = new ParserDelegator();
        ParserCallback parserCallback = new ParserCallback() {

            public void handleText(final char[] data, final int pos) {
                list.add(new String(data));
            }

            // handle the end tag
            public void handleEndTag(Tag t, final int pos) {
            }

            // handle comments
            public void handleComment(final char[] data, final int pos) {
            }

            // handle errors
            public void handleError(final java.lang.String errMsg, final int pos) {
            }
        };
        parserDelegator.parse(new StringReader(html), parserCallback, true);

        StringBuffer text = new StringBuffer("");

        // add a space as necessary
        for (String s : list) {
            text.append(" ").append(s);
        }

        return text.toString();
    }
}
