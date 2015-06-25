/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ColorVo;


/**
 * Generate ColorVo
 */
public class ColorGenerator extends VoGenerator<ColorVo> {

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Generate a list of ColorVo
     * 
     * @return List<ColorVo>
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<ColorVo> generate() {
        List<ColorVo> colors = new ArrayList<ColorVo>();

        colors.add(newColor("14", "Beige"));
        colors.add(newColor("73", "Black"));
        colors.add(newColor("1", "Blue"));
        colors.add(newColor("2", "Brown"));
        colors.add(newColor("3", "Clear"));
        colors.add(newColor("4", "Gold"));
        colors.add(newColor("5", "Gray"));
        colors.add(newColor("6", "Green"));
        colors.add(newColor("44", "Maroon"));
        colors.add(newColor("7", "Orange"));
        colors.add(newColor("8", "Pink"));
        colors.add(newColor("9", "Purple"));
        colors.add(newColor("10", "Red"));
        colors.add(newColor("11", "Tan"));
        colors.add(newColor("12", "White"));
        colors.add(newColor("13", "Yellow"));
        colors.add(newColor("69", "Beige & Red"));
        colors.add(newColor("55", "Black & Green"));
        colors.add(newColor("70", "Black & Teal"));
        colors.add(newColor("48", "Black & Yellow"));
        colors.add(newColor("52", "Blue & Brown"));
        colors.add(newColor("45", "Blue & Gray"));
        colors.add(newColor("71", "Blue & Orange"));
        colors.add(newColor("53", "Blue & Peach"));
        colors.add(newColor("34", "Blue & Pink"));
        colors.add(newColor("19", "Blue & White"));
        colors.add(newColor("26", "Blue & White Specks"));
        colors.add(newColor("21", "Blue & Yellow"));
        colors.add(newColor("47", "Brown & Clear"));
        colors.add(newColor("54", "Brown & Orange"));
        colors.add(newColor("28", "Brown & Peach"));
        colors.add(newColor("16", "Brown & Red"));
        colors.add(newColor("57", "Brown & White"));
        colors.add(newColor("27", "Brown & Yellow"));
        colors.add(newColor("49", "Clear & Green"));
        colors.add(newColor("46", "Dark Green & Light Green"));
        colors.add(newColor("51", "Gold & White"));
        colors.add(newColor("61", "Gray & Peach"));
        colors.add(newColor("39", "Gray & Pink"));
        colors.add(newColor("58", "Gray & Red"));
        colors.add(newColor("67", "Gray & White"));
        colors.add(newColor("68", "Gray & Yellow"));
        colors.add(newColor("65", "Green & Orange"));
        colors.add(newColor("63", "Green & Peach"));
        colors.add(newColor("56", "Green & Pink"));
        colors.add(newColor("43", "Green & Purple"));
        colors.add(newColor("62", "Green & Turquoise"));
        colors.add(newColor("30", "Green & White"));
        colors.add(newColor("22", "Green & Yellow"));
        colors.add(newColor("42", "Lavender & White"));
        colors.add(newColor("40", "Maroon & Pink"));
        colors.add(newColor("50", "Orange & Turquoise"));
        colors.add(newColor("64", "Orange & White"));
        colors.add(newColor("23", "Orange & Yellow"));
        colors.add(newColor("60", "Peach & Purple"));
        colors.add(newColor("66", "Peach & Red"));
        colors.add(newColor("18", "Peach & White"));
        colors.add(newColor("15", "Pink & Purple"));
        colors.add(newColor("37", "Pink & Red Specks"));
        colors.add(newColor("29", "Pink & Turquoise"));
        colors.add(newColor("25", "Pink & White"));
        colors.add(newColor("72", "Pink & Yellow"));
        colors.add(newColor("17", "Red & Turquoise"));
        colors.add(newColor("35", "Red & White"));
        colors.add(newColor("20", "Red & Yellow"));
        colors.add(newColor("33", "Tan & White"));
        colors.add(newColor("59", "Turquoise & White"));
        colors.add(newColor("24", "Turquoise & Yellow"));
        colors.add(newColor("32", "White & Blue Specks"));
        colors.add(newColor("41", "White & Red Specks"));
        colors.add(newColor("38", "White & Yellow"));
        colors.add(newColor("31", "Yellow & Gray"));
        colors.add(newColor("36", "Yellow & White"));

        return colors;
    }

    /**
     * Add a color
     * 
     * @param id String
     * @param value String
     * @return ColorVo
     */
    private ColorVo newColor(String id, String value) {
        ColorVo color = new ColorVo();
        color.setId(id);
        color.setValue(value);

        return color;
    }

}
