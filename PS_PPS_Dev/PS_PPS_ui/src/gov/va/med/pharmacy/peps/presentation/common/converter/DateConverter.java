/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.converter;


import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;


/**
 * 
 * DateConverter's brief summary
 * 
 * Details of DateConverter's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class DateConverter implements Converter<String, Date> {

    private static final Logger LOG = Logger.getLogger(DateConverter.class);

    @Override
    public Date convert(String dateString) {
        Date convertedDate = null;

        if (StringUtils.isNotEmpty(dateString)) {

            try {
                convertedDate = new Date(Long.parseLong(dateString));
            } catch (NumberFormatException e) {

                try {
                    convertedDate = DateFormatUtility.convertToDateStrictly(dateString);
                } catch (ParseException e1) {
                    LOG.info("Unable to parse string '" + dateString + "' into a valid Date.");
                    throw new IllegalArgumentException(e1.getLocalizedMessage());
                }
            }
        }

        return convertedDate;
    }
}
