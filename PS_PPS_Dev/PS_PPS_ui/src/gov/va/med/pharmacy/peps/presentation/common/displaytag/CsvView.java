/**
 * Source file created in 2011 by Southwest Research Institute
 * Modified from original to meet javadoc programming standards and use
 * Log4J instead of printstacktrace.
 * 
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * 
 */


package gov.va.med.pharmacy.peps.presentation.common.displaytag;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.displaytag.model.TableModel;


/**
 * Export view for comma separated value exporting.
 */
public class CsvView extends BaseExportView {

    private static final Logger LOG = Logger.getLogger(CsvView.class);
    
    /**
     * setParameters is used to set the parameters
     * @param tableModel The table model
     * @param exportFullList True if exporting the entire list
     * @param includeHeader True if including the header
     * @param decorateValues True if decorating the values
     * @see org.displaytag.export.BaseExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader,
        boolean decorateValues) {
        super.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
    }

    /**
     * getRowEnd returns the hex 0A characters.
     * @return "/n"
     * @see org.displaytag.export.BaseExportView#getRowEnd()
     */
    protected String getRowEnd() {
        return "\n"; //$NON-NLS-1$
    }

    /**
     * getCellEnd returns the last character of the cell.
     * @return "^"
     * @see org.displaytag.export.BaseExportView#getCellEnd()
     */
    protected String getCellEnd() {
        return "^"; //$NON-NLS-1$
    }

    /**
     * getAlwaysAppendCellEnd
     * @return false
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
     */
    protected boolean getAlwaysAppendCellEnd() {
        return false;
    }

    /**
     * getAlwaysAppendRowEnd
     * @return true
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
     */
    protected boolean getAlwaysAppendRowEnd() {
        return true;
    }

    /**
     * getMimeType
     * @return "text/csv"
     * @see org.displaytag.export.ExportView#getMimeType()
     */
    public String getMimeType() {
        return "text/csv"; //$NON-NLS-1$
    }

    /**
     * Escaping for csv format.
     * <ul>
     * <li>Quotes inside quoted strings are escaped with a /</li>
     * <li>Fields containings newlines or , are surrounded by "</li>
     * </ul>
     * Note this is the standard CVS format and it's not handled well by excel.
     * @param value the value 
     * @return The appropriate escape value
     * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    protected String escapeColumnValue(Object value) {
        String stringValue = StringUtils.trim(value.toString());
        String cleanStringValue = "";

//        String cleanStringValue = stringValue.replaceAll("\\<.*?>", "");

        try {
            cleanStringValue = extractText(stringValue);
        } catch (IOException e) {
            LOG.debug(e.getMessage());
        }

        if (!StringUtils.containsNone(cleanStringValue, new char[] { '\n', ',' })) {
            return "\"" + //$NON-NLS-1$
                StringUtils.replace(cleanStringValue, "\"", "\\\"") + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        return cleanStringValue;
    }

    /**
     * Parse HTML to String
     * @param html Input String
     * @return text String value
     * @throws IOException IOException
     */
    String extractText(String html) throws IOException {
        final ArrayList<String> list = new ArrayList<String>();

        ParserDelegator parserDelegator = new ParserDelegator();
        ParserCallback parserCallback = new ParserCallback() {

            public void handleText(final char[] data, final int pos) {
                list.add(new String(data));
            }

            public void handleEndTag(Tag t, final int pos) {
            }

            public void handleComment(final char[] data, final int pos) {
            }

            public void handleError(final java.lang.String errMsg, final int pos) {
            }
        };
        parserDelegator.parse(new StringReader(html), parserCallback, true);

        String text = "";

        for (String s : list) {
            text += " " + s;
        }

        return text;
    }

}
