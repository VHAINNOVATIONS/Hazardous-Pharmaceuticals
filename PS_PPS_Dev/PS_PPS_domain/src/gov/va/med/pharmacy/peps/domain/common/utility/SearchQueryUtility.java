/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;


/**
 * Help make search queries
 */
public class SearchQueryUtility {

    private static final String EQAPOST = " = '";
  
    /**
     * Cannot instantiate
     */
    private SearchQueryUtility() {
        super();
    }

    /**
     * Adds Criteria for the given ItemStatus
     * 
     * @param itemStatuses List<ItemStatus>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createItemStatusCriteria(List<ItemStatus> itemStatuses, Criteria criteria, String column) {
        if (itemStatuses != null && !itemStatuses.isEmpty()) {
            List<String> strings = new ArrayList<String>(itemStatuses.size());

            for (ItemStatus itemStatus : itemStatuses) {
                strings.add(itemStatus.name());
            }

            criteria.add(Restrictions.in(column, strings));
        }

        if (!itemStatuses.contains(ItemStatus.ARCHIVED)) {
            criteria.add(Restrictions.ne(column, ItemStatus.ARCHIVED.name()));
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Categories
     * 
     * @param categories List<Category>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createCategoriesCriteria(List<Category> categories, Criteria criteria, String column) {

        if (categories != null && !categories.isEmpty()) {
            criteria.add(Restrictions.in(column, categories));
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Medication
     * 
     * @param categories List<Category>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createMedicationCategoriesCriteria(List<Category> categories, Criteria criteria, String column) {

        if (categories != null && !categories.isEmpty()) {
            for (Category cat : categories) {

                if (cat.equals(Category.MEDICATION)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Investigational
     * 
     * @param categories List<Category>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createInvestigationalCategoriesCriteria(List<Category> categories, Criteria criteria,
        String column) {

        if (categories != null && !categories.isEmpty()) {
            for (Category cat : categories) {

                if (cat.equals(Category.INVESTIGATIONAL)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Compound
     * 
     * @param categories List<Category>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createCompoundCategoriesCriteria(List<Category> categories, Criteria criteria, String column) {

        if (categories != null && !categories.isEmpty()) {
            for (Category cat : categories) {

                if (cat.equals(Category.COMPOUND)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Supply
     * 
     * @param categories List<Category>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createSupplyCategoriesCriteria(List<Category> categories, Criteria criteria, String column) {

        if (categories != null && !categories.isEmpty()) {
            for (Category cat : categories) {

                if (cat.equals(Category.SUPPLY)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Herbal
     * 
     * @param subCategories List<SubCategory>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createHerbalSubCategoriesCriteria(List<SubCategory> subCategories, Criteria criteria,
        String column) {

        if (subCategories != null && !subCategories.isEmpty()) {
            for (SubCategory subCat : subCategories) {

                if (subCat.equals(SubCategory.HERBAL)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Chemotherapy
     * 
     * @param subCategories List<SubCategory>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createChemotherapySubCategoriesCriteria(List<SubCategory> subCategories, Criteria criteria,
        String column) {

        if (subCategories != null && !subCategories.isEmpty()) {
            for (SubCategory subCat : subCategories) {

                if (subCat.equals(SubCategory.CHEMOTHERAPY)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Otc
     * 
     * @param subCategories List<SubCategory>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createOtcSubCategoriesCriteria(List<SubCategory> subCategories, Criteria criteria, String column) {

        if (subCategories != null && !subCategories.isEmpty()) {
            for (SubCategory subCat : subCategories) {

                if (subCat.equals(SubCategory.OTC)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds Criteria for the given Veterinary
     * 
     * @param subCategories List<SubCategory>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createVeterinarySubCategoriesCriteria(List<SubCategory> subCategories, Criteria criteria,
        String column) {

        if (subCategories != null && !subCategories.isEmpty()) {
            for (SubCategory subCat : subCategories) {

                if (subCat.equals(SubCategory.VETERINARY)) {
                    criteria.add(Restrictions.eq(column, "Y"));
                    break;
                }
            } // end for
        }

        return criteria;
    }

    /**
     * Adds criteria for local use
     * 
     * @param iStatuses List<LocalUseSearchType>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     * 
     */
    public static Criteria createLocalUseCriteria(List<LocalUseSearchType> iStatuses, Criteria criteria, String column) {

        // can only pick one value from drop-down list
        for (LocalUseSearchType stat : iStatuses) {

            if (stat.isLocalUse()) {
                criteria.add(Restrictions.eq(column, "Y"));
                break;
            } else if (stat.isNotLocalUse()) {
                criteria.add(Restrictions.eq(column, "N"));
                break;
            }
        } // end for

        return criteria;

    }

