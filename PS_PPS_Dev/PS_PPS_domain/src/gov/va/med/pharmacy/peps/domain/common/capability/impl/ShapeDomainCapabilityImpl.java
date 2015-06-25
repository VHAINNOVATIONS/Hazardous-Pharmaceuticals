/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ShapeDomainCapability;


/**
 * Perform CRUD operations on shape
 */
public class ShapeDomainCapabilityImpl implements ShapeDomainCapability {

    /**
     * Retrieve a List of possible values to select from
     * 
     * @return List of possible values
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.ShapeDomainCapability#retrieveDomain()
     */
    public List<ShapeVo> retrieveDomain() {


        List<ShapeVo> shapes = new ArrayList<ShapeVo>();

        addShape(shapes, "1", "Barrel");

        addShape(shapes, "2", "Biconcave");

        addShape(shapes, "3", "Biconvex");

        addShape(shapes, "4", "Bowtie");

        addShape(shapes, "5", "Capsule-shape");

        addShape(shapes, "6", "Character-shape");

        addShape(shapes, "7", "Diamond");

        addShape(shapes, "8", "D-shape");

        addShape(shapes, "9", "Egg-shape");

        addShape(shapes, "10", "Eight-sided");

        addShape(shapes, "11", "Elliptical");

        addShape(shapes, "12", "Figure eight-shape");

        addShape(shapes, "13", "Five-sided");

        addShape(shapes, "14", "Four-sided");

        addShape(shapes, "15", "Gear-shape");

        addShape(shapes, "16", "Heart-shape");

        addShape(shapes, "17", "Hourglass-shape");

        addShape(shapes, "18", "Kidney-shape");

        addShape(shapes, "19", "Oblong");

        addShape(shapes, "20", "Oval");

        addShape(shapes, "21", "Ovoid-rectangular");

        addShape(shapes, "22", "Peanut");

        addShape(shapes, "23", "Rectangle");

        addShape(shapes, "24", "Round");

        addShape(shapes, "25", "Seven-sided");

        addShape(shapes, "26", "Shield-shape");

        addShape(shapes, "27", "Six-sided");

        addShape(shapes, "28", "Square");

        addShape(shapes, "29", "Spherical");

        addShape(shapes, "30", "Teardrop-shape");

        addShape(shapes, "31", "Three-sided");

        addShape(shapes, "32", "Triangle");

        addShape(shapes, "33", "U-shape");

        return shapes;
    }

    /**
     * Add a shape to the list of shapes.
     * 
     * @param shapes List of shapes
     * @param id String id
     * @param value String value
     */
    private void addShape(List<ShapeVo> shapes, String id, String value) {
        ShapeVo shape = new ShapeVo();
        shape.setId(value.toLowerCase(Locale.US));
        shape.setValue(value.toUpperCase(Locale.US));
        shapes.add(shape);
    }
}
