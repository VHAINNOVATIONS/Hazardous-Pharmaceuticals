/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ColorDomainCapability;


/**
 * Perform CRUD operations on colors
 */
public class ColorDomainCapabilityImpl implements ColorDomainCapability {

    /**
     * Retrieve a List of possible colors to select from
     * 
     * @return List of colors
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.ColorDomainCapability#retrieveDomain()
     */
    public List<ColorVo> retrieveDomain() {
        List<ColorVo> colors = new ArrayList<ColorVo>();

        addColor(colors, "14", "Beige");
        addColor(colors, "73", "Black");
        addColor(colors, "1", "Blue");
        addColor(colors, "2", "Brown");
        addColor(colors, "3", "Clear");

        addColor(colors, "4", "Gold");

        addColor(colors, "5", "Gray");

        addColor(colors, "6", "Green");

        addColor(colors, "44", "Maroon");

        addColor(colors, "7", "Orange");

        addColor(colors, "8", "Pink");

        addColor(colors, "9", "Purple");

        addColor(colors, "10", "Red");

        addColor(colors, "11", "Tan");

        addColor(colors, "12", "White");

        addColor(colors, "13", "Yellow");

        addColor(colors, "69", "Beige & Red");

        addColor(colors, "55", "Black & Green");

        addColor(colors, "70", "Black & Teal");

        addColor(colors, "48", "Black & Yellow");

        addColor(colors, "52", "Blue & Brown");

        addColor(colors, "45", "Blue & Gray");

        addColor(colors, "71", "Blue & Orange");

        addColor(colors, "53", "Blue & Peach");

        addColor(colors, "34", "Blue & Pink");

        addColor(colors, "19", "Blue & White");

        addColor(colors, "26", "Blue & White Specks");

        addColor(colors, "21", "Blue & Yellow");

        addColor(colors, "47", "Brown & Clear");

        addColor(colors, "54", "Brown & Orange");

        addColor(colors, "28", "Brown & Peach");

        addColor(colors, "16", "Brown & Red");

        addColor(colors, "57", "Brown & White");

        addColor(colors, "27", "Brown & Yellow");

        addColor(colors, "49", "Clear & Green");

        addColor(colors, "46", "Dark Green & Light Green");

        addColor(colors, "51", "Gold & White");

        addColor(colors, "61", "Gray & Peach");

        addColor(colors, "39", "Gray & Pink");

        addColor(colors, "58", "Gray & Red");

        addColor(colors, "67", "Gray & White");

        addColor(colors, "68", "Gray & Yellow");

        addColor(colors, "65", "Green & Orange");

        addColor(colors, "63", "Green & Peach");

        addColor(colors, "56", "Green & Pink");

        addColor(colors, "43", "Green & Purple");

        addColor(colors, "62", "Green & Turquoise");

        addColor(colors, "30", "Green & White");

        addColor(colors, "22", "Green & Yellow");

        addColor(colors, "42", "Lavender & White");

        addColor(colors, "40", "Maroon & Pink");

        addColor(colors, "50", "Orange & Turquoise");

        addColor(colors, "64", "Orange & White");

        addColor(colors, "23", "Orange & Yellow");

        addColor(colors, "60", "Peach & Purple");

        addColor(colors, "66", "Peach & Red");

        addColor(colors, "18", "Peach & White");

        addColor(colors, "15", "Pink & Purple");

        addColor(colors, "37", "Pink & Red Specks");

        addColor(colors, "29", "Pink & Turquoise");

        addColor(colors, "25", "Pink & White");

        addColor(colors, "72", "Pink & Yellow");

        addColor(colors, "17", "Red & Turquoise");

        addColor(colors, "35", "Red & White");

        addColor(colors, "20", "Red & Yellow");

        addColor(colors, "33", "Tan & White");

        addColor(colors, "59", "Turquoise & White");

        addColor(colors, "24", "Turquoise & Yellow");

        addColor(colors, "32", "White & Blue Specks");

        addColor(colors, "41", "White & Red Specks");

        addColor(colors, "38", "White & Yellow");

        addColor(colors, "31", "Yellow & Gray");

        addColor(colors, "36", "Yellow & White");

        return colors;
    }

    /**
     * Add a color
     * 
     * @param colors List
     * @param id String
     * @param value String
     */
    private void addColor(List<ColorVo> colors, String id, String value) {
        ColorVo color = new ColorVo();
        color.setId(value.toLowerCase(Locale.US));
        color.setValue(value.toUpperCase(Locale.US));
        colors.add(color);
    }
}
