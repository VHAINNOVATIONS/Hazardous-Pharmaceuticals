/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ShapeVo;


/**
 * Generate ShapeVo
 */
public class ShapeGenerator extends VoGenerator<ShapeVo> {

    /**
     * Generate a list of ShapeVo
     * 
     * @return List<ShapeVo>
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<ShapeVo> generate() {
        List<ShapeVo> shapes = new ArrayList<ShapeVo>();

        shapes.add(newShape("1", "Barrel"));
        shapes.add(newShape("2", "Biconcave"));
        shapes.add(newShape("3", "Biconvex"));
        shapes.add(newShape("4", "Bowtie"));
        shapes.add(newShape("5", "Capsule-shape"));
        shapes.add(newShape("6", "Character-shape"));
        shapes.add(newShape("7", "Diamond"));
        shapes.add(newShape("8", "D-shape"));
        shapes.add(newShape("9", "Egg-shape"));
        shapes.add(newShape("10", "Eight-sided"));
        shapes.add(newShape("11", "Elliptical"));
        shapes.add(newShape("12", "Figure eight-shape"));
        shapes.add(newShape("13", "Five-sided"));
        shapes.add(newShape("14", "Four-sided"));
        shapes.add(newShape("15", "Gear-shape"));
        shapes.add(newShape("16", "Heart-shape"));
        shapes.add(newShape("17", "Hourglass-shape"));
        shapes.add(newShape("18", "Kidney-shape"));
        shapes.add(newShape("19", "Oblong"));
        shapes.add(newShape("20", "Oval"));
        shapes.add(newShape("21", "Ovoid-rectangular"));
        shapes.add(newShape("22", "Peanut"));
        shapes.add(newShape("23", "Rectangle"));
        shapes.add(newShape("24", "Round"));
        shapes.add(newShape("25", "Seven-sided"));
        shapes.add(newShape("26", "Shield-shape"));
        shapes.add(newShape("27", "Six-sided"));
        shapes.add(newShape("28", "Square"));
        shapes.add(newShape("29", "Spherical"));
        shapes.add(newShape("30", "Teardrop-shape"));
        shapes.add(newShape("31", "Three-sided"));
        shapes.add(newShape("32", "Triangle"));
        shapes.add(newShape("33", "U-shape"));

        return shapes;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }

    /**
     * Create a new ShapeVo.
     * 
     * @param id String id
     * @param value String value
     * @return ShapeVo
     */
    private ShapeVo newShape(String id, String value) {
        ShapeVo shape = new ShapeVo();
        shape.setId(id);
        shape.setValue(value);

        return shape;
    }
}
