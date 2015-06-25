/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.WordProcessingFormatter;

import junit.framework.TestCase;


/**
 * Test formatting text blocks.
 */
public class WordProcessingFormatterTest extends TestCase {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(WordProcessingFormatterTest.class);
    
    private static final String SILLY = 
        " This is a silly string of text designed to test the word formatting capability of this formatter.";
    private static final String DIDITWORK = 
        " Did it work?";
    private static final String HELLO =
        "Hello world. This is|";
    private static final String HELLOWORLD =
        "Hello world.";
    
    
    /**
     * WordProcessingFormatterTest
     */
    public WordProcessingFormatterTest() {
        super();
    }
    
    /**
     * setup
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        LOG.debug("----------------------------------- " + getName() + "------------------------------------");
    }

    /**
     * Normal formatting.
     */
    public void testNormalFormat() {
        String text = "Hello world.\r\n"
            + SILLY
            + DIDITWORK;

        String expected = HELLO + " a silly string of|" + " text designed to|" + " test the word|"
            + " formatting|" + " capability of this|" + " formatter. Did it|" + " work?";

        String formattedText = new WordProcessingFormatter(text).format(PPSConstants.I20, PPSConstants.I250, true);

        LOG.debug(formattedText);

        assertEquals("This Text should match", expected, formattedText);
    }

    /**
     * Incorrect line length.
     */
    public void testIncorrectLineLength() {
        String text = HELLOWORLD
            + SILLY
            + DIDITWORK;

        try {
            LOG.debug(new WordProcessingFormatter(text).format(PPSConstants.I4, PPSConstants.I250, true));
            fail("should fail");
        } catch (CommonException e) {
            assertTrue("Error string should match!",
                "The lineLength argument passed is incompatible with the method.".equals(e.getMessage()));
        }
    }

    /**
     * Check for word breakage.
     */
    public void testBrokenWords() {
        String text = HELLOWORLD
            + SILLY
            + DIDITWORK;
        String expected = "Hell|o wo|rld.|"
            + " Thi|s is| a| sil|ly s|trin|g of| tex|t de|sign|ed t|o te|st t|he w|ord |form|atti|ng c|apab|ilit|y"
            + " of| thi|s fo|rmat|ter.|" + " Did| it| wor|k?";

        String formattedText = new WordProcessingFormatter(text).format(PPSConstants.I4, PPSConstants.I250, false);

        LOG.debug(formattedText);

        assertEquals("The text should match", expected, formattedText);
    }

    /**
     * Check for a single long word break.
     */
    public void testBrokenWord() {
        int length = PPSConstants.I2000;
        int lineLength = PPSConstants.I4;

        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            text.append("a");
        }

        StringBuilder expected = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (i > 0 && (i % lineLength) == 0) {
                expected.append('|');
            }

            expected.append("a");
        }

        String formattedText = new WordProcessingFormatter(text.toString()).format(lineLength, length, false);

        LOG.debug(formattedText);

        assertEquals("text should match", expected.toString(), formattedText);
    }

    /**
     * Truncated formatting.
     */
    public void testTruncation() {
        String text = HELLOWORLD
            + SILLY
            + DIDITWORK;

        String expected = HELLO + " a";

        String formattedText = new WordProcessingFormatter(text).format(PPSConstants.I20, PPSConstants.I22, true);

        LOG.debug(formattedText.length() + " - " + formattedText);

        assertEquals("truncated text should match", expected, formattedText);
    }
}
