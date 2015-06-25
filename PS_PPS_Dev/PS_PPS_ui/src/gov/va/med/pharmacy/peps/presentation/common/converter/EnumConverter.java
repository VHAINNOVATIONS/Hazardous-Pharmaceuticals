/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.converter;


import org.springframework.core.convert.converter.Converter;


//import org.springframework.binding.convert.converters.Converter;


/**
 * Convert the given source into an instance of the correct enumeration.
 */
public class EnumConverter implements Converter<String, Enum> {

// 2012-04-17 jbarde: updated to use the spring core converter instead of the converter from spring binding.

//    /**
//     * Get the source class
//     * 
//     * @return String class
//     * 
//     * @see org.springframework.binding.convert.Converter#getSourceClass()
//     */
//    public Class getSourceClass() {
//        return String.class;
//    }
//
//    /**
//     * Get the target class
//     * 
//     * @return Enum class
//     * 
//     * @see org.springframework.binding.convert.Converter#getTargetClass()
//     */
//    public Class getTargetClass() {
//        return Enum.class;
//    }
//
//    /**
//     * Convert the provided source object argument to an instance of the specified target class.
//     * 
//     * @param source the source object to convert, which must be an instance of {@link #getSourceClass()}
//     * @param targetClass the target class to convert the source to, which must be equal to or a specialization of
//     *            {@link #getTargetClass()}
//     * @return the converted object, which must be an instance of the <code>targetClass</code>
//     * 
//     * @see org.springframework.binding.convert.converters.AbstractConverter#doConvert(java.lang.Object, java.lang.Class,
//     *      java.lang.Object)
//     */
//    public Object convertSourceToTargetClass(Object source, Class targetClass) {
//        return Enum.valueOf(targetClass, (String) source);
//    }

    /**
     * convert string to enum
     * @param source String
     * @return Enum
     * 
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public Enum convert(String source) {
        return Enum.valueOf(Enum.class, source);
    }
}
