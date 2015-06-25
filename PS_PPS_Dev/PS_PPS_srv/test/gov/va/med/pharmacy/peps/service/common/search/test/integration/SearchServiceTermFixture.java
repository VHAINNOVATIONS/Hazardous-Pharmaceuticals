/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import java.util.ListResourceBundle;


/**
 * Resources for search tests
 */
public class SearchServiceTermFixture extends ListResourceBundle { 

    /**
     * CONTENTS
     */
    static final Object[][] CONTENTS = {
        
        // LOCALIZE THIS
        {"generic_name", "ATROPINE"},
        {"generic_name.lastPart", "INE"},
        {"generic_name.firstPart", "ATR"},
        {"generic_name.middlePart", "OP"},
        {"generic_name.wildcardPart", "TR*I"},
        
        {"trade_name", "MaryTradeName"},
        {"trade_name.lastPart", "Name1"},
        {"trade_name.firstPart", "Mary1"},
        {"trade_name.middlePart", "Trade"},
        {"trade_name.wildcardPart", "Tr*e"},
        
        {"product_name", "MaryVAProductName"},
        {"product_name.lastPart", "Name2"},
        {"product_name.firstPart", "Mary2"},
        {"product_name.middlePart", "VAProduct"},
        {"product_name.wildcardPart", "VA*ct"},
        
        {"print_name", "MaryVAPrintName"},
        {"print_name.lastPart", "Name"},
        {"print_name.firstPart", "Mary"},
        {"print_name.middlePart", "VAPrint"},
        {"print_name.wildcardPart", "VA*nt"},

        {"ingredient", "ATROPINE SULFATE"},
        {"ingredient.lastPart", "FATE"},
        {"ingredient.firstPart", "ATROP"},
        {"ingredient.middlePart", "PINE"},
        {"ingredient.wildcardPart", "IN*LFATE"},

        {"ndc", "036628-3672-80"},
        {"ndc.lastPart", "80"},
        {"ndc.firstPart", "036628"},
        {"ndc.middlePart", "3672"},
        {"ndc.wildcardPart", "66*72"},


        {"oi_name", "vistaOi TAB,SA"},
        {"oi_name.lastPart", "B,SA"},
        {"oi_name.firstPart", "vistaO"},
        {"oi_name.middlePart", "taOi TA"},
        {"oi_name.wildcardPart", "s%i"},

        // END OF MATERIAL TO LOCALIZE
    };

    /**
     * Returns the contents of the resource bundle data structure
     * 
     * formatting:  MessageFormat.format(string, object[] data);
     * 
     * @return the contents of the resource bundle.
     */
    public Object[][] getContents() {
        return CONTENTS;
    }

}
