/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.io;


import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Provides the capability to read and write to the mumps data stream
 */
public class MumpsStream {
    
    /**
     * END_OF_TRANSMISSION
     */
    public static final char END_OF_TRANSMISSION = '\004';

    
  /**
  * default no-arg constructor
  */
    private MumpsStream() {
        super();
    }
    
    /**
     * Writes data to the mumps stream
     * 
     * @param out Stream to write data out to
     * @param data Data that should be sent across stream
     * @throws IOException thrown if an error occurred while sending data
     */
    public static void write(Writer out, String data) throws IOException {
        try {
            out.write(data);
            out.write(END_OF_TRANSMISSION);
        } finally {
            out.flush();
        }
    }

    /**
     * Reads data from the mumps stream
     * 
     * @param in Stream to read data in from
     * @return String Returns the data that was received from the stream
     * @throws IOException Thrown if there was an error while listening on the stream
     */
    public static String read(Reader in) throws IOException {
        StringBuffer response = new StringBuffer(PPSConstants.I512);
        char[] buffer = new char[PPSConstants.I512];

        for (int data = in.read(buffer); data > 0; data = in.read(buffer)) {
            if (buffer[data - 1] == END_OF_TRANSMISSION) {
                response.append(buffer, 0, data - 1);

                break;
            }

            response.append(buffer, 0, data);
        }

        return response.toString();
    }



}