    /**
     * Returns a string query based on local use, item statuses and request statuses
     * 
     * @param localUse LocalUseSearchType
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @return String
     */
    public static String createStatusLocaUseQuery(LocalUseSearchType localUse, List<ItemStatus> iStatuses,
        List<RequestItemStatus> rStatuses) {

        StringBuffer query = new StringBuffer();

        if (LocalUseSearchType.LOCAL_USE.equals(localUse)) {
            query.append("  WHERE item.localUse = 'Y'");
        } else if (LocalUseSearchType.NOT_LOCAL_USE.equals(localUse)) {
            query.append("  WHERE item.localUse = 'N'");
        } else {
            query.append("  WHERE (item.localUse = 'N' OR item.localUse = 'Y')");
        }

        query.append(createItemStatusQuery(iStatuses));
        query.append(createRequestItemStatusQueryNew(rStatuses));

        return query.toString();
    }

    /**
     * Returns a string query based on local dispense ,local use, item statuses and request statuses
     * 
     * @param localUse localUse
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @return String
     */
    public static String createNdcStatusLocaDispenseQuery(LocalUseSearchType localUse, List<ItemStatus> iStatuses,
        List<RequestItemStatus> rStatuses) {

        StringBuffer query = new StringBuffer();

        if (LocalUseSearchType.LOCAL_USE.equals(localUse)) {
            query.append(" WHERE item.localDispense = 'Y'");
        } else if (LocalUseSearchType.NOT_LOCAL_USE.equals(localUse)) {
            query.append(" WHERE item.localDispense = 'N'");
        } else {
            query.append(" WHERE (item.localDispense = 'N' OR item.localDispense = 'Y')");
        }

        query.append(createItemStatusQuery(iStatuses));
        query.append(createRequestItemStatusQueryNew(rStatuses));

        return query.toString();
    }

    /**
     * Returns a string query based on local use, item statuses and request statuses
     * 
     * @param localUse LocalUseSearchType
     * @param iStatuses List<ItemStatus>
     * @param rStatuses List<RequestItemStatus>
     * @return String
     */
    public static String createNewStatusLocaUseQuery(List<LocalUseSearchType> localUse, List<ItemStatus> iStatuses,
        List<RequestItemStatus> rStatuses) {

        StringBuffer query = new StringBuffer();

        if (localUse.size() == 0) {
            query.append(" WHERE (item.localUse = 'N' OR item.localUse = 'Y') ");
        }

        for (LocalUseSearchType loc : localUse) {
            if (loc.isLocalUse()) {
                query.append(" WHERE item.localUse = 'Y' ");
                break;
            } else if (loc.isNotLocalUse()) {
                query.append(" WHERE item.localUse = 'N' ");
                break;
            } else {
                query.append(" WHERE (item.localUse = 'N' OR item.localUse = 'Y')");
            }

        }

        query.append(createItemStatusQuery(iStatuses));
        query.append(createRequestItemStatusQueryNew(rStatuses));

        return query.toString();
    }

    /**
     * Creates a String for categories query based on category
     * 
     * @param categories List<Category>
     * @return String
     * 
     */
    @SuppressWarnings("unused")
    private static String createCategoriesQuery(List<Category> categories) {
        StringBuffer query = new StringBuffer();
        query.append(" AND Categories IN (");

        for (int i = 0; i < categories.size(); i++) {
            query.append("'").append(categories).append("'");

            if (i < categories.size() - 1) {
                query.append(", ");
            }
        }

        query.append(")");

        return query.toString();
    }

    /**
     * Creates a String for itemStatus query based on statuses
     * 
     * @param itemStatuses List<ItemStatus>
     * @return String
     * 
     */
    private static String createItemStatusQuery(List<ItemStatus> itemStatuses) {
        StringBuffer query = new StringBuffer();
        query.append(" AND item.itemStatus IN (");

        for (int i = 0; i < itemStatuses.size(); i++) {
            query.append("'").append(itemStatuses.get(i).name()).append("'");

            if (i < itemStatuses.size() - 1) {
                query.append(", ");
            }
        }

        query.append(")");

        if (!itemStatuses.contains(ItemStatus.ARCHIVED)) {
            query.append(" AND item.itemStatus <> '").append(ItemStatus.ARCHIVED.name()).append("'");
        }

        return query.toString();
    }

    /**
     * Creates criteria on name of Orderable Item
     * 
     * @param searchType SearchType
     * @param criteria criteria
     * @param value String
     * @return Criteria
     * 
     */
    public static Criteria createOrderableItemNameCriteria(SearchType searchType, Criteria criteria, String value) {

        if (searchType.equals(SearchType.CONTAINS)) {
            criteria.add(Restrictions.ilike(EplOrderableItemDo.OI_NAME, value, MatchMode.ANYWHERE));
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            criteria.add(Restrictions.ilike(EplOrderableItemDo.OI_NAME, value, MatchMode.START));
        } else {
            criteria.add(Restrictions.ilike(EplOrderableItemDo.OI_NAME, value));
        }

        return criteria;

    }

