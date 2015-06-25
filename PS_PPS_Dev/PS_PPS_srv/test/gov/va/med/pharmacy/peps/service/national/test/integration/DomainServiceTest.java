/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * This test tests the Domain Service.
 */
public class DomainServiceTest extends IntegrationTestCase {

    private DomainService domainService;
    private String empty = "Collection was empty";
    
    /**
     * Constructor
     * 
     * @param name A name for the test.
     */
    public DomainServiceTest(String name) {
        super(name);
    }

    /**
     * setup the test
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        domainService = getNationalService(DomainService.class);
    }

    /**
     * test the getDosageForms method
     * 
     */
    public void testGetDosageForms() {
        List<DosageFormVo> dosageForms = domainService.getDosageForms();

        assertFalse(empty, dosageForms.isEmpty());
    }

    /**
     * test the getColors method
     * 
     */
    public void testGetColors() {
        List<ColorVo> colors = domainService.getColors();

        assertFalse(empty, colors.isEmpty());
    }

    /**
     * Should not return null
     */
    public void testGetDataFields() {
        DataFields dataFields = domainService.getDataFields();

        assertFalse(empty, dataFields.isEmpty());
    }

    /**
     * test the getDrugClasses method
     * 
     */
    public void testGetDrugClasses() {
        List<DrugClassVo> drugClasses = domainService.getDrugClasses();

        assertFalse(empty, drugClasses.isEmpty());
    }

    /**
     * test the getManufactures method
     * 
     */
    public void testGetManufacturers() {
        List<ManufacturerVo> manufacturers = domainService.getManufacturers();

        assertFalse(empty, manufacturers.isEmpty());
    }

    /**
     * test the getPackageTypes method
     * 
     */
    public void testGetPackageTypes() {
        List<PackageTypeVo> packageTypes = domainService.getPackageTypes();

        assertFalse(empty, packageTypes.isEmpty());
    }

    /**
     * test the getRoutes method
     * 
     * @throws Exception Exception
     */
    public void testGetRoutes() throws Exception {
        List<StandardMedRouteVo> routes = domainService.getRoutes();

        assertFalse(empty, routes.isEmpty());
    }

    /**
     * test the getShapes method
     * 
     */
    public void testGetShapes() {
        List<ShapeVo> shapes = domainService.getShapes();

        assertFalse(empty, shapes.isEmpty());
    }
}
