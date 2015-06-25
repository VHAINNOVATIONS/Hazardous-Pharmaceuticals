/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.crypto.VistaKernelHash;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymRuntimeException;


/**
 * class used to parse a string request into a request object
 */
public class Parser {
    private String text;
    private int index;
    private int defaultPadding;

    /**
     * The default constructor for the parser which sets the default padding to 5;
     */
    public Parser() {
        this.defaultPadding = PPSConstants.I5;
    }

    /**
     * Constructor used to set the string needing to be parsed
     * @param text string representing the incoming command to parse
     */
    public Parser(String text) {
        this();

        this.text = text;
    }

    /**
     * Method sets the string that needs to be parsed.
     * @param text value to set text to
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Method to convert the test string into a request object
     * @return parsed request object
     */
    public Request parse() {
        index = 0;

        Request request = new Request();

        next(2); //xob
        advance(); //debug
        readPadding(); //padding
        request.setVlv(next());
        next(); //rhv
        next(); //rpc
        request.setRpc(next());
        request.setRcx(VistaKernelHash.decrypt(next()));
        request.setRto(next());
        request.setSec(next());
        request.setDiv(next());
        request.setRas(next());

        if (!request.isAuthenticated()) {
            request.setSecParam(next());
        }

        for (int i = 0, pms = Integer.parseInt(next()); i < pms; i++) {
            String type = next();
            next(); //pos

            if (type.startsWith("array")) {
                List list = new ArrayList(PPSConstants.I3);

                for (int j = 0, its = Integer.parseInt(next()); j < its; j++) {
                    next(); //sub
                    list.add(next());
                }

                request.getParameters().add(new Request.ArrayParameter(list));
            } else if (type.startsWith("string")) {
                request.getParameters().add(new Request.StringParameter(next()));
            } else if (type.startsWith("ref")) {
                throw new UnsupportedOperationException("I don't know what a reference type is!");
            } else {
                throw new PseudonymRuntimeException("unknown type: " + type);
            }
        }

        return request;
    }

    /**
     * Used to get the next value from the string using the default padding
     * @return string representing the next parameter
     */
    private String next() {
        return next(defaultPadding);
    }

    /**
     * Returns the next value in the text string given the correct padding
     * @param padding integer representing the amount of padding
     * @return string representing the parsed value
     */
    private String next(int padding) {
        int end = Integer.parseInt(text.substring(index, index + padding));
        index += padding;

        String returnValue = text.substring(index, index + end);
        index += end;
        
        return returnValue;
    }

    /**
     * Moves the index up by one
     */
    private void advance() {
        index++;
    }

    /**
     * Parses out the integer representing the padding for this message
     */
    private void readPadding() {
        this.defaultPadding = Integer.parseInt(text.substring(index, index + 1));

        advance();
    }
}
