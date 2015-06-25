/**
 * Source file created in 2012 by Southwest Research Institute
 *
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */


package gov.va.med.pharmacy.peps.presentation.common.displaytag;


import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import org.displaytag.export.ExportView;


/**
 * Main interface for exportViews which need to output binary data.
 */
public interface BinaryExportView extends ExportView {

    /**
     * Returns the exported content as a String.
     * @param out output writer
     * @throws IOException for exceptions in accessing the output stream
     * @throws JspException for other exceptions during export
     */
    void doExport(OutputStream out) throws IOException, JspException;
}
