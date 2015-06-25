package gov.va.med.pharmacy.peps.presentation.common.action;

import gov.va.med.pharmacy.peps.common.vo.EntityType;

import java.util.List;

public class SearchForm {

	private String searchForName;
	private List<EntityType> itemTypeList;
	private String entityTypeSelected;

	/**
	 * @param searchForName the searchForName to set
	 */
	public void setSearchForName(String searchForName) {
		this.searchForName = searchForName;
	}

	/**
	 * @return the searchForName
	 */
	public String getSearchForName() {
		return searchForName;
	}

	/**
	 * @param itemTypeList the itemTypeList to set
	 */
	public void setItemTypeList(List<EntityType> itemTypeList) {
		this.itemTypeList = itemTypeList;
	}

	/**
	 * @return the itemTypeList
	 */
	public List<EntityType> getItemTypeList() {
		return itemTypeList;
	}

	/**
	 * @param entityTypeSelected the entityTypeSelected to set
	 */
	public void setEntityTypeSelected(String entityTypeSelected) {
		this.entityTypeSelected = entityTypeSelected;
	}

	/**
	 * @return the entityTypeSelected
	 */
	public String getEntityTypeSelected() {
		return entityTypeSelected;
	}
}