    /**
     * Adds Criteria for the given RequestItemStatus
     * 
     * @param requestItemStatuses List<RequestItemStatus>
     * @param criteria criteria
     * @param column String
     * @return Criteria
     */
    public static Criteria createRequestItemStatusCriteria(List<RequestItemStatus> requestItemStatuses, Criteria criteria,
        String column) {

        if (requestItemStatuses != null && !requestItemStatuses.isEmpty()) {
            List<String> strings = new ArrayList<String>(requestItemStatuses.size());

            for (RequestItemStatus requestItemStatus : requestItemStatuses) {
                strings.add(requestItemStatus.name());
            }

            criteria.add(Restrictions.in(column, strings));
        }

        return criteria;
    }

    /**
     * Creates a query for request item status
     * 
     * @param rStatuses List<ItemStatus>
     * @param column String
     * @return String
     * 
     */
    public static String createRequestItemStatusQuery(List<RequestItemStatus> rStatuses, String column) {
        StringBuffer query = new StringBuffer();
        query.append(" AND (");

        // now gor through request statuses
        int statusCount = 0;

        for (RequestItemStatus stat : rStatuses) {

            if (stat.isApproved()) {
                if (statusCount == 0) {
                    query.append(" item.").append(column).append(EQAPOST).append(RequestItemStatus.APPROVED.name())
                        .append("'");
                } else {
                    query.append(" OR item.").append(column).append(EQAPOST).append(RequestItemStatus.APPROVED.name())
                        .append("'");
                }

                statusCount = 1;
            } else if (stat.isPending()) {
                if (statusCount == 0) {
                    query.append("  item.").append(column).append(EQAPOST).append(RequestItemStatus.PENDING.name())
                        .append("'");
                } else {
                    query.append("  OR item.").append(column).append(EQAPOST).append(RequestItemStatus.PENDING.name())
                        .append("'");
                }

                statusCount = 1;
            } else if (stat.isRejected()) {
                if (statusCount == 0) {
                    query.append("   item.").append(column).append(EQAPOST).append(RequestItemStatus.REJECTED.name())
                        .append("'");
                } else {
                    query.append("   OR item.").append(column).append(EQAPOST).append(RequestItemStatus.REJECTED.name())
                        .append("'");
                }

                statusCount = 1;
            }
        }// end for

        query.append(")");

        return query.toString();
    }

    /**
     * Creates a query for request item status
     * 
     * @param rStatuses List<ItemStatus>
     * @return String
     * 
     */
    public static String createRequestItemStatusQueryNew(List<RequestItemStatus> rStatuses) {
        StringBuffer query = new StringBuffer();

        if (rStatuses.size() == 1) {
            query.append(" AND item.requestStatus = '").append(rStatuses.get(0).name()).append("'");
        } else if (rStatuses.size() == 2) {
            query.append(" AND (item.requestStatus = '").append(rStatuses.get(0).name())
                .append("' OR item.requestStatus = '").append(rStatuses.get(1).name()).append("')");
        }

        return query.toString();
    }

    /**
     * Creates a query for local use
     * 
     * @param localUse boolean
     * @return String
     */
    public static String createLocalUseBeginningWithWhere(boolean localUse) {

        StringBuffer query = new StringBuffer();

        if (localUse) {
            query.append(" WHERE item.localUse = 'Y'");
        } else {
            query.append(" WHERE item.localUse = 'N'");
        }

        return query.toString();
    }

    /**
     * Creates a query based on search type, column name, and value
     * 
     * @param searchType SearchType
     * @param columnName Sring
     * @param value String
     * @return String
     */
    public static String getColumnClause(SearchType searchType, String columnName, String value) {
        StringBuffer query = new StringBuffer();

        if (SearchType.CONTAINS.equals(searchType)) {

            // M5 Part 2--The query below is case sensitive. Ultimately a SQL query such as this below needs to be
            // generated: "select * from epl_products where TO_UPPER(va_product_name) = '%XYZ%';" where XYZ is the
            // value converted to all-upper case letters. The basic problem with this approach though is that
            // each database has their own dialect for doing this, so really need to use the Hibernate objects
            // as much as possible.
            query.append(columnName);
            query.append(" like '%");
            query.append(value);
            query.append("%'");
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            query.append(columnName);
            query.append(" like '");
            query.append(value);
            query.append("%'");
        } else {
            query.append(columnName);
            query.append(EQAPOST);
            query.append(value);
            query.append("'");
        }

        return query.toString();

    }

}
