/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;

import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.utility.ManagedItemDomainCapabilityFactory;


/**
 * Super class for all Data/Value Object conversion utilities.
 * 
 * @param <VO>
 *            type of {@link ValueObject} converted
 * @param <DO>
 *            type of {@link DataObject} converted
 */
public abstract class Converter<VO extends ValueObject, DO extends DataObject> {

    /** BOTH */
    protected static final String BOTH = "Both";

    /** I_INPATIENT */
    protected static final String I_INPATIENT = "I-Inpatient";

    /** INPATIENT */
    protected static final String INPATIENT = "Inpatient";

    /** O_OUTPATIENT */
    protected static final String O_OUTPATIENT = "O-Outpatient";

    /** OUTPATIENT */
    protected static final String OUTPATIENT = "Outpatient";
    
  //  private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Converter.class);

    private ManagedItemDomainCapabilityFactory managedItemDomainCapabilityFactory;

    /**
     * Convert a Collection of {@link DataObject} into a List of fully populated
     * {@link ValueObject}.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            Collection of {@link DataObject} to convert
     * @return List of fully populated {@link ValueObject}
     * 
     * @see #convert(DataObject)
     */
    public List<VO> convert(Collection<DO> data) {
        List<VO> values = new ArrayList<VO>();

        if (data != null) {
            for (DO current : data) {
                VO value = convert(current);

                if (value != null) {
                    values.add(value);
                }
            }
        }

        return values;
    }

    // This method has been commented out because it has the same method
    // signature as public List<VO> convert(Collection<DO> data),
    // which was causing compiling errors in the project. - sgw 05/31/2011
    // /**
    // * Convert a Collection of {@link ValueObject} into a List of fully
    // populated {@link DataObject}.
    // * <p>
    // * The default implementation should be sufficient for most scenarios, but
    // sub classes can override this method if
    // * necessary.
    // *
    // * @param data Collection of {@link ValueObject} to convert
    // * @return List of fully populated {@link DataObject}
    // *
    // * @see #convert(ValueObject)
    // */

