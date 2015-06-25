/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.presentation.common.converter;


import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import gov.va.med.pharmacy.peps.common.vo.EntityType;


/**
 * 
 * Converter used by spring to turn strings into EntityTypes
 *
 */
public class EntityTypeConverter implements Converter<String, EntityType> {


    @Override
    public EntityType convert(String entityTypeString) {
        EntityType entityType = null;

        if (StringUtils.isNotEmpty(entityTypeString)) {
            entityType = EntityType.valueOf(entityTypeString.toUpperCase(Locale.US));

        }

        return entityType;
    }

}
