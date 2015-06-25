/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.displaytag;


import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.TableModel;

import gov.va.med.pharmacy.peps.common.utility.ResourceBundleUtility;


/**
 * Add the HTML title attribute on the header cells to indicate sorting options.
 */
public class SortTitleTableDecorator extends TableDecorator {
    private static final String RESOURCE_BUNDLE = "resources";
    private static final String TITLE_ATTRIBUTE = "title";

    private static final String ASCENDING_SORT = "table.sort.ascending";
    private static final String DESCENDING_SORT = "table.sort.descending";

    /**
     * Initialize the TableTecorator instance.
     * 
     * @param pageContext PageContext
     * @param decorated decorated object (usually a list)
     * @param tableModel table model
     * 
     * @see org.displaytag.decorator.Decorator#init(javax.servlet.jsp.PageContext, java.lang.Object,
     *      org.displaytag.model.TableModel)
     */
    @Override
    public void init(PageContext pageContext, Object decorated, TableModel tableModel) {
        setHeaderSortTitles(tableModel, pageContext.getRequest().getLocale());

        super.init(pageContext, decorated, tableModel);
    }

    /**
     * Set the HTML title attribute on the header cells to indicate sorting options.
     * 
     * @param tableModel TableModel with header cells
     * @param locale Locale to localize HTML title attribute
     */
    private void setHeaderSortTitles(TableModel tableModel, Locale locale) {
        List<HeaderCell> headerCells = tableModel.getHeaderCellList();

        for (HeaderCell headerCell : headerCells) {
            if (headerCell.getSortable()) {
                if (headerCell.isAlreadySorted() && tableModel.isSortOrderAscending()) {
                    headerCell.getHeaderAttributes().put(TITLE_ATTRIBUTE, getLocalizedTitle(DESCENDING_SORT, locale));
                } else {
                    headerCell.getHeaderAttributes().put(TITLE_ATTRIBUTE, getLocalizedTitle(ASCENDING_SORT, locale));
                }
            }
        }
    }

    /**
     * Localize the given key for the given {@link Locale} using the {@link #RESOURCE_BUNDLE}.
     * 
     * @param key String key
     * @param locale {@link Locale} to localize
     * @return localized value for given key
     */
    private String getLocalizedTitle(String key, Locale locale) {
        return ResourceBundleUtility.getResourceBundleValue(RESOURCE_BUNDLE, key, locale);
    }
}