    /**
     * public Set<DO> convert(Collection<VO> data) {
     * 
     * /** Fully copies data from the given {@link DataObject} into a {@link
     * ValueObject}. <p> Parent objects, if any, are minimally populated. Child
     * objects, if any, are not populated at all. Any remaining aggregated
     * {@link ManagedItemVo} are minimally populated. If any of these item types
     * are intended to be fully populated, the appropriate {@link
     * Converter#convert(DataObject)} should be called. <p> The default
     * implementation should be sufficient for most scenarios, but sub classes
     * can override this method if necessary.
     * 
     * @param data {@link DataObject} to convert
     * 
     * @return fully populated {@link ValueObject}
     */
    public VO convert(DO data) {
        VO value = null;

        if (data != null) {
            value = toValueObject(data);

            if (value != null) {
                value.setMinimallyPopulated(false);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * convertAdvanced
     * @param data data object being converted
     * @return the value object
     */
    public VO convertAdvanced(DO data) {
        VO value = null;

        if (data != null) {
            value = toSearchValueObject(data);

            if (value != null) {
                value.setMinimallyPopulated(false);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     */
    public DO convert(VO data) {
        DO value = null;

        if (data != null) {
            value = toDataObject(data);

            if (value != null) {
                value.setMinimallyPopulated(false);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * Convert a Collection of {@link DataObject} into a List of minimally
     * populated {@link ValueObject}.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            Collection of {@link DataObject} to convert
     * @return List of minimally populated {@link ValueObject}
     * 
     * @see #convertMinimal(DataObject)
     */
    public List<VO> convertMinimal(Collection<DO> data) {
        List<VO> values = new ArrayList<VO>();

        if (data != null) {
            for (DO current : data) {
                VO value = convertMinimal(current);

                if (value != null) {
                    values.add(value);
                }
            }
        }

        return values;
    }

    // This method has been commented out because it has the same method
    // signature as public List<VO> convertMinimal(Collection<DO> data),
    // which was causing compiling errors in the project. - sgw 05/31/2011
    // /**
    // * Convert a Collection of {@link ValueObject} into a List of minimally
    // populated {@link DataObject}.
    // * <p>
    // * The default implementation should be sufficient for most scenarios, but
    // sub classes can override this method if
    // * necessary.
    // *
    // * @param data Collection of {@link ValueObject} to convert
    // * @return List of minimally populated {@link DataObject}
    // *
    // * @see #convertMinimal(ValueObject)
    // */
    // public Set<DO> convertMinimal(Collection<VO> data) {
    // Set<DO> values = new HashSet<DO>();
    //
    // if (data != null) {
    // for (VO current : data) {
    // DO value = convertMinimal(current);
    //
    // if (value != null) {
    // values.add(value);
    // }
    // }
    // }
    //
    // return values;
    // }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * {@link ValueObject}.
     * <p>
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the
     * {@link ValueObject#toShortString()} and {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls.
     * <p>
     * <p>
     * This method is only intended to be called for displaying the
     * {@link ValueObject} in a drop-down or multi-select list where a simple
     * text value is displayed and the ID is sent back to the server.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    public VO convertMinimal(DO data) {
        VO value = null;

        if (data != null) {
            value = toMinimalValueObject(data);

            if (value != null) {
                value.setMinimallyPopulated(true);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * Populate the created/modified by/date in the VO from the DO.
     * 
     * @param value
     *            {@link ValueObject}
     * @param data
     *            {@link DataObject}
     */
    private void setCreatedModified(VO value, DO data) {
        if (value.getCreatedBy() == null) {
            value.setCreatedBy(data.getCreatedBy());
        }

        if (value.getCreatedDate() == null) {
            value.setCreatedDate(data.getCreatedDtm());
        }

        if (value.getModifiedBy() == null) {
            value.setModifiedBy(data.getLastModifiedBy());
        }

        if (value.getModifiedDate() == null) {
            value.setModifiedDate(data.getLastModifiedDtm());
        }
    }

    /**
     * Populate the created by/date in the DO from the VO.
     * <p>
     * This does not set the modified by/date as that is set by the DAO!
     * 
     * @param data
     *            {@link DataObject}
     * @param value
     *            {@link ValueObject}
     */
    private void setCreatedModified(DO data, VO value) {
        if (data.getCreatedBy() == null) {
            data.setCreatedBy(value.getCreatedBy());
        }

        if (data.getCreatedDtm() == null) {
            data.setCreatedDtm(value.getCreatedDate());
        }
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * {@link ValueObject}.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the
     * {@link ValueObject#toShortString()}, {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls, and whatever additional data
     * is required for display and processing of a parent item.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    public VO convertParent(DO data) {
        VO value = null;

        if (data != null) {
            value = toParentValueObject(data);

            if (value != null) {
                value.setMinimallyPopulated(true);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * Minimally copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * <p>
     * The returned {@link DataObject} likely only has the primary key data
     * populated.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @return minimally populated {@link DataObject}
     */
    public DO convertMinimal(VO data) {
        DO value = null;

        if (data != null) {
            value = toMinimalDataObject(data);

            if (value != null) {
                value.setMinimallyPopulated(true);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * Convert a Collection of Collection<DO> into a List of
     * List<VO> populated with enough data to be displayed in the
     * search results table.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            Collection of {@link DataObject} to convert
     * @return List of minimally populated {@link ValueObject}
     * 
     * @see #convertMinimal(DataObject)
     */
    public List<VO> convertSearch(Collection<DO> data) {
        List<VO> values = new ArrayList<VO>();

        // Removes duplicate values to prevent multiple rows in simple search
        Set<Object> hs = new LinkedHashSet<Object>(data);
        hs.addAll(data);
        data.clear();
        data.addAll((Collection) hs);

        if (data != null) {
            for (DO current : data) {
                VO value = convertSearch(current);

                if (value != null) {
                    values.add(value);
                }
            }
        }

        return values;
    }

    /**
     * Copies data from the given DO into a VO
     * populated with enough data to be displayed in the search results table.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data DO to convert
     * @return minimally populated VO
     */
    public VO convertSearch(DO data) {
        VO value = null;

        if (data != null) {
            value = toSearchValueObject(data);

            if (value != null) {
                value.setMinimallyPopulated(true);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * Convert a Collection of {@link DataObject} into a List of
     * {@link ValueObject} populated with enough data to be displayed in the
     * table of child {@link ValueObject}.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            Collection of {@link DataObject} to convert
     * @return List of minimally populated {@link ValueObject}
     * 
     * @see #convertMinimal(DataObject)
     */
    public List<VO> convertChild(Collection<DO> data) {
        List<VO> values = new ArrayList<VO>();

        if (data != null) {
            for (DO current : data) {
                VO value = convertChild(current);

                if (value != null) {
                    values.add(value);
                }
            }
        }

        return values;
    }

    /**
     * Copies data from the given {@link DataObject} into a {@link ValueObject}
     * populated with enough data to be displayed in the table of child
     * {@link ValueObject}.
     * <p>
     * The default implementation should be sufficient for most scenarios, but
     * sub classes can override this method if necessary.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    public VO convertChild(DO data) {
        VO value = null;

        if (data != null) {
            value = toChildValueObject(data);

            if (value != null) {
                value.setMinimallyPopulated(true);
                setCreatedModified(value, data);
            }
        }

        return value;
    }

    /**
     * Converts the specified user into a flattened-out String.
     * 
     * @param user
     *            The user VO to flatten out.
     * @return String The flattened-out user VO information.
     * 
     * @see #toUser for the un-flatten companion to this one.
     */
    protected String fromUser(UserVo user) {
        return user.getFirstName() + "_" + user.getLastName() + "_"
                + user.getUsername() + "_" + user.getLocation();
    }

    /**
     * Convenience method to get the {@link ManagedItemDomainCapability} with an
     * EntityType.
     * 
     * @param <K>
     *            type of {@link ManagedItemVo}
     * @param <T>
     *            type of {@link DataObject}
     * @param entityType
     *            {@link EntityType} used to create Spring bean ID for
     *            {@link ManagedItemDomainCapability}
     * @return {@link ManagedItemDomainCapability} for the given
     *         {@link EntityType}
     * 
     * @see ManagedItemDomainCapabilityFactory#getDomainCapability(EntityType)
     */
    @SuppressWarnings("unchecked")
    protected
        <K extends ManagedItemVo, T extends DataObject> ManagedItemDomainCapability<K, T> getManagedItemDomainCapability(
            EntityType entityType) {
        return (ManagedItemDomainCapability<K, T>) managedItemDomainCapabilityFactory
                .getDomainCapability(entityType);
    }

    /**
     * Return the Spring managed instance for the given {@link DataAccessObject}
     * .
     * 
     * @param <T>
     *            type of {@link DataAccessObject}
     * @param clazz
     *            {@link DataAccessObject}
     * @return {@link DataAccessObject}
     */
    @SuppressWarnings("rawtypes")
    protected <T extends DataAccessObject> T getDataAccessObject(Class<T> clazz) {
        return (T) managedItemDomainCapabilityFactory
                .getDataAccessObject(clazz);
    }

    /**
     * isEmptyOrNotSelected
     * @param value String to test
     * @return true if the String is equal to "EMPTY" or "NOT SELECTED",
     *         ignoring case
     */
    protected boolean isEmptyOrNotSelected(String value) {
        return "EMPTY".equalsIgnoreCase(value)
                || "NOT SELECTED".equalsIgnoreCase(value);
    }

    /**
     * setManagedItemDomainCapabilityFactory
     * @param managedItemDomainCapabilityFactory managedItemDomainCapabilityFactory property
     */
    public void setManagedItemDomainCapabilityFactory(
            ManagedItemDomainCapabilityFactory managedItemDomainCapabilityFactory) {
        this.managedItemDomainCapabilityFactory = managedItemDomainCapabilityFactory;
    }

    /**
     * Convert a "Y" or "N" to a boolean true or false.
     * 
     * @param yOrN
     *            String
     * @return boolean
     */
    protected boolean toBoolean(String yOrN) {
        return "Y".equals(yOrN);
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     */
    protected abstract DO toDataObject(VO data);

    /**
     * Minimally copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * <p>
     * The returned {@link DataObject} likely only has the primary key data
     * populated.
     * <p>
     * Default implementation calls {@link #toDataObject(ValueObject)}.
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @return minimally populated {@link DataObject}
     */
    protected DO toMinimalDataObject(VO data) {
        return toDataObject(data);
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * {@link ValueObject}.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the
     * {@link ValueObject#toShortString()} and {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the
     * {@link ValueObject} in a drop-down or multi-select list where a simple
     * text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    protected VO toMinimalValueObject(DO data) {
        return toValueObject(data);
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * {@link ValueObject}.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the
     * {@link ValueObject#toShortString()}, {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls, and whatever additional data
     * is required for display and processing of a parent item.
     * <p>
     * Default implementation calls {@link #toMinimalValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    protected VO toParentValueObject(DO data) {
        return toMinimalValueObject(data);
    }

    /**
     * Copies data from the given {@link DataObject} into a {@link ValueObject}
     * populated with enough data to be displayed in the search results table.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    protected VO toSearchValueObject(DO data) {
        return toValueObject(data);
    }

    /**
     * Copies data from the given {@link DataObject} into a {@link ValueObject}
     * populated with enough data to be displayed in the table of child
     * {@link ValueObject}.
     * <p>
     * Default implementation calls {@link #toMinimalValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    protected VO toChildValueObject(DO data) {
        return toMinimalValueObject(data);
    }

    /**
     * Creates userVO given last name and first name
     * 
     * @param data
     *            String first/last user name delimited by a '_'
     * @return UserVo
     * 
     * @see #fromUser(UserVo)
     */
    protected UserVo toUser(String data) {
        UserVo user = new UserVo();

        if (data == null) {
            user.setFirstName("");
            user.setLastName("");
            user.setUsername("");
            user.setLocation("");
        } else {
            String[] names = data.split("_");
            user.setFirstName(names[0]);

            if (names.length > 1) {
                user.setLastName(names[1]);
            } else {
                user.setLastName("");
            }

            if (names.length > 2) {
                user.setUsername(names[2]);
            } else {
                user.setUsername("");
            }

            if (names.length > PPSConstants.I3) {
                user.setLocation(names[PPSConstants.I3]);
            } else {
                user.setUsername("");
            }
        }

        return user;
    }

    /**
     * Fully copies data from the given DO into a VO
     * 
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data DO to convert
     * @return fully populated VO
     */
    protected abstract VO toValueObject(DO data);

    /**
     * Convert a true/false into Y/N.
     * 
     * @param value
     *            boolean
     * @return String "Y" or "N"
     */
    protected String toYesOrNo(Boolean value) {
        if (value != null && value) {
            return "Y";
        } else {
            return "N";
        }
    }
}
