/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.converter;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.SingleMultiSourceProductVo;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * 
 * Converter classed used by spring to convert string ids into value objects
 *
 */
public class ValueObjectConverterFactory implements ConverterFactory<String, ValueObject> {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ValueObjectConverterFactory.class);

    @Autowired
    private DomainService domainService;

    @Autowired
    private ManagedItemService managedItemService;

    @Autowired
    private DomainUtility domainUtility;

    @Override
    public <T extends ValueObject> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToValueObjectConverter<T>(targetType);
    }

    /**
     * 
     * StringToValueObjectConverter's brief summary
     * 
     * Converts string ids to value objects
     *
     * @param <T>
     */
    private final class StringToValueObjectConverter<T extends ValueObject> implements Converter<String, T> {

        private final Class<T> valueObjectType;

        /**
         * 
         * Description here
         *
         * @param valueObjectType the type of the value object
         */
        public StringToValueObjectConverter(Class<T> valueObjectType) {
            this.valueObjectType = valueObjectType;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T convert(String id) {
            return (T) getValueObject(valueObjectType, id);
        }
    }

    /**
     * 
     * Gets the value object given a class and id
     *
     * @param clazz of the value object
     * @param id string id
     * @return the ValueObject
     */
    protected ValueObject getValueObject(Class<? extends ValueObject> clazz, String id) {

        ValueObject vo = null;

        try {
            if (clazz == ProductVo.class) {
                vo = managedItemService.retrieve(id, EntityType.PRODUCT);
            } else if (clazz == NdcVo.class) {
                vo = managedItemService.retrieve(id, EntityType.NDC);
            } else if (clazz == OrderableItemVo.class) {
                vo = managedItemService.retrieve(id, EntityType.ORDERABLE_ITEM);
            } else if (clazz == IngredientVo.class) {
                vo = domainService.retrieveIngredientName(id);
            } else if (clazz == DrugUnitVo.class) {
                vo = domainService.retrieveDrugUnit(id);
            } else if (clazz == DispenseUnitVo.class) {
                vo = domainService.retrieveDispenseUnit(id);
            } else if (clazz == GenericNameVo.class) {
                vo = domainService.retrieveGenericName(id);
            } else if (clazz == DrugClassVo.class) {
                vo = domainService.retrieveDrugClass(id);
            } else if (clazz == CsFedScheduleVo.class) {
                vo = domainService.retrieveCsFedSchedule(id);
            } else if (clazz == SpecialHandlingVo.class) {
                vo = domainService.retrieveSpecialHandling(id);
            } else if (clazz == PackageTypeVo.class) {
                vo = domainService.retrievePackageType(id);
            } else if (clazz == ManufacturerVo.class) {
                vo = domainService.retrieveManufacturer(id);
            } else if (clazz == OrderUnitVo.class) {
                vo = domainService.retrieveOrderUnit(id);
            } else if (clazz == ColorVo.class) {
                vo = domainService.retrieveColor(id);
            } else if (clazz == ShapeVo.class) {
                vo = domainService.retrieveShape(id);
            } else if (clazz == IntendedUseVo.class) {
                vo = domainService.retrieveIntendedUse(id);
            } else if (clazz == SingleMultiSourceProductVo.class) {
                vo = retrieveSingleMultiSourceProduct(id);
            } else if (clazz == DosageFormVo.class) {
                vo = domainService.retrieveDosageForm(id);
            } else if (clazz == DrugClassificationTypeVo.class) {
                vo = domainService.retrieveDrugClassificationType(id);
            } else if (clazz == DrugTextVo.class) {
                vo = domainService.retrieveNationalDrugText(id);
            } else if (clazz == OtcRxVo.class) {
                vo = domainService.retrieveOtcRx(id);
            } else if (clazz == PossibleDosagesPackageVo.class) {

                for (PossibleDosagesPackageVo possibleDosagesPackageVo : (List<PossibleDosagesPackageVo>) domainUtility
                    .getDomain(FieldKey.POSSIBLE_DOSAGE_PACKAGE)) {
                    if (possibleDosagesPackageVo.getValue().equals(id)) {
                        vo = possibleDosagesPackageVo;
                    }
                }
            } else if (clazz == StandardMedRouteVo.class) {
                vo = domainService.retrieveStandardMedRoute(id);
            }

        } catch (ItemNotFoundException e) {
            vo = instantiateValueObject(clazz);

            if (vo instanceof ManagedDataVo) {
                ((ManagedDataVo) vo).setValue("");
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getLocalizedMessage(), e);
            }
        } catch (NumberFormatException e) {
            vo = instantiateValueObject(clazz);

            if (vo instanceof ManagedDataVo) {
                ((ManagedDataVo) vo).setValue("");
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getLocalizedMessage(), e);
            }
        }

        return vo;
    }

    /**
     * 
     * Instantiates a value object given its class
     *
     * @param clazz of the value object
     * @return an new ValueObject
     */
    private ValueObject instantiateValueObject(Class clazz) {
        ValueObject valueObject = null;

        try {
            valueObject = (ValueObject) clazz.newInstance();
        } catch (InstantiationException e) {
            LOG.error(
                "An InstantiationException was caught while calling newInstance() on the class " + clazz.getCanonicalName()
                    + ".", e);
        } catch (IllegalAccessException e) {
            LOG.error(
                "An IllegalAccessException was caught while calling newInstance() on the class " + clazz.getCanonicalName()
                    + ".", e);
        }

        return valueObject;
    }

    /**
     * 
     * Retrieves the SingleMultiSourceProductVo
     *
     * @param id String
     * @return the SingleMultiSourceProductVo
     */
    private SingleMultiSourceProductVo retrieveSingleMultiSourceProduct(String id) {

        List<SingleMultiSourceProductVo> lstSingleMultiSourceProduct =
            domainUtility.getDomain(FieldKey.SINGLE_MULTISOURCE_PRODUCT);

        for (SingleMultiSourceProductVo singleMultiSourceProduct : lstSingleMultiSourceProduct) {
            if (singleMultiSourceProduct.getId().equalsIgnoreCase(StringUtils.trim(id))) {
                return singleMultiSourceProduct;
            }
        }

        return null;
    }
}
